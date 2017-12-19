package com.metro.metromall.fragments.orders;

import android.content.Context;
import android.widget.HorizontalScrollView;

/**
 * Created by guhf on 2017/11/9.
 */

public class WView extends HorizontalScrollView {
    private int scrollOffset = 10;// 当显示多个tab时，底部导航线距离左侧的位移,默认是10，请不要太小
    private int lastScrollX = 0;

    public WView(Context context) {
        super(context);
    }

//    private void scrollToChild(int position, int offset) {
//        if (offset == 0) {
//            return;
//        }
//
//        int newScrollX = line_bottom.getLeft() + offset;
//
//        if (position > 0 || offset > 0) {
//            newScrollX -= scrollOffset;
//        }
//
//        if (newScrollX != lastScrollX) {
//            lastScrollX = newScrollX;
//            scrollTo(newScrollX, 0);
//        }
//
//    }
}
