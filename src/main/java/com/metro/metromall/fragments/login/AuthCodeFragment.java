package com.metro.metromall.fragments.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.utils.RegexUtil;
import com.metro.metromall.utils.ValidationCode;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

/**
 * 需要检测手机号码或者邮箱是否注册
 */
public class AuthCodeFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    public EditText re_account;
    EditText re_auth_code;
    ValidationCode validation_code;
    Button re_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auth_code, container, false);
        initView();
        return view;
    }

    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        re_account = (EditText) view.findViewById(R.id.re_account);
        re_auth_code = (EditText) view.findViewById(R.id.re_auth_code);
        validation_code = (ValidationCode)view.findViewById(R.id.validation_code);
        re_next = (Button) view.findViewById(R.id.re_next);


        news_title.setText(R.string.retrieve_password);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        re_next.setOnClickListener(this);
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
            case R.id.re_next:
                if (!check()){
                    return;
                }
                SMSFragment smsFragment = new SMSFragment();
                Bundle bundle = new Bundle();
                bundle.putString("account",re_account.getText().toString().trim());
                smsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frams,smsFragment)
                        .addToBackStack("vip").commit();
                break;
        }
    }
    private boolean check(){
        if (re_account.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入手机号码或邮箱！", Toast.LENGTH_LONG).show();
            return false;
        }
        if(!RegexUtil.isMobileNO(re_account.getText().toString().trim()) && !RegexUtil.isEmail(re_account.getText().toString().trim())){
            MyFailToast.makeText(getContext(),"手机号码或邮箱输入有误！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (re_auth_code.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入图形验证码！",Toast.LENGTH_LONG).show();
            return false;
        }
        if (!re_auth_code.getText().toString().trim().toUpperCase().equals(validation_code.getCodeString().toUpperCase())){
            MyFailToast.makeText(getContext(),"验证码不正确！",Toast.LENGTH_LONG).show();
            return false;
        }
//        //判断手机号是否存在
//        if (RegexUtil.isMobileNO(re_account.getText().toString()) && re_auth_code.getText().toString().equals("")){
//            MyFailToast.makeText(getContext(),"手机号码未注册！",Toast.LENGTH_LONG).show();
//            return false;
//        }
//        if (RegexUtil.isMobileNO(re_account.getText().toString()) && re_auth_code.getText().toString().equals("")){
//            MyFailToast.makeText(getContext(),"该邮箱没有注册过，请重新输入邮箱！",Toast.LENGTH_LONG).show();
//            return false;
//        }
        return true;
    }
}
