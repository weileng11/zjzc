package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/15.
 */
public class WithdrawResponse extends BaseResponse {
    /**
     * process_money : 5.00
     */
    public DataBean data;

    public static class DataBean {
        public String process_money;
    }
}
