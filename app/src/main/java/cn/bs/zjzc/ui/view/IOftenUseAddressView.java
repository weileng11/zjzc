package cn.bs.zjzc.ui.view;

import cn.bs.zjzc.base.IBaseView;
import cn.bs.zjzc.model.response.OftenUseAddressResponse;

/**
 * Created by Ming on 2016/7/2.
 */
public interface IOftenUseAddressView extends IBaseView{
    void setHomeAddress(OftenUseAddressResponse.DataBean data);

    void setCompanyAddress(OftenUseAddressResponse.DataBean data);

    void hideHomeAddress();

    void hideCompanyAddress();
}
