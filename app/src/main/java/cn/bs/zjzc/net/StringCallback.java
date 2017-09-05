package cn.bs.zjzc.net;

import com.zhy.http.okhttp.callback.Callback;

import cn.bs.zjzc.util.CodeUtils;
import okhttp3.Response;

/**
 * Created by yiming on 2016/6/15.
 */
public abstract class StringCallback extends Callback<String>{
    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return CodeUtils.decodeToSgtring(response.body().string());
    }
}
