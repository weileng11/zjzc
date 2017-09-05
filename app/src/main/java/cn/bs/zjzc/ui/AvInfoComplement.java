package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.bean.HistoryAddressParamters;
import cn.bs.zjzc.bean.OrderParameters;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.util.DBUtils;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.T;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvInfoComplement extends BaseActivity {

    private Context mContext = this;
    private TextView selectAddress;
    private TopBarView topBar;
    private EditText floorInfo;
    private EditText etName;
    private EditText etPhoneNumber;
    private TextView confirm;

    private String address;
    private int type;//setTake setReceipt
    private int mType;//订单类型

    private DBUtils db;
    private HistoryAddressParamters historyAddressParamters = new HistoryAddressParamters();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_infomation_complement);
        initViews();
        initEvents();

        db = new DBUtils(this);
        db.open();

        Intent intent = getIntent();
        historyAddressParamters = (HistoryAddressParamters) intent.getSerializableExtra("HistoryAddressParamters");
        address = intent.getStringExtra("address");
        type = intent.getIntExtra("type", 1);
        mType = intent.getIntExtra("mType", 1);
        if (address != null) {
            selectAddress.setText(address);
            setDataView(AvHome.orderParametersS[mType - 1], type);
        } else {
            selectAddress.setText(historyAddressParamters.address);
            if (historyAddressParamters.add_address != null)
                floorInfo.setText(historyAddressParamters.add_address);
        }
    }

    private void setDataView(OrderParameters params, int types) {
        if (types == 1) {
            floorInfo.setText(params.getTake_add_address());
            etName.setText(params.getTake_name());
            etPhoneNumber.setText(params.getTake_phone());
        } else if (types == 2) {
            floorInfo.setText(params.getReceipt_add_address());
            etName.setText(params.getReceipt_name());
            etPhoneNumber.setText(params.getReceipt_phone());
        }
    }

    private void setData(OrderParameters params, int types) {
        if (historyAddressParamters == null) {
            historyAddressParamters = new HistoryAddressParamters();
        }
        historyAddressParamters.address = selectAddress.getText().toString();
        historyAddressParamters.add_address = floorInfo.getText().toString();
        historyAddressParamters.name = etName.getText().toString();
        historyAddressParamters.phone = etPhoneNumber.getText().toString();
        String provinceId = historyAddressParamters.province_id;
        String cityId = historyAddressParamters.city_id;
        String x = historyAddressParamters.x;
        String y = historyAddressParamters.y;
        if (types == 1) {
            params.setTake_address(historyAddressParamters.address);
            params.setTake_add_address(historyAddressParamters.add_address);
            params.setTake_name(historyAddressParamters.name);
            params.setTake_phone(historyAddressParamters.phone);
            if (x != null && y != null) {
                params.setTake_x(x);
                params.setTake_y(y);
            }else{
                historyAddressParamters.x = params.getTake_x();
                historyAddressParamters.y = params.getTake_y();
            }
            if (provinceId != null && cityId != null) {
                params.setTake_province_id(provinceId);
                params.setTake_city_id(cityId);
            }else{
                historyAddressParamters.province_id = params.getTake_province_id();
                historyAddressParamters.city_id = params.getTake_city_id();
            }
        } else if (types == 2) {
            params.setReceipt_address(historyAddressParamters.address);
            params.setReceipt_add_address(historyAddressParamters.add_address);
            params.setReceipt_name(historyAddressParamters.name);
            params.setReceipt_phone(historyAddressParamters.phone);
            if (x != null && y != null) {
                params.setReceipt_x(x);
                params.setReceipt_y(y);
            }else{
                historyAddressParamters.x = params.getReceipt_x();
                historyAddressParamters.y = params.getReceipt_y();
            }
            if (provinceId != null && cityId != null) {
                params.setReceipt_province_id(provinceId);
                params.setReceipt_city_id(cityId);
            }else{
                historyAddressParamters.province_id = params.getReceipt_province_id();
                historyAddressParamters.city_id = params.getReceipt_city_id();
            }
        }
        if (db.queryOneData("'" + new Gson().toJson(historyAddressParamters) + "'") == null) {
            db.insert(new Gson().toJson(historyAddressParamters));
        }
    }

    private void initEvents() {
        topBar.setRightClickListener(new View.OnClickListener() {//历史记录
            @Override
            public void onClick(View v) {//历史地址记录
                Intent intent = new Intent(mContext, AvHistory.class);
                startActivityForResult(intent, 2);
            }
        });
        selectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//选择地址
                Intent intent = new Intent(mContext, AvInputAddress.class);
                intent.putExtra("forResult", true);
                intent.putExtra("type", 1);
                intent.putExtra("mType", mType);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.info_complement_top_bar));
        selectAddress = (TextView) findViewById(R.id.info_complement_select_address);
        floorInfo = ((EditText) findViewById(R.id.info_complement_floor_info));
        etName = ((EditText) findViewById(R.id.info_complement_et_name));
        etPhoneNumber = ((EditText) findViewById(R.id.info_complement_et_phone_number));
        confirm = ((TextView) findViewById(R.id.info_complement_btn_confirm));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regular()) {
                    int index = mType - 1;
                    setData(AvHome.orderParametersS[index], type);
                    finish();
                }
            }
        });
    }

    //空值验证&正则验证
    private boolean regular() {
        if (isInputEmpty(etName)) {
            T.showShort(AvInfoComplement.this, "请输入联系人姓名.");
            return false;
        }
        if (isInputEmpty(etPhoneNumber)) {
            T.showShort(AvInfoComplement.this, "请输入联系人电话.");
            return false;
        }
        if (!isInputMobileNO(etPhoneNumber)) {
            T.showShort(AvInfoComplement.this, "请输入正确的联系人电话.");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1://选择地址
                    historyAddressParamters = (HistoryAddressParamters) data.getSerializableExtra("HistoryAddressParamters");
                    selectAddress.setText(historyAddressParamters.address);
                    if (historyAddressParamters.add_address != null)
                        floorInfo.setText(historyAddressParamters.add_address);
                    break;
                case 2://选择历史地址
                    historyAddressParamters = (HistoryAddressParamters) data.getSerializableExtra("HistoryAddressParamters");
                    selectAddress.setText(historyAddressParamters.address);
                    floorInfo.setText(historyAddressParamters.add_address);
                    etPhoneNumber.setText(historyAddressParamters.phone);
                    etName.setText(historyAddressParamters.name);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d("OrderParameters", "Info:" + historyAddressParamters);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
