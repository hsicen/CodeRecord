package com.toucheart.library.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.LongSparseArray;

import com.toucheart.library.util.InstallUtil;
import com.toucheart.library.util.LogUtils;

import java.io.File;

/**
 * <p>作者：黄思程  2018/4/13 11:20
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：apk下载安装Service
 * <p>
 * 使用该Service需要在Manifest文件中注册
 * 添加FileProvider授权码字符串
 */
public class DownApkService extends Service {
    private DownloadManager mDownloadManager;
    private DownloadBinder mBinder = new DownloadBinder();
    private LongSparseArray<String> mApkPaths;
    private DownloadFinishReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        mApkPaths = new LongSparseArray<>();
        //注册下载完成的广播
        mReceiver = new DownloadFinishReceiver();
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);//取消注册广播接收者
        super.onDestroy();
    }

    /**
     * Download的Binder
     */
    public class DownloadBinder extends Binder {
        private long mDownloadId;

        /**
         * 下载
         *
         * @param apkUrl 下载的url
         * @return 下载id
         */
        public long startDownload(String apkUrl) {
            //点击下载
            //删除原有的APK
            InstallUtil.clearApk(DownApkService.this, apkUrl + ".apk");
            //使用DownLoadManager来下载
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
            //将文件下载到自己的Download文件夹下,必须是External的
            //这是DownloadManager的限制
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), apkUrl + ".apk");
            request.setDestinationUri(Uri.fromFile(file));

            //可以在这里添加请求头
            //request.addRequestHeader(AppClient.TOKEN_KEY, AppClient.getTokenValue())
            //VISIBILITY_HIDDEN 下载UI不会显示，也不会显示在通知中，如果设置该值，需要声明 Android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle(apkUrl);
            //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            //添加请求 开始下载
            mDownloadId = mDownloadManager.enqueue(request);
            LogUtils.d("DownloadBinder", file.getAbsolutePath());
            mApkPaths.put(mDownloadId, file.getAbsolutePath());
            return mDownloadId;
        }

        /**
         * 获取进度信息
         *
         * @param downloadId 要获取下载的id
         * @return 进度信息 max-100
         */
        public int getProgress(long downloadId) {

            return DownApkService.this.getProgress(downloadId);
        }

        /**
         * 获取下载id
         *
         * @return id
         */
        public long getDownloadId() {

            return mDownloadId;
        }

        /**
         * 根据下载id获取应用下载的路径
         *
         * @param id 下载id
         * @return 应用路径
         */
        public String getApkPathById(Long id) {

            return mApkPaths.get(id);
        }

    }

    /**
     * 获取下载进度
     *
     * @param downloadId 下载的id
     * @return 进度 0-100
     */
    private int getProgress(long downloadId) {
        //查询进度
        DownloadManager.Query query = new DownloadManager.Query()
                .setFilterById(downloadId);
        Cursor cursor = null;
        int progress = 0;
        try {
            cursor = mDownloadManager.query(query);//获得游标
            if (cursor != null && cursor.moveToFirst()) {
                //当前的下载量
                int downloadSoFar = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //文件总大小
                int totalBytes = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                progress = (int) (downloadSoFar * 1.0f / totalBytes * 100);
            }
        } finally {
            if (cursor != null) {

                cursor.close();
            }
        }

        return progress;
    }

    /*** 下载完成的广播*/
    private class DownloadFinishReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                //下载完成的广播接收者
                long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //小于100时，是用户点击了取消
                if (getProgress(completeDownloadId) < 100) {
                    return;
                }
                String apkPath = mApkPaths.get(completeDownloadId);
                if (apkPath != null && !apkPath.isEmpty()) {
                    InstallUtil.setPermission(apkPath);//提升读写权限,否则可能出现解析异常
                    InstallUtil.install(context, apkPath);
                } else {
                    LogUtils.e("DownloadFinishReceiver", "apkPath is null");
                }
            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
                LogUtils.e("action_notification_clicked", "notification is clicked");
            }
        }
    }
}
