package cn.bs.zjzc.model.bean;

/**
 * Created by yiming on 2016/6/20.
 */
public class RegisterRequestBody {
    public String token;
    public String acount;
    public String name;
    public String passwd;
    public String confirmPassword;
    public String code;

    public RegisterRequestBody(String token, String acount, String name, String passwd, String confirmPassword, String code) {
        this.token = token;
        this.acount = acount;
        this.name = name;
        this.passwd = passwd;
        this.confirmPassword = confirmPassword;
        this.code = code;
    }
}
