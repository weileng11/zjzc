package cn.bs.zjzc.presenter;


import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.ICompanyJobModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.CompanyJobModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.ICompanyJobView;

/**
 * Created by Ming on 2016/6/13.
 */
public class CompanyJobPresenter {
    private ICompanyJobView mCompanyJobView;
    private ICompanyJobModel mCompanyJobModel;

    public CompanyJobPresenter(ICompanyJobView companyJobView) {
        mCompanyJobView = companyJobView;
        mCompanyJobModel = new CompanyJobModel();
    }

    public void changeCompanyJob(String company_job) {
        mCompanyJobView.showLoading("正在保存...");
        mCompanyJobModel.changeCompanyJob(company_job, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mCompanyJobView.hideLoading();
                mCompanyJobView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mCompanyJobView.hideLoading();
                mCompanyJobView.changeCompanyJobSuccess();
            }
        });
    }
}
