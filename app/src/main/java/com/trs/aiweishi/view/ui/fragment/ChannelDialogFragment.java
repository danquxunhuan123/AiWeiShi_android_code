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
import com.trs.aiweishi.util.RecycleviewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Liufan on 2018/7/9.
 */

public class ChannelDialogFragment extends DialogFragment implements ChannelAdapter.OnChannelClickListener {
    public static String TAG = "channel";
    private static String param1 = "titles";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Unbinder bind;

    public static ChannelDialogFragment newInstance(List<String> channels) {
        ChannelDialogFragment dialogFragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
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
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        View view = inflater.inflate(R.layout.activity_channel, null);
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
        List<String> titles = getArguments().getStringArrayList(param1);

        if (ObjectUtils.isNotEmpty(titles)){
            ChannelAdapter adapter = new ChannelAdapter(titles,getActivity());
            adapter.setOnChannelListener(this);
            RecycleviewUtil.initGridNoTypeRecycleView(recyclerView,adapter,getActivity(),4);
        }

    }

    @OnClick(R.id.icon_collapse)
    public void onClick(View v) {
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

    private OnChannelListener mOnChannelListener;

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        mOnChannelListener = onChannelListener;
    }

    @OnClick(R.id.icon_collapse)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void OnChanelClick(int position) {
        dismiss();
        mOnChannelListener.onItemClick(position);
    }
}
