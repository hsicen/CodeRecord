package com.toucheart.library.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.toucheart.library.R;


/**
 * <p>作者：黄思程  2018/4/20 9:40
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述： 自定义CheckBox
 */
public class MaterialCheckBox extends View {

    private static final float SFLOATA = 378f;
    private static final float SFLOATB = 0.5f;
    private static final float SFLOATC = 3f;
    private Paint paintBlue;
    private Paint paintWithe;
    private Paint paintCenter;

    private int borderColor = Color.GRAY;     //边框颜色
    private int backgroundColor = Color.BLUE; //填充颜色
    private int doneShapeColor = Color.WHITE; //对号颜色

    private int baseWidth;                    //checkbox 边框宽度
    private int borderWidth;
    private int width;                //控件宽高
    private int height;

    private float[] points = new float[8];    //对号的4个点的坐标

    private int mDuration = 200;               //动画时长
    private boolean checked;                  //选择状态
    private float correctProgress;            //划对号的进度

    private boolean drawRect;
    private boolean isAnim;
    private OnCheckedChangeListener listener;

    private float padding;                    //内切圆的边据边框的距离

    /**
     * @param context context
     */
    public MaterialCheckBox(Context context) {
        this(context, null);
    }

    /**
     * @param context context
     * @param attrs   attrs
     */
    public MaterialCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context      context
     * @param attrs        attrs
     * @param defStyleAttr defStyleAttr
     */
    public MaterialCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * init
     *
     * @param context context
     */
    private void init(Context context) {
        backgroundColor = ContextCompat.getColor(context, R.color.colorAccent);
        borderColor = ContextCompat.getColor(context, R.color.colorAccent);
        borderWidth = baseWidth = dp2px(2);

        paintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBlue.setColor(borderColor);
        paintBlue.setStrokeWidth(borderWidth);

        paintWithe = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintWithe.setColor(doneShapeColor);
        paintWithe.setStrokeWidth(dp2px(2));

        paintCenter = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCenter.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        paintCenter.setStrokeWidth(borderWidth);

        setOnClickListener(v -> setChecked(!isChecked()));
        drawRect = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = width = Math.max(w, h);

        points[0] = 101 / SFLOATA * width;
        points[1] = SFLOATB * width;

        points[2] = 163 / SFLOATA * width;
        points[3] = 251 / SFLOATA * width;

        points[4] = 149 / SFLOATA * width;
        points[5] = 250 / SFLOATA * width;

        points[6] = 278 / SFLOATA * width;
        points[7] = 122 / SFLOATA * width;

        padding = 57 / SFLOATA * width;
    }

    /**
     * draw checkbox
     *
     * @param canvas canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        RectF rect = new RectF(padding, padding, width - padding, height - padding);
        canvas.drawRoundRect(rect, baseWidth, baseWidth, paintBlue);
        if (drawRect) {
            canvas.drawRect(padding + borderWidth,
                    padding + borderWidth,
                    width - padding - borderWidth,
                    height - padding - borderWidth, paintCenter);
        } else {
            //画对号
            if (correctProgress > 0) {
                if (correctProgress < 1 / SFLOATC) {
                    float x = points[0] + (points[2] - points[0]) * correctProgress;
                    float y = points[1] + (points[3] - points[1]) * correctProgress;
                    canvas.drawLine(points[0], points[1], x, y, paintWithe);
                } else {
                    float x = points[4] + (points[6] - points[4]) * correctProgress;
                    float y = points[5] + (points[7] - points[5]) * correctProgress;
                    canvas.drawLine(points[0], points[1], points[2], points[3], paintWithe);
                    canvas.drawLine(points[4], points[5], x, y, paintWithe);
                }
            }
        }
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @param doneShapeColor doneShapeColor
     */
    public void setDoneShapeColor(int doneShapeColor) {
        this.doneShapeColor = doneShapeColor;
        paintWithe.setColor(doneShapeColor);
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * @return check
     */
    public boolean isChecked() {
        return checked;
    }

    /*** set
     * @param checked set
     * */
    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            showRect();
        } else {
            hideCorrect();
        }
    }

    /*** hide*/
    private void hideRect() {
        if (isAnim) {
            return;
        }
        isAnim = true;
        drawRect = true;
        ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(mDuration);
        va.addUpdateListener(animation -> {
            float p = (float) animation.getAnimatedValue();
            float c = 1f - p;
            borderWidth = (int) (baseWidth + c * (width - baseWidth));
            paintBlue.setColor(evaluate(c, borderColor, backgroundColor));
            invalidate();
            if (p >= 1) {
                isAnim = false;
                if (listener != null) {
                    checked = false;
                    listener.onCheckedChanged(MaterialCheckBox.this, checked);
                }
            }
        });
        va.start();
    }

    /*** show*/
    private void showRect() {
        if (isAnim) {
            return;
        }
        isAnim = true;
        drawRect = true;
        ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(mDuration);
        va.addUpdateListener(animation -> {
            float p = (float) animation.getAnimatedValue();
            borderWidth = (int) (10 + p * (width - 10));
            paintBlue.setColor(evaluate(p, borderColor, backgroundColor));
            invalidate();
            if (p >= 1) {
                isAnim = false;
                drawRect = false;
                showCorrect();
            }
        });
        va.start();
    }

    /*** show*/
    private void showCorrect() {
        if (isAnim) {
            return;
        }
        isAnim = true;
        correctProgress = 0;
        drawRect = false;
        ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(mDuration);
        va.addUpdateListener(animation -> {
            correctProgress = (float) animation.getAnimatedValue();
            invalidate();
            if (correctProgress >= 1) {
                isAnim = false;
                if (listener != null) {
                    checked = true;
                    listener.onCheckedChanged(MaterialCheckBox.this, checked);
                }
            }
        });
        va.start();
    }

    /*** hide*/
    private void hideCorrect() {
        if (isAnim) {
            return;
        }
        isAnim = true;
        correctProgress = 1;
        drawRect = false;
        ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(mDuration);
        va.addUpdateListener(animation -> {
            float p = (float) animation.getAnimatedValue();
            correctProgress = 1f - p;
            invalidate();
            if (p >= 1) {
                isAnim = false;
                hideRect();
            }
        });
        va.start();
    }

    /***
     * @param listener listener
     * */
    public void setOnCheckedChangedListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    /**
     * OnCheckedChangeListener
     */
    interface OnCheckedChangeListener {

        /**
         * @param view      view
         * @param isChecked isChecked
         */
        void onCheckedChanged(View view, boolean isChecked);
    }

    /**
     * @param fraction   fraction
     * @param startValue startValue
     * @param endValue   endValue
     * @return return
     */
    private int evaluate(float fraction, int startValue, int endValue) {
        int startInt = startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return ((startA + (int) (fraction * (endA - startA))) << 24)
                | ((startR + (int) (fraction * (endR - startR))) << 16)
                | ((startG + (int) (fraction * (endG - startG))) << 8)
                | startB + (int) (fraction * (endB - startB));
    }

    /**
     * @param value value
     * @return value
     */
    public int dp2px(float value) {
        final float scale = getContext().getResources().getDisplayMetrics().densityDpi;
        return (int) (value * (scale / 160) + SFLOATB);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
    }

    /**
     * @param mDuration time
     */
    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }
}
