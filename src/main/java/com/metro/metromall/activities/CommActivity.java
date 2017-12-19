package com.metro.metromall.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.metro.metromall.R;
import com.metro.metromall.fragments.NoticeFragment;
import com.metro.metromall.fragments.vip_center.CollectFragment;
import com.metro.metromall.fragments.vip_center.CouponsFragment;
import com.metro.metromall.fragments.vip_center.DeliveryAddressFragment;
import com.metro.metromall.fragments.vip_center.FootPrintFragment;
import com.metro.metromall.tools.FragmentBackListener;

import java.util.Stack;

public class CommActivity extends AppCompatActivity {
    //Fragment管理器
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    //使用一个栈记录所有添加的fragment
    private Stack<Fragment> fragmentStack = new Stack<Fragment>();

    private FragmentBackListener backListener;
    private boolean isInterception = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm);
        initView();
    }
    private void initView(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        String cases = getIntent().getStringExtra("case");
        switch (cases){
            case "coupons":
                transaction.add(R.id.comm_activity,new CouponsFragment());
                break;
            case "collect":
                transaction.add(R.id.comm_activity,new CollectFragment());
                break;
            case "footPrint":
                transaction.add(R.id.comm_activity,new FootPrintFragment());
                break;
            case "deliveryAddress":
                transaction.add(R.id.comm_activity,new DeliveryAddressFragment());
                break;
        }
        fragmentStack.push(new NoticeFragment());
        //transaction.addToBackStack("tag");
        transaction.commit();
        if (fragmentStack.empty()){
            this.finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //新增地址返回键事件
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (isInterception()) {
                if (backListener != null) {
                    backListener.onBackForward();
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public FragmentBackListener getBackListener() {
        return backListener;
    }

    public void setBackListener(FragmentBackListener backListener) {
        this.backListener = backListener;
    }

    public boolean isInterception() {
        return isInterception;
    }

    public void setInterception(boolean isInterception) {
        this.isInterception = isInterception;
    }
}
