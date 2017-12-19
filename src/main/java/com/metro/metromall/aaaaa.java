//package com.liang.scancode;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.location.Criteria;
//import android.location.Location;
//import android.location.LocationManager;
//import android.net.ConnectivityManager;
//import android.net.wifi.WifiManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AlertDialog;
//import android.util.Log;
//import android.view.Window;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Toast;
//
//import com.liang.scancode.utils.BaseActivity;
//import com.liang.scancode.utils.MACUntil;
//import com.liang.scancode.utils.MyDialog;
//import com.liang.scancode.utils.NetManager;
//import com.liang.scancode.utils.NetWorkStatusReceiver;
//import com.liang.scancode.utils.SdkTools;
//import com.liang.scancode.zxing.utils.MyWebChromeClient;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//import cn.weipass.pos.sdk.Scanner;
//import cn.weipass.pos.sdk.impl.WeiposImpl;
//
//import static com.liang.scancode.utils.coon._URL;
//
//public class MainActivity extends BaseActivity  {
//    //android调用JS网页的时候会用到
//    private Handler mHandler = new Handler();
//    private WebView browser;
//    private String scanResult="";
//    private String m_szWLANMAC;
//    private Scanner scanner;
//    String orderCode;
//
//
//    private LocationManager locationManager;
//    private String locationProvider;
//    private double s_Longitude;
//    private double s_Latitude;
//    NetWorkStatusReceiver mNetworkStateReceiver= new NetWorkStatusReceiver();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //初始化sdk，只需要在apk启动入口初始化一次，当应用完全退出是会自动调用sdk的onDestroy()
//        SdkTools.initSdk(this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        scanner = WeiposImpl.as().openScanner();
//        setContentView(R.layout.activity_main);
//        //@SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
//        m_szWLANMAC = MACUntil.getMacAddress(); //wm.getConnectionInfo().getMacAddress();
//
//        NetManager.checkNetwork(MainActivity.this);
//
//        browser=(WebView)findViewById(R.id.Toweb);
//        browser.loadUrl(_URL+"/app/index.aspx?token=" + m_szWLANMAC);
//
//        //设置后可以弹出alert
//        browser.setWebChromeClient(new WebChromeClient());
//        //设置可自由缩放网页
//        browser.getSettings().setJavaScriptEnabled(true);
//        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        browser.getSettings().setSupportZoom(true);
//        browser.getSettings().setBuiltInZoomControls(true);
//        browser.getSettings().setSupportMultipleWindows(true);
//
//        browser.getSettings().setDatabaseEnabled(true);
//        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
//        browser.getSettings().setGeolocationEnabled(true);
//        browser.getSettings().setGeolocationDatabasePath(dir);
//        browser.getSettings().setDomStorageEnabled(true);
//        //GeoClient geo = new GeoClient();
//        //browser.setWebViewClient(new PTCWebViewClient(this, browser));
//        //browser.setWebChromeClient(geo);
//        //String origin = "";
//        //geo.onGeolocationPermissionsShowPrompt(origin, this);
//
//        browser.addJavascriptInterface(new APPJS(), "app");
//
//        browser.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url)
//            {
//                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                view.loadUrl(url);
//                return true;
//            }
//        });
//
//        Intent intent = getIntent();
//        String out_trade_no = intent.getStringExtra("out_trade_no");
//        String pay_type = intent.getStringExtra("pay_type");
//        String amount = intent.getStringExtra("amount");
//        if (out_trade_no != null)
//        {
//            try {
//                out_trade_no = URLEncoder.encode(out_trade_no,"UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            browser.loadUrl(_URL+"/payment/wangpos/PaymentResult.aspx?operation=paymentSuccess&out_trade_no="+out_trade_no+"&pay_type="+pay_type+"&amount="+amount);
//            //MainActivity.this.finish();
//            Log.e("121212121212112",_URL+"/payment/wangpos/PaymentResult.aspx?operation=paymentSuccess&out_trade_no="+out_trade_no+"&pay_type="+pay_type+"&amount="+amount);
//        }
//        //注册网络监听
////        IntentFilter filter = new IntentFilter();
////        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        //registerReceiver(mNetworkStateReceiver, filter);
//    }
//
//    //提供给js调用的方法
//    public class APPJS {
//        @JavascriptInterface
//        public void openappwin(int appwintype,String returnUrl) {
//            Intent openCameraIntent = null;
//            if (appwintype == 3){
//                //打开扫描界面扫描条形码或二维码
//                openCameraIntent = new Intent(MainActivity.this,ScanActivity.class);
//            }else if (appwintype == 4 || appwintype == 6){
//                //OpenScanning( appwintype,   returnUrl);
//                //data( appwintype, returnUrl);
//                openCameraIntent = new Intent(MainActivity.this,FullScanActivity.class);
//            }
//            Bundle bundle = new Bundle();
//            bundle.putString("appWinType",Integer.toString(appwintype));
//            bundle.putString("winUrl",returnUrl);
//            openCameraIntent.putExtras(bundle);
//
//            startActivityForResult(openCameraIntent, 0);
//            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//        }
//
//        @JavascriptInterface
//        public String GetAPPName() {return "送货神器";}
//
//        @JavascriptInterface
//        public void goIndex() {
//            browser.loadUrl(_URL+"/app/index.aspx?token=" + m_szWLANMAC);
//            //browser.loadUrl("http://shsq.mi100.com/app/index.aspx?token=" + m_szWLANMAC);
//        }
//        @JavascriptInterface
//        public void Payment(String app_id, String out_trade_no, String body, String total_fee, String attach, String notify_url, String operator) {
//            Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("app_id", app_id);
//            bundle.putString("out_trade_no", out_trade_no);
//            bundle.putString("body", body);
//            bundle.putString("total_fee", total_fee);
//            bundle.putString("attach", attach);
//            bundle.putString("notify_url", notify_url);
//            bundle.putString("operator", operator);
//            intent.putExtras(bundle);
//            MainActivity.this.startActivityForResult(intent, 0);
//            //startActivity(intent);
//        }
//
//        @JavascriptInterface
//        public void Dialog(String message) {
//            MyDialog dialog = new MyDialog();
//            dialog.Dialog(MainActivity.this, message);
//        }
//
//        @JavascriptInterface
//        public void CloseActivity() {
//            MainActivity.this.finish();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //Log.e("1212121212121221", requestCode + "---" + resultCode + "--" + data.getStringExtra("OrdersCode") + "---1111111111111111111111111111111111111");
//        //处理扫描结果（在界面上显示）
//        if (resultCode == RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            scanResult = bundle.getString("result");
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    //调用JS中的 函数，当然也可以不传参
//                    browser.loadUrl("javascript:GetScanCode('" + scanResult + "')");
//                }
//            });
//        }else
//        if (resultCode == 1) {//付款完成跳回
//            browser.loadUrl(_URL + "/payment/PayBarCode.aspx?operation=Credit&OrdersCode=" + data.getStringExtra("OrdersCode") + "&Type=" + data.getStringExtra("Type"));
//        } else {
//            browser.loadUrl("javascript:TheReLoad()");
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //取消注册Receiver
//        //unregisterReceiver(mNetworkStateReceiver);
//    }
//
//    @Override
//    public void onNetChange(int netMobile) {
//        super.onNetChange(netMobile);
//        //在这个判断，根据需要做处理
//        if(netMobile == -1){
//            Toast.makeText(MainActivity.this,"您的网络连接已中断",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void OpenScanning(final int type, final String returnUrl){
//        // 调用扫码二维码的activity需要在Manifest文件中配置android:exported="true"属性
//        if (scanner==null) {
//            Toast.makeText(MainActivity.this, "SDK扫描对象为空", Toast.LENGTH_SHORT).show();
//        }
//        scanner.scan(Scanner.TYPE_QR, new Scanner.OnResultListener() {
//            @Override
//            public void onResult(int what, String result) {
//                // TODO Auto-generated method stub
//                orderCode = result;
//                data( type, returnUrl);
//            }
//        });
//    }
//
//    private void data(int type, final String returnUrl){
//        if (type == 4 ) {
//            //收款
//            browser.loadUrl(_URL+"/payment/PayBarCode.aspx?operation=getByOrdersCode&PayBarCode=" + orderCode + "&Lon=" + s_Longitude + "&Lat=" + s_Latitude);
//        } else if (type == 6 ) {
//            //跳转至退货界面
//            Intent intent = new Intent(MainActivity.this, ReturnGoodsActivity.class);
//            intent.putExtra("OrderCode", orderCode);
//            startActivity(intent);
//        }
//
//    }
//    //	// 设置回退
////	public boolean onKeyDown(int keyCode, KeyEvent event) {
////		if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {// && browser.canGoBack()
////			browser.goBack();
////			//finish();
////			return true;
////		}
//////		if(browser.canGoBack()){
//////			//finish();
//////			browser.goBack();
//////			return true;
//////		}
////		return super.onKeyDown(keyCode, event);
////	}
////    @Override
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////        ButterKnife.bind(this);
////        int mode = getIntent().getIntExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
////
////
////    }
//}
