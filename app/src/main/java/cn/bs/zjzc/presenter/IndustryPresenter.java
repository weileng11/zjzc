package cn.bs.zjzc.presenter;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IIndustryModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.IndustryModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.IndustryResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IIndustryView;

/**
 * Created by Ming on 2016/6/13.
 */
public class IndustryPresenter {

    private IIndustryView mIndustryView;
    private IIndustryModel mIndustrModel;

    public IndustryPresenter(IIndustryView industryView) {
        mIndustryView = industryView;
        mIndustrModel = new IndustryModel();
    }

    public void getIndustryList() {
        mIndustryView.showLoading("正在获取行业列表...");
        mIndustrModel.getIndustryList(new HttpTaskCallback<IndustryResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mIndustryView.hideLoading();
                mIndustryView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(IndustryResponse data) {
                mIndustryView.hideLoading();
                mIndustryView.showIndustry(data.data);
            }
        });
    }

    public void changeIndustry(String industry) {
        mIndustryView.showLoading("正在保存...");
        mIndustrModel.changeIndustry(industry, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mIndustryView.hideLoading();
                mIndustryView.showMsg(errorInfo);

            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mIndustryView.hideLoading();
                mIndustryView.changeIndustrySuccess();
            }
        });
    }
}
