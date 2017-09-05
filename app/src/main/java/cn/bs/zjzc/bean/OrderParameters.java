package cn.bs.zjzc.bean;

/**
 * 下单参数
 * Created by mgc on 2016/6/29.
 */
public class OrderParameters {

    public String token;
    public String type;
    public String receipt_province_id;
    public String receipt_city_id;
    public String take_province_id;
    public String take_city_id;

    public String goods_name;
    public String service_name;
    public String service_money;

    public String receipt_x;
    public String receipt_y;
    public String receipt_address;
    public String receipt_add_address;
    public String receipt_name;
    public String receipt_phone;
    public String take_x;
    public String take_y;
    public String take_address;
    public String take_add_address;
    public String take_name;
    public String take_phone;
    public String take_time;
    public String tip_money;
    public String remark_txt;
    public String insured_money;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceipt_province_id() {
        return receipt_province_id;
    }

    public void setReceipt_province_id(String receipt_province_id) {
        this.receipt_province_id = receipt_province_id;
    }

    public String getReceipt_city_id() {
        return receipt_city_id;
    }

    public void setReceipt_city_id(String receipt_city_id) {
        this.receipt_city_id = receipt_city_id;
    }

    public String getTake_province_id() {
        return take_province_id;
    }

    public void setTake_province_id(String take_province_id) {
        this.take_province_id = take_province_id;
    }

    public String getTake_city_id() {
        return take_city_id;
    }

    public void setTake_city_id(String take_city_id) {
        this.take_city_id = take_city_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_money() {
        return service_money;
    }

    public void setService_money(String service_money) {
        this.service_money = service_money;
    }

    public String getReceipt_x() {
        return receipt_x;
    }

    public void setReceipt_x(String receipt_x) {
        this.receipt_x = receipt_x;
    }

    public String getReceipt_y() {
        return receipt_y;
    }

    public void setReceipt_y(String receipt_y) {
        this.receipt_y = receipt_y;
    }

    public String getReceipt_address() {
        return receipt_address;
    }

    public void setReceipt_address(String receipt_address) {
        this.receipt_address = receipt_address;
    }

    public String getReceipt_add_address() {
        return receipt_add_address;
    }

    public void setReceipt_add_address(String receipt_add_address) {
        this.receipt_add_address = receipt_add_address;
    }

    public String getReceipt_name() {
        return receipt_name;
    }

    public void setReceipt_name(String receipt_name) {
        this.receipt_name = receipt_name;
    }

    public String getReceipt_phone() {
        return receipt_phone;
    }

    public void setReceipt_phone(String receipt_phone) {
        this.receipt_phone = receipt_phone;
    }

    public String getTake_x() {
        return take_x;
    }

    public void setTake_x(String take_x) {
        this.take_x = take_x;
    }

    public String getTake_y() {
        return take_y;
    }

    public void setTake_y(String take_y) {
        this.take_y = take_y;
    }

    public String getTake_address() {
        return take_address;
    }

    public void setTake_address(String take_address) {
        this.take_address = take_address;
    }

    public String getTake_add_address() {
        return take_add_address;
    }

    public void setTake_add_address(String take_add_address) {
        this.take_add_address = take_add_address;
    }

    public String getTake_name() {
        return take_name;
    }

    public void setTake_name(String take_name) {
        this.take_name = take_name;
    }

    public String getTake_phone() {
        return take_phone;
    }

    public void setTake_phone(String take_phone) {
        this.take_phone = take_phone;
    }

    public String getTake_time() {
        return take_time;
    }

    public void setTake_time(String take_time) {
        this.take_time = take_time;
    }

    public String getTip_money() {
        return tip_money;
    }

    public void setTip_money(String tip_money) {
        this.tip_money = tip_money;
    }

    public String getRemark_txt() {
        return remark_txt;
    }

    public void setRemark_txt(String remark_txt) {
        this.remark_txt = remark_txt;
    }

    public String getInsured_money() {
        return insured_money;
    }

    public void setInsured_money(String insured_money) {
        this.insured_money = insured_money;
    }

    @Override
    public String toString() {
        String name = "";
        if (type != null) {
            switch (Integer.parseInt(type)) {
                case 1:
                    name = "外卖服务";
                    break;
                case 2:
                    name = "代买服务";
                    break;
                case 3:
                    name = "代办服务";
                    break;
                case 4:
                    name = "车友之家服务";
                    break;
                case 5:
                    name = "同城服务";
                    break;
            }
        }
        return name +
                "\n, OrderParameters{" +
                "\n, token='" + token + '\'' +
                "\n, type='" + type + '\'' +
                "\n, receipt_province_id='" + receipt_province_id + '\'' +
                "\n, receipt_city_id='" + receipt_city_id + '\'' +
                "\n, take_province_id='" + take_province_id + '\'' +
                "\n, take_city_id='" + take_city_id + '\'' +
                "\n, goods_name='" + goods_name + '\'' +
                "\n, service_name='" + service_name + '\'' +
                "\n, service_money='" + service_money + '\'' +
                "\n, receipt_x='" + receipt_x + '\'' +
                "\n, receipt_y='" + receipt_y + '\'' +
                "\n, receipt_address='" + receipt_address + '\'' +
                "\n, receipt_add_address='" + receipt_add_address + '\'' +
                "\n, receipt_name='" + receipt_name + '\'' +
                "\n, receipt_phone='" + receipt_phone + '\'' +
                "\n, take_x='" + take_x + '\'' +
                "\n, take_y='" + take_y + '\'' +
                "\n, take_address='" + take_address + '\'' +
                "\n, take_add_address='" + take_add_address + '\'' +
                "\n, take_name='" + take_name + '\'' +
                "\n, take_phone='" + take_phone + '\'' +
                "\n, take_time='" + take_time + '\'' +
                "\n, tip_money='" + tip_money + '\'' +
                "\n, remark_txt='" + remark_txt + '\'' +
                "\n, insured_money='" + insured_money + '\'' +
                '}';
    }
}
