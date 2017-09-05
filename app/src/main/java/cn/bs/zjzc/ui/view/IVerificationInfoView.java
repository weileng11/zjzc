package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.VerificationInfoResponse;

/**
 * Created by Ming on 2016/6/14.
 */
public interface IVerificationInfoView extends IBaseView {

    void showVerificationInfo(VerificationInfoResponse.DataBean data);
}
