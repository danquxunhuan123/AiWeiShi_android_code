package com.lf.http.model;

import com.lf.http.IResponseCallBack;

import java.util.Map;

import okhttp3.MultipartBody;


/**
 * Created by Liufan on 2018/5/16.
 */

public interface IDataModel {

    void getHomeData(String url,IResponseCallBack callBack);

    void getLocationData(Map<String,String> param,IResponseCallBack callBack);

    void login(Map<String,String> param,IResponseCallBack callBack);

    void regist(Map<String,String> param,IResponseCallBack callBack);

    void getCode(Map<String, String> params, IResponseCallBack callBack);

    void getLogout(Map<String, String> param, IResponseCallBack callBack);

    void getChannelData(String url, IResponseCallBack callBack);

    void getDetail(String url, IResponseCallBack callBack);

    void getSearch(String url, Map<String, String> params, IResponseCallBack callBack);

    void notifyUserAttr(Map<String, String> params, IResponseCallBack callBack);

    void saveEdit(Map<String, String> param, IResponseCallBack callBack);

    void changePsd(Map<String, String> params, IResponseCallBack callBack);

    void refreshSession(Map<String, String> params, IResponseCallBack callBack);

    void getUserInfo(Map<String, String> param, IResponseCallBack callBack);

    void findPsd(Map<String, String> params, IResponseCallBack callBack);

    void loginByUID(Map<String, String> params, IResponseCallBack callBack);

    void checkAccountMapping(Map<String, String> params, IResponseCallBack callBack);

    void addAccountMapping(Map<String, String> params, IResponseCallBack callBack);

    void editHeadImg(String url, MultipartBody.Part part, IResponseCallBack callBack);

    void feedBack(Map<String, String> params, IResponseCallBack callBack);

    void getUpdate(String update, IResponseCallBack callBack);

    void loginQuesiton(String url, IResponseCallBack callBack);

    void submitBooking(String url, Map<String, String> param, IResponseCallBack callBack);

    void getBooked(String url,Map<String, String> param, IResponseCallBack callBack);

    void cancleBook(String url, Map<String, String> param, IResponseCallBack callBack);

    void getAdData(String adUrl, IResponseCallBack callBack);

    void getCheckInfo(String url, Map<String, String> param, IResponseCallBack iResponseCallBack);

    void submitPackage(String url, Map<String, String> param, IResponseCallBack iResponseCallBack);

    void getCheckHistory(String url, Map<String, String> param, IResponseCallBack iResponseCallBack);

    void savePackagePhoto(String url, MultipartBody.Part part, IResponseCallBack iResponseCallBack);

    void requestCommend(String url, Map<String, String> param, IResponseCallBack iResponseCallBack);

    void addMonitoringSite(Map<String, String> param, IResponseCallBack iResponseCallBack);

    void addCorrection(Map<String, String> param, IResponseCallBack iResponseCallBack);
}
