package cn.bs.zjzc.presenter;


import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.IModifyPasswordModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.ModifyPasswordModel;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.view.IModifyPasswordView;

/**
 * Created by Ming on 2016/6/12.
 */
public class ModifyPasswordPresenter {
    private IModifyPasswordView mModifyPasswordView;
    private IModifyPasswordModel mModifyPasswordModel;

    public ModifyPasswordPresenter(IModifyPasswordView modifyPasswordView) {
        mModifyPasswordView = modifyPasswordView;
        mModifyPasswordModel = new ModifyPasswordModel();
    }

    public void modifyPassword(String org_passwd, String passwd, String repasswd) {
        mModifyPasswordModel.modifyPassword(org_passwd, passwd, repasswd, new HttpTaskCallback<BaseResponse>() {
            @Override
            public void onTaskFailed(String errorInfo) {
                mModifyPasswordView.showMsg(errorInfo);
            }

            @Override
            public void onTaskSuccess(BaseResponse data) {
                mModifyPasswordView.showMsg("修改成功");
                mModifyPasswordView.finish();
            }
        });
    }


}
