package com.delaroystudios.chromecustomtab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by delaroy on 2/20/17.
 */
public class WebviewActivity extends AppCompatActivity {
    public static final String EXTRA_URL = "extra.url";

    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String url = getIntent().getStringExtra(EXTRA_URL);
        WebView webview = (WebView)findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        setTitle(url);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webview.loadUrl(url);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);

    }
}
