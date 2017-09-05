package cn.bs.zjzc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.OrderListAdapter;
import cn.bs.zjzc.model.bean.OrderListRequestBody;
import cn.bs.zjzc.model.response.OrderListResponse;
import cn.bs.zjzc.presenter.MyOrderPresenter;
import cn.bs.zjzc.util.T;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/6/30.
 */
public class MyOrderFragment extends Fragment implements IMyOrderView {
    private String mState;
    private String mType;
    private String mOrderType;
    private String mKeyWord;
    private int currentPage = 1;
    private View view;
    private ListView listView;
    private PtrClassicFrameLayout ptrFrameLayout;
    private OrderListAdapter orderListAdapter;
    private MyOrderPresenter myOrderPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOrderPresenter = new MyOrderPresenter(this);
        Bundle arguments = getArguments();
        mState = arguments.getString("state");
        mType = arguments.getString("type");
        mOrderType = arguments.getString("order_type");
        mKeyWord = arguments.getString("keyword");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_list, container, false);
            initViews();
            initEvents();
            getOrderList(1);
        }
        return view;
    }

    private void initEvents() {
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                getOrderList(++currentPage);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getOrderList(1);
            }
        });
    }

    private void getOrderList(int page) {
        OrderListRequestBody requestBody = new OrderListRequestBody();
//        requestBody.token = App.LOGIN_USER.getToken();
//        requestBody.type = mType;
//        requestBody.state = mState;
//        requestBody.order_type = mOrderType;
//        requestBody.page = String.valueOf(page);

        requestBody.token = App.LOGIN_USER.getToken();
        requestBody.type = mType;
        requestBody.state = mState;
        requestBody.order_type = mOrderType;
        requestBody.page = String.valueOf(page);
        requestBody.keyword = mKeyWord;
        myOrderPresenter.getOrderList(requestBody);
    }

    private void initViews() {
        ptrFrameLayout = ((PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame_layout));
        listView = ((ListView) view.findViewById(R.id.list_view));

        orderListAdapter = new OrderListAdapter(getContext(), mType);
        listView.setAdapter(orderListAdapter);
    }

    @Override
    public void showOrderList(OrderListResponse.DataBean data) {
        if (TextUtils.equals(data.page.page, "1")) {
            orderListAdapter.setData(data.list);
            return;
        }
        orderListAdapter.addDatas(data.list);
        ptrFrameLayout.refreshComplete();
    }


    @Override
    public void showMsg(String msg) {
        T.showShort(getContext(), msg);
    }

    @Override
    public void completeFresh() {
        ptrFrameLayout.refreshComplete();
    }
}
