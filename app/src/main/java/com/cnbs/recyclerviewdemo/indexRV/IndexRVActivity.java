package com.cnbs.recyclerviewdemo.indexRV;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cnbs.recyclerviewdemo.BaseActivity;
import com.cnbs.recyclerviewdemo.R;
import com.cnbs.recyclerviewdemo.utils.StrData;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IndexRVActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.appoint_rv)
    RecyclerView appointRv;
    @BindView(R.id.index_text)
    IndexBar indexBar;
    @BindView(R.id.index_text_s)
    TextView indexTextHint;

    private LinearLayoutManager linearLayoutManager;
    private List<IndexBean.ListBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_rv);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Gson gson = new Gson();
        IndexBean json = gson.fromJson(StrData.indexStr, IndexBean.class);
        list = json.getList();
        IndexAdapter adapter = new IndexAdapter(IndexRVActivity.this, list);
        linearLayoutManager = new LinearLayoutManager(this);
        appointRv.setAdapter(adapter);
        appointRv.setLayoutManager(linearLayoutManager);
        linkIndex();
    }

    private void linkIndex() {
        indexBar.setmPressedShowTextView(indexTextHint);
        indexBar.setmLayoutManager(linearLayoutManager);
        indexBar.setmOnIndexPressedListener(new IndexBar.onIndexPressedListener() {
            @Override
            public void onIndexPressed(int index, String text) {
                if (indexTextHint != null) { //显示hintTexView
                    indexTextHint.setVisibility(View.VISIBLE);
                    indexTextHint.setText(text);
                }
                //滑动Rv
                if (linearLayoutManager != null) {
                    int position = getPosByTag(text);
                    if (position != -1) {
                        linearLayoutManager.scrollToPositionWithOffset(position, 0);
                    }
                }
            }

            @Override
            public void onMotionEventEnd() {
                //隐藏hintTextView
                if (indexTextHint != null) {
                    indexTextHint.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 根据传入的pos返回tag
     * @param tag
     * @return
     */
    private int getPosByTag(String tag) {
        int index = -1 ;
        if (!TextUtils.isEmpty(tag)) {
            for (int i = 0; i < list.size(); i++) {
                String inputcode = list.get(i).getInputcode();
                if (tag.equals(inputcode)) {
                    return i;
                }
            }
        }
        return index;
    }

    @OnClick(R.id.left)
    public void onClick() {
        finish();
    }
}
