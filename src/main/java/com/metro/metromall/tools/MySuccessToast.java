package com.metro.metromall.tools;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;

/**
 * Created by guhf on 2017/10/23.
 */

public class MySuccessToast {
    private Toast mToast;

    private MySuccessToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.toast_success, null);
        TextView textView = (TextView) v.findViewById(R.id.my_toast_text);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.setView(v);
    }

    public static MySuccessToast makeText(Context context, CharSequence text, int duration) {
        return new MySuccessToast(context, text, duration);
    }
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
