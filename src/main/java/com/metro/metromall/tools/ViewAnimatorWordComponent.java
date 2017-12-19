package com.metro.metromall.tools;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.metro.metromall.R;

import java.util.List;

/**
 * Created by guhf on 2017/11/24.
 */

public class ViewAnimatorWordComponent extends RelativeLayout {
    private ViewAnimator viewAnimator;
    private final int MSG_CODE = 0x667;
    private final int TIMER_INTERVAL = 3000;
    private List<String> strings;
    TextView textView;

    public void setStrings(List<String> strings) {
        this.strings = strings;
        if(strings != null){
            //先清除再添加
            viewAnimator.removeAllViews();
            for (int i = 0; i < strings.size(); i++) {
                textView = new TextView(getContext());
                textView.setText(strings.get(i));
                //任意设置你的文字样式，在这里
                textView.setTextColor(Color.parseColor("#333333"));// getResources().getColor("#333333"));
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER_VERTICAL);
                viewAnimator.addView(textView,i);
            }
        }
    }
    public String getText(){
        //getDisplayedChild () 返回当前显示的子视图的索引
        String item = strings.get(viewAnimator.getDisplayedChild()).toString();
        return  item;
    }

    public ViewAnimatorWordComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimator = new ViewAnimator(getContext());
        viewAnimator.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(viewAnimator);
        Message message = handler.obtainMessage(MSG_CODE);
        handler.sendMessageDelayed(message,TIMER_INTERVAL);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == MSG_CODE){
                viewAnimator.setOutAnimation(getContext(),R.anim.slide_out_up);
                viewAnimator.setInAnimation(getContext(), R.anim.slide_in_down);
                viewAnimator.showNext();
                Message message = handler.obtainMessage(MSG_CODE);
                handler.sendMessageDelayed(message,TIMER_INTERVAL);
            }
        }
    };
}
