package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.bean.AdBean;
import com.trs.aiweishi.bean.DetailBean;
import com.trs.aiweishi.bean.ListData;
import com.trs.aiweishi.helper.SuperPlayerHelper;
import com.trs.aiweishi.presenter.IHomePresenter;
import com.trs.aiweishi.util.AndroidBug5497Workaround_1;
import com.trs.aiweishi.util.PopWindowUtil;
import com.trs.aiweishi.util.ReadFromFile;
import com.trs.aiweishi.util.RegularConversionUtil;
import com.trs.aiweishi.util.UMShareUtil;
import com.trs.aiweishi.util.Utils;
import com.trs.aiweishi.util.WebViewUtils;
import com.trs.aiweishi.view.ui.fragment.ChannelDialogFragment;
import com.trs.aiweishi.view.ui.fragment.ImgsDialogFragment;
import com.trs.aiweishi.view.ui.fragment.PdfDialogFragment;
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
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_name)
    TextView toolName;
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.ib_share)
    ImageButton share;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.vs_player)
    ViewStub viewStub;

//    private String playerTitle = "测试视频-720P";
//    private String playerUrl = "http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/68e3febf4564972819220421305/v.f30.mp4";

    public static final String PARCELABLE = "parcelable";
    public static final String TITLE_NAME = "title_name";
    public static final String URL = "url";
    public static final String TYPE = "type";
    private SuperPlayerHelper playerHelper;
    private UMShareUtil shareUtil;
    private PopWindowUtil sharePopUtil;
    private String toolbarName = "";
    private int type;
    private List<String> imgs = new ArrayList<>();
    private String imgUrl;
    private String thumbUrl;
    private String shareUrl;
    private String title;

    @Override
    protected boolean isFitsSystemWindows() {
        if (getIntent().getIntExtra(TYPE, 2) == 3)
            return false;
        else
            return super.isFitsSystemWindows();
    }

    @Override
    protected String initToolBarName() {
        AndroidBug5497Workaround_1.assistActivity(this);
        toolbarName = getIntent().getStringExtra(TITLE_NAME);
        return toolbarName;
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initListener() {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollBarSize(5);
        webView.getSettings().setSupportZoom(true); // 可以缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setNeedInitialFocus(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new JavaScriptInterface(), "imagelistner"); //给图片设置点击监听的
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);//自动加载图片
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    @Override
    protected void initData() {
        shareUtil = UMShareUtil.getInstance().setOnShareSuccessListener(this);
        sharePopUtil = new PopWindowUtil(this);
        String url = getIntent().getStringExtra(URL);
        type = getIntent().getIntExtra(TYPE, 2);

        if (type == 2) {  //新闻详情
            ListData listData = getIntent().getParcelableExtra(PARCELABLE);
            if (listData != null) {
                if ("视频".equals(listData.getArticleType())) {
                    initPlayer(listData.getTitle(), listData.getVideoURL());
                }
            }

            if (url.contains(".json")) {
                //获取ip地址
                String[] split = url.split("/");
                imgUrl = url.substring(0, url.length() - split[split.length - 1].length());
                presenter.getDetailData(url);
            } else {
                WebViewUtils.load(webView, new MyWebViewClient(), url, type);
            }
        } else if (type == 3) {  //小思问答
            share.setVisibility(View.GONE);
            toolName.setTextColor(Color.WHITE);
            toolbar.setBackground(getResources().getDrawable(R.mipmap.bg_xiaosi));
            ViewGroup.LayoutParams params = toolbar.getLayoutParams();
            params.height = BarUtils.getStatusBarHeight() + BarUtils.getActionBarHeight(this);
            toolbar.setLayoutParams(params);
            toolbar.setPadding(0, BarUtils.getStatusBarHeight() + 10, 0, 10);
            WebViewUtils.load(webView, new MyWebViewClient(), url, type);
        } else if (type == -1) {//广告详情
            AdBean adBean = getIntent().getParcelableExtra(PARCELABLE);
            share.setVisibility(View.VISIBLE);
            title = adBean.getAdTitle();
            url = adBean.getAdUrl();
            thumbUrl = adBean.getImageUrl();
            shareUrl = adBean.getAdUrl();
            WebViewUtils.load(webView, new MyWebViewClient(), url, type);
        }

        MProgressDialog.showProgress(this, config);
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
        ListData detailBean = ((DetailBean) baseBean).getDatas();
        loadUrl(detailBean);
    }

    private void initPlayer(String palyerTitle, String playerUrl) {
        toolbar.setVisibility(View.GONE);
        SuperPlayerView playerView = viewStub.inflate().findViewById(R.id.spv);
        playerView.setPlayerViewCallback(new SuperPlayerView.PlayerViewCallback() {
            @Override
            public void hideViews() {

            }

            @Override
            public void showViews() {

            }

            @Override
            public void onQuit(int i) {
                finish();
            }
        });
        playerHelper = new SuperPlayerHelper(playerView);
        playerHelper.playWithMode(palyerTitle, playerUrl);
    }

    public void share(View view) {
        sharePopUtil.setContentView(R.layout.share_layout)
                .getView(R.id.tv_qq, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.QQ,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_zone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.QZONE,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_wecheat, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.WEIXIN,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_circle_friend, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_sina, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity.this, SHARE_MEDIA.SINA,
                                title, title, thumbUrl, shareUrl);
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
        String updatedate = listData.getTime().split(" ")[0];
        String body = listData.getBody();

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
            str = str.replaceAll("#SOURCE#", source);
            str = str.replace("#SHUXIAN#", "来源:");
        } else {
            str = str.replace("#SHUXIAN#", "");
            str = str.replaceAll("#SOURCE#", "");
        }
        if (!TextUtils.isEmpty(updatedate))
            str = str.replaceAll("#TIME#", updatedate);
        else
            str = str.replaceAll("#TIME#", "");

        // webview加载html标签
        webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);

        MProgressDialog.dismissProgress();
    }


    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (type == -1) {
                view.loadUrl(url);
                return true;
            }

            if (url.endsWith("pdf")) {
                loadPdf(url);
            } else {
                Intent intent = new Intent(DetailActivity.this, DetailActivity_1.class);
                intent.putExtra(DetailActivity.TITLE_NAME, toolbarName);
                intent.putExtra(DetailActivity.URL, url);
                startActivity(intent);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            addImageClickListner(view);
            MProgressDialog.dismissProgress();
        }
    }

    private void loadPdf(String url) {
        PdfDialogFragment.newInstance(url)
                .show(getSupportFragmentManager(), PdfDialogFragment.TAG);
    }

    private void addImageClickListner(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i < objs.length; i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    private class JavaScriptInterface {
        public JavaScriptInterface() {
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            if (ObjectUtils.isNotEmpty(imgs)) {
                ImgsDialogFragment dialogFragment = ImgsDialogFragment.newInstance(imgs.indexOf(img), imgs);
                dialogFragment.show(getSupportFragmentManager(), ChannelDialogFragment.TAG);
//                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                    }
//                });
            }
        }
    }

    @Override
    public void onResume() {
        webView.onResume();
        if (playerHelper != null) {
            playerHelper.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        webView.onPause();
        if (playerHelper != null) {
            playerHelper.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        shareUtil.onDestory();

        if (playerHelper != null) {
            playerHelper.release();
        }
        super.onDestroy();
    }
}
