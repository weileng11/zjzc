package cn.bs.zjzc.model.response;


import cn.bs.zjzc.base.IBaseModel;
import cn.bs.zjzc.model.callback.HttpTaskCallback;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface IMyCouponModel extends IBaseModel {
    void getCouponList(String type, String page, HttpTaskCallback<MyCoupon> myCouponHttpTaskCallback);
}
