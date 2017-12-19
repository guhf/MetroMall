package com.metro.metromall.fragments.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.fragments.vip_center.PerfectInfoFragment;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class RegSuccessFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    Button perfect_information;
    Button go_mall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_reg_success, container, false);
        initView();
        return view;
    }
    private void initView(){
        re_search = (RelativeLayout)view.findViewById(R.id.re_search);
        img_back = (ImageView)view.findViewById(R.id.img_back);
        text_back = (TextView)view.findViewById(R.id.text_back);
        news_title = (TextView)view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        perfect_information = (Button) view.findViewById(R.id.perfect_information) ;
        go_mall = (Button) view.findViewById(R.id.go_mall) ;

        news_title.setText(R.string.phone_reg);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        perfect_information.setOnClickListener(this);
        go_mall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                break;
            case R.id.text_back:
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                break;
            case R.id.perfect_information:
                hideInputKeyboard(getContext());
                PerfectInfoFragment perfectInfoFragment = new PerfectInfoFragment();
                getFragmentManager().beginTransaction().replace(R.id.frams,perfectInfoFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.go_mall:
                hideInputKeyboard(getContext());
                getActivity().finish();
                break;
        }
    }
}
