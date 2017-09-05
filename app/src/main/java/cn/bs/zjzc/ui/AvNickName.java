package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.presenter.NickNamePresenter;
import cn.bs.zjzc.ui.view.INickNameView;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvNickName extends BaseActivity implements INickNameView {
    private static final int NICK_NAME_RESULT_CODE = 100;
    private Context mContext;
    private NickNamePresenter mNickNamePresenter;
    private EditText etNickName;
    private TextView btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_set_nick_name);
        mNickNamePresenter = new NickNamePresenter(this);
        initView();
        initEvents();
    }

    private void initEvents() {
        etNickName.addTextChangedListener(new TextWatcher() {
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
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals(etNickName.getText().toString(), App.LOGIN_USER.getName())) {
                    mNickNamePresenter.changeNickName(etNickName.getText().toString());
                } else {
                    finish();
                }
            }
        });
        canNext();
    }

    private void canNext() {
        if (etNickName.length() < 2) {
            btnSave.setEnabled(false);
        } else {
            btnSave.setEnabled(true);
        }
    }

    private void initView() {
        etNickName = ((EditText) findViewById(R.id.set_nick_et_name));
        btnSave = ((TextView) findViewById(R.id.set_nick_name_save));
        btnSave.setEnabled(false);
        etNickName.setText(App.LOGIN_USER.getName());
    }

    @Override
    public void changeSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
