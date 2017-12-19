package com.metro.metromall.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.metro.metromall.R;
import com.metro.metromall.fragments.search.ResultBackListener;
import com.metro.metromall.fragments.search.SearchBackListener;
import com.metro.metromall.fragments.search.SearchFragment;
import com.metro.metromall.fragments.search.SearchResultFragment;

import java.util.Stack;

public class SearchActivity extends AppCompatActivity {
    //Fragment管理器
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    //使用一个栈记录所有添加的fragment
    private Stack<Fragment> fragmentStack = new Stack<Fragment>();
    private ResultBackListener resultBackListener;
    private boolean isInterception = false;
    private boolean isSearch = false;
    private SearchBackListener searchBackListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView(){
        String state = getIntent().getStringExtra("state");
        if (state.equals("search")){
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frams,new SearchFragment());
            fragmentStack.push(new SearchFragment());
            //transaction.addToBackStack("tag");
            transaction.commit();
        }
        if (state.equals("result")){
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.frams,new SearchResultFragment());
            fragmentStack.push(new SearchResultFragment());
            //transaction.addToBackStack("tag");
            transaction.commit();
        }

        if (fragmentStack.empty()){
            this.finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //搜索结果界面返回键事件
            if (isInterception()) {
                if (resultBackListener != null) {
                    resultBackListener.onBackForward();
                    return false;
                }
            }
            //搜索界面返回键事件
            if (isSearch()) {
                if (searchBackListener != null) {
                    searchBackListener.onBackForward();
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public ResultBackListener getResultBackListener() {
        return resultBackListener;
    }

    public void setResultBackListener(ResultBackListener resultBackListener) {
        this.resultBackListener = resultBackListener;
    }

    public SearchBackListener getSearchBackListener() {
        return searchBackListener;
    }

    public void setSearchBackListener(SearchBackListener searchBackListener) {
        this.searchBackListener = searchBackListener;
    }

    public boolean isInterception() {
        return isInterception;
    }

    public void setInterception(boolean isSearch) {
        this.isInterception = isSearch;
    }
    public boolean isSearch() {
        return isSearch;
    }

    public void setSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }
}
