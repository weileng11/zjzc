package cn.bs.zjzc.bean;

import java.io.Serializable;

/**
 * Created by mgc on 2016/7/4.
 */
public class HistoryAddressParamters implements Serializable{
    public String province_id;
    public String city_id;
    public String x;
    public String y;
    public String address;
    public String add_address;
    public String name;
    public String phone;

    public HistoryAddressParamters() {
    }

    /**
     *
     * @param province_id
     * @param city_id
     * @param x
     * @param y
     * @param address
     * @param add_address
     * @param name
     * @param phone
     */
    public HistoryAddressParamters(String province_id, String city_id, String x, String y, String address, String add_address, String name, String phone) {
        this.province_id = province_id;
        this.city_id = city_id;
        this.x = x;
        this.y = y;
        this.address = address;
        this.add_address = add_address;
        this.name = name;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "HistoryAddressParamters{" +
                "\n, province_id='" + province_id + '\'' +
                "\n, city_id='" + city_id + '\'' +
                "\n, x='" + x + '\'' +
                "\n, y='" + y + '\'' +
                "\n, address='" + address + '\'' +
                "\n, add_address='" + add_address + '\'' +
                "\n, name='" + name + '\'' +
                "\n, phone='" + phone + '\'' +
                '}';
    }
}
