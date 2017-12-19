package com.metro.metromall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.metro.metromall.activities.AmplifyPictureActivity;
import com.metro.metromall.tools.ZoomImageView;
import com.metro.metromall.utils.cacheImage.AsyncImageLoader;
import com.metro.metromall.utils.cacheImage.FileCache;
import com.metro.metromall.utils.cacheImage.MemoryCache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

/**
 * Created by guhf on 2017/11/27.
 */

public class GoodsDetailAdapter extends PagerAdapter {
    private AsyncImageLoader imageLoader;//异步组件
    Context context;
    List<ImageView> goods_detail_images;
    List<String> goods_detail_image_urls;

    public GoodsDetailAdapter(final Context context, final List<String> goods_detail_image_urls) {
        this.context = context;
        this.goods_detail_image_urls = goods_detail_image_urls;
        this.goods_detail_images = goods_detail_images;
        goods_detail_images = new ArrayList<ImageView>();

        //MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
        MemoryCache mcache=new MemoryCache();//内存缓存
        File sdCard = android.os.Environment.getExternalStorageDirectory();//获得SD卡
        File cacheDir = new File(sdCard, "metro_cache" );//缓存根目录
        FileCache fcache=new FileCache(context, cacheDir, "news_img");//文件缓存
        imageLoader = new AsyncImageLoader(context, mcache,fcache);

        for (int i = 0; i < goods_detail_image_urls.size(); i++) {
            ImageView imageView = new ImageView(context);
            Bitmap bmp = imageLoader.loadBitmap(imageView, goods_detail_image_urls.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(bmp);
            goods_detail_images.add(imageView);
            final String position = String.valueOf(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideInputKeyboard(context);
                    Intent intent = new Intent(context,AmplifyPictureActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("position", position);
                    bundle.putStringArrayList("zoom_image", (ArrayList<String>) goods_detail_image_urls);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //不重写可能会notifyDataSetChanged失败
    @Override
    public int getItemPosition(Object object) {
        int count = goods_detail_images.size();
        if (count > 0) {
            count--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (goods_detail_images.size() != 0){
            container.removeView(goods_detail_images.get(position%goods_detail_images.size()));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (goods_detail_images.size() == 0){
            return null;
        }
        container.addView(goods_detail_images.get(position%goods_detail_images.size()));
        return  goods_detail_images.get(position%goods_detail_images.size());
    }
}
