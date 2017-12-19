package com.metro.metromall.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.utils.DensityUtil;

import java.util.List;

/**
 * Created by guhf on 2017/11/24.
 */

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.MyViewHolder> {
    private int max_count = 20;//最大显示数
    //两个final int类型表示ViewType的两种类型
    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1;
    private final int COMPLETE_TYPE = 2;
    private boolean complete = false;
    private Boolean isFootView = false;//是否添加了FootView
    private String footViewText = "正在加载中...";//FootView的内容
    private String complete_text = "已经到底了";

    Context context;
    List<String> notice_content_urls;
    private NoticeItemOnClickListener noticeItemOnClickListener;

    public NoticeListAdapter(Context context, List<String> notice_content_urls ) {
        this.context = context;
        this.notice_content_urls = notice_content_urls;
    }
    @Override
    public int getItemViewType(int position) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        int screenHeight =  ((WindowManager) context .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        int height = DensityUtil.px2dip(screenHeight - statusBarHeight);
        if (position == max_count - 1 ) {
            return FOOT_TYPE ;
        }
        if (position == notice_content_urls.size() - 1 && position >= (height - 50) / 45){ //(position * 45) + 50 >= height
            return COMPLETE_TYPE;
        }
        return NORMAL_TYPE;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View normal_views = LayoutInflater.from(
                context).inflate(R.layout.item_notice, parent,
                false);
        View foot_view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_refresh_foot, parent, false);
        View complete_view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_complete, parent, false);
        if (viewType == COMPLETE_TYPE){
            return new MyViewHolder(complete_view, COMPLETE_TYPE,null);
        }
        if (viewType == FOOT_TYPE){
            return new MyViewHolder(foot_view, FOOT_TYPE,null);
        }
        return new MyViewHolder(normal_views, NORMAL_TYPE, noticeItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == COMPLETE_TYPE) {
            holder.complete_view.setText(complete_text);
        }else
        if (getItemViewType(position) == FOOT_TYPE) {
            holder.tvFootView.setText(footViewText);
            // 刷新太快 所以使用Hanlder延迟1.5秒
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    max_count += 10;
                    notifyDataSetChanged();
                }
            }, 1500);
        } else
            if (getItemViewType(position) == NORMAL_TYPE) {
            holder.notice_name.setText(notice_content_urls.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (notice_content_urls.size() < max_count) {
            return notice_content_urls.size();
        }
        return max_count;
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout notice;
        TextView notice_name;
        private NoticeItemOnClickListener itemOnClickListener;
        public TextView tvFootView;//footView的TextView属于独自的一个layout
        public TextView complete_view;

        public MyViewHolder(View view, int viewType,NoticeItemOnClickListener itemOnClickListener) {
            super(view);
            if (viewType == COMPLETE_TYPE) {
                complete_view = (TextView) view.findViewById(R.id.foot_complete_text);
            }
            if (viewType == NORMAL_TYPE) {
                notice = (LinearLayout) view.findViewById(R.id.notice);
                notice_name = (TextView) view.findViewById(R.id.notice_name);
                this.itemOnClickListener = itemOnClickListener;
                notice.setOnClickListener(this);
            }
            if (viewType == FOOT_TYPE) {
                tvFootView = (TextView) view.findViewById(R.id.foot_refresh_text);
            }
        }

        @Override
        public void onClick(View v) {
            itemOnClickListener.itemClick(v,getPosition());
        }
    }
    public interface NoticeItemOnClickListener{
        void itemClick(View view, int position);
    }
    public void setNoticeItemOnClickListener(NoticeItemOnClickListener noticeItemOnClickListener){
        this.noticeItemOnClickListener = noticeItemOnClickListener;
    }
}
