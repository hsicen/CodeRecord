package com.toucheart.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：Toucheart  2017/9/2 20:33
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：可适应ScrollView的ListView
 */
public class FixedListView extends ListView {

    public FixedListView(Context context) {
        super(context);
    }

    public FixedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /*** 重写该方法，达到使ListView适应ScrollView的效果*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}