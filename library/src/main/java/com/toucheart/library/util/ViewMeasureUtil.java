package com.toucheart.library.util;

import android.view.View;

/**
 * 作者： TouchHeart   2017/10/18 下午9:41
 * 邮箱： codinghuang@163.com
 * 作用：
 * 描述： View测量工具类
 */
public class ViewMeasureUtil {
	private int mWidth;
	private int mHeight;

	public int measureHeight(int heightMeasureSpec) {
		int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
		switch (heightMode) {
			case View.MeasureSpec.UNSPECIFIED:
			case View.MeasureSpec.AT_MOST:
				break;
			case View.MeasureSpec.EXACTLY:
				mHeight = heightSize;
				break;
			default:
				break;
		}
		return mHeight;
	}

	public int measureWidth(int widthMeasureSpec) {
		int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);

		switch (widthMode) {
			case View.MeasureSpec.UNSPECIFIED:
			case View.MeasureSpec.AT_MOST:
				break;
			case View.MeasureSpec.EXACTLY:
				mWidth = widthSize;
				break;
			default:
				break;
		}
		return mWidth;
	}
}
