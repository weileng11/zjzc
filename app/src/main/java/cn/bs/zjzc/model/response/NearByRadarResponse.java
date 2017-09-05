package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * 首页地图上，附近接单，下单用户的 锚点
 * Created by GGG on 2016/7/16.
 */
public class NearByRadarResponse extends BaseResponse {

    /**
     * x : 11.12315646
     * y : 43.45612312
     * state : 1
     */

    public List<DataBean> data;

    public static class DataBean {
        public String x;
        public String y;
        public String state;

        @Override
        public String toString() {
            return "DataBean{" +
                    "x='" + x + '\'' +
                    ", y='" + y + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NearByRadarResponse{" +
                "data=" + data +
                '}';
    }
}
