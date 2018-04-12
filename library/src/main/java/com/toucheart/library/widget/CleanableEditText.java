package com.toucheart.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.toucheart.library.R;

/**
 * 作者：Toucheart  2017/9/2 19:23
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：可以清除的TextInputEditText
 */
public class CleanableEditText extends TextInputEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearTextIcon;
    private View.OnFocusChangeListener mOnFocusChangeListener;
    private View.OnTouchListener mOnTouchListener;
    private Context mContext;
    private float mPaddingLeft;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public CleanableEditText(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性
     */
    public CleanableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * @param context      上下文
     * @param attrs        属性
     * @param defStyleAttr 默认值
     */
    public CleanableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray at = context.getResources().obtainAttributes(attrs, R.styleable.ClearableEditText);
        int resId = R.drawable.icon_clear;
        try {
            resId = at.getResourceId(R.styleable.ClearableEditText_clearIcon, R.drawable.icon_clear);
            mPaddingLeft = at.getDimension(R.styleable.ClearableEditText_clearIconPaddingLeft, 0);
        } finally {
            at.recycle();
        }
        mClearTextIcon = getDrawable(resId);
        //设置宽高
        mClearTextIcon.setBounds(0, 0, mClearTextIcon.getIntrinsicHeight(), mClearTextIcon.getIntrinsicWidth());

        setClearIconVisible(false);

        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    /**
     * 设施清除按钮可见
     *
     * @param visible 可见
     */
    private void setClearIconVisible(final boolean visible) {
        mClearTextIcon.setVisible(visible, false);
        final Drawable[] compoundDrawables = getCompoundDrawables();
        if (mPaddingLeft > 0) {

        } else {
            setCompoundDrawables(
                    compoundDrawables[0],
                    compoundDrawables[1],
                    visible ? mClearTextIcon : null,
                    compoundDrawables[3]);
        }

    }


    /**
     * 设置清除图标
     *
     * @param resId
     */
    public void setClearIcon(int resId) {
        mClearTextIcon = getDrawable(resId);
        setClearIconVisible(true);
    }

    /**
     * 获取图标Drawable
     *
     * @param resId 资源id
     * @return 图标Drawable
     */
    private Drawable getDrawable(int resId) {
        //封装drawable对象
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
//        //简单着色
//        DrawableCompat.setTint(wrappedDrawable, getCurrentHintTextColor());
        return wrappedDrawable;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int x = (int) event.getX();
        if (mClearTextIcon.isVisible() && x > getWidth() - getPaddingRight() - mClearTextIcon.getIntrinsicWidth()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                setError(null);
                setText("");
            }
            return true;
        }
        return mOnTouchListener != null && mOnTouchListener.onTouch(v, event);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public final void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void setOnFocusChangeListener(final View.OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
    }

    @Override
    public void setOnTouchListener(final View.OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    @Override
    public void setEnabled(boolean enabled) {
        setClearIconVisible(enabled);
        super.setEnabled(enabled);
    }
}

