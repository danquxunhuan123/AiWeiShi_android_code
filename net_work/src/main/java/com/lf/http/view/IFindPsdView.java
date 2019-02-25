package com.lf.http.view;

import com.lf.http.bean.BaseBean;

/**
 * Created by Liufan on 2018/7/12.
 */

public interface IFindPsdView extends IBaseView {
    void getCodeSuccess(BaseBean obj);

    void notifyCodeSuccess(BaseBean obj);
}
