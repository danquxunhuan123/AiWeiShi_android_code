package com.lf.http.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Liufan on 2018/6/30.
 */

public class Site extends BaseBean {
    private List<Monitor> monitoringSites;

    public List<Monitor> getMonitoringSites() {
        return monitoringSites;
    }

    public void setMonitoringSites(List<Monitor> monitoringSites) {
        this.monitoringSites = monitoringSites;
    }

    public static class Monitor implements Parcelable {
        private String city;
        private String country;
        private String description;
        private String detectionWay;
        private String geoHash;
        private String id;
        private String reservable;
        private String provideDetectionPackage;
        private String isFree;
        private String lat;
        private String lon;
        private String orgAddr;
        private String orgId;
        private String orgName;
        private String orgType;
        private String remark;
        private String postcode;
        private String province;
        private String tel;

        protected Monitor(Parcel in) {
            city = in.readString();
            country = in.readString();
            description = in.readString();
            detectionWay = in.readString();
            geoHash = in.readString();
            id = in.readString();
            reservable = in.readString();
            provideDetectionPackage = in.readString();
            isFree = in.readString();
            lat = in.readString();
            lon = in.readString();
            orgAddr = in.readString();
            orgId = in.readString();
            orgName = in.readString();
            orgType = in.readString();
            remark = in.readString();
            postcode = in.readString();
            province = in.readString();
            tel = in.readString();
        }

        public static final Creator<Monitor> CREATOR = new Creator<Monitor>() {
            @Override
            public Monitor createFromParcel(Parcel in) {
                return new Monitor(in);
            }

            @Override
            public Monitor[] newArray(int size) {
                return new Monitor[size];
            }
        };

        public String getDescription() {
            return description;
        }

        public String getDetectionWay() {
            return detectionWay;
        }

        public String getReservable() {
            return reservable;
        }

        public String getIsFree() {
            return isFree;
        }

        public void setIsFree(String isFree) {
            this.isFree = isFree;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getGeoHash() {
            return geoHash;
        }

        public void setGeoHash(String geoHash) {
            this.geoHash = geoHash;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getOrgId() {
            return orgId;
        }

        public String getOrgAddr() {
            return orgAddr;
        }

        public void setOrgAddr(String orgAddr) {
            this.orgAddr = orgAddr;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgType() {
            return orgType;
        }

        public void setOrgType(String orgType) {
            this.orgType = orgType;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(city);
            dest.writeString(country);
            dest.writeString(description);
            dest.writeString(detectionWay);
            dest.writeString(geoHash);
            dest.writeString(id);
            dest.writeString(reservable);
            dest.writeString(provideDetectionPackage);
            dest.writeString(isFree);
            dest.writeString(lat);
            dest.writeString(lon);
            dest.writeString(orgAddr);
            dest.writeString(orgId);
            dest.writeString(orgName);
            dest.writeString(orgType);
            dest.writeString(remark);
            dest.writeString(postcode);
            dest.writeString(province);
            dest.writeString(tel);
        }
    }
}
