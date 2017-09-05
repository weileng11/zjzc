package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IServiceHelpModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.ServiceHelpModel;
import cn.bs.zjzc.model.response.FAQListResponse;
import cn.bs.zjzc.ui.view.IServiceHelpView;

/**
 * Created by Ming on 2016/7/7.
 */
public class ServiceHelpPresenter {
    private IServiceHelpView mServiceHelpView;
    private IServiceHelpModel mServiceHelpModel;

    public ServiceHelpPresenter(IServiceHelpView serviceHelpView) {
        mServiceHelpView = serviceHelpView;
        mServiceHelpModel = new ServiceHelpModel();
    }

    public void getFQAList(String keyword, String page) {
        mServiceHelpModel.getFAQList(keyword, page, new HttpTaskCallback<FAQListResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mServiceHelpView.completeFresh();
                mServiceHelpView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(FAQListResponse.DataBean data) {
                mServiceHelpView.showFAQList(data);
            }
        });
    }
}
