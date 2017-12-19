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

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class SuccessFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    Button back_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_success, container, false);
        initView();
        return view;
    }
    private void initView(){
        re_search = (RelativeLayout)view.findViewById(R.id.re_search);
        img_back = (ImageView)view.findViewById(R.id.img_back);
        text_back = (TextView)view.findViewById(R.id.text_back);
        news_title = (TextView)view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        back_login = (Button) view.findViewById(R.id.back_login) ;

        news_title.setText(R.string.retrieve_password_success);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        back_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                break;
            case R.id.text_back:
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                break;
            case R.id.back_login:
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
                break;
        }
    }
}
