package com.cnbs.recyclerviewdemo.indexRV;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnbs.recyclerviewdemo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<IndexBean.ListBean> data;

    public IndexAdapter(Context context, List<IndexBean.ListBean> data) {
        this.context = context;
        this.data = data;
    }

    public void refresh(List<IndexBean.ListBean>  data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).itemView.setTag(position);
            IndexBean.ListBean bean = data.get(position);
            ((ItemViewHolder) holder).name.setText(bean.getName());
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ItemViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }

}
