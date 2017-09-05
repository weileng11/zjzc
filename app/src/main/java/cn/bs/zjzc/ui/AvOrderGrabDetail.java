package cn.bs.zjzc.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.model.response.BalanceResponse;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.AccountBalancePresenter;
import cn.bs.zjzc.socket.response.PushOrderResponse;
import cn.bs.zjzc.socket.service.SocketService;
import cn.bs.zjzc.ui.AvAddAccount;
import cn.bs.zjzc.ui.AvFundDetail;
import cn.bs.zjzc.ui.AvRecharge;
import cn.bs.zjzc.ui.AvRechargeDetail;
import cn.bs.zjzc.ui.AvWithdraw;
import cn.bs.zjzc.ui.AvWithdrawDetail;
import cn.bs.zjzc.util.T;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvOrderGrabDetail extends BaseActivity implements View.OnClickListener {

    private Context mContext = this;
    private TextView orderNumber;
    private TextView order_grab_time;
    private TextView order_grab_type;
    private TextView order_grab_money;
    private ViewGroup takeLayout;
    private TextView order_grab_address_take;
    private View order_line;
    private ViewGroup photoLayout;
    private TextView order_grab_address_info_take;
    private TextView order_grab_contacts_take;
    private TextView order_grab_contacts_address;
    private TextView order_grab_distance;
    private TextView order_grab_address_delivery;
    private TextView order_grab_floor_info_delivery;
    private TextView order_grab_contacts_delivery;
    private TextView order_grab_check_address_delivery;
    private TextView order_detail_service_lab;
    private TextView order_detail_remark;
    private TextView confirmGrabOrder;
    private PushOrderResponse mPushOrderResponse;

    private SocketService.SocketServiceBinder socketServiceBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            socketServiceBinder = (SocketService.SocketServiceBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_order_grab);
        bindService(new Intent(mContext, SocketService.class), connection, BIND_AUTO_CREATE);
        initViews();
        initEvents();
        initBasic();
    }

    private void initBasic() {
        mPushOrderResponse = (PushOrderResponse) getIntent().getSerializableExtra("pushOrderResponse");
        if (mPushOrderResponse == null) {
            T.showShort(mContext, "订单详情获取失败");
            return;
        }
        toShowDataView();
    }

    /**
     * 展示数据视图
     */
    private void toShowDataView() {
        orderNumber.setText("订单：" + mPushOrderResponse.number);
        order_grab_time.setText(mPushOrderResponse.place_time);
        order_grab_type.setText(getOrderType(mPushOrderResponse.type));
        order_grab_money.setText(mPushOrderResponse.money + "元");

        if (mPushOrderResponse.type.equals("2") || mPushOrderResponse.type.equals("3") || mPushOrderResponse.type.equals("4")) {
            takeLayout.setVisibility(View.GONE);
            order_detail_service_lab.setVisibility(View.VISIBLE);
            order_line.setVisibility(View.VISIBLE);
            if (mPushOrderResponse.type.equals("2")) {
//                order_detail_service_lab.setText("商品名称："+mPushOrderResponse.);
            } else if (mPushOrderResponse.type.equals("3")) {
//                order_detail_service_lab.setText("服务名称："+mPushOrderResponse.);
            } else if (mPushOrderResponse.type.equals("4")) {
//                order_detail_service_lab.setText("车友之家："+mPushOrderResponse.);
            }
        } else {
            order_grab_address_take.setText(mPushOrderResponse.take.address);
            order_grab_address_info_take.setText(mPushOrderResponse.take.add_address);
            order_grab_distance.setText("相距" + mPushOrderResponse.way + "KM");
        }

        order_grab_address_delivery.setText(mPushOrderResponse.receipt.address);
        order_grab_floor_info_delivery.setText(mPushOrderResponse.receipt.add_address);

//        备注
        String remarkStr = TextUtils.isEmpty(mPushOrderResponse.remark_txt) ? "备注：无" : "备注：" + mPushOrderResponse.remark_txt;
        order_detail_remark.setText(remarkStr);

//        备注下的照片
        for (int i = 0; i < photoLayout.getChildCount(); i++) {
            ImageView imageView = (ImageView) photoLayout.getChildAt(i);
            imageView.setVisibility(View.INVISIBLE);
            if (mPushOrderResponse.remark_img.size() - 1 >= i) {
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(this).load(RequestUrl.getImgPath(mPushOrderResponse.remark_img.get(i))).into(imageView);
            }
        }
        if (mPushOrderResponse.remark_img.size() == 0) {
            photoLayout.setVisibility(View.GONE);
        }


    }

    //    订单类型（1 外卖，2 代买，3 代办 , 4 车友，5 速递）
    private String getOrderType(String tag) {
        switch (tag) {
            case "1":
                return "外卖配送";
            case "2":
                return "代买服务";
            case "3":
                return "代办服务";
            case "4":
                return "车友之家";
            case "5":
                return "同城速递";
        }
        return "";
    }

    private void initEvents() {
        confirmGrabOrder.setOnClickListener(this);
        order_grab_contacts_address.setOnClickListener(this);
        order_grab_check_address_delivery.setOnClickListener(this);
    }

    private void initViews() {
        orderNumber = ((TextView) findViewById(R.id.order_grab_number));
        order_grab_time = ((TextView) findViewById(R.id.order_grab_time));
        order_grab_type = ((TextView) findViewById(R.id.order_grab_type));
        order_grab_money = ((TextView) findViewById(R.id.order_grab_money));
        takeLayout = ((ViewGroup) findViewById(R.id.order_grab_takeLayout));
        order_grab_address_take = ((TextView) findViewById(R.id.order_grab_address_take));
        order_grab_address_info_take = ((TextView) findViewById(R.id.order_grab_address_info_take));
        order_grab_contacts_take = ((TextView) findViewById(R.id.order_grab_contacts_take));
        order_grab_contacts_address = ((TextView) findViewById(R.id.order_grab_contacts_address));
        order_grab_distance = ((TextView) findViewById(R.id.order_grab_distance));
        order_grab_address_delivery = ((TextView) findViewById(R.id.order_grab_address_delivery));
        order_grab_floor_info_delivery = ((TextView) findViewById(R.id.order_grab_floor_info_delivery));
        order_grab_contacts_delivery = ((TextView) findViewById(R.id.order_grab_contacts_delivery));
        order_grab_check_address_delivery = ((TextView) findViewById(R.id.order_grab_check_address_delivery));
        order_line = findViewById(R.id.order_line);
        order_detail_service_lab = ((TextView) findViewById(R.id.order_detail_service_lab));
        order_detail_remark = ((TextView) findViewById(R.id.order_detail_remark));
        photoLayout = ((ViewGroup) findViewById(R.id.order_detail_photoLayout));
        confirmGrabOrder = ((TextView) findViewById(R.id.confirmGrabOrder));
    }

    private String x;
    private String y;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmGrabOrder:
                //{"action":"accept","id":"256"}
                JSONObject json = new JSONObject();
                try {
                    json.put("action", "accept");
                    json.put("id", mPushOrderResponse.id);
                    socketServiceBinder.sendMsg(json);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.order_grab_contacts_address:  //查看送货地址
                x = mPushOrderResponse.take.x;
                y = mPushOrderResponse.take.y;
                readAddressInMap();
                break;
            case R.id.order_grab_check_address_delivery: //查看取货地址
                x = mPushOrderResponse.receipt.x;
                y = mPushOrderResponse.receipt.y;
                readAddressInMap();
                break;
        }
    }

    private void readAddressInMap() {
        Intent intent = new Intent(this, AvAddressInMap.class);
        intent.putExtra("x", x);
        intent.putExtra("y", y);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
