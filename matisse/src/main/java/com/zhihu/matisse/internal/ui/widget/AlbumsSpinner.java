package com.zhihu.matisse.internal.ui.widget;

import android.content.Context;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.ListPopupWindow;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhihu.matisse.R;
import com.zhihu.matisse.internal.entity.Album;
import com.zhihu.matisse.internal.utils.Platform;
import com.zhihu.matisse.ui.MatisseActivity;

public class AlbumsSpinner {

    private static final int MAX_SHOWN_COUNT = 3;
    private CursorAdapter mAdapter;
    private TextView mSelected;
    private ListPopupWindow mListPopupWindow;
    private AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private Context mContext;

    public AlbumsSpinner(@NonNull Context context) {
        mContext = context;
        mListPopupWindow = new ListPopupWindow(context, null, R.attr.listPopupWindowStyle);
        mListPopupWindow.setModal(true);

        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumsSpinner.this.onItemSelected(parent.getContext(), position);
                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onItemSelected(parent, view, position, id);
                }
            }
        });
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        mOnItemSelectedListener = listener;
    }

    public void setSelection(Context context, int position) {
        mListPopupWindow.setSelection(position);
        onItemSelected(context, position);
    }

    private void onItemSelected(Context context, int position) {
        mListPopupWindow.dismiss();
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        Album album = Album.valueOf(cursor);
        String displayName = album.getDisplayName(context);
        if (mSelected.getVisibility() == View.VISIBLE) {
            mSelected.setText(displayName);
        } else {
            if (Platform.hasICS()) {
                mSelected.setAlpha(0.0f);
                mSelected.setVisibility(View.VISIBLE);
                mSelected.setText(displayName);
                mSelected.animate().alpha(1.0f).setDuration(context.getResources().getInteger(
                        android.R.integer.config_longAnimTime)).start();
            } else {
                mSelected.setVisibility(View.VISIBLE);
                mSelected.setText(displayName);
            }
        }
    }

    public void setAdapter(CursorAdapter adapter) {
        mListPopupWindow.setAdapter(adapter);
        mAdapter = adapter;
    }

    public void setSelectedTextView(TextView textView) {
        mSelected = textView;
        mSelected.setVisibility(View.GONE);
        mSelected.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int itemHeight = v.getResources().getDimensionPixelSize(R.dimen.album_item_height);
                mListPopupWindow.setHeight(
                        mAdapter.getCount() > MAX_SHOWN_COUNT ? itemHeight * MAX_SHOWN_COUNT
                                : itemHeight * mAdapter.getCount());
                mListPopupWindow.show();
                MatisseActivity mActivity = (MatisseActivity) mContext;
                WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
                params.alpha = 0.5f;
                Window window = mActivity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setAttributes(params);
            }
        });
        mSelected.setOnTouchListener(mListPopupWindow.createDragToOpenListener(mSelected));
    }

    public void setPopupAnchorView(View view) {
        mListPopupWindow.setAnchorView(view);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        mListPopupWindow.setOnDismissListener(listener);
    }
}