package com.metro.metromall.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.metro.metromall.R;
import com.metro.metromall.fragments.NoticeFragment;

import java.util.Stack;

public class NoticeActivity extends FragmentActivity {
    //Fragment管理器
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    //使用一个栈记录所有添加的fragment
    private Stack<Fragment> fragmentStack = new Stack<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        initView();
    }

    private void initView(){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frams,new NoticeFragment());
        fragmentStack.push(new NoticeFragment());
        //transaction.addToBackStack("tag");
        transaction.commit();
        if (fragmentStack.empty()){
            this.finish();
        }
    }
}
