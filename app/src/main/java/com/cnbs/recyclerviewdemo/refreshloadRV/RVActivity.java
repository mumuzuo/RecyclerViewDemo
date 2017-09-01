package com.cnbs.recyclerviewdemo.refreshloadRV;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnbs.recyclerviewdemo.R;
import com.cnbs.recyclerviewdemo.utils.OnRcvScrollListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RVActivity extends Activity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swipe_rv)
    SwipeRefreshLayout swipeRv;
    @BindView(R.id.activity_rv)
    LinearLayout activityRv;
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
        setContentView(R.layout.activity_rv);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        list0 = Arrays.asList(str0);
        list1 = Arrays.asList(str1);
        list2 = Arrays.asList(str2);
        initView();
    }

    private void initView() {
        list.addAll(list0);
        adapter = new RVRefreshAdapter(RVActivity.this, list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //刷新
        swipeRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=0;
                list.clear();
                list.addAll(list0);
                adapter.refresh(list);
                swipeRv.setRefreshing(false);
            }
        });

        //加载更多，使用自定义的滑动监听，主要是onBottom()方法
        rv.addOnScrollListener(new OnRcvScrollListener(){
            @Override
            public void onBottom() {
                super.onBottom();
                // 到底部自动加载
                page++;
                if (page==1){
                    list.addAll(list1);
                }
                if (page==2){
                    list.addAll(list2);
                }
                adapter.refresh(list);
            }
        });
    }

    @OnClick(R.id.left)
    public void onClick() {
        finish();
    }
}
