package com.trs.aiweishi.app;

/**
 * Created by Liufan on 2018/5/16.
 */
public class AppConstant {
    /*----------------------权限--------------------*/
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
    /*----------------------接口相关--------------------*/
    public static final String HEAD_URL = "http://139.199.128.170:9090";
    public static final String BASE_URL = "http://139.199.128.170:9090/ids/";
    public static final String BASE_URL_WCM = "http://139.199.128.170:8080/pub/aws/channels.json";
    public static final String CLICK_TO_READ = "http://139.199.128.170/wcm/rws/api/clickToRead.jsp";
    public static final String GET_READING = "http://139.199.128.170/wcm/rws/api/getReading.jsp";
    public static String XIAOSI = "http://139.199.128.170/xiaosi/index.html";
    public static String SEARCH = "http://139.199.128.170/search/sitesearch.jsp";
    public static String SEARCH_JCD = "http://139.199.128.170/search/jianchadian.jsp";
    public static String UPDATE = "http://www.iws365.com/bx/version.json";
    public static String YINGYONGBAO = "yingyongbao_url";
    public static String AD_URL = "http://www.iws365.com/banner/index.json";
    //ngo
    public static String GET_NGO_INFO = "http://139.199.128.170/wcm/rws/api/getNGOinfos.jsp";
    public static String SUBMIT_BOOKING = "http://139.199.128.170/wcm/rws/api/submitBooking.jsp";
    public static String FIND_NGO_BYID = "http://139.199.128.170/wcm/rws/api/findbyIdNgoinfo.jsp";
    public static String GET_HISTORY_BOOKED = "http://139.199.128.170/wcm/rws/api/getHistoryBooked.jsp";
    public static String CANCLE_BOOKED = "http://139.199.128.170/wcm/rws/api/cancelBooked.jsp";
    public static String GET_BOOKED = "http://139.199.128.170/wcm/rws/api/getBooked.jsp";
    //自检包
    public static String CHECK_RESULT = "http://139.199.128.170/wcm/rws/api/getCheckResult.jsp";
    public static String CHECK_HISTORY_RESULT = "http://139.199.128.170/wcm/rws/api/getHistoryPackage.jsp";
    public static String SUBMIT_PACKAGE = "http://139.199.128.170/wcm/rws/api/submitPackage.jsp";
    public static String SAVE_PACKAGE_PHOTO = "http://139.199.128.170/wcm/rws/api/savePackagePhoto.jsp";
    public static String PACKAG_REPORT = "http://139.199.128.170/wcm/rws/api/packageReport.jsp";
    public static String APPLAY_CHECK_PACKAGE = "http://www.iws365.com/getPackageInfo/info.html?monitoringPointId=";
    /*----------------------sp字段--------------------*/
    public static String OK_HTTP = "okHttpUtil";
    public static String SP_NAME = "AiWeiShi";
    public static String IS_LOGIN = "isLogin";
    public static String SESSION_ID = "session_id";
    public static String APP_NAME = "IWSApp";
    public static String USER_PIC = "user_pic";
    public static String USER_PHONE = "user_phone";
    public static String USER_NAME = "user_name";
    public static String UER_PSD = "user_psd";
    public static String AUTH_SITE = "auth_site";
    public static String IS_UPDATE = "is_update";
    public static String CANCLE_UPDATE = "cancle_update";
    public static String VERSION_NAME = "version_name";
    public static String AD_FILE_UPDATE_TIME = "ad_file_update_time";
    public static String AD_PIC_CACHE = "ad_pic_cache";
    public static String AD_IS_SHOW = "ad_is_show";
    public static String AD_PIC_URL = "ad_pic_url";
    /*----------------------标题--------------------*/
    public static final String CHECK_JUDGE = "检测点纠错";
    public static final String ADD_CHECK = "添加机构";
}
