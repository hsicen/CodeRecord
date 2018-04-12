package com.toucheart.library.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by smartown on 2018/3/6 14:14.
 * <br>
 * Desc:
 * <br>
 * 对图片进行圆角处理
 */
public class RoundRectTransform extends BitmapTransformation {

    private int radius;
    private int resultWidth;
    private int resultHeight;

    public RoundRectTransform(Context context, int radius, int resultWidth, int resultHeight) {
        super(context);
        this.radius = radius;
        this.resultHeight = resultHeight;
        this.resultWidth = resultWidth;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        float sourceWidth = toTransform.getWidth();
        float sourceHeight = toTransform.getHeight();

        float scaleWidth = resultWidth / sourceWidth;
        float scaleHeight = resultHeight / sourceHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap scaleBitmap = Bitmap.createBitmap(toTransform, 0, 0, (int) sourceWidth, (int) sourceHeight, matrix, true);
        Bitmap result = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(scaleBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        canvas.drawRoundRect(new RectF(0, 0, resultWidth, resultHeight), radius, radius, paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        //doNothing
    }
}
