package com.cnbs.recyclerviewdemo.groupRV.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;

import java.util.List;

/**
 * @author zuo
 * @date 2018/5/22 19:18
 */
public class SectionAdapter extends BaseSectionQuickAdapter<TypeBean,TypeViewHolder> {

    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(TypeViewHolder helper, TypeBean item) {

    }

    @Override
    protected void convert(TypeViewHolder helper, TypeBean item) {

    }
}
