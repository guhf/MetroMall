package com.metro.metromall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.metro.metromall.activities.LoginActivity;
import com.metro.metromall.adapter.MainPagerAdapter;
import com.metro.metromall.fragments.main.ClassifyFragment;
import com.metro.metromall.fragments.main.HomeFragment;
import com.metro.metromall.fragments.main.MyFragment;
import com.metro.metromall.fragments.main.ShopCarFragment;
import com.metro.metromall.fragments.vip_center.AddAddressFragment;
import com.metro.metromall.tools.JsonHandler;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.tools.NetworkConnectChangedReceiver;
import com.metro.metromall.tools.RoundImageView;
import com.metro.metromall.utils.DownloadUtil;
import com.metro.metromall.utils.SharePUtil;
import com.metro.metromall.tools.FragmentBackListener;
import com.metro.metromall.tools.MyRadioButton;
import com.metro.metromall.tools.NoScrollViewPager;

import static android.media.MediaRecorder.VideoSource.CAMERA;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //退出时的时间
    private long mExitTime ;
    NoScrollViewPager main_pager;
    RadioGroup mainRadio;
    MyRadioButton bottomHome;
    MyRadioButton bottomClassify;
    MyRadioButton bottomShopCar;
    MyRadioButton bottomMy;
    RoundImageView main_center;
    JsonHandler jsonHandler;

    private HomeFragment homeFragment;
    private ClassifyFragment classifyFragment;
    private ShopCarFragment shopCarFragment;
    private MyFragment myFragment;
    private AddAddressFragment addAddressFragment;

    private MainPagerAdapter pagerAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        AccessPermissions();
        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new NetworkConnectChangedReceiver(),filter);
    }

    //实例化控件
    private void initView() {
        main_pager = (NoScrollViewPager) findViewById(R.id.main_pager);
        mainRadio = (RadioGroup) findViewById(R.id.main_radio);
        fragmentManager = getSupportFragmentManager();
        bottomHome = (MyRadioButton) findViewById(R.id.bottom_home);
        bottomClassify = (MyRadioButton) findViewById(R.id.bottom_classify);
        bottomShopCar = (MyRadioButton) findViewById(R.id.bottom_shop_car);
        bottomMy = (MyRadioButton) findViewById(R.id.bottom_my);
        main_center = (RoundImageView)findViewById(R.id.main_center);

        setMainCenter();
        bottomHome.setOnClickListener(this);
        bottomClassify.setOnClickListener(this);
        bottomShopCar.setOnClickListener(this);
        bottomMy.setOnClickListener(this);
        main_center.setOnClickListener(this);
        jsonHandler = new JsonHandler(this);

        SelectCurrent();
    }
    private void setMainCenter(){
        final String url = "http://pic.metromall.cn/Resource/FixedPic/17111310523713.png";

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bmp = DownloadUtil.getPicture(url);
                main_center.post(new Runnable() {
                    @Override
                    public void run() {
                        main_center.setImageBitmap(bmp);
                    }
                });
            }
        }).start();;

    }
    private void SelectCurrent(){
        pagerAdapter = new MainPagerAdapter(fragmentManager);
        main_pager.setAdapter(pagerAdapter);
        main_pager.setOffscreenPageLimit(4);
        main_pager.setCurrentItem(0);
        bottomHome.setChecked(true);
        //默认选中第一个
        //setTabSelection(0);
    }
    //Android6.0以上获取权限
    private void AccessPermissions(){
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA);
                //SelectCurrent();
            } else {
                //说明已经获取到权限了 想干嘛干嘛
                //SelectCurrent();
                return;
            }
        } else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA) {
            if (grantResults.length != 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //SelectCurrent();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    //底部导航栏选中
//    private void setTabSelection(int index) {
//        clearSelection();
//        //transaction = fragmentManager.beginTransaction();
//        hideFragments(transaction);
//        switch (index) {
//            case 0:
//                // 当点击了tab时，改变控件的图片和文字颜色
//                Drawable drawable_home = getResources().getDrawable(R.mipmap.icon_home_on);
//                bottomHome.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_home, null, null);
//                bottomHome.setTextColor(R.color.bottom_check);
//                break;
//            case 1:
//                // 当点击了tab时，改变控件的图片和文字颜色
//                Drawable drawable_classify = getResources().getDrawable(R.mipmap.icon_classify_on);
//                bottomClassify.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_classify, null, null);
//                bottomClassify.setTextColor(R.color.bottom_check);
//                break;
//            case 2:
//                // 当点击了tab时，改变控件的图片和文字颜色
//                Drawable drawable_shop_car = getResources().getDrawable(R.mipmap.icon_cart_on);
//                bottomShopCar.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_shop_car, null, null);
//                bottomShopCar.setTextColor(R.color.bottom_check);
//                break;
//            case 3:
//                // 当点击了tab时，改变控件的图片和文字颜色
//                Drawable drawable_my = getResources().getDrawable(R.mipmap.icon_customer_on);
//                bottomMy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_my, null, null);
//                bottomMy.setTextColor(R.color.bottom_check);
//                break;
//        }
//
//    }
//
//    // 每次选中之前先清楚掉上次的选中状态
//    private void clearSelection() {
//        Drawable drawable_home = getResources().getDrawable(R.mipmap.icon_home_default);
//        bottomHome.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_home, null, null);
//        bottomHome.setTextColor(R.color.gray);
//
//        Drawable drawable_classify = getResources().getDrawable(R.mipmap.icon_classify_default);
//        bottomClassify.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_classify, null, null);
//        bottomClassify.setTextColor(R.color.gray);
//
//        Drawable drawable_shop_car = getResources().getDrawable(R.mipmap.icon_cart_default);
//        bottomShopCar.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_shop_car, null, null);
//        bottomShopCar.setTextColor(R.color.gray);
//
//        Drawable drawable_my = getResources().getDrawable(R.mipmap.icon_customer_default);
//        bottomMy.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable_my, null, null);
//        bottomMy.setTextColor(R.color.gray);
//    }
//
//    // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
//    private void hideFragments(FragmentTransaction transaction) {
//        if (homeFragment != null) {
//            transaction.hide(homeFragment);
//        }
//        if (classifyFragment != null) {
//            transaction.hide(classifyFragment);
//        }
//        if (shopCarFragment != null) {
//            transaction.hide(shopCarFragment);
//        }
//        if (myFragment != null) {
//            transaction.hide(myFragment);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 1) {
            //setTabSelection(2);
            main_pager.setCurrentItem(2);
        }
        if (requestCode == 3 && resultCode == 1) {
           // setTabSelection(3);
            main_pager.setCurrentItem(3);
        }
        //没有登录返回之后
        if (requestCode == 2 && resultCode == 10) {
            //setTabSelection(0);
            main_pager.setCurrentItem(0);
            bottomShopCar.setChecked(false);
            bottomHome.setChecked(true);
        }
        if (requestCode == 2 && resultCode == 10) {
            //setTabSelection(0);
            main_pager.setCurrentItem(0);
            bottomMy.setChecked(false);
            bottomHome.setChecked(true);
        }
        //注销之后
        if (resultCode == 10) {
            //setTabSelection(0);
            main_pager.setCurrentItem(0);
            bottomMy.setChecked(false);
            bottomHome.setChecked(true);
        }
        //无卡注册完成后
        if (resultCode == 2) {
            //setTabSelection(0);
            main_pager.setCurrentItem(0);
        }
        //点击先逛逛按钮
        if (requestCode == 30 && resultCode == 30){
            fragmentManager.popBackStack();
            main_pager.setCurrentItem(0);
            bottomMy.setChecked(false);
            bottomHome.setChecked(true);
        }
        //首页进入的商品详情页面点击购物车按钮
        if (requestCode == 103 && resultCode == 103){
            main_pager.setCurrentItem(2);
            bottomHome.setChecked(false);
            bottomShopCar.setChecked(true);
        }
        //分类进入的商品详情页面点击购物车按钮
        if (resultCode == 203){
            main_pager.setCurrentItem(2);
            bottomClassify.setChecked(false);
            bottomShopCar.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_home:
                //setTabSelection(0);
                main_pager.setCurrentItem(0);
                break;
            case R.id.bottom_classify:
                //setTabSelection(1);
                main_pager.setCurrentItem(1);
                break;
            case R.id.bottom_shop_car:
                //Utils.checkLoginState(this);
                if (!SharePUtil.get(this,"metro_sp","login_state","0").equals("1")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    this.startActivityForResult(intent,2);
                }else {
                    //setTabSelection(2);
                    main_pager.setCurrentItem(2);
                }
                break;
            case R.id.bottom_my:
                //Utils.checkLoginState(this);
                if (!SharePUtil.get(this,"metro_sp","login_state","0").equals("1")){
                    Intent intent = new Intent(this, LoginActivity.class);
                    this.startActivityForResult(intent,3);
                }else {
                //setTabSelection(3);
                main_pager.setCurrentItem(3);
                }
                break;
            case R.id.main_center:
                MyFailToast.makeText(this,"--暂无数据", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==KeyEvent.ACTION_DOWN && backStackEntryCount >1 ){
//            //fragmentManager.popBackStack();
//            //main_pager.setCurrentItem(0);
//            //bottomHome.setChecked(true);
//            return true;
//        }
        /**
         * event.getRepeatCount()  这是重复次数。
         * 点后退键的时候，为了防止点得过快，触发两次后退事件，故做此设置。
         * 建议保留这个判断，增强程序健壮性。
         */
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 0 && backStackEntryCount ==0 && bottomHome.isChecked() == true) {//&& bottomHome.isChecked() == true
            if (System.currentTimeMillis() - mExitTime >2000){
                Toast.makeText(this,"再按一次返回键退出麦德龙网上商城",Toast.LENGTH_SHORT).show();//+ R.string.app_name
                mExitTime = System.currentTimeMillis();
            }else {
                finish();
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 0 && backStackEntryCount ==0 && bottomHome.isChecked() != true) {
            main_pager.setCurrentItem(0);
            bottomHome.setChecked(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
