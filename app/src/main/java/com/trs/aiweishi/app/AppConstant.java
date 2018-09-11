package com.trs.aiweishi.app;

/**
 * Created by Liufan on 2018/5/16.
 */
public class AppConstant {
    //打电话
    public static String CALL_PHONE = "android.permission.CALL_PHONE";
    //获取手机状态
    public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    //获取位置信息
    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String ACCESS_WIFI_STATE = "android.permission.ACCESS_FINE_LOCATION";
    //读写SD卡
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";

    public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static final String HEAD_URL= "http://139.199.128.170:9090";
    public static final String BASE_URL= "http://139.199.128.170:9090/ids/";
    public static final String BASE_URL_WCM= "http://139.199.128.170:8080/pub/aws/channels.json";
    public static String XIAOSI = "http://139.199.128.170/xiaosi/index.html";
    public static String SEARCH = "http://139.199.128.170/search/sitesearch.jsp";
    public static String SEARCH_JCD = "http://139.199.128.170/search/jianchadian.jsp";
    public static String UPDATE = "http://www.iws365.com/bx/version.json";
    public static String YINGYONGBAO = "yingyongbao_url";

    public static  String GET_NGO_INFO = "http://139.199.128.170/wcm/rws/api/getNGOinfos.jsp";
    public static  String SUBMIT_BOOKING = "http://139.199.128.170/wcm/rws/api/submitBooking.jsp";
    public static  String FIND_NGO_BYID = "http://139.199.128.170/wcm/rws/api/findbyIdNgoinfo.jsp";
    public static String GET_HISTORY_BOOKED = "http://139.199.128.170/wcm/rws/api/getHistoryBooked.jsp";
    public static String CANCLE_BOOKED = "http://139.199.128.170/wcm/rws/api/cancelBooked.jsp";
    public static String GET_BOOKED = "http://139.199.128.170/wcm/rws/api/getBooked.jsp";

    public static String LOG_FLAG = "logFlag";
    public static String OK_HTTP = "okHttpUtil";
    public static String SP_NAME = "AiWeiShi";
    public static String IS_LOGIN = "isLogin";
    public static String SESSION_ID = "session_id";
    public static String APP_NAME = "IWSApp";
    public static String USER_PIC = "user_pic";
    public static String USER_PHONE = "user_phone";
    public static String USER_NAME = "user_name";
    public static String USER_BIRTHDAT = "user_birthday";
    public static String UER_PSD = "user_psd";
    public static String AUTH_SITE = "auth_site";
    public static String IS_UPDATE = "is_update";
    public static String CANCLE_UPDATE = "cancle_update";
}
