package com.cnbs.recyclerviewdemo.groupRV.itemDecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现分组悬停的ItemDecoration
 * 适用于LinearLayoutManager
 * @author zuo
 * @date 2018/5/22 10:56
 */
public class GroupLineItemDecoration extends RecyclerView.ItemDecoration {
    private List<TypeBean> mDatas;
    private Paint mPaint;
    private Rect mBounds;
    private int mTitleHeight;
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private int mTitleFontSize;

    public GroupLineItemDecoration(Context context, List<TypeBean> data, int titleHeight, int titleFontSize) {
        super();
        mDatas = data;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, titleHeight, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, titleFontSize, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
    }

    public void refresh(ArrayList<TypeBean> data) {
        mDatas = data;
    }

    /**
     * 绘制在最底层
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();
            if (position >= 0) {
                String type = mDatas.get(position).getType();
                if (position == 0) {
                    drawTitleArea(c, left, right, child, params, position);
                } else {
                    String typeLast = mDatas.get(position - 1).getType();
                    if (null != type && !type.equals(typeLast)) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    }
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     * 最先调用，绘制在最下层
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        mPaint.setColor(COLOR_TITLE_BG);
        mPaint.setStyle(Paint.Style.FILL);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.getTextBounds(mDatas.get(position).getType(), 0, mDatas.get(position).getType().length(), mBounds);
        c.drawText(mDatas.get(position).getType(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    /**
     * 最后调用，绘制在最上面，实现悬浮效果
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        String tag = mDatas.get(pos).getType();
        //出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
        mPaint.setColor(COLOR_TITLE_BG);
        mPaint.setStyle(Paint.Style.FILL);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        c.drawText(tag, child.getPaddingLeft(),
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
                mPaint);
    }

    /**
     * 我们需要利用 parent 和 state 变量，来获取需要的辅助信息，例如 postion，
     * 最终调用 outRect.set(int left, int top, int right, int bottom)方法，
     * 设置四个方向上需要为 ItemView 设置 padding 的值。
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position >= 0) {
            if (position == 0) {
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                String type = mDatas.get(position).getType();
                String typeLast = mDatas.get(position - 1).getType();
                if (null != type && !type.equals(typeLast)) {
                    //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }
}
