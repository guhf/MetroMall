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
import com.metro.metromall.utils.ValidationCode;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class YRegFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    EditText y_vip_code;
    EditText y_reg_auth_code;
    ValidationCode y_reg_validation_code;
    TextView y_reg_phone;
    Button y_reg_get_sms;
    EditText y_reg_sms_code;
    EditText y_reg_password;
    Button y_next;
    TextView n_reg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reg_y, container, false);
        initView();
        return view;
    }
    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        y_vip_code = (EditText)view.findViewById(R.id.y_vip_code);
        y_reg_auth_code = (EditText) view.findViewById(R.id.y_reg_auth_code);
        y_reg_validation_code = (ValidationCode)view.findViewById(R.id.y_reg_validation_code);
        y_reg_phone = (TextView) view.findViewById(R.id.y_reg_phone);
        y_reg_get_sms = (Button)view.findViewById(R.id.y_reg_get_sms);
        y_reg_sms_code = (EditText) view.findViewById(R.id.y_reg_sms_code);
        y_reg_password = (EditText) view.findViewById(R.id.y_reg_password);

        y_next = (Button) view.findViewById(R.id.y_next);
        n_reg = (TextView) view.findViewById(R.id.n_reg);

        news_title.setText(R.string.y_vip_reg);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        y_reg_get_sms.setOnClickListener(this);
        y_next.setOnClickListener(this);
        n_reg.setOnClickListener(this);
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
            case R.id.y_reg_get_sms :
                hideInputKeyboard(getContext());
                if (!checkCode()){
                    return;
                }
                CountDownTimerUtil timerUtils = new CountDownTimerUtil(y_reg_get_sms,60000,1000);
                timerUtils.start();
                break;
            case R.id.y_next:
                hideInputKeyboard(getContext());
                if (!checkSMS()){
                    return;
                }

                break;
            case R.id.n_reg:
                hideInputKeyboard(getContext());
                NRegFragment nRegFragment = new NRegFragment();
                getFragmentManager().beginTransaction().replace(R.id.frams,nRegFragment)
                        .addToBackStack("vip").commit();
                break;
        }
    }
    private boolean checkCode(){
        if (y_vip_code.getText().toString().trim().equals("") ){
            MyFailToast.makeText(getContext(),"请输入正确会员卡号！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (y_reg_auth_code.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入图形验证码！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!y_reg_auth_code.getText().toString().trim().toUpperCase().equals(y_reg_validation_code.getCodeString().toUpperCase())){
            MyFailToast.makeText(getContext(),"图形验证码不正确，请重新输入！",Toast.LENGTH_LONG).show();
            return false;
        }
//        if (y_reg_phone.getText().toString().trim().equals("") || !RegexUtil.isMobileNO(y_reg_phone.getText().toString().trim()) ){
//            MyFailToast.makeText(getContext(),"请输入11位手机号码！", Toast.LENGTH_LONG).show();
//            return false;
//        }
        return true;
    }
    private boolean checkSMS(){
        if (y_reg_sms_code.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入正确短信验证码！", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!y_reg_sms_code.getText().toString().trim().toUpperCase().equals(y_reg_validation_code.getCodeString().toUpperCase())){
            MyFailToast.makeText(getContext(),"短信验证码不正确，请重新输入！",Toast.LENGTH_LONG).show();
            return false;
        }

        if (y_reg_password.getText().toString().trim().length() < 6)
        {
            MyFailToast.makeText(getContext(),"请输入正确格式的密码！",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
