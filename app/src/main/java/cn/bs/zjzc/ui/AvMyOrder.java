package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.MyFragmentPagerAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.NormalPopuMenu;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.fragment.MyOrderFragment;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvMyOrder extends BaseActivity {
    private String mType;
    private List<String> titles;
    private List<String> states;
    private Context mContext = this;
    private TopBarView topBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_my_order);
        initBase();
        initViews();
        initViewPager();
        initEvents();
    }

    private void initBase() {
        mType = getIntent().getStringExtra("type");
        titles = new ArrayList<>();
        states = new ArrayList<>();
//类型（1 我的接单，2 我的订单）
//状态（1 待接单，2 进行中[2 待取货，3 进行中，4 待收货，9 接单用户确认取货，10 下单用户确认取货]，5 已完成 ，6 取消订单[6 下单取消，7 接单取消，11 系统取消]，8 待付款）
        if (TextUtils.equals(mType, "1")) {
            titles.add("进行中");
            titles.add("已完成");
            titles.add("全部");

            states.add("2");
            states.add("5");
            states.add(null);
        } else if (TextUtils.equals(mType, "2")) {
            titles.add("待付款");
            titles.add("待接单");
            titles.add("进行中");
            titles.add("已完成");
            titles.add("全部");

            states.add("8");
            states.add("1");
            states.add("2");
            states.add("5");
            states.add(null);
        }
    }

    private void initEvents() {
        topBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBar.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final NormalPopuMenu normalPopuMenu = new NormalPopuMenu(mContext);
                normalPopuMenu.setMenuItems(getResources().getStringArray(R.array.str_array_home_order_type));
                normalPopuMenu.setMenuGravity(Gravity.TOP);
                normalPopuMenu.setTouchCancelable(true);
                normalPopuMenu.setOnMenuItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 5) {
                            viewPager.setVisibility(View.VISIBLE);
                            tabLayout.setVisibility(View.VISIBLE);
                        } else {
                            viewPager.setVisibility(View.GONE);
                            tabLayout.setVisibility(View.GONE);
                            MyOrderFragment fragment = new MyOrderFragment();
                            Bundle bundle = new Bundle();
                            String orderType = String.valueOf(position + 1);
                            bundle.putString("state", null);
                            bundle.putString("type", mType);
                            bundle.putString("order_type", orderType);
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.my_order_fragment_frame, fragment, orderType).commit();
                        }
                        normalPopuMenu.dismiss();
                    }
                });
                normalPopuMenu.showAsDropDown(v);
            }
        });
        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvOrderSearch.class);
                intent.putExtra("type", mType);
                startActivity(intent);
            }
        });
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        int size = states.size();
        for (int i = 0; i < size; i++) {
            MyOrderFragment fragment = new MyOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putString("state", states.get(i));
            bundle.putString("type", mType);
            bundle.putString("order_type", null);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(fragments.size() - 1);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.my_order_top_bar));
        tabLayout = ((TabLayout) findViewById(R.id.my_order_tab_layout));
        viewPager = ((ViewPager) findViewById(R.id.my_order_view_pager));
    }
}
