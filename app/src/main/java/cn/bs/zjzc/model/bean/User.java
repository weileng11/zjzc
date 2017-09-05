package cn.bs.zjzc.model.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.App;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.model.response.AddInsuredResponse;
import cn.bs.zjzc.model.response.LoginResponse;
import cn.bs.zjzc.model.response.UserDataResponse;
import cn.bs.zjzc.net.RequestUrl;

/**
 * Created by Ming on 2016/6/20.
 */
public class User {
    private String token;//令牌
    private String account;//帐号

    private String name; //昵称
    private String phone; //手机号
    private String money; //余额
    private String points; //积分
    private String age; //年龄
    private String sex; //性别
    private String company_job; //公司职位
    private String head_img; //头像路径
    private String type; //用户类型
    private String state;//用户状态（1正常，2冻结 ，3注册帐号未完善信息）
    private String level;//用户等级
    private String industry;//行业
    private String star;//综合评分/星级
    private String apply_state;//认证状态（0 未认证，1 审核中，2 认证通过，3 认证不通过）

    private List<ProvinceCityListResponse.DataBean> provinceCityList;//获取城市列表
    private AddInsuredResponse.DataBean data;//保价费计算方式

    private String[] cityInfo;//当前定位城市id和名称

    private SharedPreferences sp;

    public User(Context context, String account) {
        sp = context.getSharedPreferences(account, Context.MODE_PRIVATE);
        this.account = account;
    }

    /**
     * 登录
     *
     * @param loginInfo 登录信息
     */
    public void login(LoginResponse.DataBean loginInfo) {
        sp.edit().putBoolean("is_login", true).apply();
        setToken(loginInfo.token);
        setPhone(loginInfo.phone);
        setName(loginInfo.name);
        setHead_img(loginInfo.head_img);
        setLevel(loginInfo.level);
    }

    /**
     * 是否记住密码
     *
     * @return
     */
    public boolean isLogin() {
        return sp.getBoolean("is_login", false);
    }

