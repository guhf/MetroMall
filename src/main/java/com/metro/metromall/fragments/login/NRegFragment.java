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
import com.metro.metromall.utils.CountDownTimerUtil;
import com.metro.metromall.utils.RegexUtil;
import com.metro.metromall.utils.ValidationCode;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class NRegFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    EditText n_reg_auth_code;
    ValidationCode n_reg_validation_code;
    EditText n_reg_phone;
    Button n_reg_get_sms;
    EditText n_reg_sms_code;
    EditText n_reg_password;
    Button n_register;
    TextView y_reg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg_n, container, false);
        initView();
        return view;
    }
    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        n_reg_auth_code = (EditText) view.findViewById(R.id.n_reg_auth_code);
        n_reg_validation_code = (ValidationCode)view.findViewById(R.id.n_reg_validation_code);
        n_reg_phone = (EditText) view.findViewById(R.id.n_reg_phone);
        n_reg_get_sms = (Button)view.findViewById(R.id.n_reg_get_sms);
        n_reg_sms_code = (EditText) view.findViewById(R.id.n_reg_sms_code);
        n_reg_password = (EditText) view.findViewById(R.id.n_reg_password);

        n_register = (Button) view.findViewById(R.id.n_register);
        y_reg = (TextView) view.findViewById(R.id.y_reg);

        news_title.setText(R.string.n_vip_reg);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);


        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        n_reg_get_sms.setOnClickListener(this);
        n_register.setOnClickListener(this);
        y_reg.setOnClickListener(this);
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
            case R.id.n_reg_get_sms :
                if (!checkCode()){
                    return;
                }
                CountDownTimerUtil timerUtils = new CountDownTimerUtil(n_reg_get_sms,60000,1000);
                timerUtils.start();
                break;
            case R.id.n_register:
                if (!checkSMS()){
                    return;
                }
                hideInputKeyboard(getContext());
                RegSuccessFragment regSuccessFragment = new RegSuccessFragment();
                getFragmentManager().beginTransaction().replace(R.id.frams,regSuccessFragment)
                        .addToBackStack("vip").commit();
                break;
            case R.id.y_reg:
                hideInputKeyboard(getContext());
                YRegFragment yRegFragment = new YRegFragment();
                getFragmentManager().beginTransaction().replace(R.id.frams,yRegFragment)
                        .addToBackStack("vip").commit();
                break;
        }
    }
    private boolean checkCode(){
        if (n_reg_auth_code.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入图形验证码！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!n_reg_auth_code.getText().toString().trim().toUpperCase().equals(n_reg_validation_code.getCodeString().toUpperCase())){
            MyFailToast.makeText(getContext(),"图形验证码不正确，请重新输入！",Toast.LENGTH_LONG).show();
            return false;
        }
        if (n_reg_phone.getText().toString().trim().equals("") || !RegexUtil.isMobileNO(n_reg_phone.getText().toString().trim()) ){
            MyFailToast.makeText(getContext(),"请输入11位手机号码！", Toast.LENGTH_LONG).show();
            return false;
        }
//                if (n_reg_phone.getText().toString().trim().equals("") || !RegexUtil.isMobileNO(n_reg_phone.getText().toString().trim()) ){
//                    MyFailToast.makeText(getContext(),"手机号码已存在！", Toast.LENGTH_LONG).show();
//                    return ;
//                }
        return true;
    }
    private boolean checkSMS(){
        if (n_reg_sms_code.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入正确短信验证码！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!n_reg_sms_code.getText().toString().trim().toUpperCase().equals(n_reg_validation_code.getCodeString().toUpperCase())){
            MyFailToast.makeText(getContext(),"短信验证码不正确，请重新输入！",Toast.LENGTH_LONG).show();
            return false;
        }

        if (n_reg_password.getText().toString().trim().length() < 6)
        {
            MyFailToast.makeText(getContext(),"请输入正确格式的密码！",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
