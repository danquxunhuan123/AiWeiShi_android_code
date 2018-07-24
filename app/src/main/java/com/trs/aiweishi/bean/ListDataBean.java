package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

import java.util.List;

/**
 * Created by Liufan on 2018/7/3.
 */

public class ListDataBean extends BaseBean {
    public List<ListData> list_datas;

    public List<ListData> getList_datas() {
        return list_datas;
    }

    public void setList_datas(List<ListData> list_datas) {
        this.list_datas = list_datas;
    }
}
