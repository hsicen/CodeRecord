package com.toucheart.library.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Create by 黄思程 on 2017/4/1   9:46
 * Function：
 * Desc：自定义旋转的圆
 */
public class RingRotateView extends View {

    private Context context;
    private Paint rightPaint, circlePaint;
    private int width, height; //外圆弧宽高
    private float outRadius, inRadius; //外圆弧和小圆半径
    private float outX, outY, inX, inY; //外圆弧和小圆中心点
    private float distance; //大圆中心到小圆中心的距离
    private int PADDING = 50;
    private int STROKE_WIDTH = 4; //圆弧宽度
    private CirclePoint currentPoint;

    public RingRotateView(Context context) {
        this(context, null);
    }

    public RingRotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        init();
    }

    /**
     * 初始化工作
     */
    private void init() {
        rightPaint = new Paint();
        rightPaint.setStrokeWidth(STROKE_WIDTH);
        rightPaint.setStyle(Paint.Style.STROKE);
        rightPaint.setColor(Color.BLUE);
        rightPaint.setAntiAlias(true);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setAntiAlias(true);

        currentPoint = new CirclePoint(0,inRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        outRadius = width > height ? height / 2 - PADDING : width / 2 - PADDING;
        inRadius = outRadius / 16;
        outX = width / 2;
        outY = height / 2;
        distance = outRadius - inRadius - STROKE_WIDTH / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(outX, outY, outRadius, rightPaint);
        inX = outX + distance * (float) Math.sin(currentPoint.angle / 360 * 2 * Math.PI);
        inY = outY - distance * (float) Math.cos(currentPoint.angle / 360 * 2 * Math.PI);
        canvas.drawCircle(inX, inY, inRadius, circlePaint);
    }

    /**
     * 提供给外部调用动画的方法
     */
    public void startAnimation() {

        ValueAnimator animator = ValueAnimator.ofObject((fraction, startValue, endValue) -> {
            CirclePoint start = (CirclePoint) startValue;
            CirclePoint end = (CirclePoint) endValue;
            float startAng = start.angle;
            float endAng = end.angle;
            float currentAng = startAng + (endAng - startAng) * fraction;
            return new CirclePoint(currentAng);
        }, new CirclePoint(0), new CirclePoint(360));

        animator.addUpdateListener(animation -> {
            currentPoint = (CirclePoint) animation.getAnimatedValue();
            invalidate();
        });

        //控制动画速率
        animator.setInterpolator((Interpolator) input -> 1 - (1 - input) * (1 - input) * (1 - input));

        animator.setRepeatCount(-1);  //设置无限重复
        animator.setDuration(10000);
        animator.start();
    }

    private class CirclePoint {
        float angle;
        float radius;
        int color;

        public CirclePoint(float angle, float radius, int color) {
            this.angle = angle;
            this.radius = radius;
            this.color = color;
        }

        public CirclePoint(float angle) {
            this.angle = angle;
        }

        public CirclePoint(float angle, int color) {
            this.angle = angle;
            this.color = color;
        }

        public CirclePoint(float angle, float radius) {
            this.angle = angle;
            this.radius = radius;
        }
    }
}
