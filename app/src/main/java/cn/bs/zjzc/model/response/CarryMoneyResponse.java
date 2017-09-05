package cn.bs.zjzc.model.response;

/**
 * 获取运费（配送费） 和 距离
 * Created by mgc on 2016/6/23.
 */
public class CarryMoneyResponse extends BaseResponse {

    public DataBean data;

    @Override
    public String toString() {
        return "CarryMoneyResponse{" +
                "data=" + data +
                '}';
    }

    public static class DataBean {
        public String distance;//距离（公里）
        public String carry_money;//运费（配送费）

        @Override
        public String toString() {
            return "DataBean{" +
                    "distance='" + distance + '\'' +
                    ", carry_money='" + carry_money + '\'' +
                    '}';
        }
    }
}
