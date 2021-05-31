package com.example.firebase_log_event;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

public class NextActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        //이벤트 로깅 (이벤트 이름 : NEvent)
        final WebView mWebView1 = (WebView) findViewById(R.id.webview);

        mWebView1.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게
        mWebView1.setWebChromeClient(new WebChromeClient());
        mWebView1.getSettings().setJavaScriptEnabled(true);
        mWebView1.loadUrl("http://210.114.9.23/GA_part/myshin/gav4.html"); // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        //수동화면추적
        //SCREEN_VIEW 이벤트를 수동으로 로깅할 수 있습니다.

        String userAgent = mWebView1.getSettings().getUserAgentString(); // 기존 useragentstring을 받아와
        mWebView1.getSettings().setUserAgentString(userAgent+"/GA_Android");
        mWebView1.addJavascriptInterface(new MainActivity.WebAppInterface(),"gascriptAndroid");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    //수동화면추적

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAnalytics2 = FirebaseAnalytics.getInstance(this);
        //수동화면추적
        //SCREEN_VIEW 이벤트를 수동으로 로깅할 수 있습니다.

        Bundle bundle = new Bundle(); //Bundle 객체 생성
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "20210419_fmain2"); //보고서-페이지 제목 및 화면 이름 or 이벤트-firebase_screen
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "20210419_fmain2"); //보고서-이벤트-screen_view-firebase_screen_class
        mFirebaseAnalytics2.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle); //logEvent(이벤트 명, bundle)


        //더 이상 지원 안 되는 서비스
        //mFirebaseAnalytics.setCurrentScreen(this, "FinalActivity", null ); //보고서-페이지 제목 및 화면 이름
    }

}
