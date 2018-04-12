package com.toucheart.library.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * 作者： TouchHeart   2017/10/25 下午7:11
 * 邮箱： codinghuang@163.com
 * 作用：
 * 描述： 矩阵工具类
 */
public class MatrixUtil {

    private MatrixUtil() {

        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 创建Bitmap
     *
     * @param mBitmap 被绘制的Bitmap
     * @param mMatrix 效果矩阵
     * @return 绘制好的Bitmap
     */
    public Bitmap setImageMatrix(Bitmap mBitmap, ColorMatrix mMatrix) {
        Bitmap bmp = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mMatrix);

        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        return bmp;
    }

    /**
     * 获取灰度效果矩阵
     */
    public static ColorMatrix getGrayEffect() {
        return new ColorMatrix(new float[]{
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0.33F, 0.59F, 0.11F, 0, 0,
                0, 0, 0, 1, 0});
    }

    /**
     * 获取反转效果矩阵
     */
    public static ColorMatrix getFlipEffect() {
        return new ColorMatrix(new float[]{
                -1, 0, 0, 1, 1,
                0, -1, 0, 1, 1,
                0, 0, -1, 1, 1,
                0, 0, 0, -1, 0});
    }

    /**
     * 获取怀旧效果矩阵
     */
    public static ColorMatrix getRemindEffect() {
        return new ColorMatrix(new float[]{
                0.393F, 0.769F, 0.189F, 0, 0,
                0.349F, 0.686F, 0.168F, 0, 0,
                0.272F, 0.534F, 0.131F, 0, 0,
                0, 0, 0, 1, 0});
    }

    /**
     * 获取去色效果矩阵
     */
    public static ColorMatrix getDisColorationEffect() {
        return new ColorMatrix(new float[]{
                1.5F, 1.5F, 1.5F, 0, -1,
                1.5F, 1.5F, 1.5F, 0, -1,
                1.5F, 1.5F, 1.5F, 0, -1,
                0, 0, 0, 1, 0});
    }

    /**
     * 获取高饱和效果矩阵
     */
    public static ColorMatrix getHighSaturationEffect() {
        return new ColorMatrix(new float[]{
                1.438F, -0.122F, -0.016F, 0 - 0.03F,
                -0.062F, 1.378F, -0.016F, 0, 0.05F,
                -0.062F, -0.122F, 1.438F, 0, -0.02F,
                0, 0, 0, 1, 0});
    }
}
