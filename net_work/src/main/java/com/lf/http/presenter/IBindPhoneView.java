package com.lf.http.presenter;

import com.lf.http.bean.BaseBean;
import com.lf.http.view.IBaseView;

/**
 * Created by Liufan on 2018/7/17.
 */

public interface IBindPhoneView extends IBaseView {
    void loginByUID(BaseBean bean);

    void getUserInfo(BaseBean bean);
}
