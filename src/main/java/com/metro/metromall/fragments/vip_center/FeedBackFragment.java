package com.metro.metromall.fragments.vip_center;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.metro.metromall.R;
import com.metro.metromall.tools.MyFailToast;
import com.metro.metromall.tools.MySuccessToast;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class FeedBackFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;
    EditText back_content;
    TextView editor_length;
    Button back_commit;
    TextView tel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_feed_back, container, false);
        initView();
        return view;
    }

    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);
        back_content = (EditText)view.findViewById(R.id.back_content);
        editor_length = (TextView)view.findViewById(R.id.editor_length);
        back_commit = (Button)view.findViewById(R.id.back_commit);
        tel = (TextView)view.findViewById(R.id.tel);



        news_title.setText(R.string.feedback);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        back_commit.setOnClickListener(this);
        tel.setOnClickListener(this);

        back_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = back_content.getText().toString().trim().length();
                editor_length.setText(length+"/400");
                if (length > 30){
                    MyFailToast.makeText(getContext(),"亲，已达到输入上限(400字)！",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                break;
            case R.id.text_back:
                hideInputKeyboard(getContext());
                getFragmentManager().popBackStack();
                break;
            case R.id.back_commit:
                back(back_content.getText().toString().trim());
                break;
            case R.id.tel:
                call(tel.getText().toString());
                break;
        }
    }
    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     *
     * @param content 反馈内容
     */
    private void back(String content){
        if (content.equals("")){
            MyFailToast.makeText(getContext(),"反馈内容不许为空！", Toast.LENGTH_LONG).show();
            return;
        }
        if (content.length() <15 || content.length() > 400){
            MyFailToast.makeText(getContext(),"反馈内容在15-400字之间！", Toast.LENGTH_LONG).show();
            return;
        }
        /**
         * 提交到服务器的处理
         */


        try {
            MySuccessToast.makeText(getContext(),"谢谢您的反馈！", Toast.LENGTH_LONG).show();
            Thread.sleep(2000);
            hideInputKeyboard(getContext());
            getFragmentManager().popBackStack();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
