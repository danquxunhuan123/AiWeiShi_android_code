package com.trs.aiweishi.view.ui.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.trs.aiweishi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;

/**
 * Created by Liufan on 2018/9/26.
 */

public class PdfDialogFragment extends DialogFragment implements DownloadFile.Listener {
    public static String TAG = "pdf_dialog";
    private static String URL = "url";
    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter adapter;
    private Unbinder bind;

    @BindView(R.id.ll_pdf)
    LinearLayout pdfLayout;
    @BindView(R.id.progress_pdf)
    ProgressBar progress;

    public static PdfDialogFragment newInstance(String url) {
        PdfDialogFragment dfragment = new PdfDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        dfragment.setArguments(bundle);
        return dfragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
        View view = inflater.inflate(R.layout.pdf_layout, null);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams params = win.getAttributes();
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(params);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);

        remotePDFViewPager = new RemotePDFViewPager(getActivity(), getArguments().getString(URL), this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        remotePDFViewPager.setLayoutParams(params);
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        if (progress == null)
            return;

        progress.setVisibility(View.GONE);
        pdfLayout.removeAllViews();
        adapter = new PDFPagerAdapter(getActivity(), destinationPath);
        remotePDFViewPager.setAdapter(adapter);
        pdfLayout.addView(remotePDFViewPager);
    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onProgressUpdate(int progress, int total) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (adapter != null)
            adapter.close();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
