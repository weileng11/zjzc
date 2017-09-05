package cn.bs.zjzc.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.MyFragmentPagerAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.fragment.MyOrderFragment;
import cn.bs.zjzc.fragment.OrderCompetitionFragment;
import cn.bs.zjzc.model.response.OrderSetting;
import cn.bs.zjzc.presenter.OrderSettingPresenter;
import cn.bs.zjzc.socket.service.SocketService;
import cn.bs.zjzc.ui.view.IOrderSettingView;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.T;
import cn.bs.zjzc.util.ViewUtils;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvOrderCompetition extends BaseActivity implements IOrderSettingView, CompoundButton.OnCheckedChangeListener {
    private Context mContext = this;
    private List<String> titles;
    private List<Fragment> fragments;
    private TopBarView topBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;

    private TopBarView menuTopBar;
    private Spinner spinnerDistance;//接单距离选择
    private CheckBox receiveOrderSwtich;//接单开关
    private CheckBox autoFlushSwitch;
    private CheckBox homeSwitch;//回家接单开关

    private CheckBox menuItemWorkSwitch;//上班接单开关

    private OrderSettingPresenter mOrderSettingPresenter;
    private TextView order_type;
    private View orderTypeView;
    private PopupWindow orderPwindow;
    private ViewGroup vg_orderType_layout;
    private TopBarView order_type_top_bar;
    private CheckBox cb_All;
    private ViewGroup menuLayout;
    private View view;
    private int width = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_order_competition);
        initViews();
        initBasic();
        initEvents();
    }

    private void initBasic() {
        mOrderSettingPresenter = new OrderSettingPresenter(this);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

        MyOrderFragment fragment = new MyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("state", "2");
        bundle.putString("type", "1");
        bundle.putString("order_type", null);
        fragment.setArguments(bundle);
        fragments.add(new OrderCompetitionFragment());
        fragments.add(fragment);

        titles.add("新订单");
        titles.add("进行中");

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }

    private boolean canSave = false;

    private void initEvents() {
        topBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvHome.class);
                startActivity(intent);
                finish();
            }
        });
        topBar.setTitleClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

//        设置接单按钮
        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderSettingPresenter.getOrderSetting();
            }
        });

        menuTopBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


//        保存接单设置按钮
        menuTopBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String range = "";
                if (spinnerDistance.getSelectedItemPosition() == 0) {
                    range = "1";
                } else {
                    range = "3";
                }

                String orderType = "";
                if (((CheckBox) vg_orderType_layout.getChildAt(0)).isChecked()) {
                    orderType = "1,2,3,4,5";
                } else {
                    for (int i = 1; i < vg_orderType_layout.getChildCount(); i++) {
                        if (((CheckBox) vg_orderType_layout.getChildAt(i)).isChecked()) {
                            orderType = orderType + i + ",";
                        }
                    }
                    if (orderType.length() != 0)
                        orderType = orderType.substring(0, orderType.length() - 1);
                }

                String isFlush = autoFlushSwitch.isChecked() ? "1" : "2";
                String isPush = receiveOrderSwtich.isChecked() ? "1" : "2";


                String tempStr = homeSwitch.isChecked() ? "1" : "2";

                String accepyType = receiveOrderSwtich.isChecked() ? tempStr : "3";

                if (orderSettingData.distance.equals(range) && orderTempStr.equals(orderType)
                        && orderSettingData.flush.equals(isFlush) && orderSettingData.push.equals(isPush)
                        && orderSettingData.type.equals(accepyType)
                        ) {
                    canSave = false;
                } else {
                    canSave = true;
                }

                L.e("orderType=" + orderType + "  range=" + range + "   isFlush=" + isFlush + "   accepyType=" + accepyType + "   isPush=" + isPush);

                if (canSave) {
                    mOrderSettingPresenter.saveOrderSetting(orderType, range, isFlush, accepyType, isPush);
                } else {
                    T.showShort(mContext, "你没有修改过设置数据");
                }

            }
        });


