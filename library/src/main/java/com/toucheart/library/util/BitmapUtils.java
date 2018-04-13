package com.toucheart.library.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;

import java.util.HashMap;

/**
 * 作者： TouchHeart   2017/9/11 下午10:11
 * 邮箱： codinghuang@163.com
 * 作用：
 * 描述： Bitmap工具类
 */
public class BitmapUtils {

    private BitmapUtils() {

        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * @param resId 资源id
     * @return 将resource文件转换成bitmap
     */
    public static Bitmap resourceToBitmap(int resId) {
        Resources resources = ContextUtil.withContext().getResources();

        return BitmapFactory.decodeResource(resources, resId);
    }

    /**
     * @param bitmap bitmap
     * @return 将bitmap转换成Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {

        return new BitmapDrawable(bitmap);
    }

    /**
     * @param drawable drawable文件
     * @return 将drawable转换成bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 将两张图片绘制为一张图片
     *
     * @param firstBitmap
     * @param secondBitmap
     * @return
     */
    public static Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(),
                firstBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(firstBitmap, new Matrix(), null);
        canvas.drawBitmap(secondBitmap, (firstBitmap.getWidth() - secondBitmap.getWidth()) / 2,
                (firstBitmap.getHeight() - secondBitmap.getHeight()) / 2, null);
        return bitmap;
    }

    /**
     * 获取视频的第一帧
     *
     * @param url
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmap(final String url, final int width, final int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime(1000);
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }

        return bitmap;
    }
}