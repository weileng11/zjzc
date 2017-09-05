package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvInviteFriend extends BaseActivity {

    private TopBarView topBar;
    private TextView invitationCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_invite_friend);
        initViews();
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.invite_friend_top_bar));
        invitationCode = ((TextView) findViewById(R.id.invite_friend_invitation_code));
    }
}
