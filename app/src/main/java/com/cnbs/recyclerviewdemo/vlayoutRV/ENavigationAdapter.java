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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/29.
 * 和普通的RecyclerView使用的adapter一样，只是把继承RecyclerView.Adapter换做了继承DelegateAdapter.Adapter
 */

public class ENavigationAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private ArrayList<HashMap<String, Object>> mData;
    private int mCount = 0;

    public ENavigationAdapter(Context context, LayoutHelper layoutHelper, int count, ArrayList<HashMap<String, Object>> data) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mData = data;
    }

    public void refresh(int count,ArrayList<HashMap<String, Object>> data) {
        this.mCount = count;
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vlayout, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                Toast toast = Toast.makeText(mContext, "点击"+(String)mData.get(tag).get("ItemTitle"), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ENavigationAdapter.ItemViewHolder) {
            ((ENavigationAdapter.ItemViewHolder) holder).itemView.setTag(position);
            ((ENavigationAdapter.ItemViewHolder) holder).position.setText((String)mData.get(position).get("ItemTitle"));
            ((ENavigationAdapter.ItemViewHolder) holder).img.setImageResource((Integer) mData.get(position).get("ItemImage"));
           /* GlideConfig config = new GlideConfig();
            LruBitmapPool pool = new LruBitmapPool(config.getMemorySize());
            Glide.with(mContext)
                    .load(mData.get(position).get("ItemImage"))
                    .bitmapTransform(new CropCircleTransformation(pool))
                    .into(((ItemViewHolder) holder).img);*/
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView position;
        ImageView img;
        public ItemViewHolder(View view) {
            super(view);
            position = (TextView) view.findViewById(R.id.position_vlayout);
            img = (ImageView) view.findViewById(R.id.img_vlayout);
        }
    }

}