//      接单类型的点击事件
        findViewById(R.id.order_type_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              orderPwindow.showAsDropDown(v);
                if (orderPwindow == null) {
                    orderPwindow = new PopupWindow(mContext);
                    width = menuLayout.getMeasuredWidth();
                    orderPwindow.setWidth(width);
                    orderPwindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                    orderPwindow.setContentView(orderTypeView);
                    // 设置允许在外点击消失
                    orderPwindow.setOutsideTouchable(true);
                    orderPwindow.setFocusable(true);
                    ColorDrawable dw = new ColorDrawable(0xb0000000);
                    orderPwindow.setBackgroundDrawable(dw);
                    orderPwindow.setAnimationStyle(R.style.popwin_anim_style);
                }
                orderPwindow.showAsDropDown(view, width, 0);
            }
        });


//      接受派单监听事件
        homeSwitch.setClickable(receiveOrderSwtich.isChecked());
        menuItemWorkSwitch.setClickable(receiveOrderSwtich.isChecked());

        receiveOrderSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    homeSwitch.setChecked(true);
                    homeSwitch.setClickable(true);
                    menuItemWorkSwitch.setClickable(true);
                } else {
                    homeSwitch.setChecked(false);
                    menuItemWorkSwitch.setChecked(false);
                    homeSwitch.setClickable(false);
                    menuItemWorkSwitch.setClickable(false);
                }
            }
        });
        homeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (receiveOrderSwtich.isChecked())
                    menuItemWorkSwitch.setChecked(!isChecked);
            }
        });
        menuItemWorkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (receiveOrderSwtich.isChecked())
                    homeSwitch.setChecked(!isChecked);
            }
        });

//       接单类型取消按钮
        order_type_top_bar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                恢复状态
                setOrderData();
                if (orderPwindow.isShowing())
                    orderPwindow.dismiss();
            }
        });

//       保存接单类型按钮
        order_type_top_bar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canCommint()) {
                    orderTypeStr.setLength(0);
                    for (int i = 0; i < vg_orderType_layout.getChildCount(); i++) {
                        if (((CheckBox) vg_orderType_layout.getChildAt(i)).isChecked()) {
                            orderTypeStr.append((((CheckBox) vg_orderType_layout.getChildAt(i)).getText()) + "、");
                        }
                    }
                    if (orderTypeStr.length() != 0)
                        order_type.setText(orderTypeStr.toString().substring(0, orderTypeStr.toString().length() - 1));
                    if (orderPwindow.isShowing())
                        orderPwindow.dismiss();
                } else {
                    T.showShort(mContext, "请至少选择其中一项");
                }
            }
        });
