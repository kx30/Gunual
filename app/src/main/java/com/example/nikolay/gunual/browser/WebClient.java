package com.example.nikolay.gunual.browser;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

}
