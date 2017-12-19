package com.metro.metromall.fragments.search;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.MainActivity;
import com.metro.metromall.R;
import com.metro.metromall.activities.SearchActivity;
import com.metro.metromall.tools.FragmentBackListener;
import com.metro.metromall.utils.NetWorkUtil;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class SearchResultFragment extends Fragment implements View.OnClickListener, ResultBackListener{
    View view ;
    ImageView search_result_img_back;
    TextView search_result_text;
    ImageView search_result_search;
    LinearLayout goods_list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        initView();
        Loading();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetWorkUtil.ping()){
            goods_list.removeAllViews();
            View notNetWork = getActivity().getLayoutInflater().inflate(R.layout.layout_not_network, null);
            goods_list.addView(notNetWork);
            return;
        }
    }

    private void initView(){
        search_result_img_back = (ImageView)view.findViewById(R.id.search_result_img_back);
        search_result_text = (TextView) view.findViewById(R.id.search_result_text);
        search_result_search = (ImageView)view.findViewById(R.id.search_result_search);
        goods_list = (LinearLayout)view.findViewById(R.id.goods_list);


        search_result_img_back.setOnClickListener(this);
        search_result_text.setOnClickListener(this);
        search_result_search.setOnClickListener(this);

        try {
            String search_text = getActivity().getIntent().getStringExtra("search_text");
            search_result_text.setText(search_text);
        }catch (Exception ex){}
        try{
            String top_search_text = getArguments().getString("search_text");// getActivity().getIntent().getStringExtra("");
            if (!top_search_text.equals("")){
                search_result_text.setText(top_search_text);
            }
        }catch (Exception ex){}

    }

    private void Loading(){

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_result_img_back:
                hideInputKeyboard(getContext());
                getActivity().finish();
                break;
            case R.id.search_result_text:
                ((SearchActivity)getActivity()).setInterception(false);
                SearchFragment searchFragment = new SearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString("last_search_text",search_result_text.getText().toString().trim());
                searchFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frams,searchFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.search_result_search:
                hideInputKeyboard(getContext());
                //
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        if(context instanceof SearchActivity){
            ((SearchActivity)context).setResultBackListener(this);
            ((SearchActivity)context).setInterception(true);
        }
        super.onAttach(context);
    }
        @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if(activity instanceof SearchActivity){
            ((SearchActivity)activity).setResultBackListener(this);
            ((SearchActivity)activity).setInterception(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(getActivity() instanceof SearchActivity){
            ((SearchActivity)getActivity()).setResultBackListener(null);
            ((SearchActivity)getActivity()).setInterception(false);
        }
    }
    @Override
    public void onBackForward() {
        hideInputKeyboard(getContext());
        getActivity().finish();
    }
}
