package com.trs.aiweishi.view.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.SearchBean;
import com.trs.aiweishi.bean.Site;
import com.trs.aiweishi.util.AlbumUtil;
import com.trs.aiweishi.util.PopWindowUtil;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

public class CheckDetailActivity extends BaseActivity {

    @BindView(R.id.ll_content)
    LinearLayout content;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_check_way)
    TextView tvCheckWay;
    @BindView(R.id.tv_check_note)
    TextView tvCheckNote;

    public static String TAG = "bean";
    public static String INTEXTRA = "extra";
    private String lat = "";
    private String lon = "";
    private String title = "";
    private PopWindowUtil instance;

    @Override
    protected String initToolBarName() {
        return "快乐检";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initData() {
        instance = PopWindowUtil.getInstance(this);
        int extra = getIntent().getIntExtra(INTEXTRA, 0);

        if (extra == 0) {
            Site.Monitor bean = (Site.Monitor) getIntent().getSerializableExtra(TAG);
            tvName.setText(bean.getOrgName());
            tvAddress.setText(Html.fromHtml(bean.getOrgAddr()));
            tvPhone.setText(bean.getTel());
            tvCheckWay.setText(bean.getRemark());
            tvCheckNote.setText("暂无");

            lat = bean.getLat();
            lon = bean.getLon();
            title = String.valueOf(Html.fromHtml(bean.getOrgAddr()));
        } else {
            SearchBean.SearchData searBean = getIntent().getParcelableExtra(TAG);
            tvName.setText(searBean.getORGNAME());
            tvAddress.setText(Html.fromHtml(searBean.getORGADDR()));
            tvPhone.setText(searBean.getTEL());
            tvCheckWay.setText("暂无");
            tvCheckNote.setText("暂无");

            lat = searBean.getLAT();
            lon = searBean.getLON();
            title = String.valueOf(Html.fromHtml(searBean.getORGNAME()));
        }

    }

    @Override
    public int initLayout() {
        return R.layout.activity_check_detail;
    }

    @OnClick({R.id.iv_back, R.id.tv_phone, R.id.tv_address})
    public void back(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_address:
                openMap();
//                toLocation();
                break;
            case R.id.tv_phone:
                PhoneUtils.dial(tvPhone.getText().toString());
                break;
        }
    }

    private void toLocation() {
        instance.setContentView(R.layout.pop_location_layout)
                .getView(R.id.tv_baidu, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baiduMap();
                    }
                })
                .getView(R.id.tv_gaode, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .showAtLocation(content);
    }

    public void baiduMap() {
        if (isAvilible(this, "com.baidu.BaiduMap")) {
            Intent i1 = new Intent();
            i1.setData(Uri.parse("baidumap://map/marker?location=" + lat + "," + lon + "&title=" + title));    //&title=Marker&content=makeamarker&traffic=on"
            startActivity(i1);
        } else {
            ToastUtils.showShort("请安装第三方地图");
        }
    }

    public void openMap() {
//        String pkgName = "com.autonavi.minimap";
//        if (isAvilible(this,pkgName)){
//        //高德地图车机版本 使用该包名
//        Uri mUri = Uri.parse("geo:"+lat+","+lon+"?q="+title);
//        Intent intent = new Intent("android.intent.action.VIEW",mUri);
//        startActivity(intent);
//        } else {
//            ToastUtils.showShort("请安装第三方地图");
//        }

        //如果已安装,
        if(isAvilible(this,"com.baidu.BaiduMap")) {//传入指定应用包名
//            ToastUtils.showShort("即将打开百度地图");
            Uri mUri = Uri.parse("geo:"+lat+","+lon+"?q="+title);
            Intent mIntent = new Intent(Intent.ACTION_VIEW,mUri);
            startActivity(mIntent);
        }else if(isAvilible(this,"com.autonavi.minimap")){
//            ToastUtils.showShort("即将打开高德地图");
            Uri mUri = Uri.parse("geo:"+lat+","+lon+"?q="+title);
            Intent intent = new Intent("android.intent.action.VIEW",mUri);
            startActivity(intent);
        }else {
            ToastUtils.showShort("请安装第三方地图方可导航");
        }
    }

    //检查手机上是否安装了指定的软件
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


//    public void googleMap() {
//        if (isAvilible(this, "com.google.android.apps.maps")) {
//            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location[0] + "," + location[1] + ", + Sydney +Australia");
//            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            mapIntent.setPackage("com.google.android.apps.maps");
//            startActivity(mapIntent);
//        } else {
//            Toast.makeText(this, "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();
//
//            Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
//        }
//    }

    @Override
    public void showSuccess(BaseBean baseBean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance.onDestory();
    }
}
