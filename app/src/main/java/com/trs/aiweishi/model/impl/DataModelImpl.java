package com.trs.aiweishi.model.impl;

import com.trs.aiweishi.http.HttpHelper;
import com.trs.aiweishi.http.IResponseCallBack;
import com.trs.aiweishi.model.IDataModel;

import java.util.Map;

import javax.inject.Inject;

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

}
