package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.dialog.MyDialog;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.model.response.MyWalletResponse;
import cn.bs.zjzc.presenter.MyWalletPresenter;
import cn.bs.zjzc.ui.view.IMyWalletView;
import cn.bs.zjzc.util.T;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvMyWallet extends BaseActivity implements View.OnClickListener, IMyWalletView {

    private Context mContext = this;
    private MyWalletPresenter mMyWalletPresenter;
    private NormalOptionView optionAccountBalance;
    private NormalOptionView optionPoint;
    private NormalOptionView optionCoupon;

    private String myPoint = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_my_wallet);
        mMyWalletPresenter = new MyWalletPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        optionAccountBalance.setOnClickListener(this);
        optionPoint.setOnClickListener(this);
        optionCoupon.setOnClickListener(this);
    }

    private void initViews() {
        optionAccountBalance = ((NormalOptionView) findViewById(R.id.my_wallet_account_balance));
        optionPoint = ((NormalOptionView) findViewById(R.id.my_wallet_point));
        optionCoupon = ((NormalOptionView) findViewById(R.id.my_wallet_coupon));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMyWalletPresenter.getWalletInfo();
    }

    @Override
    public void onClick(View v) {
        if (!isLoadSuccess) {  //数据还没加载成功
            T.showShort(mContext, "钱包数据中...");
            return;
        }
        switch (v.getId()) {
            case R.id.my_wallet_account_balance:
                startActivity(new Intent(mContext, AvAccountBalance.class));
                break;
            case R.id.my_wallet_point:
                Intent intent = new Intent(mContext, AvMyPoint.class);
                intent.putExtra("point", myPoint);
                startActivity(intent);
                break;
            case R.id.my_wallet_coupon:
                startActivity(new Intent(mContext, AvMyCoupon.class));
                break;
        }
    }

    private boolean isLoadSuccess = false;

    @Override
    public void showWalletInfo(MyWalletResponse.DataBean data) {
        optionAccountBalance.setContent(data.money);
        optionPoint.setContent(data.points);
        myPoint = data.points;
        optionCoupon.setContent(data.coupon_num);
        isLoadSuccess = true;
    }
}
