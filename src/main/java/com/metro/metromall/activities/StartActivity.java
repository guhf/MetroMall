package com.metro.metromall.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.MainActivity;
import com.metro.metromall.R;
import com.metro.metromall.utils.CommonUtil;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.FormattableFlags;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    public static String ISADV = "isAdv";
    // 是否显示广告页
    private String flag;
    // 跳转的url
    private String url;
    // 广告秒数
    private int count;
    // 广告页本地地址
    private String filePath;
    private Bitmap bitmap;
    private Animation animation;
    //广告图片
    private ImageView Advertising_Image;
    LinearLayout start;
    //跳过按钮
    private RelativeLayout start_skip;
    private TextView start_skip_text;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String text = "跳过( <font color='red'>"+getCount()+"</font> )";
                start_skip_text.setText(Html.fromHtml(text));
                handler.sendEmptyMessageDelayed(0, 1000);
                //animation.reset();
                //start_skip.startAnimation(animation);
            }
        }
    };
    /**
     * 声明处理动画的handler
     */
    private Handler handler_animation = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    start.setVisibility(View.VISIBLE);
                    Advertising_Image.setImageBitmap(bitmap);
//                    animation = AnimationUtils.loadAnimation(StartActivity.this,
//                            R.anim.slide_in_down);
                    handler.sendEmptyMessageDelayed(0, 1000);
                    break;
                case 2:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置顶部状态栏为透明
        //CommonUtil.setTranslucentStatus(this);
        setContentView(R.layout.activity_start);
        initView();
        //初始化的时候取数据
        flag = "1";
        url = "http://pic.metromall.cn/Resource/ProductCataFilePath/6121"+ URLEncoder.encode("进口水果")+".jpg" ;
        count = 5;
        //filePath ;
        String text = "跳过( <font color='red'>"+count+"</font> )";
        start_skip_text.setText(Html.fromHtml(text));

//        bitmap = BitmapFactory.decodeFile(filePath);
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.guanggao);
        BitmapDrawable drawable = new BitmapDrawable(getResources().openRawResource(R.mipmap.guanggao));
        bitmap = drawable.getBitmap();
        //为了让app启动页正常显示，加了延时-该延时正常是下载图片的时间，这里将图片下载到了本地，每次启动时显示的都是上次加载的
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if ("1".equals(flag) && bitmap != null) {
                    handler_animation.sendEmptyMessage(1);
                    if (!TextUtils.isEmpty(url)) {//广告图片点击之后的响应
                        Advertising_Image.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(StartActivity.this,
                                        WebActivity.class);
                                intent.putExtra("_url", url);
                                intent.putExtra(ISADV, true);
//                                intent.putExtra(
//                                        HomeWebFragment.EXTRA_PARAM_URL, url);
//                                IntentUtil.startNewActivityWithData(
//                                        (Activity) StartActivity.this, intent);
                                startActivity(intent);
                                handler.removeMessages(0);//关键-将定时器remove掉，不然跳转之后还在记时
                                finish();
                            }
                        });
                    }
                } else {
                    turnMainActivity();
                }
            }

        }, 1000);
    }
    private void initView(){
        start = (LinearLayout) findViewById(R.id.start);
        Advertising_Image = (ImageView)findViewById(R.id.Advertising_Image);
        start_skip = (RelativeLayout) findViewById(R.id.start_skip);
        start_skip_text = (TextView) findViewById(R.id.start_skip_text);

        start_skip.setOnClickListener(this);
    }

    private int getCount() {
        count--;
        if (count == 0) {
            turnMainActivity();
        }
        return count;
    }
    /**
     * 跳转到主界面
     */
    private void turnMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_skip:
                turnMainActivity();
                break;
        }
    }
}
