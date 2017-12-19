package com.metro.metromall.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.adapter.OrdersAdapter;
import com.metro.metromall.fragments.orders.ScllorTabView;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class OrdersActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    RadioButton all_order;
    RadioButton payment_pending;
    RadioButton awaiting_delivery;
    RadioButton complete_order;
    ViewPager orders_view;
    View line_bottom;
    int line_width;

    ScllorTabView scllorTabView;

    private OrdersAdapter ordersAdapter;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initView();
    }
    //实例化控件
    private void initView() {
        fragmentManager = getSupportFragmentManager();
        re_search = (RelativeLayout) findViewById(R.id.re_search);
        img_back = (ImageView) findViewById(R.id.img_back);
        text_back = (TextView) findViewById(R.id.text_back);
        news_title = (TextView) findViewById(R.id.text_title_context);
        logout = (TextView)findViewById(R.id.logout);
        all_order = (RadioButton) findViewById(R.id.all_order);
        payment_pending = (RadioButton) findViewById(R.id.payment_pending);
        awaiting_delivery = (RadioButton) findViewById(R.id.awaiting_delivery);
        complete_order = (RadioButton) findViewById(R.id.complete_order);
        orders_view = (ViewPager)findViewById(R.id.orders_view);
        line_bottom = findViewById(R.id.line_bottom);

        news_title.setText(R.string.my_orders);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);


        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        all_order.setOnClickListener(this);
        payment_pending.setOnClickListener(this);
        awaiting_delivery.setOnClickListener(this);
        complete_order.setOnClickListener(this);
        orders_view.setOnPageChangeListener(this);

        ordersAdapter = new OrdersAdapter(fragmentManager);
        orders_view.setAdapter(ordersAdapter);

        String selector = getIntent().getStringExtra("selector");
        switch (selector){
            case "one":
                orders_view.setCurrentItem(1);
                payment_pending.setChecked(true);
                break;
            case "two":
                orders_view.setCurrentItem(2);
                awaiting_delivery.setChecked(true);
                break;
            case "three":
                orders_view.setCurrentItem(0);
                all_order.setChecked(true);
                break;
        }
        // 设置下划线宽度
        line_width = getWindowManager().getDefaultDisplay().getWidth() / ordersAdapter.getCount();
        line_bottom.getLayoutParams().width = line_width;
        line_bottom.requestLayout();
//        scllorTabView = new ScllorTabView(OrdersActivity.this);
//        scllorTabView.setSelectedColor(Color.parseColor("#003894"),Color.parseColor("#003894"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                hideInputKeyboard(this);
                finish();
                break;
            case R.id.text_back:
                hideInputKeyboard(this);
                finish();
                break;
            case R.id.all_order:
                orders_view.setCurrentItem(0);
                break;
            case R.id.payment_pending:
                orders_view.setCurrentItem(1);
                break;
            case R.id.awaiting_delivery:
                orders_view.setCurrentItem(2);
                break;
            case R.id.complete_order:
                orders_view.setCurrentItem(3);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float tagerX = position * line_width + positionOffset / ordersAdapter.getCount();
        line_bottom.animate().translationX(tagerX).setDuration(0);

        //scllorTabView.setOffset(position,positionOffset);
//        ViewPropertyAnimator.animate(line_bottom).translationX(tagerX)
//                .setDuration(0);
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                all_order.setChecked(true);
                break;
            case 1:
                payment_pending.setChecked(true);
                break;
            case 2:
                awaiting_delivery.setChecked(true);
                break;
            case 3:
                complete_order.setChecked(true);
                break;
        }
    }
    /*
    参数state 有三种取值：
    0：什么都没做
    1：开始滑动
    2：滑动结束
    从滑动开始依次为：
    argo== (1,2,0)
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
