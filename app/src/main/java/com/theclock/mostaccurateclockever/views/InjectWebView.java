package com.theclock.mostaccurateclockever.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.theclock.mostaccurateclockever.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by beno on 22-Apr-17.
 */

public class InjectWebView extends FrameLayout {

    OnCloseButtonListener listener;

    @BindView(R.id.inject_webview)
    WebView webView;
    @BindView(R.id.inject_webview_close_button)
    ImageButton closeButton;

    String jsToInject;

    public final static String CLOCK_URL = "https://time.is/Lisbon";


    public InjectWebView(Context context) {
        super(context);
        init();
    }

    public InjectWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InjectWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.fragment_clock_webview_layout, null);
        addView(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.inject_webview_close_button)
    public void closeButtonClick(View view) {
        this.setVisibility(View.GONE);
        if (listener != null)
            listener.closeButtonClicked();
    }


    public void injectJsAndLoad(String jsToInject, OnCloseButtonListener listener) {
        this.jsToInject = jsToInject;
        this.listener = listener;
        setVisibility(View.VISIBLE);
        initWebView();

    }

    private void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(CLOCK_URL);
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            webView.evaluateJavascript("(function() {" +
                    "var script = document.createElement('script'); " +
                    "script.setAttribute('type','text/javascript'); " +
                    "script.setAttribute('src','https://momentjs.com/downloads/moment.min.js'); " +
                    "document.head.appendChild(script)" +
                    " })();", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.d("LogName", s);
                }
            });

            webView.loadUrl("javascript:(function() { document.getElementById('twd').setAttribute('id', 'youre_mine') })()");
            webView.loadUrl("javascript:(function() { document.getElementById('youre_mine').outerHTML = moment() ;})()");
//            webView.evaluateJavascript("javascript:(function() { return moment().format() })()", new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {
//                    Log.i("value", value);
//                }
//            });

        }

    }


    public interface OnCloseButtonListener {
        public void closeButtonClicked();
    }

}
