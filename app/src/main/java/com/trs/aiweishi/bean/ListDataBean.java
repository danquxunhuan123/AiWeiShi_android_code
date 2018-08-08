package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

import java.util.List;

/**
 * Created by Liufan on 2018/7/3.
 */

public class ListDataBean extends BaseBean {
    private int nowPage;
    private int countPage;
    private List<ListData> list_datas;

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public List<ListData> getList_datas() {
        return list_datas;
    }

    public void setList_datas(List<ListData> list_datas) {
        this.list_datas = list_datas;
    }
}
