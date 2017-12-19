package com.metro.metromall.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.activities.SearchActivity;
import com.metro.metromall.tools.DividerGridItemDecoration;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guhf on 2017/11/10.
 */

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.MyViewHolder> {
    Context context;
    List<String> item_imgs;
    List<String> item_names;
    ClassifyItemAdapter itemAdapter;
    List<Map<String,String>> contents;
    Map<String,String> maps;
    String img_url ="http://pic.metromall.cn/Resource/ProductCataFilePath/6121"+ URLEncoder.encode("进口水果")+".jpg";

    public ClassifyAdapter(Context context, List<String> item_names) {
        this.context = context;
        this.item_names = item_names;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_classify_detail, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.small_class_title.setText(item_names.get(position));

        item_imgs = new ArrayList<String>();
        contents = new ArrayList<Map<String, String>>();
        maps = new HashMap<String,String>();
        for (int m = 0; m < 5; m++)
        {
            item_imgs.add(img_url);
        }
        for (int i = 0; i < item_imgs.size(); i++)
        {
            maps.put("classify_item_img",item_imgs.get(i));
            maps.put("classify_item_name",item_names.get(position) + "--" + i);
            contents.add(maps);
        }
        itemAdapter = new ClassifyItemAdapter(context,contents);
        holder.classify_item_recycler.setAdapter(itemAdapter);
        holder.classify_item_recycler.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        holder.classify_item_recycler.addItemDecoration(new DividerGridItemDecoration(context));
        itemAdapter.setClassifyDetailItemOnClickListener(new ClassifyItemAdapter.ClassifyDetailItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, SearchActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("state","result");
                bundle.putString("search_text",item_names.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return item_names.size();
    }

    class MyViewHolder extends ViewHolder
    {
        TextView small_class_title;
        RecyclerView classify_item_recycler;
        public MyViewHolder(View view)
        {
            super(view);
            small_class_title = (TextView) view.findViewById(R.id.small_class_title);
            classify_item_recycler = (RecyclerView)view.findViewById(R.id.classify_item_recycler);
        }
    }
}
