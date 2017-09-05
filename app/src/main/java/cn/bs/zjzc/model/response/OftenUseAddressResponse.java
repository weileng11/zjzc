package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/29.
 */
public class OftenUseAddressResponse extends BaseResponse {
    /**
     * province_id : 1
     * city_id : 2
     * x : 12.1456465
     * y : 45.1423156
     * address : 白云区云城西路北
     * add_address : 89号今尚科技大厦
     * province : 广东省
     * city : 广州市
     */
    public DataBean data;

    public static class DataBean {
        public String province_id;
        public String city_id;
        public String x;
        public String y;
        public String address;
        public String add_address;
        public String province;
        public String city;

        @Override
        public String toString() {
            return "{" +
                    "add_address:\"" + add_address + '\"' +
                    ", province_id:\"" + province_id + '\"' +
                    ", city_id:\"" + city_id + '\"' +
                    ", x:\"" + x + '\"' +
                    ", y:\"" + y + '\"' +
                    ", address:\"" + address + '\"' +
                    ", province:\"" + province + '\"' +
                    ", city:\"" + city + '\"' +
                    '}';
        }
    }
}
