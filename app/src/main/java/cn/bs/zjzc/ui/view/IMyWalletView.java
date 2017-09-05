package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.MyWalletResponse;

/**
 * Created by Ming on 2016/6/14.
 */
public interface IMyWalletView extends IBaseView {

    void showWalletInfo(MyWalletResponse.DataBean data);
}
