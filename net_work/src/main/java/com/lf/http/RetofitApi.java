package com.lf.http;


import com.lf.http.bean.AdBean;
import com.lf.http.bean.BaseBean;
import com.lf.http.bean.CheckPackegBean;
import com.lf.http.bean.CheckResult;
import com.lf.http.bean.DetailBean;
import com.lf.http.bean.HomeBean;
import com.lf.http.bean.ListDataBean;
import com.lf.http.bean.LoginBean;
import com.lf.http.bean.RegistBean;
import com.lf.http.bean.SearchBean;
import com.lf.http.bean.SiteBean;
import com.lf.http.bean.UpdateBean;
import com.lf.http.bean.UserBean;
import com.lf.http.bean.UserData;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Liufan on 2018/5/16.
 */
public interface RetofitApi {

    @GET
    Observable<UpdateBean> update(@Url String url);

    @GET
    Observable<HomeBean> getHomeData(@Url String url);

    @GET
    Observable<ListDataBean> getChannelData(@Url String url);

    @GET
    Observable<DetailBean> getDetail(@Url String url);

    //搜索
    @GET
    Observable<SearchBean> getSearchData(@Url String url,
                                         @QueryMap Map<String, String> params);

    //检索
    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=listMonitoringSitesByFilter")
    Observable<SiteBean> getLocationData(@FieldMap Map<String, String> params);  //SiteBean

    //登录
    @FormUrlEncoded
    @POST("service?idsServiceType=httpssoservice&serviceName=loginByUP ")
    Observable<UserBean> login(@FieldMap Map<String, String> param);

    //注册
    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=userRegister")
    Observable<RegistBean> regist(@FieldMap Map<String, String> param);

    //验证码
    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=sendVerifyCode")
    Observable<RegistBean> getCode(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=activateUserAttr")
    Observable<BaseBean> activateUserAttr(@FieldMap Map<String, String> params);

    //退出
    @FormUrlEncoded
    @POST("service?idsServiceType=httpssoservice&serviceName=logout")
    Observable<LoginBean> getLogout(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=userManageService")
    Observable<BaseBean> saveEdit(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=userQueryForManage")
    Observable<UserData> getUserInfo(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=pwdReset")
    Observable<BaseBean> changePsd(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=resetPwdv5")
    Observable<BaseBean> findPsd(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("service?idsServiceType=httpssoservice&serviceName=refreshSession")
    Observable<BaseBean> refreshSession(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType= httpssoservice&serviceName=loginByUID")
    Observable<UserBean> loginByUID(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=addAccountMapping")
    Observable<BaseBean> addAccountMapping(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=checkAccountMapping")
    Observable<BaseBean> checkAccountMapping(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=addFeedBack")
    Observable<BaseBean> addFeedBack(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=addMonitoringSite")
    Observable<ResponseBody> addMonitoringSite(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("service?idsServiceType=remoteapi&method=addCorrection")
    Observable<ResponseBody> addCorrection(@FieldMap Map<String, String> param);

    @Multipart
    @POST()
    Observable<ResponseBody> editHeadImg(@Url String url,
                                         @Part() MultipartBody.Part part);

    @GET()
    Observable<ResponseBody> loginQuesiton(@Url String url);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> submitBooking(@Url String url, @FieldMap Map<String, String> param);

    @GET()
    Observable<AdBean> getAdData(@Url String adUrl);

    @FormUrlEncoded
    @POST()
    Observable<CheckResult> getCheckInfo(@Url String url, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST()
    Observable<CheckPackegBean> getCheckHistory(@Url String url, @FieldMap Map<String, String> param);

    @Multipart
    @POST()
    Observable<ResponseBody> savePackagePhoto(@Url String url, @Part() MultipartBody.Part part);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> requestCommend(@Url String url, @FieldMap Map<String, String> param);

}


