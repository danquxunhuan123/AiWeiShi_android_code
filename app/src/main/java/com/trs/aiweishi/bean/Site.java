package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

import java.io.Serializable;
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

    public class Monitor implements Serializable{
        private String city;
        private String country;
        private String geoHash;
        private String id;
        private String isFree;
        private String lat;
        private String lon;
        private String orgAddr;
        private String orgName;
        private String orgType;
        private String remark;
        private String postcode;
        private String province;
        private String tel;

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
    }
}
