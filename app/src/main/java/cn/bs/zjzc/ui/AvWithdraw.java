package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.presenter.WithdrawPresenter;
import cn.bs.zjzc.ui.view.IWithdrawView;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvWithdraw extends BaseActivity implements View.OnClickListener, IWithdrawView {
    private static final int CHANGE_ACCOUNT_REQUEST_CODE = 1;
    private Context mContext = this;
    private WithdrawPresenter mWithdrawPresenter;
    private TextView tvAccountBalance;
    private TextView tvMaxWithdrawalMoney;
    private TextView tvBankName;
    private TextView tvBankAccount;
    private EditText etWithdrawalMoney;
    private EditText etLoginPassword;
    private TextView btnCommit;
    private TextView btnChangeAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_withdrawal);
        mWithdrawPresenter = new WithdrawPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnChangeAccount.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    private void initViews() {
        tvAccountBalance = ((TextView) findViewById(R.id.withdrawal_account_balance));
        tvMaxWithdrawalMoney = ((TextView) findViewById(R.id.withdrawal_max_withdrawal_money));
        tvBankName = ((TextView) findViewById(R.id.withdrawal_bank_name));
        btnChangeAccount = ((TextView) findViewById(R.id.withdrawal_change_account));
        tvBankAccount = ((TextView) findViewById(R.id.withdrawal_bank_account_number));
        etWithdrawalMoney = ((EditText) findViewById(R.id.withdrawal_et_withdrawal_money));
        etLoginPassword = ((EditText) findViewById(R.id.withdrawal_et_login_password));
        btnCommit = ((TextView) findViewById(R.id.withdrawal_commit));

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        BankAccountInfoResponse.DataBean accountInfo = null;
        //如果已经绑定帐号
        if ((accountInfo = (BankAccountInfoResponse.DataBean) intent.getSerializableExtra("account_info")) == null) {
            //如果是新添加帐号
            accountInfo = (BankAccountInfoResponse.DataBean) intent.getSerializableExtra("add_account_info");
        }
        if (accountInfo == null) {
            showMsg("获取帐号信息失败");
            return;
        }
        updateData(accountInfo);
    }

    private void updateData(BankAccountInfoResponse.DataBean accountInfo) {
        tvAccountBalance.setText(accountInfo.money);
        tvMaxWithdrawalMoney.setText(accountInfo.withdraw);
        tvBankName.setText(accountInfo.rank);
        tvBankAccount.setText(accountInfo.acount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //更改银行卡帐号的返回结果
        if (requestCode == CHANGE_ACCOUNT_REQUEST_CODE && data != null) {
            updateData((BankAccountInfoResponse.DataBean) data.getSerializableExtra("add_account_info"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withdrawal_change_account:
                startActivityForResult(new Intent(mContext, AvAddAccount.class), CHANGE_ACCOUNT_REQUEST_CODE);
                break;
            case R.id.withdrawal_commit:
                withdraw();
                break;
        }
    }

    private void withdraw() {
        String password = etLoginPassword.getText().toString();
        String money = etWithdrawalMoney.getText().toString();
        if (password == null) {
            showMsg("请输入提现金额");
        }

        if (password == null) {
            showMsg("请输入密码");
        }
        mWithdrawPresenter.withdraw(password, money);
    }
}
