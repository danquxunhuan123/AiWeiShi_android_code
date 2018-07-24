package com.trs.aiweishi.presenter;

import android.support.annotation.VisibleForTesting;

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
}
