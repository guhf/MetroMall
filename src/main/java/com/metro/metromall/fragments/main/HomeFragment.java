package com.metro.metromall.fragments.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;
import com.metro.metromall.activities.GoodsDetailActivity;
import com.metro.metromall.activities.MallActivity;
import com.metro.metromall.activities.NoticeActivity;
import com.metro.metromall.activities.SearchActivity;
import com.metro.metromall.adapter.BannerAdapter;
import com.metro.metromall.adapter.GoodsViewAdapter;
import com.metro.metromall.adapter.MiddleAdapter;
import com.metro.metromall.adapter.QualityRecyclerAdapter;
import com.metro.metromall.fragments.GoodsDetailFragment;
import com.metro.metromall.tools.DividerGridItemDecoration;
import com.metro.metromall.tools.JsonHandler;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.tools.MyScrollView;
import com.metro.metromall.tools.SuperSwipeRefreshLayout;
import com.metro.metromall.tools.ViewAnimatorWordComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 */
public class HomeFragment extends Fragment implements View.OnClickListener,MyScrollView.OnScrollListener, SuperSwipeRefreshLayout.OnPullRefreshListener, SuperSwipeRefreshLayout.OnPushLoadMoreListener {
    View view;
    SuperSwipeRefreshLayout home_super_srl;
    private ProgressBar head_pb_view;
    private TextView head_refresh_text;
    private ImageView head_refresh_image;
    private ProgressBar foot_pb_view;
    private TextView foot_refresh_text;
    MyScrollView my_scroll;
    RelativeLayout home_top_content_one;
    RelativeLayout home_top_content;
    LinearLayout HomeList;
    LinearLayout dots;
    ViewPager mViewPager;
    TextView more_notice;
    TextView mall_name;
    ViewAnimatorWordComponent roll_notice;
    EditText search_text;
    RelativeLayout mall;
    RelativeLayout scanning;
    RecyclerView home_middle_images;
    RecyclerView mai_quality;
    RecyclerView snap_up_recycler;
    RecyclerView high_quality_recycler;
    RecyclerView boutique_recycler;
    RecyclerView own_brand_recycler;
    private int searchLayoutTop;
    //轮播图图片资源
    private List<String> images_url ;
    private List<String> banner_images ;
    BannerAdapter bannerAdapter;
    //促销图片资源
    private List<String> middle_images_url ;
    MiddleAdapter middleAdapter;
    //麦享品质图片资源
    private List<String> mai_quality_images_url ;
    QualityRecyclerAdapter qualityRecyclerAdapter;
    //限量抢图片
    private List<String> snap_up_images_url ;
    //超值优品图片
    private List<String> high_quality_images_url ;
    //精品优选图片
    private List<String> boutique_images_url ;
    //麦德龙自有品牌图片
    private List<String> own_brand_images_url ;
    private List<String> goods_name_url ;
    private List<String> goods_price_url ;
    GoodsViewAdapter goodsViewAdapter;
    private List<String> notices_url ;
    JsonHandler jsonHandler;

