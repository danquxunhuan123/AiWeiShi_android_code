package com.trs.aiweishi.view.ui.activity;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.TimeUtils;
import com.trs.aiweishi.R;
import com.trs.aiweishi.app.AppConstant;
import com.trs.aiweishi.base.BaseActivity;
import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.presenter.impl.UserPresenterImpl;
import com.trs.aiweishi.util.Utils;
import com.trs.aiweishi.util.WebViewUtils;
import com.trs.aiweishi.view.IQuestionView;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class MyQuestionActivity extends BaseActivity implements IQuestionView {
    @Inject
    IUserPresenter userPresenter;
    @BindView(R.id.webview_question)
    WebView webView;

    private String appId = "uojwaffuzcgfurspemg";
    private String username = "移动端¤287534";
    private String extf = "APP_Android";
    private String appKey = "zm1j6g17fw8qxjlsfiwoxrdq1k0vn48l5uw";

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected String initToolBarName() {
        return "我的问卷";
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_my_question;
    }

    @Override
    protected void initData() {
        long ts = System.currentTimeMillis();
        StringBuilder sha1 = new StringBuilder()
                .append(appId)
                .append(appKey)
                .append(username)
                .append(ts);
        final String daiCanYu = "http://www.wjx.cn/partner/login.aspx?appid=";
        StringBuilder builder = new StringBuilder(daiCanYu).append(appId)
                .append("&username=").append(username)
                .append("&ts=").append(ts)
                .append("&sign=").append(Utils.shaEncrypt(sha1.toString()));
        userPresenter.loginQuesiton(builder.toString());
    }

    @Override
    public void loginQuesiton(ResponseBody responseBody) {
        long ts = System.currentTimeMillis();
        StringBuilder sha1 = new StringBuilder()
                .append(appId)
                .append(appKey)
                .append(username)
                .append(spUtils.getString(AppConstant.USER_PHONE))
                .append(spUtils.getString(AppConstant.USER_NAME))
                .append(extf)
                .append(ts);
        final String daiCanYu = "http://www.wjx.cn/partner/qlist.aspx?appid=";
        StringBuilder builder = new StringBuilder(daiCanYu).append(appId)
                .append("&username=").append(username)
                .append("&joiner=").append(spUtils.getString(AppConstant.USER_PHONE))
                .append("&realname=").append(spUtils.getString(AppConstant.USER_NAME))
                .append("&extf=").append(extf)
                .append("&ts=").append(ts)
                .append("&sign=").append(Utils.shaEncrypt(sha1.toString()));

        WebViewUtils.load(webView, new MyWebViewClient(), builder.toString(), 2);
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
