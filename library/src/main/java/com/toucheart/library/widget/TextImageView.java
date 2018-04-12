package com.toucheart.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 作者：Toucheart  2017/9/2 19:47
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：能够显示文字的ImageView
 */
public class TextImageView extends android.support.v7.widget.AppCompatImageView {
    private TextPaint mPaint;
    private String text;

    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(text))
            canvas.drawText(text, getWidth() / 2, (int) ((getMeasuredHeight() - (mPaint.descent() - mPaint.ascent())) / 2 - mPaint.ascent()), mPaint);
    }

    /**
     * set the text of the view
     *
     * @param text       text
     * @param texSize    text size
     * @param textColor  textColor
     * @param backGround backGroundColor for the view
     * @param isRoundBg  whether show the background as a round shape
     */
    public void setText(String text, int texSize, int textColor, int backGround, boolean isRoundBg) {
        this.text = text;
        mPaint.setColor(textColor);
        mPaint.setTextSize(texSize);
        setBackgroundResource(0);
        if (isRoundBg) {
            setBackgroundDrawable((ContextCompat.getDrawable(getContext(), backGround)));
        } else {
            setBackgroundColor(backGround);
        }
        invalidate();
    }

}
