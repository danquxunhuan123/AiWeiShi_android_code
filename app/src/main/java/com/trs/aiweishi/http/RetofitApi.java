package com.trs.aiweishi.http;


import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.DetailBean;
import com.trs.aiweishi.bean.HomeBean;
import com.trs.aiweishi.bean.ListDataBean;
import com.trs.aiweishi.bean.LoginBean;
import com.trs.aiweishi.bean.RegistBean;
import com.trs.aiweishi.bean.SearchBean;
import com.trs.aiweishi.bean.SiteBean;
import com.trs.aiweishi.bean.UpdateBean;
import com.trs.aiweishi.bean.UserBean;
import com.trs.aiweishi.bean.UserData;
import com.trs.aiweishi.bean.WorkTimeBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
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

    @Multipart
    @POST()
    Observable<BaseBean> editHeadImg(@Url String url,
                                     @Part() MultipartBody.Part part);

    @GET()
    Observable<ResponseBody> loginQuesiton(@Url String url);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> submitBooking(@Url String url, @FieldMap Map<String, String> param);

}


