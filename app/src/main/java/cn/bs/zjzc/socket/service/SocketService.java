package cn.bs.zjzc.socket.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.App;
import cn.bs.zjzc.dialog.LoginConflictDialog;
import cn.bs.zjzc.dialog.PushOrderDialog;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.socket.response.PushOrderResponse;

/**
 * Created by Ming on 2016/7/13.
 */
public class SocketService extends Service implements BDLocationListener {
    private static final String TAG = "SocketService";
    private Context mContext = this;
    private LocalBroadcastManager mLocalBroadcastManager;//内部广播
    private LocationClient mLocClient;//定位服务
    private Socket mSocket;
    private SocketReceiver mSocketClient;//socket线程
    private LatLng mLastLatLng;//记录上一次发送的坐标
    private boolean isLogin;//记录登录状态
    private List<PushOrderResponse> pushOrderList;//保存接收订单信息

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        pushOrderList = new ArrayList<>();
        initBaiduLocation();
    }

    private void initBaiduLocation() {
        mLocClient = new LocationClient(getApplicationContext());
         /*设置定位参数*/
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(15000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(false);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(this);
        mLocClient.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new SocketServiceBinder();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (mSocketClient == null) {
            mSocketClient = new SocketReceiver();
            mSocketClient.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (mSocket == null) {
            return;
        }
        if (!mSocket.isConnected()) {
            return;
        }
        if (bdLocation == null) {
            return;
        }

        LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        //如果上次发送的位置信息为空,直接发送当年位置给服务器
        if (mLastLatLng == null) {
            sendPosition(ll);
            return;
        }

        //计算上次发送的位置与当前位置的距离,如果小于100,则返回,不发送距离
        double distance = DistanceUtil.getDistance(mLastLatLng, ll);
        if (distance < 100) {
            return;
        }

        sendPosition(ll);
    }

    private void sendPosition(LatLng ll) {
        try {
            JSONObject json = new JSONObject();
            json.put("action", "position");
            json.put("x", String.valueOf(ll.latitude));
            json.put("y", String.valueOf(ll.longitude));

            Log.i(TAG, "sendPosition: " + json.toString());
            //发送坐标给服务端
            sendMsg(json);
            //发送成功则更新发送的坐标
            mLastLatLng = ll;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(JSONObject json) {
        OutputStream os = null;
        try {
            os = mSocket.getOutputStream();
            String msg = json.toString() + "EOF\r\n";
            os.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * socket线程,在线程中连接socket之后循环读取数据
     */
    class SocketReceiver extends Thread {
        @Override
        public void run() {
            try {
                if (mSocket == null) {
                    mSocket = new Socket(RequestUrl.socketHost, RequestUrl.socketPort);
                }

                if (!isLogin) {
                    doLogin();
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                String json = "";
                Log.i(TAG, "start: ");
                //循环读取数据
                while ((json = br.readLine()) != null) {
                    //如果不是json字符串则跳过,防止有其他无关信息干扰.
                    if (!json.contains("{")) {
                        continue;
                    }

                    //截取json字符串
                    json = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1);
                    Log.i(TAG, "run: " + json);
                    JSONObject response = new JSONObject(json);
                    //获取action,判断具体操作
                    String action = response.getString("action");

                    //登录结果的返回信息
                    if (TextUtils.equals(action, "login")) {
                        actionLogin(response);
                        continue;
                    }
                    //登录冲突的返回信息
                    if (TextUtils.equals(action, "conflict")) {
                        actionConflict(response);
                        continue;
                    }

                    //推送类型 delivery派单（弹窗）
                    if (TextUtils.equals(action, "delivery")) {
                        Intent intent = new Intent(mContext, PushOrderDialog.class);
                        intent.putExtra("order_info", new Gson().fromJson(json, PushOrderResponse.class));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        continue;
                    }

                    //推送类型  grab抢单（列表
                    if (TextUtils.equals(action, "grab")) {
                        //添加订单
                        addPushOrder(new Gson().fromJson(json, PushOrderResponse.class));
                        //其他类型信息直接广播(内部广播)发出去,用于订单推送,接单等其他操作
                        Intent intent = new Intent();
                        intent.putExtra("action", "grab");
                        intent.setAction(AllConsts.IntentAction.socketBroadcast);
                        mLocalBroadcastManager.sendBroadcast(intent);
                        continue;
                    }

                    //订单被接 {"action":"receive","order":"457"}
                    if (TextUtils.equals(action, "receive")) {
                        //添加订单
                        removePushOrder(new Gson().fromJson(json, PushOrderResponse.class));
                        //其他类型信息直接广播(内部广播)发出去,用于订单推送,接单等其他操作
                        Intent intent = new Intent();
                        intent.putExtra("action", "receive");
                        intent.setAction(AllConsts.IntentAction.socketBroadcast);
                        mLocalBroadcastManager.sendBroadcast(intent);
                        continue;
                    }

                    //订单超时 {"action":"timeout","order":"458","time":"600"} 订单超时后 必须做出【继续等待】【添加小费】【取消订单】的操作 倒计时time秒 然后系统自动取消订单
                    if (TextUtils.equals(action, "timeout")) {
                        continue;
                    }
                }
                Log.i(TAG, "run: " + "end");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPushOrder(PushOrderResponse response) {
        if (response == null) {
            return;
        }

        int size = pushOrderList.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(pushOrderList.get(i).id, response.id)) {
                return;
            }
        }
        pushOrderList.add(response);
    }

    private void removePushOrder(PushOrderResponse response) {
        if (response == null) {
            return;
        }

        int size = pushOrderList.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(pushOrderList.get(i).id, response.id)) {
                pushOrderList.remove(i);
                return;
            }
        }
    }

    /**
     * 登录冲突弹窗
     *
     * @param response
     * @throws JSONException
     */
    private void actionConflict(JSONObject response) throws JSONException {
        //跳转activity弹窗
        Intent intent = new Intent(mContext, LoginConflictDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("msg", response.getString("msg"));
        startActivity(intent);
        stopSelf();
        return;
    }

    /**
     * socket登录回调
     *
     * @param response
     * @throws JSONException
     */
    private void actionLogin(JSONObject response) throws JSONException {
        String status = response.getString("status");
        //设置登录成功状态
        if (TextUtils.equals(status, "success")) {
            isLogin = true;
        }
    }

    /**
     * 登录socket
     *
     * @throws IOException
     */
    private void doLogin() throws IOException {
        OutputStream os = mSocket.getOutputStream();
        JSONObject loginObject = new JSONObject();
        try {
            loginObject.put("token", App.LOGIN_USER.getToken());
            loginObject.put("action", "declare");
            String loginParams = loginObject.toString() + "EOF\r\n";
            //socket发送登录的信息
            os.write(loginParams.getBytes());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "socket service stop");
        mLocClient.stop();
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class SocketServiceBinder extends Binder {
        public void sendMsg(JSONObject json) {
            SocketService.this.sendMsg(json);
        }

        public List<PushOrderResponse> getPushOrder() {
            return pushOrderList;
        }
    }

}

