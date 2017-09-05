package cn.bs.zjzc.util;

import android.util.Base64;

import java.util.Map;

public class CodeUtils {// encode和decode

    static public String encodeToString(String code) {
        if (code.length() == 0 || code == null) {
            return "";
        }
        return Base64.encodeToString(code.getBytes(), Base64.NO_WRAP);
    }

    static public String decodeToSgtring(String code) {
        if (code.length() == 0 || code == null) {
            return "";
        }
        return new String(Base64.decode(code.getBytes(), Base64.NO_WRAP));
    }

    static public String decodeToSgtring(byte[] code) {
        if (code.length == 0 || code == null) {
            return "";
        }
        return new String(Base64.decode(code, Base64.NO_WRAP));
    }

    static public byte[] decodeToByte(String code) {
        if (code.length() == 0 || code == null) {
            return null;
        }
        return Base64.decode(code.getBytes(), Base64.NO_WRAP);
    }

    static public byte[] decodeToByte(byte[] code) {
        if (code.length == 0 || code == null) {
            return null;
        }
        return Base64.decode(code, Base64.NO_WRAP);
    }
}