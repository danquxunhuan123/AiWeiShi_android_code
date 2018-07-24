package com.trs.aiweishi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.trs.aiweishi.base.BaseBean;

/**
 * Created by Liufan on 2018/7/2.
 */

public class UserBean extends BaseBean implements Parcelable{

    private UserData data;

    protected UserBean(Parcel in) {
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
