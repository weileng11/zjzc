package cn.bs.zjzc.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.Date;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.dialog.MyDialog;
import cn.bs.zjzc.div.Stars;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.OrderDetailPresenter;
import cn.bs.zjzc.ui.view.IOrderDetailView;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.T;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/5/31.
 */
public class AvOrderDetail extends BaseActivity implements IOrderDetailView, View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CALLPHONE = 0;
    private static final int CANCEL_CODE = 100;
    private TextView orderNumber;
    private TextView time;
    private TextView type;
    private TextView money;
    private TextView addressTake;
    private TextView floorInfoTake;
    private TextView contactsTake;
    private TextView checkAddressTake;
    private TextView distance;
    private TextView addressDelivery;
    private TextView floorInfoDelivery;
    private TextView checkAddressDelivery;
    private TextView contactsDelivery;
    private AutoRelativeLayout tipLayout;
    private TextView tip;
    private TextView preferential;
    private TextView realPay;
    private TextView insuredFee;
    private AutoLinearLayout remarkLayout;
    private TextView remark;
    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;
    private ImageView photo4;
    private TextView orderTime;
    private AutoLinearLayout receiveTimeLayout;
    private TextView receiveTime;
    private AutoLinearLayout takeTimeLayout;
    private TextView takeTime;
    private AutoLinearLayout doneTimeLayout;
    private TextView doneTime;
    private AutoRelativeLayout contactOrderUserLayout;
    private TextView contactOrderUser;
    private Stars stars;
    private AutoRelativeLayout confirmLayout;
    private TextView confirmReceipt;
    private TextView cancelOrder;
    private AutoFrameLayout evaluationLayout;
    private TextView evaluation;
    private TextView orderStatus;

    private OrderDetailPresenter mOrderDetailPresenter;
    private OrderDetail orderDetail;
    private ViewGroup order_detail_photoLayout;
    private ViewGroup order_detail_address_takeLayout;
    private View line;
    private TextView order_detail_service_lab;
    private View payIcon;
    private String orderType; // 1:表示我的接单  2：表示我的订单
    private ViewGroup order_detail_contact_userLayout;
    private Context mContext = this;
    private OrderDetail.DataBean dataBean;
    private String order_id;
    private TextView order_detail_contact_user;
    private TopBarView topBarView;
    private MyDialog completedOrderDailog;  //完成订单弹窗
    private long feerCancelTime;  //可以免费取消订单的范围
    private ImageView iv_payTypeIcon;
    private ImageView iv_payTypeIcon2;
    private ViewGroup order_detail_delivery_layout1;
    private MyDialog cancelDailog; //取消订单弹窗

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_order_detail);
        initViews();
        initBasic();
        initEvent();
    }

    /**
     * 点击事件
     */
    private void initEvent() {
        topBarView.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(mContext, "帮助");
            }
        });

        contactsTake.setOnClickListener(this);
        contactsDelivery.setOnClickListener(this);
        cancelOrder.setOnClickListener(this);
        order_detail_contact_user.setOnClickListener(this);
        contactOrderUser.setOnClickListener(this);

        checkAddressTake.setOnClickListener(this); //取货地址
        checkAddressDelivery.setOnClickListener(this); //取货地址

    }

    private void initBasic() {
        orderType = getIntent().getStringExtra("type");
        order_id = getIntent().getStringExtra("order_id");
        mOrderDetailPresenter = new OrderDetailPresenter(this);
        mOrderDetailPresenter.getOrderDetail(orderType, order_id);
    }

    @Override
    public void showData(OrderDetail orderDetail) {
        toShowDataView(orderDetail);
    }


    private void toShowDataView(OrderDetail orderDetail) {
        orderTime.setVisibility(View.VISIBLE);
        receiveTimeLayout.setVisibility(View.VISIBLE);
        takeTimeLayout.setVisibility(View.VISIBLE);
        doneTimeLayout.setVisibility(View.VISIBLE);
        contactOrderUserLayout.setVisibility(View.VISIBLE);
        confirmLayout.setVisibility(View.VISIBLE);
        evaluationLayout.setVisibility(View.VISIBLE);

        this.orderDetail = orderDetail;
        dataBean = orderDetail.data;
//        测试数据
//        orderType = "1";  // 1:表示我的接单  2：表示我的订单
//        dataBean.state = "3";  //状态（1 待接单，2 待取货，3 进行中，4 待收货，5 已完成 ，6 下单取消，7 接单取消，8 待付款 9 接单用户确认取货，10 下单用户确认取货， 11 系统取消
//        dataBean.type = "3";  //1 外卖，2 代买，3 代办 , 4 车友，5 速递）

        orderNumber.setText("订单：" + dataBean.order_num);
        orderStatus.setText("状态：" + getOrderState(Integer.parseInt(dataBean.state)));
//        下单时间
        String place_time = getTimeString(dataBean.take_time);
        time.setText(place_time);

        type.setText(getOrderType(dataBean.type));  //订单类型

//        判断是否是代买、代办和车友之家服务
        if (dataBean.type.equals("2")) {
            order_detail_address_takeLayout.setVisibility(View.GONE);
            order_detail_delivery_layout1.setPadding(50, 25, 50, 25);
            line.setVisibility(View.VISIBLE);
            order_detail_service_lab.setVisibility(View.VISIBLE);
            order_detail_service_lab.setText("商品名称:" + dataBean.goods_name);
        } else if (dataBean.type.equals("3") || dataBean.type.equals("4")) {
            order_detail_address_takeLayout.setVisibility(View.GONE);
            order_detail_delivery_layout1.setPadding(50, 25, 50, 25);
            line.setVisibility(View.VISIBLE);
            order_detail_service_lab.setVisibility(View.VISIBLE);
            order_detail_service_lab.setText("服务名称:" + dataBean.service_name);
        }


//       总金额
        double allMoney = Double.parseDouble(dataBean.money) + Double.parseDouble(dataBean.coupon_money);
        money.setText(allMoney + "元");
//        取货地址
        addressTake.setText(dataBean.take_address);
        floorInfoTake.setText(dataBean.take_add_address);
        contactsTake.setText("联系" + dataBean.take_name);
        distance.setText("相距" + dataBean.distance + "KM");
//        送货地址
        addressDelivery.setText(dataBean.receipt_address);
        floorInfoDelivery.setText(dataBean.receipt_add_address);
        contactsDelivery.setText("联系" + dataBean.receipt_name);


        if (Double.parseDouble(dataBean.tip_money) > 0 || Double.parseDouble(dataBean.coupon_money) > 0) {
            //小费
            if (Double.parseDouble(dataBean.tip_money) > 0) {
                tip.setText("小费" + dataBean.tip_money + "元");
            } else {
                tip.setVisibility(View.GONE);
            }

//        优惠
            if (Double.parseDouble(dataBean.coupon_money) > 0) {
                preferential.setText("优惠" + dataBean.coupon_money + "元");
            } else {
                preferential.setVisibility(View.GONE);
            }
        } else {
            payIcon.setVisibility(View.GONE);
            tip.setVisibility(View.GONE);
            preferential.setVisibility(View.GONE);
        }

//        保价
        if (Double.parseDouble(dataBean.insured_money) > 0) {
            insuredFee.setText("保价费：" + dataBean.insured_money + "元");
        } else {
            insuredFee.setVisibility(View.GONE);
        }
// 支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）
        if (dataBean.pay_type.equals("1")) {
            iv_payTypeIcon.setBackgroundResource(R.mipmap.zjzc_icon_alipay2);
        } else if (dataBean.pay_type.equals("2")) {
            iv_payTypeIcon.setBackgroundResource(R.mipmap.zjzc_icon_wechat_pay);
        } else if (dataBean.pay_type.equals("3")) {
            iv_payTypeIcon.setBackgroundResource(R.mipmap.zjzc_icon_account_pay);
        } else if (dataBean.pay_type.equals("4")) {
            iv_payTypeIcon.setBackgroundResource(R.mipmap.zjzc_icon_alipay2);
            iv_payTypeIcon2.setVisibility(View.VISIBLE);
            iv_payTypeIcon2.setBackgroundResource(R.mipmap.zjzc_icon_account_pay);
        } else if (dataBean.pay_type.equals("5")) {
            iv_payTypeIcon.setBackgroundResource(R.mipmap.zjzc_icon_wechat_pay);
            iv_payTypeIcon2.setVisibility(View.VISIBLE);
            iv_payTypeIcon2.setBackgroundResource(R.mipmap.zjzc_icon_account_pay);
        }


//实付
        String monyStr = "实付：<font color=\"#ff8704\" >" + Double.parseDouble(dataBean.money) + "</font>元";
        realPay.setText(Html.fromHtml(monyStr));
        contactOrderUser.setText("接单：" + dataBean.other_user.name);

//        备注
        String remarkStr = TextUtils.isEmpty(dataBean.remark_txt) ? "备注：无" : "备注：" + dataBean.remark_txt;
        remark.setText(remarkStr);

//        备注下的照片
        for (int i = 0; i < order_detail_photoLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) order_detail_photoLayout.getChildAt(i);
            imageView.setVisibility(View.INVISIBLE);
            if (dataBean.remark_img.size() - 1 >= i) {
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(this).load(RequestUrl.getImgPath(dataBean.remark_img.get(i))).into(imageView);
            }
        }
        if (dataBean.remark_img.size() == 0) {
            order_detail_photoLayout.setVisibility(View.GONE);
        }

