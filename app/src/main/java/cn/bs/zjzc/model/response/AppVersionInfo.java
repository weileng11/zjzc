package cn.bs.zjzc.model.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/13.
 */
public class AppVersionInfo extends BaseResponse {

    /**
     * num : 456132
     * name : 版本1
     * url : /Public/Upload/Apk/aaaa.apk
     * time : 1436864169
     * capability : 新功能
     * optimization : 优化
     * remark : 这是一个新版本
     */

    public DataBean data;

    public static class DataBean implements Serializable {
        public String num;
        public String name;
        public String url;
        public String time;
        public String capability;
        public String optimization;
        public String remark;
    }
}
