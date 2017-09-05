package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvPhoneNumber extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_phone_number);

        TextView phone = (TextView) findViewById(R.id.phone_number_info);
        phone.setText("手机号" + App.LOGIN_USER.getPhone());
    }
}
