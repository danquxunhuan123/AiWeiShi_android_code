package com.lf.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Liufan on 2018/7/2.
 */

public class UserData extends BaseBean {
    private String sdToken;
    private String coSessionId;
    private User user;
    private User entry;

    public User getEntry() {
        return entry;
    }

    public void setEntry(User entry) {
        this.entry = entry;
    }

    public String getCoSessionId() {
        return coSessionId;
    }

    public String getSdToken() {
        return sdToken;
    }

    public void setSdToken(String sdToken) {
        this.sdToken = sdToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User implements Parcelable {
        private String userId;
        private String nickName;
        private String city;
        private String birthday;
        private String province;
        private String userName;
        private String headUrl;
        private String mobile;

        public User(Parcel in) {
            userId = in.readString();
            nickName = in.readString();
            city = in.readString();
            birthday = in.readString();
            province = in.readString();
            userName = in.readString();
            headUrl = in.readString();
            mobile = in.readString();
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        public User() {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(userId);
            dest.writeString(nickName);
            dest.writeString(city);
            dest.writeString(birthday);
            dest.writeString(province);
            dest.writeString(userName);
            dest.writeString(headUrl);
            dest.writeString(mobile);
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
