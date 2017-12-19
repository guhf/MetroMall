package com.metro.metromall.fragments.orders;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.metro.metromall.R;

public class OrdersFragment extends Fragment implements View.OnClickListener {
    View view;
    LinearLayout null_info;
    Button go_stroll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        initView();
        return view;
    }
    private void initView(){
        null_info = (LinearLayout)view.findViewById(R.id.null_info);
        go_stroll = (Button) view.findViewById(R.id.go_stroll);

//        if (){
//            null_info.setVisibility(View.INVISIBLE);
//        }

        go_stroll.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_stroll:
                getActivity().setResult(30);
                getActivity().finish();
                break;
        }
    }
}
