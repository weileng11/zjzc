package cn.bs.zjzc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.zhy.autolayout.AutoFrameLayout;

import java.io.Serializable;
import java.util.List;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.model.response.OrderAmountResponse;
import cn.bs.zjzc.model.response.OrderCouponResponse;
import cn.bs.zjzc.model.response.OrderPayResponse;
import cn.bs.zjzc.presenter.OrderPayPresenter;
import cn.bs.zjzc.ui.view.IOrderPayView;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.T;

/**
 * 订单支付
 * Created by Ming on 2016/5/24.
 */
public class AvOrderPay extends BaseActivity implements IOrderPayView, CompoundButton.OnCheckedChangeListener {

    private Context mContext = this;
    private TextView totalMoney;
    private NormalOptionView coupon;//优惠券
    private CheckBox accountPay, wechatPay, aliPay;//支付方式
    private AutoFrameLayout weChatPayView, aliPayView;

    private List<OrderCouponResponse.DataBean> data;
    private String order_id;//订单ID
    private String coupon_id = null;//优惠券ID
    private OrderPayPresenter presenter;
    private boolean adequate;//余额是否充足
    private double payMoney;//需支付
    private int payType = -1;//支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）
    private int mType = -1;//订单类型

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_order_pay);
        initViews();
        initEvents();

        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        mType = intent.getIntExtra("mType", 1);
        L.d("order_id", order_id + ",mType=" + mType);
        presenter = new OrderPayPresenter(this);
        //当前定位城市id,获取订单可用优惠券
        if (App.LOGIN_USER.getCityInfo() != null && App.LOGIN_USER.getCityInfo()[0] != null) {
            presenter.getOrderCoupon(App.LOGIN_USER.getCityInfo()[0]);
        } else {
            //获取订单总费用
            presenter.getOrderAmount(order_id, coupon_id);
        }
    }

    private void initEvents() {
        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvAvailableCoupon.class);
                if (data != null) {
                    intent.putExtra("data", (Serializable) data);
                }
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            coupon.setContent(data.getStringExtra("name"));
            coupon_id = data.getStringExtra("coupon_id");
            //获取订单总费用
            presenter.getOrderAmount(order_id, coupon_id);
        }
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                L.d("pay", "result::" + result + ",\nerrorMsg::" + errorMsg + ",\nextraMsg::" + extraMsg);
                if ("success".equals(result)) {
                    Intent intent = new Intent(mContext, AvOrderPaySuccess.class);
                    intent.putExtra("order_id", order_id);
                    intent.putExtra("mType", mType);
                    startActivity(intent);
                    finish();
                } else if ("fail".equals(result)) {
                    T.showShort(mContext, "支付失败");
                } else if ("cancel".equals(result)) {
                    T.showShort(mContext, "用户取消支付");
                } else if ("invalid".equals(result)) {
                    L.d("pay", "payment plugin not installed");
                }
            }
        }
    }

    private void initViews() {
        coupon = ((NormalOptionView) findViewById(R.id.order_pay_coupon));
        totalMoney = ((TextView) findViewById(R.id.totalMoney));

        weChatPayView = (AutoFrameLayout) findViewById(R.id.weChatPay);
        aliPayView = (AutoFrameLayout) findViewById(R.id.aliPay);

        accountPay = ((CheckBox) findViewById(R.id.order_pay_account));
        accountPay.setOnCheckedChangeListener(this);
        wechatPay = ((CheckBox) findViewById(R.id.order_pay_wechat));
        wechatPay.setOnCheckedChangeListener(this);
        aliPay = ((CheckBox) findViewById(R.id.order_pay_alipay));
        aliPay.setOnCheckedChangeListener(this);
    }

    /**
     * 确认支付
     * <p/>
     * 支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）
     */
    public void clickConfirm(View v) {
        if (aliPay.isChecked()) {
            payType = 1;
        } else if (wechatPay.isChecked()) {
            payType = 2;
        } else if (accountPay.isChecked()) {
            payType = 3;
        } else if (accountPay.isChecked() && aliPay.isChecked()) {
            payType = 4;
        } else if (accountPay.isChecked() && wechatPay.isChecked()) {
            payType = 5;
        }
        if (payType == -1) {
            T.showShort(mContext, "请选择支付方式");
            return;
        }
        if (!adequate && payType == 3) {
            T.showShort(mContext, "账户余额不足");
            return;
        }
        presenter.createPayment(payType + "", order_id, coupon_id);
    }

    /**
     * 成功获取订单总费用
     *
     * @param dataBean
     */
    @Override
    public void getOrderAmountSuccessView(OrderAmountResponse.DataBean dataBean) {
        L.d("AvOrderPay", dataBean.toString());
        double money = Double.parseDouble(dataBean.money);
        double coupon_money = Double.parseDouble(dataBean.coupon_money);
        double total_money = Double.parseDouble(dataBean.total_money);
        payMoney = total_money - coupon_money;
        if (money >= payMoney) {
            adequate = true;//余额充足
        } else {
            adequate = false;//余额不足
        }
        totalMoney.setText("￥" + payMoney + "元");
    }

    /**
     * 获取订单总费用失败
     */
    @Override
    public void getOrderAmountFailedView() {
        coupon_id = null;
    }

    /**
     * 成功获取订单可用优惠券
     *
     * @param dataBean
     */
    @Override
    public void getOrderCouponSuccessView(List<OrderCouponResponse.DataBean> dataBean) {
        if (dataBean != null) {
            L.d("AvOrderPay", dataBean.toString());
            data = dataBean;
            coupon.setContent(dataBean.size() != 0 ? dataBean.size() + "张可用优惠券" : "暂无可用优惠券");
        }
        //获取订单总费用
        presenter.getOrderAmount(order_id, coupon_id);
    }

    //后台成功生成支付订单
    @Override
    public void createPaymentSuccssView(OrderPayResponse data) {
        if (payType == 3) {//账户余额支付
            if (AllConsts.http.successErrCode.equals(data.errcode)) {
                Intent intent = new Intent(mContext, AvOrderPaySuccess.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("mType", mType);
                startActivity(intent);
                finish();
            }
            T.showShort(mContext, data.errinfo);
        } else {
            //调用Ping++支付功能
            String json = new Gson().toJson(data.data);
            Pingpp.createPayment(this, json);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.order_pay_account:
                accountPay.setChecked(isChecked);
                if (adequate && accountPay.isChecked()) {
                    wechatPay.setChecked(false);
                    aliPay.setChecked(false);
                    weChatPayView.setVisibility(View.GONE);
                    aliPayView.setVisibility(View.GONE);
                } else {
                    weChatPayView.setVisibility(View.VISIBLE);
                    aliPayView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.order_pay_wechat:
                aliPay.setChecked(false);
                wechatPay.setChecked(isChecked);
                break;
            case R.id.order_pay_alipay:
                wechatPay.setChecked(false);
                aliPay.setChecked(isChecked);
                break;
        }
    }
}
