package cn.bs.zjzc.model.response;

/**
 * 添加保价费计算方式
 * Created by mgc on 2016/7/1.
 */
public class AddInsuredResponse extends BaseResponse{

    /**
     * prop : 2
     * max_money : 50
     */

    public DataBean data;


    @Override
    public String toString() {
        return "AddInsuredResponse{" +
                "data=" + data +
                '}';
    }

    public static class DataBean {
        public String prop;
        public String max_money;

        @Override
        public String toString() {
            return "DataBean{" +
                    "prop='" + prop + '\'' +
                    ", max_money='" + max_money + '\'' +
                    '}';
        }
    }
}
