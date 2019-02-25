package com.lf.http.view;


/**
 * Created by Liufan on 2018/9/11.
 */

public interface IBookView extends IBaseView {
    void getUnBooked(String result);
    void getHistoryBooked(String result);
    void cancleBooking(String obj);
}
