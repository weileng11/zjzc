package cn.bs.zjzc.socket.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ming on 2016/7/14.
 */

public class PushOrderResponse implements Serializable {

    /**
     * place_time : 0
     * number : TC192016070710304671034
     * take : {"address":"云城西路228号A8创意园苹果大厦旁","distance":"","x":"113.27812138491255","y":"23.196720110876427","add_address":"401"}
     * money : 15
     * remark_txt : 同城送999朵鲜花---乜鬼，玫瑰，蓝色妖姬
     * receipt : {"address":"P-PASS派歌量贩式KTV","distance":"","x":"23.121452683659367","y":"113.28404121822135","add_address":"901"}
     * id : 312
     * type : 5
     * remark_img : ["/Public/Upload/Order/2016-07-07/577dbed6be9da.png","/Public/Upload/Order/2016-07-07/577dbed6d7c38.png"]
     * way :
     */

    public String place_time;
    public String number;
    /**
     * address : 云城西路228号A8创意园苹果大厦旁
     * distance :
     * x : 113.27812138491255
     * y : 23.196720110876427
     * add_address : 401
     */

    public TakeBean take;
    public String money;
    public String remark_txt;
    /**
     * address : P-PASS派歌量贩式KTV
     * distance :
     * x : 23.121452683659367
     * y : 113.28404121822135
     * add_address : 901
     */

    public ReceiptBean receipt;
    public String id;
    public String type;
    public String way;
    public List<String> remark_img;

    public static class TakeBean implements Serializable {
        public String address;
        public String distance;
        public String x;
        public String y;
        public String add_address;
    }

    public static class ReceiptBean implements Serializable {
        public String address;
        public String distance;
        public String x;
        public String y;
        public String add_address;
    }
}
