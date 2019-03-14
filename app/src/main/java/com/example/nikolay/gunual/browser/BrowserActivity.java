package com.example.nikolay.gunual.browser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.nikolay.gunual.R;

public class BrowserActivity extends AppCompatActivity {

    private static final String TAG = "BrowserActivity";

    private WebView mWebView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Bundle argument = getIntent().getExtras();
        String url = argument.getString("url");
        Log.d(TAG, "onCreate: " + url);

        mWebView = findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebClient());

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

        initToolbar();
    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        Log.d(TAG, "initToolbar: Initialized.");
    }

}
