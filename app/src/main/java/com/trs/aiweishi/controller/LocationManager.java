package com.trs.aiweishi.controller;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.trs.aiweishi.app.AppAplication;

import javax.inject.Inject;

/**
 * Created by Liufan on 2018/5/16.
 */

public class LocationManager {
    @Inject
    public LocationManager() {
        initLocationClient();
    }

    private LocationClient mLocationClient = null;
    private MyLocationListener locationListener = new MyLocationListener();

    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    private void initLocationClient() {
        mLocationClient = new LocationClient(AppAplication.getInstance());

        LocationClientOption option = new LocationClientOption();
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(0);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(true);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，
        // 若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        //可选，是否需要位置描述信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的位置信息，此处必须为true
        option.setIsNeedLocationDescribe(true);

        //默认为不需要，即参数为false,如需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);

//        mLocationClient.requestHotSpotState();  获取设备所链接网络信息
        mLocationClient.setLocOption(option);

        mLocationClient.registerLocationListener(locationListener);//注册监听函数
    }

    //调用LocationClient的start()方法，便可发起定位请求
    public void startLocation() {
        mLocationClient.start();
    }

    public boolean isStartLocation(){
        return mLocationClient.isStarted();
    }

    public void stopLocation() {
        mLocationClient.stop();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            listener.onLocation(location);
            //connectWifiMac：表示连接WIFI的MAC地址，无连接或者异常时返回NULL
            //hotSpotState有以下三种情况
            //LocationClient.CONNECT_HPT_SPOT_TRUE：连接的是移动热点
            //LocationClient.CONNECT_HPT_SPOT_FALSE：连接的非移动热点
            //LocationClient.CONNECT_HPT_SPOT_UNKNOWN：连接状态未知

            // LogUtils.dTag(Logger.LOG_FLAG,location.getCountry());  //国家  中国
            //城市  北京市  location.getCity();
            //市区、地区  朝阳区  location.getDistrict();
            //街道地址   中国北京市朝阳区科学园南里中街  location.getAddrStr();
            //获取纬度信息  String.valueOf(location.getLatitude());
            //获取经度信息  String.valueOf(location.getLongitude());


            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取位置描述信息相关的结果
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
        }
    }

    private OnLocationListener listener;
    public void setOnLocationListener(OnLocationListener listener){
        this.listener = listener;
    }

    public interface OnLocationListener{
        void onLocation(BDLocation location);
    }
}
