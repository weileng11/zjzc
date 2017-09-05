package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
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
public class AvUserLevel extends BaseActivity {
    private Context mContext = this;
    private TextView userLevel;
    private TextView userAccountNumber;
    private TextView userUpgradeInfo;
    private TextView useRule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_user_level);
        initViews();
        initEvents();
    }

    private void initViews() {
        userLevel = ((TextView) findViewById(R.id.user_level));
        userAccountNumber = ((TextView) findViewById(R.id.user_level_account_number));
        userUpgradeInfo = ((TextView) findViewById(R.id.user_level_upgrade_info));
        useRule = ((TextView) findViewById(R.id.user_level_user_rule));

        userLevel.setText(App.LOGIN_USER.getLevel());
        userAccountNumber.setText(App.LOGIN_USER.getPhone());
        setUpgradeInfo();
    }

    private void setUpgradeInfo() {
        int points = 0;
        try {
            points = Integer.parseInt(App.LOGIN_USER.getPoints());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (points < 100) {
            userUpgradeInfo.setText("仍需" + (100 - points) + "积分升级到V1");
            return;
        }

        if (points >= 100 && points < 150) {
            userUpgradeInfo.setText("仍需" + (150 - points) + "积分升级到V2");
            return;
        }

        if (points >= 150 && points < 250) {
            userUpgradeInfo.setText("仍需" + (250 - points) + "积分升级到V3");
            return;
        }

        if (points >= 250 && points < 350) {
            userUpgradeInfo.setText("仍需" + (350 - points) + "积分升级到V4");
            return;
        }

        if (points >= 350 && points < 500) {
            userUpgradeInfo.setText("仍需" + (500 - points) + "积分升级到V5");
            return;
        }

        if (points >= 500) {
            userUpgradeInfo.setText("当前积分：" + points);
            return;
        }


    }

    private void initEvents() {
        useRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AvLevelRules.class));
            }
        });
    }
}
