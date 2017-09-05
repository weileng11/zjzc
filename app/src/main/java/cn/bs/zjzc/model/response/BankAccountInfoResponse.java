package cn.bs.zjzc.model.response;

import java.io.Serializable;

/**
 * Created by Ming on 2016/6/15.
 */
public class BankAccountInfoResponse extends BaseResponse implements Serializable{


    /**
     * user_id : 1
     * type : 1
     * rank : 中国银行
     * acount : 123456789
     * name : 王小明
     * money : 100
     * withdraw : 100
     */

    public DataBean data;

    public static class DataBean implements Serializable{
        public String user_id;
        public String type;
        public String rank;
        public String acount;
        public String name;
        public String money;
        public String withdraw;

        @Override
        public String toString() {
            return "{" +
                    "acount:\"" + acount + '\"' +
                    ", user_id:\"" + user_id + '\"' +
                    ", type:\"" + type + '\"' +
                    ", rank:\"" + rank + '\"' +
                    ", name:\"" + name + '\"' +
                    ", money:\"" + money + '\"' +
                    ", withdraw:\"" + withdraw + '\"' +
                    '}';
        }
    }
}
