package com.trs.aiweishi.bean;

import com.trs.aiweishi.base.BaseBean;

import java.util.List;

/**
 * Created by Liufan on 2018/7/3.
 */

public class HomeBean extends BaseBean {
    private List<ListData> channel_list;

    public List<ListData> getChannel_list() {
        return channel_list;
    }

    public void setChannel_list(List<ListData> channel_list) {
        this.channel_list = channel_list;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "channel_list=" + channel_list +
                '}';
    }
}
