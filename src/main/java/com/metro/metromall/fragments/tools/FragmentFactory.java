package com.metro.metromall.fragments.tools;

import android.support.v4.app.Fragment;

import com.metro.metromall.R;
import com.metro.metromall.fragments.main.ClassifyFragment;
import com.metro.metromall.fragments.main.HomeFragment;
import com.metro.metromall.fragments.main.MyFragment;
import com.metro.metromall.fragments.main.ShopCarFragment;
import com.metro.metromall.fragments.orders.OrdersFragment;
import com.metro.metromall.fragments.vip_center.FootPrintFragment;

/**
 * fragment的工厂类
 * Created by guhf on 2017/10/18.
 */

public class FragmentFactory {
    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createById(int resId) {
        Fragment fragment = null;
//        switch (resId) {
//            case R.id.home_operation_center:// 运营中心
//                fragment = new OperationCenterFragment();
//                break;
//            case R.id.home_consume_center:// 消费中心
//                fragment = new ConsumeCenterFragment();
//                break;
//            case R.id.home_manager_center:// 管理中心
//                fragment = new ManagerCenterFragment();
//                break;
//            case R.id.home_data_center:// 数据中心
//                fragment = new DataCenterFragment();
//                break;
//        }
        return fragment;
    }

    /**
     * main
     * @param position
     * @return
     */
    public static Fragment createForMain(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:// 主页
                fragment = new HomeFragment();
                break;
            case 1:// 分类
                fragment = new ClassifyFragment();
                break;
            case 2:// 购物车
                fragment = new ShopCarFragment();
                break;
            case 3:// 我的
                fragment = new MyFragment();
                break;
        }
        return fragment;
    }
    /**
     * Orders
     * @param position
     * @return
     */
    public static Fragment createForOrders(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:// 全部
                fragment = new OrdersFragment();
                break;
            case 1:// 待付款
                fragment = new OrdersFragment();
                break;
            case 2:// 待发货
                fragment = new OrdersFragment();
                break;
            case 3:// 待完成
                fragment = new OrdersFragment();
                break;
        }
        return fragment;
    }
}
