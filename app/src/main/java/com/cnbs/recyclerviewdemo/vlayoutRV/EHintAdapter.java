package com.cnbs.recyclerviewdemo.vlayoutRV;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.cnbs.recyclerviewdemo.R;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/29.
 * 和普通的RecyclerView使用的adapter一样，只是把继承RecyclerView.Adapter换做了继承DelegateAdapter.Adapter
 */

public class EHintAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private HashMap<String, Object> mData;
    private int mCount = 0;

    public EHintAdapter(Context context, LayoutHelper layoutHelper, int count, HashMap<String, Object> data) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mData = data;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hint, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EHintAdapter.ItemViewHolder) {
            ((EHintAdapter.ItemViewHolder) holder).itemView.setTag(position);
            ((EHintAdapter.ItemViewHolder) holder).text.setText((String)mData.get("ItemTitle"));
            ((EHintAdapter.ItemViewHolder) holder).img.setImageResource((Integer) mData.get("ItemImage"));
            ((EHintAdapter.ItemViewHolder) holder).more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(mContext, "去往更多", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        TextView more;
        ImageView img;
        public ItemViewHolder(View view) {
            super(view);
            text = (TextView) view.findViewById(R.id.text_hint);
            img = (ImageView) view.findViewById(R.id.img_hint);
            more = (TextView) view.findViewById(R.id.more_hint);
        }
    }

}
