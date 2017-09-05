package cn.bs.zjzc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.RechargeResponse;
import cn.bs.zjzc.presenter.RechargePresenter;
import cn.bs.zjzc.ui.view.IRechargeView;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvRecharge extends BaseActivity implements IRechargeView {
    private Context mContext = this;
    private RechargePresenter mRechargePresenter;
    private TextView tvAccount;
    private TextView tvAccountBalance;
    private EditText etRechargeMoney;
    private RadioButton checkAlipay;
    private RadioButton checkWechat;
    private TextView btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_recharge);
        mRechargePresenter = new RechargePresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (etRechargeMoney.length() == 0) {
//                    showMsg("请输入充值金额");
//                }
//
//                if (!checkAlipay.isChecked() && !checkWechat.isChecked()) {
//                    showMsg("请选择充值方式");
//                }

                //支付宝充值
                if (checkAlipay.isChecked()) {
                    // startActivity(new Intent(mContext, PayDemoActivity.class));
                    mRechargePresenter.recharge("1", etRechargeMoney.getText().toString());
                }
                //微信充值
                if (checkWechat.isChecked()) {
                    mRechargePresenter.recharge("2", etRechargeMoney.getText().toString());
                }

            }
        });
    }

    private void initViews() {
        tvAccount = ((TextView) findViewById(R.id.recharge_current_account));
        tvAccountBalance = ((TextView) findViewById(R.id.recharge_account_balance));
        etRechargeMoney = ((EditText) findViewById(R.id.recharge_money));
        checkAlipay = ((RadioButton) findViewById(R.id.recharge_check_alipay));
        checkWechat = ((RadioButton) findViewById(R.id.recharge_check_wechat));
        btnConfirm = ((TextView) findViewById(R.id.recharge_confirm));

        tvAccount.setText(App.LOGIN_USER.getAccount());
        tvAccountBalance.setText(getIntent().getStringExtra("balance"));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
                finish();
            }
        }
    }

    @Override
    public void createPayment(RechargeResponse.DataBean data) {
        // startActivity(new Intent(mContext, AvRechargeDetail.class));
        // finish();
        String json = new Gson().toJson(data);
        Pingpp.createPayment(this, json);
    }

}
