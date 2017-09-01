package com.cnbs.recyclerviewdemo.vlayoutRV;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.cnbs.recyclerviewdemo.R;
import com.cnbs.recyclerviewdemo.utils.OnRcvScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 电商首页的复杂界面---布局复杂但不能自定义(不能随业务改变布局)
 * 一、使用继承 自 DelegateAdapter 的 Adapter 的集合 来实现复杂布局的组合
 */
public class EShopActivity extends Activity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshView;

    private boolean hasConsistItemType;
    private List<DelegateAdapter.Adapter> adapters;
    private int pageNum = 1;
    private int pageSize = 10;
    private ENavigationAdapter eNavigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eshop);
        ButterKnife.bind(this);
        initView();
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //--模拟刷新操作
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum=1;
                        initCommendGoods(pageNum*pageSize,true);
                        refreshView.setRefreshing(false);
                    }
                },1000);
            }
        });
        //加载更多，使用自定义的滑动监听，主要是onBottom()方法
        recyclerView.addOnScrollListener(new OnRcvScrollListener(){
            @Override
            public void onBottom() {
                super.onBottom();
                // 到底部自动加载
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNum++;
                        initCommendGoods(pageNum*pageSize,true);
                    }
                },1500);
            }
        });
    }

    private void initView() {
        /**
         * 1、初始化VirtualLayoutManager
         */
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /**
         * 2、设置回收池
         * 针对type=0和1的item设置了复用池的大小，如果你的页面有多种type，需要为每一种类型的分别调整复用池大小参数。
         */
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 3);
        viewPool.setMaxRecycledViews(1, 4);
        viewPool.setMaxRecycledViews(2, 1);
        viewPool.setMaxRecycledViews(3, 10);
        viewPool.setMaxRecycledViews(4, 1);
        viewPool.setMaxRecycledViews(5, 3);
        viewPool.setMaxRecycledViews(6, 1);
        viewPool.setMaxRecycledViews(7, 3);
        viewPool.setMaxRecycledViews(8, 4);
        viewPool.setMaxRecycledViews(9, 5);
        viewPool.setMaxRecycledViews(10, 1);
        viewPool.setMaxRecycledViews(11, 10);
        /**
         * 3、设置加载数据时的adapters
         * 需要继承DelegateAdapter.Adapter（注意哦，是DelegateAdapter.Adapter不是DelegateAdapter）
         * 这里就是实现复杂界面的核心位置了，通过不同的adapter加载不同的数据、布局，组合成复杂的界面
         * 一个adapter绑定一个helper,下面来分解复杂界面
         */
        //设置Adapter列表（同时也是设置LayoutHelper列表）
        adapters = new LinkedList<>();
        //自动轮播图
        initBanner();
        //导航菜单
        initNavigation();
        //提示语
//        initHint(R.mipmap.ic_launcher, "热门推荐");
        initTitle(R.mipmap.banner2, "热 / 门 / 商 / 品");
        //热门商品
        initHotGoods();
        //提示语
//        initHint(R.mipmap.img_default, "优惠活动");
        initTitle(R.mipmap.banner1, "优 / 惠 / 活 / 动");
        //活动介绍
        initAction();
        //提示语
        initTitle(R.mipmap.img_default, "经 / 典 / 组 / 合");
        //组合商品
        initTeam(3);
        initTeam(4);
        initTeam(5);
        //返回顶部,不要放到推荐商品的后面，那样将不能加载更多
        initBackTop();
        //文字提示语
        initTitle(R.mipmap.banner0, "为 / 你 / 推 / 荐");
        //推荐商品
        initCommendGoods(pageNum*pageSize,false);
        //4、初始化DelegateAdapter
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, hasConsistItemType);
        //5. 将DelegateAdapter.Adapter列表绑定到DelegateAdapter
        delegateAdapter.setAdapters(adapters);
        //6.将delegateAdapter绑定到recyclerView
        recyclerView.setAdapter(delegateAdapter);
    }

