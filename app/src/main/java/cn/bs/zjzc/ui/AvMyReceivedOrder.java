package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.NormalPopuMenu;
import cn.bs.zjzc.div.TopBarView;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvMyReceivedOrder extends BaseActivity {
    private Context mContext = this;
    private TopBarView topBar;
    private TabLayout tabLayout;
    private ListView myOrderList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_my_order);
        initViews();
        initEvents();
        initTabLayout();
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
                NormalPopuMenu normalPopuMenu = new NormalPopuMenu(mContext);
                normalPopuMenu.setMenuItems(getResources().getStringArray(R.array.str_array_home_order_type));
                normalPopuMenu.setMenuGravity(Gravity.TOP);
                normalPopuMenu.setOnMenuItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
                normalPopuMenu.showAsDropDown(v);
            }
        });
        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AvFAQSearch.class));
            }
        });
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("进行中"));
        tabLayout.addTab(tabLayout.newTab().setText("已完成"));
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.my_order_top_bar));
        tabLayout = ((TabLayout) findViewById(R.id.my_order_tab_layout));
        topBar.setTitle("我的接单");
    }
}
