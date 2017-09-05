package cn.bs.zjzc.net;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import cn.bs.zjzc.model.bean.UploadFileBody;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.util.CodeUtils;
import cn.bs.zjzc.util.L;
import okhttp3.OkHttpClient;

/**
 * Created by yiming on 2016/6/18.
 */
public class PostHttpTask<T extends BaseResponse> {

    private PostFormBuilder mPostBuilder;

    private JSONObject mObj;

    private String mParams;

    public PostHttpTask(String url) {
        mPostBuilder = OkHttpUtils.post().url(url);
//        GetBuilder url1 = OkHttpUtils.get().url(url);
        OkHttpClient okHttpClient = new OkHttpClient();
    }



    public PostHttpTask<T> addParams(String key, String value) {
        if (mObj == null) {
            mObj = new JSONObject();
        }

        try {
            mObj.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PostHttpTask<T> params(Object object) {
        mParams = new Gson().toJson(object);
        return this;
    }

    public PostHttpTask<T> addFile(String name, String fileName, File file) {
        mPostBuilder.addFile(name, fileName, file);
        L.d("post--> File : " + file.getAbsolutePath());

       // { "account":"1234","password":"12345"}

        return this;
    }

    public PostHttpTask<T> addFiles(Map<String, UploadFileBody> fileMap) {
        for (String key : fileMap.keySet()) {
            UploadFileBody fileBody = fileMap.get(key);
            mPostBuilder.addFile(key, fileBody.fileName, fileBody.file);
            L.d("MGC",key+","+fileBody.fileName);
        }
        L.d("MGC","post--> fileMap : ");
        L.d("post--> fileMap : " + fileMap.toString());
        return this;
    }

    public void execute(Callback<T> callback) {
        String json = mParams;
        if (json == null) {
            json = mObj.toString();
        }
        L.d("post--> params : " + json);
        System.out.println("post--> params : " + json);
        mPostBuilder.addParams("data", CodeUtils.encodeToString(json)).build().connTimeOut(8000).execute(callback);
    }
}
