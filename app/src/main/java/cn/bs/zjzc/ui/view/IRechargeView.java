package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.RechargeResponse;

/**
 * Created by Ming on 2016/6/16.
 */
public interface IRechargeView extends IBaseView {
    void createPayment(RechargeResponse.DataBean data);
}
