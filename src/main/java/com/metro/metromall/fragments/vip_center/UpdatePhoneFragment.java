package com.metro.metromall.fragments.vip_center;

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
import com.metro.metromall.utils.RegexUtil;
import com.metro.metromall.utils.ValidationCode;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.tools.MySuccessToast;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class UpdatePhoneFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;

    EditText update_phone_auth_code;
    ValidationCode update_phone_validation_code;
    EditText update_phone_phone;
    EditText update_phone_sms_code;
    Button update_phone_get_sms;
    Button update_phone_save;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_update_phone, container, false);
        initView();
        return view;
    }

    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        update_phone_auth_code = (EditText)view.findViewById(R.id.update_phone_auth_code);
        update_phone_validation_code = (ValidationCode)view.findViewById(R.id.update_phone_validation_code);
        update_phone_phone = (EditText)view.findViewById(R.id.update_phone_phone);
        update_phone_sms_code = (EditText)view.findViewById(R.id.update_phone_sms_code);
        update_phone_get_sms = (Button)view.findViewById(R.id.update_phone_get_sms);
        update_phone_save = (Button)view.findViewById(R.id.update_phone_save);

        news_title.setText(R.string.update_telephone);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        update_phone_get_sms.setOnClickListener(this);
        update_phone_save.setOnClickListener(this);
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
            case R.id.update_phone_get_sms:
                if (!checkSendSMS()){
                    return;
                }
                break;
            case R.id.update_phone_save:
                if (!checkUpdate()){
                    return;
                }

                MySuccessToast.makeText(getContext(),"恭喜你，手机号码修改成功！",Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                break;
        }
    }
    private boolean checkSendSMS(){
        if (update_phone_auth_code.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入图形验证码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!update_phone_auth_code.getText().toString().trim().toUpperCase().equals(update_phone_validation_code.getCodeString().toUpperCase())){
            MyFailToast.makeText(getContext(),"请输入正确的图形验证码！",Toast.LENGTH_LONG).show();
            return false;
        }
        if (update_phone_phone.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入手机号码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!RegexUtil.isMobileNO(update_phone_phone.getText().toString().trim())){
            MyFailToast.makeText(getContext(),"请输入正确的手机号码", Toast.LENGTH_LONG).show();
            return false;
        }
//        if (update_phone_phone.getText().toString().trim().equals()){
//            MyFailToast.makeText(getContext(),"请换个手机号码", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        if (update_phone_phone.getText().toString().trim().equals()){
//            MyFailToast.makeText(getContext(),"此手机号码已经注册，请重新输入！", Toast.LENGTH_LONG).show();
//            return false;
//        }
        return true;
    }
    private boolean checkUpdate(){
        if (update_phone_sms_code.getText().toString().trim().equals("")){
            MyFailToast.makeText(getContext(),"请输入短信验证码", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!update_phone_sms_code.getText().toString().trim().equals(update_phone_validation_code.getCodeString().toUpperCase())){
            MyFailToast.makeText(getContext(),"请输入正确的短信验证码！",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
