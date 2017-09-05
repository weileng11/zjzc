package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.zhy.autolayout.AutoRelativeLayout;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.bean.EditAddressRequestBody;
import cn.bs.zjzc.presenter.OftenAddressComplementPresenter;
import cn.bs.zjzc.ui.view.IOftenAddressComplementView;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvOftenAddressComplement extends BaseActivity implements IOftenAddressComplementView {
    public String mAction;
    private OftenAddressComplementPresenter mOftenAddressComplementPresenter;
    private Context mContext = this;
    private TextView selectAddress;
    private TopBarView topBar;
    private EditText floorInfo;
    private AutoRelativeLayout contactsLayout;
    private TextView confirm;
    private PoiInfo addressInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_infomation_complement);
        initBase();
        initViews();
        initEvents();

    }

    private void initBase() {
        mOftenAddressComplementPresenter = new OftenAddressComplementPresenter(this);
        Intent intent = getIntent();
        mAction = intent.getAction();
        addressInfo = intent.getParcelableExtra(("address_info"));
    }

    private void initEvents() {
        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AvHistory.class));
            }
        });
        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvInputAddress.class);
                startActivityForResult(intent, 1);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditAddressRequestBody requestBody = new EditAddressRequestBody();
                requestBody.add_address = floorInfo.getText().toString();
                requestBody.address = addressInfo.address;
                requestBody.city_id = App.LOGIN_USER.getCity(addressInfo.city).id;
                requestBody.province_id = App.LOGIN_USER.getProvince(addressInfo.city).id;
                requestBody.token = App.LOGIN_USER.getToken();
                requestBody.x = String.valueOf(addressInfo.location.latitude);
                requestBody.y = String.valueOf(addressInfo.location.longitude);
                if (TextUtils.equals(mAction, AllConsts.IntentAction.actionEditCompanyAddress)) {
                    requestBody.type = "2";
                } else if (TextUtils.equals(mAction, AllConsts.IntentAction.actionEditHomeAddress)) {
                    requestBody.type = "1";
                }
                Log.i(TAG, "onClick: " + requestBody);
                mOftenAddressComplementPresenter.editOftenAddress(requestBody, addressInfo.city);
            }
        });
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.info_complement_top_bar));
        selectAddress = (TextView) findViewById(R.id.info_complement_select_address);
        floorInfo = ((EditText) findViewById(R.id.info_complement_floor_info));
        confirm = ((TextView) findViewById(R.id.info_complement_btn_confirm));

        contactsLayout = ((AutoRelativeLayout) findViewById(R.id.info_complement_contacts_layout));
        contactsLayout.setVisibility(View.GONE);
        selectAddress.setText(addressInfo.address);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectAddress.setText(data.getStringExtra("address"));
        }
    }

    @Override
    public void showEditSuccess() {
        finish();
    }
}
