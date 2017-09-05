package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.baidumap.BaiduMapLocate;
import cn.bs.zjzc.baidumap.IndexOfUtils;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.bean.OrderParameters;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.dialog.TimeDialog;
import cn.bs.zjzc.dialog.TipMoneyDialog;
import cn.bs.zjzc.div.AutoRadioGroup;
import cn.bs.zjzc.div.CircleImageView;
import cn.bs.zjzc.div.FloatView;
import cn.bs.zjzc.div.HomeTopBarView;
import cn.bs.zjzc.model.bean.UploadFileBody;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.model.response.CarryMoneyResponse;
import cn.bs.zjzc.model.response.NearByRadarResponse;
import cn.bs.zjzc.model.response.OrderRespose;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.HomePresenter;
import cn.bs.zjzc.socket.service.SocketService;
import cn.bs.zjzc.ui.view.IHomeView;
import cn.bs.zjzc.util.DateTimeUtils;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.ViewUtils;

/**
 * Created by Ming on 2016/5/20.
 */
public class AvHome extends BaseActivity implements View.OnClickListener, IHomeView, OnGetGeoCoderResultListener, BDLocationListener {
    public static final int STATUS_INIT = 11;//初始状态
    public static final int STATUS_ORDER = 22;//下单状态
    public static final int STATUS_RECEIVE_ORDER = 33;//接单状态
    public static final int TYPE_DELIVER_FOOD = 1;//外卖服务
    public static final int TYPE_CITY_EXPRESS = 5;//同城速递
    public static final int TYPE_BUYING_SERVICE = 2;//代买服务
    public static final int TYPE_COMMISSION_SERVICE = 3;//代办服务
    public static final int TYPE_CAR_CLUB = 4;//车友之家

    private Context mContext = this;
    private HomePresenter mHomePresenter;

    private static int mStatus = STATUS_INIT;//当前状态
    private static int mType = TYPE_DELIVER_FOOD;//当前状态

    private DrawerLayout drawerLayout;
    private HomeTopBarView topBar;
    private AutoRadioGroup serviceSelectedBar;
    //侧滑菜单相关
    private AutoLinearLayout headerLayout;//菜单头部
    private CircleImageView headerImage;
    private TextView userName;
    private TextView level;
    private TextView userAccount;
    private TextView optionMyOrder;//我的订单
    private TextView optionReceivedOrder;//我的接单
    private TextView optionMyWallet;
    private TextView optionNewsCenter;
    private TextView optionMyEvaluation;//我的评价
    private TextView optionFeeTable;
    private TextView optionInviteFriend;
    private TextView optionSetting;
    //下单窗口相关
    private AutoLinearLayout orderWindow;//下单窗口布局
    private AutoLinearLayout timeLayout;//时间显示栏
    private TextView tvTime;//时间
    private AutoRelativeLayout addressLayout;//地址选择栏
    private TextView selectTakeAddress;//取
    private TextView selectDeliveryAddress;//送或者服务地址
    private ImageView btnSwapAddress;//地址转换按钮
    private AutoRelativeLayout tipLayout;//小费备注栏
    private TextView addTip;//添加小费
    private TextView addRemark;//添加备注
    private AutoRelativeLayout infoNameLayout;//配送费,距离显示栏的标题
    private AutoRelativeLayout infoValueLayout;//配送费,距离显示栏的值
    private TextView fee;//服务费
    private TextView distance;//距离
    private AutoRelativeLayout enterLayout;//商品名称输入,服务费输入栏
    private EditText enterShopName;//商品名称输入
    private EditText enterServiceFee;//服务费输入
    private ImageView btnSearchShop;//搜索商品
    private TextView addInsured;//保价
    private ImageView btnShow;//显示时间,小费备注栏
    private ImageView btnHide;//隐藏时间,小费备注栏
    private TextView btnConfirm;//下单确认
    //其他
    private TextView goReceiveOrder;//进入接单界面
    private FloatView btnSwapOrderStatus;//接单下单切换

    private ImageView btn_loc;//重新定位按钮

    //下单所需参数
    public static OrderParameters[] orderParametersS = new OrderParameters[5];
    public static ArrayList<String>[] photoPathLilst = new ArrayList[5];
    public List<int[]> iconList = new ArrayList<>();

    //小费部弹出框
    private TipMoneyDialog tipMoneyDialog;

    //时间选择器相关
    private TimeDialog timeDialog;

    //百度地图
    private MapView mMapView;
    private TextView marker_address;
    private BaiduMap mBaiduMap;
    private GeoCoder mSearch;
    //定位相关
    private LocationClient mLocClient;
    private boolean isFirstLoc = true;//是否首次定位
    private boolean needMove = true;//是否需要移动地图到定位点
    private String city;//当前定位城市

