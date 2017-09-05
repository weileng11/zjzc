package cn.bs.zjzc.model.response;

import java.io.Serializable;

/**
 * Created by Ming on 2016/6/6.
 */
public class BaseResponse implements Serializable {
    public String errcode;
    public String errinfo;

    @Override
    public String toString() {
        return "{" +
                "errcode:\"" + errcode + '\"' +
                ", errinfo:\"" + errinfo + '\"' +
                '}';
    }
}
