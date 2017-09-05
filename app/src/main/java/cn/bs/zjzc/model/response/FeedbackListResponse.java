package cn.bs.zjzc.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ming on 2016/6/14.
 */
public class FeedbackListResponse extends BaseResponse implements Serializable {

    /**
     * id : 10
     * content : 10
     * ctime : 1465701617
     * reply : 01
     * reply_time : 0
     * state : 2
     */

    public List<DataBean> data;

    public static class DataBean implements Serializable {
        public String id;
        public String content;
        public String ctime;
        public String reply;
        public String reply_time;
        public String state;
    }
}
