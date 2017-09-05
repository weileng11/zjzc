package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.presenter.ModifyPasswordPresenter;
import cn.bs.zjzc.ui.view.IModifyPasswordView;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvModifyPassword extends BaseActivity implements IModifyPasswordView, TextWatcher {

    private Context mContext = this;
    private ModifyPasswordPresenter mModifyPasswordPresenter;
    private EditText etOldPassword;
    private EditText etNewPassword;
    private EditText etRepeatPassword;
    private TextView btnCommit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_modify_password);
        mModifyPasswordPresenter = new ModifyPasswordPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        etOldPassword.addTextChangedListener(this);
        etNewPassword.addTextChangedListener(this);
        etRepeatPassword.addTextChangedListener(this);
    }

    private void changePassword() {
        String org_passwd = etOldPassword.getText().toString();
        String passwd = etNewPassword.getText().toString();
        String repasswd = etRepeatPassword.getText().toString();
        if (!passwd.equals(repasswd)) {
            showMsg("两次输入密码不一致");
            return;
        }

        if (org_passwd.equals(passwd)) {
            showMsg("原密码不能与新密码相同");
            return;
        }
        mModifyPasswordPresenter.modifyPassword(org_passwd, passwd, repasswd);
    }

    private void initViews() {
        etOldPassword = ((EditText) findViewById(R.id.modify_password_old_password));
        etNewPassword = ((EditText) findViewById(R.id.modify_password_new_password));
        etRepeatPassword = ((EditText) findViewById(R.id.modify_password_repeat_password));
        btnCommit = ((TextView) findViewById(R.id.modify_password_commit));

        canNext();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        canNext();
    }

    private void canNext() {
        //密码长度小于6,确认按钮不可用
        if (etOldPassword.length() < 6) {
            btnCommit.setEnabled(false);
            return;
        }
        //密码长度小于6,确认按钮不可用
        if (etNewPassword.length() < 6) {
            btnCommit.setEnabled(false);
            return;
        }
        //密码长度小于6,确认按钮不可用
        if (etRepeatPassword.length() < 6) {
            btnCommit.setEnabled(false);
            return;
        }
        btnCommit.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
