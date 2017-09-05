package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.MyFragmentPagerAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.fragment.MyEvaluationFragment;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvMyEvaluation extends BaseActivity {

    private TabLayout tabLayout;
    private TopBarView topBar;
    private ViewPager viewPager;
    //    类型（1 来自下单，2 来自接单）
    private List<String> titles;
    private List<Fragment> fragments;
    private MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_my_evaluation);
        initViews();
        initTabLayout();
        initViewPager();
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

        MyEvaluationFragment fragment1 = new MyEvaluationFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("type", "1");
        fragment1.setArguments(bundle1);
        fragments.add(fragment1);

        MyEvaluationFragment fragment2 = new MyEvaluationFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("type", "2");
        fragment2.setArguments(bundle2);
        fragments.add(fragment2);

        titles.add("来自接单用户");
        titles.add("来自下单用户");

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("来自接单用户"));
        tabLayout.addTab(tabLayout.newTab().setText("来自下单用户"));
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.my_evaluation_top_bar));
        tabLayout = ((TabLayout) findViewById(R.id.my_evaluation_tab_layout));
        viewPager = ((ViewPager) findViewById(R.id.my_evaluation_view_pager));
    }
}
