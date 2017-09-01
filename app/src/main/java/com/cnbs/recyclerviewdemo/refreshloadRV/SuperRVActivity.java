package com.cnbs.recyclerviewdemo.refreshloadRV;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnbs.recyclerviewdemo.R;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuperRVActivity extends Activity {

    @BindView(R.id.list_super_rv)
    SuperRecyclerView superRv;
    @BindView(R.id.activity_super_rv)
    LinearLayout activitySuperRv;
    @BindView(R.id.title)
    TextView title;

    private String[] str0 = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09"};
    private String[] str1 = {"10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
    private String[] str2 = {"20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    private ArrayList<String> list;
    private List<String> list0;
    private List<String> list1;
    private List<String> list2;
    private RVRefreshAdapter adapter;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_rv);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        list0 = Arrays.asList(str0);
        list1 = Arrays.asList(str1);
        list2 = Arrays.asList(str2);
        initView();
    }

    private void initView() {
        list.addAll(list0);
        adapter = new RVRefreshAdapter(SuperRVActivity.this, list);
        superRv.setAdapter(adapter);
        superRv.setLayoutManager(new LinearLayoutManager(this));
        //刷新
        superRv.getSwipeToRefresh().setColorSchemeResources(R.color.base_color);    //设置刷新进度条颜色
        superRv.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
                list.clear();
                list.addAll(list0);
                adapter.refresh(list);
                superRv.setRefreshing(false);
            }
        });

        //加载,max---还剩几个的时候开始加载更多，利用handler模拟请求耗时
        superRv.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                page++;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (page==1){
                            list.addAll(list1);
                        }
                        if (page==2){
                            list.addAll(list2);
                        }
                        adapter.refresh(list);
                    }
                },1000);
            }
        },1);
    }

    @OnClick(R.id.left)
    public void onClick() {
        finish();
    }
}
