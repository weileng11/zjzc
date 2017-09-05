package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.presenter.LoginPresenter;
import cn.bs.zjzc.ui.view.ILoginView;

/**
 * Created by Ming on 2016/5/27.
 */
public class AvLogin extends BaseActivity implements View.OnClickListener, ILoginView, TextWatcher {

    private Context mContext = this;
    private LoginPresenter mLoginPresenter;

    private EditText etAccount;
    private EditText etPassword;
    private TextView btnForgetPassword;
    private CheckBox checkRememberPassword;
    private TextView BtnRegisterNow;
    private TextView btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter = new LoginPresenter(this);
        //检查是否登录,如果登录就直接进入主页
        mLoginPresenter.checkLoginStatus();

        setContentView(R.layout.av_login);
        initViews();
        initEvents();
    }

    private void initEvents() {
        BtnRegisterNow.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        etAccount.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
    }

    private void initViews() {
        etAccount = ((EditText) findViewById(R.id.login_et_account));
        etPassword = ((EditText) findViewById(R.id.login_et_password));
        checkRememberPassword = ((CheckBox) findViewById(R.id.login_remember_passwork));
        btnForgetPassword = ((TextView) findViewById(R.id.login_forget_password));
        BtnRegisterNow = ((TextView) findViewById(R.id.login_register_now));
        btnLogin = ((TextView) findViewById(R.id.btn_login));

        //检查之前登录是否以选择了记住密码
        mLoginPresenter.checkRememberPassword();
        canNext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_forget_password:
                startActivity(new Intent(mContext, AvForgotPassword.class));
                break;
            case R.id.login_register_now:
                startActivity(new Intent(mContext, AvRegister.class));
                break;
            case R.id.btn_login:
                mLoginPresenter.login(etAccount.getText().toString(), etPassword.getText().toString());
                break;
        }
    }

    private void canNext() {
        //如果帐号程度不是11位数字,密码长度小于6,则登录按钮不可用
        if (etAccount.length() != 11 || etPassword.length() < 6) {
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //输入变化时检查登录按钮是否可用
        canNext();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean isRememberPassword() {
        return checkRememberPassword.isChecked();
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(mContext, AvHome.class));
        finish();
    }

    @Override
    public void setAccount(String account) {
        etAccount.setText(account);
    }

    @Override
    public void setPassword(String password) {
        etPassword.setText(password);
    }

    @Override
    public void setRememberPassword(boolean isRemember) {
        checkRememberPassword.setChecked(isRemember);
    }
}
