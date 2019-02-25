package com.trs.aiweishi.view.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.CacheUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseDialogFragment;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.view.custview.RingProgressView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Liufan on 2018/7/9.
 */

public class AdDialogFragment extends BaseDialogFragment {
    public static String TAG = "AdDialogFragment";
    //    private SPUtils spUtils;
    private int what_1 = 1;
    private int what_3 = 3;
    private long delayMillis = 5000;
    private Handler handler;
    private int adTime = 5;

    @BindView(R.id.iv_ad)
    ImageView ivAd;
    @BindView(R.id.rpv)
    RingProgressView progressView;

    public static AdDialogFragment newInstance() {
        AdDialogFragment dfragment = new AdDialogFragment();
//        Bundle bundle = new Bundle();
//        dfragment.setArguments(bundle);
        return dfragment;
    }

    protected void initData() {
        Bitmap bitmapCache = CacheUtils.getInstance(getActivity().getExternalCacheDir())
                .getBitmap(AppConstant.AD_PIC_CACHE, null);
        if (bitmapCache == null) {
            handler.sendEmptyMessage(what_1);
        } else {
            showLocalAdPic(bitmapCache);
        }

        //广告倒计时
        progressView.setCurrentProgress(0);
        handler.postDelayed(runnable, 1000);

        setCancelable(false);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (adTime == 0) {
                return;
            }

            adTime--;
            if (progressView != null)
                progressView.setCurrentProgress(5 - adTime);

            handler.postDelayed(runnable, 1000);
        }
    };

    private void showLocalAdPic(Bitmap bitmapCache) {
        GlideUtils.loadBitmapImg(getContext(), bitmapCache, ivAd);
        handler.sendEmptyMessageDelayed(what_1, delayMillis);
    }

    public void setHandle(Handler handle) {
        this.handler = handle;
    }

    public interface OnPicDownloadListener {
        void OnPicDownload(Bitmap resource);
    }

    @Override
    public int getInflater() {
        return R.layout.ad_dialog_layout;
    }


    @OnClick({R.id.iv_ad, R.id.rpv})  //, R.id.btn_cancle_ad
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ad: //点击广告图片
                handler.removeCallbacks(runnable);
                handler.removeMessages(what_1);
                handler.sendEmptyMessage(what_3);
                break;
            case R.id.rpv:  //点击跳过按钮
                handler.removeCallbacks(runnable);
                handler.sendEmptyMessage(what_1);
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        handler.removeCallbacks(runnable);
        handler.removeMessages(what_1);
        handler.removeMessages(what_3);
    }

}
