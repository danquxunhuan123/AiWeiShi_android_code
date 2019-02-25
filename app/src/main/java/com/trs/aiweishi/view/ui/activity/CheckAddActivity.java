package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.brocast.LocationReceiver;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.AlbumUtil;
import com.lf.http.view.ICheckView;
import com.trs.aiweishi.view.custview.MyLinearlayoutView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.PHOTO_ALBUM;
import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.SHOW_PHOTO;
import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.TAKE_PHOTO;

public class CheckAddActivity extends BaseActivity implements ICheckView {

    public static final String LOCATION_DATA = "location_data";
    @Inject
    IHomePresenter presenter;
    @BindView(R.id.mllv)
    MyLinearlayoutView myLinearlayoutView;
    @BindView(R.id.bmapView)
    MapView mapView;
    @BindView(R.id.edit_jgmc)
    EditText editName;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.tv_address)
    TextView tvAdress;

    private CheckActivity activity;
    private BDLocation locationData;
    private String pic = "check_add_image.jpeg";
    private Uri cropUri;
    private LocationReceiver locationReceiver;
//    private String fileName;


    @Override
    protected String initToolBarName() {
        return AppConstant.ADD_CHECK;
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_check_add;
    }

    @Override
    protected void initData() {
        activity = (CheckActivity) ActivityUtils.getActivityList().get(1);
        registReceiver();
        mapLocation();
        myLinearlayoutView.setPicPath(pic);
    }

    private void registReceiver() {
        locationReceiver = new LocationReceiver();
        IntentFilter f = new IntentFilter();
        f.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        registerReceiver(locationReceiver, f);
    }

    private void mapLocation() {
        locationData = activity.getLocation();
        if (locationData != null && locationData.getRadius() != 0.0) {
            mapView.showZoomControls(false);
            BaiduMap mBaiduMap = mapView.getMap();// 开启定位图层
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            mBaiduMap.setMyLocationEnabled(true);

            MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus())
//                .rotate(rotateAngle)
                    .zoom(16)
//                .overlook(overlookAngle)
                    .build();
            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
            mBaiduMap.animateMapStatus(u);

// 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(locationData.getRadius())
                    .direction(100)// 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(locationData.getLatitude())
                    .longitude(locationData.getLongitude())
                    .build();

// 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
            MyLocationConfiguration config = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.FOLLOWING,
                    true,
                    null);
            mBaiduMap.setMyLocationConfiguration(config);
        } else {
            ToastUtils.showShort("请检查定位是否开启");
        }
    }

    @OnClick(R.id.tv_address)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                locationData = activity.getLocation();
                if (locationData != null && !TextUtils.isEmpty(locationData.getAddrStr()))
                    tvAdress.setText(locationData.getAddrStr());
                else
                    ToastUtils.showShort("请检查定位是否开启");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_ALBUM:
                if (resultCode == RESULT_OK) {
                    Uri imageUriFromAlbum = data.getData();
                    cropUri = AlbumUtil.startCrop(this, imageUriFromAlbum, SHOW_PHOTO, pic);
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    cropUri = AlbumUtil.startCrop(this, myLinearlayoutView.getImageUriFromCamera(), SHOW_PHOTO, pic);
                }
                break;
            case SHOW_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        myLinearlayoutView.popDismiss();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(cropUri));

                        myLinearlayoutView.addImage(bitmap);
//                        uploadImg();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

//    public void uploadImg() {
//        File file = new File(getExternalCacheDir().getPath() + "/" + pic);
//        file = new CompressHelper.Builder(this)
//                .setFileName("compress_user_head")
//                .setCompressFormat(Bitmap.CompressFormat.JPEG)
//                .setDestinationDirectoryPath(getExternalCacheDir().getPath())
//                .build()
//                .compressToFile(file);
//
//        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//
//        presenter.savePackagePhoto(AppConstant.SAVE_PACKAGE_PHOTO, part);
//    }

//    @Override
//    public void savePackagePhoto(String result) {
//        try {
//            JSONObject json = new JSONObject(result);
//            int code = json.getInt("code");
//            if (code == 0) {
//                fileName = json.getString("file");
//            } else {
//                ToastUtils.showShort(json.getInt("msg"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public void commitCheck(View view) {
        try {
            locationData = activity.getLocation();
            String province = "";
            if (locationData != null && !TextUtils.isEmpty(locationData.getProvince()))
                province = locationData.getProvince();
            else {
                ToastUtils.showShort("请添加地址");
                return;
            }

            String orgName = editName.getText().toString().trim();
            if (TextUtils.isEmpty(orgName)) {
                ToastUtils.showShort("请填写机构名称");
                return;
            }

            String orgAddress = tvAdress.getText().toString().trim();

            Map<String, String> param = new HashMap<>();
            param.put("orgName", Base64.encodeToString(orgName.getBytes("UTF-8"), Base64.NO_WRAP));

            param.put("province", Base64.encodeToString(province.getBytes("UTF-8"), Base64.NO_WRAP)); //

            param.put("orgAddr", Base64.encodeToString(orgAddress.getBytes("UTF-8"), Base64.NO_WRAP));
            param.put("orgId", Base64.encodeToString("0".getBytes("UTF-8"), Base64.NO_WRAP));

            String phone = editPhone.getText().toString().trim();
            if (!TextUtils.isEmpty(phone))
                param.put("tel", phone);

            presenter.addMonitoringSite(param);

            MProgressDialog.showProgress(this, config);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkSuccess(String result) {
        try {
            JSONObject json = new JSONObject(result);
            String desc = json.getString("desc");
            if (json.getInt("code") == 0) {
                ToastUtils.showShort(getResources().getString(R.string.check_judge_success));
                finish();
            } else {
                ToastUtils.showShort(desc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MProgressDialog.dismissProgress();
    }

    //    @Override
//    public void picCommit(String result) {
//        MProgressDialog.dismissProgress();
//
//        try {
//            JSONObject json = new JSONObject(result);
////            int code = json.getInt("code");
//            ToastUtils.showShort(json.getString("msg"));
////            if (code == 0)
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        unregisterReceiver(locationReceiver);
        super.onDestroy();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
