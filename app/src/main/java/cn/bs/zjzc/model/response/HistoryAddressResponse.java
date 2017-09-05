package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * 地址历史记录
 * Created by mgc on 2016/7/4.
 */
public class HistoryAddressResponse {


    public List<Data> data;

    public static class Data {
        public String province_id;
        public String city_id;
        public String x;
        public String y;
        public String address;
        public String add_address;
        public String name;
        public String phone;
    }
}
