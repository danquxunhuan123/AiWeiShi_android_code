package com.trs.aiweishi.http;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.config.MDialogConfig;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

/**
 * Created by Liufan on 2018/5/16.
 */

public class HttpHelper {
    private RetofitApi api;
    private FragmentActivity activity;
    private MDialogConfig config;

    public HttpHelper(FragmentActivity baseActivity) {
        api = HttpMethods.getInstance().info;
        activity = baseActivity;
        config = new MDialogConfig.Builder()
                .isCanceledOnTouchOutside(true)
                .build();
    }

    private void getObservable(Observable<? extends BaseBean> observable, final IResponseCallBack callBack) {
        MProgressDialog.showProgress(activity, config);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        DisposedUtil.getInstance().addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        MProgressDialog.dismissProgress();
                        callBack.onSuccess(baseBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        MProgressDialog.dismissProgress();
                        callBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void addCommenParams(Map<String, String> param) {
        param.put("appName", AppConstant.APP_NAME);
        param.put("type", "json");
    }

    public void getHomeData(String url, IResponseCallBack callBack) {
        getObservable(api.getHomeData(url), callBack);
    }

    public void getChannelData(String url, IResponseCallBack callBack) {
        getObservable(api.getChannelData(url), callBack);
    }

    public void getDetail(String url, IResponseCallBack callBack) {
        getObservable(api.getDetail(url), callBack);
    }

    public void getSearch(String url, Map<String, String> param, IResponseCallBack callBack) {
        getObservable(api.getSearchData(url, param), callBack);
    }

    public void getLocationData(Map<String, String> param, IResponseCallBack callBack) {
        addCommenParams(param);
        getObservable(api.getLocationData(param), callBack);
    }

    public void login(Map<String, String> param, IResponseCallBack callBack) {
        addCommenParams(param);
        getObservable(api.login(param), callBack);
    }

    public void regist(Map<String, String> param, IResponseCallBack callBack) {
        addCommenParams(param);
        getObservable(api.regist(param), callBack);
    }

    public void getCode(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.getCode(params), callBack);
    }

    public void getLogout(Map<String, String> param, IResponseCallBack callBack) {
        addCommenParams(param);
        getObservable(api.getLogout(param), callBack);
    }

    public void notifyUserAttr(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.activateUserAttr(params), callBack);
    }

    public void saveEdit(Map<String, String> param, IResponseCallBack callBack) {
        addCommenParams(param);
        getObservable(api.saveEdit(param), callBack);
    }

    public void changePsd(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.changePsd(params), callBack);
    }

    public void refreshSession(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.refreshSession(params), callBack);
    }

    public void getUserInfo(Map<String, String> param, IResponseCallBack callBack) {
        addCommenParams(param);
        getObservable(api.getUserInfo(param), callBack);
    }

    public void findPsd(Map<String, String> param, IResponseCallBack callBack) {
        addCommenParams(param);
        getObservable(api.findPsd(param), callBack);
    }

    public void loginByUID(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.loginByUID(params), callBack);
    }

    public void checkAccountMapping(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.checkAccountMapping(params), callBack);
    }

    public void addAccountMapping(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.addAccountMapping(params), callBack);
    }

    public void editHeadImg(String url, MultipartBody.Part part, IResponseCallBack callBack) {
        getObservable(api.editHeadImg(url, part), callBack);
    }

    public void feedBack(Map<String, String> params, IResponseCallBack callBack) {
        addCommenParams(params);
        getObservable(api.addFeedBack(params), callBack);
    }
}
