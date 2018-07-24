package com.trs.aiweishi.view.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ObjectUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.adapter.ChannelAdapter;
import com.trs.aiweishi.listener.OnChannelListener;
import com.trs.aiweishi.util.BannerHelper;
import com.trs.aiweishi.util.RecycleviewUtil;
import com.trs.aiweishi.util.Utils;
import com.trs.aiweishi.view.custview.MyBanner;
import com.youth.banner.Banner;

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

    @BindView(R.id.banner_img)
    Banner banner;

    private Unbinder bind;
    private int position;

    public static ImgsDialogFragment newInstance(int position,List<String> channels) {
        ImgsDialogFragment dialogFragment = new ImgsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(param2,position);
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

        Utils.setParam(banner,16,9);

        List<String> imgs = getArguments().getStringArrayList(param1);
        if (ObjectUtils.isNotEmpty(imgs)) {
            BannerHelper.initBannner(getActivity(), banner, imgs);
            banner.toRealPosition(position);
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
