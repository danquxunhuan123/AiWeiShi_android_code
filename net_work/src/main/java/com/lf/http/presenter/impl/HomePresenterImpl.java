package com.lf.http.presenter.impl;

import com.google.gson.Gson;
import com.lf.http.IResponseCallBack;
import com.lf.http.bean.BaseBean;
import com.lf.http.bean.UpdateBean;
import com.lf.http.model.IDataModel;
import com.lf.http.presenter.BasePresenter;
import com.lf.http.presenter.IHomePresenter;
import com.lf.http.view.IBaseView;
import com.lf.http.view.ICheckView;
import com.lf.http.view.IDetailView;
import com.lf.http.view.IHomeView;
import com.lf.http.view.IMainView;
import com.lf.http.view.INgoView;
import com.lf.http.view.ITimeView;
import com.lf.http.view.IZiXunView;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by Liufan on 2018/5/17.
 */

public class HomePresenterImpl extends BasePresenter implements IHomePresenter {

    public HomePresenterImpl(IBaseView iBaseView, IDataModel dataModel) {  //
        this.baseView = iBaseView;
        this.dataModel = dataModel;
    }

    @Override
    public void getHomeData(String url) {
        dataModel.getHomeData(url, new IResponseCallBack() {
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
    public void getNgoData(final int count, String url) {
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
        dataModel.getSearch(url, params, new IResponseCallBack() {
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
    public void updateInfo(String update) {
        dataModel.getUpdate(update, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                ((IMainView) baseView).update((UpdateBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getYuYueTime(String url, Map<String, String> params) {
        dataModel.submitBooking(url, params, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                try {
                    ((ITimeView) baseView).getYuYueTime(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getNgoInfo(String url) {
        dataModel.loginQuesiton(url, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                try {
                    ((ITimeView) baseView).getNgoInfo(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void submitBooking(String url, Map<String, String> param) {
        dataModel.submitBooking(url, param, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                try {
                    ((ITimeView) baseView).submitBooking(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getAdData(String adUrl) {
        dataModel.getAdData(adUrl, new IResponseCallBack() {
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
    public void submitPackage(String url, Map<String, String> param) {
        dataModel.submitPackage(url, param, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                try {
                    ((ITimeView) baseView).submitPackage(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void clickToRead(String url, Map<String, String> param) {
        dataModel.requestCommend(url, param, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                try {
                    ((IZiXunView) baseView).clickToRead(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void getRead(String url, Map<String, String> param) {
        dataModel.requestCommend(url, param, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                try {
                    ((IDetailView) baseView).getRead(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void addCorrection(Map<String, String> param) {
        dataModel.addCorrection(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    ((ICheckView) baseView).checkSuccess(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void addMonitoringSite(Map<String, String> param) {
        dataModel.addMonitoringSite(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    ((ICheckView) baseView).checkSuccess(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }
}
