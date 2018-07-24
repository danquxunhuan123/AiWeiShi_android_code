package com.trs.aiweishi.view;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/6/28.
 */

public interface IBaseView {
    void showSuccess(BaseBean baseBean);
    void showError(Throwable e);
}
