package com.metro.metromall.fragments.login;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.utils.SharePUtil;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class LoginFragment extends Fragment implements View.OnClickListener {
    View view ;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    EditText account;
    EditText password;
    TextView retrieve_password;
    CheckBox cb_no_login;
    Button login;
    TextView reg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_login, container, false);
        initView();
        return view;
    }
    private void initView(){
        re_search = (RelativeLayout)view.findViewById(R.id.re_search);
        img_back = (ImageView)view.findViewById(R.id.img_back);
        text_back = (TextView)view.findViewById(R.id.text_back);
        news_title = (TextView)view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        account = (EditText)view.findViewById(R.id.account) ;
        password = (EditText)view.findViewById(R.id.password) ;
        retrieve_password = (TextView) view.findViewById(R.id.retrieve_password) ;
        cb_no_login = (CheckBox) view.findViewById(R.id.cb_no_login) ;
        login = (Button) view.findViewById(R.id.login) ;
        reg = (TextView)view.findViewById(R.id.reg) ;

        news_title.setText(R.string.vip_login);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);
        //设置下划线
        retrieve_password.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        reg.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        retrieve_password.setOnClickListener(this);
        login.setOnClickListener(this);
        reg.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                hideInputKeyboard(getContext());
                getActivity().setResult(10);
                getActivity().finish();
                break;
            case R.id.text_back:
                hideInputKeyboard(getContext());
                getActivity().setResult(10);
                getActivity().finish();
                break;
            case R.id.retrieve_password:
                hideInputKeyboard(getContext());
                AuthCodeFragment authCodeFragment = new AuthCodeFragment();
                getFragmentManager().beginTransaction().replace(R.id.frams,authCodeFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.login:
                hideInputKeyboard(getContext());
                if (!check()){
                    return;
                }
                if (cb_no_login.isChecked() == true){

                }
                SharePUtil.put(getContext(),"metro_sp","login_state","1");
                hideInputKeyboard(getContext());
                getActivity().setResult(1);
                getActivity().finish();
                break;
            case R.id.reg:
                hideInputKeyboard(getContext());
                NRegFragment nRegFragment = new NRegFragment();
                getFragmentManager().beginTransaction().replace(R.id.frams,nRegFragment)
                        .addToBackStack("vip").commit();
                break;
        }
    }
    private boolean check(){
        if (account.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入用户名",Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入正确的密码",Toast.LENGTH_LONG).show();
            return false;
        }
//        if (true){
//            MyFailToast.makeText(getContext(),"用户不存在",Toast.LENGTH_LONG).show();
//            return false;
//        }
        return true;
    }
}
