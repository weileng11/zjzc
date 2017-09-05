package cn.bs.zjzc.presenter;

import cn.bs.zjzc.model.IMyPointModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.MyPointModel;
import cn.bs.zjzc.model.response.PointDetailResponse;
import cn.bs.zjzc.ui.view.IMyPointView;

/**
 * Created by Ming on 2016/7/11.
 */
public class MyPointPresenter {
    private IMyPointView mMyPointView;
    private IMyPointModel mMyPointModel;

    public MyPointPresenter(IMyPointView myPointView) {
        mMyPointView = myPointView;
        mMyPointModel = new MyPointModel();
    }

    public void getPointList(String page) {
        mMyPointModel.getPointList(page, new HttpTaskCallback<PointDetailResponse.DataBean>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mMyPointView.showMsg(errorInfo);
                mMyPointView.completeFresh();
            }

            @Override
            public void onTaskSuccess(PointDetailResponse.DataBean data) {
                mMyPointView.showPointList(data);
            }
        });
    }
}
