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
import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;

import com.toucheart.library.util.IOUtils;
import com.toucheart.library.util.InstallUtil;
import com.toucheart.library.util.LogUtils;
import com.toucheart.library.util.OpenFileUtil;

import java.io.File;

/**
 * <p>作者：黄思程  2018/4/13 11:09
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：下载文件服务
 * 使用该Service需要在Manifest文件中注册
 * 添加FileProvider授权码字符串
 */
public class DownFileService extends Service {
    /*** 默认下载路径*/
    public static final String DOWNLOAD_DIR = "/Download/";
    private static final String TAG = "DownFileService";

    private DownloadManager mDownloadManager;
    private DownloadBinder mBinder = new DownloadBinder();
    private LongSparseArray<String> mApkPaths;
    private DownloadReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        mApkPaths = new LongSparseArray<>();
        //注册下载完成的广播
        mReceiver = new DownloadReceiver();
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

        /**
         * 下载
         *
         * @param apkUrl   下载的url
         * @param fileName 文件名
         * @return 下载id
         */
        public long startDownload(String apkUrl, String fileName) {
            //使用DownLoadManager来下载
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));

            //可以在这里添加请求头
            //request.addRequestHeader(AppClient.TOKEN_KEY, AppClient.getTokenValue())
            //设置WIFI下才能下载
            //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)

            //设置文件类型，可以在下载结束后自动打开该文件
            request.setMimeType(OpenFileUtil.getFileType(apkUrl));

            //sdcard的目录下的download文件夹，必须设置
            request.setDestinationInExternalPublicDir(DOWNLOAD_DIR, fileName);
            //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

            //在通知栏中显示，默认就是显示的
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setVisibleInDownloadsUi(true);
            request.setTitle(fileName);

            //添加请求 开始下载
            long downloadId = mDownloadManager.enqueue(request);
            mApkPaths.put(downloadId, Environment.getExternalStoragePublicDirectory
                    (DOWNLOAD_DIR).getAbsolutePath() + File.separator + fileName);
            LogUtils.d(TAG, Environment.getExternalStoragePublicDirectory
                    (DOWNLOAD_DIR).getAbsolutePath() + File.separator + fileName);

            return downloadId;
        }

        /**
         * 获取进度信息
         *
         * @param downloadId 要获取下载的id
         * @return 进度信息 max-100
         */
        public int getProgress(long downloadId) {

            return DownFileService.this.getProgress(downloadId);
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
            IOUtils.closeIO(cursor);
        }

        return progress;
    }

    /**
     * 下载完成的广播
     */
    private class DownloadReceiver extends BroadcastReceiver {

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
                if (!TextUtils.isEmpty(apkPath)) {
                    InstallUtil.setPermission(apkPath);//提升读写权限,否则可能出现解析异常
                    //下载完成后安装
                    //InstallUtil.install(context, apkPath)
                } else {
                    LogUtils.e(TAG, "apkPath is null");
                }
            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
                LogUtils.e(TAG, "notification is clicked");
            }
        }
    }
}
