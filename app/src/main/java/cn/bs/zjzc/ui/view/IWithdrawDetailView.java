package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;

/**
 * Created by Ming on 2016/6/16.
 */
public interface IWithdrawDetailView extends IBaseView {
    void showWithdrawDetail(WithdrawDetailResponse.DataBean data);
}
