package com.metro.metromall.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;
import com.metro.metromall.adapter.NoticeListAdapter;
import com.metro.metromall.tools.DividerGridItemDecoration;
import com.metro.metromall.tools.JsonHandler;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.tools.SuperSwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;

public class NoticeFragment extends Fragment implements View.OnClickListener,SuperSwipeRefreshLayout.OnPullRefreshListener {
    SuperSwipeRefreshLayout notice_swp;
    private ProgressBar head_pb_view;
    private TextView head_refresh_text;
    private ImageView head_refresh_image;
    private ProgressBar foot_pb_view;
    private TextView foot_refresh_text;
    LinearLayout notice_form;
    View view ;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    RecyclerView notice_list;
    List<String> notices_url;
    JsonHandler jsonHandler;
    NoticeListAdapter noticeListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_notice, container, false);
        initView();
        loadingData();
        return view;
    }
    private void initView(){
        notice_form = (LinearLayout)view.findViewById(R.id.notice_form);
        notice_swp = (SuperSwipeRefreshLayout)view.findViewById(R.id.notice_swp);
        View refresh_head = LayoutInflater.from(notice_swp.getContext())
                .inflate(R.layout.layout_refresh_head, null);
        head_pb_view = (ProgressBar) refresh_head.findViewById(R.id.head_pb_view);
        head_refresh_text = (TextView) refresh_head.findViewById(R.id.head_refresh_text);
        head_refresh_image = (ImageView) refresh_head.findViewById(R.id.head_refresh_image);
        head_refresh_image.setVisibility(View.VISIBLE);
        head_pb_view.setVisibility(View.GONE);
        head_refresh_text.setText("下拉刷新");
        head_refresh_image.setImageResource(R.mipmap.refresh_down_arrow);
        notice_swp.setHeaderView(refresh_head);

//        View refresh_foot = LayoutInflater.from(notice_swp.getContext())
//                .inflate(R.layout.layout_refresh_foot, null);
//        foot_refresh_text = (TextView) refresh_foot.findViewById(R.id.foot_refresh_text);
//        foot_pb_view = (ProgressBar) refresh_foot.findViewById(R.id.foot_pb_view);
//        foot_refresh_text.setText("正在加载");
//        notice_swp.setFooterView(refresh_foot);

        re_search = (RelativeLayout)view.findViewById(R.id.re_search);
        img_back = (ImageView)view.findViewById(R.id.img_back);
        text_back = (TextView)view.findViewById(R.id.text_back);
        news_title = (TextView)view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        notice_list = (RecyclerView)view.findViewById(R.id.notice_list);

        notice_list.addItemDecoration(new DividerGridItemDecoration(getContext()));


        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);
        news_title.setText(R.string.notice_list);
        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        notice_swp.setOnPullRefreshListener(this);
    }
    private void loadingData(){
        jsonHandler = new JsonHandler(getContext());
        notices_url = new ArrayList<String>();
        notices_url = jsonHandler.NoticesData();
        noticeListAdapter = new NoticeListAdapter(getContext(),notices_url);
        notice_list.setAdapter(noticeListAdapter);
        notice_list.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        noticeListAdapter.setNoticeItemOnClickListener(new NoticeListAdapter.NoticeItemOnClickListener() {
            @Override
            public void itemClick(View view, int position) {
                MyFailToast.makeText(getContext(),notice_form.getMeasuredHeight()+"--暂无数据", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                getActivity().finish();
                break;
            case R.id.text_back :
                getActivity().finish();
                break;
        }
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        head_refresh_text.setText("正在刷新");
        head_refresh_image.setVisibility(View.GONE);
        head_pb_view.setVisibility(View.VISIBLE);

        loadingData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notice_swp.setRefreshing(false);
                head_pb_view.setVisibility(View.GONE);
            }
        }, 1000);
    }

    @Override
    public void onPullDistance(int distance) { }

    @Override
    public void onPullEnable(boolean enable) {
        head_refresh_text.setText(enable ? "松开刷新" : "下拉刷新");
        head_refresh_image.setVisibility(View.VISIBLE);
        head_refresh_image.setRotation(enable ? 180 : 0);
    }
//    //上拉加载
//    @Override
//    public void onLoadMore() {
//        foot_refresh_text.setText("正在加载");
//        foot_pb_view.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                notice_swp.setLoadMore(false);
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void onPushDistance(int distance) {
//    }
//    @Override
//    public void onPushEnable(boolean enable) {
//        foot_refresh_text.setText(enable ? "松开加载" :"上拉加载更多数据");
//        foot_pb_view.setVisibility(View.INVISIBLE);
//    }
}