//        下单时间
        orderTime.setText("下单时间：" + getTimeString(dataBean.place_time)); //下单时间

        //  取消订单按钮倒计时时间
        long takeTimeAgo = (System.currentTimeMillis() / 1000) - Long.parseLong(dataBean.take_order_time);//距离接单时间过去了多久
        double can_cancel_time = Double.parseDouble(dataBean.can_cancel_time);
        feerCancelTime = (long) (can_cancel_time * 60);  //可以取消订单的时间范围   单位：秒


//        根据订单状态来控制底部控件的显示和隐藏
        switch (Integer.parseInt(dataBean.state)) {
            case 1:   //待接单
                receiveTimeLayout.setVisibility(View.GONE);//接单时间
                takeTimeLayout.setVisibility(View.GONE);//取货时间
                doneTimeLayout.setVisibility(View.GONE);//完成时间

                confirmLayout.setVisibility(View.GONE); //去付款
                contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                evaluation.setText("取消订单");
                break;

            case 2:  //待取货
                receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
                takeTimeLayout.setVisibility(View.GONE);//取货时间
                doneTimeLayout.setVisibility(View.GONE);//完成时间

                if (orderType.equals("1")) { //我的接单
                    order_detail_contact_userLayout.setVisibility(View.VISIBLE); //显示联系下单用户
                    contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                    evaluationLayout.setVisibility(View.GONE);
                    confirmReceipt.setText("已到达，我要取货");
                    if (dataBean.type.equals("2") || dataBean.type.equals("3") || dataBean.type.equals("4")) {
                        confirmReceipt.setText("完成订单");
                    }
                } else if (orderType.equals("2")) { //我的订单
                    if (dataBean.type.equals("1") || dataBean.type.equals("5")) {
                        confirmReceipt.setText("我要确认取货");
                        contactOrderUser.setText("接单：" + dataBean.other_user.name);
                        if (!TextUtils.isEmpty(dataBean.other_user.phone))
                            stars.setStars((int) Float.parseFloat(dataBean.other_user.star)); //设置评星
                    } else {
                        contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                        confirmReceipt.setText("付款");
                    }
                    evaluationLayout.setVisibility(View.GONE); //隐藏评价
                }
                break;

            case 3:  //配送中
            case 4:  //待收货
            case 9:  //接单用户确认取货
            case 10:  //下单用户确认取货
                receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
                takeTime.setText("取货时间：" + getTimeString(dataBean.take_real_time));
                doneTimeLayout.setVisibility(View.GONE);//完成时间

                if (orderType.equals("1")) { //我的接单
                    order_detail_contact_userLayout.setVisibility(View.VISIBLE); //显示联系下单用户
                    contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                    if (dataBean.type.equals("1") || dataBean.type.equals("5")) {  //外卖和速递
                        confirmLayout.setVisibility(View.GONE);  //确认取货
//                        cancelOrder.setText("取消订单");
//                        evaluation.setText("已到达，验证收货");
                        evaluation.setText("完成订单");
                        if (dataBean.type.equals("5")) {
                            evaluation.setText("已到达，查验收货");
                        }
                    } else {
                        takeTimeLayout.setVisibility(View.GONE);
//                        cancelOrder.setText("取消订单（50S）");

                        if (takeTimeAgo < feerCancelTime) {   //在取消时间的范围内，取消订单按钮倒计时
                            new CountDownTimer(takeTimeAgo * 1000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    if (AvOrderDetail.this == null || isFinishing()) return;
                                    cancelOrder.setText("取消订单（" + (millisUntilFinished / 1000) + "S）");
                                }

                                @Override
                                public void onFinish() {
                                    if (AvOrderDetail.this == null || isFinishing()) return;
                                    cancelOrder.setVisibility(View.GONE);
                                    if (cancelDailog.isShowing())
                                        cancelDailog.dismiss();
                                }
                            }.start();
                        } else {  //隐藏
                            cancelOrder.setVisibility(View.GONE);
                        }


                        confirmReceipt.setText("完成订单");
                        evaluationLayout.setVisibility(View.GONE); //隐藏评价
//                        evaluation.setText("完成订单");
                    }
                } else if (orderType.equals("2")) { //我的订单
                    contactOrderUser.setText("接单：" + dataBean.other_user.name);
                    if (!TextUtils.isEmpty(dataBean.other_user.phone))
                        stars.setStars((int) Float.parseFloat(dataBean.other_user.star)); //设置评星
                    if (dataBean.type.equals("2") || dataBean.type.equals("3") || dataBean.type.equals("4")) {
                        takeTimeLayout.setVisibility(View.GONE);
//                        evaluation.setText("取消订单（51S）");
                        if (takeTimeAgo < feerCancelTime) {   //在取消时间的范围内，取消订单按钮倒计时
                            new CountDownTimer(takeTimeAgo * 1000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    if (AvOrderDetail.this == null || isFinishing()) return;
                                    evaluation.setText("取消订单（" + (millisUntilFinished / 1000) + "S）");
                                }

                                @Override
                                public void onFinish() {
                                    if (AvOrderDetail.this == null || isFinishing()) return;
                                    evaluation.setText("取消订单");
                                    evaluation.setBackgroundColor(Color.parseColor("#669999"));
                                    evaluation.setTextColor(Color.parseColor("#666666"));
                                    evaluation.setEnabled(false);
                                    if (cancelDailog.isShowing())
                                        cancelDailog.dismiss();
                                }
                            }.start();
                        } else {
                            evaluation.setText("取消订单");
                            evaluation.setBackgroundColor(Color.parseColor("#999999"));
                            evaluation.setTextColor(Color.parseColor("#666666"));
                            evaluation.setEnabled(false);
                        }
                        confirmLayout.setVisibility(View.GONE);  //确认取货
                    } else {
                        confirmLayout.setVisibility(View.GONE);  //确认取货
                        evaluationLayout.setVisibility(View.GONE); //隐藏评价
                    }
                }
                break;
