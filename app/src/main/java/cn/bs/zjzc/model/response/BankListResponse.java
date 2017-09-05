package cn.bs.zjzc.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ming on 2016/6/15.
 */
public class BankListResponse extends BaseResponse implements Serializable{

    /**
     * id : 1
     * name : 中国银行
     */

    public List<DataBean> data;

    public static class DataBean implements Serializable{
        public String id;
        public String name;
    }
}