//       设置接单类型的item监听事件
        for (int i = 0; i < vg_orderType_layout.getChildCount(); i++) {
            ((CheckBox) vg_orderType_layout.getChildAt(i)).setOnCheckedChangeListener(this);
        }
    }

    //    接单类型变量，用于最后提交保存做比较是否修改过
    private String orderTempStr = "";

    private void setOrderData() {
        List<Integer> orderType = orderSettingData.order;
        orderTypeStr.setLength(0);
        for (int i = 0; i < orderType.size(); i++) {
            Integer integer = orderType.get(i);
            ((CheckBox) vg_orderType_layout.getChildAt(integer)).setChecked(true);
            orderTypeStr.append(((CheckBox) vg_orderType_layout.getChildAt(integer)).getText() + "、");
            orderTempStr = orderTempStr + integer + ",";
        }

        if (orderTempStr.length() != 0)
            orderTempStr = orderTempStr.substring(0, orderTempStr.length() - 1);
        if (orderTypeStr.length() != 0)
            order_type.setText(orderTypeStr.toString().substring(0, orderTypeStr.toString().length() - 1));
    }

    private boolean canCommint() {
        for (int i = 0; i < vg_orderType_layout.getChildCount(); i++) {
            if (((CheckBox) vg_orderType_layout.getChildAt(i)).isChecked()) {
                return true;
            }
        }
        return false;
    }

    private StringBuffer orderTypeStr = new StringBuffer();
    private OrderSetting.DataBean orderSettingData;

    /**
     * 获取订单配置信息成功
     */
    @Override
    public void getOrderSettingSuccess(OrderSetting data) {
        orderSettingData = data.data;

//       距离spinner
        String distance = orderSettingData.distance;
        if (distance.equals("1")) {
            spinnerDistance.setSelection(0);
        } else if (distance.equals("3")) {
            spinnerDistance.setSelection(1);
        }

//设置接单类型数据
        setOrderData();

        if (orderSettingData.flush.equals("1")) {
            autoFlushSwitch.setChecked(true);
        } else autoFlushSwitch.setChecked(false);

        if (orderSettingData.push.equals("1")) {
            receiveOrderSwtich.setChecked(true);
        } else receiveOrderSwtich.setChecked(false);

        if (orderSettingData.type.equals("1")) {
            homeSwitch.setChecked(true);
        } else if (orderSettingData.type.equals("2")) {
            menuItemWorkSwitch.setChecked(true);
        } else if (orderSettingData.type.equals("3")) {
            homeSwitch.setChecked(false);
            menuItemWorkSwitch.setChecked(false);
        }

//        打开设置窗口
        if (!drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    /**
     * 保存订单设置成功回调
     */
    @Override
    public void saveOrderSettingSuccess() {
        canSave = false;
        orderTempStr = "";
        drawerLayout.closeDrawers();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.all:
                if (isChecked) {
                    chageCheckBoxIsFalse();
                }
                break;
            case R.id.waimai: //外买
                checkBoxChanged(isChecked);
                break;
            case R.id.daimai: //代买
                checkBoxChanged(isChecked);
                break;
            case R.id.daiban: //代办
                checkBoxChanged(isChecked);
                break;
            case R.id.cheyou: //车友
                checkBoxChanged(isChecked);
                break;
            case R.id.tongcheng: //同城
                checkBoxChanged(isChecked);
                break;

        }
    }

    /**
     * 改变checkbox
     */
    private void checkBoxChanged(boolean isChecked) {
        if (isChecked) {
            if (checkCBisAllCheck()) {//所有的checkBox都选中
                ((CheckBox) vg_orderType_layout.getChildAt(0)).setChecked(true);
                chageCheckBoxIsFalse();
            } else {
                ((CheckBox) vg_orderType_layout.getChildAt(0)).setChecked(false);
            }
        }
    }


    /**
     * 改变所有的checkbox为未选中
     */
    private void chageCheckBoxIsFalse() {
        for (int i = 1; i < vg_orderType_layout.getChildCount(); i++) {
            ((CheckBox) vg_orderType_layout.getChildAt(i)).setChecked(false);
        }
    }

    /**
     * 检查接单类型的checkbox是不是全部选中
     */
    public boolean checkCBisAllCheck() {
        for (int i = 1; i < vg_orderType_layout.getChildCount(); i++) {
            if (((CheckBox) vg_orderType_layout.getChildAt(i)).isChecked() == false)
                return false;
        }
        return true;
    }

    private void initViews() {
        drawerLayout = ((DrawerLayout) findViewById(R.id.order_competition_drawer_layout));
        topBar = ((TopBarView) findViewById(R.id.order_competition_top_bar));
        tabLayout = ((TabLayout) findViewById(R.id.order_competition_tab_layout));
        viewPager = ((ViewPager) findViewById(R.id.order_competition_view_pager));

        menuTopBar = ((TopBarView) findViewById(R.id.order_setting_top_bar));
        spinnerDistance = ((Spinner) findViewById(R.id.order_setting_spinner_distance));
        order_type = ((TextView) findViewById(R.id.order_type));
        receiveOrderSwtich = ((CheckBox) findViewById(R.id.order_setting_receive_order_switch));
        autoFlushSwitch = ((CheckBox) findViewById(R.id.order_setting_auto_flush_switch));
        homeSwitch = ((CheckBox) findViewById(R.id.order_setting_home_switch));
        menuItemWorkSwitch = ((CheckBox) findViewById(R.id.order_setting_work_switch));

//        接单类型
        orderTypeView = View.inflate(mContext, R.layout.view_order_type_choice, null);
        order_type_top_bar = ((TopBarView) orderTypeView.findViewById(R.id.order_type_top_bar));
        vg_orderType_layout = ((ViewGroup) orderTypeView.findViewById(R.id.vg_orderType_layout));
        cb_All = ((CheckBox) findViewById(R.id.all));
        menuLayout = ((ViewGroup) findViewById(R.id.order_competition_menu_setting));
        view = findViewById(R.id.populocationLab);
    }
}
