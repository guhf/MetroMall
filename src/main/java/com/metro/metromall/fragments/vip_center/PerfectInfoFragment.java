package com.metro.metromall.fragments.vip_center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class PerfectInfoFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;

    TextView vip_code;
    TextView user_name;
    TextView email;
    TextView reg_date;
    TextView telephone;
    RadioButton man;
    RadioButton woman;
    Button save_info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_perfect_info, container, false);
        initView();
        return view;
    }

    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        vip_code = (TextView)view.findViewById(R.id.vip_code);
        user_name = (TextView)view.findViewById(R.id.user_name);
        email = (TextView)view.findViewById(R.id.email);
        reg_date = (TextView)view.findViewById(R.id.reg_date);
        telephone = (TextView)view.findViewById(R.id.telephone);
        man = (RadioButton)view .findViewById(R.id.man);
        woman = (RadioButton)view .findViewById(R.id.woman);
        save_info = (Button)view.findViewById(R.id.save_info);

        news_title.setText(R.string.perfect_information);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        save_info.setOnClickListener(this);
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


            case R.id.save_info:

                break;
        }
    }
}
