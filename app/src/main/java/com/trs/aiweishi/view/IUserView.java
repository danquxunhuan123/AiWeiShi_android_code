package com.trs.aiweishi.view;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/5/16.
 */

public interface IUserView extends IBaseView{

    void thirdLoginSuccess(BaseBean bean);

    void checkAccountMapping(BaseBean obj);

    void addAccountMapping(BaseBean obj);
}
