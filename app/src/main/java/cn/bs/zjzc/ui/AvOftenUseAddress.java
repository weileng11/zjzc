package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.OftenUseAddressResponse;
import cn.bs.zjzc.presenter.OftenUseAddressPresenter;
import cn.bs.zjzc.ui.view.IOftenUseAddressView;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvOftenUseAddress extends BaseActivity implements View.OnClickListener, IOftenUseAddressView {
    private Context mContext = this;
    private OftenUseAddressPresenter mOftenUseAddressPresenter;
    private AutoFrameLayout homeLayout;
    private TextView tvHomeTitle;
    private AutoLinearLayout homeAddressLayout;
    private TextView tvHomeAddress;
    private TextView tvHomeAddAddress;
    private AutoFrameLayout companyLayout;
    private TextView tvCompanyTitle;
    private AutoLinearLayout companyAddressLayout;
    private TextView tvCompanyAddress;
    private TextView tvCompanyAddAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_often_use_address);
        mOftenUseAddressPresenter = new OftenUseAddressPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        homeLayout.setOnClickListener(this);
        companyLayout.setOnClickListener(this);
    }

    private void initViews() {
        homeLayout = ((AutoFrameLayout) findViewById(R.id.home_layout));
        tvHomeTitle = ((TextView) findViewById(R.id.tv_home_title));

        homeAddressLayout = ((AutoLinearLayout) findViewById(R.id.home_address_info_layout));
        tvHomeAddress = ((TextView) findViewById(R.id.tv_home_address));
        tvHomeAddAddress = ((TextView) findViewById(R.id.tv_home_add_address));

        companyLayout = ((AutoFrameLayout) findViewById(R.id.company_layout));
        tvCompanyTitle = ((TextView) findViewById(R.id.tv_company_title));

        companyAddressLayout = ((AutoLinearLayout) findViewById(R.id.company_address_info_layout));
        tvCompanyAddress = ((TextView) findViewById(R.id.tv_company_address));
        tvCompanyAddAddress = ((TextView) findViewById(R.id.tv_company_add_address));


    }

    @Override
    protected void onResume() {
        super.onResume();
        mOftenUseAddressPresenter.getHomeAddress();
        mOftenUseAddressPresenter.getCompanyAddress();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, AvInputOftenAddress.class);
        switch (v.getId()) {
            case R.id.home_layout:
                intent.setAction(AllConsts.IntentAction.actionEditHomeAddress);
                break;
            case R.id.company_layout:
                intent.setAction(AllConsts.IntentAction.actionEditCompanyAddress);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void setHomeAddress(OftenUseAddressResponse.DataBean data) {
        Log.i(TAG, "setHomeAddress: " + data);
        tvHomeTitle.setTextColor(getResources().getColor(R.color.zjzc_orange_light));
        tvHomeTitle.setText("家");
        tvHomeTitle.setTextSize(26);

        homeAddressLayout.setVisibility(View.VISIBLE);
        tvHomeAddress.setText(data.address);
        tvHomeAddAddress.setText(data.add_address);
    }

    @Override
    public void setCompanyAddress(OftenUseAddressResponse.DataBean data) {
        Log.i(TAG, "setCompanyAddress: " + data);
        tvCompanyTitle.setTextColor(getResources().getColor(R.color.zjzc_orange_light));
        tvCompanyTitle.setText("公司");
        tvCompanyTitle.setTextSize(26);

        companyAddressLayout.setVisibility(View.VISIBLE);
        tvCompanyAddress.setText(data.address);
        tvCompanyAddAddress.setText(data.add_address);
    }

    @Override
    public void hideHomeAddress() {
        tvHomeTitle.setTextColor(getResources().getColor(R.color.zjzc_color_grey_light));
        tvHomeTitle.setText("请输入家庭住址");
        tvHomeTitle.setTextSize(34);
        homeAddressLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideCompanyAddress() {
        tvCompanyTitle.setTextColor(getResources().getColor(R.color.zjzc_color_grey_light));
        tvCompanyTitle.setText("请输入公司地址");
        tvCompanyTitle.setTextSize(34);
        companyAddressLayout.setVisibility(View.GONE);
    }


}
