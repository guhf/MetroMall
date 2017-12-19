package com.metro.metromall.activities;

import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.metro.metromall.MainActivity;
import com.metro.metromall.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView web_back;
    WebView web_view;
    String _URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        loadingData();
    }
    private void initView(){
        web_back = (ImageView)findViewById(R.id.web_back);
        web_view = (WebView) findViewById(R.id.web_view);
        web_view.setVisibility(View.VISIBLE);

        web_back.setOnClickListener(this);
    }
    private void loadingData(){
       //String _URL = getIntent().getStringExtra("_url");
        _URL = "https://m.baidu.com/";
        web_view.loadUrl(_URL);
        //设置后可以弹出alert
        web_view.setWebChromeClient(new WebChromeClient());
        //设置可自由缩放网页
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_view.getSettings().setSupportZoom(true);
        web_view.getSettings().setBuiltInZoomControls(true);
        web_view.getSettings().setSupportMultipleWindows(true);
        web_view.getSettings().setAppCacheEnabled(true);
        web_view.getSettings().setDomStorageEnabled(true);

        web_view.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==KeyEvent.ACTION_DOWN){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
