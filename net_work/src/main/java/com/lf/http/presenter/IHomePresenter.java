package com.lf.http.presenter;

import java.util.Map;

/**
 * Created by Liufan on 2018/5/17.
 */

public interface IHomePresenter {

    void getHomeData(String url);

    void getLocationData(Map<String,String> param);

    void getBannerData(String url);

    void getHuoDongData(String url);

    void getNgoData(int count,String url);

    void getChannelData(String url);

    void getDetailData(String url);

    void getSearchData(String url, Map<String, String> params);

    void updateInfo(String update);

    void getYuYueTime(String s,Map<String, String> params);

    void getNgoInfo(String url);

    void submitBooking(String submitBooking, Map<String, String> param);

    void getAdData(String adUrl);

    void submitPackage(String url, Map<String, String> param);

    void clickToRead(String url, Map<String, String> param);

    void getRead(String getReading, Map<String, String> param);

    void addCorrection(Map<String, String> param);

    void addMonitoringSite(Map<String, String> param);
}
