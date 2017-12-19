package com.metro.metromall.fragments.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.activities.SearchActivity;
import com.metro.metromall.adapter.SearchHistoryTopAdapter;
import com.metro.metromall.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class SearchFragment extends Fragment implements View.OnClickListener, TextWatcher, SearchBackListener {
    View view ;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    EditText search_text;
    ImageView search;
    ImageView clear;
    TextView logout;

    RelativeLayout recent_search;
    View recent_search_view;
    View line_view;
    RelativeLayout top_search;
    View top_search_view;
    TextView empty_search_history;
    TextView in_batch;
    RecyclerView history_list;
    RecyclerView top_list;
    SearchHistoryTopAdapter historyTopAdapter;
    SearchHandler searchHandler;
    List<String> top_search_data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        initView();
//        view.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
//        view.setFocusableInTouchMode(true);
//        view.setOnKeyListener(backListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //弹出软键盘
        Utils.showSoftInputFromWindow(getActivity(),search_text);
    }

    private void initView(){
        img_back = (ImageView)view.findViewById(R.id.img_back);
        text_back = (TextView)view.findViewById(R.id.text_back);
        news_title = (TextView)view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        search_text = (EditText)view.findViewById(R.id.search_text);
        search = (ImageView)view.findViewById(R.id.search);
        clear = (ImageView)view.findViewById(R.id.clear);
        recent_search = (RelativeLayout)view.findViewById(R.id.recent_search);
        recent_search_view = view.findViewById(R.id.recent_search_view);
        line_view = view.findViewById(R.id.line_view);
        top_search = (RelativeLayout)view.findViewById(R.id.top_search);
        top_search_view = view.findViewById(R.id.top_search_view);
        empty_search_history = (TextView)view.findViewById(R.id.empty_search_history);
        in_batch = (TextView)view.findViewById(R.id.in_batch);
        history_list = (RecyclerView)view.findViewById(R.id.history_list);
        top_list = (RecyclerView)view.findViewById(R.id.top_list);

        text_back.setVisibility(View.INVISIBLE);
        news_title.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);
        clear.setVisibility(View.GONE);
        recent_search_view.setVisibility(View.INVISIBLE);
        empty_search_history.setVisibility(View.INVISIBLE);
        img_back.setOnClickListener(this);
        search.setOnClickListener(this);
        clear.setOnClickListener(this);
        recent_search.setOnClickListener(this);
        top_search.setOnClickListener(this);
        empty_search_history.setOnClickListener(this);
        in_batch.setOnClickListener(this);
        search_text.addTextChangedListener(this);
        String top_search_text = getActivity().getIntent().getStringExtra("search_text");
        search_text.setHint(top_search_text);
        try{
            String last_search_text = getArguments().getString("last_search_text");
            if (!last_search_text.equals("")){
                search_text.setText(last_search_text);
            }
        }catch (Exception ex){

        }
        searchHandler = new SearchHandler(getContext());
        showData();
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                //弹出软键盘
//                Utils.showSoftInputFromWindow(getActivity(),search_text);
//            }
//        },1000);
    }
    private void showData(){
        if (searchHandler.selectData().size() < 1){
            recent_search.setVisibility(View.GONE);
            line_view.setVisibility(View.GONE);
            top_search_view.setVisibility(View.GONE);
            empty_search_history.setVisibility(View.GONE);
            showTopData();
        }else{
            showSearchData();
        }
    }
    /**
     * 显示查询历史数据
     */
    private void showSearchData() {
        historyTopAdapter = new SearchHistoryTopAdapter(getContext(),searchHandler.selectData());
        history_list.setAdapter(historyTopAdapter);
        history_list.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //有问题多次选择会使Item变小
        //history_list.addItemDecoration(new DividerGridItemDecoration(getContext()));
        recent_search_view.setVisibility(View.VISIBLE);
        empty_search_history.setVisibility(View.VISIBLE);
        top_search_view.setVisibility(View.INVISIBLE);
        in_batch.setVisibility(View.INVISIBLE);
        historyTopAdapter.setSearchItemOnClickListener(new SearchHistoryTopAdapter.SearchItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((SearchActivity)getActivity()).setInterception(true);
                String text = searchHandler.selectData().get(position).toString();
                //搜索记录存入数据库
                searchHandler.insertData(text);
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString("search_text",text);
                searchResultFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frams,searchResultFragment).commit();
            }
        });
    }
    /**
     *显示热门搜索数据
     */
    private void showTopData(){
        top_search_data = new ArrayList<String>();
        top_search_data.add("热门搜索1");
        top_search_data.add("热门搜索2");
        top_search_data.add("热门搜索3");

        //historyTopAdapter.notifyDataSetChanged();
        historyTopAdapter = new SearchHistoryTopAdapter(getContext(),top_search_data);
        top_list.setAdapter(historyTopAdapter);
        top_list.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //top_list.addItemDecoration(new DividerGridItemDecoration(getContext()));
        historyTopAdapter.setSearchItemOnClickListener(new SearchHistoryTopAdapter.SearchItemOnClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((SearchActivity)getActivity()).setInterception(true);
                String text = top_search_data.get(position).toString();
                //搜索记录存入数据库
                searchHandler.insertData(text);
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString("search_text",text);
                searchResultFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frams,searchResultFragment).commit();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                if (getFragmentManager().getBackStackEntryCount() > 0 ){
                    ((SearchActivity)getActivity()).setInterception(true);
                    hideInputKeyboard(getContext());
                    getFragmentManager().popBackStack();
                }else {
                    hideInputKeyboard(getContext());
                    getActivity().finish();
                }
                break;
            case R.id.clear:
                search_text.getText().clear();
                break;
            case R.id.recent_search:
                recent_search_view.setVisibility(View.VISIBLE);
                empty_search_history.setVisibility(View.VISIBLE);
                top_search_view.setVisibility(View.INVISIBLE);
                history_list.setVisibility(View.VISIBLE);
                in_batch.setVisibility(View.INVISIBLE);
                top_list.setVisibility(View.INVISIBLE);
                showSearchData();
                break;
            case R.id.top_search:
                if (recent_search.getVisibility() != View.GONE){
                    recent_search_view.setVisibility(View.INVISIBLE);
                    empty_search_history.setVisibility(View.INVISIBLE);
                    top_list.setVisibility(View.VISIBLE);
                    top_search_view.setVisibility(View.VISIBLE);
                    in_batch.setVisibility(View.VISIBLE);
                    history_list.setVisibility(View.INVISIBLE);
                }
                showTopData();
                break;
            case R.id.empty_search_history:
                //清空搜索历史
                searchHandler.deleteData();
                recent_search.setVisibility(View.GONE);
                line_view.setVisibility(View.GONE);
                top_search_view.setVisibility(View.GONE);
                empty_search_history.setVisibility(View.GONE);
                in_batch.setVisibility(View.VISIBLE);
                showTopData();
                break;
            case R.id.in_batch://换一批


                break;
            //搜索
            case R.id.search:
                ((SearchActivity)getActivity()).setInterception(true);
                String text = search_text.getText().toString().trim();
                String hint = search_text.getHint().toString().trim();
                //搜索记录存入数据库
                searchHandler.insertData(text.equals("") ? hint : text);

                hideInputKeyboard(getContext());
                SearchResultFragment searchResultFragment = new SearchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putString("search_text",text.equals("") ? hint : text);
                searchResultFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frams,searchResultFragment).commit();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (search_text.getText().toString().trim().equals("")){
            clear.setVisibility(View.GONE);
        }else {
            clear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof  SearchActivity){
            ((SearchActivity)context).setSearchBackListener(this);
            ((SearchActivity)context).setSearch(true);
        }
        super.onAttach(context);
    }
        @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if(activity instanceof SearchActivity){
            ((SearchActivity)activity).setSearchBackListener(this);
            ((SearchActivity)activity).setSearch(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(getActivity() instanceof SearchActivity){
            ((SearchActivity)getActivity()).setSearchBackListener(null);
            ((SearchActivity)getActivity()).setSearch(false);
        }
    }
    @Override
    public void onBackForward() {
        ((SearchActivity)getActivity()).setInterception(true);
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            hideInputKeyboard(getContext());
            getFragmentManager().popBackStack();
        }else {
            hideInputKeyboard(getContext());
            getActivity().finish();
        }
    }
//    private View.OnKeyListener backListener = new View.OnKeyListener() {
//        @Override
//        public boolean onKey(View v, int keyCode, KeyEvent event) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN){
//                if (keyCode == KeyEvent.KEYCODE_BACK){
//                    hideInputKeyboard(getContext());
//                    getFragmentManager().popBackStack();
//                    return true;//已处理
//                }
//            }
//            return false;
//        }
//    };
}
