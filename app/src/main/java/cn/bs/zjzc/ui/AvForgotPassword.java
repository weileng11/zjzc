package cn.bs.zjzc.ui;

/**
 * Created by Ming on 2016/5/27.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.presenter.ForgotPasswordPresenter;
import cn.bs.zjzc.ui.view.IForgotPasswordView;
import cn.bs.zjzc.util.CheckCodeTimer;
import cn.bs.zjzc.util.DensityUtils;


public class AvForgotPassword extends BaseActivity implements View.OnClickListener, TextWatcher, IForgotPasswordView {

    private Context mContext = this;
    private ForgotPasswordPresenter mForgotPasswordPresenter;
    private EditText etPhoneNumber;
    private EditText etVerificationCode;
    private EditText etNewPassword;
    private EditText etRepeatPassword;
    private TextView btnGetCode;
    private TextView btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_forgot_password);
        mForgotPasswordPresenter = new ForgotPasswordPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnGetCode.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        etPhoneNumber.addTextChangedListener(this);
        etVerificationCode.addTextChangedListener(this);
        etNewPassword.addTextChangedListener(this);
        etRepeatPassword.addTextChangedListener(this);
    }

    private void initViews() {
        etPhoneNumber = ((EditText) findViewById(R.id.forget_password_et_phone_number));
        etVerificationCode = ((EditText) findViewById(R.id.forget_password_et_verification_code));
        etNewPassword = ((EditText) findViewById(R.id.forget_password_et_new_password));
        etRepeatPassword = ((EditText) findViewById(R.id.forget_password_et_repeat_password));
        btnGetCode = ((TextView) findViewById(R.id.forget_password_get_verification_code));
        btnConfirm = ((TextView) findViewById(R.id.forget_password_confirm));

        canNext();
    }

    @Override
    public void onClick(View v) {
        String phone = etPhoneNumber.getText().toString();
        switch (v.getId()) {
            case R.id.forget_password_get_verification_code:
                getCode(phone);
                break;
            case R.id.forget_password_confirm:
                String code = etVerificationCode.getText().toString();
                String passwd = etNewPassword.getText().toString();
                String rePasswd = etRepeatPassword.getText().toString();
                recoverPassword(phone, code, passwd, rePasswd);
                break;
        }
    }

    private void recoverPassword(String phone, String code, String passwd, String rePasswd) {
        if (!passwd.equals(rePasswd)) {
            showMsg("两次输入密码不一致");
            return;
        }
        mForgotPasswordPresenter.recoverPassword(phone, code, passwd, rePasswd);
    }

    private void getCode(String phone) {
        if (TextUtils.isEmpty(phone)) {
            showMsg("帐号不能为空");
            return;
        }

        if (!DensityUtils.isMobileNO(phone)) {
            showMsg("请输入正确的手机号");
            return;
        }
        mForgotPasswordPresenter.getVerificationCode(phone, "2");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        canNext();
    }


    @Override
    public void afterTextChanged(Editable s) {
    }

    private void canNext() {
        //电话号码长度不等于11,确认按钮不可用
        if (etPhoneNumber.length() != 11) {
            btnConfirm.setEnabled(false);
            return;
        }
        //验证码长度不等于4,确认按钮不可用
        if (etVerificationCode.length() != 4) {
            btnConfirm.setEnabled(false);
            return;
        }
        //密码长度小于6,确认按钮不可用
        if (etNewPassword.length() < 6) {
            btnConfirm.setEnabled(false);
            return;
        }
        //密码长度小于6,确认按钮不可用
        if (etRepeatPassword.length() < 6) {
            btnConfirm.setEnabled(false);
            return;
        }
        //全部满足,设置确认按钮可用
        btnConfirm.setEnabled(true);
    }

    @Override
    public void startCountDownTimer() {
        new CheckCodeTimer(btnGetCode).start();
    }
}