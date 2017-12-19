package com.metro.metromall.fragments.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;
import com.metro.metromall.utils.ValidationCode;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.tools.MySuccessToast;
import com.metro.metromall.tools.PassWordEditText;
import com.wx.pwd.CheckStrength;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class UpdatePasswordFragment extends Fragment implements View.OnClickListener, TextWatcher {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    PassWordEditText re_password;
    PassWordEditText qe_password;
    TextView lvl;
    Button up_next;

    LinearLayout lin_code;
    EditText update_password_auth_code;
    ValidationCode update_password_validation_code;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_update_password, container, false);
        initView();
        return view;
    }
    private void initView(){
        re_search = (RelativeLayout)view.findViewById(R.id.re_search);
        img_back = (ImageView)view.findViewById(R.id.img_back);
        text_back = (TextView)view.findViewById(R.id.text_back);
        news_title = (TextView)view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        re_password = (PassWordEditText)view.findViewById(R.id.re_password);
        qe_password = (PassWordEditText)view.findViewById(R.id.qe_password);
        lvl = (TextView)view.findViewById(R.id.lvl);
        up_next = (Button) view.findViewById(R.id.up_next) ;
        lin_code = (LinearLayout)view.findViewById(R.id.lin_code);
        update_password_auth_code = (EditText)view.findViewById(R.id.update_password_auth_code);
        update_password_validation_code = (ValidationCode)view.findViewById(R.id.update_password_validation_code);

        news_title.setText(R.string.retrieve_password);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);
        if (getArguments() != null) {
            lin_code.setVisibility(View.VISIBLE);
            up_next.setText(R.string.complete);
        }

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        up_next.setOnClickListener(this);
        re_password.addTextChangedListener(this);

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
            case R.id.up_next:
                hideInputKeyboard(getContext());
                if (!check()){
                    return;
                }
                updatePassword();
                if (getArguments() != null) {
                    MySuccessToast.makeText(getContext(),"恭喜你，密码修改成功！",Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    hideInputKeyboard(getContext());
                    getFragmentManager().popBackStack();
                }else {
                    SuccessFragment successFragment = new SuccessFragment();
                    getFragmentManager().beginTransaction().replace(R.id.frams,successFragment)
                            .addToBackStack("vip").commit();
                }
                break;
        }
    }
    private boolean check(){
        if (getArguments() != null) {
            if (update_password_auth_code.getText().toString().trim().equals("")){
                MyFailToast.makeText(getContext(),"请输入图形验证码！",Toast.LENGTH_LONG).show();
                return false;
            }
            if (!update_password_auth_code.getText().toString().trim().toUpperCase().equals(update_password_validation_code.getCodeString().toUpperCase())){
                MyFailToast.makeText(getContext(),"图形验证码不正确，请重新输入！",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        if (re_password.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入新密码！",Toast.LENGTH_LONG).show();
            return false;
        }
        if (qe_password.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入确认密码！",Toast.LENGTH_LONG).show();
            return false;
        }
        if (!re_password.getText().toString().trim().equals(qe_password.getText().toString().trim())){
            MyFailToast.makeText(getContext(),"两次密码不一致！",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void updatePassword(){

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String password = re_password.getText().toString().trim();
        if (password.equals("")){
            lvl.setText("弱");
            return;
        }
        int level = CheckStrength.checkPasswordStrength(password);
        if (0 <= level && level<= 3){
            lvl.setText("弱");
        }
        if (4 <= level && level<= 6){
            lvl.setText("中");
        }
        if (7 <= level && level<= 9){
            lvl.setText("强");
        }
        if (10 <= level && level<= 12){
            lvl.setText("很强");
        }
        if (level >= 12){
            lvl.setText("非常强");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
