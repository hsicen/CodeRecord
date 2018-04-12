package com.toucheart.library.other;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：Toucheart  2017/9/2 20:40
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：RecyclerView item的间距设置
 */
public class ItemDivider extends RecyclerView.ItemDecoration {
    private int leftSpace;
    private int topSpace;
    private int rightSpace;
    private int bottomSpace;

    /**
     * item之间的间距
     *
     * @param topSpace    顶部间距
     * @param leftSpace   左间距
     * @param rightSpace  右间距
     * @param bottomSpace 底间距
     */
    public ItemDivider(int topSpace, int leftSpace, int rightSpace, int bottomSpace) {
        this.leftSpace = leftSpace;
        this.topSpace = topSpace;
        this.rightSpace = rightSpace;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = topSpace;
        outRect.left = leftSpace;
        outRect.right = rightSpace;
        outRect.bottom = bottomSpace;
    }
}
