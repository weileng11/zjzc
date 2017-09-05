package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderSetting;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface IOrderSettingView extends IBaseView {
    void getOrderSettingSuccess(OrderSetting data);
    void saveOrderSettingSuccess();
}
