package cn.bs.zjzc.baidumap;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 百度地图定位参数设置
 * Created by mgc on 2016/6/27.
 */
public class BaiduMapLocate {

    // 定位相关
    private LocationClient mLocClient;

    public BaiduMapLocate(LocationClient mLocClient) {
        this.mLocClient = mLocClient;
        setOptions();
    }

    private void setOptions() {
        /*设置定位参数*/
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        mLocClient.setLocOption(option);
    }

    public void startLoc() {
        mLocClient.start();
    }
}
