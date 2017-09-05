package cn.bs.zjzc.baidumap;

import cn.bs.zjzc.App;
import cn.bs.zjzc.util.L;

/**
 * Created by mgc on 2016/6/28.
 */
public class IndexOfUtils {

    /**
     * @param provinceName
     * @param cityName
     * @param isSetCityId
     * @return
     */
    public static String[] setProvinceAndCityId(String provinceName, String cityName, boolean isSetCityId) {
        String[] ids = new String[2];
        if (App.LOGIN_USER.getProvinceCityList().size() != 0) {
            String province = provinceName.replaceAll("省", "");
            String city = cityName.replaceAll("市", "");
            int indexP = 0;
            for (int i = 0; i < App.LOGIN_USER.getProvinceCityList().size(); i++) {
                indexP = App.LOGIN_USER.getProvinceCityList().get(i).name.indexOf(province);
                if (indexP != -1) {
                    indexP = i;
                    L.d("MGC", indexP + ",province=" + province);
                    ids[0] = App.LOGIN_USER.getProvinceCityList().get(indexP).id;
                    break;
                }
            }
            if (indexP != -1 && App.LOGIN_USER.getProvinceCityList().get(indexP).city_list.size() != 0) {
                int indexC = 0;
                for (int i = 0; i < App.LOGIN_USER.getProvinceCityList().get(indexP).city_list.size(); i++) {
                    indexC = App.LOGIN_USER.getProvinceCityList().get(indexP).city_list.get(i).name.indexOf(city);
                    if (indexC != -1) {
                        indexC = i;
                        L.d("MGC", indexP + ",city=" + city);
                        if (!isSetCityId) {
                            ids[1] = App.LOGIN_USER.getProvinceCityList().get(indexP).city_list.get(indexC).id;
                        } else {
                            App.LOGIN_USER.setCityInfo(new String[]{App.LOGIN_USER.getProvinceCityList().get(indexP).city_list.get(indexC).id, cityName});
                        }
                        break;
                    }
                }
            }
        }
        return ids;
    }

    public static String getProvinceName(String cityName) {
        String city = cityName.replaceAll("市", "");
        L.d("MGC", "replace:" + cityName.replaceAll("市", ""));
        for (int i = 0; i < App.LOGIN_USER.getProvinceCityList().size(); i++) {
            L.d("MGC", "getProvinceName," + city);
            for (int j = 0; j < App.LOGIN_USER.getProvinceCityList().get(i).city_list.size(); j++) {
                if (App.LOGIN_USER.getProvinceCityList().get(i).city_list.get(j).name.equals(city)) {
                    return App.LOGIN_USER.getProvinceCityList().get(i).name;
                }
            }
        }
        return null;
    }
}
