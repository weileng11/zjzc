package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.OrderDetail;

/**
 * Created by Administrator on 2016/6/29.
 */
public interface IOrderDetailView extends IBaseView {
    void showData(OrderDetail orderDetail);

    void confirmTakeSuccess();

    void orderCompletedSuccess();
}
