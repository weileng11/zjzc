package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.BankAccountInfoResponse;

/**
 * Created by Ming on 2016/6/15.
 */
public interface IAddAccountView extends IBaseView {
    void startWithdrawActivity(BankAccountInfoResponse.DataBean data);
}
