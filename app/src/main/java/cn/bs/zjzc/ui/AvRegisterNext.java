package cn.bs.zjzc.ui;

/**
 * Created by Ming on 2016/5/27.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.bean.RegisterRequestBody;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.RegisterNextPresenter;
import cn.bs.zjzc.ui.view.IRegisterNextView;


public class AvRegisterNext extends BaseActivity implements View.OnClickListener, IRegisterNextView, TextWatcher {

    private Context mContext = this;
    private RegisterNextPresenter mRegisterNextPresenter;

    private EditText etRepeatPassword;
    private EditText etNickName;
    private EditText etPassword;
    private EditText etInvitationCode;
    private CheckBox checkAcceptUserProtocol;
    private TextView btnCommit;
    private TextView btnRegisterProtocol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_register_next);
        mRegisterNextPresenter = new RegisterNextPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnRegisterProtocol.setOnClickListener(this);
        btnCommit.setOnClickListener(this);

        //输入昵称,密码时检查是否能能点击注册按钮
        etNickName.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        etRepeatPassword.addTextChangedListener(this);

        //注册协议勾选状态发生变检查是否能能点击注册按钮
        checkAcceptUserProtocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                canNext();
            }
        });
    }

    private void initViews() {
        etNickName = ((EditText) findViewById(R.id.register_next_et_nickname));
        etPassword = ((EditText) findViewById(R.id.register_next_et_password));
        etRepeatPassword = ((EditText) findViewById(R.id.register_next_et_password_confirm));
        etInvitationCode = ((EditText) findViewById(R.id.register_next_et_invitation_code));
        checkAcceptUserProtocol = ((CheckBox) findViewById(R.id.register_next_agree_register_protocol));
        btnRegisterProtocol = ((TextView) findViewById(R.id.register_next_register_protocol));
        btnCommit = ((TextView) findViewById(R.id.register_next_commit));

        canNext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_next_register_protocol:
                Intent webIntent = new Intent(mContext, AvCommdWebView.class);
                webIntent.putExtra("url", RequestUrl.getWebRequestPath(RequestUrl.WebPath.userAgreement));
                webIntent.putExtra("title", "用户注册协议");
                startActivity(webIntent);
                break;
            case R.id.register_next_commit:
                String passwd = etPassword.getText().toString();//密码
                String confirmPassword = etRepeatPassword.getText().toString();//确认密码
                if (!passwd.equals(confirmPassword)) {
                    showMsg("两次输入的密码不一致");
                    return;
                }

                Intent intent = getIntent();
                String token = intent.getStringExtra("token");//令牌
                String acount = intent.getStringExtra("acount");//帐号
                String name = etNickName.getText().toString();//昵称
                String code = etInvitationCode.getText().toString();//邀请码
                mRegisterNextPresenter.register(new RegisterRequestBody(token, acount, name, passwd, confirmPassword, code));
                break;
        }
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
        //昵称长度小于2,确认按钮不可用
        if (etNickName.length() < 2) {
            btnCommit.setEnabled(false);
            return;
        }
        //密码长度小于6,确认按钮不可用
        if (etPassword.length() < 6) {
            btnCommit.setEnabled(false);
            return;
        }
        //密码长度小于6,确认按钮不可用
        if (etRepeatPassword.length() < 6) {
            btnCommit.setEnabled(false);
            return;
        }
        //未同意用户注册协议,确认按钮不可用
        if (!checkAcceptUserProtocol.isChecked()) {
            btnCommit.setEnabled(false);
            return;
        }
        btnCommit.setEnabled(true);
    }

    @Override
    public void startHomeActivity() {
        //注册成功自动登录并进入主页
        startActivity(new Intent(mContext, AvHome.class));
        //销毁全部activity
        finishAll();
    }

    @Override
    public void startLoginActivity() {
        finishTop(2);
    }
}