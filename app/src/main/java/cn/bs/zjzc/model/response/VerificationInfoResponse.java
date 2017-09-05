package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/14.
 */
public class VerificationInfoResponse extends BaseResponse {


    /**
     * address : 得得得
     * my_real_name : 呃呃呃额额
     * id_card_number : 36446464316949595
     * id_photo : 192.168.10.88/Public/Upload/UserApply/2016-06-14/575fde1d2816a.jpg
     * id_back_photo : 192.168.10.88/Public/Upload/UserApply/2016-06-14/575fde1d5d68b.jpg
     * id_people : 192.168.10.88/Public/Upload/UserApply/2016-06-14/575fde1d42bfa.jpg
     * remark :
     * state : 1
     */

    public DataBean data;

    public static class DataBean {
        public String address;
        public String my_real_name;
        public String id_card_number;
        public String id_photo;
        public String id_back_photo;
        public String id_people;
        public String remark;
        public String state;

        @Override
        public String toString() {
            return "{" +
                    "address:\"" + address + '\"' +
                    ", my_real_name:\"" + my_real_name + '\"' +
                    ", id_card_number:\"" + id_card_number + '\"' +
                    ", id_photo:\"" + id_photo + '\"' +
                    ", id_back_photo:\"" + id_back_photo + '\"' +
                    ", id_people:\"" + id_people + '\"' +
                    ", remark:\"" + remark + '\"' +
                    ", state:\"" + state + '\"' +
                    '}';
        }
    }
}
