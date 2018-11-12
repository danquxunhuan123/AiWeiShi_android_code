package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/12 0012.
 */

public class CheckResult extends BaseBean {
    private int count;
    private List<CResult> data;
    private String msg;

    public int getCount() {
        return count;
    }

    public List<CResult> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public class CResult {
        private int bookingId;
        private String checkPerson;
        private String checkWay;
        private String referralPlace;
        private String checkResult;
        private String referral;
        private String checkTime;
        private String checkPlace;

        public int getBookingId() {
            return bookingId;
        }

        public String getCheckPerson() {
            return checkPerson;
        }

        public String getCheckWay() {
            return checkWay;
        }

        public String getReferralPlace() {
            return referralPlace;
        }

        public String getCheckResult() {
            return checkResult;
        }

        public String getReferral() {
            return referral;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public String getCheckPlace() {
            return checkPlace;
        }
    }
}
