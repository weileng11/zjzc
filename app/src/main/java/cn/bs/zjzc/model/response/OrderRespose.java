package cn.bs.zjzc.model.response;

/**
 * 下单返回
 * Created by mgc on 2016/6/29.
 */
public class OrderRespose extends BaseResponse  {


    /**
     * order_id : 1
     */

    public DataBean data;

    public static class DataBean {
        public String order_id;
    }
}
