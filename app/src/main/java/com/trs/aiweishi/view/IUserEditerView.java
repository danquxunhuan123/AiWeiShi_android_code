package com.trs.aiweishi.view;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/7/10.
 */

public interface IUserEditerView extends IBaseView {
    void editHeadSuccess(BaseBean bean);
    void refSesSuccess(BaseBean bean);
    void loginSuccess(BaseBean bean);
    void saveInfoSuccess(BaseBean obj);
}
