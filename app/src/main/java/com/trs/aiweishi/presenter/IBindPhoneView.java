package com.trs.aiweishi.presenter;

import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.view.IBaseView;

/**
 * Created by Liufan on 2018/7/17.
 */

public interface IBindPhoneView extends IBaseView {
    void loginByUID(BaseBean bean);
    void getUserInfo(BaseBean bean);
}
