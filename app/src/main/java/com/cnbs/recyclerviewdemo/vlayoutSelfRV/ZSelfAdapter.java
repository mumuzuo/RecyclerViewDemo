package com.cnbs.recyclerviewdemo.vlayoutSelfRV;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * Created by Administrator on 2017/8/31.
 * 二、使用继承 自 VirtualLayoutAdapter 的 Adapter 的集合 来实现复杂布局的组合
 */

public class ZSelfAdapter extends VirtualLayoutAdapter<RecyclerView.ViewHolder> {

    public ZSelfAdapter(@NonNull VirtualLayoutManager layoutManager) {
        super(layoutManager);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
