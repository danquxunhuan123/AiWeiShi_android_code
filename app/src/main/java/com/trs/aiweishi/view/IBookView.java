package com.trs.aiweishi.view;

/**
 * Created by Liufan on 2018/9/11.
 */

public interface IBookView extends IBaseView {
    void getUnBooked(String obj);
    void getHistoryBooked(String obj);
    void cancleBooking(String obj);
}
