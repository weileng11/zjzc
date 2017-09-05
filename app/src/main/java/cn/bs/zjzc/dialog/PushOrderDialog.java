package cn.bs.zjzc.dialog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.socket.response.PushOrderResponse;
import cn.bs.zjzc.socket.service.SocketService;
import cn.bs.zjzc.util.CheckCodeTimer;
import cn.bs.zjzc.util.ViewUtils;

/**
 * Created by Ming on 2016/7/15.
 */
public class PushOrderDialog extends BaseActivity {
    private Context mContext = this;

    private TextView deliveryTime;
    private TextView tvType;
    private TextView money;
    private ImageView insured;
    private TextView takeIcon;
    private AutoRelativeLayout addressLayout;
    private TextView takeAddress;
    private TextView deliveryIcon;
    private TextView deliveryAddress;
    private TextView takeDistance;
    private TextView deliveryDistance;
    private PushOrderResponse orderBean;
    private TextView btnTakeOrder;

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
        setFinishOnTouchOutside(false);
        setContentView(R.layout.dialog_push_order);
        initBase();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    

    private void initBase() {
        bindService(new Intent(mContext, SocketService.class),connection,BIND_AUTO_CREATE);
        orderBean = (PushOrderResponse) getIntent().getSerializableExtra("order_info");
    }

    private void initViews() {
        deliveryTime = (TextView) findViewById(R.id.order_item_delivery_time);
        tvType = (TextView) findViewById(R.id.order_item_type);
        money = (TextView) findViewById(R.id.order_item_money);
        insured = ((ImageView) findViewById(R.id.order_item_insured));
        takeIcon = (TextView) findViewById(R.id.order_item_icon_take);
        addressLayout = (AutoRelativeLayout) findViewById(R.id.order_item_address_layout);
        takeAddress = (TextView) findViewById(R.id.order_item_take_address);
        deliveryIcon = (TextView) findViewById(R.id.order_item_icon_delivery);
        deliveryAddress = (TextView) findViewById(R.id.order_item_delivery_address);
        takeDistance = (TextView) findViewById(R.id.order_item_distance_take);
        deliveryDistance = (TextView) findViewById(R.id.order_item_distance_delivery);
        btnTakeOrder = ((TextView) findViewById(R.id.btn_take_order));

        setType();
        initTimer();
        initEvents();
    }

    private void initEvents() {
        btnTakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //{"action":"accept","id":"256"}
                JSONObject json = new JSONObject();
                try {
                    json.put("action", "accept");
                    json.put("id", orderBean.id);
                    socketServiceBinder.sendMsg(json);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTimer() {
        long time = 5000;
        new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnTakeOrder.setText("接单（倒计时" + (millisUntilFinished / 1000) + "）秒");
            }

            @Override
            public void onFinish() {
                // {"action":"notDelivery","order":"488"}
                JSONObject json = new JSONObject();
                try {
                    json.put("action", "notDelivery");
                    json.put("id", orderBean.id);
                   // socketServiceBinder.sendMsg(json);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void setType() {
        int type = -1;
        try {
            type = Integer.parseInt(orderBean.type);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //1 外卖，5 速递
        if (type == 1 || type == 5) {
            //显示"送"字图标和距离
            ViewUtils.showViews(deliveryIcon, deliveryDistance);
            takeIcon.setText("取");
            takeIcon.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_green_circle, 0, 0, 0);
            //取货地址
            takeAddress.setText(orderBean.take.add_address);
            //送货地址
            deliveryAddress.setText(orderBean.receipt.add_address);

            //设置服务类型及其字体颜色和背景
            if (type == 1) {
                tvType.setText("外卖服务");
                tvType.setTextColor(0xffcf69ff);
                tvType.setBackgroundResource(R.mipmap.zjzc_box_delivry);
            } else if (type == 5) {
                tvType.setText("同城速递");
                tvType.setTextColor(0xffe1d61f);
                tvType.setBackgroundResource(R.mipmap.zjzc_box_city_express);
            }
            return;
        }

        //如果不是"外卖服务","同城速递"则隐藏"送"字图标和距离
        ViewUtils.hideViews(deliveryIcon, deliveryDistance);
        //隐藏了"送"字图标后把原来"取"字图标改成"送"字图标
        takeIcon.setText("送");
        takeIcon.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_red_circle, 0, 0, 0);
        takeAddress.setText(orderBean.receipt.add_address);

        //2 代买
        if (type == 2) {
            tvType.setText("代买服务");
            deliveryAddress.setText("商品名称:" + orderBean.receipt.add_address);
            tvType.setTextColor(0xff52a7ff);
            tvType.setBackgroundResource(R.mipmap.zjzc_box_buy_service);
            return;
        }
        //3 代办 , 4 车友
        if (type == 3 || type == 4) {
            if (type == 3) {
                tvType.setText("代办服务");
            } else if (type == 4) {
                tvType.setText("车友之家");
            }
            deliveryAddress.setText("服务名称:" + orderBean.receipt.add_address);
            tvType.setTextColor(0xfff07031);
            tvType.setBackgroundResource(R.mipmap.zjzc_box_comission_service);
            return;
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
