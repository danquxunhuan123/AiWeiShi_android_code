package com.lf.http.view;

import com.lf.http.bean.BaseBean;

/**
 * Created by Liufan on 2018/5/17.
 */

public interface IHomeView extends IBaseView{
    void showBanner(BaseBean baseBean);
    void showHuoDong(BaseBean baseBean);
}
