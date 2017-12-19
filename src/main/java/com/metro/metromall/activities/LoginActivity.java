package com.metro.metromall.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.metro.metromall.R;
import com.metro.metromall.fragments.login.AuthCodeFragment;
import com.metro.metromall.fragments.login.LoginFragment;
import com.metro.metromall.fragments.login.NRegFragment;
import com.metro.metromall.fragments.vip_center.PerfectInfoFragment;
import com.metro.metromall.fragments.login.RegSuccessFragment;
import com.metro.metromall.fragments.login.SMSFragment;
import com.metro.metromall.fragments.login.SuccessFragment;
import com.metro.metromall.fragments.login.UpdatePasswordFragment;
import com.metro.metromall.fragments.login.YRegFragment;

import java.util.Stack;

public class LoginActivity extends AppCompatActivity {
    //Fragment管理器
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    //使用一个栈记录所有添加的fragment
    private Stack<Fragment> fragmentStack = new Stack<Fragment>();

    //实例化Fragment
    LoginFragment loginFragment = new LoginFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg);
        initView();
    }
    //默认进入登录界面
    private void initView(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frams,loginFragment);
        fragmentStack.push(loginFragment);
        //transaction.addToBackStack("tag");
        transaction.commit();
        if (fragmentStack.empty()){
            this.finish();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (fragmentManager.getBackStackEntryCount() == 0){//|| state.equals("logout")
                this.setResult(10);
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
