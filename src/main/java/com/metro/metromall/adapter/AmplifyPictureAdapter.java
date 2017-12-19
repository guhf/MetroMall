package com.metro.metromall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.Toast;

import com.metro.metromall.tools.MyDialog;
import com.metro.metromall.utils.NamedUtil;
import com.metro.metromall.utils.cacheImage.AsyncImageLoader;
import com.metro.metromall.utils.cacheImage.FileCache;
import com.metro.metromall.utils.cacheImage.MemoryCache;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by guhf on 2017/12/12.
 */

public class AmplifyPictureAdapter extends PagerAdapter {
    private AsyncImageLoader imageLoader;//异步组件
    Context context;
    List<PhotoView> goods_detail_images;
    List<String> goods_detail_image_urls;
    Activity activity;
    PhotoView imageView;
    Bitmap bmp;

    public AmplifyPictureAdapter(final Context context, List<String> goods_detail_image_urls) {
        this.context = context;
        this.goods_detail_image_urls = goods_detail_image_urls;
        goods_detail_images = new ArrayList<PhotoView>();
        if(Activity.class.isInstance(context))
        {
            // 转化为activity，然后finish就行了
            activity = (Activity)context;
        }

        //MyApplication.getContext().getExternalFilesDir(null).getAbsolutePath();
        MemoryCache mcache=new MemoryCache();//内存缓存
        File sdCard = android.os.Environment.getExternalStorageDirectory();//获得SD卡
        File cacheDir = new File(sdCard, "metro_cache" );//缓存根目录
        FileCache fcache=new FileCache(context, cacheDir, "news_img");//文件缓存
        imageLoader = new AsyncImageLoader(context, mcache,fcache);

        for (int i = 0; i < goods_detail_image_urls.size(); i++) {
            imageView = new PhotoView(context);
            bmp = imageLoader.loadBitmap(imageView, goods_detail_image_urls.get(i));
            //imageView.setImageBitmap(bmp);
            goods_detail_images.add(imageView);
            //点击图片外的空白区域返回上级界面
            imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    activity.finish();
                }
            });
            //点击图片返回上级界面
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    activity.finish();
                }
            });
            final String image_name = "/" + NamedUtil.stringToMD5(goods_detail_image_urls.get(i)) + ".jpg";; //UUID.randomUUID().toString() + ".jpg";
            //长按出现保存图片提示
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MyDialog.Builder builder = new MyDialog.Builder(context);
                    builder.setSaveButton("保存图片", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //存储路径
                            File file = new File(android.os.Environment.getExternalStorageDirectory() + "/MetroMall/Picture/");
                            if (!file.exists()){
                                file.mkdirs();
                            }
                            try{
                                FileOutputStream fileOutputStream = new FileOutputStream(file.getPath() + image_name);
                                BitmapDrawable bmpDrawable = (BitmapDrawable)imageView.getDrawable();
                                Bitmap s_bmp = bmpDrawable.getBitmap();
                                s_bmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();

                                dialog.dismiss();
                                Toast.makeText(context,"图片已保存至" + file.getPath(),Toast.LENGTH_LONG).show();
                            }catch (Exception ex){
                                ex.printStackTrace();
                                dialog.dismiss();
                                Toast.makeText(context,"图片保存失败" ,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.createSaveImageDialog().show();
                    return false;
                }
            });
        }
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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
