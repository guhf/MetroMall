package com.metro.metromall.fragments.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.activities.CommActivity;
import com.metro.metromall.activities.LoginActivity;
import com.metro.metromall.activities.OrdersActivity;
import com.metro.metromall.fragments.vip_center.FeedBackFragment;
import com.metro.metromall.fragments.vip_center.PolicyFragment;
import com.metro.metromall.fragments.vip_center.VipDataFragment;
import com.metro.metromall.tools.SuperSwipeRefreshLayout;
import com.metro.metromall.utils.SharePUtil;
import com.metro.metromall.tools.MyDialog;
import com.metro.metromall.tools.RoundImageView;

public class MyFragment extends Fragment implements View.OnClickListener, SuperSwipeRefreshLayout.OnPullRefreshListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    SuperSwipeRefreshLayout my_super_srl;
    private ProgressBar head_pb_view;
    private TextView head_refresh_text;
    private ImageView head_refresh_image;
    RoundImageView ic_heads;
    TextView vip_account;
    LinearLayout wait_payment;
    LinearLayout wait_delivery;
    LinearLayout my_orders;
    LinearLayout coupons;
    LinearLayout my_collect;
    LinearLayout my_footprint;
    LinearLayout service_center;
    LinearLayout feedback;
    LinearLayout policy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_my, container, false);
        initView();
        return view;
    }
    private void initView(){
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        my_super_srl = (SuperSwipeRefreshLayout)view.findViewById(R.id.my_super_srl);
        View refresh_head = LayoutInflater.from(my_super_srl.getContext())
                .inflate(R.layout.layout_refresh_head, null);
        head_pb_view = (ProgressBar) refresh_head.findViewById(R.id.head_pb_view);
        head_refresh_text = (TextView) refresh_head.findViewById(R.id.head_refresh_text);
        head_refresh_image = (ImageView) refresh_head.findViewById(R.id.head_refresh_image);
        head_refresh_image.setVisibility(View.VISIBLE);
        head_pb_view.setVisibility(View.GONE);
        head_refresh_text.setText("下拉刷新");
        head_refresh_image.setImageResource(R.mipmap.refresh_down_arrow);
        my_super_srl.setHeaderView(refresh_head);
        //设置下拉时，被包含的View是否随手指的移动而移动
        my_super_srl.setTargetScrollWithLayout(true);

        ic_heads = (RoundImageView)view.findViewById(R.id.ic_heads);
        vip_account = (TextView) view.findViewById(R.id.vip_account);
        wait_payment = (LinearLayout)view.findViewById(R.id.wait_payment);
        wait_delivery = (LinearLayout)view.findViewById(R.id.wait_delivery);
        my_orders = (LinearLayout)view.findViewById(R.id.my_orders);
        coupons = (LinearLayout)view.findViewById(R.id.coupons);
        my_collect = (LinearLayout)view.findViewById(R.id.my_collect);
        my_footprint = (LinearLayout)view.findViewById(R.id.my_footprint);
        service_center = (LinearLayout)view.findViewById(R.id.service_center);
        feedback = (LinearLayout)view.findViewById(R.id.feedback);
        policy = (LinearLayout)view.findViewById(R.id.policy);

        news_title.setText(R.string.vip_center);
        re_search.setVisibility(View.INVISIBLE);
        img_back.setVisibility(View.INVISIBLE);
        text_back.setVisibility(View.INVISIBLE);

        logout.setOnClickListener(this);
        //下拉刷新
        my_super_srl.setOnPullRefreshListener(this);
        ic_heads.setOnClickListener(this);
        vip_account.setOnClickListener(this);
        wait_payment.setOnClickListener(this);
        wait_delivery.setOnClickListener(this);
        my_orders.setOnClickListener(this);
        coupons.setOnClickListener(this);
        my_collect.setOnClickListener(this);
        my_footprint.setOnClickListener(this);
        service_center.setOnClickListener(this);
        feedback.setOnClickListener(this);
        policy.setOnClickListener(this);
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                logout();
                break;
            case R.id.ic_heads:
                VipDataFragment vipDataFragment = new VipDataFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_view,vipDataFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.vip_account:
                VipDataFragment vipDFragment = new VipDataFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_view,vipDFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.wait_payment:
                Intent intentPaymentPending = new Intent(getActivity(), OrdersActivity.class);
                intentPaymentPending.putExtra("selector","one");
                getActivity().startActivityForResult(intentPaymentPending,30);
                break;
            case R.id.wait_delivery:
                Intent intentAwaitingDelivery = new Intent(getActivity(), OrdersActivity.class);
                intentAwaitingDelivery.putExtra("selector","two");
                getActivity().startActivityForResult(intentAwaitingDelivery,30);
                break;
            case R.id.my_orders:
                Intent intentAllOrder = new Intent(getActivity(), OrdersActivity.class);
                intentAllOrder.putExtra("selector","three");
                getActivity().startActivityForResult(intentAllOrder,30);
                break;
            case R.id.coupons:
                Intent intentCoupons = new Intent(getActivity(), CommActivity.class);
                intentCoupons.putExtra("case","coupons");
                getActivity().startActivityForResult(intentCoupons,30);
//                CouponsFragment couponsFragment = new CouponsFragment();
//                getFragmentManager().beginTransaction().replace(R.id.main_view,couponsFragment)
//                        .addToBackStack("vip").commit();
                break;
            case R.id.my_collect:
                Intent intentCollect = new Intent(getActivity(), CommActivity.class);
                intentCollect.putExtra("case","collect");
                getActivity().startActivityForResult(intentCollect,30);
//                CollectFragment collectFragment = new CollectFragment();
//                getFragmentManager().beginTransaction().replace(R.id.main_view,collectFragment)
//                        .addToBackStack("vip").commit();
                break;
            case R.id.my_footprint:
                Intent intentFootPrint = new Intent(getActivity(), CommActivity.class);
                intentFootPrint.putExtra("case","footPrint");
                getActivity().startActivityForResult(intentFootPrint,30);
//                FootPrintFragment footPrintFragment = new FootPrintFragment();
//                getFragmentManager().beginTransaction().replace(R.id.main_view,footPrintFragment)
//                        .addToBackStack("vip").commit();
                break;
            case R.id.service_center:
                call("4001518518");
                break;
            case R.id.feedback:
                FeedBackFragment feedBackFragment = new FeedBackFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_view,feedBackFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.policy:
                PolicyFragment policyFragment = new PolicyFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_view,policyFragment)
                        .addToBackStack("vip").commit();
                break;
        }
    }

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /**
     * 注销
     */
    private void logout(){
        MyDialog.Builder builder = new MyDialog.Builder(getActivity());
        builder.setMessage("确定注销登录？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SharePUtil.remove(getContext(),"metro_sp","login_state");

                Intent intent = new Intent(getActivity(),LoginActivity.class);
                intent.putExtra("state","logout");
                getActivity().startActivityForResult(intent,0);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.createNoTitleDialog().show();
    }

    @Override
    public void onRefresh() {
        head_refresh_text.setText("正在刷新");
        head_refresh_image.setVisibility(View.GONE);
        head_pb_view.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                my_super_srl.setRefreshing(false);
                head_pb_view.setVisibility(View.GONE);
            }
        }, 2000);
    }

    @Override
    public void onPullDistance(int distance) {

    }

    @Override
    public void onPullEnable(boolean enable) {
        head_refresh_text.setText(enable ? "松开刷新" : "下拉刷新");
        head_refresh_image.setVisibility(View.VISIBLE);
        head_refresh_image.setRotation(enable ? 180 : 0);
    }
}
