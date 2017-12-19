package com.metro.metromall.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by guhf on 2017/11/24.
 */

public class CommonUtil {

    /**
     * 状态栏设置为透明(在setContentView()方法之前使用)
     */
    public static void setTranslucentStatus(Activity activity) {
        // 4.4以上支持
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 透明导航栏
//            activity.getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            SystemStatusManager tintManager = new activity.SystemStatusManager(activity);
//            tintManager.setStatusBarTintEnabled(true);
            // // 设置状态栏的颜色
            // tintManager.setStatusBarTintResource(R.color.trans);
            // getWindow().getDecorView().setFitsSystemWindows(true);
        }
        // 5.0以上支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }
}
