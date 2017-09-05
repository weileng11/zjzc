package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class OrderSetting extends BaseResponse {

    /**
     * order : [1,2]
     * distance : 3
     * flush : 2
     * type : 2
     * push : 2
     */

    public DataBean data;

    public static class DataBean {
        public String distance;
        public String flush;
        public String type;
        public String push;
        public List<Integer> order;

    }
}
