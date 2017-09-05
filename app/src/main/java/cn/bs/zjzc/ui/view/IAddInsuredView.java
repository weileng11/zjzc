package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.AddInsuredResponse;

/**
 * 添加保价费计算方式
 * Created by mgc on 2016/7/1.
 */
public interface IAddInsuredView extends IBaseView {

    void getInsuredWaySuccess(AddInsuredResponse.DataBean data);
}
