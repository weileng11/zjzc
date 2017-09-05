package cn.bs.zjzc.socket.response;

/**
 * Created by Ming on 2016/7/14.
 */
public class OrderCompeitionResponse {

    /**
     * code : 1
     * data : {"take":{"phone":"13612341234","name":"555"},"receipt":{"phone":"13612341234","name":"4"}}
     * action : accept
     * info : 操作成功
     */

    public String code;
    /**
     * take : {"phone":"13612341234","name":"555"}
     * receipt : {"phone":"13612341234","name":"4"}
     */

    public DataBean data;
    public String action;
    public String info;

    public static class DataBean {
        /**
         * phone : 13612341234
         * name : 555
         */

        public TakeBean take;
        /**
         * phone : 13612341234
         * name : 4
         */

        public ReceiptBean receipt;

        public static class TakeBean {
            public String phone;
            public String name;
        }

        public static class ReceiptBean {
            public String phone;
            public String name;
        }
    }
}
