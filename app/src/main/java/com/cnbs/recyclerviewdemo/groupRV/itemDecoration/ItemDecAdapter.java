package com.cnbs.recyclerviewdemo.groupRV.itemDecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cnbs.recyclerviewdemo.R;

import java.util.List;

/**
 * @author zuo
 * @date 2018/5/22 11:46
 */
public class ItemDecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<TypeBean> data;

    public ItemDecAdapter(Context context, List<TypeBean> data) {
        this.context = context;
        this.data = data;
    }

    public void refresh(List<TypeBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_rv, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                TypeBean bean = data.get(tag);
                Toast.makeText(context, String.format("%s(%s)", bean.getName(), bean.getCode()), Toast.LENGTH_SHORT).show();
            }
        });
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            TypeBean bean = data.get(position);
            ((ItemViewHolder) holder).itemView.setTag(position);
            ((ItemViewHolder) holder).value.setText(String.format("%s(%s)", bean.getName(), bean.getCode()));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView value;

        ItemViewHolder(View view) {
            super(view);
            value = (TextView) view.findViewById(R.id.tv);
        }
    }

    /**
     * 检测是否被遮住显示不全
     *
     * @return
     */
    protected boolean isCover(View view) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);
        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                return !cover;
            }
        }
        return true;
    }
}
