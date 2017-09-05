package cn.bs.zjzc.ui;

/**
 * Created by Ming on 2016/5/27.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;


public class AvSetPassword extends BaseActivity implements View.OnClickListener {

    private Context mContext = this;
    private EditText passwordConfirm;
    private EditText nickName;
    private EditText password;
    private EditText invitationCode;
    private CheckBox agreeRegisterProtocol;
    private TextView done;
    private TextView registerProtocol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_set_password);
        initViews();
        initEvents();
    }

    private void initEvents() {
        registerProtocol.setOnClickListener(this);
        done.setOnClickListener(this);
    }

    private void initViews() {
        nickName = ((EditText) findViewById(R.id.set_password_et_nickname));
        password = ((EditText) findViewById(R.id.set_password_et_password));
        passwordConfirm = ((EditText) findViewById(R.id.set_password_et_password_confirm));
        invitationCode = ((EditText) findViewById(R.id.set_password_et_invitation_code));
        agreeRegisterProtocol = ((CheckBox) findViewById(R.id.set_password_agree_register_protocol));
        registerProtocol = ((TextView) findViewById(R.id.set_password_register_protocol));
        done = ((TextView) findViewById(R.id.set_password_done));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_password_register_protocol:
                break;
            case R.id.set_password_done:
        }
    }
}