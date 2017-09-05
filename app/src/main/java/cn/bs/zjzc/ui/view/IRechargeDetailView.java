package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.RechargeDetailResponse;

/**
 * Created by Ming on 2016/6/16.
 */
public interface IRechargeDetailView extends IBaseView {
    void showRechargeDetail(RechargeDetailResponse.DataBean data);
}
