package com.toucheart.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.toucheart.library.R;
import com.toucheart.library.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Toucheart  2017/9/2 16:45
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：面包屑控件
 */
public class BreadcrumbView extends HorizontalScrollView {

    private int mLastTextColor;
    private int mTextColor;
    private float mLastTextSize;
    private float mTextSize;
    private LinearLayout mContainer;
    private List<String> mDatas;
    private int mNumBread;
    private String mSplitText;

    private OnItemClickListener onItemClickListener;
    private boolean mFirstLoad;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public BreadcrumbView(Context context) {
        this(context, null);
    }

    /**
     * 构造器
     *
     * @param context 上下文
     * @param attrs   属性
     */
    public BreadcrumbView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造器
     *
     * @param context      上下文
     * @param attrs        属性
     * @param defStyleAttr defStyleAttr
     */
    public BreadcrumbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 初始化，读取属性，添加一个LinearLayout容器
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void init(Context context, AttributeSet attrs) {
        mFirstLoad = true;
        TypedArray typedArray = context.getResources().obtainAttributes(attrs, R.styleable.BreadcrumbView);
        try {
            mLastTextColor = typedArray.getColor(R.styleable.BreadcrumbView_BreadcrumbViewLastTextColor, Color.parseColor("#8899a9"));
            mTextColor = typedArray.getColor(R.styleable.BreadcrumbView_BreadcrumbViewTextColor, Color.parseColor("#20a6fd"));
            mLastTextSize = DisplayUtil.px2sp(typedArray.getDimension(R.styleable.BreadcrumbView_BreadcrumbViewLastTextSize, 30));
            mTextSize = DisplayUtil.px2sp(typedArray.getDimension(R.styleable.BreadcrumbView_BreadcrumbViewTextSize, 30));
            mSplitText = typedArray.getString(R.styleable.BreadcrumbView_BreadcrumbViewSplit);
            if (TextUtils.isEmpty(mSplitText)) {
                mSplitText = ">";
            }
        } finally {
            typedArray.recycle();
        }
        setHorizontalScrollBarEnabled(false);
        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setGravity(Gravity.CENTER_VERTICAL);
        addView(mContainer);
    }

    /**
     * 设置初始数据
     *
     * @param datas 数据源
     */
    public void setData(List<String> datas) {
        mDatas = datas;
        updateBreadCrumbs();
    }

    /**
     * 数据入栈
     *
     * @param data 显示的文本
     */
    public void addData(String data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(data);
        updateBreadCrumbs();
    }

    /**
     * 数据出栈
     */
    public void removeLast() {
        removeAfterIndex(mDatas.size() - 1);

    }

    /**
     * 移除index之后的所有显示
     *
     * @param index 数据下标
     */
    public void removeAfterIndex(int index) {
        if (mDatas == null) {
            return;
        }
        int size = mDatas.size();
        if (index < 0 || index >= size) {
            return;
        }
        for (int i = size - 1; i > index; i--) {
            mDatas.remove(i);
        }
        updateBreadCrumbs();
    }


    /**
     * 更新面包屑的数量
     */
    private void updateBreadCrumbs() {

        int dataSize = mDatas.size();

        if (dataSize > mNumBread) {
            //增加面包屑
            for (int i = mNumBread; i < dataSize; i++) {
                View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bread_crumb, null);
                TextView tv = (TextView) itemView.findViewById(R.id.tv_content);
                TextView split = (TextView) itemView.findViewById(R.id.tv_split);
                tv.setText(mDatas.get(i));
                split.setText(mSplitText);
                final int finalI = i;
                tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null && finalI != mNumBread - 1) {
                            onItemClickListener.onClick(v, finalI);
                        }
                        removeAfterIndex(finalI);

                    }
                });
                addBread(itemView);
            }
        } else if (dataSize < mNumBread) {
            //减少面包屑
            for (int i = mNumBread - 1; i > dataSize - 1; i--) {
                removeBread(i);
            }
        } else {
            return;
        }
        int start;

        if (mFirstLoad) {
            start = 0;
            mFirstLoad = false;
        } else {
            start = mNumBread - 2;
            if (start < 0) {
                start = 0;
            }
        }
        //调整可见性
        for (int i = start; i < mNumBread; i++) {
            View child = mContainer.getChildAt(i);
            // 高亮
            highLightIndex(child, i >= (mNumBread - 1));
        }
        // 滑动到最后一个
        post(new Runnable() {
            @Override
            public void run() {
                fullScroll(ScrollView.FOCUS_RIGHT);
            }
        });
    }

    /**
     * 添加一个面包节点
     *
     * @param view view
     */
    private void addBread(View view) {
        mContainer.addView(view);
        mNumBread = mContainer.getChildCount();
    }

    /**
     * 移除一个面包节点
     *
     * @param i 移除位置
     */
    private void removeBread(int i) {
        mContainer.removeViewAt(i);
        mNumBread = mContainer.getChildCount();

    }

    /**
     * @param view      view
     * @param highLight 是否高亮
     */
    public void highLightIndex(View view, boolean highLight) {
        TextView text = (TextView) view.findViewById(R.id.tv_content);
        TextView split = (TextView) view.findViewById(R.id.tv_split);

        if (highLight) {
            text.setTextColor(mLastTextColor);
            text.setTextSize(mLastTextSize);
            split.setVisibility(View.GONE);

        } else {
            text.setTextColor(mTextColor);
            text.setTextSize(mTextSize);
            split.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 点击节点监听
     */
    public interface OnItemClickListener {
        /**
         * 点击
         *
         * @param view  点击的view
         * @param index 数据下标
         */
        void onClick(View view, int index);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
