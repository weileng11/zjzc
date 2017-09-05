package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/6/24.
 */
public class AdvertisementResponse extends BaseResponse {

    /**
     * id : 1
     * type : 1
     * img : Ad/2016-06-08/5757714fed2b2.jpg
     * title : 测试广告
     */

    public List<DataBean> data;

    public static class DataBean {
        public String id;
        public String type;
        public String img;
        public String title;
    }
}
