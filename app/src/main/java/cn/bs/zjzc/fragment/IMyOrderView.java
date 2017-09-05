package cn.bs.zjzc.fragment;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.OrderListResponse;

/**
 * Created by Ming on 2016/6/29.
 */
public interface IMyOrderView {
    void showOrderList(OrderListResponse.DataBean data);

    void showMsg(String msg);

    void completeFresh();

}
