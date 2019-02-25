package com.trs.aiweishi.view.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.lf.http.bean.BaseBean;
import com.lf.http.bean.LoginBean;
import com.lf.http.bean.UserBean;
import com.lf.http.bean.UserData;
import com.lf.http.presenter.IUserPresenter;
import com.lf.http.utils.GsonParse;
import com.lf.http.view.IUserEditerView;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.bean.JsonBean;
import com.trs.aiweishi.listener.MyUMAuthListener;
import com.trs.aiweishi.util.AlbumUtil;
import com.trs.aiweishi.util.AlertDialogUtil;
import com.trs.aiweishi.util.GetJsonDataUtil;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.util.PopWindowUtil;
import com.trs.aiweishi.util.Utils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class UserConfigActivity extends BaseActivity implements IUserEditerView, MyUMAuthListener.OnLoginSuccessListener {

    @Inject
    MyUMAuthListener umAuthListener;
    @Inject
    IUserPresenter presenter;
    @BindView(R.id.ll_content)
    LinearLayout content;
    @BindView(R.id.iv_image_head)
    ImageView head;
    @BindView(R.id.tv_nick_name)
    TextView nickName;
    @BindView(R.id.tv_birthday)
    TextView birthday;
    @BindView(R.id.tv_change_address)
    TextView address;
    @BindView(R.id.tv_detail_address)
    TextView detailAddress;
    @BindView(R.id.iv_back)
    ImageView back;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private TimePickerView pvTime;
    private UserData.User user;

    private final String userHeadName = "image.jpeg";
    private Uri cropUri;
    private Uri imageUriFromCamera;
    private Thread thread;
    private final String EDIT_HEAD_AGAIN = "edit_head_again";
    private final String LOGOUT_AGAIN = "logout_again";
    private String againLogin = "";
    private boolean isLoaded = false;
    private boolean isChanged = false;  //是否修改过信息
    //    private int currOptions1 = 0, currOptions2 = 0, currOptions3 = 0;
    public static String USER = "user";
    public static final int TAKE_PHOTO = 1;
    public static final int SHOW_PHOTO = 2;
    public static final int PHOTO_ALBUM = 3;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    public static final int MSG_LOAD_FAILED = 0x0003;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    @Override
    protected String initToolBarName() {
        return "个人资料";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        user = getIntent().getParcelableExtra(USER);

        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        umAuthListener.setOnThirdSucListener(this);

        if (spUtils.getInt(AppConstant.AUTH_SITE) != 0) { //三方登录
            GlideUtils.loadCircleUrlImg(this, user.getHeadUrl(), head);
        } else {
            GlideUtils.loadCircleUrlImg(this, AppConstant.HEAD_URL + user.getHeadUrl(), head);
        }

        nickName.setText(user.getNickName());
        birthday.setText(user.getBirthday());
        address.setText(user.getProvince());
        detailAddress.setText(user.getCity());

        initTimePicker();
    }

    @Override
    public int initLayout() {
        return R.layout.config_layout;
    }

    @OnClick({R.id.rl_changeHead, R.id.rl_changeNickname, R.id.tv_change_psd, R.id.rl_changeBirthday
            , R.id.rl_change_address, R.id.rl_detail_address, R.id.iv_back, R.id.but_logout
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_changeHead:
                showPopWindow();
                break;
            case R.id.rl_changeNickname:
                showDialog(nickName);
                break;
            case R.id.tv_change_psd:
                Intent in = new Intent(this, ChangePsdActivity.class);
                startActivity(in);
                break;
            case R.id.rl_changeBirthday:
                pvTime.show(birthday);
                break;
            case R.id.rl_change_address:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.parse_location_desc), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rl_detail_address:
                showDialog(detailAddress);
                break;
            case R.id.iv_back:
                if (isChanged)
                    saveEdit();
                else
                    finish();
                break;
            case R.id.but_logout:
                refreshSession();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (isChanged) {
                saveEdit();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void editHeadSuccess(String res) {
        try {
            JSONObject obj = new JSONObject(res);
            if (obj.getBoolean("result")) {
                saveInfo();
            } else {
                againLogin = EDIT_HEAD_AGAIN;
                login();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveInfo() {
        Map<String, String> param = new HashMap();
        param.put("manageServiceTag", "managerUpdateUser");
        param.put("userName", Base64.encodeToString(spUtils.getString(AppConstant.USER_PHONE).getBytes(), Base64.DEFAULT));
        param.put("nickName", Base64.encodeToString(nickName.getText().toString().trim().getBytes(), Base64.DEFAULT));
        param.put("birthday", Base64.encodeToString(birthday.getText().toString().trim().getBytes(), Base64.DEFAULT));
        param.put("province", Base64.encodeToString(address.getText().toString().trim().getBytes(), Base64.DEFAULT));
        param.put("city", Base64.encodeToString(detailAddress.getText().toString().trim().getBytes(), Base64.DEFAULT));
        presenter.saveEdit(param);
    }

    @Override
    public void saveInfoSuccess(BaseBean obj) {
        if (obj.getCode() == 0) {
            ToastUtils.showShort("修改信息成功");
        } else {
            ToastUtils.showShort(obj.getDesc());
        }
        back.setClickable(true);
        finish();
    }

    @Override
    public void refSesSuccess(BaseBean bean) {
        if (bean.getCode() == 404) {
            againLogin = LOGOUT_AGAIN;
            login();
        } else if (bean.getCode() == 200) {
            logout();
        }
    }

    @Override
    public void loginSuccess(BaseBean baseBean) {
        UserBean bean = (UserBean) baseBean;
        if (bean.getCode() == 200) {
            spUtils.put(AppConstant.SESSION_ID, bean.getData().getCoSessionId());

            //上传头像失败,重新登录上传
            if (EDIT_HEAD_AGAIN.equals(againLogin))
                saveEdit();

            if (LOGOUT_AGAIN.equals(againLogin))
                logout();
        }
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        LoginBean bean = (LoginBean) baseBean;
        if (bean.getCode() == 200) {
            spUtils.put(AppConstant.IS_LOGIN, false, true);
            setResult(0, null);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_ALBUM:
                if (resultCode == RESULT_OK) {
                    imageUriFromCamera = data.getData();
                    cropUri = AlbumUtil.startCrop(this, imageUriFromCamera, SHOW_PHOTO, userHeadName);
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    cropUri = AlbumUtil.startCrop(this, imageUriFromCamera, SHOW_PHOTO, userHeadName);
                }
                break;
            case SHOW_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        isChanged = true;
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(cropUri));
                        GlideUtils.loadCircleBitmapImg(this, bitmap, head);
                        spUtils.put(AppConstant.USER_PIC, cropUri.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showError(Throwable e) {
        super.showError(e);
        back.setClickable(true);
    }

    /**
     * 登录
     */
    public void login() {
        Map<String, String> params = new HashMap<>();
        params.put("Version", "v5.0");
        params.put("coAppName", "IWSApp");
        String psdStr = "loginType=mobile;loginKey=" + spUtils.getString(AppConstant.USER_PHONE);
        params.put("userName", psdStr);
        params.put("password", spUtils.getString(AppConstant.UER_PSD));
        params.put("coSessionId", Base64.encodeToString(Utils.getRandomString(5).getBytes(), Base64.DEFAULT));
        presenter.login(1, params);
    }

    /**
     * 退出
     */
    private void logout() {
        Map<String, String> param = new HashMap();
        param.put("coAppName", AppConstant.APP_NAME);
        param.put("coSessionId", spUtils.getString(AppConstant.SESSION_ID));
        presenter.logout(param);
    }

    /**
     * 保存信息
     */
    private void saveEdit() {
        back.setClickable(false);
        try {
            String path = getExternalCacheDir().getPath();
            String pathName = path + "/" + userHeadName;
            String userPhone = spUtils.getString(AppConstant.USER_PHONE);
            String sessionId = spUtils.getString(AppConstant.SESSION_ID);
            presenter.editHeadImg(this,path,pathName, userPhone,sessionId);
        } catch (Exception e) {
            saveInfo();
        }
    }

    /**
     * 刷新session
     */
    private void refreshSession() {
        if (spUtils.getInt(AppConstant.AUTH_SITE) == 1) {
            UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.QQ, umAuthListener);
        } else if (spUtils.getInt(AppConstant.AUTH_SITE) == 2) {
            UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, umAuthListener);
        } else if (spUtils.getInt(AppConstant.AUTH_SITE) == 3) {
            UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.SINA, umAuthListener);
        } else {
            Map<String, String> param = new HashMap();
            param.put("coAppName", AppConstant.APP_NAME);
            param.put("coSessionId", spUtils.getString(AppConstant.SESSION_ID));
            presenter.refreshSession(param);
        }
    }

    @Override
    public void OnThirdSeccess(Map<String, String> data) {
        logout();
    }

    private void showDialog(final TextView textView) {
        final AlertDialogUtil dialogUtil = new AlertDialogUtil(this);
        dialogUtil.setContentView(R.layout.editer_dialog_layout)
                .setViewText(R.id.dialog_content, textView.getText().toString().trim())
                .setViewClickListener(R.id.dialog_btn_sure, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText viewText = (EditText) dialogUtil.getViewText(R.id.dialog_content);
                        String str = viewText.getText().toString().trim();
                        if (!TextUtils.isEmpty(str)) {
                            isChanged = true;
                            textView.setText(str);
                        }
                        dialogUtil.dismiss();
                    }
                })
                .setViewClickListener(R.id.dialog_btn_cancel
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogUtil.dismiss();
                            }
                        })
                .create();
    }

    private void showPopWindow() {
        new PopWindowUtil(this)
                .setContentView(R.layout.pop_bottom_layout)
                .setContent(R.id.tv_select_1, "从相册选择")
                .setContent(R.id.tv_select_2, "相机拍照")
                .getView(R.id.tv_select_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlbumUtil.openAlbum(UserConfigActivity.this, PHOTO_ALBUM, userHeadName);
                    }
                })
                .getView(R.id.tv_select_2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageUriFromCamera = AlbumUtil.takePhone(UserConfigActivity.this, TAKE_PHOTO, userHeadName);
                    }
                })
                .showAtLocation(content);
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                currOptions1 = options1;
//                currOptions2 = options2;
//                currOptions3 = options3;
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + "," +
                        options2Items.get(options1).get(options2) + "," +
                        options3Items.get(options1).get(options2).get(options3);

                address.setText(tx);

                isChanged = true;
            }
        })

//                .setSelectOptions(currOptions1, currOptions2, currOptions3)
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private void initJsonData() {//解析数据
        parseJson();
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public void parseJson() {
        String JsonData = new GetJsonDataUtil().getJson(this, "province_new.json");

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    private ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = GsonParse.parse(data.optJSONObject(i).toString(), JsonBean.class);
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(CheckActivity.MSG_LOAD_FAILED);
        }
        return detail;
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 23);

//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，
                // 如果show的时候没有添加参数，v则为null

                isChanged = true;
                TextView birth = (TextView) v;
                birth.setText(getTime(date));
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
//                .setType(new boolean[]{true, true, true, false, false, false})
//                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
//                .setDecorView(mFrameLayout)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(getResources().getColor(R.color.color_white_trans))  //0x00000000
                .setOutSideCancelable(true)
                .build();

        pvTime.setKeyBackCancelable(true);//系统返回键监听屏蔽掉
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  // HH:mm:ss
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