//            case 4:  //待收货
//                receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
//                takeTime.setText("取货时间：" + getTimeString(dataBean.take_real_time));
//                doneTimeLayout.setVisibility(View.GONE);//完成时间
//
//                if (dataBean.type.equals("1") || dataBean.type.equals("5")) {
////                    confirmReceipt.setText("我要确认取货");
//                    contactOrderUser.setText("接单：" + dataBean.other_user.name);
//                    stars.setStars(Float.parseFloat(dataBean.other_user.star)); //设置评星
//                    evaluationLayout.setVisibility(View.GONE);
//                    confirmLayout.setVisibility(View.GONE);
//                } else {
//                    contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
//                    confirmReceipt.setText("付款");
//                }
//                evaluationLayout.setVisibility(View.GONE); //隐藏评价
//                break;
            case 5:  //已完成
                receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
                takeTime.setText("取货时间：" + getTimeString(dataBean.take_real_time));
                doneTime.setText("完成时间：" + getTimeString(dataBean.complete_time));
                if (orderType.equals("1")) { //我的接单
                    order_detail_contact_userLayout.setVisibility(View.VISIBLE); //显示联系下单用户
                    contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                    confirmLayout.setVisibility(View.GONE);  //确认取货
                    if (dataBean.evaluate_state == 0) {  //未评价
                        evaluation.setText("我要评价");
                    } else if (dataBean.evaluate_state == 1) { //已评价
                        evaluation.setText("查看评价");
                    }
                } else if (orderType.equals("2")) { //我的订单
                    contactOrderUser.setText("接单：" + dataBean.other_user.name);
                    if (!TextUtils.isEmpty(dataBean.other_user.phone))
                        stars.setStars((int) Float.parseFloat(dataBean.other_user.star)); //设置评星
                    confirmLayout.setVisibility(View.GONE);  //确认取货
                    if (dataBean.evaluate_state == 0) {  //未评价
                        evaluation.setText("我要评价");
                    } else if (dataBean.evaluate_state == 1) { //已评价
                        evaluation.setText("查看评价");
                    }
                }
                if (dataBean.type.equals("2") || dataBean.type.equals("3") || dataBean.type.equals("4")) {
                    takeTimeLayout.setVisibility(View.GONE);
                }
                break;
            case 6:  //下单取消
                if (dataBean.other_user.phone.equals("")) {
                    receiveTimeLayout.setVisibility(View.GONE);
                } else {
                    receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
                }
                takeTime.setText("取消时间：" + getTimeString(dataBean.cancel_time));
                doneTime.setText("取消原因：" + dataBean.cancel_reason);
                takeTime.setTextColor(Color.parseColor("#ff8704"));
                doneTime.setTextColor(Color.parseColor("#ff8704"));

                confirmLayout.setVisibility(View.GONE);  //确认取货
                contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                evaluationLayout.setVisibility(View.GONE); //隐藏评价
                break;
            case 7:  //接单取消
                if (dataBean.other_user.phone.equals("")) {
                    receiveTimeLayout.setVisibility(View.GONE);
                } else {
                    receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
                }
                receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
                doneTime.setText("取消原因：" + dataBean.cancel_reason);
                takeTime.setTextColor(Color.parseColor("#ff8704"));
                doneTime.setTextColor(Color.parseColor("#ff8704"));

                order_detail_contact_userLayout.setVisibility(View.GONE); //联系下单用户
                confirmLayout.setVisibility(View.GONE);  //确认取货
                contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                evaluationLayout.setVisibility(View.GONE); //隐藏评价

                break;
            case 8:  //待付款
                confirmReceipt.setText("付款");
                receiveTimeLayout.setVisibility(View.GONE);//接单时间
                takeTimeLayout.setVisibility(View.GONE);//取货时间
                doneTimeLayout.setVisibility(View.GONE);//完成时间

                contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                evaluationLayout.setVisibility(View.GONE); //隐藏评价
                break;

