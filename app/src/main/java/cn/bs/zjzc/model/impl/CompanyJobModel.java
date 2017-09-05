package cn.bs.zjzc.model.impl;


import cn.bs.zjzc.App;
import cn.bs.zjzc.model.ICompanyJobModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/13.
 */
public class CompanyJobModel implements ICompanyJobModel {
    @Override
    public void changeCompanyJob(final String company_job, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.changeUserInfo);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("company_job", company_job)
                .execute(new GsonCallback<BaseResponse>() {

                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        App.LOGIN_USER.setCompany_job(company_job);
                        callback.onTaskSuccess(response);
                    }
                });
    }
}
