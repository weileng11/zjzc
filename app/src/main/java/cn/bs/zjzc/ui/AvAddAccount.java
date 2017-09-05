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
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.model.response.BankListResponse;
import cn.bs.zjzc.popupwindow.NormalPopuWindow;
import cn.bs.zjzc.presenter.AddAccountPresenter;
import cn.bs.zjzc.ui.view.IAddAccountView;

/**
 * Created by Ming on 2016/5/31.
 */
public class AvAddAccount extends BaseActivity implements View.OnClickListener, IAddAccountView {
    private static final int SELECT_BANK_REQUEST_CODE = 1;
    private Context mContext = this;
    private AddAccountPresenter mAddAccountPresenter;
    private TopBarView topBar;
    private EditText etRealName;
    private TextView btnSelectAccount;
    private EditText etAccountNumber;
    private TextView btnCommit;
    private BankListResponse.DataBean bankData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_add_account);
        mAddAccountPresenter = new AddAccountPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnSelectAccount.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.add_account_top_bar));
        etRealName = ((EditText) findViewById(R.id.add_account_real_name));
        btnSelectAccount = ((TextView) findViewById(R.id.add_account_select_account));
        etAccountNumber = ((EditText) findViewById(R.id.add_account_et_account_number));
        btnCommit = ((TextView) findViewById(R.id.add_account_commit));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_BANK_REQUEST_CODE && data != null) {
            bankData = (BankListResponse.DataBean) data.getSerializableExtra("selected_bank");
            btnSelectAccount.setText(bankData.name);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_account_select_account:
                startActivityForResult(new Intent(mContext, AvSelectBank.class), SELECT_BANK_REQUEST_CODE);
                break;
            case R.id.add_account_commit:
                if (bankData == null) {
                    showMsg("请选择帐号");
                }
                mAddAccountPresenter.addAccount(bankData,
                        etAccountNumber.getText().toString(),
                        etRealName.getText().toString());
                break;
        }
    }

    @Override
    public void startWithdrawActivity(BankAccountInfoResponse.DataBean data) {
        Intent intent = new Intent(mContext, AvWithdraw.class);
        intent.putExtra("add_account_info", data);
        startActivity(intent);
        finish();
    }
}
