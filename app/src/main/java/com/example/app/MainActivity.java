package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView myWebView;
    String Url = "http://ieeebracu.com/";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = findViewById(R.id.activity_main_webview);
        myWebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        // REMOTE RESOURCE
        myWebView.loadUrl(Url);

        //SwipeRefreshLayout
        final SwipeRefreshLayout finalMySwipeRefreshLayout1;
        finalMySwipeRefreshLayout1 = findViewById(R.id.swiperefresh);
        finalMySwipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                myWebView.loadUrl(myWebView.getUrl());
                // stop the refreshing
                finalMySwipeRefreshLayout1.setRefreshing(false);
            }
        });

        myWebView.setWebViewClient(new WebViewClient() {
            //pdf support
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".pdf")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    // if want to download pdf manually create AsyncTask here
                    // and download file
                    return true;
                }
                return false;
            }
        });

        // LOCAL RESOURCE
        // mWebView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
