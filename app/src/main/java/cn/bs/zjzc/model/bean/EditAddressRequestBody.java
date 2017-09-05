package cn.bs.zjzc.model.bean;

/**
 * Created by Ming on 2016/6/28.
 */
public class EditAddressRequestBody {

    /**
     * token : jsdalkfjls
     * type : 1
     * province_id : 1
     * city_id : 2
     * x : 12.46541321
     * y : 26.12314541
     * address : 白云区云城西路北
     * add_address : 89号今尚科技大厦
     */

    public String token;
    public String type;
    public String province_id;
    public String city_id;
    public String x;
    public String y;
    public String address;
    public String add_address;

    public EditAddressRequestBody() {
    }

    public EditAddressRequestBody(String add_address, String address, String city_id, String province_id, String token, String type, String x, String y) {
        this.add_address = add_address;
        this.address = address;
        this.city_id = city_id;
        this.province_id = province_id;
        this.token = token;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" +
                "add_address:\"" + add_address + '\"' +
                ", token:\"" + token + '\"' +
                ", type:\"" + type + '\"' +
                ", province_id:\"" + province_id + '\"' +
                ", city_id:\"" + city_id + '\"' +
                ", x:\"" + x + '\"' +
                ", y:\"" + y + '\"' +
                ", address:\"" + address + '\"' +
                '}';
    }
}
