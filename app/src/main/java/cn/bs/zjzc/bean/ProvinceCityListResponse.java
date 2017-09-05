package cn.bs.zjzc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.model.response.BaseResponse;

/**
 * 省份和城市列表
 * Created by mgc on 2016/6/27.
 */
public class ProvinceCityListResponse extends BaseResponse {

    /**
     * id : 1
     * name : 广东省
     * city_list : [{"id":"2","name":"广州市"},{"id":"3","name":"深圳市"}]
     */

    public List<DataBean> data;

    @Override
    public String toString() {
        return "ProvinceCityListResponse{" +
                "data=" + data +
                '}';
    }

    public static class DataBean implements Serializable{
        public String id;
        public String name;

        /**
         * 多级菜单城市列表依赖toString方法,勿更改
         * @return
         */
//        @Override
//        public String toString() {
//            return name;
//        }


        @Override
        public String toString() {
            /*return "{" +
                    "city_list:" + city_list +
                    ", id:\"" + id + '\"' +
                    ", name:\"" + name + '\"' +
                    '}';*/
            return name;
        }

        /**
         * id : 2
         * name : 广州市
         */

        public ArrayList<CityListBean> city_list;

        public static class CityListBean implements Serializable{
            public String id;
            public String name;

            /**
             * 多级菜单城市列表依赖toString方法,勿更改
             * @return
             */
            @Override
            public String toString() {
                return name;
            }
        }
    }
}
