package com.trs.aiweishi.view.custview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.util.AlbumUtil;
import com.trs.aiweishi.util.PopWindowUtil;

import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.PHOTO_ALBUM;
import static com.trs.aiweishi.view.ui.activity.UserConfigActivity.TAKE_PHOTO;

public class MyLinearlayoutView extends LinearLayout {
    private Context context;
    private String picPath;
    private PopWindowUtil popWindowUtil;
    private Uri imageUriFromCamera;
    private LinearLayout.LayoutParams params;
    private int maxCount = 1;

    public MyLinearlayoutView(Context context) {
        this(context, null);
    }

    public MyLinearlayoutView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearlayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        // 得到自定义资源数组
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingProgressView);
//        ringColor = typedArray.getColor(R.styleable.RingProgressView_ringColor, ringColor);
//        ringProgressColor = typedArray.getColor(R.styleable.RingProgressView_ringProgressColor, ringProgressColor);
//        ringWidth = (int) typedArray.getDimension(R.styleable.RingProgressView_ringWidth, dip2px(10));
//        textSize = (int) typedArray.getDimension(R.styleable.RingProgressView_textSize, dip2px(20));
//        textColor = typedArray.getColor(R.styleable.RingProgressView_textColor, textColor);
//        currentProgress = typedArray.getInt(R.styleable.RingProgressView_currentProgress, currentProgress);
//        maxProgress = typedArray.getColor(R.styleable.RingProgressView_maxProgress, maxProgress);
//        typedArray.recycle();
        initData();
    }

    private void initData() {
        setOrientation(HORIZONTAL);
        params = new LinearLayout.LayoutParams(
                SizeUtils.dp2px(80), SizeUtils.dp2px(80));
        params.setMargins(0, 0, SizeUtils.dp2px(10), 0);

        ImageView addPic = new ImageView(context);
        addPic.setImageDrawable(getResources().getDrawable(R.mipmap.icon_add_pic));
        addPic.setScaleType(ImageView.ScaleType.FIT_XY);
        addPic.setLayoutParams(params);

        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
        addView(addPic);
    }

    private void showPopWindow() {
        popWindowUtil = new PopWindowUtil(context);
        popWindowUtil.setContentView(R.layout.pop_bottom_layout)
                .setContent(R.id.tv_select_1, "从相册选择")
                .setContent(R.id.tv_select_2, "相机拍照")
                .getView(R.id.tv_select_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlbumUtil.openAlbum(context, PHOTO_ALBUM, picPath);
                    }
                })
                .getView(R.id.tv_select_2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageUriFromCamera = AlbumUtil.takePhone(context, TAKE_PHOTO, picPath);
                    }
                })
                .showAtLocation(this);
    }

    public void addImage(Bitmap bitmap) {
        ImageView showPic = new ImageView(context);
        showPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        showPic.setImageBitmap(bitmap);
        showPic.setLayoutParams(params);
        addView(showPic, 0);

        if (getChildCount() == maxCount + 1){
            removeViewAt(getChildCount() - 1);
        }
    }

//    public void setMaxCount(int maxCount) {
//        this.maxCount = maxCount;
//    }

    public void popDismiss() {
        popWindowUtil.disMiss();
    }

    public void setPicPath(String path) {
        this.picPath = path;
    }

    public Uri getImageUriFromCamera() {
        return imageUriFromCamera;
    }
}