//            case 9:  //接单用户确认取货
//
//                break;
//            case 10:  //下单用户确认取货
//                break;

            case 11: //系统取消
                if (dataBean.other_user.phone.equals("")) {
                    receiveTimeLayout.setVisibility(View.GONE);
                } else {
                    receiveTime.setText("接单时间：" + getTimeString(dataBean.take_order_time));
                }
                takeTime.setText("取消时间：" + getTimeString(dataBean.cancel_time));
                doneTime.setText("取消原因：" + dataBean.cancel_reason);
                takeTime.setTextColor(Color.parseColor("#ff8704"));
                doneTime.setTextColor(Color.parseColor("#ff8704"));

                order_detail_contact_userLayout.setVisibility(View.GONE); //联系下单用户
                confirmLayout.setVisibility(View.GONE);  //确认取货
                contactOrderUserLayout.setVisibility(View.GONE);//接单人隐藏
                evaluationLayout.setVisibility(View.GONE); //隐藏评价
                break;
        }

        evaluation.setOnClickListener(this); //评价
        confirmReceipt.setOnClickListener(this); //我要确认取货
    }

    private String phoneNumber = "";  //要拨打的号码

    @Override
    public void onClick(View v) {
        if (dataBean == null) return;
        switch (v.getId()) {
            case R.id.order_detail_contacts_take:  //取货联系人
                phoneNumber = dataBean.take_phone;
                checkCallPhone();
                break;
            case R.id.order_detail_contacts_delivery:  //送货联系人
                phoneNumber = dataBean.receipt_phone;
                checkCallPhone();
                break;
            case R.id.order_detail_cancel_order:  //取消订单
                showCancelDialog();
                break;
            case R.id.order_detail_evaluation:

                if (orderType.equals("1")) {  //我的接单
                    if (dataBean.state.equals("1")) {  //待接单
//                    执行取消订单操作
                        showCancelDialog();
                    } else {  //订单其他状态
                        if (dataBean.type.equals("1") || dataBean.type.equals("5")) {  //外卖和速递
                            if (!dataBean.state.equals("5")) {
                                if (dataBean.type.equals("1")) { //外卖的直接完成订单，其他的都要弹窗验证码
//                                    外卖完成订单事件
                                    mOrderDetailPresenter.orderCompleted(order_id, true, "");
                                } else { //确认取货
                                    confirmReceiveGoods();
                                }
                            } else {
                                if (dataBean.evaluate_state == 0) {  //未评价
//                                    T.showShort(mContext, "我要评价");
//                                    去评价
                                    isReadEvaluation = false;
                                    goOrderEvaluation();
                                } else if (dataBean.evaluate_state == 1) { //已评价
//                                    T.showShort(mContext, "查看评价");
                                    isReadEvaluation = true;
                                    goOrderEvaluation();
                                }
                            }

                        } else {
                            if (dataBean.evaluate_state == 0) {  //未评价
//                                T.showShort(mContext, "我要评价");
                                isReadEvaluation = false;
                                goOrderEvaluation();
                            } else if (dataBean.evaluate_state == 1) { //已评价
//                                T.showShort(mContext, "查看评价");
                                isReadEvaluation = true;
                                goOrderEvaluation();
                            }
                        }
                    }
                } else {  //我的订单

                    if (dataBean.state.equals("1") || dataBean.state.equals("3") || dataBean.state.equals("4")) {  //待接单
//                    执行取消订单操作
                        showCancelDialog();
                    } else {
                        if (dataBean.evaluate_state == 0) {  //未评价
//                            T.showShort(mContext, "我要评价");
                            isReadEvaluation = false;
                            goOrderEvaluation();
                        } else if (dataBean.evaluate_state == 1) { //已评价
//                            T.showShort(mContext, "查看评价");
                            isReadEvaluation = true;
                            goOrderEvaluation();
                        }
                    }
                }

                break;

            case R.id.order_detail_confirm_receipt:

                if (orderType.equals("1")) { //我的接单
                    if (dataBean.type.equals("1") || dataBean.type.equals("5")) { //外卖和速递特有的确认取货
                        if (dataBean.state.equals("2")) {
//                            取货提示
                            mOrderDetailPresenter.confirmTakeGoogs(orderType, order_id);
                        }
                    } else {
                        if (dataBean.state.equals("2") || dataBean.state.equals("3") || dataBean.state.equals("4")) {
//                            T.showShort(mContext, "完成订单");
                            confirmReceiveGoods();
                        } else {
//                            T.showShort(mContext, "我的接单去付款");
                            goPay(order_id);
                        }
                    }

                } else {  //我的订单
                    if (dataBean.type.equals("1") || dataBean.type.equals("5")) { //外卖和速递特有的确认取货
                        if (dataBean.state.equals("8")) {
//                            T.showShort(mContext, "我的订单去付款");
                            goPay(order_id);
                        } else {
//                            MessageTools.showDialog(this, "取货提示", "请确认是否有人来取货\n\n取货人：" + dataBean.other_user.name,
//                                    true, "确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            mOrderDetailPresenter.confirmTakeGoogs(orderType, order_id);
//                                        }
//                                    }, "取消", null);

                            final MyDialog takeGoodsDailog = new MyDialog(mContext);
                            takeGoodsDailog.setTitle("取货提示");
                            takeGoodsDailog.setContent("请确认是否有人来取货\n\n取货人：" + dataBean.other_user.name);
                            takeGoodsDailog.setNegativeButton("取消", null);
                            takeGoodsDailog.setPositiveButton("确定", new MyDialog.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mOrderDetailPresenter.confirmTakeGoogs(orderType, order_id);
                                    takeGoodsDailog.dismiss();
                                }
                            });
                            takeGoodsDailog.show();
                        }
                    } else {
//                        T.showShort(mContext, "我的订单去付款");
                        goPay(order_id);
                    }
                }

                break;

            case R.id.order_detail_contact_user:  //联系下单用户
                phoneNumber = dataBean.other_user.phone;
                checkCallPhone();
                break;

            case R.id.order_detail_contact_order_user:  //拨打接单用户
                phoneNumber = dataBean.other_user.phone;
                checkCallPhone();
                break;
            case R.id.order_detail_check_address_take:  //查看取货地址
                x = dataBean.take_x;
                y = dataBean.take_y;
                startActivity2Map(Double.parseDouble(x), Double.parseDouble(y));
                break;
            case R.id.order_detail_check_address_delivery:  //查看送货地址
                x = dataBean.receipt_x;
                y = dataBean.receipt_y;
                startActivity2Map(Double.parseDouble(x), Double.parseDouble(y));
                break;
        }
    }

    public String x = "";  //地图x坐标
    public String y = "";   //地图y坐标

    private void startActivity2Map(double x, double y) {
        Intent intent = new Intent(mContext, AvAddressInMap.class);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        startActivity(intent);
    }

    //    完成订单弹窗
    private void confirmReceiveGoods() {
        completedOrderDailog = new MyDialog(this);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_content_verify_order, null);
        completedOrderDailog.setCustomContentView(contentView);
        TextView btnCantContact = (TextView) contentView.findViewById(R.id.btn_cant_contact);
        final EditText etPhoneNum = (EditText) contentView.findViewById(R.id.et_phone_num);
        final EditText etCode = (EditText) contentView.findViewById(R.id.et_code);
        TextView btnGetCode = (TextView) contentView.findViewById(R.id.btn_get_code);
        final AutoRelativeLayout codeLayout = (AutoRelativeLayout) contentView.findViewById(R.id.code_layout);

        btnCantContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeLayout.isShown()) {
                    codeLayout.setVisibility(View.GONE);
                    etPhoneNum.setHint("请输入4位收货验证码");
                } else {
                    codeLayout.setVisibility(View.VISIBLE);
                    etPhoneNum.setHint("请输入被委托人的手机号");

                }
            }
        });

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DensityUtils.isMobileNO(etPhoneNum.getText().toString().trim())) {
                    T.showShort(getContext(), "请输入正确的手机号码");
                    return;
                }
