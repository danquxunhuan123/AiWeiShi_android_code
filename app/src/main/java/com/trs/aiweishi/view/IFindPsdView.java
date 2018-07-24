package com.trs.aiweishi.view;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/7/12.
 */

public interface IFindPsdView extends IBaseView {
    void getCodeSuccess(BaseBean obj);

    void notifyCodeSuccess(BaseBean obj);
}