//------上面是主体架构，下面是具体实现-------

    /**
     * 3-1.自动轮播图--我们使用通栏布局SingleLayoutHelper（只显示一个组件View）
     */
    private void initBanner() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        //造数据
        for (int i = 0; i < 3; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "个");
            if (i == 0) map.put("ItemImage", R.mipmap.banner0);
            if (i == 1) map.put("ItemImage", R.mipmap.banner1);
            if (i == 2) map.put("ItemImage", R.mipmap.banner2);
            listItem.add(map);
        }
        //初始化SingleLayoutHelper以实现布局
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        // 公共属性
        singleLayoutHelper.setItemCount(1);// 设置布局里Item个数
        singleLayoutHelper.setPadding(0, 0, 0, 0);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        singleLayoutHelper.setMargin(0, 0, 0, 0);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        singleLayoutHelper.setBgColor(Color.WHITE);// 设置背景颜色
        singleLayoutHelper.setAspectRatio(16f / 9f);// 设置设置布局内每行布局的宽与高的比
        //创建Adapter绑定数据
        EBannerAdapter eBannerAdapter = new EBannerAdapter(EShopActivity.this, singleLayoutHelper, 1, listItem);
        adapters.add(eBannerAdapter);
    }

    /**
     * 3-2.设置导航--我们使用栏格布局ColumnLayoutHelper
     */
    private void initNavigation() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        //造数据
        for (int i = 0; i < 4; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "导航" + i);
            map.put("ItemImage", R.mipmap.img_default);
            listItem.add(map);
        }
        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();
        // 公共属性
        columnLayoutHelper.setItemCount(4);// 设置布局里Item个数
        columnLayoutHelper.setPadding(1, 1, 1, 1);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        columnLayoutHelper.setMargin(1, 2, 1, 2);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        columnLayoutHelper.setBgColor(Color.WHITE);// 设置背景颜色
        columnLayoutHelper.setAspectRatio(4f / 1f);// 设置设置布局内每行布局的宽与高的比
        columnLayoutHelper.setBgColor(Color.GRAY);
        // columnLayoutHelper特有属性
        columnLayoutHelper.setWeights(new float[]{25, 25, 25, 25});// 设置该行每个Item占该行总宽度的比例
        ENavigationAdapter eNavigationAdapter = new ENavigationAdapter(EShopActivity.this, columnLayoutHelper, 4, listItem);
        adapters.add(eNavigationAdapter);
    }

    /**
     * 3-3.设置页面提示语，我们使用通栏布局SingleLayoutHelper
     *
     * @param imgHint
     * @param strHint
     */
    private void initHint(int imgHint, String strHint) {
        //初始化SingleLayoutHelper以实现布局
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemTitle", strHint);
        map.put("ItemImage", imgHint);
        // 公共属性
        singleLayoutHelper.setItemCount(1);// 设置布局里Item个数
        singleLayoutHelper.setPadding(0, 0, 0, 0);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        singleLayoutHelper.setMargin(0, 0, 0, 0);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        singleLayoutHelper.setBgColor(Color.WHITE);// 设置背景颜色
        singleLayoutHelper.setAspectRatio(10f / 1f);// 设置设置布局内每行布局的宽与高的比
        EHintAdapter eHintAdapter = new EHintAdapter(EShopActivity.this, singleLayoutHelper, 1, map);
        adapters.add(eHintAdapter);
    }

    /**
     * 3-4.设置网格布局
     */
    private void initHotGoods() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        //造数据
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "商品" + i);
            map.put("ItemImage", R.mipmap.img_default);
            listItem.add(map);
        }
        GridLayoutHelper gridLayoutHelper1 = new GridLayoutHelper(6);
        // 公共属性
        gridLayoutHelper1.setItemCount(10);// 设置布局里Item个数
        gridLayoutHelper1.setPadding(0, 0, 0, 0);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        gridLayoutHelper1.setMargin(0, 1, 0, 1);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        gridLayoutHelper1.setBgColor(Color.GRAY);// 设置背景颜色
        gridLayoutHelper1.setAspectRatio(3f / 1f);// 设置设置布局内每行布局的宽与高的比
        // gridLayoutHelper特有属性（下面会详细说明）