    //存放图片数组
    private List<ImageView> banner_images_list;
    //存放小圆点
    private List<ImageView> imgDots;
    //当前索引位置以及上一个索引位置
    private int index = 0,preIndex = 0;
    //是否需要轮播标志
    private boolean isContinue = true;
    //定时器，用于实现轮播
    private Timer timer;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  1:
                    index ++;
                    //mViewPager.setCurrentItem(index%banner_images_list.size());
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        initView();
        setOnClick();
        requestData();
        return view;
    }
    private void initView(){
        home_super_srl = (SuperSwipeRefreshLayout)view.findViewById(R.id.home_super_srl);
        View refresh_head = LayoutInflater.from(home_super_srl.getContext())
                .inflate(R.layout.layout_refresh_head, null);
        head_pb_view = (ProgressBar) refresh_head.findViewById(R.id.head_pb_view);
        head_refresh_text = (TextView) refresh_head.findViewById(R.id.head_refresh_text);
        head_refresh_image = (ImageView) refresh_head.findViewById(R.id.head_refresh_image);
        head_refresh_image.setVisibility(View.VISIBLE);
        head_pb_view.setVisibility(View.GONE);
        head_refresh_text.setText("下拉刷新");
        head_refresh_image.setImageResource(R.mipmap.refresh_down_arrow);
        home_super_srl.setHeaderView(refresh_head);
        View refresh_foot = LayoutInflater.from(home_super_srl.getContext())
                .inflate(R.layout.layout_refresh_foot, null);
        foot_refresh_text = (TextView) refresh_foot.findViewById(R.id.foot_refresh_text);
        foot_pb_view = (ProgressBar) refresh_foot.findViewById(R.id.foot_pb_view);
        foot_refresh_text.setText("正在加载");
        home_super_srl.setFooterView(refresh_foot);

        my_scroll = (MyScrollView)view.findViewById(R.id.my_scroll);
        home_top_content = (RelativeLayout)view.findViewById(R.id.home_top_content);
        mViewPager = (ViewPager)view.findViewById(R.id.mViewPager);
        HomeList = (LinearLayout)view.findViewById(R.id.HomeList);
        more_notice = (TextView)view.findViewById(R.id.more_notice);
        mall_name = (TextView)view.findViewById(R.id.MallName);
        roll_notice = (ViewAnimatorWordComponent)view.findViewById(R.id.roll_notice);
        dots = (LinearLayout) view.findViewById(R.id.dots);
        search_text = (EditText)view.findViewById(R.id.search_text);
        mall = (RelativeLayout)view.findViewById(R.id.mall);
        scanning = (RelativeLayout)view.findViewById(R.id.scanning);
        home_middle_images = (RecyclerView)view.findViewById(R.id.home_middle_images);
        mai_quality = (RecyclerView)view.findViewById(R.id.mai_quality);
        snap_up_recycler = (RecyclerView)view.findViewById(R.id.snap_up_recycler);
        high_quality_recycler = (RecyclerView)view.findViewById(R.id.high_quality_recycler);
        boutique_recycler = (RecyclerView)view.findViewById(R.id.boutique_recycler);
        own_brand_recycler = (RecyclerView)view.findViewById(R.id.own_brand_recycler);

        home_middle_images.addItemDecoration(new DividerGridItemDecoration(getContext()));
        home_middle_images.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //解决RecyclerView与ScrollView滑动冲突问题
        home_middle_images.setNestedScrollingEnabled(false);
        mai_quality.addItemDecoration(new DividerGridItemDecoration(getContext()));
        mai_quality.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //解决RecyclerView与ScrollView滑动冲突问题
        mai_quality.setNestedScrollingEnabled(false);
        snap_up_recycler.addItemDecoration(new DividerGridItemDecoration(getContext()));
        snap_up_recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //解决RecyclerView与ScrollView滑动冲突问题
        snap_up_recycler.setNestedScrollingEnabled(false);
        high_quality_recycler.addItemDecoration(new DividerGridItemDecoration(getContext()));
        high_quality_recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //解决RecyclerView与ScrollView滑动冲突问题
        high_quality_recycler.setNestedScrollingEnabled(false);
        boutique_recycler.addItemDecoration(new DividerGridItemDecoration(getContext()));
        boutique_recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //解决RecyclerView与ScrollView滑动冲突问题
        boutique_recycler.setNestedScrollingEnabled(false);
        own_brand_recycler.addItemDecoration(new DividerGridItemDecoration(getContext()));
        own_brand_recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //解决RecyclerView与ScrollView滑动冲突问题
        own_brand_recycler.setNestedScrollingEnabled(false);

        mall_name.setText(R.string.app_name);
        search_text.setText(R.string.limited_time_offers);
    }
    // 设置监听事件
    private void setOnClick(){
        more_notice.setOnClickListener(this);
        my_scroll.setOnScrollListener(this);
        search_text.setOnClickListener(this);
        mall.setOnClickListener(this);
        scanning.setOnClickListener(this);
        //下拉刷新
        home_super_srl.setOnPullRefreshListener(this);
        //上拉加载
        home_super_srl.setOnPushLoadMoreListener(this);
    }
    //加载数据
    private void requestData(){
        jsonHandler = new JsonHandler(getContext());
        RoastingChartRes();
        HeadLinesRes();
        MiddleRes();
        QualityRes();
        SnapUpRes();
        HighQualityRes();
        BoutiqueRes();
        OwnBrandRes();
    }
    //显示的轮播图图片
    private void RoastingChartRes(){
        banner_images = new ArrayList<String>();
        banner_images = jsonHandler.BannerData();
        imgDots = new ArrayList<ImageView>();
        ImageView dotImage;
        dots.removeAllViews();
        for (int i =0;i<banner_images.size();i++){
            //显示的小点
            dotImage = new ImageView(getActivity());
            dotImage.setImageResource(R.drawable.dot_home_select);
            dotImage.setPadding(8,3,3,3);
            dots.addView(dotImage);
            imgDots.add(dotImage);
        }
        bannerAdapter = new BannerAdapter(getContext(),banner_images);
        mViewPager.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();

        //imgDots.get(100%imgDots.size()).setSelected(true);
        imgDots.get(0).setSelected(true);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imgDots.size(); i++) {
                    if (position % imgDots.size() == i) {
                        imgDots.get(i).setSelected(true);
                    } else {
                        imgDots.get(i).setSelected(false);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        timer = new Timer();
        //执行定时任务
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //首先判断是否需要轮播，是的话我们才发消息
                if (isContinue) {
                    mHandler.sendEmptyMessage(1);
                }
            }
        },3000,3000);//延迟3秒，每隔3秒发一次消息
    }
    //麦头条
    private void HeadLinesRes(){
        notices_url = new ArrayList<String>();
        notices_url = jsonHandler.NoticesData();
        roll_notice.setStrings(notices_url);
        roll_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFailToast.makeText(getContext(),roll_notice.getText() +"--暂无数据", Toast.LENGTH_LONG).show();
            }
        });
    }
    //促销
    private void MiddleRes(){
        middle_images_url = new ArrayList<String>();
        middle_images_url = jsonHandler.MiddleData();
        middleAdapter = new MiddleAdapter(getContext(),middle_images_url);
        home_middle_images.setAdapter(middleAdapter);
        //更新数据
        middleAdapter.notifyDataSetChanged();
        middleAdapter.setMiddleItemOnClickListener(new MiddleAdapter.MiddleItemOnClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("state","result");
                bundle.putString("search_text",middle_images_url.get(position));
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
    }
    //麦享品质
    private void QualityRes(){
        mai_quality_images_url = new ArrayList<String>();
        mai_quality_images_url = jsonHandler.MaiQualityData();
        qualityRecyclerAdapter = new QualityRecyclerAdapter(getContext(),mai_quality_images_url);
        mai_quality.setAdapter(qualityRecyclerAdapter);
        //更新数据
        qualityRecyclerAdapter.notifyDataSetChanged();
        qualityRecyclerAdapter.setQualityItemOnClickListener(new QualityRecyclerAdapter.QualityItemOnClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("state","result");
                bundle.putString("search_text",mai_quality_images_url.get(position));
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
    }
    //限量抢
    private void SnapUpRes(){
        snap_up_images_url = new ArrayList<String>();
        snap_up_images_url = jsonHandler.GoodsImageData();
        goods_name_url = new ArrayList<String>();
        goods_name_url = jsonHandler.GoodsNameData();
        goods_price_url = new ArrayList<String>();
        goods_price_url = jsonHandler.GoodsPriceData();
        goodsViewAdapter = new GoodsViewAdapter(getContext(),snap_up_images_url,goods_name_url,goods_price_url);
        snap_up_recycler.setAdapter(goodsViewAdapter);
        //更新数据
        goodsViewAdapter.notifyDataSetChanged();
        goodsViewAdapter.setGoodsItemOnClickListener(new GoodsViewAdapter.GoodsItemOnClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("goods_name",goods_name_url.get(position));
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent,103);
            }
        });
    }
    //超值优品
    private void HighQualityRes(){
        snap_up_images_url = new ArrayList<String>();
        snap_up_images_url = jsonHandler.GoodsImageData();
        goods_name_url = new ArrayList<String>();
        goods_name_url = jsonHandler.GoodsNameData();
        goods_price_url = new ArrayList<String>();
        goods_price_url = jsonHandler.GoodsPriceData();

        goodsViewAdapter = new GoodsViewAdapter(getContext(),snap_up_images_url,goods_name_url,goods_price_url);
        high_quality_recycler.setAdapter(goodsViewAdapter);
        //更新数据
        goodsViewAdapter.notifyDataSetChanged();
        goodsViewAdapter.setGoodsItemOnClickListener(new GoodsViewAdapter.GoodsItemOnClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("goods_name",goods_name_url.get(position));
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent,103);
            }
        });
    }
    //精品优选
    private void BoutiqueRes(){
        snap_up_images_url = new ArrayList<String>();
        snap_up_images_url = jsonHandler.GoodsImageData();
        goods_name_url = new ArrayList<String>();
        goods_name_url = jsonHandler.GoodsNameData();
        goods_price_url = new ArrayList<String>();
        goods_price_url = jsonHandler.GoodsPriceData();

        goodsViewAdapter = new GoodsViewAdapter(getContext(),snap_up_images_url,goods_name_url,goods_price_url);
        boutique_recycler.setAdapter(goodsViewAdapter);
        //更新数据
        goodsViewAdapter.notifyDataSetChanged();
        goodsViewAdapter.setGoodsItemOnClickListener(new GoodsViewAdapter.GoodsItemOnClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("goods_name",goods_name_url.get(position));
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent,103);
            }
        });
    }
    //麦德龙自有品牌
    private void OwnBrandRes(){
        snap_up_images_url = new ArrayList<String>();
        snap_up_images_url = jsonHandler.GoodsImageData();
        goods_name_url = new ArrayList<String>();
        goods_name_url = jsonHandler.GoodsNameData();
        goods_price_url = new ArrayList<String>();
        goods_price_url = jsonHandler.GoodsPriceData();

        goodsViewAdapter = new GoodsViewAdapter(getContext(),snap_up_images_url,goods_name_url,goods_price_url);
        own_brand_recycler.setAdapter(goodsViewAdapter);
        //更新数据
        goodsViewAdapter.notifyDataSetChanged();
        goodsViewAdapter.setGoodsItemOnClickListener(new GoodsViewAdapter.GoodsItemOnClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                Bundle bundle =  new Bundle();
                bundle.putString("goods_name",goods_name_url.get(position));
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent,103);
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_notice:
                Intent nIntent = new Intent(getActivity(),NoticeActivity.class);
                startActivity(nIntent);
                break;
            case R.id.search_text:
                Intent sIntent = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("state","search");
                bundle.putString("search_text",search_text.getText().toString().trim());
                sIntent.putExtras(bundle);
                startActivity(sIntent);
                break;
            case R.id.mall:
                Intent mIntent = new Intent(getActivity(), MallActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        if(isInMultiWindowMode){
            searchLayoutTop = HomeList.getBottom();//获取searchLayout的顶部位置
        }
    }

    @Override
    public void onScroll(int scrollY) {  }

    //监听滚动Y值变化
    @Override
    public void onScrollChange(MyScrollView myScrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            //向下滑动逐渐变色  向上滑动顶部逐渐变为原来颜色
            home_top_content.setBackgroundColor(Color.argb((int) 0, 0, 56, 148));
        } else if (y > 0 && y <= 180) {
            float scale = (float) y / 180;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            home_top_content.setBackgroundColor(Color.argb((int) alpha, 0, 56, 148));
        } else {
            home_top_content.setBackgroundColor(Color.argb((int) 255, 0, 56, 148));
        }
        //向下滚动时顶部消失
//        if(scrollY > searchLayoutTop) {
//            home_top_content.setBackgroundColor(Color.parseColor("#003894"));
//        }
//        if (scrollY==searchLayoutTop) {
//            //设置成透明色
//            home_top_content.setBackgroundResource(Color.TRANSPARENT);
//        }
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        head_refresh_text.setText("正在刷新");
        head_refresh_image.setVisibility(View.GONE);
        head_pb_view.setVisibility(View.VISIBLE);

        timer.cancel();
        timer = null;
        requestData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                home_super_srl.setRefreshing(false);
                head_pb_view.setVisibility(View.GONE);
            }
        }, 1000);
    }
    @Override
    public void onPullDistance(int distance) {
        if (distance > 0) {
            home_top_content.setVisibility(View.GONE);
        } else {
            home_top_content.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onPullEnable(boolean enable) {
        head_refresh_text.setText(enable ? "松开刷新" : "下拉刷新");
        head_refresh_image.setVisibility(View.VISIBLE);
        head_refresh_image.setRotation(enable ? 180 : 0);
    }
    //上拉加载
    @Override
    public void onLoadMore() {
        foot_refresh_text.setText("正在加载");
        foot_pb_view.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                home_super_srl.setLoadMore(false);
            }
        }, 2000);
    }

    @Override
    public void onPushDistance(int distance) {
    }
    @Override
    public void onPushEnable(boolean enable) {
        foot_refresh_text.setText(enable ? "松开加载" :"上拉加载更多数据");
        foot_pb_view.setVisibility(View.INVISIBLE);
    }
}
