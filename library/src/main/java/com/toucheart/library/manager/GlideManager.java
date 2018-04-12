package com.toucheart.library.manager;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.toucheart.library.R;
import com.toucheart.library.transform.GlideCircleTransform;
import com.toucheart.library.transform.RoundRectTransform;

/**
 * 作者：Toucheart  2017/9/2 20:22
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：Glide二次封装
 */
public class GlideManager {
    private static int sCommonPlaceholder;
    private static int sCirclePlaceholder;
    private static int sRoundPlaceholder;

    static {
        sCommonPlaceholder = R.drawable.icon_img;
        sCirclePlaceholder = R.drawable.icon_img;
        sRoundPlaceholder = R.drawable.icon_img;
    }

    private GlideManager() {
    }

    /***
     * @param circlePlaceholder 设置圆形图片的占位图*/
    public static void setCirclePlaceholder(int circlePlaceholder) {
        sCirclePlaceholder = circlePlaceholder;
    }

    /***
     * @param commonPlaceholder 设置正常图片的占位符*/
    public static void setCommonPlaceholder(int commonPlaceholder) {
        sCommonPlaceholder = commonPlaceholder;
    }

    /*** 设置圆角图片的占位符
     ** @param roundPlaceholder roundPlaceholder*/
    public static void setsRoundPlaceholder(int roundPlaceholder) {
        sRoundPlaceholder = roundPlaceholder;
    }

    /**
     * @param context context
     * @param url     图片地址/路径
     * @param iv      载体
     */
    public static void loadImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(sCommonPlaceholder);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * @param context context
     * @param url     图片地址/路径
     * @param iv      载体
     */
    public static void loadRoundImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(sCirclePlaceholder)
                .transform(new GlideCircleTransform());

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     * @param context context
     * @param url     图片地址/路径
     * @param radius  裁剪角度
     * @param iv      载体
     */
    public static void loadRadiusImage(Context context, String url, int radius, ImageView iv) {
        ViewGroup.LayoutParams params = iv.getLayoutParams();
        RequestOptions options = new RequestOptions()
                .placeholder(sRoundPlaceholder)
                .transform(new RoundRectTransform(context, radius, params.width, params.height));

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

}