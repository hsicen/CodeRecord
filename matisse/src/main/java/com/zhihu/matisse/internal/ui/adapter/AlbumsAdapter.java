package com.zhihu.matisse.internal.ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhihu.matisse.R;
import com.zhihu.matisse.internal.entity.Album;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.ui.MatisseActivity;

import java.io.File;

public class AlbumsAdapter extends CursorAdapter {

    private final Drawable mPlaceholder;
    private Context mContext;

    public AlbumsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.mContext = context;

        TypedArray ta = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.album_thumbnail_placeholder});
        mPlaceholder = ta.getDrawable(0);
        ta.recycle();
    }

    public AlbumsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        TypedArray ta = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.album_thumbnail_placeholder});
        mPlaceholder = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.album_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int checkPosition = ((MatisseActivity) mContext).getCheckedPosition();

        Log.d(">>>>>", "位置：" + cursor.getPosition() + "当前位置：" + checkPosition);
        Album album = Album.valueOf(cursor);
        ((TextView) view.findViewById(R.id.album_name)).setText(album.getDisplayName(context));
        ((TextView) view.findViewById(R.id.album_media_count)).setText(String.valueOf(album.getCount()));
        AppCompatRadioButton button = ((AppCompatRadioButton) view.findViewById(R.id.album_media_status));
        if (cursor.getPosition() == checkPosition) { //初始化Cursor的位置和选中位置相等则设置为选中
            button.setChecked(true);
        } else {
            button.setChecked(false);
        }

        SelectionSpec.getInstance().imageEngine.loadThumbnail(context, context.getResources().getDimensionPixelSize(R
                        .dimen.media_grid_size), mPlaceholder,
                (ImageView) view.findViewById(R.id.album_cover), Uri.fromFile(new File(album.getCoverPath())));
    }
}
