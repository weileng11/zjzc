package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.presenter.AccountBalancePresenter;
import cn.bs.zjzc.ui.view.IAccountBalanceView;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvAccountBalance extends BaseActivity implements View.OnClickListener, IAccountBalanceView {

    private Context mContext = this;
    private AccountBalancePresenter mAccountBalancePresenter;
    private TextView tvBalance;//余额
    private TextView tvEarning;//收益
    private TextView tvMaxWithdrawalMoney;//可提现金额
    private NormalOptionView optionRechargeDetail;//充值明细
    private NormalOptionView optionWithdrawalDetail;//提现明细
    private NormalOptionView optionReceiptPaymentDetail;//收支明细
    private TextView btnRecharge;//充值
    private TextView btnWithdraw;//提现
    private BankAccountInfoResponse.DataBean mAccountInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountBalancePresenter = new AccountBalancePresenter(this);

        setContentView(R.layout.av_account_balance);
        initViews();
        initEvents();
    }

    private void initEvents() {
        optionRechargeDetail.setOnClickListener(this);
        optionWithdrawalDetail.setOnClickListener(this);
        optionReceiptPaymentDetail.setOnClickListener(this);
        btnRecharge.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);
    }

    private void initViews() {
        tvBalance = ((TextView) findViewById(R.id.account_balance_money));
        tvEarning = ((TextView) findViewById(R.id.account_balance_earning));
        tvMaxWithdrawalMoney = ((TextView) findViewById(R.id.account_balance_max_withdrawal_money));
        optionRechargeDetail = ((NormalOptionView) findViewById(R.id.account_balance_recharge_details));
        optionWithdrawalDetail = ((NormalOptionView) findViewById(R.id.account_balance_withdrawal_details));
        optionReceiptPaymentDetail = ((NormalOptionView) findViewById(R.id.account_balance_receipt_payment_details));
        btnRecharge = ((TextView) findViewById(R.id.account_balance_recharge));
        btnWithdraw = ((TextView) findViewById(R.id.account_balance_withdraw));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAccountBalancePresenter.getBalanceInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_balance_recharge_details:
                startActivity(new Intent(mContext, AvRechargeDetail.class));
                break;
            case R.id.account_balance_withdrawal_details:
                startActivity(new Intent(mContext, AvWithdrawDetail.class));
                break;
            case R.id.account_balance_receipt_payment_details:
                startActivity(new Intent(mContext, AvFundDetail.class));
                break;
            case R.id.account_balance_recharge:
                Intent intent = new Intent(mContext, AvRecharge.class);
                intent.putExtra("balance", tvBalance.getText().toString());
                startActivity(intent);
                break;
            case R.id.account_balance_withdraw:
                mAccountBalancePresenter.getBankAccountInfo();
                //startActivity(new Intent(mContext, AvWithdraw.class));
                //startActivity(new Intent(mContext, AvAddAccount.class));
                break;

        }
    }

    @Override
    public void showBalanceInfo(BalanceResponse.DataBean data) {
        tvBalance.setText(data.money);
        tvMaxWithdrawalMoney.setText(data.withdrawal);
    }

    @Override
    public void startWithdrawActivity(BankAccountInfoResponse.DataBean data) {
        Intent intent = new Intent(mContext, AvWithdraw.class);
        intent.putExtra("account_info", data);
        startActivity(intent);
    }

    @Override
    public void startAddAccountActivity() {
        Intent intent = new Intent(mContext, AvAddAccount.class);
        startActivity(intent);
    }
}
