package com.metro.metromall.fragments.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.activities.SearchActivity;
import com.metro.metromall.fragments.ClassifyDetailFragment;
import com.metro.metromall.tools.NoScrollViewPager;

public class ClassifyFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    RelativeLayout classify_search;
    TextView classify_search_text;

    public String toolsList[];
    private TextView toolsTextViews[];
    private View views[];
    private LayoutInflater layoutInflater;
    private ScrollView scrollView;
    private int scrollViewWidth = 0, scrollViewMiddle = 0;
    private NoScrollViewPager goods_pager;
    private int currentItem = 0;
    private ClassifyAdapter classifyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_classify, container, false);
        layoutInflater = LayoutInflater.from(getActivity());
        initView();
        showToolsView();
        initPager();
        return view;
    }
    @Override
    public void onResume() {
        super.onStart();
        //showToolsView();
        //initPager();
    }

    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        classify_search = (RelativeLayout)view.findViewById(R.id.classify_search);
        classify_search_text = (TextView)view.findViewById(R.id.classify_search_text);
        scrollView = (ScrollView)view.findViewById(R.id.tools_scrollview);

        news_title.setText(R.string.mer_classify);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);
        img_back.setVisibility(View.INVISIBLE);
        text_back.setVisibility(View.INVISIBLE);
        classify_search.setOnClickListener(this);
        classify_search_text.setText(R.string.limited_time_offers);
    }
    /**
     * 动态生成显示items中的textview
     */
    private void showToolsView() {
        toolsList = new String[]{"常用分类", "潮流女装", "品牌男装", "内衣配饰", "家用电器", "手机数码", "电脑办公", "个护化妆", "母婴频道", "食物生鲜", "酒水饮料", "家居家纺", "整车车品", "鞋靴箱包", "运动户外", "图书", "玩具乐器", "钟表", "居家生活", "珠宝饰品", "音像制品", "家具建材", "计生情趣", "营养保健", "奢侈礼品", "生活服务", "旅游出行"};
        LinearLayout toolsLayout = (LinearLayout) view.findViewById(R.id.tools);
        toolsTextViews = new TextView[toolsList.length];
        views = new View[toolsList.length];

        for (int i = 0; i < toolsList.length; i++) {
            View view = layoutInflater.inflate(R.layout.item_classify_left, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.classify_text);
            textView.setText(toolsList[i]);
            toolsLayout.addView(view);
            toolsTextViews[i] = textView;
            views[i] = view;
        }
        changeTextColor(0);
    }
    /**
     * initPager<br/>
     * 初始化ViewPager控件相关内容
     */
    private void initPager() {
        classifyAdapter = new ClassifyAdapter(getFragmentManager());
        goods_pager = (NoScrollViewPager)view.findViewById(R.id.goods_pager);
        goods_pager.setAdapter(classifyAdapter);
        goods_pager.setOnPageChangeListener(onPageChangeListener);
    }
    //左侧导航栏点击事件
    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goods_pager.setCurrentItem(v.getId());
            changeTextColor(v.getId());
        }
    };
    //监听ViewPager选项卡变化事的事件
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (goods_pager.getCurrentItem() != position) goods_pager.setCurrentItem(position);
            if (currentItem != position) {
                changeTextColor(position);
                changeTextLocation(position);
            }
            currentItem = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                toolsTextViews[i].setBackgroundResource(R.color.white);
                toolsTextViews[i].setTextColor(Color.parseColor("#000000"));
            }
        }
        toolsTextViews[id].setBackgroundResource(R.color.bak);
        toolsTextViews[id].setTextColor(Color.parseColor("#ff0000"));
    }
    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
        scrollView.smoothScrollTo(0, x);
    }
    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }
    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewheight() {
        if (scrollViewWidth == 0)
            scrollViewWidth = scrollView.getBottom() - scrollView.getTop();
        return scrollViewWidth;
    }
    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.classify_search:
                Intent sIntent = new Intent(getActivity(), SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("state","search");
                bundle.putString("search_text",classify_search_text.getText().toString().trim());
                sIntent.putExtras(bundle);
                startActivity(sIntent);
                break;
        }
    }
    /**
     *  ViewPager 加载选项卡
     */
    private class ClassifyAdapter extends FragmentPagerAdapter {

        public ClassifyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ClassifyDetailFragment();
            Bundle bundle = new Bundle();
            String str = toolsList[position];
            bundle.putString("typename", str);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return toolsList.length;
        }
    }

}
