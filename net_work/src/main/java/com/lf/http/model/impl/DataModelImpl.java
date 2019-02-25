package com.lf.http.model.impl;

import com.lf.http.HttpHelper;
import com.lf.http.IResponseCallBack;
import com.lf.http.model.IDataModel;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Liufan on 2018/5/16.
 */

public class DataModelImpl implements IDataModel {

    HttpHelper httpHelper;

    public DataModelImpl(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public void getHomeData(String url,IResponseCallBack callBack) {
        httpHelper.getHomeData(url,callBack);
    }

    @Override
    public void getLocationData(Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.getLocationData(param, callBack);
    }

    @Override
    public void login(Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.login(param, callBack);
    }

    @Override
    public void regist(Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.regist(param, callBack);
    }

    @Override
    public void getCode(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.getCode(params, callBack);
    }

    @Override
    public void getLogout(Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.getLogout(param, callBack);
    }


    @Override
    public void getChannelData(String url, IResponseCallBack callBack) {
        httpHelper.getChannelData(url, callBack);
    }

    @Override
    public void getDetail(String url, IResponseCallBack callBack) {
        httpHelper.getDetail(url, callBack);
    }

    @Override
    public void getSearch(String url, Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.getSearch(url, params,callBack);
    }

    @Override
    public void notifyUserAttr(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.notifyUserAttr(params,callBack);
    }

    @Override
    public void saveEdit(Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.saveEdit(param,callBack);
    }

    @Override
    public void changePsd(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.changePsd(params,callBack);
    }

    @Override
    public void refreshSession(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.refreshSession(params,callBack);
    }

    @Override
    public void getUserInfo(Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.getUserInfo(param,callBack);
    }

    @Override
    public void findPsd(Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.findPsd(param,callBack);
    }

    @Override
    public void loginByUID(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.loginByUID(params,callBack);
    }

    @Override
    public void checkAccountMapping(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.checkAccountMapping(params,callBack);
    }

    @Override
    public void addAccountMapping(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.addAccountMapping(params,callBack);
    }

    @Override
    public void editHeadImg(String url, MultipartBody.Part part, IResponseCallBack callBack) {
        httpHelper.editHeadImg(url,part,callBack);
    }

    @Override
    public void feedBack(Map<String, String> params, IResponseCallBack callBack) {
        httpHelper.feedBack(params,callBack);
    }

    @Override
    public void getUpdate(String update, IResponseCallBack callBack) {
        httpHelper.getUpdate(update,callBack);
    }

    @Override
    public void loginQuesiton(String url, IResponseCallBack callBack) {
        httpHelper.loginQuesiton(url,callBack);
    }

    @Override
    public void submitBooking(String url, Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.submitBooking(url,param,callBack);
    }

    @Override
    public void getBooked(String url, Map<String, String> param,IResponseCallBack callBack) {
//        httpHelper.getBook(url,param,callBack);
    }

    @Override
    public void cancleBook(String url, Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.cancleBook(url,param,callBack);
    }

    @Override
    public void getAdData(String adUrl, IResponseCallBack callBack) {
        httpHelper.getAdData(adUrl,callBack);
    }

    @Override
    public void getCheckInfo(String url, Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.getCheckInfo(url,param,callBack);
    }

    @Override
    public void submitPackage(String url, Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.submitPackage(url,param,callBack);
    }

    @Override
    public void getCheckHistory(String url, Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.getCheckHistory(url,param,callBack);
    }

    @Override
    public void savePackagePhoto(String url, MultipartBody.Part part, IResponseCallBack callBack) {
        httpHelper.savePackagePhoto(url,part,callBack);
    }

    @Override
    public void requestCommend(String url, Map<String, String> param, IResponseCallBack callBack) {
        httpHelper.requestCommend(url,param,callBack);
    }

    @Override
    public void addMonitoringSite(Map<String, String> param, IResponseCallBack iResponseCallBack) {
        httpHelper.addMonitoringSite(param,iResponseCallBack);
    }

    @Override
    public void addCorrection(Map<String, String> param, IResponseCallBack iResponseCallBack) {
        httpHelper.addCorrection(param,iResponseCallBack);
    }

}
