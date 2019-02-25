package com.lf.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Liufan on 2018/7/3.
 */

public class HomeBean extends BaseBean implements Parcelable {
    private List<ListData> channel_list;

    protected HomeBean(Parcel in) {
        channel_list = in.createTypedArrayList(ListData.CREATOR);
    }

    public static final Creator<HomeBean> CREATOR = new Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel in) {
            return new HomeBean(in);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };

    public List<ListData> getChannel_list() {
        return channel_list;
    }

    public void setChannel_list(List<ListData> channel_list) {
        this.channel_list = channel_list;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "channel_list=" + channel_list +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(channel_list);
    }
}
