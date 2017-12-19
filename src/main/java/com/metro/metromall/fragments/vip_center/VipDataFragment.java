package com.metro.metromall.fragments.vip_center;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.activities.CommActivity;
import com.metro.metromall.fragments.login.UpdatePasswordFragment;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class VipDataFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;

    RelativeLayout my_info;
    RelativeLayout address;
    RelativeLayout update_phone;
    RelativeLayout update_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_vip_data, container, false);
        initView();
        return view;
    }

    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);

        my_info = (RelativeLayout)view.findViewById(R.id.my_info);
        address = (RelativeLayout)view.findViewById(R.id.address);
        update_phone = (RelativeLayout)view.findViewById(R.id.update_phone);
        update_password = (RelativeLayout)view.findViewById(R.id.update_password);

        news_title.setText(R.string.vip_info);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        my_info.setOnClickListener(this);
        address.setOnClickListener(this);
        update_phone.setOnClickListener(this);
        update_password.setOnClickListener(this);
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
            case R.id.my_info:
                PerfectInfoFragment perfectInfoFragment = new PerfectInfoFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_view,perfectInfoFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.address:
                Intent intentDeliveryAddress = new Intent(getActivity(), CommActivity.class);
                intentDeliveryAddress.putExtra("case","deliveryAddress");
                getActivity().startActivityForResult(intentDeliveryAddress,30);
//                DeliveryAddressFragment deliveryAddressFragment = new DeliveryAddressFragment();
//                getFragmentManager().beginTransaction().replace(R.id.main_view,deliveryAddressFragment)
//                        .addToBackStack("vip").commit();
                break;
            case R.id.update_phone:
                UpdatePhoneFragment updatePhoneFragment = new UpdatePhoneFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_view,updatePhoneFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.update_password:
                UpdatePasswordFragment updatePasswordFragment = new UpdatePasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putString("update_password","true");
                updatePasswordFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main_view,updatePasswordFragment)
                        .addToBackStack("vip").commit();
                break;
        }
    }
}
