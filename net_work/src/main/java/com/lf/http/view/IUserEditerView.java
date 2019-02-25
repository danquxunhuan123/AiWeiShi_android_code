package com.lf.http.view;

import com.lf.http.bean.BaseBean;

/**
 * Created by Liufan on 2018/7/10.
 */

public interface IUserEditerView extends IBaseView {
    void editHeadSuccess(String res);
    void refSesSuccess(BaseBean bean);
    void loginSuccess(BaseBean bean);
    void saveInfoSuccess(BaseBean obj);
}
