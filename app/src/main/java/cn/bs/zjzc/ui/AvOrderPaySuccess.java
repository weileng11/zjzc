package cn.bs.zjzc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.CustomDialog;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.OrderDetail;
import cn.bs.zjzc.model.response.OrderPayResponse;
import cn.bs.zjzc.presenter.OrderPaySuccessPresenter;
import cn.bs.zjzc.ui.view.IAvOrderPaySuccessView;
import cn.bs.zjzc.util.DateTimeUtils;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.T;

/**
 * 订单支付成功
 * 倒计时
 */
public class AvOrderPaySuccess extends BaseActivity implements IAvOrderPaySuccessView, CompoundButton.OnCheckedChangeListener {

    private Context mContext = this;
    private String order_id;
    private int mType;
    private AutoLinearLayout layout;
    private TopBarView topBar;
    private TextView[] tvs;
    private String[] txt1, txt2, txt3;

    private CheckBox accountPay, wechatPay, aliPay;//支付方式
    private TextView order_needpay;//需支付
    private String tipMoney;//小费

    private AutoFrameLayout weChatPayView, aliPayView;
    //添加小费
    private OptionsPickerView pvOptionsTips;
    private CustomDialog tipDialog;

    private ImageView btn_tip;//添加小费按钮
    private TextView tv_time;//倒计时

    private OrderPaySuccessPresenter presenter;
    private boolean adequate;//余额是否充足
    private int payType = -1;//支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_av_order_pay_success);

        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        mType = intent.getIntExtra("mType", 1);

        initView();

