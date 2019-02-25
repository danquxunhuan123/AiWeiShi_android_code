package com.lf.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Liufan on 2018/10/18.
 */

public class AdBean extends BaseBean implements Parcelable {
    private String adTitle;
    private String isShow; //		是否显示
    private String imageUrl;//		图片地址
    private String adUrl;//		广告地址
    private String updateTime;//	更新时间

    protected AdBean(Parcel in) {
        adTitle = in.readString();
        isShow = in.readString();
        imageUrl = in.readString();
        adUrl = in.readString();
        updateTime = in.readString();
    }

    public static final Creator<AdBean> CREATOR = new Creator<AdBean>() {
        @Override
        public AdBean createFromParcel(Parcel in) {
            return new AdBean(in);
        }

        @Override
        public AdBean[] newArray(int size) {
            return new AdBean[size];
        }
    };

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AdBean{" +
                "isShow=" + isShow +
                ", imageUrl='" + imageUrl + '\'' +
                ", adUrl='" + adUrl + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adTitle);
        dest.writeString(isShow);
        dest.writeString(imageUrl);
        dest.writeString(adUrl);
        dest.writeString(updateTime);
    }
}
