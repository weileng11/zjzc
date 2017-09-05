package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IRegisterOrderTakerModel;
import cn.bs.zjzc.model.bean.RegisterOrderTakerRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.RegisterOrderTakerModel;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.ui.view.IRegisterOrderTakerView;

/**
 * Created by Ming on 2016/6/14.
 */
public class RegisterOrderTakerPresenter {

    private IRegisterOrderTakerView mRegisterOrderTakerView;
    private IRegisterOrderTakerModel mRegisterOrderTakerModel;


    public RegisterOrderTakerPresenter(IRegisterOrderTakerView registerOrderTakerView) {
        mRegisterOrderTakerView = registerOrderTakerView;
        mRegisterOrderTakerModel = new RegisterOrderTakerModel();
    }

    public void registerOrderTaker(RegisterOrderTakerRequestBody requestBody) {

        mRegisterOrderTakerView.showLoading();
        mRegisterOrderTakerModel.registerOrderTaker(requestBody, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mRegisterOrderTakerView.hideLoading();
                mRegisterOrderTakerView.showMsg(errorInfo);
            }
            @Override
            public void onTaskSuccess(BaseResponse data) {
                mRegisterOrderTakerView.hideLoading();
                mRegisterOrderTakerView.registOrderUserSuccess(data.errinfo);
            }
        });

    }
}