    //app版本号
    private int mVersionCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_home);
        initBase();
        initParams();

        initViews();
        initEvents();
        updateOrderWindowStatus();

        //获取省份和城市列表
        mHomePresenter.getProvinceAndCity();
        //获取客服电话
        mHomePresenter.getServicePhone();

        //百度地图相关
        initBaiduMap();

        //小费dialog
        initTipDialog();

        //时间选择器
        initTimeDialog();
    }

    private void initBase() {
        //开启socket服务
        startService(new Intent(mContext, SocketService.class));
    }

    private void initParams() {
        mHomePresenter = new HomePresenter(this);
        for (int i = 0; i < orderParametersS.length; i++) {
            orderParametersS[i] = new OrderParameters();
            photoPathLilst[i] = new ArrayList<>();
        }
        iconList.add(new int[]{R.mipmap.zjzc_marker_wm_orange, R.mipmap.zjzc_marker_wm_gray});
        iconList.add(new int[]{R.mipmap.zjzc_marker_dm_orange, R.mipmap.zjzc_marker_dm_gray});
        iconList.add(new int[]{R.mipmap.zjzc_marker_db_orange, R.mipmap.zjzc_marker_db_gray});
        iconList.add(new int[]{R.mipmap.zjzc_marker_car_orange, R.mipmap.zjzc_marker_car_gray});
        iconList.add(new int[]{R.mipmap.zjzc_marker_kd_orange, R.mipmap.zjzc_marker_kd_gray});

    }

    /**
     * 时间选择器
     */
    private void initTimeDialog() {
        timeDialog = new TimeDialog(mContext, new TimeDialog.TimeSelectListener() {
            @Override
            public void timeSelect(String time) {
                tvTime.setText(time);
                int index = mType - 1;
                try {
                    orderParametersS[index].setTake_time(DateTimeUtils.getDateToTime(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 小费弹框
     */
    private void initTipDialog() {
        tipMoneyDialog = new TipMoneyDialog(mContext, new TipMoneyDialog.TipMoneySelectListener() {
            @Override
            public void tipMoneySelect(String tipMoney) {
                int index = mType - 1;
                switch (mType) {
                    case 1:
                    case 5:
                        orderParametersS[index].setTip_money(tipMoney.replaceAll("元", ""));
                        break;
                }
                addTip.setText(tipMoney);
            }
        });
    }

    private void initEvents() {
        //topbar相关事件
        topBar.setMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        topBar.setSearchButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvFAQSearch.class);
                startActivity(intent);
            }
        });
        topBar.setActivityButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvActivity.class);
                startActivity(intent);
            }
        });
        //下单接单悬浮切换按钮点击事件
        btnSwapOrderStatus.setFloatViewClickListener(new FloatView.onFloatViewClickListener() {
            @Override
            public void onFloatViewClick() {
                //切换下单和接单界面
                if (mStatus != STATUS_RECEIVE_ORDER) {
                    mStatus = STATUS_RECEIVE_ORDER;
                } else {
                    mStatus = STATUS_INIT;
                    selectDeliveryAddress.setText(selectDeliveryAddress.getText().toString());
                    selectTakeAddress.setText(selectTakeAddress.getText().toString());
                }
                //更新下单窗口界面
                updateOrderWindowStatus();
                //重新定位获取周边附近的人
                needMove = true;
                mLocClient.requestLocation();
            }
        });
        //初始化服务选择相关事件
        serviceSelectedBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //更换服务类型
                switch (checkedId) {
                    case R.id.home_tab_delivery_food://外卖配送
                        mType = TYPE_DELIVER_FOOD;
                        break;
                    case R.id.home_tab_city_express://
                        mType = TYPE_CITY_EXPRESS;
                        break;
                    case R.id.home_tab_buying_service:
                        mType = TYPE_BUYING_SERVICE;
                        break;
                    case R.id.home_tab_commission_service:
                        mType = TYPE_COMMISSION_SERVICE;
                        break;
                    case R.id.home_tab_car_club:
                        mType = TYPE_CAR_CLUB;
                        break;
                }
                updateOrderWindowStatus();
                int index = mType - 1;
                int mmType = mType;
                orderParametersS[index].setType(mmType + "");
                updateOrderParametersView(orderParametersS[index]);
                //重新定位获取周边附近的人
                needMove = true;
                mLocClient.requestLocation();
            }
        });
        //menu相关事件
        headerLayout.setOnClickListener(this);
        optionMyOrder.setOnClickListener(this);
        optionReceivedOrder.setOnClickListener(this);
        optionMyWallet.setOnClickListener(this);
        optionNewsCenter.setOnClickListener(this);
        optionMyEvaluation.setOnClickListener(this);
        optionFeeTable.setOnClickListener(this);
        optionInviteFriend.setOnClickListener(this);
        optionSetting.setOnClickListener(this);
        //下单窗口相关事件
        selectTakeAddress.setOnClickListener(this);
        selectDeliveryAddress.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        addTip.setOnClickListener(this);
        addRemark.setOnClickListener(this);
        btnSearchShop.setOnClickListener(this);
        addInsured.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        //其他
        goReceiveOrder.setOnClickListener(this);
        //定位
        btn_loc.setOnClickListener(this);
    }

    private void initViews() {
        drawerLayout = ((DrawerLayout) findViewById(R.id.home_drawer_layout));
        topBar = (HomeTopBarView) findViewById(R.id.home_top_bar);
        serviceSelectedBar = ((AutoRadioGroup) findViewById(R.id.home_service_select_bar));
        orderWindow = (AutoLinearLayout) findViewById(R.id.home_order_window);

        headerLayout = ((AutoLinearLayout) findViewById(R.id.home_menu_option_personal_info));
        userName = ((TextView) findViewById(R.id.home_menu_user_name));
        level = ((TextView) findViewById(R.id.home_menu_user_level));
        userAccount = ((TextView) findViewById(R.id.home_menu_user_account));
        headerImage = ((CircleImageView) findViewById(R.id.home_menu_img_header));
        optionMyOrder = ((TextView) findViewById(R.id.home_menu_option_my_order));
        optionReceivedOrder = ((TextView) findViewById(R.id.home_menu_option_received_order));
        optionMyWallet = ((TextView) findViewById(R.id.home_menu_option_my_wallet));
        optionNewsCenter = ((TextView) findViewById(R.id.home_menu_option_news_center));
        optionMyEvaluation = ((TextView) findViewById(R.id.home_menu_option_my_evaluation));
        optionFeeTable = ((TextView) findViewById(R.id.home_menu_option_fee_table));
        optionInviteFriend = ((TextView) findViewById(R.id.home_menu_option_invite_friend));
        optionSetting = ((TextView) findViewById(R.id.home_menu_option_setting));


        timeLayout = ((AutoLinearLayout) findViewById(R.id.home_time_layout));
        tvTime = ((TextView) findViewById(R.id.home_tv_time));
        btn_loc = ((ImageView) findViewById(R.id.btn_loc));

        addressLayout = ((AutoRelativeLayout) findViewById(R.id.home_address_info_layout));

        selectTakeAddress = ((TextView) findViewById(R.id.home_select_take_address));
        selectTakeAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"要去哪儿取".equals(selectTakeAddress.getText().toString()) && !"要送去哪儿".equals(selectDeliveryAddress.getText().toString())) {
                    int index = mType - 1;
                    switch (mType) {
                        case 1:
                        case 5:
                            //获取运费和距离
                            if (orderParametersS[index].getTake_city_id() != null && orderParametersS[index].getTake_x() != null && orderParametersS[index].getTake_y() != null && orderParametersS[index].getReceipt_x() != null && orderParametersS[index].getReceipt_y() != null)
                                mHomePresenter.getCarreyMoney(mType + "", orderParametersS[index].getTake_city_id(), orderParametersS[index].getTake_x(), orderParametersS[index].getTake_y(), orderParametersS[index].getReceipt_x(), orderParametersS[index].getReceipt_y());
                            break;
                    }
                    mStatus = STATUS_ORDER;
                    //切换为下单窗口状态
                    updateOrderWindowStatus();
                    if (orderParametersS[mType - 1].getTip_money() != null || orderParametersS[mType - 1].getRemark_txt() != null) {//添加消费和备注
                        ViewUtils.showViews(tipLayout);
                    }
                } else {
                    //切换为初始窗口状态
                    mStatus = STATUS_INIT;
                    updateOrderWindowStatus();
                }
            }
        });

        selectDeliveryAddress = ((TextView) findViewById(R.id.home_select_delivery_address));
        selectDeliveryAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean flag = false;
                switch (mType) {
                    case 1:
                    case 5:
                        flag = !"要送去哪儿".equals(selectDeliveryAddress.getText().toString()) && !"要去哪儿取".equals(selectTakeAddress.getText().toString());
                        break;
                    case 2:
                    case 3:
                    case 4:
                        flag = !"要送去哪儿".equals(selectDeliveryAddress.getText().toString());
                        break;
                    default:

                        break;
                }
                L.d("GMC", ",flag = " + flag + ",mType =" + mType + ",要送去哪儿delivery =" + selectDeliveryAddress.getText().toString() + ",要去哪儿取take = " + selectTakeAddress.getText().toString());
                if (flag) {
                    mStatus = STATUS_ORDER;
                    //切换为下单窗口状态
                    updateOrderWindowStatus();
                } else {
                    //切换为初始窗口状态
                    mStatus = STATUS_INIT;
                    updateOrderWindowStatus();
                }
            }
        });

        btnSwapAddress = ((ImageView) findViewById(R.id.home_swap_address));
        btnSwapAddress.setOnClickListener(this);

        tipLayout = ((AutoRelativeLayout) findViewById(R.id.home_tip_layout));
        addTip = ((TextView) findViewById(R.id.home_add_tip));
        addRemark = ((TextView) findViewById(R.id.home_add_remark));

        infoNameLayout = ((AutoRelativeLayout) findViewById(R.id.home_info_name_layout));
        infoValueLayout = ((AutoRelativeLayout) findViewById(R.id.home_info_value_layout));
        fee = ((TextView) findViewById(R.id.home_fee));
        distance = ((TextView) findViewById(R.id.home_distance));

        enterLayout = ((AutoRelativeLayout) findViewById(R.id.home_enter_layout));
        enterShopName = ((EditText) findViewById(R.id.home_enter_shop_name));
        enterShopName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int index = mType - 1;
                switch (mType) {
                    case 2:
                        if (!"".equals(s.toString().trim())) {
                            orderParametersS[index].setGoods_name(s.toString().trim());
                        } else {
                            orderParametersS[index].setGoods_name(null);
                        }
                        break;
                    case 3:
                    case 4:
                        if (!"".equals(s.toString().trim())) {
                            orderParametersS[index].setService_name(s.toString().trim());
                        } else {
                            orderParametersS[index].setService_name(null);
                        }
                        break;
                }
            }
        });
        enterServiceFee = ((EditText) findViewById(R.id.home_enter_service_fee));
        enterServiceFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int index = mType - 1;
                if (!"".equals(s.toString().trim())) {
                    orderParametersS[index].setService_money(s.toString().trim());
                } else {
                    orderParametersS[index].setService_money(null);
                }
            }
        });
        btnSearchShop = ((ImageView) findViewById(R.id.home_search_shop));

        addInsured = ((TextView) findViewById(R.id.home_add_insured));

        btnShow = ((ImageView) findViewById(R.id.home_btn_show));
        btnHide = ((ImageView) findViewById(R.id.home_btn_hide));
        btnConfirm = ((TextView) findViewById(R.id.home_password_confirm));
        goReceiveOrder = ((TextView) findViewById(R.id.home_go_receive_order));
        btnSwapOrderStatus = ((FloatView) findViewById(R.id.home_swap_order_status));

        ((RadioButton) serviceSelectedBar.getChildAt(0)).setChecked(true);
    }

    /**
     * 更新窗口接单下单。。。状态
     */
    public void updateOrderWindowStatus() {
        //判断是否为接单状态,是则隐藏下单窗口和服务选择栏,显示进入接单界面按钮.
        if (mStatus == STATUS_RECEIVE_ORDER) {
            ViewUtils.hideViews(orderWindow, serviceSelectedBar);
            ViewUtils.showViews(goReceiveOrder);
            return;
        }

        //不是接单状态,则显示下单窗口和服务选择栏,隐藏进入接单界面按钮.
        ViewUtils.hideViews(goReceiveOrder);
        ViewUtils.showViews(orderWindow, serviceSelectedBar);

        //初始状态操作
        if (mStatus == STATUS_INIT) {
            switch (mType) {
                case TYPE_DELIVER_FOOD:
                case TYPE_CITY_EXPRESS:
                    ViewUtils.hideAll(orderWindow);
                    ViewUtils.showViews(addressLayout);
                    ViewUtils.showAll(addressLayout);
                    ViewUtils.hideViews(btnShow);
                    break;
                case TYPE_BUYING_SERVICE:
                case TYPE_COMMISSION_SERVICE:
                case TYPE_CAR_CLUB:
                    ViewUtils.hideAll(orderWindow);
                    ViewUtils.hideAll(addressLayout);
                    ViewUtils.showViews(selectDeliveryAddress);
                    if (mType == TYPE_BUYING_SERVICE) {
//                        selectDeliveryAddress.setText("要送去哪儿");
                        selectDeliveryAddress.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_home_icon_delivery, 0, 0, 0);
                    } else {
//                        selectDeliveryAddress.setText("去哪儿服务");
                        selectDeliveryAddress.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_home_icon_service, 0, 0, 0);
                    }
                    break;
            }
            return;
        }
        //下单状态操作
        if (mStatus == STATUS_ORDER) {
            switch (mType) {
                case TYPE_DELIVER_FOOD:
                case TYPE_CITY_EXPRESS:
                    ViewUtils.hideAll(orderWindow);
                    ViewUtils.showAll(addressLayout);
                    ViewUtils.showViews(infoNameLayout, infoValueLayout, btnConfirm);
                    if (mType == TYPE_CITY_EXPRESS) {
                        ViewUtils.showViews(addInsured);
                    }
                    break;
                case TYPE_BUYING_SERVICE:
                case TYPE_COMMISSION_SERVICE:
                case TYPE_CAR_CLUB:
                    ViewUtils.hideAll(orderWindow);
                    ViewUtils.hideAll(addressLayout);
                    ViewUtils.hideViews(btnHide);
                    ViewUtils.showViews(timeLayout, selectDeliveryAddress, enterLayout, addInsured, btnConfirm);
//                    selectDeliveryAddress.setText("要送去哪儿");
                    selectDeliveryAddress.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_home_icon_delivery, 0, 0, 0);
                    break;
            }
            return;
        }
    }

    //若菜单打开按返回键关闭菜单
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //侧滑菜相关事件
            case R.id.home_menu_option_personal_info:
                startActivity(new Intent(mContext, AvPersonalInfo.class));
                break;
            case R.id.home_menu_option_my_order:
                Intent myOrderIntent = new Intent(mContext, AvMyOrder.class);
                myOrderIntent.putExtra("type", "2");
                startActivity(myOrderIntent);
                break;
            case R.id.home_menu_option_received_order:
                Intent myReceivedOrderIntent = new Intent(mContext, AvMyOrder.class);
                myReceivedOrderIntent.putExtra("type", "1");
                startActivity(myReceivedOrderIntent);
                break;
            case R.id.home_menu_option_my_wallet:
                startActivity(new Intent(mContext, AvMyWallet.class));
                break;
            case R.id.home_menu_option_news_center:
                startActivity(new Intent(mContext, AvNewsCenter.class));
                break;
            case R.id.home_menu_option_my_evaluation:
                startActivity(new Intent(mContext, AvMyEvaluation.class));
                break;
            case R.id.home_menu_option_fee_table:
                Intent feeTableIntent = new Intent(mContext, AvCommdWebView.class);
                feeTableIntent.putExtra("url", RequestUrl.getRequestPath(RequestUrl.SubPaths.charge_list));
                feeTableIntent.putExtra("title", "收费表");
                String[] cityInfo = App.LOGIN_USER.getCityInfo();
                feeTableIntent.putExtra("titleRight", cityInfo[1]);
                startActivity(feeTableIntent);
                break;
            case R.id.home_menu_option_invite_friend:
                startActivity(new Intent(mContext, AvInviteFriend.class));
                break;
            case R.id.home_menu_option_setting:
                startActivity(new Intent(mContext, AvSetting.class));
                break;
            //下单窗口相关事件
            case R.id.home_select_take_address://要去哪儿取requestCode=1
                Intent intentTake = new Intent();
                String address = null;
                int indexs = mType - 1;
                switch (mType) {
                    case 1:
                    case 5:
                        address = orderParametersS[indexs].getTake_address();
                        break;
                }
                if (address != null) {
                    intentTake.setClass(mContext, AvInfoComplement.class);
                    intentTake.putExtra("address", address);
                } else {
                    intentTake.setClass(mContext, AvInputAddress.class);
                }
                intentTake.putExtra("type", 1);
                intentTake.putExtra("mType", mType);
                startActivity(intentTake);
                break;
            case R.id.home_select_delivery_address://要送去哪儿requestCode=2
                int index = mType - 1;
                Intent intentSend = new Intent();
                String addressSend = orderParametersS[index].getReceipt_address();
                if (addressSend != null) {
                    intentSend.setClass(mContext, AvInfoComplement.class);
                    intentSend.putExtra("address", addressSend);
                } else {
                    intentSend.setClass(mContext, AvInputAddress.class);
                }
                intentSend.putExtra("type", 2);
                intentSend.putExtra("mType", mType);
                startActivity(intentSend);
                break;
            case R.id.home_add_tip://添加小费
                tipMoneyDialog.show();
                break;
            case R.id.home_tv_time://取货时间
                timeDialog.show();
                break;
            case R.id.home_add_remark://添加备注和图片
                Intent intent = new Intent(mContext, AvAddRemark.class);
                int indexx = mType - 1;
                intent.putExtra("remark_content", orderParametersS[indexx].getRemark_txt());
                intent.putStringArrayListExtra("photo", photoPathLilst[indexx]);
                startActivityForResult(intent, 3);
                break;
            case R.id.home_search_shop:
                startActivity(new Intent(mContext, AvFAQSearch.class));
                break;
            case R.id.home_add_insured://添加保价
                int indexxx = mType - 1;
                if (mType == 5) {//添加保价
                    Intent intentInsured = new Intent(mContext, AvAddInsured.class);
                    intentInsured.putExtra("mType", mType);
                    intentInsured.putExtra("insured_money", orderParametersS[indexxx].getInsured_money());
                    startActivity(intentInsured);
                } else {//详情说明&照片添加
                    Intent intentDetail = new Intent(mContext, AvAddRemark.class);
                    intentDetail.putExtra("remark_content", orderParametersS[indexxx].getRemark_txt());
                    intentDetail.putStringArrayListExtra("photo", photoPathLilst[indexxx]);
                    startActivityForResult(intentDetail, 3);
                }
                break;
            case R.id.home_password_confirm://确定下单
                int indexxxx = mType - 1;
                order(orderParametersS[indexxxx], photoPathLilst[indexxxx]);
                break;
            case R.id.home_btn_show:
                ViewUtils.showViews(timeLayout, tipLayout, btnHide);
                ViewUtils.hideViews(btnShow);
                break;
            case R.id.home_btn_hide:
                ViewUtils.hideViews(timeLayout, tipLayout, btnHide);
                ViewUtils.showViews(btnShow);
                break;
            //其他
            case R.id.home_go_receive_order:
                startActivity(new Intent(mContext, AvOrderCompetition.class));
                break;
            //地址切换按钮
            case R.id.home_swap_address:
                exchangeWayView();
                break;
            //地图重新定位
            case R.id.btn_loc:
                needMove = true;
                mLocClient.requestLocation();
                break;
        }
    }

    /**
     * (取货、送货)地址交换
     */
    public void exchangeWayView() {
        String send = selectDeliveryAddress.getText().toString();//要送去哪儿
        String get = selectTakeAddress.getText().toString();//去哪儿取
        if (!"".equals(send) || send != null) {
            if ("要送去哪儿".equals(send)) {
                selectTakeAddress.setText("要去哪儿取");
            } else {
                selectTakeAddress.setText(send);
            }
        }
        if (!"".equals(get) || get != null) {
            if ("要去哪儿取".equals(get)) {
                selectDeliveryAddress.setText("要送去哪儿");
            } else {
                selectDeliveryAddress.setText(get);
            }
        }
        int indexp = mType - 1;
        switch (mType) {
            case 1:
            case 5:
                exchange(orderParametersS[indexp]);
                break;
        }

    }

    public void exchange(OrderParameters params) {
        String address = params.getReceipt_address();
        String x = params.getReceipt_x();
        String y = params.getReceipt_y();
        String provinceId = params.getReceipt_province_id();
        String cityId = params.getReceipt_city_id();

        params.setReceipt_address(params.getTake_address());
        params.setReceipt_x(params.getTake_x());
        params.setReceipt_y(params.getTake_y());
        params.setReceipt_province_id(params.getTake_province_id());
        params.setReceipt_city_id(params.getTake_city_id());

        params.setTake_address(address);
        params.setTake_x(x);
        params.setTake_y(y);
        params.setTake_province_id(provinceId);
        params.setTake_city_id(cityId);
    }

    /**
     * 检查版本
     */
    private void checkVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            mVersionCode = packageInfo.versionCode;
            //获取版本信息
            mHomePresenter.getAppVersionInfo();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下单
     *
     * @param params    参数
     * @param photoList 图片
     */
    private void order(OrderParameters params, List<String> photoList) {
        params.setType(mType + "");
        params.setToken(App.LOGIN_USER.getToken());
        if (photoList != null && photoList.size() != 0) {
            Map<String, UploadFileBody> map = new HashMap<>();
            String[] picName = new String[]{"pic1", "pic2", "pic3", "pic4"};
            for (int i = 0; i < photoList.size(); i++) {
                map.put(picName[i], new UploadFileBody(photoList.get(i), new File(photoList.get(i))));
            }
            mHomePresenter.order(map, params);
        } else {
            mHomePresenter.order(null, params);
        }
    }

    /**
     * onResume()
     */
    public void updateOrderParametersView(OrderParameters params) {
        fee.setText("0");
        distance.setText("0");
        tvTime.setText(params.getTake_time() == null ? "现在" : DateTimeUtils.getTimeToDate(params.getTake_time()));
        selectDeliveryAddress.setText(params.getReceipt_address() == null ? "要送去哪儿" : params.getReceipt_address());
        switch (mType) {
            case 1://外卖
                selectTakeAddress.setText(params.getTake_address() == null ? "要去哪儿取" : params.getTake_address());
                addTip.setText(params.getTip_money() == null ? "加小费" : params.getTip_money());
                addRemark.setText(params.getRemark_txt() == null ? "添加备注/照片说明" : params.getRemark_txt());
                if (params.getTip_money() != null || params.getRemark_txt() != null) {
                    ViewUtils.showViews(tipLayout);
                }
                break;
            case 2://代买
                enterShopName.setText(params.getGoods_name() == null ? "" : params.getGoods_name());//商品名称
                enterServiceFee.setText(params.getService_money() == null ? "" : params.getService_money());//服务费
                addInsured.setText(params.getRemark_txt() == null ? "详情说明/照片添加" : params.getRemark_txt());
                addInsured.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_add_insured, 0, 0, 0);
                break;
            case 3://代办
            case 4://车友之家
                enterShopName.setText(params.getService_name() == null ? "" : params.getService_name());//服务名称
                enterServiceFee.setText(params.getService_money() == null ? "" : params.getService_money());//服务费
                addInsured.setText(params.getRemark_txt() == null ? "详情说明/照片添加" : params.getRemark_txt());
                addInsured.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_add_insured, 0, 0, 0);
                break;
            case 5://同城
                selectTakeAddress.setText(params.getTake_address() == null ? "要去哪儿取" : params.getTake_address());
                addTip.setText(params.getTip_money() == null ? "加小费" : params.getTip_money());
                addRemark.setText(params.getRemark_txt() == null ? "添加备注/照片说明" : params.getRemark_txt());
                addInsured.setText(params.getInsured_money() == null ? "添加保价" : params.getInsured_money() + "元");
                if (params.getInsured_money() == null) {
                    addInsured.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_add_insured, 0, 0, 0);
                } else {
                    addInsured.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_add_insured_rmb, 0, 0, 0);
                }
                if (params.getTip_money() != null || params.getRemark_txt() != null) {
                    ViewUtils.showViews(tipLayout);
                }
                break;
        }
    }

    /**
     * 成功获取运费和距离 更新UI
     */
    @Override
    public void updateCarryMoneyView(CarryMoneyResponse.DataBean data) {
        L.d("MGC", "运费和距离：" + data.toString());
        fee.setText(data.carry_money);///配送费
        distance.setText(data.distance);//距离
    }

    /**
     * 下单成功
     *
     * @param data
     */
    @Override
    public void orderSuccessView(OrderRespose.DataBean data) {
        //清空数据重新下单
        int index = mType - 1;
        orderParametersS[index] = new OrderParameters();
        //调整到支付界面
        Intent intent = new Intent(mContext, AvOrderPay.class);
        intent.putExtra("order_id", data.order_id);
        intent.putExtra("mType", mType);
        startActivity(intent);
    }

    /**
     * 成功获取所有开通服务的省份和城市列表
     *
     * @param data
     */
    @Override
    public void getProvinceAndCitySuccess(List<ProvinceCityListResponse.DataBean> data) {
        App.LOGIN_USER.setProvinceCityList(data);
        //获取到所有开通服务的省份和城市以及id之后进行定位
        new BaiduMapLocate(mLocClient).startLoc();
        L.d("LOC", "getProvinceAndCitySuccess");
    }

    /**
     * 获取版本信息成功回调
     */
    @Override
    public void getVersionInfoSuccess(AppVersionInfo data) {
        int verSionCode = (int) Double.parseDouble(data.data.num);
        if (verSionCode > mVersionCode) {  //有新版本 弹版本更新
            Intent intent = new Intent(getApplicationContext(), AvAppUpdate.class);
            intent.putExtra("AppVersionInfo", data);
            startActivityForResult(intent, 2);
        } else {//没新版本 弹广告
            if (city != null && !"".equals(city)) {
                Intent intent = new Intent(mContext, AvAdvertisement.class);
                intent.putExtra("cityName", city);
                startActivity(intent);
            }
        }
    }

    /**
     * 周边雷达
     *
     * @param data
     */
    @Override
    public void getNearByRadarView(NearByRadarResponse data) {
        if (data.data == null && data.data.size() == 0) {
            return;
        }
        L.d("NearBy", "getNearByRadarView start" + data);
        mBaiduMap.clear();
        for (int i = 0; i < data.data.size(); i++) {
            addMarker(new LatLng(Double.parseDouble(data.data.get(i).x), Double.parseDouble(data.data.get(i).y)), Integer.parseInt(data.data.get(i).state) - 1);
        }
    }

    /**
     * 百度地图相关
     */
    private void initBaiduMap() {
        marker_address = (TextView) findViewById(R.id.marker_address);
        mMapView = (MapView) findViewById(R.id.mapView);
        // 删除百度地图LoGo
        mMapView.removeViewAt(1);
        // 删除缩放按钮
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(this);
        /*初始化搜索模块，注册事件监听*/
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                marker_address.setText("正在获取位置信息...");
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                LatLng ptCenter = mBaiduMap.getMapStatus().target; //获取地图中心点坐标
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {

            }
        });
    }

    /**
     * 百度地图定位回调
     *
     * @param location
     */
    @Override
    public void onReceiveLocation(BDLocation location) {
        L.d("LOC", "onReceiveLocation");
        if (location == null) {
            //定位失败
            return;
        }
        if (needMove) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            needMove = false;
        }
        if (!isFirstLoc) {
            //获取周边接单人
            int type = mStatus == STATUS_ORDER ? 2 : mStatus == STATUS_RECEIVE_ORDER ? 1 : 2;//接单下单状态
            String order_type = type == 2 ? mType + "" : null;
            mHomePresenter.getNearByRadar(type, order_type, location.getLatitude() + "", location.getLongitude() + "");
            return;
        }
        //设置当前定位城市id和名称
        if (App.LOGIN_USER.getCityInfo() == null && App.LOGIN_USER.getProvinceCityList().size() != 0) {
            String[] ids = IndexOfUtils.setProvinceAndCityId(location.getProvince(), location.getCity(), false);
            App.LOGIN_USER.setCityInfo(new String[]{ids[1], location.getCity().replace("市", "")});
        }
        city = location.getCity().replace("市", "");
        //检查版本
        checkVersion();
        //获取周边接单人
        int type = mStatus == STATUS_ORDER ? 2 : mStatus == STATUS_RECEIVE_ORDER ? 1 : 2;//接单下单状态
        String order_type = type == 2 ? mType + "" : null;
        mHomePresenter.getNearByRadar(type, order_type, location.getLatitude() + "", location.getLongitude() + "");
        isFirstLoc = false;
    }

    /**
     * 在地图上添加marker
     *
     * @param point 坐标
     * @param resId marker图标 iconList.get(mType - 1)[resId]
     * @return
     */
    private Marker addMarker(LatLng point, int resId) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(iconList.get(mType - 1)[resId]);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        return (Marker) mBaiduMap.addOverlay(option);
    }

    /**
     * 百度地图反地理编码结果
     *
     * @param result
     */

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        //设置地图中心点坐标
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(result.getLocation());
        mBaiduMap.animateMapStatus(status);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AvHome.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        List<PoiInfo> list = result.getPoiList();
        String address = "";
        if (list != null && list.size() != 0) {
            address = list.get(0).name;
        } else {
            address = result.getAddress();
        }
        if ("".equals(address)) {
            address = result.getAddress();
        }
        marker_address.setText(address);
        if (mStatus != STATUS_RECEIVE_ORDER) {//接单状态
            String[] ids = IndexOfUtils.setProvinceAndCityId(result.getAddressDetail().province, result.getAddressDetail().city, false);
            int index = mType - 1;
            switch (mType) {
                case 1://外卖
                case 5://同城
                    selectTakeAddress.setText(address);
                    orderParametersS[index].setTake_address(address);
                    orderParametersS[index].setTake_x(result.getLocation().latitude + "");
                    orderParametersS[index].setTake_y(result.getLocation().longitude + "");
                    orderParametersS[index].setTake_province_id(ids[0]);
                    orderParametersS[index].setTake_city_id(ids[1]);
                    break;
                case 2://代买
                case 3://代办
                case 4://车友
                    selectDeliveryAddress.setText(address);
                    orderParametersS[index].setReceipt_address(address);
                    orderParametersS[index].setReceipt_x(result.getLocation().latitude + "");
                    orderParametersS[index].setReceipt_y(result.getLocation().longitude + "");
                    orderParametersS[index].setReceipt_province_id(ids[0]);
                    orderParametersS[index].setReceipt_city_id(ids[1]);
                    break;
            }
        }
    }

    /**
     * 实现地图生命周期管理
     */
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        Picasso.with(mContext).load(App.LOGIN_USER.getHead_img()).into(headerImage);
        userName.setText(App.LOGIN_USER.getName());
        userAccount.setText(App.LOGIN_USER.getAccount());
        level.setText(App.LOGIN_USER.getLevel());
        int index = mType - 1;
        updateOrderParametersView(orderParametersS[index]);
        L.d("OrderParameters", orderParametersS[index].toString());
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3://添加照片和备注
                    int index = mType - 1;
                    String remark_content = data.getStringExtra("remark_content");
                    if ("".equals(remark_content.trim())) {
                        orderParametersS[index].setRemark_txt(null);
                    } else {
                        orderParametersS[index].setRemark_txt(remark_content);
                    }
                    photoPathLilst[index].clear();
                    photoPathLilst[index].addAll(data.getStringArrayListExtra("photo"));
                    break;
                case 2://弹完版本更新后弹广告
                    if (city != null && !"".equals(city)) {
                        Intent intent = new Intent(mContext, AvAdvertisement.class);
                        intent.putExtra("cityName", city);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }
}