        //获取订单详情
        presenter = new OrderPaySuccessPresenter(this);
        presenter.getOrderDetail("2", order_id);
    }

    private void initView() {
        btn_tip = (ImageView) findViewById(R.id.btn_tip);
        tv_time = (TextView) findViewById(R.id.tv_time);

        topBar = ((TopBarView) findViewById(R.id.home_top_bar));
        topBar.setRightClickListener(new View.OnClickListener() {//历史记录
            @Override
            public void onClick(View v) {//取消订单
                Intent intent = new Intent(mContext, AvCancelOrder.class);
                intent.putExtra("isPay", true);
                intent.putExtra("type", "2");
                intent.putExtra("order_id", order_id);
                startActivity(intent);
            }
        });

        layout = (AutoLinearLayout) findViewById(R.id.layout);
        tvs = new TextView[layout.getChildCount()];
        for (int i = 0; i < layout.getChildCount(); i++) {
            tvs[i] = (TextView) layout.getChildAt(i);
        }
        txt1 = new String[]{"订单号：", "服务类型：", "取餐时间：", "取餐地址：", "送餐地址：", "备注："};//外卖
        txt2 = new String[]{"订单号：", "服务类型：", "取货时间：", "取货地址：", "送货地址：", "备注："};//同城
        txt3 = new String[]{"", "订单号：", "服务类型：", "时间：", "地址：", "备注："};//代买代办，车友之家

        switch (mType) {
            case 1:
                for (int i = 0; i < tvs.length; i++) {
                    tvs[i].setText(txt1[i]);
                }
                break;
            case 2:
            case 3:
            case 4:
                btn_tip.setVisibility(View.GONE);//不支持添加小费
                tvs[0].setVisibility(View.GONE);
                for (int i = 1; i < tvs.length; i++) {
                    tvs[i].setText(txt3[i]);
                }
                break;
            case 5:
                for (int i = 0; i < tvs.length; i++) {
                    tvs[i].setText(txt2[i]);
                }
                break;
        }

        initTipDialog();

        View view = LayoutInflater.from(AvOrderPaySuccess.this).inflate(R.layout.dialog_addtips, null);
        order_needpay = (TextView) view.findViewById(R.id.order_needpay);
        weChatPayView = (AutoFrameLayout) view.findViewById(R.id.weChatPay);
        aliPayView = (AutoFrameLayout) view.findViewById(R.id.aliPay);
        accountPay = (CheckBox) view.findViewById(R.id.order_pay_account);
        wechatPay = (CheckBox) view.findViewById(R.id.order_pay_wechat);
        aliPay = (CheckBox) view.findViewById(R.id.order_pay_alipay);
        accountPay.setOnCheckedChangeListener(this);
        wechatPay.setOnCheckedChangeListener(this);
        aliPay.setOnCheckedChangeListener(this);
        tipDialog = new CustomDialog(AvOrderPaySuccess.this, "选择支付方式", view, AutoFrameLayout.LayoutParams.MATCH_PARENT, AutoFrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
    }

    /**
     * 小费弹框
     * mgc
     */
    private void initTipDialog() {
        //选项选择器
        pvOptionsTips = new OptionsPickerView(this);
        //选项1
        final ArrayList<String> tipsPrice = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            tipsPrice.add(i + "元");
        }
        pvOptionsTips.setPicker(tipsPrice);
        //监听确定选择按钮
        pvOptionsTips.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3) {
                tipMoney = tipsPrice.get(options1).replaceAll("元", "");
                order_needpay.setText(tipMoney + "元");
                tipDialog.show();
                //获取账户余额
                presenter.getWalletBalance();
            }
        });
    }

    public void clickBTN(View v) {
        switch (v.getId()) {
            case R.id.btn_check_order://查看订单
                Intent intent = new Intent(mContext, AvOrderDetail.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.btn_tip://添加小费
                pvOptionsTips.show();
                break;
            case R.id.btn_order_again://继续下单
                finish();
                break;
            case R.id.home_password_confirm://确认支付
                pay();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    T.showShort(mContext, "支付成功");
                } else if ("fail".equals(result)) {
                    T.showShort(mContext, "支付失败");
                } else if ("cancel".equals(result)) {
                    T.showShort(mContext, "用户取消支付");
                } else if ("invalid".equals(result)) {
                    L.d("pay", "payment plugin not installed");
                }
                tipDialog.dismiss();
            }
        }
    }

    /**
     * 获取账户余额
     *
     * @param data
     */
    @Override
    public void getWalletBalanceView(BalanceResponse.DataBean data) {
        if (Double.parseDouble(data.money) >= Double.parseDouble(tipMoney)) {
            adequate = true;//余额充足
        } else {
            adequate = false;//余额不足
        }
    }

    /**
     * 获取订单详情
     *
     * @param data
     */
    @Override
    public void getOrderDetailView(OrderDetail data) {
        String dateTime = DateTimeUtils.getTimeToDate(data.data.take_time);
        String[] content;
        switch (mType) {
            case 1:
                content = new String[]{data.data.order_num, "外卖配送", dateTime, data.data.take_address, data.data.receipt_address, data.data.remark_txt};
                for (int i = 0; i < content.length; i++) {
                    tvs[i].setText(txt1[i] + content[i]);
                }
                break;
            case 2:
                content = new String[]{"", data.data.order_num, "代买服务", dateTime, data.data.receipt_address, data.data.remark_txt};
                for (int i = 1; i < content.length; i++) {
                    tvs[i].setText(txt3[i] + content[i]);
                }
                break;
            case 3:
                content = new String[]{"", data.data.order_num, "代办服务", dateTime, data.data.receipt_address, data.data.remark_txt};
                for (int i = 1; i < content.length; i++) {
                    tvs[i].setText(txt3[i] + content[i]);
                }
                break;
            case 4:
                content = new String[]{"", data.data.order_num, "车友之家", dateTime, data.data.receipt_address, data.data.remark_txt};
                for (int i = 1; i < content.length; i++) {
                    tvs[i].setText(txt3[i] + content[i]);
                }
                break;
            case 5:
                content = new String[]{data.data.order_num, "同城速递", dateTime, data.data.take_address, data.data.receipt_address, data.data.remark_txt};
                for (int i = 0; i < content.length; i++) {
                    tvs[i].setText(txt2[i] + content[i]);
                }
                break;
        }
    }

    /**
     * 生成支付订单
     *
     * @param data
     */
    @Override
    public void createPaymentView(OrderPayResponse data) {
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

    /**
     * 确认支付
     * <p/>
     * 支付类型（1 支付宝，2 微信，3 余额，4 支付宝+余额，5 微信+余额）
     */
    public void pay() {
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
        presenter.createPayment(payType + "", order_id, "2", tipMoney);
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
