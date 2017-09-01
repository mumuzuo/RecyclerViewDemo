package com.cnbs.recyclerviewdemo.vlayoutRV;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.cnbs.recyclerviewdemo.R;
import com.cnbs.recyclerviewdemo.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/29.
 * 和普通的RecyclerView使用的adapter一样，只是把继承RecyclerView.Adapter换做了继承DelegateAdapter.Adapter
 */

public class EBannerAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private ArrayList<HashMap<String, Object>> mData;
    private int mCount = 0;

    public EBannerAdapter(Context context, LayoutHelper layoutHelper, int count,ArrayList<HashMap<String, Object>> data) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EBannerAdapter.ItemViewHolder) {
            ((EBannerAdapter.ItemViewHolder) holder).itemView.setTag(position);
            loadBanner( ((EBannerAdapter.ItemViewHolder) holder).viewpager ,((EBannerAdapter.ItemViewHolder) holder).viewGroup );
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        AutoScrollViewPager viewpager;
        LinearLayout viewGroup;
        public ItemViewHolder(View view) {
            super(view);
            viewpager = (AutoScrollViewPager) view.findViewById(R.id.viewpager);
            viewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);
        }
    }

    //--自的轮播实现
    private ImageView[] dots;
    private ImageView dot;
    private void loadBanner(AutoScrollViewPager vp, LinearLayout vg) {
        initViewPager(vp);
        initDot(vg);
    }

    private void initViewPager(final AutoScrollViewPager viewpager) {
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View adView = LayoutInflater.from(mContext).inflate(R.layout.item_banner_content, null);
                ImageView icon = (ImageView) adView.findViewById(R.id.banner_icon);
                icon.setImageResource((Integer)mData.get(position).get("ItemImage"));
                container.addView(adView);
                return adView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        viewpager.setScrollFactgor(5);
        viewpager.setOffscreenPageLimit(4);
        viewpager.startAutoScroll(2000);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dots.length; i++) {
                    if (position != i) {
                        dots[i].setBackgroundResource(R.drawable.grey_dot_shape);
                    } else {
                        dots[position].setBackgroundResource(R.drawable.white_dot_shape);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

    }


    private void initDot(LinearLayout viewGroup1) {
        if (mData.size() <= 1)return;
        dots = new ImageView[mData.size()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
        layoutParams.setMargins(10, 3, 10, 3);

        viewGroup1.removeAllViews();
        for (int i = 0; i <mData.size(); i++) {
            dot = new ImageView(mContext);
            dot.setLayoutParams(layoutParams);
            dots[i] = dot;
            dots[i].setTag(i);
            if (i == 0) {
                dots[i].setBackgroundResource(R.drawable.white_dot_shape);
            } else {
                dots[i].setBackgroundResource(R.drawable.grey_dot_shape);
            }
            viewGroup1.addView(dots[i]);
        }
    }
}
