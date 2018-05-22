package com.cnbs.recyclerviewdemo.groupRV;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.cnbs.recyclerviewdemo.BaseActivity;
import com.cnbs.recyclerviewdemo.R;
import com.cnbs.recyclerviewdemo.groupRV.itemDecoration.GroupLineItemDecoration;
import com.cnbs.recyclerviewdemo.groupRV.itemDecoration.ItemDecAdapter;
import com.cnbs.recyclerviewdemo.groupRV.itemDecoration.TypeBean;
import com.cnbs.recyclerviewdemo.groupRV.vlayout.DataBean;
import com.cnbs.recyclerviewdemo.groupRV.vlayout.TypeAdapter;
import com.cnbs.recyclerviewdemo.groupRV.vlayout.TypeDataAdapter;
import com.cnbs.recyclerviewdemo.utils.StrData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zuo
 * @date 2018/5/22 10:44
 * 实现分组功能
 */
public class GroupActivity extends BaseActivity {

    @BindView(R.id.group_rv)
    RecyclerView groupRv;
    private List<TypeBean> mData;
    private List<DataBean.TypeListBean> typeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Gson gson = new Gson();
//        DataTypeBean json = gson.fromJson(StrData.groupStr, DataTypeBean.class);
        DataBean json = gson.fromJson(StrData.groupStr02, DataBean.class);
        typeList = json.getTypeList();
    }

    @OnClick({R.id.group_0, R.id.group_1, R.id.group_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.group_0:
//                itemDecorationGroup();
                break;
            case R.id.group_1:
                adapterGroup();
                break;
            case R.id.group_2:
                vLayoutGroup();
                break;
            default:
                break;
        }
    }

    /**
     * 3、利用vlayout实现分组列表
     */
    private List<DelegateAdapter.Adapter> adapters;
    private void vLayoutGroup() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        groupRv.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        groupRv.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 5);
        viewPool.setMaxRecycledViews(1, 20);
        adapters = new LinkedList<>();
        for (DataBean.TypeListBean listBean : typeList) {
            String type = listBean.getType();
            List<DataBean.TypeListBean.ListBean> list = listBean.getList();
            initType(type);
            initTypeData(list);
        }
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);
        groupRv.setAdapter(delegateAdapter);
    }

    private void initTypeData(List<DataBean.TypeListBean.ListBean> list) {
        GridLayoutHelper gridLayoutHelper1 = new GridLayoutHelper(3);
        gridLayoutHelper1.setItemCount(list.size());
        gridLayoutHelper1.setPadding(0, 0, 0, 0);
        gridLayoutHelper1.setMargin(0, 0, 0, 0);
        gridLayoutHelper1.setBgColor(Color.WHITE);
//        gridLayoutHelper1.setAspectRatio(5f / 1f);
        gridLayoutHelper1.setVGap(-1);
        gridLayoutHelper1.setHGap(-1);
        gridLayoutHelper1.setSpanCount(3);
        gridLayoutHelper1.setAutoExpand(true);
        TypeDataAdapter dataAdapter = new TypeDataAdapter(this, gridLayoutHelper1, list.size(), list);
        adapters.add(dataAdapter);
    }

    /**
     * 类型标题
     * @param type
     */
    private void initType(String type) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setItemCount(1);
        singleLayoutHelper.setPadding(0, 0, 0, 0);
        singleLayoutHelper.setMargin(0, 0, 0, 0);
//        singleLayoutHelper.setBgColor(R.color.title_type);
        singleLayoutHelper.setBgColor(0xFFC3CDDA);
        singleLayoutHelper.setAspectRatio(10f / 1f);
        TypeAdapter typeAdapter = new TypeAdapter(this, singleLayoutHelper, 1, type);
        adapters.add(typeAdapter);
    }

    /**
     * 2、利用BaseRecyclerViewAdapterHelper框架实现分组列表
     */
    private void adapterGroup() {
    }




    /**
     * 1、使用ItemDecoration实现分组悬浮列表
     */
    private List<Integer> indexList = new ArrayList<>();
    private Map<Integer, Integer> spanMap = new HashMap<>();
    private final int spanCount = 6;
    private final int lineNum = 3;

    private void itemDecorationGroup() {
        ItemDecAdapter itemDecAdapter = new ItemDecAdapter(GroupActivity.this, mData);
        groupRv.setAdapter(itemDecAdapter);
        //在这里计算好需要重新设置span的item的位置
        for (int i = 0; i < mData.size() - 1; i++) {
            String type = mData.get(i).getType().trim();
            String typeLast = mData.get(i + 1).getType().trim();
            if (null != type && !type.equals(typeLast)) {
                //不为空 且跟前一个tag不一样了，说明是新的分类，也要title,由于是Grid布局需要计算位置
                int remainder = (i + 1) % lineNum;
                for (int i1 = 0; i1 < remainder; i1++) {
                    indexList.add(i - i1);
                    spanMap.put(i - i1, spanCount / remainder);
                }
            }
        }

        GridLayoutManager manager = new GridLayoutManager(GroupActivity.this, spanCount);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

            @Override
            public int getSpanSize(int position) {
                if (indexList.contains(position)) {
                    return spanMap.get(position);
                }
                return spanCount / lineNum;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(GroupActivity.this);
        groupRv.setLayoutManager(manager);
        GroupLineItemDecoration groupItemDecoration = new GroupLineItemDecoration(GroupActivity.this, mData, 40, 16);
        groupRv.addItemDecoration(groupItemDecoration);
    }
}
