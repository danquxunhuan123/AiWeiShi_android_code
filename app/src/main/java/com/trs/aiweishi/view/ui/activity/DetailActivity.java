package com.trs.aiweishi.view.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.config.MDialogConfig;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.DetailBean;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.PopWindowUtil;
import com.trs.aiweishi.util.ReadFromFile;
import com.trs.aiweishi.util.RegularConversionUtil;
import com.trs.aiweishi.util.UMShareUtil;
import com.trs.aiweishi.util.Utils;
import com.trs.aiweishi.util.WebViewUtils;
import com.trs.aiweishi.view.ui.fragment.ChannelDialogFragment;
import com.trs.aiweishi.view.ui.fragment.ImgsDialogFragment;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements UMShareUtil.OnShareSuccessListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.ib_share)
    ImageButton share;
    @BindView(R.id.webview)
    WebView webView;

    public static final String URL = "url";
    public static final String TYPE = "type";
    private MDialogConfig config;
    private UMShareUtil shareUtil;
    private PopWindowUtil sharePopUtil;
    private int type;
    private List<String> imgs;
    private String imgUrl;
    private String thumbUrl;
    private String shareUrl;
    private String title;
    private String body;

    @Override
    protected String initToolBarName() {
        return "";
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initListener() {
        webView.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollBarSize(5);
        webView.getSettings().setSupportZoom(true); // 可以缩放
        webView.getSettings().setNeedInitialFocus(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner"); //给图片设置点击监听的
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);//自动加载图片
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    @Override
    protected void initData() {
        shareUtil = UMShareUtil.getInstance()
                .setOnShareSuccessListener(this);
        sharePopUtil = PopWindowUtil.getInstance(this);

        String url = getIntent().getStringExtra(URL);
        type = getIntent().getIntExtra(TYPE, 2);

        //获取ip地址
        String[] split = url.split("/");
        imgUrl = url.substring(0, url.length() - split[split.length - 1].length());

        config = new MDialogConfig.Builder()
                .isCanceledOnTouchOutside(true)
                .build();
        MProgressDialog.showProgress(this, config);

        if (type == 2){
            presenter.getDetailData(url);
        }
        else{
            share.setVisibility(View.GONE);
            WebViewUtils.load(webView, new MyWebViewClient(), url, type);
        }
    }

    @Override
    public int initLayout() {
        return R.layout.activity_detail;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        MProgressDialog.dismissProgress();
//        if (type == 2)
//            webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);;
//        else
        loadUrl(((DetailBean) baseBean).getDatas());
    }

    public void share(View view) {
        sharePopUtil.setContentView(R.layout.share_layout)
                .getView(R.id.tv_qq, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.QQ,
                                title, body, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_zone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.QZONE,
                                title, body, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_wecheat, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.WEIXIN,
                                title, body, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_circle_friend, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE,
                                title, body, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_sina, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.SINA,
                                title, body, thumbUrl, shareUrl);
                    }
                })
                .showAtLocation(webView);
    }

    @Override
    public void onShareSuccess(SHARE_MEDIA platform) {
        sharePopUtil.disMiss();
        ToastUtils.showShort(getResources().getString(R.string.share_success));
    }

    @Override
    public void onShareError(SHARE_MEDIA platform, Throwable t) {
        sharePopUtil.disMiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void loadUrl(ListData listData) {
        shareUrl = listData.getShareurl();
        title = listData.getTitle();
        String source = listData.getSource();
        String updatedate = listData.getTime();
        body = listData.getBody();

        body.replaceAll(".jpg/", ".jpg");

        imgs = new ArrayList<>();
        List<String> imgSrcList = Utils.getImgSrcList(body);
        for (int a = 0; a < imgSrcList.size(); a++) {
            String img = imgSrcList.get(a);
            String url = imgUrl + img.substring(1);
            imgs.add(url);
            body = body.replace(img, url);

            if (a == 0)
                thumbUrl = url;
        }

        body = RegularConversionUtil.removeHtmlTag(body);
        String str = ReadFromFile.getFromAssets(this, "xhwDetailedView.html");

        if (!TextUtils.isEmpty(body)) {
            str = str.replace("#CONTENT#", body);
        } else {
            str = str.replace("#CONTENT#", "");
        }
        if (!TextUtils.isEmpty(title)) {
            str = str.replaceAll("#TITLE#", title.replace("&amp;nbsp;", " "));
        } else {
            str = str.replaceAll("#TITLE#", "");
        }
        if (!TextUtils.isEmpty(source)) {
            if (!TextUtils.isEmpty(updatedate)) {
                str = str.replaceAll("#SOURCE#", source);
                str = str.replace("#SHUXIAN#", " | ");
                str = str.replaceAll("#TIME#", "  " + updatedate);
            } else {
                str = str.replaceAll("#SOURCE#", source);
                str = str.replace("#SHUXIAN#", "");
                str = str.replaceAll("#TIME#", "");
            }
        } else {
            if (!TextUtils.isEmpty(updatedate)) {
                str = str.replace("#SHUXIAN#", "|");
                str = str.replaceAll("#TIME#", updatedate);
            } else {
                str = str.replace("#SHUXIAN#", "");
                str = str.replaceAll("#TIME#", "");
            }
            str = str.replaceAll("#SOURCE#", "");
        }
        // webview加载html标签
        webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            addImageClickListner(view);
            MProgressDialog.dismissProgress();
        }
    }

    private void addImageClickListner(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(i,this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    private class JavaScriptInterface {
        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(int position, String img) {
            if (ObjectUtils.isNotEmpty(imgs)) {
                ImgsDialogFragment dialogFragment = ImgsDialogFragment.newInstance(position, imgs);
                dialogFragment.show(getSupportFragmentManager(), ChannelDialogFragment.TAG);
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
            }
        }
    }
}
