package com.lf.http.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;

import com.lf.http.AppConstant;
import com.lf.http.IResponseCallBack;
import com.lf.http.bean.BaseBean;
import com.lf.http.model.IDataModel;
import com.lf.http.presenter.BasePresenter;
import com.lf.http.presenter.IBindPhoneView;
import com.lf.http.presenter.IUserPresenter;
import com.lf.http.view.IBaseView;
import com.lf.http.view.IBookView;
import com.lf.http.view.IFindPsdView;
import com.lf.http.view.IQuestionView;
import com.lf.http.view.IRegistView;
import com.lf.http.view.IUploadPicView;
import com.lf.http.view.IUserCenterView;
import com.lf.http.view.IUserEditerView;
import com.lf.http.view.IUserView;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by Liufan on 2018/5/16.
 */

public class UserPresenterImpl extends BasePresenter implements IUserPresenter {

    IUserView userView;
    IRegistView registVeiw;
    IFindPsdView findPsdView;
    IUserEditerView editVeiw;
    IUserCenterView userInfoView;
    IBindPhoneView bindPhoneView;
    IQuestionView questionView;

    public UserPresenterImpl(IBaseView baseView, IDataModel dataModel) {
        this.dataModel = dataModel;

        if (baseView instanceof IRegistView)
            registVeiw = (IRegistView) baseView;
        else if (baseView instanceof IUserEditerView)
            editVeiw = (IUserEditerView) baseView;
        else if (baseView instanceof IFindPsdView)
            findPsdView = (IFindPsdView) baseView;
        else if (baseView instanceof IUserView)
            this.userView = (IUserView) baseView;
        else if (baseView instanceof IBindPhoneView)
            this.bindPhoneView = (IBindPhoneView) baseView;
        else if (baseView instanceof IUserCenterView)
            this.userInfoView = (IUserCenterView) baseView;
        else if (baseView instanceof IQuestionView)
            this.questionView = (IQuestionView) baseView;
        else
            this.baseView = baseView;
    }

    @Override
    public void login(final int count, Map<String, String> params) {
        dataModel.login(params, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                if (count == 0)
                    userView.showSuccess((BaseBean) obj);
                else
                    editVeiw.loginSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                if (count == 0)
                    userView.showError(e);
                else
                    editVeiw.showError(e);
            }
        });
    }

    @Override
    public void changePsd(Map<String, String> params) {
        dataModel.changePsd(params, new IResponseCallBack() {

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
    public void regist(Map<String, String> params) {
        dataModel.regist(params, new IResponseCallBack() {
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
    public void notifyUserAttr(final int count, Map<String, String> params) {
        dataModel.notifyUserAttr(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0) {
                    registVeiw.notifyCodeSuccess((BaseBean) obj);
                } else if (count == 1) {
                    findPsdView.notifyCodeSuccess((BaseBean) obj);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (count == 0) {
                    registVeiw.showError(e);
                } else if (count == 1) {
                    findPsdView.showError(e);
                }
            }
        });
    }

    @Override
    public void getCode(final int count, Map<String, String> params) {
        dataModel.getCode(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0)
                    registVeiw.showSuccess((BaseBean) obj);
                else
                    findPsdView.getCodeSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                if (count == 0)
                    registVeiw.showError(e);
                else
                    findPsdView.showError(e);
            }
        });
    }

    @Override
    public void logout(Map<String, String> param) {
        dataModel.getLogout(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                editVeiw.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }

    @Override
    public void saveEdit(Map<String, String> param) {
        dataModel.saveEdit(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                editVeiw.saveInfoSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }

    @Override
    public void refreshSession(Map<String, String> params) {
        dataModel.refreshSession(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                editVeiw.refSesSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }

    @Override
    public void getUserInfo(final int count, Map<String, String> param) {
        dataModel.getUserInfo(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0)
                    userInfoView.getUserInfo((BaseBean) obj);
                if (count == 1)
                    bindPhoneView.getUserInfo((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                if (count == 0)
                    userInfoView.showError(e);
                if (count == 1)
                    bindPhoneView.showError(e);
            }
        });
    }

    @Override
    public void findPsd(Map<String, String> params) {
        dataModel.findPsd(params, new IResponseCallBack() {

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
    public void loginByUID(final int count, Map<String, String> params) {
        dataModel.loginByUID(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0) {
                    userView.thirdLoginSuccess((BaseBean) obj);
                } else {
                    bindPhoneView.loginByUID((BaseBean) obj);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (count == 0) {
                    userView.showError(e);
                } else {
                    bindPhoneView.showError(e);
                }
            }
        });
    }

    @Override
    public void checkAccountMapping(Map<String, String> params) {
        dataModel.checkAccountMapping(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                userView.checkAccountMapping((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                userView.showError(e);
            }
        });
    }

    @Override
    public void addAccountMapping(Map<String, String> params) {
        dataModel.addAccountMapping(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                bindPhoneView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                bindPhoneView.showError(e);
            }
        });
    }

    @Override
    public void editHeadImg(Context context,String path,String pathName,String userPhone,String sessionId) {
        File file = new File(pathName);
        file = new CompressHelper.Builder(context)
//                    .setMaxWidth(720)  // 默认最大宽度为720
//                    .setMaxHeight(960) // 默认最大高度为960
//                    .setQuality(80)
                .setFileName("compress_user_head")
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(path)
                .build()
                .compressToFile(file);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        StringBuffer buffer = new StringBuffer();
        buffer.append(AppConstant.BASE_URL)
                .append("admin/user/editHeadImg.jsp?")
                .append("loginUserName=")
                .append(userPhone)
                .append("&appName=")
                .append(AppConstant.APP_NAME)
                .append("&coSessionId=")
                .append(sessionId)
                .append("&fileName=")
                .append(file);

        dataModel.editHeadImg(buffer.toString(), part, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    editVeiw.editHeadSuccess(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }

    @Override
    public void feedBack(Map<String, String> params) {
        dataModel.feedBack(params, new IResponseCallBack() {

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
    public void loginQuesiton(String url) {
        dataModel.loginQuesiton(url, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    questionView.loginQuesiton(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                questionView.showError(e);
            }
        });
    }

    @Override
    public void getHistoryBooked(String url, Map<String, String> param) {
        dataModel.submitBooking(url, param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    ((IBookView) baseView).getHistoryBooked(((ResponseBody) obj).string());
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
    public void getUnBooked(String url, Map<String, String> param) {
        dataModel.submitBooking(url, param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    ((IBookView) baseView).getUnBooked(((ResponseBody) obj).string());
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
    public void cancleBook(String url, Map<String, String> param) {
        dataModel.cancleBook(url, param, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                try {
                    ((IBookView) baseView).cancleBooking(((ResponseBody) obj).string());
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
    public void getCheckInfo(String url, Map<String, String> param) {
        dataModel.getCheckInfo(url, param, new IResponseCallBack() {
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
    public void getCheckHistory(String url, Map<String, String> param) {
        dataModel.getCheckHistory(url, param, new IResponseCallBack() {
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
    public void savePackagePhoto(Context context, String url, String pathName, String path) {
        File file = new File(pathName);
        file = new CompressHelper.Builder(context)
                .setFileName("compress_user_head")
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(path)
                .build()
                .compressToFile(file);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);


        dataModel.savePackagePhoto(url, part, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    ((IUploadPicView) baseView).savePackagePhoto(((ResponseBody) obj).string());
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
    public void picCommit(String url, Map<String, String> param) {
        dataModel.submitPackage(url, param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                try {
                    ((IUploadPicView) baseView).picCommit(((ResponseBody) obj).string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }
}
