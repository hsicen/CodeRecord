package com.toucheart.library.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

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
}