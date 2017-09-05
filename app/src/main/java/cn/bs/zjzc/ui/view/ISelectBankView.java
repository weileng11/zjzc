package cn.bs.zjzc.ui.view;

import java.util.List;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.BankListResponse;

/**
 * Created by Ming on 2016/6/15.
 */
public interface ISelectBankView extends IBaseView {
    void showBankList(List<BankListResponse.DataBean> data);
}
