package com.metro.metromall.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.adapter.AmplifyPictureAdapter;
import com.metro.metromall.fragments.utils.ContentViewPager;

import java.util.ArrayList;
import java.util.List;

public class AmplifyPictureActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager zoom_image_viewpager;
    TextView zoom_image_position;
    ArrayList<String> image_list;
    AmplifyPictureAdapter amplifyPictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amplify_picture);
        initView();
        setOnclick();
        initData();
    }
    private void initView(){
        zoom_image_viewpager = (ViewPager)findViewById(R.id.zoom_image_viewpager);
        zoom_image_position = (TextView)findViewById(R.id.zoom_image_position);
    }
    private void initData(){
        image_list = new ArrayList<>();
        image_list = getIntent().getStringArrayListExtra("zoom_image");
        int position = Integer.parseInt(getIntent().getStringExtra("position"));
        zoom_image_position.setText((position+1) + "/" + image_list.size());
        amplifyPictureAdapter = new AmplifyPictureAdapter(this,image_list);
        zoom_image_viewpager.setAdapter(amplifyPictureAdapter);
        amplifyPictureAdapter.notifyDataSetChanged();
        zoom_image_viewpager.setCurrentItem(position);
        zoom_image_viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                zoom_image_position.setText((position % image_list.size())+1 + "/" + image_list.size());
            }
        });
    }
    private  void setOnclick(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
