package com.trs.aiweishi.view.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.config.MDialogConfig;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ChannelAdapter;
import com.trs.aiweishi.adapter.ImgsAdapter;
import com.trs.aiweishi.listener.OnChannelListener;
import com.trs.aiweishi.util.BannerHelper;
import com.trs.aiweishi.util.GlideUtils;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.util.SaveBitmapToLocalUtil;
import com.trs.aiweishi.util.Utils;
import com.trs.aiweishi.view.custview.MyBanner;
import com.trs.aiweishi.view.custview.PinchImageView;
import com.youth.banner.Banner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Liufan on 2018/7/9.
 */

public class ImgsDialogFragment extends DialogFragment {
    private static String param1 = "imgs";
    private static String param2 = "position";

    @BindView(R.id.vp_imgs)
    ViewPager viewPager;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Unbinder bind;
    private int position;

    public static ImgsDialogFragment newInstance(int position, List<String> channels) {
        ImgsDialogFragment dialogFragment = new ImgsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(param2, position);
        bundle.putStringArrayList(param1, (ArrayList<String>) channels);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
//        if (dialog != null) {
//            //添加动画
//            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
//        }
        View view = inflater.inflate(R.layout.img_dialog_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);

        processLogic();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    private void processLogic() {
        position = getArguments().getInt(param2, 0);

        List<String> imgs = getArguments().getStringArrayList(param1);
        List<View> views = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(imgs)) {
            for (int i = 0; i < imgs.size(); i++) {
                final PinchImageView imageView = new PinchImageView(getActivity());
                GlideUtils.loadUrlBitmap(imgs.get(i), imageView, new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                        imageView.setImageBitmap(resource);
//                        SaveBitmapToLocalUtil.saveImageToGallery(getActivity(),resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        if (progressBar != null)
                            progressBar.setVisibility(View.GONE);
                    }
                });
                views.add(imageView);
            }
            ImgsAdapter adapter = new ImgsAdapter(getActivity(), views);
            viewPager.setAdapter(adapter);

            viewPager.setCurrentItem(position);
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        dismiss();
    }

    private DialogInterface.OnDismissListener mOnDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss(dialog);
    }

}
