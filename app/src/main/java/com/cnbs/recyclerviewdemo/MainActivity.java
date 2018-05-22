package com.cnbs.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cnbs.recyclerviewdemo.groupRV.GroupActivity;
import com.cnbs.recyclerviewdemo.indexRV.IndexRVActivity;
import com.cnbs.recyclerviewdemo.mulitRV.MulitActivity;
import com.cnbs.recyclerviewdemo.refreshloadRV.RVActivity;
import com.cnbs.recyclerviewdemo.refreshloadRV.SuperRVActivity;
import com.cnbs.recyclerviewdemo.vlayoutRV.EShopActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rv_01,R.id.rv_0, R.id.rv_1, R.id.rv_2, R.id.rv_3, R.id.rv_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_01:
                startActivity(new Intent(MainActivity.this, GroupActivity.class));
                break;
            case R.id.rv_0:
                startActivity(new Intent(MainActivity.this, IndexRVActivity.class));
                break;

            case R.id.rv_1:
                startActivity(new Intent(MainActivity.this, MulitActivity.class));
                break;
            case R.id.rv_2:
                startActivity(new Intent(MainActivity.this, RVActivity.class));
                break;
            case R.id.rv_3:
                startActivity(new Intent(MainActivity.this, SuperRVActivity.class));
                break;
            case R.id.rv_4:
                startActivity(new Intent(MainActivity.this, EShopActivity.class));
                break;
            default:
                break;
        }
    }
}
