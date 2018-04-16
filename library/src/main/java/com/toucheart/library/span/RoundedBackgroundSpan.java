package com.toucheart.library.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;

/**
 * <p>作者：黄思程  2018/4/4 15:56
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：实现圆角标签
 */
public class RoundedBackgroundSpan extends ReplacementSpan {

    private int mSize;
    private int mRadius;
    private int backgroundColor;
    private int textColor;

    /**
     * @param backgroundColor textBackgroundColor
     * @param textColor       textColor
     * @param radius          backgroundRadius
     */
    public RoundedBackgroundSpan(int backgroundColor, int textColor, int radius) {
        super();
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.mRadius = radius;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        paint.setColor(backgroundColor);//设置背景颜色
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
        //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x + mRadius, y, paint);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end) + 2 * mRadius);

        return mSize;
    }
}
