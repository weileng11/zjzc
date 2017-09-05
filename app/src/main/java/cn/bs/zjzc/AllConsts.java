package cn.bs.zjzc;

import android.content.Intent;

import java.text.SimpleDateFormat;

import cn.bs.zjzc.cache.DataCacheManager;


public interface AllConsts {
    interface IndexConsts {
        // 广告轮播间隔
        int adLoopTime = 2000;
    }

    interface cache {
        // 一次行缓存，退出应用后清除所有缓存
        DataCacheManager onceCache = new DataCacheManager();
        // 应用程序缓存，如设置，首页订单类型等
        DataCacheManager cacheData = new DataCacheManager();
    }


    interface app {
        //客服电话
        String SEVICE_PHONE_NUMBER = "4001669856";
        int gcTime = 500;
        // 网络连接时间
        int netConnectTime = 60;
        // 读取网络数据时间
        int netReadTime = 30;
        // 网络写数据时间
        int netWriteTime = 60;
        // 验证码倒计时
        long getCheckCode = 60000;

        //应用程序缓存根目录名
        String cacheDirName = "Caches";
        //一次行缓存目录
        String tempDir = "/temp";
        //应用缓存，如设置，订单类型等数据
        String cacheDir = "/cacheDatas";
    }

    interface location {
        String privinceKey = "location_privince";
        String cityKey = "location_city";
        String areaKey = "location_area";
    }

    /**
     * 网络请求
     *
     * @author pt-xuejj
     */
    interface http {
        String successErrCode = "1";
        String failedErrCode = "2";
        String loadingErrCode = "3";
    }

    interface DataFormats {
        SimpleDateFormat dateFormatRefresh = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat systemMessageItemDataFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm");
        SimpleDateFormat discountCouplonDateFormat = new SimpleDateFormat(
                "yyyy.MM.dd");
        SimpleDateFormat mindDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat orderLoadFormatRefresh = new SimpleDateFormat("HH:mm");
        SimpleDateFormat ViewLineMenuItem = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    interface TaskName {
        // 系统消息
        String systemRefreshName = "sys_msg_refresh";
        String systemLoadMore = "sys_msg_load_more";

        // 常用地址
        String oftenAddressRefreshName = "of_ad_refresh";
        String oftenAddressLoadMore = "of_ad_load_more";

        // 返程车
        String returnCar = "activity_return_car";
        // 常用司机
        String oftenDriver = "often_driver";
        // 意见反馈列表
        String userMindsTaskName = "user_minds";
    }

    interface IntentAction {
        //修改家地址
        String actionEditHomeAddress = "edit_often_address_home_address";
        //修改公司地址
        String actionEditCompanyAddress = "edit_often_address_company_address";
        //
        String socketBroadcast = "";
    }
}
