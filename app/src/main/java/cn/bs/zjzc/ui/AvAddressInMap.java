package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import cn.bs.zjzc.R;
import cn.bs.zjzc.util.L;

/**
 * Created by Administrator on 2016/7/4.
 */
public class AvAddressInMap extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private double mX;
    private double mY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.av_address_in_map);

        mMapView = ((MapView) findViewById(R.id.mapView));
        mBaiduMap = mMapView.getMap();
// 删除百度地图LoGo
        mMapView.removeViewAt(1);

        mX = getIntent().getDoubleExtra("x", 0);
        mY = getIntent().getDoubleExtra("y", 0);
        //定义Maker坐标点
        LatLng point = new LatLng(mX, mY);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.marker);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLngZoom(point, 15);
        mBaiduMap.setMapStatus(mapStatus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