//                获取验证码
                mOrderDetailPresenter.getCode(order_id, etPhoneNum.getText().toString().trim(), (TextView) v);
            }
        });

        completedOrderDailog.setNegativeButton("取消", null);
        completedOrderDailog.setPositiveButton("确定", new MyDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code;
                if (codeLayout.isShown()) {
                    code = etCode.getText().toString();
                } else {
                    code = etPhoneNum.getText().toString();
                }
                if (TextUtils.isEmpty(code)) {
                    T.showShort(mContext, "验证码不能为空");
                    return;
                }
//                完成订单
                mOrderDetailPresenter.orderCompleted(order_id, false, code);
            }
        });

        completedOrderDailog.show();
    }


    //    是否是查看评价，用于跳转到评价界面
    private boolean isReadEvaluation = false;
    //Activity跳转请求码
    private final int REQUEST_EVALUATION_CODE = 1000;


    /**
     * 去评价
     */
    private void goOrderEvaluation() {
        Intent intent = new Intent(mContext, AvOrderEvaluation.class);
        intent.putExtra("order_id", order_id);
        intent.putExtra("type", orderType);
        intent.putExtra("isRead", isReadEvaluation);
        startActivityForResult(intent, REQUEST_EVALUATION_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EVALUATION_CODE && resultCode == 1001) {
            boolean isSuccess = data.getBooleanExtra("isSuccess", false);
            if (isSuccess) {
                orderDetail.data.evaluate_state = 1;  //订单详情按钮
                toShowDataView(orderDetail);
            }
        }
        if (requestCode == CANCEL_CODE && resultCode == 110) {
            dataBean.state = "6";
            String reason = data.getStringExtra("reason");
            String time = data.getStringExtra("time");
            L.e(reason + "\n" + time);
            if (TextUtils.isEmpty(reason) || TextUtils.isEmpty(time)) {
                mOrderDetailPresenter.getOrderDetail(orderType, order_id);
                return;
            }
            dataBean.cancel_reason = reason;
            dataBean.cancel_time = time;
            toShowDataView(orderDetail);
        }
    }

    /**
     * 去支付
     *
     * @param order_id 订单id
     */
    private void goPay(String order_id) {
        Intent intent = new Intent(mContext, AvOrderPay.class);
        intent.putExtra("order_id", order_id);
        mContext.startActivity(intent);
    }

    //    确认取货成功回调
    @Override
    public void confirmTakeSuccess() {

        if (orderType.equals("1")) {  //我的接单
            MyDialog takeGoodsDailog = new MyDialog(getContext());
            takeGoodsDailog.setTitle("取货提示");
            takeGoodsDailog.setContent("已通知下单用户，请等待对方确认");
            takeGoodsDailog.setNegativeButton("好的", new MyDialog.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            takeGoodsDailog.show();

        } else {  //我的订单

//        伪造数据
//        dataBean.state = "3";
//        dataBean.take_real_time = (System.currentTimeMillis()) + "";
//        toShowDataView(orderDetail);

//        重新联网
            mOrderDetailPresenter.getOrderDetail(orderType, order_id);

        }
    }

    //完成订单成功回调
    @Override
    public void orderCompletedSuccess() {
        if (completedOrderDailog.isShowing())
            completedOrderDailog.dismiss();
        mOrderDetailPresenter.getOrderDetail(orderType, order_id);
    }

    /**
     * 获取格式化时间
     *
     * @param timeStr
     * @return 返回类型： yyyy-MM-dd HH:mm:ss
     */
    public String getTimeString(String timeStr) {
        if (TextUtils.isEmpty(timeStr))
            return "时间格式错误";
        return TimeFormatUtils.parseDate(new Date(Long.parseLong(timeStr) * 1000), "yyyy-MM-dd HH:mm:ss");
    }

    private String getOrderState(int state) {
        switch (state) {
            case 1:
                return "待接单";
            case 2:
                return "待取货";
            case 3:
                if (dataBean.type.equals("2") || dataBean.type.equals("3")) {
                    return "处理中";
                }
                return "配送中";
            case 4:  //待收货
                if (dataBean.type.equals("2") || dataBean.type.equals("3")) {
                    return "处理中";
                }
                return "配送中";
            case 5:
                return "已完成";
            case 6:
                return "已取消"; //下单取消
            case 7:
                return "已取消"; //接单取消
            case 8:
                return "待付款";
            case 9:
                return "处理中";
            case 10:
                return "配送中";
            case 11:
                return "已取消"; //系统取消
        }
        return "";
    }

    /**
     * 根据订单类型编号获取订单类型字符串
     */
    private String getOrderType(String type) {
        if (type.equals("1")) {
            return "外卖配送";
        } else if (type.equals("2")) {
            return "代买服务";
        } else if (type.equals("3")) {
            return "代办服务";
        } else if (type.equals("4")) {
            return "车友之家";
        } else if (type.equals("5")) {
            return "同城速递";
        }
        return "";
    }


    //取消订单
    private void canCelOrder() {
        Intent cancelOrder = new Intent(mContext, AvCancelOrder.class);
        if (dataBean.state.equals("5")) { //已付款
            cancelOrder.putExtra("isPay", true);
        } else {
            cancelOrder.putExtra("isPay", false);
        }
        cancelOrder.putExtra("type", orderType);
        cancelOrder.putExtra("order_id", order_id);
//        startActivity(cancelOrder);
        startActivityForResult(cancelOrder, CANCEL_CODE);
    }

    //    显示取消订单弹窗
    private void showCancelDialog() {
        String title = "";
        if (dataBean.state.equals("1")) { //待接单
            title = "您确定要取消此订单吗？";
            cancelDialog(title);
        } else {
            long timeTemp = (System.currentTimeMillis() / 1000) - Long.parseLong(dataBean.take_order_time);
            if (timeTemp > feerCancelTime) {
                //超过后台设置免费取消订单的时间弹窗
                if (orderType.equals("1")) {
                    title = "您确定要取消此订单吗？\n取消订单会扣除10个积分，并且会降低星级信誉等级！";
                } else {
                    title = "您确定要取消此订单吗？\n取消订单会扣除20%的费用（最低值2元）给" + dataBean.other_user.name + "，并扣除你5个积分";
                }
                cancelDialog(title);
            } else {
                title = "您确定要取消此订单吗？";
                cancelDialog(title);
            }
        }
    }

    private void cancelDialog(String title) {

        cancelDailog = new MyDialog(mContext);
        cancelDailog.setTitle("取消提示");
        cancelDailog.setContent(title);
        cancelDailog.setNegativeButton("取消", null);
        cancelDailog.setPositiveButton("确定", new MyDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                canCelOrder();
                cancelDailog.dismiss();
            }
        });
        cancelDailog.show();

    }

    //    授权的结果回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALLPHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                Toast.makeText(mContext, "您没有给应用授于拨打电话的权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 检查拨打电话
     */
    public void checkCallPhone() {
        //拨打电话
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {  //检查是否已授权
            boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE);
            if (!shouldShow) {
                ActivityCompat.requestPermissions(AvOrderDetail.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CALLPHONE);
            } else {
                Toast.makeText(AvOrderDetail.this, "您已经关闭拨打电话权限，可能无法正常使用程序", Toast.LENGTH_SHORT).show();
            }
        } else {
            callPhone();
        }
    }

    public void callPhone() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNumber);
            intent.setData(data);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        topBarView = ((TopBarView) findViewById(R.id.order_detail_top_bar));
        orderNumber = ((TextView) findViewById(R.id.order_detail_order_number));
        orderStatus = ((TextView) findViewById(R.id.order_detail_status));
        time = ((TextView) findViewById(R.id.time));
        type = ((TextView) findViewById(R.id.order_detail_type));
        money = ((TextView) findViewById(R.id.money));
        order_detail_address_takeLayout = ((ViewGroup) findViewById(R.id.order_detail_address_takeLayout));
        order_detail_delivery_layout1 = ((ViewGroup) findViewById(R.id.order_detail_delivery_layout1));
        addressTake = ((TextView) findViewById(R.id.order_detail_address_take));
        floorInfoTake = ((TextView) findViewById(R.id.order_detail_floor_info_take));
        contactsTake = ((TextView) findViewById(R.id.order_detail_contacts_take));
        checkAddressTake = ((TextView) findViewById(R.id.order_detail_check_address_take));
        distance = ((TextView) findViewById(R.id.order_detail_distance));
        addressDelivery = ((TextView) findViewById(R.id.order_detail_address_delivery));
        floorInfoDelivery = ((TextView) findViewById(R.id.order_detail_floor_info_delivery));
        checkAddressDelivery = ((TextView) findViewById(R.id.order_detail_check_address_delivery));
        contactsDelivery = ((TextView) findViewById(R.id.order_detail_contacts_delivery));
        tipLayout = ((AutoRelativeLayout) findViewById(R.id.order_detail_tip_layout));
        tip = ((TextView) findViewById(R.id.order_detail_tip));
        preferential = ((TextView) findViewById(R.id.order_detail_preferential));
        realPay = ((TextView) findViewById(R.id.order_detail_real_pay));
        insuredFee = ((TextView) findViewById(R.id.order_detail_insured_fee));
        remarkLayout = ((AutoLinearLayout) findViewById(R.id.order_detail_remark_layout));
        remark = ((TextView) findViewById(R.id.order_detail_remark));
