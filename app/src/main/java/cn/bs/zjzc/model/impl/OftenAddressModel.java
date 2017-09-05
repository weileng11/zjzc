package cn.bs.zjzc.model.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import cn.bs.zjzc.App;
import cn.bs.zjzc.cache.CacheManager;
import cn.bs.zjzc.cache.DataCacheManager;
import cn.bs.zjzc.model.IOftenAddressModel;
import cn.bs.zjzc.model.bean.EditAddressRequestBody;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OftenUseAddressResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/29.
 */
public class OftenAddressModel implements IOftenAddressModel {
    private SharedPreferences sp;

    public OftenAddressModel(Context context, String account) {
        sp = context.getSharedPreferences(account + "_address", Context.MODE_PRIVATE);
    }

    @Override
    public void getOftenAddress(final String type, final HttpTaskCallback<OftenUseAddressResponse.DataBean> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getOftenAddress);
        PostHttpTask<OftenUseAddressResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("type", type)
                .execute(new GsonCallback<OftenUseAddressResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(OftenUseAddressResponse response) {
                        if (callback != null) {
                            callback.onTaskSuccess(response.data);
                        }
                    }
                });
    }

    @Override
    public void editOftenAddress(final EditAddressRequestBody requestBody, final String city, final HttpTaskCallback<BaseResponse> callback) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.editAddress);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<>(url);
        httpTask.params(requestBody)
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        callback.onTaskFailed(errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        callback.onTaskSuccess(response);
                        saveAddress(requestBody, city);
                    }
                });
    }

    @Override
    public void saveAddress(EditAddressRequestBody addressBody, String city) {
        if (TextUtils.equals(addressBody.type, "1")) {
            sp.edit().putString("home_add_address", addressBody.add_address)
                    .putString("home_address", addressBody.address)
                    .putString("home_city", city)
                    .putString("home_city_id", addressBody.city_id)
                    .putString("home_province_id", addressBody.province_id)
                    .putString("home_x", addressBody.x)
                    .putString("home_y", addressBody.y)
                    .apply();
        } else if (TextUtils.equals(addressBody.type, "2")) {
            sp.edit().putString("company_add_address", addressBody.add_address)
                    .putString("company_address", addressBody.address)
                    .putString("company_city", city)
                    .putString("company_city_id", addressBody.city_id)
                    .putString("company_province_id", addressBody.province_id)
                    .putString("company_x", addressBody.x)
                    .putString("company_y", addressBody.y)
                    .apply();
        }
    }

    @Override
    public void getHomeAddress(HttpTaskCallback<OftenUseAddressResponse.DataBean> callback) {
        OftenUseAddressResponse.DataBean homeAddressData = new OftenUseAddressResponse.DataBean();
        homeAddressData.add_address = sp.getString("home_add_address", "");
        homeAddressData.address = sp.getString("home_address", "");
        homeAddressData.city = sp.getString("home_city", "");
        homeAddressData.city_id = sp.getString("home_city_id", "");
        homeAddressData.province_id = sp.getString("home_province_id", "");
        homeAddressData.x = sp.getString("home_x", "");
        homeAddressData.y = sp.getString("home_y", "");

        if (TextUtils.isEmpty(homeAddressData.add_address)
                || TextUtils.isEmpty(homeAddressData.add_address)
                || TextUtils.isEmpty(homeAddressData.address)
                || TextUtils.isEmpty(homeAddressData.city)
                || TextUtils.isEmpty(homeAddressData.city_id)
                || TextUtils.isEmpty(homeAddressData.province_id)
                || TextUtils.isEmpty(homeAddressData.x)
                || TextUtils.isEmpty(homeAddressData.y)) {
            getOftenAddress("1", callback);
        } else {
            callback.onTaskSuccess(homeAddressData);
        }
    }

    @Override
    public void getCompanyAddress(HttpTaskCallback<OftenUseAddressResponse.DataBean> callback) {
        OftenUseAddressResponse.DataBean CompanyAddressData = new OftenUseAddressResponse.DataBean();
        CompanyAddressData.add_address = sp.getString("company_add_address", "");
        CompanyAddressData.address = sp.getString("company_address", "");
        CompanyAddressData.city = sp.getString("company_city", "");
        CompanyAddressData.city_id = sp.getString("company_city_id", "");
        CompanyAddressData.province_id = sp.getString("company_province_id", "");
        CompanyAddressData.x = sp.getString("company_x", "");
        CompanyAddressData.y = sp.getString("company_y", "");

        if (TextUtils.isEmpty(CompanyAddressData.add_address)
                || TextUtils.isEmpty(CompanyAddressData.add_address)
                || TextUtils.isEmpty(CompanyAddressData.address)
                || TextUtils.isEmpty(CompanyAddressData.city)
                || TextUtils.isEmpty(CompanyAddressData.city_id)
                || TextUtils.isEmpty(CompanyAddressData.province_id)
                || TextUtils.isEmpty(CompanyAddressData.x)
                || TextUtils.isEmpty(CompanyAddressData.y)) {
            getOftenAddress("2", callback);
        } else {
            callback.onTaskSuccess(CompanyAddressData);
        }
    }


}
