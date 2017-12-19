package com.metro.metromall.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;

public class MallActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        initView();
    }
    private void initView(){
        re_search = (RelativeLayout)findViewById(R.id.re_search);
        img_back = (ImageView)findViewById(R.id.img_back);
        text_back = (TextView)findViewById(R.id.text_back);
        news_title = (TextView)findViewById(R.id.text_title_context);
        logout = (TextView)findViewById(R.id.logout);

        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);
        news_title.setText(R.string.check_mall);
        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                this.finish();
                break;
            case R.id.text_back :
                this.finish();
                break;
        }
    }
}
