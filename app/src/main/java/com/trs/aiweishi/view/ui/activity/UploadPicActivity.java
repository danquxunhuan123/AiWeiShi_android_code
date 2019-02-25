package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lf.http.bean.CheckPackegBean;
import com.lf.http.presenter.IUserPresenter;
import com.lf.http.view.IUploadPicView;
import com.maning.mndialoglibrary.MProgressDialog;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.util.AlbumUtil;
import com.trs.aiweishi.util.PopWindowUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.PHOTO_ALBUM;
import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.SHOW_PHOTO;
import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.TAKE_PHOTO;

public class UploadPicActivity extends BaseActivity implements IUploadPicView {

    @Inject
    IUserPresenter presenter;
    @BindView(R.id.ll_contain_pic)
    LinearLayout containPic;

    private PopWindowUtil popWindowUtil;
    private Uri cropUri;
    private Uri imageUriFromCamera;
    private ImageView showPic;
    private String pic = "check_package_image.jpeg";
    private CheckPackegBean.CPackeg packBean;
    private String fileName;
    public static String PACKAGE = "package";

    @Override
    protected String initToolBarName() {
        return "添加机构";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_upload_pic;
    }

    @Override
    protected void initData() {
        packBean = getIntent().getParcelableExtra(PACKAGE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                SizeUtils.dp2px(80), SizeUtils.dp2px(80));
        params.setMargins(0, 0, SizeUtils.dp2px(10), 0);

        showPic = new ImageView(this);
        showPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        showPic.setLayoutParams(params);

        ImageView addPic = new ImageView(this);
        addPic.setImageDrawable(getResources().getDrawable(R.mipmap.icon_add_pic));
        addPic.setScaleType(ImageView.ScaleType.FIT_XY);
        addPic.setLayoutParams(params);

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
        containPic.addView(addPic);
    }

    private void showPopWindow() {
        popWindowUtil = new PopWindowUtil(this);
        popWindowUtil.setContentView(R.layout.pop_bottom_layout)
                .setContent(R.id.tv_select_1, "从相册选择")
                .setContent(R.id.tv_select_2, "相机拍照")
                .getView(R.id.tv_select_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlbumUtil.openAlbum(UploadPicActivity.this, PHOTO_ALBUM, pic);
                    }
                })
                .getView(R.id.tv_select_2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageUriFromCamera = AlbumUtil.takePhone(UploadPicActivity.this, TAKE_PHOTO, pic);
                    }
                })
                .showAtLocation(containPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_ALBUM:
                if (resultCode == RESULT_OK) {
                    imageUriFromCamera = data.getData();
                    cropUri = AlbumUtil.startCrop(this, imageUriFromCamera, SHOW_PHOTO, pic);
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    cropUri = AlbumUtil.startCrop(this, imageUriFromCamera, SHOW_PHOTO, pic);
                }
                break;
            case SHOW_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        popWindowUtil.disMiss();
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(cropUri));

                        showPic.setImageBitmap(bitmap);
                        containPic.removeView(showPic);
                        containPic.addView(showPic, 0);
                        uploadImg();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void uploadImg() {
        presenter.savePackagePhoto(this, AppConstant.SAVE_PACKAGE_PHOTO,
                getExternalCacheDir().getPath() + "/" + pic,
                getExternalCacheDir().getPath());
    }

    @Override
    public void savePackagePhoto(String result) {
        try {
            JSONObject json = new JSONObject(result);
            int code = json.getInt("code");
            if (code == 0) {
                fileName = json.getString("file");
            } else {
                ToastUtils.showShort(json.getInt("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void upload(View view) {
        if (TextUtils.isEmpty(fileName)) {
            ToastUtils.showShort("请添加图片");
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put("packageId", packBean.getBookingId() + "");
        param.put("File", fileName);
        presenter.picCommit(AppConstant.PACKAG_REPORT, param);

        MProgressDialog.showProgress(this, config);
    }

    @Override
    public void picCommit(String result) {
        MProgressDialog.dismissProgress();

        try {
            JSONObject json = new JSONObject(result);
//            int code = json.getInt("code");
            ToastUtils.showShort(json.getString("msg"));
//            if (code == 0)

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
