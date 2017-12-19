package com.metro.metromall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.metro.metromall.utils.cacheImage.AsyncImageLoader;
import com.metro.metromall.utils.cacheImage.FileCache;
import com.metro.metromall.utils.cacheImage.MemoryCache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guhf on 2017/11/27.
 */

public class BannerAdapter extends PagerAdapter {
    Context context;
    List<ImageView> banner_images;
    List<String> banner_image_urls;

    private AsyncImageLoader imageLoader;//异步组件
    public BannerAdapter(final Context context, final List<String> banner_image_urls) {
        this.context = context;
        this.banner_image_urls = banner_image_urls;
        banner_images = new ArrayList<ImageView>();

        //MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
        MemoryCache mcache=new MemoryCache();//内存缓存
        File sdCard = android.os.Environment.getExternalStorageDirectory();//获得SD卡
        File cacheDir = new File(sdCard, "metro_cache" );//缓存根目录
        FileCache fcache=new FileCache(context, cacheDir, "news_img");//文件缓存
        imageLoader = new AsyncImageLoader(context, mcache,fcache);

        for (int i = 0; i < banner_image_urls.size(); i++) {
            ImageView imageView = new ImageView(context);
            Bitmap bmp = imageLoader.loadBitmap(imageView, banner_image_urls.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageBitmap(bmp);
            banner_images.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < banner_image_urls.size(); i++) {
//                    final Bitmap bmp = DownloadUtil.getPicture(banner_image_urls.get(i));
//                    final ImageView imageView =  new ImageView(context);
//
//                    imageView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            imageView.setImageBitmap(bmp);
//                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                        }
//                    });
//                    banner_images.add(imageView);
//                    final int finalI = i;
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            MyFailToast.makeText(context, finalI +"--暂无数据", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            }
//        }).start();
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    //不重写可能会notifyDataSetChanged失败
    @Override
    public int getItemPosition(Object object) {
        int count = banner_image_urls.size();
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
        if (banner_images.size() != 0){
            container.removeView(banner_images.get(position%banner_images.size()));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (banner_images.size() == 0){
            return null;
        }
        container.addView(banner_images.get(position%banner_images.size()));
        return  banner_images.get(position%banner_images.size());
    }
}
