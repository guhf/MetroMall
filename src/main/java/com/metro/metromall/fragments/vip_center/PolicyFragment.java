package com.metro.metromall.fragments.vip_center;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.metro.metromall.R;

import static com.metro.metromall.utils.Utils.hideInputKeyboard;

public class PolicyFragment extends Fragment implements View.OnClickListener {
    View view;
    RelativeLayout re_search;
    ImageView img_back;
    TextView text_back;
    TextView news_title;
    TextView logout;

    RelativeLayout faq;
    RelativeLayout change_gods_policy;
    RelativeLayout payment_method;
    RelativeLayout dispatching;
    RelativeLayout deal;

    TextView faq_details;
    TextView change_gods_policy_details;
    ImageView payment_method_img;
    TextView payment_method_details;
    TextView dispatching_details;
    TextView deal_details;

    boolean faq_switch = false;
    boolean change_gods_policy_switch = false;
    boolean payment_method_switch = false;
    boolean dispatching_switch = false;
    boolean deal_switch = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_policy, container, false);
        initView();
        return view;
    }
    private void initView() {
        re_search = (RelativeLayout) view.findViewById(R.id.re_search);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        text_back = (TextView) view.findViewById(R.id.text_back);
        news_title = (TextView) view.findViewById(R.id.text_title_context);
        logout = (TextView)view.findViewById(R.id.logout);

        faq = (RelativeLayout)view.findViewById(R.id.faq);
        change_gods_policy = (RelativeLayout)view.findViewById(R.id.change_gods_policy);
        payment_method = (RelativeLayout)view.findViewById(R.id.payment_method);
        dispatching = (RelativeLayout)view.findViewById(R.id.dispatching);
        deal = (RelativeLayout)view.findViewById(R.id.deal);
        faq_details = (TextView)view.findViewById(R.id.faq_details);
        change_gods_policy_details = (TextView)view.findViewById(R.id.change_gods_policy_details);
        payment_method_img = (ImageView)view.findViewById(R.id.payment_method_img);
        payment_method_details = (TextView)view.findViewById(R.id.payment_method_details);
        dispatching_details = (TextView)view.findViewById(R.id.dispatching_details);
        deal_details = (TextView)view.findViewById(R.id.deal_details);

        news_title.setText(R.string.policy);
        logout.setVisibility(View.INVISIBLE);
        re_search.setVisibility(View.INVISIBLE);

        img_back.setOnClickListener(this);
        text_back.setOnClickListener(this);
        faq.setOnClickListener(this);
        change_gods_policy.setOnClickListener(this);
        payment_method.setOnClickListener(this);
        dispatching.setOnClickListener(this);
        deal.setOnClickListener(this);
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
            case R.id.faq:
                openFAQ();
                break;
            case R.id.change_gods_policy:
                openChangeGoodsPolicy();
                break;
            case R.id.payment_method:
                openPaymentMethod();
                break;
            case R.id.dispatching:
                openDispatching();
                break;
            case R.id.deal:
                openDeal();
                break;
        }
    }
    private void openFAQ(){
        if (faq_switch == false) {
            faq_details.setVisibility(View.VISIBLE);
            String text = "<font color='#333333'><big>1、Q：是否一定是麦德龙会员才可以在麦德龙网上商场购物？</big></font><br>";
            text += "<font color='#333333'>A：不是，我们非常欢迎广大顾客前来我们网上商场购物，如您是我们麦德龙会员，建议您直接在注册页面进行“已有会员卡绑定”，如您还未成为我们麦德龙会员，" +
                    "您也可以轻松注册，开通麦德龙网上商场的网购服务。</font><br><br>";
            text += "<font color='#333333'><big>2、Q：忘记了密码如何登陆呢？</big></font><br>";
            text += "<font color='#333333'>A：您可以点击麦德龙网上商场页面上的“登录”按扭，再点击“忘记密码”可以通过邮箱及手机方式找回，也可以致电客服热线4001-518-518咨询。</big></font><br><br>";
            text += "<font color='#333333'><big>3、Q：购物可以开具发票吗？</big></font><br>";
            text += "<font color='#333333'>A：我们每单都会为客户开具普通发票。</font><br>";
            text += "<font color='#333333'>具体说明：1）未持卡者（即，不是麦德龙会员），只能开具个人抬头的普票；</font><br>";
            text += "<font color='#333333'>2）持卡者（即，麦德龙会员），可根据会员卡抬头开具普票；</font><br>";
            text+="<font color='red'><big><i>如需开具增值税发票，需提供企业工商营业证及近一个月的增值税发票单据作为凭证，具体也可咨询客服热线 4001-518-518。</i></big></font><br><br>";
            text += "<font color='#333333'><big>4、Q：可以在线申请办理麦德龙会员卡吗？</big></font><br>";
            text += "<font color='#333333'>A：请参考 http://www.metromall.cn/help.aspx?HelpNewsID=84</font><br><br>";
            text += "<font color='#333333'><big>5、Q：我收到的货物破损了，可以申请退换货吗？</big></font><br>";
            text += "<font color='#333333'>A：很抱歉，我们暂时没有换货服务，如您收到的商品有任何问题，需要您携带发票及所订购商品，前往为您发货的麦德龙商场，他们将为您完成售后退货，" +
                    "退货完成后欢迎您再次在麦德龙网上商场享受线上购物体验，如有问题，也请咨询麦德龙网上商场客服热线。4001-518-518）</font><br><br>";
            text += "<font color='#333333'><big>6、Q：登录麦德龙网上商场网页总是无法链接，这是怎么回事？</big></font><br>";
            text += "<font color='#333333'>A：请您先刷新一下您所登录的页面，或者检查一下网络是否正常，能否登录其它网站，如果以上两种方式都无效，还有一种情况是网页正在更新，可能会影响您的浏览，还望能谅解。</font><br><br>";
            text += "<font color='#333333'><big>7、Q：网站上面显示商品已售完请问什么时候可以在到货？</big></font><br>";
            text += "<font color='#333333'>A：一般补货时间是7-15个工作日，具体还是以网站信息为准。</font><br><br>";
            text += "<font color='#333333'><big>8、Q：如何取消订单？</big></font><br>";
            text += "<font color='#333333'>A：如商品没有发货，您可以通过联系客服热线4001-518-518取消订单。如商品已经发货，请您到时可以直接拒收，发货商场在收到您的拒收商品后可为您办理退款。</font><br><br>";
            text += "<font color='#333333'><big>9、Q：为何商品页面上没有具体生产日期，是否能查询该情况？</big></font><br>";
            text += "<font color='#333333'>A：由于进货批次不同，发货商场将随机选取您所选购的商品，发货前无法告知具体生产日期。但我们发送的商品都是有质量保证的，敬请放心。</font><br><br>";
            text += "<font color='#333333'><big>10、Q：麦德龙网上商场是否能使用麦德龙购物卡？</big></font><br>";
            text += "<font color='#333333'>A：很抱歉，暂时不支持购物卡。</font><br><br>";
            text += "<font color='#333333'><big>11、Q：麦德龙网上商场的商品价格是否与麦德龙商场一样？</big></font><br>";
            text += "<font color='#333333'>A：您所选购的商品与您发货商场的价格相同。同时麦德龙网上商场也会不定期开展各项促销活动，提供您更快捷的高品质服务体验。</font><br><br>";
            text += "<font color='#333333'><big>12、Q：订单支付后，如何修改送货地址？</big></font><br>";
            text += "<font color='#333333'>A：请先拨打4001-518-518确认订单状态，我们会尽力满足您的要求，为您修改送货地址，但若订单已经从门店发出，则订单地址不可修改。</font><br>";

            CharSequence charSequence= Html.fromHtml(text);
            faq_details.setText(charSequence);
            faq_switch = true;
        }else {
            faq_details.setVisibility(View.GONE);
            faq_switch = false;
        }
    }
    private void openChangeGoodsPolicy(){
        if (change_gods_policy_switch == false) {
            change_gods_policy_details.setVisibility(View.VISIBLE);
            String text = "<font color='#548dd4'><big>麦德龙网上商场售后服务政策---退换货政策：</big></font><br><br>";
            text += "<font color='#333333'>一、麦德龙网上商场销售的均为全新品，我们承诺自您收到商品之日起您可享有90天内无理由退换货服务，" +
                    "但下列商品不享受90天内无理由退换货服务：</font><br><br>";
            text += "<font color='#333333'>酒类、手机、电脑、数码产品、家用电器、电池；</font><br>" ;
            text += "<font color='#333333'>根据顾客要求定制或加工过的商品；</font><br>";
            text += "<font color='#333333'>瑕疵商品或样品。</font><br>" ;
            text += "<font color='#333333'>除以上所列商品外，其他根据商品性质不宜退换货的商品不适用无理由退换货。</font><br><br>";
            text += "<font color='#548dd4'><big>“90天无忧退换”服务细则：</big></font><br><br>";
            text += "<font color='#333333'>顾客使用折扣券购买的商品只能换货，不享受退货；</font><br>" ;
            text += "<font color='#333333'>麦德龙信用额度客户通过本网上商场购买的商品，自收到商品日起15天内无理由退换货，但退货不得退还现金，只能恢复相应的信用额度；</font><br>" ;
            text += "<font color='#333333'>顾客购买时已发放各类折扣券、印花或赠品的，必须一并退还，才可享受90天内无理由退；</font><br><br>" ;
            text += "<font color='#548dd4'><big>商品验货与签收：</big></font><br><br>";
            text += "<font color='#333333'>一、为保障您的权益，请您在收货时注意以下环节</font><br>" ;
            text += "<font color='#333333'>1、签收商品时与配送人员当面核对：商品及配件、应付金额、商品数量及发货清单、发票（如有）、赠品（如有）等；</font><br>" ;
            text += "<font color='#333333'>2、如存在包装破损、商品错误、商品短缺、商品存在质量问题等影响签收的因素，请您可以拒收全部或部分商品，相关的赠品，配件或捆绑商品应一起当场拒收；</font><br>" ;
            text += "<font color='#333333'>3、为了保护您的权益，建议您尽量不要委托他人代为签收；如由他人代为签收商品而没有在配送人员在场的情况下验货，" +
                    "请在15天之内联系客服，否则视为您所订购商品的包装无任何问题；</font><br>" ;
            text += "<font color='#333333'>4、为了方便日后可能出现的退换货流程，请保留商品外包装箱和发票15天。</font><br><br>" ;
            text += "<font color='#333333'>二、麦德龙网上商场只接受与原订单相同商品、相同数量的退换货</font><br>" ;
            text += "<font color='#333333'>退货的商品应当完好（如有赠品，则需包括赠品完好）请携带订单商品及发票前往为您发货的麦德龙商场，我们商场会为您办理退货手续。</font><br><br>" ;
            text += "<font color='#333333'>三.特别声明：</font><br>" ;
            text += "<font color='#333333'>1、麦德龙网上商场目前暂时无法提供邮寄退货服务，退货需携带订单中所有商品及发票前往为您发货的麦德龙商场，我们商场会为您办理退货手续，退货手续办理完成后，" +
                    "我们会尽快为您申请银联退款，退款周期为您申请退货后的7日内返还。信用顾客可当天恢复相应的信用额度；</font><br>" ;
            text += "<font color='#333333'>2、本退货政策经顾客在购物订单生成确认后，视为网上商场交易协议不可分割的一部分。</font><br><br>" ;
            text += "<font color='#333333'>如您有任何进一步的问题，请致电客服（4001-518-518）或直接去门店售后服务台。我们将有专人为您服务。</font><br>" ;

            CharSequence charSequence= Html.fromHtml(text);
            change_gods_policy_details.setText(charSequence);
            change_gods_policy_switch = true;
        }else {
            change_gods_policy_details.setVisibility(View.GONE);
            change_gods_policy_switch = false;
        }
    }
    private void openPaymentMethod(){
        if (payment_method_switch == false) {
            payment_method_img.setVisibility(View.VISIBLE);
            payment_method_details.setVisibility(View.VISIBLE);

            String text = "<font color='red'>友情提示：</font><br>";
            text += "<font color='#333333'>个人卡无法开具增值税发票，如需开具增值税发票，需提供企业工商营业证及近一个月的增值税发票单据作为凭证，具体也可咨询客服热线</font>" ;
            text += "<font color='red'>4001-518-518</font><br>";

            CharSequence charSequence= Html.fromHtml(text);
            payment_method_details.setText(charSequence);
            payment_method_switch = true;
        }else {
            payment_method_img.setVisibility(View.GONE);
            payment_method_details.setVisibility(View.GONE);
            payment_method_switch = false;
        }
    }
    private void openDispatching(){
        if (dispatching_switch == false) {
            dispatching_details.setVisibility(View.VISIBLE);

            //CharSequence charSequence= Html.fromHtml(text);
            //faq_details.setText(charSequence);
            dispatching_switch = true;
        }else {
            dispatching_details.setVisibility(View.GONE);
            dispatching_switch = false;
        }
    }
    private void openDeal(){
        if (deal_switch == false) {
            deal_details.setVisibility(View.VISIBLE);
            String text = "<font color='#333333'><big>麦德龙会员网上商场交易协议</big></font><br>";
            text += "<font color='#333333'><big>甲方：锦江麦德龙现购自运有限公司 （以下简称麦德龙）</big></font><br>";
            text += "<font color='#333333'><big>乙方：在麦德龙网上商场购物的顾客 （以下简称顾客）</big></font><br>";
            text += "<font color='#333333'><big>一、交易协议</big></font><br>";
            text += "<font color='#333333'><big>本协议为麦德龙与在麦德龙网上商场购物的顾客购销协议，购销双方都有义务遵守</big></font><br>";
            text += "<font color='#333333'><big>(一)购买条款</big></font><br>";
            text += "<font color='#333333'><big>1、订购的商品价格以您下订单时麦德龙所标价格为准。</big></font><br>";
            text += "<font color='#333333'><big>2、请清楚准确地填写您的真实姓名、送货地址及联系方式。因如下情况造成订单延迟或无法配送等，麦德龙将不承担责任：</big></font><br>";
            text += "<font color='#333333'><big>1)顾客提供错误信息和不详细的地址；</big></font><br>";
            text += "<font color='#333333'><big>2)货物送达无人签收，由此造成的重复配送所产生的费用及相关的后果。</big></font><br>";
            text += "<font color='#333333'><big>3)不可抗力，例如：自然灾害、交通戒严、突发战争等。</big></font><br>";
            text += "<font color='#333333'><big>(二)退换货条款</big></font><br>";
            text += "<font color='#333333'><big>签署本协议视同同意接受</big></font><br>";
            text += "<font color='#333333'><big>二、安全性</big></font><br>";
            text += "<font color='#333333'><big>无论您是以电话订购或是网络订购，我们会保证安全第一，将会有麦德龙授权的员工处理您的订单，并且保证您\n" +
                    "的信息处于高度安全的状态。</big></font><br>";
            text += "<font color='#333333'><big>三、顾客信息隐私权</big></font><br>";
            text += "<font color='#333333'><big>麦德龙尊重顾客的隐私权，在任何情况下，我们都不会将您的信息出售或泄露给任何第三方。</big></font><br>";
            text += "<font color='#333333'><big>(一)顾客信息的获取</big></font><br>";
            text += "<font color='#333333'><big>我们获取用户信息的主要目的在于向您提供一个顺畅、高效的购物流程，并致力于不断完善和提升您的购物体验。</big></font><br>";
            text += "<font color='#333333'><big>1、我们可能获取的顾客信息类型包括：</big></font><br>";
            text += "<font color='#333333'><big>1)注册信息（如，用户名、及在注册过程中提供的其他信息）；</big></font><br>";
            text += "<font color='#333333'><big>2)个人或公司联系方式（如，姓名、公司名称、通讯地址、电邮地址、电话、手机号码、传真号码）；</big></font><br>";
            text += "<font color='#333333'><big>3)订购信息（如，(i) 订购人和收货人名称、送货地址、电邮地址和电话号码等与订购相关的任何信息，(ii) 在\n" +
                    "交付文件上的签名，(iii)网站帐户及帐户信息，(iv) 为帮助我们提供服务（包括特定服务）而提供给我们的信息）；</big></font><br>";
            text += "<font color='#333333'><big>4)用于您身份识别的信息（如，身份证号码、护照号码、驾驶证号码）；</big></font><br>";
            text += "<font color='#333333'><big>5)指示我们将商品或服务提供给其他人士时提供的该人士的有关信息（如，该人士的姓名、送货地址、电邮地址和\n" +
                    "电话号码）；</big></font><br>";
            text += "<font color='#333333'><big>6)支付信息（如，订单支付详情）和财务信息（如，银行账号）；</big></font><br>";
            text += "<font color='#333333'><big>7)其他个人、公司、单位、或社会实体信息。</big></font><br>";
            text += "<font color='#333333'><big>2、我们可能会就下列各种情况，获得您的顾客信息：</big></font><br>";
            text += "<font color='#333333'><big>1)您在访问或使用麦德龙服务时提供的信息；</big></font><br>";
            text += "<font color='#333333'><big>2)您在与我们的沟通中自动获取的信息：无论您何时与我们沟通，我们接收并储存的信息；</big></font><br>";
            text += "<font color='#333333'><big>3)从其他来源获取的信息：如您参加我们或我们与合作伙伴组织的任何活动。</big></font><br>";
            text += "<font color='#333333'><big>(二)顾客信息的使用</big></font><br>";
            text += "<font color='#333333'><big>1、我们可能会利用我们获取的用户信息：</big></font><br>";
            text += "<font color='#333333'><big>1)向您提供所需要的产品或服务（如，定向采购、货物配送）；</big></font><br>";
            text += "<font color='#333333'><big>2)处理和收取款项；</big></font><br>";
            text += "<font color='#333333'><big>3)就您的要求向您提供咨询服务，或向您做出回应或与您沟通；</big></font><br>";
            text += "<font color='#333333'><big>4)向您提供我们认为您可能会感兴趣的产品或服务；</big></font><br>";
            text += "<font color='#333333'><big>5)为提升我们的服务，联系您进行调研；</big></font><br>";
            text += "<font color='#333333'><big>6)进行数据分析（如，购买行为或趋势分析、市场或顾客调查、财务分析）；</big></font><br>";
            text += "<font color='#333333'><big>7)管理或认可您参加各种活动的资格，如线上或线下活动、优惠或促销活动；</big></font><br>";
            text += "<font color='#333333'><big>8)经营、评估和完善我们的业务（包括开发新产品和服务；管理我们的通信；判断我们的销售、营销和广告效果；\n" +
                    "分析和强化我们的产品、服务和网站；和开展会计、审计、记账、对账和收集活动）；</big></font><br>";
            text += "<font color='#333333'><big>9)在事先获得您同意的情况下，向您指定的联系人发送信息；</big></font><br>";
            text += "<font color='#333333'><big>10)防止各种违法或犯罪活动和其他法律责任；</big></font><br>";
            text += "<font color='#333333'><big>11)遵守法律规定和我们的政策规则。</big></font><br>";




            CharSequence charSequence= Html.fromHtml(text);
            deal_details.setText(charSequence);
            deal_switch = true;
        }else {
            deal_details.setVisibility(View.GONE);
            deal_switch = false;
        }
    }
}
