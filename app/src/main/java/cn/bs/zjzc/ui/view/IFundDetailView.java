package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.FundDetailResponse;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;

/**
 * Created by Ming on 2016/7/6.
 */
public interface IFundDetailView extends IBaseView{
    void showFundDetail(FundDetailResponse.DataBean data);

    void completeFresh();
}