    /**
     * 退出登录
     */
    public void logout() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("is_login", false);
        editor.putString("token", "");
        editor.apply();
    }

    /**
     * 记住密码
     *
     * @param passwd
     */
    public void savePassword(String passwd) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("passwd", passwd);
        editor.putBoolean("is_rem", true);
        editor.apply();
    }

    /**
     * 移除密码
     */
    public void removePassword() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("passwd");
        editor.putBoolean("is_rem", false);
        editor.apply();
    }

    public String getPassword() {
        return sp.getString("passwd", "");
    }

    public boolean isRememberPassword() {
        return sp.getBoolean("is_rem", false);
    }

    /**
     * 更新用户信息
     *
     * @param data
     */
    public void updateUserInfo(UserDataResponse.DataBean data) {
        setPhone(data.phone);
        setName(data.name);
        setMoney(data.money);
        setPoints(data.points);
        setAge(data.age);
        setSex(data.sex);
        setCompany_job(data.company_job);
        setHead_img(data.head_img);
        setType(data.type);
        setState(data.state);
        setLevel(data.level);
        setIndustry(data.industry);
        setStar(data.star);
        setApply_state(data.apply_state);
    }

    /**
     * 保存信息
     *
     * @param key
     * @param value
     */
    private void save(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getAccount() {
        if (account == null) {
            account = sp.getString("acount", "");
        }
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        save("acount", account);
    }

    public String getToken() {
        if (token == null) {
            token = sp.getString("token", "");
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        save("token", token);
    }

    public String getAge() {
        if (age == null) {
            age = sp.getString("age", "");
        }
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        save("age", age);
    }

    public String getApply_state() {
        if (apply_state == null) {
            apply_state = sp.getString("apply_state", "");
        }
        if (apply_state.equals("0")) {
            return "未认证";
        }
        if (apply_state.equals("1")) {
            return "审核中";
        }
        if (apply_state.equals("2")) {
            return "认证通过";
        }
        if (apply_state.equals("3")) {
            return "认证不通过";
        }
        return apply_state;
    }

    public void setApply_state(String apply_state) {
        this.apply_state = apply_state;
        save("apply_state", apply_state);
    }

    public String getCompany_job() {
        if (company_job == null) {
            company_job = sp.getString("company_job", "");
        }
        return company_job;
    }

    public void setCompany_job(String company_job) {
        this.company_job = company_job;
        save("company_job", company_job);
    }

    public Uri getHead_img() {
        if (head_img == null) {
            head_img = sp.getString("head_img", "");
        }
        if (head_img.contains(App.cacheDir.getAbsolutePath())) {
            Log.i("getHead_img", "fromFile: " + Uri.fromFile(new File(head_img)));
            return Uri.fromFile(new File(head_img));
        }
        Log.i("getHead_img", "getImgPath: " + Uri.parse(RequestUrl.getImgPath(head_img)));
        return Uri.parse(RequestUrl.getImgPath(head_img));
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
        save("head_img", head_img);
    }

    public String getIndustry() {
        if (industry == null) {
            industry = sp.getString("industry", "");
        }
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
        save("industry", industry);
    }

    public String getLevel() {
        if (level == null) {
            level = sp.getString("level", "");
        }
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
        save("level", level);
    }

    public String getMoney() {
        if (money == null) {
            money = sp.getString("money", "");
        }
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
        save("money", money);
    }

    public String getName() {
        if (name == null) {
            name = sp.getString("name", "");
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
        save("name", name);
    }

    public String getPhone() {
        if (phone == null) {
            phone = sp.getString("phone", "");
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        save("phone", phone);
    }

    public String getPoints() {
        if (points == null) {
            points = sp.getString("points", "");
        }
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
        save("points", points);
    }

    public String getSex() {
        if (sex == null) {
            sex = sp.getString("sex", "");
        }
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        save("sex", sex);
    }

    public String getStar() {
        if (star == null) {
            star = sp.getString("star", "0");
        }
        return star;
    }

    public void setStar(String star) {
        this.star = star;
        save("star", star);
    }

    public String getState() {
        if (state == null) {
            state = sp.getString("state", "");
        }
        if (state.equals("1")) {
            return "正常";
        }
        if (state.equals("2")) {
            return "冻结";
        }
        if (state.equals("3")) {
            return "帐号资料未完善";
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
        save("state", state);
    }

    public String getType() {
        if (type == null) {
            type = sp.getString("type", "");
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
        save("type", type);
    }

    public List<ProvinceCityListResponse.DataBean> getProvinceCityList() {
        if (provinceCityList == null) {
            provinceCityList = new ArrayList<>();
        }
        return provinceCityList;
    }

    public void setProvinceCityList(List<ProvinceCityListResponse.DataBean> provinceCityList) {
        this.provinceCityList = provinceCityList;
    }

    public ProvinceCityListResponse.DataBean getProvince(String cityName) {
        if (provinceCityList != null) {
            cityName = cityName.replaceAll("市", "");
            for (int i = 0; i < provinceCityList.size(); i++) {
                for (int i1 = 0; i1 < provinceCityList.get(i).city_list.size(); i1++) {
                    if (TextUtils.equals(provinceCityList.get(i).city_list.get(i1).name, cityName)) {
                        return provinceCityList.get(i);
                    }
                }
            }
        }
        return null;
    }

    public ProvinceCityListResponse.DataBean.CityListBean getCity(String cityName) {
        if (provinceCityList != null) {
            cityName = cityName.replaceAll("市", "");
            for (int i = 0; i < provinceCityList.size(); i++) {
                for (int i1 = 0; i1 < provinceCityList.get(i).city_list.size(); i1++) {
                    if (TextUtils.equals(provinceCityList.get(i).city_list.get(i1).name, cityName)) {
                        return provinceCityList.get(i).city_list.get(i1);
                    }
                }
            }
        }
        return null;
    }

    public AddInsuredResponse.DataBean getData() {
        return data;
    }

    public void setData(AddInsuredResponse.DataBean data) {
        this.data = data;
    }

    public String[] getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String[] cityInfo) {
        this.cityInfo = cityInfo;
    }
}
