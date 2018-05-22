package com.cnbs.recyclerviewdemo.groupRV.vlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.cnbs.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/8/29.
 * 和普通的RecyclerView使用的adapter一样，只是把继承RecyclerView.Adapter换做了继承DelegateAdapter.Adapter
 */

public class TypeAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private String mType;
    private int mCount = 0;

    public TypeAdapter(Context context, LayoutHelper layoutHelper, int count, String type) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mType = type;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_rv, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(mContext, mType, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TypeAdapter.ItemViewHolder) {
            ((TypeAdapter.ItemViewHolder) holder).itemView.setTag(position);
            ((TypeAdapter.ItemViewHolder) holder).text.setText(mType);
            ((TypeAdapter.ItemViewHolder) holder).text.setGravity(Gravity.CENTER_VERTICAL);
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        public ItemViewHolder(View view) {
            super(view);
            text =  view.findViewById(R.id.tv);
        }
    }

}
