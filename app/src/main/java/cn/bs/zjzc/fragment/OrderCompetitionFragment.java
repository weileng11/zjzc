package cn.bs.zjzc.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.OrderCompetitionAdapter;
import cn.bs.zjzc.socket.response.PushOrderResponse;
import cn.bs.zjzc.socket.service.SocketService;
import cn.bs.zjzc.ui.AvOrderGrabDetail;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

/**
 * Created by Ming on 2016/7/13.
 */
public class OrderCompetitionFragment extends Fragment {

    private View view;
    private AutoRelativeLayout bottomBar;
    private TextView btnFlushList;
    private CheckBox workSwitch;
    private ListView lvOrderList;
    private PtrClassicFrameLayout ptrFrame;
    private OrderCompetitionAdapter orderListAdapter;

    private LocalBroadcastManager mLocalBroadcastManager;
    private OrderPushReceiver receiver;
    private IntentFilter filter;

    private SocketService.SocketServiceBinder socketServiceBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            socketServiceBinder = (SocketService.SocketServiceBinder) service;
            orderListAdapter.setData(socketServiceBinder.getPushOrder());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().bindService(new Intent(getActivity(), SocketService.class), connection, Context.BIND_AUTO_CREATE);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        filter = new IntentFilter(AllConsts.IntentAction.socketBroadcast);
        receiver = new OrderPushReceiver();
        mLocalBroadcastManager.registerReceiver(receiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_competition, container, false);
            bottomBar = ((AutoRelativeLayout) view.findViewById(R.id.order_competition_bottom_bar));
            btnFlushList = ((TextView) view.findViewById(R.id.order_competition_flush_list));
            workSwitch = ((CheckBox) view.findViewById(R.id.order_competition_work_switch));
            lvOrderList = ((ListView) view.findViewById(R.id.order_competition_lv_order_list));
            ptrFrame = ((PtrClassicFrameLayout) view.findViewById(R.id.order_competition_ptr_frame));

            initListView();
        }
        return view;
    }

    private void initListView() {
        orderListAdapter = new OrderCompetitionAdapter(getContext());
        lvOrderList.setAdapter(orderListAdapter);

        lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), AvOrderGrabDetail.class);
                intent.putExtra("pushOrderResponse", orderListAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalBroadcastManager.unregisterReceiver(receiver);
        getActivity().unbindService(connection);
    }

    class OrderPushReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("action");
            orderListAdapter.setData(socketServiceBinder.getPushOrder());
        }
    }

}
