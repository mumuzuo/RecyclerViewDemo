package com.cnbs.recyclerviewdemo.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = 20;
        outRect.right = 20;
        outRect.bottom = 20;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 12;
        }

    }
    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }

}
