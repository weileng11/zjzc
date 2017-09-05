package cn.bs.zjzc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.TimeUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.presenter.CancelOrderPresenter;
import cn.bs.zjzc.ui.view.ICancelOrderView;
import cn.bs.zjzc.util.T;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/6/1.
 */
public class AvCancelOrder extends BaseActivity implements ICancelOrderView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private boolean mOrderIsPay;
    private ViewGroup order_detail_reasonLayout;
    private EditText et_other_reason;
    private TextView home_password_confirm;
    private TextView tv_tips;
    private String type;
    private String order_id;
    private CheckBox cancel_order_error;
    private CheckBox cancel_order_replay;
    private CheckBox cancel_order_noNeed;
    private CancelOrderPresenter mCancelOrderPresenter;
    private CheckBox cancel_order_Tremor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_cancel_order);

        initBasic();
        initView();
        initEvent();

    }

    private void initEvent() {
        home_password_confirm.setOnClickListener(this);
        cancel_order_error.setOnCheckedChangeListener(this);
        cancel_order_replay.setOnCheckedChangeListener(this);
        cancel_order_noNeed.setOnCheckedChangeListener(this);
        cancel_order_Tremor.setOnCheckedChangeListener(this);
    }

    private void initBasic() {
        mOrderIsPay = getIntent().getBooleanExtra("isPay", false);
        type = getIntent().getStringExtra("type");
        order_id = getIntent().getStringExtra("order_id");
        mCancelOrderPresenter = new CancelOrderPresenter(this);
    }

    private void initView() {
        tv_tips = ((TextView) findViewById(R.id.tv_tips));
        order_detail_reasonLayout = ((ViewGroup) findViewById(R.id.order_detail_reasonLayout));
        et_other_reason = ((EditText) findViewById(R.id.et_other_reason));
        home_password_confirm = ((TextView) findViewById(R.id.home_password_confirm));
        cancel_order_error = ((CheckBox) findViewById(R.id.cancel_order_error));
        cancel_order_replay = ((CheckBox) findViewById(R.id.cancel_order_replay));
        cancel_order_noNeed = ((CheckBox) findViewById(R.id.cancel_order_noNeed));
        cancel_order_Tremor = ((CheckBox) findViewById(R.id.cancel_order_Tremor));

        if (type.equals("1")) {  //我的接单
            tv_tips.setText("建议您与下单用户充分沟通，并如实反馈原因");
            cancel_order_Tremor.setVisibility(View.VISIBLE);  //手抖误抢单
            cancel_order_error.setText("看错路线");
            cancel_order_replay.setText("无法按预约时间出发");
            cancel_order_noNeed.setText("看错时间");
        } else {  //我的订单
            if (mOrderIsPay) {
                tv_tips.setText("建议您与接单用户充分沟通，并如实反馈原因");
            }
            cancel_order_error.setText("下单有误");
            cancel_order_replay.setText("重新下单");
            cancel_order_noNeed.setText("不需要了");
        }
    }

    private boolean canCofirm = false;
    private String reasonStr = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_password_confirm:
                for (int i = 0; i < order_detail_reasonLayout.getChildCount(); i++) {
                    CheckBox cb = (CheckBox) order_detail_reasonLayout.getChildAt(i);
                    if (cb.isChecked()) {
                        canCofirm = true;
                        reasonStr = cb.getText().toString();
                    }
                }
                if (!TextUtils.isEmpty(et_other_reason.getText().toString().trim())) {
                    canCofirm = true;
//                    if (TextUtils.isEmpty(reasonStr)) {
//                        reasonStr = et_other_reason.getText().toString().trim();
//                    } else {
//                        reasonStr = reasonStr + "，其他原因：" + et_other_reason.getText().toString().trim();
//                    }
                    reasonStr = et_other_reason.getText().toString().trim();
                }
                if (canCofirm) {
//                    提交取消订单原因到服务器
                    mCancelOrderPresenter.cancelOrder(type, order_id, reasonStr);
                } else {
                    T.showShort(this, "请带上您取消订单的原因");
                }

                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cancel_order_error:
                if (isChecked) {
                    cancel_order_replay.setChecked(false);
                    cancel_order_noNeed.setChecked(false);
                    cancel_order_Tremor.setChecked(false);
                }
                break;
            case R.id.cancel_order_replay:
                if (isChecked) {
                    cancel_order_error.setChecked(false);
                    cancel_order_noNeed.setChecked(false);
                    cancel_order_Tremor.setChecked(false);
                }
                break;
            case R.id.cancel_order_noNeed:
                if (isChecked) {
                    cancel_order_error.setChecked(false);
                    cancel_order_replay.setChecked(false);
                    cancel_order_Tremor.setChecked(false);
                }
                break;
            case R.id.cancel_order_Tremor:
                if (isChecked) {
                    cancel_order_error.setChecked(false);
                    cancel_order_replay.setChecked(false);
                    cancel_order_noNeed.setChecked(false);
                }
                break;
        }
    }

    @Override
    public void orderSuccessView() {
//        cancel_order_error.setChecked(false);
//        cancel_order_replay.setChecked(false);
//        cancel_order_noNeed.setChecked(false);
//        cancel_order_Tremor.setChecked(false);
//        et_other_reason.setText("");
        Intent intent = new Intent();
        intent.putExtra("reason", reasonStr);
        String time = (System.currentTimeMillis() / 1000L) + "";
        intent.putExtra("time", time);
        setResult(110, intent);
        finish();
    }
}
