package com.lf.http.view;

import com.lf.http.bean.BaseBean;

/**
 * Created by Liufan on 2018/6/28.
 */

public interface IBaseView {
    void showSuccess(BaseBean baseBean);
    void showError(Throwable e);
}
