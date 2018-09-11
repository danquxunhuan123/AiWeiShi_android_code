package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/9/11.
 */

public class YuYueItem extends BaseBean{
    private YuYue data;

    private class YuYue {
        private String bookingId;
        private String monitoringPoint;
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
    }

    public YuYue getData() {
        return data;
    }
}
