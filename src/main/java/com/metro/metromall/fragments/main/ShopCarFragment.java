package com.metro.metromall.fragments.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;


public class ShopCarFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView editor;
    TextView logout;
    //空购物车
    LinearLayout null_shop_car;
    Button stroll;
    //非空
    RelativeLayout shop_car;
    CheckBox cb_all_check;
    TextView price;
    TextView discount_weight;
    TextView clearing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_car, container, false);
        initView();
        return view; //scroll_home_title
    }
    private void initView(){
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        editor = (TextView)view.findViewById(R.id.editor);
        logout = (TextView)view.findViewById(R.id.logout);

        null_shop_car = (LinearLayout)view.findViewById(R.id.null_shop_car);
        stroll = (Button)view.findViewById(R.id.stroll);
        shop_car =(RelativeLayout)view.findViewById(R.id.shop_car);
        cb_all_check = (CheckBox)view.findViewById(R.id.cb_all_check);
        price = (TextView)view.findViewById(R.id.price);
        discount_weight = (TextView)view.findViewById(R.id.discount_weight);
        clearing = (TextView)view.findViewById(R.id.clearing);


        news_title.setText(R.string.shop_car);
        re_search.setVisibility(View.INVISIBLE);
        img_back.setVisibility(View.INVISIBLE);
        text_back.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);

        //if (true){
            //editor.setVisibility(View.VISIBLE);
            //null_shop_car.setVisibility(View.INVISIBLE);
       // }else{
            shop_car.setVisibility(View.INVISIBLE);
        // }
        stroll.setOnClickListener(this);
        cb_all_check.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stroll:
//                AddAddressFragment addAddressFragment = new AddAddressFragment();
//                getFragmentManager().beginTransaction().replace(R.id.main_view,addAddressFragment)
//                        .addToBackStack("vip").commit();
//
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.main_pager,new HomeFragment()).commit();
                break;
            case R.id.cb_all_check:
                allCheck();
                break;
        }
    }

    /**
     * 全选 结算
     */
    private void allCheck(){
        if (cb_all_check.isChecked() == true){
            clearing.setText("结算(10)");
            clearing.setTextColor(Color.parseColor("#F1E03A"));
            clearing.setBackgroundColor(Color.parseColor("#003894"));
            price.setText("¥189.00");
            discount_weight.setText("已优惠¥10.5  重量5.20kg");
        }else {
            clearing.setText("结算(0)");
            clearing.setTextColor(Color.parseColor("#FFFFFF"));
            clearing.setBackgroundColor(Color.parseColor("#CCCCCC"));
            price.setText("¥0");
            discount_weight.setText("已优惠¥0  重量0kg");
        }
    }
}
