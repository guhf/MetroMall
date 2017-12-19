package com.metro.metromall.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.metro.metromall.R;

import java.util.List;
import java.util.Map;

import static com.metro.metromall.utils.DownloadUtil.getPicture;

/**
 * Created by guhf on 2017/11/10.
 */

public class SearchHistoryTopAdapter extends RecyclerView.Adapter<SearchHistoryTopAdapter.MyViewHolder> {
    Context context;
    List<String> search_history;
    private SearchItemOnClickListener searchItemOnClickListener;

    public SearchHistoryTopAdapter(Context context, List<String> search_history) {
        this.context = context;
        this.search_history = search_history;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_search_history_top, parent,
                false),searchItemOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.search_label.setText(search_history.get(position));
    }

    @Override
    public int getItemCount()
    {
        return search_history.size();
    }

    class MyViewHolder extends ViewHolder implements View.OnClickListener {
        TextView search_label;
        private SearchItemOnClickListener itemOnClickListener;
        public MyViewHolder(View view,SearchItemOnClickListener searchItemOnClickListener)
        {
            super(view);
            search_label = (TextView) view.findViewById(R.id.search_label);
            this.itemOnClickListener = searchItemOnClickListener;
            search_label.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemOnClickListener.onItemClick(v,getPosition());
        }
    }
    public interface SearchItemOnClickListener{
        void onItemClick(View view , int position);
    }
    public void setSearchItemOnClickListener(SearchItemOnClickListener searchItemOnClickListener ){
        this.searchItemOnClickListener = searchItemOnClickListener;
    }
}
