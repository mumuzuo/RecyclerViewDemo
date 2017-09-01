package com.cnbs.recyclerviewdemo.refreshloadRV;

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

public class RVRefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> data;

    public RVRefreshAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void refresh(List<String>  data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).itemView.setTag(position);
            String num = data.get(position);
            ((ItemViewHolder) holder).value.setText(num);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView value;
        public ItemViewHolder(View view) {
            super(view);
            value = (TextView) view.findViewById(R.id.value);
        }
    }

}
