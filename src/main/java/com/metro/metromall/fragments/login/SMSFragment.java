package com.metro.metromall.fragments.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.metro.metromall.R;
import com.metro.metromall.utils.CountDownTimerUtil;
import com.metro.metromall.utils.RegexUtil;

import java.util.ArrayList;
import java.util.List;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class SMSFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    TextView sms_account;
    Spinner re_account;
    EditText re_sms_code;
    Button re_get_sms;
    Button sms_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sm, container, false);
        initView();
        return view;
    }
    private void initView(){
        re_search = (RelativeLayout)view.findViewById(R.id.re_search);
        img_back = (ImageView)view.findViewById(R.id.img_back);
        text_back = (TextView)view.findViewById(R.id.text_back);
        news_title = (TextView)view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        sms_account = (TextView)view.findViewById(R.id.sms_account);
        re_account = (Spinner) view.findViewById(R.id.re_account) ;
        re_sms_code = (EditText)view.findViewById(R.id.re_sms_code) ;
        re_get_sms = (Button)view.findViewById(R.id.re_get_sms);
        sms_next = (Button) view.findViewById(R.id.sms_next) ;

        if (getArguments() != null){
            sms_account.setText("账号"+getArguments().getString("account")+"，你好。");
            List<String> data = new ArrayList<String>();
            if (RegexUtil.isMobileNO(getArguments().getString("account"))) {
                data.add("已验证手机号");
            }
            if (RegexUtil.isEmail(getArguments().getString("account"))) {
                data.add("已验证邮箱");
            }
            data.add(getArguments().getString("account"));
            ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.item_spinner,data);
            adapter.setDropDownViewResource(R.layout.item_drown_spinner);
            re_account.setAdapter(adapter);
        }
        news_title.setText(R.string.retrieve_password);
        re_search.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        re_get_sms.setOnClickListener(this);
        sms_next.setOnClickListener(this);
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
            case R.id.re_get_sms:
                CountDownTimerUtil timerUtils = new CountDownTimerUtil(re_get_sms,60000,1000);
                timerUtils.start();
                break;
            case R.id.sms_next:
                hideInputKeyboard(getContext());
//                if (re_sms_code.getText().toString().trim().equals("")){
//                    MyToast.makeText(getContext(),"请输入短信验证码！", Toast.LENGTH_LONG).show();
//                    return ;
//                }
//                if (!re_sms_code.getText().toString().trim().equals("验证码不正确")){
//                    MyToast.makeText(getContext(),"请重新获取验证码！", Toast.LENGTH_LONG).show();
//                    return ;
//                }
                UpdatePasswordFragment updatePasswordFragment = new UpdatePasswordFragment();
                getFragmentManager().beginTransaction().replace(R.id.frams,updatePasswordFragment)
                        .addToBackStack("vip").commit();
                break;
        }
    }
}
