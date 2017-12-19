package com.metro.metromall.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metro.metromall.R;

import java.util.List;
import java.util.Map;

import static com.metro.metromall.utils.DownloadUtil.getPicture;

/**
 * Created by guhf on 2017/11/10.
 */

public class ClassifyItemAdapter extends RecyclerView.Adapter<ClassifyItemAdapter.MyViewHolder> {
    Context context;
    //List<String> names;
    List<Map<String,String>> contents;
    private ClassifyDetailItemOnClickListener classifyDetailItemOnClickListener;

    public ClassifyItemAdapter(Context context) {
        this.context = context;
    }

    public ClassifyItemAdapter(Context context, List<Map<String, String>> contents) {
        this.context = context;
        this.contents = contents;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_classify_detail_item, parent,
                false),classifyDetailItemOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(contents.get(position).get("classify_item_img"));
                holder.classify_item_img.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.classify_item_img.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
        holder.classify_item_name.setText(contents.get(position).get("classify_item_name"));
    }

    @Override
    public int getItemCount()
    {
        return contents.size();
    }

    class MyViewHolder extends ViewHolder implements View.OnClickListener {
        LinearLayout classify_detail;
        ImageView classify_item_img;
        TextView classify_item_name;
        private ClassifyDetailItemOnClickListener itemOnClickListener;
        public MyViewHolder(View view,ClassifyDetailItemOnClickListener itemOnClickListener)
        {
            super(view);
            classify_detail = (LinearLayout)view.findViewById(R.id.classify_detail);
            classify_item_img = (ImageView)view.findViewById(R.id.classify_item_img);
            classify_item_name = (TextView) view.findViewById(R.id.classify_item_name);
            this.itemOnClickListener = itemOnClickListener;
            classify_detail.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            itemOnClickListener.onItemClick(v,getPosition());
        }
    }
    public interface ClassifyDetailItemOnClickListener{
        void onItemClick(View view , int position);
    }
    public void setClassifyDetailItemOnClickListener(ClassifyDetailItemOnClickListener classifyDetailItemOnClickListener ){
        this.classifyDetailItemOnClickListener = classifyDetailItemOnClickListener;
    }
}
