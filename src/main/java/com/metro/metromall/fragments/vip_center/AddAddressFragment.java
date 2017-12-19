package com.metro.metromall.fragments.vip_center;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
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

import com.metro.metromall.MainActivity;
import com.metro.metromall.R;
import com.metro.metromall.activities.CommActivity;
import com.metro.metromall.activities.SearchActivity;
import com.metro.metromall.utils.RegexUtil;
import com.metro.metromall.tools.FragmentBackListener;
import com.metro.metromall.tools.MyDialog;
import com.metro.metromall.tools.MyFailToast;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class AddAddressFragment extends Fragment implements View.OnClickListener, FragmentBackListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;

    EditText consignee;
    EditText consignee_telephone;
    //EditText consignee;
    EditText detail_address;
    CheckBox default_address;
    Button save_address;
    private boolean hadIntercept;

    /**
     * 所有继承BackHandledFragment的子类都将在这个方法中实现物理Back键按下后的逻辑
     * FragmentActivity捕捉到物理返回键点击事件后会首先询问Fragment是否消费该事件
     * 如果没有Fragment消息时FragmentActivity自己才会消费该事件
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_address, container, false);
        initView();
//        view.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
//        view.setFocusableInTouchMode(true);
//        view.setOnKeyListener(backListener);
        return view;
    }

    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView) view.findViewById(R.id.logout);
        consignee = (EditText) view.findViewById(R.id.consignee);
        consignee_telephone = (EditText) view.findViewById(R.id.consignee_telephone);
        detail_address = (EditText) view.findViewById(R.id.detail_address);
        default_address = (CheckBox) view.findViewById(R.id.default_address);
        save_address = (Button) view.findViewById(R.id.save_address);

        news_title.setText(R.string.delivery_address);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        save_address.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                back();
                break;
            case R.id.text_back:
                back();
                break;
            case R.id.save_address:
                save();
                break;
        }
    }

    private void save() {
        if (consignee.getText().toString().trim().equals("")) {
            MyFailToast.makeText(getContext(), "请输入收货人", Toast.LENGTH_LONG).show();
            return;
        }
        if (consignee_telephone.getText().toString().trim().equals("")) {
            MyFailToast.makeText(getContext(), "请输入11位手机号码", Toast.LENGTH_LONG).show();
            return;
        }
        if (!RegexUtil.isMobileNO(consignee_telephone.getText().toString().trim())) {
            MyFailToast.makeText(getContext(), "请输入正确的手机号码", Toast.LENGTH_LONG).show();
            return;
        }
        if (consignee_telephone.getText().toString().trim().equals("")) {
            MyFailToast.makeText(getContext(), "请输入11位手机号码", Toast.LENGTH_LONG).show();
            return;
        }

        if (detail_address.getText().toString().trim().equals("")) {
            MyFailToast.makeText(getContext(), "请输入详细地址", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void back() {
        if (!consignee.getText().toString().trim().equals("") || !consignee_telephone.getText().toString().trim().equals("") ||
                !detail_address.getText().toString().trim().equals("")) {
            MyDialog.Builder builder = new MyDialog.Builder(getActivity());
            builder.setMessage("修改了信息还未保存，确认现在返回吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    hideInputKeyboard(getContext());
                    getFragmentManager().popBackStack();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.createNoTitleDialog().show();
        } else {
            hideInputKeyboard(getContext());
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof CommActivity) {
            ((CommActivity) context).setBackListener(this);
            ((CommActivity) context).setInterception(true);
        }
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof CommActivity) {
            ((CommActivity) activity).setBackListener(this);
            ((CommActivity) activity).setInterception(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof CommActivity) {
            ((CommActivity) getActivity()).setBackListener(null);
            ((CommActivity) getActivity()).setInterception(false);
        }
    }

    @Override
    public void onBackForward() {
        back();
    }
}
