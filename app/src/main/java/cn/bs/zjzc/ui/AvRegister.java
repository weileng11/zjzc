package cn.bs.zjzc.ui;

/**
 * Created by Ming on 2016/5/27.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.presenter.RegisterPresenter;
import cn.bs.zjzc.ui.view.IRegisterView;
import cn.bs.zjzc.util.CheckCodeTimer;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.T;


public class AvRegister extends BaseActivity implements View.OnClickListener, IRegisterView, TextWatcher {

    private Context mContext = this;
    private RegisterPresenter mRegisterPresenter;

    private EditText etAccount;
    private EditText etCode;
    private TextView btnGetCode;
    private TextView btnGoLogin;
    private TextView btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_register);
        mRegisterPresenter = new RegisterPresenter(this);
        initViews();
        initEvents();

    }

    private void initEvents() {
        btnGetCode.setOnClickListener(this);
        btnGoLogin.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        etAccount.addTextChangedListener(this);
        etCode.addTextChangedListener(this);
    }

    private void initViews() {
        etAccount = ((EditText) findViewById(R.id.register_et_account));
        etCode = ((EditText) findViewById(R.id.register_et_verification_code));
        btnGetCode = ((TextView) findViewById(R.id.register_get_verification_code));
        btnGoLogin = ((TextView) findViewById(R.id.register_go_login));
        btnNext = ((TextView) findViewById(R.id.register_next));

        canNext();
    }

    private void canNext() {
        //如果帐号程度不是11位数字,验证码长度小于4,则下一步按钮不可用
        if (etAccount.length() == 11 && etCode.length() == 4) {
            btnNext.setEnabled(true);
        } else {
            btnNext.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        String phone = etAccount.getText().toString();
        switch (v.getId()) {
            case R.id.register_get_verification_code:
                getCode(phone);
                break;
            case R.id.register_go_login:
                finish();
                break;
            case R.id.register_next:
                // next("", "");
                next(phone);
                break;
        }
    }

    //进入注册下一步
    private void next(String phone) {
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            showMsg("帐号不能为空");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            showMsg("验证码不能为空");
            return;
        }

        if (!DensityUtils.isMobileNO(phone)) {
            showMsg("请输入正确的手机号");
            return;
        }
        mRegisterPresenter.next(phone, code);
    }

    //获取验证码
    private void getCode(String phone) {
        if (TextUtils.isEmpty(phone)) {
            showMsg("帐号不能为空");
            return;
        }

        if (!DensityUtils.isMobileNO(phone)) {
            showMsg("请输入正确的手机号");
            return;
        }
        mRegisterPresenter.getVerificationCode(phone);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //判断能否进行下一步
        canNext();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }


    @Override
    public void startCountDownTimer() {
        //重新获取验证码倒计时
        new CheckCodeTimer(btnGetCode).start();
    }

    @Override
    public void next(String account, String token) {
        //确定验证码成功打开下一步页面
        Intent intent = new Intent(mContext, AvRegisterNext.class);
        intent.putExtra("acount", account);
        intent.putExtra("token", token);
        startActivity(intent);
    }


}