package com.metro.metromall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.tools.AddCartSuccessToast;
import com.metro.metromall.utils.cacheImage.AsyncImageLoader;
import com.metro.metromall.utils.cacheImage.FileCache;
import com.metro.metromall.utils.cacheImage.MemoryCache;

import java.io.File;
import java.util.List;

/**
 * Created by guhf on 2017/11/24.
 */

public class GoodsViewAdapter extends RecyclerView.Adapter<GoodsViewAdapter.MyViewHolder> {
    Context context;
    List<String> image_urls;
    List<String> goods_names;
    List<String> goods_prices;
    private AsyncImageLoader imageLoader;//异步组件
    private GoodsItemOnClickListener goodsItemOnClickListener;

    public GoodsViewAdapter(Context context, List<String> image_urls ,List<String> goods_names,List<String> goods_prices) {
        this.context = context;
        this.image_urls = image_urls;
        this.goods_names = goods_names;
        this.goods_prices = goods_prices;

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
                context).inflate(R.layout.item_goods, parent,
                false),goodsItemOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.mai_quality_image.setTag(image_urls.get(position));
        //异步加载图片，先从一级缓存、再二级缓存、最后网络获取图片
        Bitmap bmp = imageLoader.loadBitmap(holder.goods_image, image_urls.get(position));

        if(bmp == null) {
            holder.goods_image.setImageResource(R.mipmap.user);
        } else {
            holder.goods_image.setImageBitmap(bmp);
        }
        holder.goods_name.setText(goods_names.get(position));
        holder.goods_price.setText("¥"+goods_prices.get(position));
    }

    @Override
    public int getItemCount() {
        return image_urls.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout goods;
        ImageView goods_image;
        ImageView preferential_mark;
        ImageView get_goods_mode;
        TextView goods_name;
        TextView goods_price;
        ImageView add_shop_cart;
        private GoodsItemOnClickListener itemOnClickListener;
        public MyViewHolder(View view,GoodsItemOnClickListener itemOnClickListener) {
            super(view);
            goods = (LinearLayout) view.findViewById(R.id.goods);
            goods_image = (ImageView) view.findViewById(R.id.goods_image);
            preferential_mark = (ImageView) view.findViewById(R.id.preferential_mark);
            get_goods_mode = (ImageView) view.findViewById(R.id.get_goods_mode);
            goods_name = (TextView) view.findViewById(R.id.goods_name);
            goods_price = (TextView) view.findViewById(R.id.goods_price);
            add_shop_cart = (ImageView) view.findViewById(R.id.add_shop_cart);
            this.itemOnClickListener = itemOnClickListener;
            goods.setOnClickListener(this);
            add_shop_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddCartSuccessToast.makeText(context,"添加购物车成功!",2000).show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            itemOnClickListener.itemClick(v,getPosition());
        }
    }
    public interface GoodsItemOnClickListener{
        void itemClick(View view, int position);
    }
    public void setGoodsItemOnClickListener(GoodsItemOnClickListener goodsItemOnClickListener){
        this.goodsItemOnClickListener = goodsItemOnClickListener;
    }
}
