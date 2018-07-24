package com.trs.aiweishi.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.trs.aiweishi.base.BaseBean;

import java.util.List;

/**
 * Created by Liufan on 2018/7/6.
 */

public class SearchBean extends BaseBean {
    private int totalCount;
    private int page;
    private int pagesize;
    private List<SearchData> docData;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public List<SearchData> getDocData() {
        return docData;
    }

    public void setDocData(List<SearchData> docData) {
        this.docData = docData;
    }

    public static class SearchData implements Parcelable{
        private String ID;
        private String ORGNAME;
        private String ORGTYPE;
        private String ORGADDR;
        private String PROVINCE;
        private String CITY;
        private String COUNTRY;
        private String POSTCODE;
        private String TEL;
        private String GEOHASH;
        private String LON;
        private String LAT;

        private String docid;
        private String doctitle;
        private String docreltime;
        private String docpuburl;

        protected SearchData(Parcel in) {
            ID = in.readString();
            ORGNAME = in.readString();
            ORGTYPE = in.readString();
            ORGADDR = in.readString();
            PROVINCE = in.readString();
            CITY = in.readString();
            COUNTRY = in.readString();
            POSTCODE = in.readString();
            TEL = in.readString();
            GEOHASH = in.readString();
            LON = in.readString();
            LAT = in.readString();
            docid = in.readString();
            doctitle = in.readString();
            docreltime = in.readString();
            docpuburl = in.readString();
        }

        public static final Creator<SearchData> CREATOR = new Creator<SearchData>() {
            @Override
            public SearchData createFromParcel(Parcel in) {
                return new SearchData(in);
            }

            @Override
            public SearchData[] newArray(int size) {
                return new SearchData[size];
            }
        };

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getORGNAME() {
            return ORGNAME;
        }

        public void setORGNAME(String ORGNAME) {
            this.ORGNAME = ORGNAME;
        }

        public String getORGTYPE() {
            return ORGTYPE;
        }

        public void setORGTYPE(String ORGTYPE) {
            this.ORGTYPE = ORGTYPE;
        }

        public String getORGADDR() {
            return ORGADDR;
        }

        public void setORGADDR(String ORGADDR) {
            this.ORGADDR = ORGADDR;
        }

        public String getPROVINCE() {
            return PROVINCE;
        }

        public void setPROVINCE(String PROVINCE) {
            this.PROVINCE = PROVINCE;
        }

        public String getCITY() {
            return CITY;
        }

        public void setCITY(String CITY) {
            this.CITY = CITY;
        }

        public String getCOUNTRY() {
            return COUNTRY;
        }

        public void setCOUNTRY(String COUNTRY) {
            this.COUNTRY = COUNTRY;
        }

        public String getPOSTCODE() {
            return POSTCODE;
        }

        public void setPOSTCODE(String POSTCODE) {
            this.POSTCODE = POSTCODE;
        }

        public String getTEL() {
            return TEL;
        }

        public void setTEL(String TEL) {
            this.TEL = TEL;
        }

        public String getGEOHASH() {
            return GEOHASH;
        }

        public void setGEOHASH(String GEOHASH) {
            this.GEOHASH = GEOHASH;
        }

        public String getLON() {
            return LON;
        }

        public void setLON(String LON) {
            this.LON = LON;
        }

        public String getLAT() {
            return LAT;
        }

        public void setLAT(String LAT) {
            this.LAT = LAT;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getDoctitle() {
            return doctitle;
        }

        public void setDoctitle(String doctitle) {
            this.doctitle = doctitle;
        }

        public String getDocreltime() {
            return docreltime;
        }

        public void setDocreltime(String docreltime) {
            this.docreltime = docreltime;
        }

        public String getDocpuburl() {
            return docpuburl;
        }

        public void setDocpuburl(String docpuburl) {
            this.docpuburl = docpuburl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(ID);
            dest.writeString(ORGNAME);
            dest.writeString(ORGTYPE);
            dest.writeString(ORGADDR);
            dest.writeString(PROVINCE);
            dest.writeString(CITY);
            dest.writeString(COUNTRY);
            dest.writeString(POSTCODE);
            dest.writeString(TEL);
            dest.writeString(GEOHASH);
            dest.writeString(LON);
            dest.writeString(LAT);
            dest.writeString(docid);
            dest.writeString(doctitle);
            dest.writeString(docreltime);
            dest.writeString(docpuburl);
        }
    }
}
