package com.cnbs.recyclerviewdemo.vlayoutRV;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.cnbs.recyclerviewdemo.R;

/**
 * Created by Administrator on 2017/8/29.
 * 和普通的RecyclerView使用的adapter一样，只是把继承RecyclerView.Adapter换做了继承DelegateAdapter.Adapter
 */

public class EBackAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private ItemClickListener mListener;
    private int mCount = 0;

    public EBackAdapter(Context context, LayoutHelper layoutHelper, int count,ItemClickListener listener) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mListener = listener;
    }

    public interface ItemClickListener{
        void OnClick(View v);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(150,150));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnClick(v);
            }
        });
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EBackAdapter.ItemViewHolder) {
            ((EBackAdapter.ItemViewHolder) holder).itemView.setTag(position);
            ((EBackAdapter.ItemViewHolder) holder).img.setImageResource(R.mipmap.back_top);
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ItemViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img_back);
        }
    }
}
