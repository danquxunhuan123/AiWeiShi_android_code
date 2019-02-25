package com.lf.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by TRS on 2018/11/14.
 */

public class CheckPackegBean extends BaseBean {
    private int count;
    private List<CPackeg> data;
    private String msg;

    public int getCount() {
        return count;
    }

    public List<CPackeg> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static class CPackeg implements Parcelable {
        private int bookingId;
        private String monitoringPoint;
        private String bookingTime;
        private String checkResult;
        private String way;
        private String monitoringPointId;
        private String packageStatus;
        private String deleteReason;

        protected CPackeg(Parcel in) {
            bookingId = in.readInt();
            monitoringPoint = in.readString();
            bookingTime = in.readString();
            checkResult = in.readString();
            way = in.readString();
            monitoringPointId = in.readString();
            packageStatus = in.readString();
            deleteReason = in.readString();
        }

        public static final Creator<CPackeg> CREATOR = new Creator<CPackeg>() {
            @Override
            public CPackeg createFromParcel(Parcel in) {
                return new CPackeg(in);
            }

            @Override
            public CPackeg[] newArray(int size) {
                return new CPackeg[size];
            }
        };

        public int getBookingId() {
            return bookingId;
        }

        public String getMonitoringPoint() {
            return monitoringPoint;
        }

        public String getBookingTime() {
            return bookingTime;
        }

        public String getCheckResult() {
            return checkResult;
        }

        public String getWay() {
            return way;
        }

        public String getMonitoringPointId() {
            return monitoringPointId;
        }

        public String getPackageStatus() {
            return packageStatus;
        }

        public String getDeleteReason() {
            return deleteReason;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(bookingId);
            dest.writeString(monitoringPoint);
            dest.writeString(bookingTime);
            dest.writeString(checkResult);
            dest.writeString(way);
            dest.writeString(monitoringPointId);
            dest.writeString(packageStatus);
            dest.writeString(deleteReason);
        }
    }
}
