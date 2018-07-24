package com.trs.aiweishi.presenter.impl;

import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BasePresenter;
import com.trs.aiweishi.http.IResponseCallBack;
import com.trs.aiweishi.model.IDataModel;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.view.IBaseView;
import com.trs.aiweishi.view.IHomeView;
import com.trs.aiweishi.view.INgoView;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Liufan on 2018/5/17.
 */

public class HomePresenterImpl extends BasePresenter implements IHomePresenter {

    public HomePresenterImpl(IBaseView iBaseView,IDataModel dataModel) {  //
        this.baseView = iBaseView;
        this.dataModel = dataModel;
    }

    @Override
    public void getHomeData(String url) {
        dataModel.getHomeData(url,new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getLocationData(Map<String, String> param) {
        dataModel.getLocationData(param, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getBannerData(String url) {
        dataModel.getChannelData(url, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                ((IHomeView) baseView).showBanner((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getHuoDongData(String url) {
        dataModel.getChannelData(url, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                ((IHomeView) baseView).showHuoDong((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getNgoData(final int count,String url) {
        dataModel.getChannelData(url, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                if (count == 0)
                    ((INgoView) baseView).showChannelOne((BaseBean) obj);
                if (count == 1)
                    ((INgoView) baseView).showChannelTwo((BaseBean) obj);
                if (count == 2)
                    baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getChannelData(String url) {
        dataModel.getChannelData(url, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getDetailData(String url) {
        dataModel.getDetail(url, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getSearchData(String url, Map<String, String> params) {
        dataModel.getSearch(url,params, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

}
