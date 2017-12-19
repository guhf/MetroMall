package com.metro.metromall.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.adapter.ClassifyAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.metro.metromall.utils.DownloadUtil.getPicture;

public class ClassifyDetailFragment extends Fragment {
    View view;
    ImageView title_img;
    RecyclerView classify_recycler;
    ClassifyAdapter classifyAdapter ;
    List<String> item_names ;
    List<Map<String,String>> contents;
    Map<String,String> maps;

    String title_img_url = "http://pic.metromall.cn/Resource/ProductCataFilePath/6005.jpg";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_classify_detail, container, false);
        initView();
        loadingPicture();
        return view;
    }
    private void initView(){
        title_img = (ImageView)view.findViewById(R.id.title_img);
        classify_recycler = (RecyclerView)view.findViewById(R.id.classify_recycler);

//        contents = new ArrayList<Map<String, String>>();
//        maps = new HashMap<String,String>();
//        for (int i = 0; i < 5; i++)
//        {
//            maps.put("classify_item_img",img_url);
//            maps.put("classify_item_name",getArguments().get("typename").toString() + i+"121212122");
//            contents.add(maps);
//        }
        item_names = new ArrayList<>();
        for (int m = 0; m < 5 ; m++){
            item_names.add(getArguments().get("typename").toString() + m);
        }
        classifyAdapter = new ClassifyAdapter(getContext(),item_names);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity(),
//                LinearLayoutManager.VERTICAL, false) {
//            @Override
//            public boolean canScrollVertically() {
//                return true;
//            }
//        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity());
        classify_recycler.setLayoutManager(linearLayoutManager);
        //解决RecyclerView与ScrollView滑动冲突问题
        classify_recycler.setNestedScrollingEnabled(false);
        classify_recycler.setAdapter(classifyAdapter);
//        classify_recycler.addItemDecoration(new DividerGridItemDecoration(getContext()));
    }
    //加载抬头图片
    private void loadingPicture(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(title_img_url);
                title_img.post(new Runnable() {
                    @Override
                    public void run() {
                        title_img.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }
}
