package com.toucheart.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：Toucheart  2017/9/2 20:32
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：可在左边显示固定提示的EditText
 */
public class FixedEditText extends android.support.v7.widget.AppCompatEditText {

    private String fixedText;
    private OnClickListener mListener;
    private int leftPadding;

    public FixedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setFixedText(String text) {
        fixedText = text;
        leftPadding = getPaddingLeft();
        int left = (int) getPaint().measureText(fixedText) + leftPadding;
        setPadding(left, getPaddingTop(), getPaddingRight(), getPaddingBottom());

        invalidate();
    }

    public void setDrawableClk(OnClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(fixedText)) {
            Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
            int baseline = (getMeasuredHeight() + 0 - fontMetrics.bottom - fontMetrics.top) / 2;
            canvas.drawText(fixedText, leftPadding, baseline, getPaint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mListener != null && getCompoundDrawables()[2] != null) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int i = getMeasuredWidth() - getCompoundDrawables()[2].getIntrinsicWidth();
                    if (event.getX() > i) {
                        mListener.onClick(this);
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    break;
                default:
                    break;
            }

        }

        return super.onTouchEvent(event);
    }

}