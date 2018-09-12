package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/9/11.
 */

public class YuYueItem extends BaseBean {
    private String bookingId;
    private String monitoringPoint;
    private String bookingStatus;
    private String bookingTime;
    private String monitoringPointId;

    public String getBookingId() {
        return bookingId;
    }

    public String getMonitoringPoint() {
        return monitoringPoint;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public String getMonitoringPointId() {
        return monitoringPointId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setMonitoringPoint(String monitoringPoint) {
        this.monitoringPoint = monitoringPoint;


    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setMonitoringPointId(String monitoringPointId) {
        this.monitoringPointId = monitoringPointId;
    }
}
