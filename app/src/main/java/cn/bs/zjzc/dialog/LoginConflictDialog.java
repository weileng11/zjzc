package cn.bs.zjzc.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.SettingModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.ui.AvLogin;

/**
 * Created by Ming on 2016/7/14.
 */
public class LoginConflictDialog extends BaseActivity {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView btnConfirm;
    private Context mContext = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_normal);
        tvTitle = (TextView) findViewById(R.id.title);
        tvContent = ((TextView) findViewById(R.id.content));
        btnConfirm = ((TextView) findViewById(R.id.right_button));

        btnConfirm.setVisibility(View.VISIBLE);
        tvTitle.setText("警告");
        String msg = getIntent().getStringExtra("msg");
        if (!TextUtils.isEmpty(msg)) {
            tvContent.setText(msg);
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SettingModel().logout(new HttpTaskCallback<BaseResponse>() {
                    @Override
                    public void onTaskFailed(String errorInfo) {
                        startActivity(new Intent(mContext, AvLogin.class));
                        finishAll();
                    }

                    @Override
                    public void onTaskSuccess(BaseResponse data) {
                        startActivity(new Intent(mContext, AvLogin.class));
                        finishAll();
                    }
                });
            }
        });
    }
}
