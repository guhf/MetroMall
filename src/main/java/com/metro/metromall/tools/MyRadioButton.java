package com.metro.metromall.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.metro.metromall.R;


/**
 * Created by guhf on 2017/11/1.
 */

public class MyRadioButton extends RadioButton {
    public MyRadioButton(Context context) {
        this(context,null);
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyRadioButton);
        //drawableSize = a.getDimensionPixelSize(R.styleable.MyRadioButton_rbDrawableTopSize, 50);
        Drawable drawableTop = a.getDrawable(R.styleable.MyRadioButton_rbDrawableTop);

        //释放资源
        a.recycle();

        //setCompoundDrawablesWithIntrinsicBounds(null,drawableTop,null,null);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        //if(top != null){
            //这里只要改后面两个参数就好了，一个宽一个是高，如果想知道为什么可以查找源码
        if (Build.VERSION.SDK_INT > 22) {
            top.setBounds(0, 0, 45, 45);
        }else {
            top.setBounds(0, 0, 70, 70);
        }
        //}
        setCompoundDrawables(left,top,right,bottom);
    }
}
