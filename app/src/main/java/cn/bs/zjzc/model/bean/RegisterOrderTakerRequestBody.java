package cn.bs.zjzc.model.bean;

import java.util.Map;

/**
 * Created by yiming on 2016/6/20.
 */
public class RegisterOrderTakerRequestBody {
    public String address;
    public String my_real_name;
    public String ID_card_number;
    public Map<String, UploadFileBody> photoMap;

    public RegisterOrderTakerRequestBody(String address, String my_real_name, String ID_card_number, Map<String, UploadFileBody> photoMap) {
        this.address = address;
        this.my_real_name = my_real_name;
        this.ID_card_number = ID_card_number;
        this.photoMap = photoMap;
    }
}
