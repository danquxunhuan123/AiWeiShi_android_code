package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

import java.util.List;

/**
 * Created by Liufan on 2018/9/6.
 */

public class WorkTimeBean extends BaseBean{
    private String msg;
    private int count;
    private List<WorkTime> data;

    public List<WorkTime> getData() {
        return data;
    }

    public void setData(List<WorkTime> data) {
        this.data = data;
    }

    public class WorkTime {
        private List<Times> worktime;


        public List<Times> getWorktime() {
            return worktime;
        }

        public void setWorktime(List<Times> worktime) {
            this.worktime = worktime;
        }


        public class Times {
            private String aa;
//            private String day2;
//            private String day3;
//            private String day4;
//            private String day5;
//            private String day6;
//            private String day7;
        }
    }
}
