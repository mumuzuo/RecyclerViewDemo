package com.cnbs.recyclerviewdemo.mulitRV;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cnbs.recyclerviewdemo.BaseActivity;
import com.cnbs.recyclerviewdemo.R;
import com.cnbs.recyclerviewdemo.utils.SpaceItemDecoration;
import com.cnbs.recyclerviewdemo.utils.StrData;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MulitActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mulit_rv)
    RecyclerView mulitRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulit);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Gson gson = new Gson();
        ResponseBean responseBean = gson.fromJson(StrData.mulitStr, ResponseBean.class);
        List<MulitBean> list = responseBean.getList();
        MulitAdapter adapter = new MulitAdapter(MulitActivity.this, list);
        mulitRv.setLayoutManager(new LinearLayoutManager(this));
        mulitRv.addItemDecoration(new SpaceItemDecoration(12));
        mulitRv.setAdapter(adapter);
    }

    @OnClick(R.id.left)
    public void onClick() {
        finish();
    }
}
