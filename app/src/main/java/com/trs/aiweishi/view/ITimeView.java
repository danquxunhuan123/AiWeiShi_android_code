package com.trs.aiweishi.view;

/**
 * Created by Liufan on 2018/9/6.
 */

public interface ITimeView extends IBaseView{
    void getYuYueTime(String json);
    void getNgoInfo(String json);

    void submitBooking(String string);
}
