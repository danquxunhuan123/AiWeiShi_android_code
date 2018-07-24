package com.trs.aiweishi.util;

import android.content.Context;

import com.trs.aiweishi.R;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.bean.Site;
import com.trs.aiweishi.bean.TextDrawableBean;

import java.util.List;

/**
 * Created by Liufan on 2018/6/1.
 */

public class DataHelper {
    public static List<ListData> initHomeList(Context context, List<ListData> data, List<ListData> huodongData) {
        int[] draws = new int[]{R.mipmap.icon_zx, R.mipmap.icon_jishu, R.mipmap.icon_zixun,
                R.mipmap.icon_zhishi,R.mipmap.icon_yiyao, R.mipmap.icon_school
        };
//        String[] names = new String[]{context.getResources().getString(R.string.zxun),
//                context.getResources().getString(R.string.jc),
//                context.getResources().getString(R.string.zx),
//                context.getResources().getString(R.string.zs),
//                context.getResources().getString(R.string.yy),
//                context.getResources().getString(R.string.icon_more_list)
//        };
        for (int x = 0; x < data.size(); x++) {
            if(x == 0){
                data.get(x).setType(ListData.PAGE_HEAD_TYPE);
            }else if (x == 1) {
                data.get(x).setType(ListData.BANNER_HEAD_TYPE);
            } else if (x >= 2 && x <= draws.length + 1) {
                data.get(x).setType(TextDrawableBean.TEXT_DRAWABLE_TYPE);
                data.get(x).setDrawable(draws[x - 2]);
            } else if (x == draws.length + 1 + 1 || x == draws.length +1 + 1 + 1 + huodongData.size()) {
                data.get(x).setType(ListData.ITEM_TITLE_TYPE);
            } else {
                data.get(x).setType(ListData.ITEM_COMMEN_TYPE);
            }
        }
        return data;
    }

    public static List<ListData> initDocList(Context context, List<ListData> data) {
        int[] draws = new int[]{R.mipmap.icon_bingli, R.mipmap.icon_jsjd, R.mipmap.icon_daka,
                R.mipmap.icon_peixun, R.mipmap.icon_keji, R.mipmap.icon_huiyi,
//                R.mipmap.icon_gg, R.mipmap.icon_gkk
        };
        String[] names = new String[]{context.getResources().getString(R.string.bltl),
                context.getResources().getString(R.string.jsjd),
                context.getResources().getString(R.string.dks),
                context.getResources().getString(R.string.px),
                context.getResources().getString(R.string.kyjz),
                context.getResources().getString(R.string.hy),
//                context.getResources().getString(R.string.gg),
//                context.getResources().getString(R.string.gkk)
        };
        for (int x = 0; x < data.size(); x++) {
            if (x == 0) {
                data.get(x).setType(ListData.BANNER_HEAD_TYPE);
            } else if (x >= 1 && x <= 6) {
                data.get(x).setType(TextDrawableBean.TEXT_DRAWABLE_TYPE);
                data.get(x).setDrawable(draws[x - 1]);
            } else if (x == 7 || x == 10) {
                data.get(x).setType(ListData.ITEM_TITLE_TYPE);
            } else {
                data.get(x).setType(ListData.ITEM_COMMEN_TYPE);
            }
        }
        return data;
    }

    public static List<ListData> initNgoList(int num,List<ListData> data) {
        for (int x = 0; x < data.size(); x++) {
            if (x == 0)
                data.get(x).setType(ListData.PAGE_HEAD_TYPE);
            else if (x == (1 + 0 * num) || x == (2 + 1 * num) || x == (3 + 2 * num)) {
                data.get(x).setType(ListData.ITEM_TITLE_TYPE);
            } else if (x == (3 + 2 * num + 1)){
                data.get(x).setType(ListData.SCROOL_LINEAR_TYPE);
            }else{
                data.get(x).setType(ListData.ITEM_COMMEN_TYPE);
            }
        }
        return data;
    }

    public static List<Site.Monitor> fillCheckData(List<Site.Monitor> data) {
        for (int x = 0; x < data.size(); x++) {
            Site.Monitor bean = data.get(x);
            bean.setOrgName(bean.getOrgName());
            bean.setOrgAddr(bean.getOrgAddr());
        }
        return data;
    }
}
