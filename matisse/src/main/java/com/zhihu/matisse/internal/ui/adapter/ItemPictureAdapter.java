package com.zhihu.matisse.internal.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhihu.matisse.R;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.util.List;

/**
 * <p>作者：黄思程  2018/2/27 14:52
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：图片横向缩略图数据适配器
 */
public class ItemPictureAdapter extends RecyclerView.Adapter<ItemPictureAdapter.ItemViewHolder> implements View.OnClickListener {
    private List<Item> mData;
    private Activity mContext;
    private OnItemClickListener mOnItemClickListener = null;

    public ItemPictureAdapter(Activity context, List<Item> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(mItemView);

        /*** 添加点击事件*/
        itemViewHolder.itemView.setOnClickListener(this);

        return itemViewHolder;
    }

    @Override //设置数据
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = mData.get(position);

        if (item.isCheck) {
            holder.mItemPic.setBackground(mContext.getResources().getDrawable(R.drawable.item_stoken));
            int padding = dp2px(mContext, 2);
            holder.mItemPic.setPadding(padding, padding, padding, padding);
        } else {
            holder.mItemPic.setBackground(null);
            holder.mItemPic.setPadding(0, 0, 0, 0);
        }

        holder.mItemPic.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.itemView.setTag(position);
        Point size = PhotoMetadataUtils.getBitmapSize(item.getContentUri(), mContext);
        SelectionSpec.getInstance().imageEngine.loadImage(mContext, size.x, size.y, holder.mItemPic,
                item.getContentUri());
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    /*** 添加数据*/
    public void addAll(List<Item> items) {

        mData.addAll(items);
    }

    /*** 获取数据*/
    public List<Item> getData() {

        return mData;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    /*** item的点击事件*/
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /*** 点击事件*/
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.mOnItemClickListener = listener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mItemPic;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mItemPic = itemView.findViewById(R.id.ivPicture);
        }
    }

    /*** 单位转换*/
    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
