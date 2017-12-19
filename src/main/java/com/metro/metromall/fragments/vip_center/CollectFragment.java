package com.metro.metromall.fragments.vip_center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class CollectFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    LinearLayout null_info;
    Button go_stroll;
    TextView y_n;
    TextView delete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_collect, container, false);
        initView();
        return view;
    }
    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        null_info = (LinearLayout)view.findViewById(R.id.null_info);
        go_stroll = (Button) view.findViewById(R.id.go_stroll);
        y_n = (TextView)view.findViewById(R.id.y_n);
        delete = (TextView)view.findViewById(R.id.delete);

        news_title.setText(R.string.my_collect);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);
//        if (){
//            null_info.setVisibility(View.INVISIBLE);
//        }

//        if () {
//            y_n.setVisibility(View.VISIBLE);
//            delete.setVisibility(View.VISIBLE);
//        }

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        y_n.setOnClickListener(this);
        delete.setOnClickListener(this);
        go_stroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                hideInputKeyboard(getContext());
                getActivity().finish();
                break;
            case R.id.text_back:
                hideInputKeyboard(getContext());
                getActivity().finish();
                break;
            case R.id.y_n:

                break;
            case R.id.delete:
//                if (){
//                    MyFailToast.makeText(getContext(),"请选择取消收藏的商品", Toast.LENGTH_LONG).show();
//                }
                break;
            case R.id.go_stroll:
                getActivity().setResult(30);
                getActivity().finish();
                break;
        }
    }
}
