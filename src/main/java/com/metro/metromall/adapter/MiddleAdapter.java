package com.metro.metromall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.metro.metromall.R;
import com.metro.metromall.utils.cacheImage.AsyncImageLoader;
import com.metro.metromall.utils.cacheImage.FileCache;
import com.metro.metromall.utils.cacheImage.MemoryCache;

import java.io.File;
import java.util.List;

/**
 * Created by guhf on 2017/11/24.
 */

public class MiddleAdapter extends RecyclerView.Adapter<MiddleAdapter.MyViewHolder> {
    Context context;
    List<String> image_urls;
    private AsyncImageLoader imageLoader;//异步组件
    private MiddleItemOnClickListener middleItemOnClickListener;

    public MiddleAdapter(Context context, List<String> image_urls) {
        this.context = context;
        this.image_urls = image_urls;

        //MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
        MemoryCache mcache=new MemoryCache();//内存缓存
        File sdCard = android.os.Environment.getExternalStorageDirectory();//获得SD卡
        File cacheDir = new File(sdCard, "metro_cache" );//缓存根目录
        FileCache fcache=new FileCache(context, cacheDir, "news_img");//文件缓存
        imageLoader = new AsyncImageLoader(context, mcache,fcache);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_middle, parent,
                false),middleItemOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.mai_quality_image.setTag(image_urls.get(position));
        //异步加载图片，先从一级缓存、再二级缓存、最后网络获取图片
        Bitmap bmp = imageLoader.loadBitmap(holder.mai_quality_image, image_urls.get(position));
        if(bmp == null) {
            holder.mai_quality_image.setImageResource(R.mipmap.user);
        } else {
            holder.mai_quality_image.setImageBitmap(bmp);
        }
    }

    @Override
    public int getItemCount() {
        return image_urls.size();
    }
    class MyViewHolder extends ViewHolder implements View.OnClickListener {
        ImageView mai_quality_image;
        private MiddleItemOnClickListener itemOnClickListener;
        public MyViewHolder(View view,MiddleItemOnClickListener itemOnClickListener) {
            super(view);
            mai_quality_image = (ImageView) view.findViewById(R.id.middle_image);
            this.itemOnClickListener = itemOnClickListener;
            mai_quality_image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemOnClickListener.itemClick(v,getPosition());
        }
    }
    public interface MiddleItemOnClickListener{
        void itemClick(View view, int position);
    }
    public void setMiddleItemOnClickListener(MiddleItemOnClickListener middleItemOnClickListener){
        this.middleItemOnClickListener = middleItemOnClickListener;
    }
}
