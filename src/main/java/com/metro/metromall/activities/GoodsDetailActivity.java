package com.metro.metromall.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;
import com.metro.metromall.adapter.GoodsDetailAdapter;
import com.metro.metromall.tools.AddCartSuccessToast;
import com.metro.metromall.tools.JsonHandler;
import com.metro.metromall.tools.NoScrollViewPager;
import com.metro.metromall.utils.cacheImage.AsyncImageLoader;
import com.metro.metromall.utils.cacheImage.FileCache;
import com.metro.metromall.utils.cacheImage.MemoryCache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class GoodsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private AsyncImageLoader imageLoader;//异步组件
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;

    ViewPager goods_detail_image;
    RadioButton goods_introduce;
    RadioButton goods_specs;
    RadioButton pc_detail;
    NoScrollViewPager goods_pager;
    LinearLayout goods_collect;
    LinearLayout goods_shop_car;
    Button goods_add_shop_cart;
    List<String> goods_image_urls;
    List<ImageView> goods_detail_images;
    GoodsDetailAdapter goodsDetailAdapter;
    JsonHandler jsonHandler;
    //当前显示的图片在list中的位置
    int zoom_position;

    LinearLayout dots;
    //存放小圆点
    private List<ImageView> imgDots;
    //当前索引位置以及上一个索引位置
    private int index = 0,preIndex = 0;
    //是否需要轮播标志
    private boolean isContinue = true;
    //定时器，用于实现轮播
    private Timer timer;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  1:
                    index ++;
                    goods_detail_image.setCurrentItem(goods_detail_image.getCurrentItem()+1);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        setOnclick();
        requestData();
    }

    private void initView() {
        re_search = (RelativeLayout) findViewById(R.id.re_search);
        img_back = (ImageView) findViewById(R.id.img_back);
        text_back = (TextView) findViewById(R.id.text_back);
        news_title = (TextView) findViewById(R.id.text_title_context);
        logout = (TextView) findViewById(R.id.logout);

        goods_detail_image = (ViewPager)findViewById(R.id.goods_detail_image);
        dots = (LinearLayout) findViewById(R.id.dots);
        goods_introduce = (RadioButton)findViewById(R.id.goods_introduce);
        goods_specs = (RadioButton)findViewById(R.id.goods_specs);
        pc_detail = (RadioButton)findViewById(R.id.pc_detail);
        goods_pager = (NoScrollViewPager)findViewById(R.id.goods_pager);
        goods_collect = (LinearLayout)findViewById(R.id.goods_collect);
        goods_shop_car = (LinearLayout)findViewById(R.id.goods_shop_car);
        goods_add_shop_cart = (Button)findViewById(R.id.goods_add_shop_cart);


        news_title.setText(R.string.goods_detail);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);
    }
    private void setOnclick(){
        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        goods_introduce.setOnClickListener(this);
        goods_specs.setOnClickListener(this);
        pc_detail.setOnClickListener(this);
        goods_collect.setOnClickListener(this);
        goods_shop_car.setOnClickListener(this);
        goods_add_shop_cart.setOnClickListener(this);
    }
    private void requestData(){
        jsonHandler = new JsonHandler(this);
        RoastingChartRes();
    }
    //显示的轮播图图片
    private void RoastingChartRes(){
        goods_image_urls = new ArrayList<String>();
        goods_detail_images = new ArrayList<ImageView>();
        goods_image_urls = jsonHandler.BannerData();
        imgDots = new ArrayList<ImageView>();
        ImageView dotImage;
        dots.removeAllViews();
        for (int i =0;i<goods_image_urls.size();i++){
            //显示的小点
            dotImage = new ImageView(this);
            dotImage.setImageResource(R.drawable.dot_home_select);
            dotImage.setPadding(8,3,3,3);
            dots.addView(dotImage);
            imgDots.add(dotImage);
        }
        goodsDetailAdapter = new GoodsDetailAdapter(this,goods_image_urls);
        goods_detail_image.setAdapter(goodsDetailAdapter);
        goodsDetailAdapter.notifyDataSetChanged();

        imgDots.get(0).setSelected(true);
        goods_detail_image.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imgDots.size(); i++) {
                    if (position % imgDots.size() == i) {
                        imgDots.get(i).setSelected(true);
                        zoom_position = i;
                    } else {
                        imgDots.get(i).setSelected(false);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        timer = new Timer();
        //执行定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //首先判断是否需要轮播，是的话我们才发消息
                if (isContinue) {
                    mHandler.sendEmptyMessage(1);
                }
            }
        },3000,3000);//延迟3秒，每隔3秒发一次消息
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                hideInputKeyboard(this);
                this.finish();
                break;
            case R.id.text_back:
                hideInputKeyboard(this);
                this.finish();
                break;
            case R.id.goods_collect:
                AddCartSuccessToast.makeText(this,"商品收藏成功!", 2000).show();
                break;
            case R.id.goods_shop_car:
                this.setResult(103);
                this.finish();
                break;
            case R.id.goods_add_shop_cart:
                AddCartSuccessToast.makeText(this,"添加购物车成功!", 2000).show();
                break;
        }
    }
}
