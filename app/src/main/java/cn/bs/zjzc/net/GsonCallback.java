package cn.bs.zjzc.net;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Socket;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.util.CodeUtils;
import cn.bs.zjzc.util.L;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Ming on 2016/6/7.
 */
public abstract class GsonCallback<T extends BaseResponse> extends Callback<T> {
    private Type mParameterizedType;


    public GsonCallback() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        mParameterizedType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        String s = response.body().string();
        L.d(mParameterizedType + "-->GsonCallback-->base64 code: " + s);
        String json = CodeUtils.decodeToSgtring(s);
        L.d(mParameterizedType + "-->GsonCallback-->json : " + json);
        return new Gson().fromJson(json, mParameterizedType);
    }

    @Override
    public void onResponse(T response) {
        try {
            L.d("response", response.toString());
            if (AllConsts.http.successErrCode.equals(response.errcode)) {
                onSuccess(response);
            } else {
                onFailed(response.errinfo);
            }
        } catch (NullPointerException e) {
            L.e("GsonCallback-->onResponse(T response) : " + e.getMessage());
        }
    }

    @Override
    public void onError(Call call, Exception e) {
        L.e(mParameterizedType + "--> post error:");
        e.printStackTrace();
        onFailed(e.getMessage());
    }

    public abstract void onFailed(String errorInfo);

    public abstract void onSuccess(T response);
}
