package com.trs.aiweishi.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Liufan on 2018/3/22.
 */

public class WebViewUtils {
    /**
     * @param htmlcontent
     * @param loadType    加载方式  1,加载html标签代码  3,加载本地html
     */
    public static void load(WebView webView,WebViewClient client, String htmlcontent, int loadType) {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollBarSize(5);
        webView.getSettings().setSupportZoom(true); // 可以缩放
        webView.getSettings().setNeedInitialFocus(false);
        webView.getSettings().setJavaScriptEnabled(true);
//      webview.getSettings().setBuiltInZoomControls(true); // 显示放大缩小
//      webView.addJavascriptInterface(new JavaScriptInterface(context), "imagelistner"); //给图片设置点击监听的
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);//自动加载图片
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        if (client != null)
            webView.setWebViewClient(client);
        else
            webView.setWebViewClient(new MyWebViewClient());

        if (loadType == 1) {
            webView.loadDataWithBaseURL(null, htmlcontent, "text/html",
                    "utf-8", null); //getNewContent(htmlcontent)
        }else if (loadType == 2)
            webView.loadUrl(htmlcontent);
        else{
            webView.loadUrl(htmlcontent); // "file:///android_asset/html/example.html"
        }
    }

    //将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
    private String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height",
                        "auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

    //如果html中图片没有设置大小，可以采用下面简单方法，设置图片的宽高，但图片可能会变形。
    private String getNewContent1(String content) {
        return content.replace("<img", "<img height=\"250px\"; width=\"100%\"");
    }

    private static class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset(view);//重置webview中img标签的图片大小
//            addImageClickListner();   // 添加监听图片的点击js函数
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private static void imgReset(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    /**
     * 注入js脚本，设置图片点击函数openImage
     */
    private void addImageClickListner(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    /**
     * 实现点击事件
     */
    private static class JavaScriptInterface {
        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
//            Intent intent = new Intent();
//            intent.putExtra("image", img);
//            intent.setClass(context, BigImageActivity.class);//BigImageActivity查看大图的类，自己定义就好
//            context.startActivity(intent);
        }
    }
}
