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

public class FootPrintFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    LinearLayout null_info;
    Button go_stroll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_foot_print, container, false);
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

        news_title.setText(R.string.my_footprint);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);
//        if (){
//            null_info.setVisibility(View.INVISIBLE);
//        }

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        go_stroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                hideInputKeyboard(getContext());
                getActivity().finish();
                break;
            case R.id.text_back:
                hideInputKeyboard(getContext());
                getActivity().finish();
                break;
            case R.id.go_stroll:
                getActivity().setResult(30);
                getActivity().finish();
                break;
        }
    }
}