//        gridLayoutHelper1.setWeights(new float[]{40, 30, 30});//设置每行中 每个网格宽度 占 每行总宽度 的比例,数组中的个数和SpanCount一致，总和为100，默认等分
        gridLayoutHelper1.setVGap(1);// 控制子元素之间的垂直间距
        gridLayoutHelper1.setHGap(1);// 控制子元素之间的水平间距
        gridLayoutHelper1.setAutoExpand(false);//是否自动填充空白区域
        gridLayoutHelper1.setSpanCount(6);// 设置每行多少个网格
        gridLayoutHelper1.setAutoExpand(true);  //是否自动填满空白区域（等分）
        /**
         * 通过自定义SpanSizeLookup来控制某个Item的占网格个数，
         * 在 SpanSizeLookup 中，public int getSpanSize(int position) 方法参数的 position 是整个页面的 position 信息，
         * 需要获取当前 layoutHelper 内的相对位置，需要减去一个偏移量，即 position - getStartPosition()。
         */
        gridLayoutHelper1.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int p = position - getStartPosition();
                if (p > 5 ) {
                    return 3;
                }else {
                    return 2;
                }
            }
        });
        ENavigationAdapter eNavigationAdapter = new ENavigationAdapter(EShopActivity.this, gridLayoutHelper1, 10, listItem);
        adapters.add(eNavigationAdapter);

    }

    /**
     * 3-5.设置线性布局
     */
    private void initAction() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        //造数据
        for (int i = 0; i < 3; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            if (i == 0) map.put("ItemImage", R.mipmap.banner0);
            if (i == 1) map.put("ItemImage", R.mipmap.banner1);
            if (i == 2) map.put("ItemImage", R.mipmap.banner2);
            listItem.add(map);
        }
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        // 所有布局的公共属性（属性会在下面详细说明）
        linearLayoutHelper.setItemCount(3);// 设置布局里Item个数
        linearLayoutHelper.setPadding(1, 1, 1, 1);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        linearLayoutHelper.setMargin(1, 1, 1, 1);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        linearLayoutHelper.setBgColor(Color.WHITE);// 设置背景颜色
        linearLayoutHelper.setAspectRatio(16f / 9f);// 设置设置布局内每行布局的宽与高的比
        // linearLayoutHelper特有属性
        linearLayoutHelper.setDividerHeight(1); // 设置每行Item的距离
        EActionAdapter eActionAdapter = new EActionAdapter(EShopActivity.this, linearLayoutHelper, 3, listItem);
        adapters.add(eActionAdapter);
    }

    /**
     * --3.6-设置组合商品，使用1拖N布局
     */
    private void initTeam(int num) {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        //造数据
        for (int i = 0; i < num; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "商品" + i);
            map.put("ItemImage", R.mipmap.img_default);
            listItem.add(map);
        }
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(3);
        // 在构造函数里传入显示的Item数
        // 最多是1拖4,即5个
        // 公共属性
        onePlusNLayoutHelper.setItemCount(num);// 设置布局里Item个数，1拖2
        onePlusNLayoutHelper.setPadding(1, 1, 1, 1);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        onePlusNLayoutHelper.setMargin(1, 1, 1, 1);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        onePlusNLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
        onePlusNLayoutHelper.setAspectRatio(3f / 1f);// 设置设置布局内每行布局的宽与高的比
        ENavigationAdapter eNavigationAdapter = new ENavigationAdapter(EShopActivity.this, onePlusNLayoutHelper, num, listItem);
        adapters.add(eNavigationAdapter);
    }

    /**
     * --3.7-文字标题
     */
    private void initTitle(int imgBg, String strTitle) {
        //初始化SingleLayoutHelper以实现布局
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemTitle", strTitle);
        map.put("ItemImage", imgBg);
        // 公共属性
        singleLayoutHelper.setItemCount(1);// 设置布局里Item个数
        singleLayoutHelper.setPadding(0, 0, 0, 0);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        singleLayoutHelper.setMargin(0, 0, 0, 0);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        singleLayoutHelper.setBgColor(Color.WHITE);// 设置背景颜色
        singleLayoutHelper.setAspectRatio(10f / 1f);// 设置设置布局内每行布局的宽与高的比
        ETitleAdapter eTitleAdapter = new ETitleAdapter(EShopActivity.this, singleLayoutHelper, 1, map);
        adapters.add(eTitleAdapter);
    }

    /**
     * 3-8.设置网格布局
     */
    private void initCommendGoods(int num,boolean isLoad) {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<>();
        //造数据
        for (int i = 0; i < num; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "商品" + i);
            map.put("ItemImage", R.mipmap.img_default);
            listItem.add(map);
        }
        if (isLoad&&eNavigationAdapter!=null){    //如果是加载更多
            eNavigationAdapter.refresh(num , listItem);
            return;
        }
        GridLayoutHelper gridLayoutHelper1 = new GridLayoutHelper(6);
        // 公共属性
        gridLayoutHelper1.setItemCount(num);// 设置布局里Item个数
        gridLayoutHelper1.setPadding(0, 0, 0, 0);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        gridLayoutHelper1.setMargin(0, 1, 0, 1);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        gridLayoutHelper1.setBgColor(Color.GRAY);// 设置背景颜色
        gridLayoutHelper1.setAspectRatio(2f / 1f);// 设置设置布局内每行布局的宽与高的比
        // gridLayoutHelper特有属性（下面会详细说明）
        gridLayoutHelper1.setWeights(new float[]{50, 50});//设置每行中 每个网格宽度 占 每行总宽度 的比例,数组中的个数和SpanCount一致，总和为100，默认等分
        gridLayoutHelper1.setVGap(1);// 控制子元素之间的垂直间距
        gridLayoutHelper1.setHGap(1);// 控制子元素之间的水平间距
        gridLayoutHelper1.setAutoExpand(false);//是否自动填充空白区域
        gridLayoutHelper1.setSpanCount(2);// 设置每行多少个网格
        gridLayoutHelper1.setAutoExpand(true);  //是否自动填满空白区域（等分）
        eNavigationAdapter = new ENavigationAdapter(EShopActivity.this, gridLayoutHelper1, num, listItem);
        adapters.add(eNavigationAdapter);
    }

    /**
     * 3-4.返回顶部---固定布局
     */
    private void initBackTop() {
        ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.BOTTOM_RIGHT, 30, 50);
        // 公共属性
        scrollFixLayoutHelper.setItemCount(1);// 设置布局里Item个数
        scrollFixLayoutHelper.setPadding(10, 10, 10, 10);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        scrollFixLayoutHelper.setMargin(0, 0, 0, 0);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        scrollFixLayoutHelper.setBgColor(Color.GRAY);// 设置背景颜色
        scrollFixLayoutHelper.setAspectRatio(1);// 设置设置布局内每行布局的宽与高的比
        // fixLayoutHelper特有属性
        scrollFixLayoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);// 设置吸边时的基准位置(alignType)
        scrollFixLayoutHelper.setX(30);// 设置基准位置的横向偏移量X
        scrollFixLayoutHelper.setY(50);// 设置基准位置的纵向偏移量Y
        scrollFixLayoutHelper.setShowType(ScrollFixLayoutHelper.SHOW_ON_ENTER);// 设置Item的显示模式
        EBackAdapter eBackAdapter = new EBackAdapter(EShopActivity.this, scrollFixLayoutHelper, 1, new EBackAdapter.ItemClickListener() {
            @Override
            public void OnClick(View v) {
                recyclerView.scrollToPosition(0);
//                recyclerView.smoothScrollToPosition(0);
            }
        });
        adapters.add(eBackAdapter);
    }

    @OnClick(R.id.left)
    public void onClick() {
        finish();
    }
}
