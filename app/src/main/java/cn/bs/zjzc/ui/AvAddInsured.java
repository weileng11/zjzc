package cn.bs.zjzc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.AddInsuredResponse;
import cn.bs.zjzc.presenter.AddInsuredPresenter;
import cn.bs.zjzc.ui.view.IAddInsuredView;
import cn.bs.zjzc.util.L;

/**
 * 添加保价
 * Created by Ming on 2016/5/24.
 */
public class AvAddInsured extends BaseActivity implements IAddInsuredView {

    private TopBarView topBar;
    private EditText etPrice;
    private TextView fee;
    private TextView confirm;

    private int mType;//订单类型
    private String insured_money;//保价费
    private double count;

    private AddInsuredPresenter presenter;
    private AddInsuredResponse.DataBean dataWay;//保价费计算方式

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_add_insured);
        initViews();

        Intent intent = getIntent();
        mType = intent.getIntExtra("mType", 5);
        insured_money = intent.getStringExtra("insured_money");

        getInsuredWay();
    }

    //获取保价费计算方式
    private void getInsuredWay() {
        dataWay = App.LOGIN_USER.getData();
        if (dataWay != null) {
            if (insured_money != null) {
                etPrice.setText(String.valueOf(Double.parseDouble(insured_money) * 100 / Double.parseDouble(dataWay.prop)));
                fee.setText("保价费：" + insured_money + "元");
            }
        } else {
            presenter = new AddInsuredPresenter(this);
            presenter.getAddInsuredWay();
        }
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.add_insured_top_bar));
        etPrice = ((EditText) findViewById(R.id.add_insured_et_price));
        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    double ss = Double.parseDouble(s.toString());
                    if (ss > 50) {
                        ss = 50;
                        etPrice.setText("50");
                    }
                    if (ss < 0) {
                        ss = 0;
                        etPrice.setText("0");
                    }
                    count = ss * Double.parseDouble(dataWay.prop) / 100;
                    fee.setText("保价费：" + count + "元");
                }
            }
        });
        fee = ((TextView) findViewById(R.id.add_insured_fee));
        confirm = ((TextView) findViewById(R.id.add_insured_confirm));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mType) {

                    case 5://同城速递
                        if (!isInputEmpty(etPrice)) {
                            if (Double.parseDouble(etPrice.getText().toString()) >= 0) {
                                AvHome.orderParametersS[4].setInsured_money(count + "");
                            } else {
                                AvHome.orderParametersS[4].setInsured_money("0");
                            }
                        }
                        finish();
                        break;
                }
            }
        });
    }

    //成功获取保价费计算方式
    @Override
    public void getInsuredWaySuccess(AddInsuredResponse.DataBean data) {
        dataWay = data;
        L.d("MGC", "保价费计算方式:" + dataWay.toString());
        etPrice.setHint("物品价值≤" + dataWay.max_money + "元");
        if (insured_money != null) {
            etPrice.setText(String.valueOf(Double.parseDouble(insured_money) * 100 / Double.parseDouble(dataWay.prop)));
            fee.setText("保价费：" + insured_money + "元");
        }
    }
}
