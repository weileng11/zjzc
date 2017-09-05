package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class OrderEvaluation extends BaseResponse {


    /**
     * time : 1467770260
     * level : 1.0
     * content : 卧槽
     * type : [2,3]
     */

    public DataBean data;

    public static class DataBean {
        public String time;
        public String level;
        public String content;
        public List<Integer> type;
    }
}
