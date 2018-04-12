package com.toucheart.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.toucheart.library.R;
import com.toucheart.library.util.DisplayUtil;

/**
 * <p>作者：黄思程  2017/11/8 14:27
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：百分比View
 */
public class PercentView extends View {
    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    private int mStartAngle; //开始的角度
    private int mFanColor;//1~99%的颜色
    private int mFirstCircleColor; //0%的颜色
    private int mFanFullColor; //100%的颜色

    private int mCircleColor;
    private int mCenterCircleRadius;
    private int mInnerCircleRadius;
    private int mOuterCircleRadius;

    private int mTopTextSize;
    private int mBottomTextSize;
    private int mTopTextColor;
    private int mBottomTextColor;
    private int mTextColor;
    private int mTextSize;
    private String mText;
    private String mTopText;
    private String mBottomText;

    private RectF mFanRect;
    private Rect mTopRect;
    private Rect mTotalRect;
    private Rect mRealRect;
    private PorterDuffXfermode mXferMode;

    //应到人数
    private int mTotalNum;
    //实到人数
    private int mRealNum;

    public PercentView(Context context) {

        this(context, null);
    }

    public PercentView(Context context, @Nullable AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public PercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTotalNum = 0;
        mRealNum = 0;
        init(context, attrs);
    }

    /**
     * 初始化操作
     *
     * @param context context
     * @param attrs   attrs
     */
    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentView);

        mFanColor = typedArray.getColor(R.styleable.PercentView_fanColor, getResources().getColor(R.color.colorFailed));
        mFanFullColor = typedArray.getColor(R.styleable.PercentView_fanFullColor, getResources().getColor(R.color.colorAccent));
        mStartAngle = typedArray.getInteger(R.styleable.PercentView_fanStartAngle, 0);

        mFirstCircleColor = typedArray.getColor(R.styleable.PercentView_firstCircleColor, getResources().getColor(R.color.colorLightGray));
        mCircleColor = typedArray.getColor(R.styleable.PercentView_centerCircleColor, getResources().getColor(R.color.colorWhite));
        mCenterCircleRadius = typedArray.getDimensionPixelSize(R.styleable.PercentView_centerCircleRadius, DisplayUtil.dp2px(42));
        mInnerCircleRadius = typedArray.getDimensionPixelSize(R.styleable.PercentView_innerCircleRadius, DisplayUtil.dp2px(42));
        mOuterCircleRadius = typedArray.getDimensionPixelSize(R.styleable.PercentView_outerCircleRadius, DisplayUtil.dp2px(53));

        mTextColor = typedArray.getColor(R.styleable.PercentView_centerTextColor, getResources().getColor(R.color.colorBlack));
        mTopTextColor = typedArray.getColor(R.styleable.PercentView_topTextColor, getResources().getColor(R.color.colorBlack));
        mBottomTextColor = typedArray.getColor(R.styleable.PercentView_bottomTextColor, getResources().getColor(R.color.colorBlack));

        mTextSize = typedArray.getDimensionPixelSize(R.styleable.PercentView_centerTextSize, DisplayUtil.sp2px(16));
        mTopTextSize = typedArray.getDimensionPixelSize(R.styleable.PercentView_topTextSize, DisplayUtil.sp2px(14));
        mBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.PercentView_bottomTextSize, DisplayUtil.sp2px(14));

        mText = typedArray.getString(R.styleable.PercentView_centerText);
        mTopText = typedArray.getString(R.styleable.PercentView_topText);
        mBottomText = typedArray.getString(R.styleable.PercentView_bottomText);

        typedArray.recycle();
    }

    /**
     * @param mTotalNum 设置总数
     */
    public void setTotalNum(int mTotalNum) {
        this.mTotalNum = mTotalNum;
        if (this.mTotalNum < 0) {
            this.mTotalNum = 0;
        }
    }

    /**
     * @param mRealNum 实际数
     */
    public void setRealNum(int mRealNum) {
        this.mRealNum = mRealNum;
        if (this.mRealNum < 0) {
            this.mRealNum = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * @param heightMeasureSpec 测量高度
     * @return 高度
     */
    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight = heightSize;
                break;
            default:
                break;
        }
        return mHeight;
    }

    /**
     * @param widthMeasureSpec 测量宽度
     * @return 宽度
     */
    private int measureWidth(int widthMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = widthSize;
                break;
            default:
                break;
        }
        return mWidth;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        int center = mWidth / 2;
        int radius = mInnerCircleRadius / 2 + 1 + mOuterCircleRadius / 2;
        mFanRect = new RectF(center - radius, center - radius, center + radius, center + radius);
        mXferMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);

        mTopRect = new Rect();
        mTotalRect = new Rect();
        mRealRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //计算扫过的角度
        int mSweepAngle;
        if (mTotalNum == 0) {
            if (mRealNum > 0) {
                mSweepAngle = 360;
            } else {
                mSweepAngle = 0;
            }
        } else {
            mSweepAngle = (int) ((mRealNum / (float) mTotalNum) * 360);
        }

        //绘制内圆
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mCenterCircleRadius, mPaint);

        //绘制第一层圆环
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mOuterCircleRadius - mInnerCircleRadius);
        mPaint.setColor(mFirstCircleColor);
        canvas.drawArc(mFanRect, 0, 360, false, mPaint);

        //绘制第二层圆环
        if (mSweepAngle >= 360) {
            mPaint.setColor(mFanFullColor);
        } else {
            mPaint.setColor(mFanColor);
        }
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setXfermode(mXferMode);
        canvas.drawArc(mFanRect, mStartAngle, mSweepAngle, false, mPaint);

        //绘制中心文字
        if (mText != null && !TextUtils.isEmpty(mText)) {
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(mTextSize);

            mPaint.getTextBounds(mText, 0, mText.length(), mTopRect);
            int width = mTopRect.width();
            int height = mTopRect.height();

            canvas.drawText(mText, mWidth / 2 - width / 2, mHeight / 2 + height / 2, mPaint);
        }

        //绘制上层文字
        if (mTopText != null && !TextUtils.isEmpty(mTopText)) {
            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setColor(mTopTextColor);
            mPaint.setTextSize(mTopTextSize);
            mPaint.getTextBounds(mTopText, 0, mTopText.length(), mTopRect);
            int width = mTopRect.width();
            canvas.drawText(mTopText, mWidth / 2 - width / 2, mHeight / 2 - DisplayUtil.dp2px(4), mPaint);

            //绘制应到人数
            mPaint.setColor(mBottomTextColor);
            mPaint.setTextSize(mBottomTextSize);
            String str = "/";
            mPaint.getTextBounds(str, 0, str.length(), mTotalRect);
            int totalWid = mTotalRect.width();
            int totalHei = mTotalRect.height();
            canvas.drawText(str + mTotalNum, mWidth / 2 - totalWid, mHeight / 2 + totalHei + DisplayUtil.dp2px(4), mPaint);

            //绘制实到人数
            if (mSweepAngle >= 360) {
                mPaint.setColor(mFanFullColor);
            } else {
                mPaint.setColor(mFanColor);
            }
            String realStr = String.valueOf(mRealNum);
            mPaint.getTextBounds(realStr, 0, realStr.length(), mRealRect);
            int realWidth = mRealRect.width();
            canvas.drawText(realStr, mWidth / 2 - realWidth - totalWid - 6, mHeight / 2 + totalHei + DisplayUtil.dp2px(4), mPaint);
        }
    }
}
