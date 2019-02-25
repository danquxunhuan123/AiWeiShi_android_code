package com.lf.http.presenter;

import android.content.Context;

import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by Liufan on 2018/5/16.
 */

public interface IUserPresenter {

    void login(int count,Map<String,String> params);
    void regist(Map<String,String> params);
    void getCode(int count,Map<String, String> params);
    void logout(Map<String, String> param);
    void notifyUserAttr(int count,Map<String, String> params);
    void saveEdit(Map<String, String> param);
    void changePsd(Map<String, String> params);

    void refreshSession(Map<String, String> param);

    void getUserInfo(int count,Map<String, String> param);

    void findPsd(Map<String, String> params);

    void loginByUID(int count,Map<String, String> params);

    void checkAccountMapping(Map<String, String> params);

    void addAccountMapping(Map<String, String> params);

    void editHeadImg(Context context,String path,String pathName,String userPhone,String sessionId);
    void feedBack(Map<String, String> params);

    void loginQuesiton(String s);

    void getHistoryBooked(String url, Map<String, String> param);
    void getUnBooked(String url, Map<String, String> param);
    void cancleBook(String url, Map<String, String> param);

    void getCheckInfo(String checkResult, Map<String, String> param);

    void getCheckHistory(String checkHistoryResult, Map<String, String> param);

    void savePackagePhoto(Context context, String url, String pathName, String path);

    void picCommit(String packagReport, Map<String, String> param);
}