//      备注下的照片容器
        order_detail_photoLayout = ((ViewGroup) findViewById(R.id.order_detail_photoLayout));
        photo1 = ((ImageView) findViewById(R.id.order_detail_photo1));
        photo2 = ((ImageView) findViewById(R.id.order_detail_photo2));
        photo3 = ((ImageView) findViewById(R.id.order_detail_photo3));
        photo4 = ((ImageView) findViewById(R.id.order_detail_photo4));
        orderTime = ((TextView) findViewById(R.id.order_detail_order_time));
        receiveTimeLayout = ((AutoLinearLayout) findViewById(R.id.order_detail_receive_time_layout));
        receiveTime = ((TextView) findViewById(R.id.order_detail_receive_time));
        takeTimeLayout = ((AutoLinearLayout) findViewById(R.id.order_detail_take_time_layout));
        takeTime = ((TextView) findViewById(R.id.order_detail_take_time));
        doneTimeLayout = ((AutoLinearLayout) findViewById(R.id.order_detail_done_time_layout));
        doneTime = ((TextView) findViewById(R.id.order_detail_done_time));
        contactOrderUserLayout = ((AutoRelativeLayout) findViewById(R.id.order_detail_contact_order_user_layout));
        contactOrderUser = ((TextView) findViewById(R.id.order_detail_contact_order_user));
        stars = ((Stars) findViewById(R.id.order_detail_stars));
        confirmLayout = ((AutoRelativeLayout) findViewById(R.id.order_detail_confirm_layout));
        confirmReceipt = ((TextView) findViewById(R.id.order_detail_confirm_receipt));
        cancelOrder = ((TextView) findViewById(R.id.order_detail_cancel_order));
        evaluationLayout = ((AutoFrameLayout) findViewById(R.id.order_detail_evaluation_layout));
        evaluation = ((TextView) findViewById(R.id.order_detail_evaluation));

        line = findViewById(R.id.order_line);
        order_detail_service_lab = ((TextView) findViewById(R.id.order_detail_service_lab));
        payIcon = findViewById(R.id.irder_detail_pay);

//        我的接单 联系下单用户布局
        order_detail_contact_userLayout = ((ViewGroup) findViewById(R.id.order_detail_contact_userLayout));
//        联系下单用户
        order_detail_contact_user = ((TextView) findViewById(R.id.order_detail_contact_user));
//        支付方式图标
        iv_payTypeIcon = ((ImageView) findViewById(R.id.iv_payTypeIcon));
        iv_payTypeIcon2 = ((ImageView) findViewById(R.id.iv_payTypeIcon2));
    }
}
