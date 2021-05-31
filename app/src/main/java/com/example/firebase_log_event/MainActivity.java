package com.example.firebase_log_event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.firebase_log_event.NextActivity;
import com.example.firebase_log_event.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    public static String[]user_pseudo_id= new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NextActivity이동 버튼
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NextActivity.class);
                startActivity(i);
            }
        });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //mFirebaseAnalytics.setAnalyticsCollectionEnabled(false);  // 모든 이벤트 전송 삭제
        //mFirebaseAnalytics.setUserId("20210419_bigquerytest_2"); // user id 설정

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);

        mFirebaseAnalytics.setUserProperty("user_property1", String.valueOf(mDate)); //userid 는 userproperty로 세팅해서 전송시 데이터 전송 X
        
        //clientId 가져오는 설정
        FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(new OnCompleteListener<String>() {
            @SuppressLint("InvalidAnalyticsName")
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {

                    user_pseudo_id[0] = task.getResult();
                    Log.v("Firebase Instance ID = " , user_pseudo_id[0]);
                    //UserProperty
                    //mFirebaseAnalytics.setUserProperty("user_property2",user_pseudo_id[0]);

                    // 여기 안으로 들어오면 onResume 단계가 되는듯 그래서 screen_view 이벤트가 전송될 수 있음

                    Bundle bundle = new Bundle(); //Bundle 객체 생성
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "0512"); //보고서-페이지 제목 및 화면 이름 or 이벤트-firebase_screen
                    bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "0512"); //보고서-이벤트-screen_view-firebase_screen_class
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle); //logEvent(이벤트 명, bundle
                    
                    //mFirebaseAnalytics.logEvent(FirebaseAnalytics.Param.);

/*
        Bundle itemJeggings = new Bundle();
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_ID,"SKU_123");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_NAME,"jeggings");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"pants");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"black");
        itemJeggings.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Google");
        itemJeggings.putDouble(FirebaseAnalytics.Param.PRICE,9.99);
        itemJeggings.putString("dimension1","20210224_myshin");

            Bundle itemBoots = new Bundle();
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_ID,"SKU_456");
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_NAME,"boots");
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"shoes");
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"brown");
        itemBoots.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Google");
        itemBoots.putDouble(FirebaseAnalytics.Param.PRICE,24.99);

            Bundle itemSocks = new Bundle();
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_ID,"SKU_789");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_NAME,"ankle_socks");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,"socks");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_VARIANT,"red");
        itemSocks.putString(FirebaseAnalytics.Param.ITEM_BRAND,"Google");
        itemSocks.putDouble(FirebaseAnalytics.Param.PRICE,5.99);
            Bundle itemJeggingsWithIndex = new Bundle(itemJeggings);
        itemJeggingsWithIndex.putLong(FirebaseAnalytics.Param.INDEX,1);
        itemJeggingsWithIndex.putString("dimension1","20210224_myshin1");

            Bundle itemBootsWithIndex = new Bundle(itemBoots);
        itemBootsWithIndex.putLong(FirebaseAnalytics.Param.INDEX,2);

            Bundle itemSocksWithIndex = new Bundle(itemSocks);
        itemSocksWithIndex.putLong(FirebaseAnalytics.Param.INDEX,3);

            Bundle viewItemListParams = new Bundle();
        viewItemListParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID,"L001");
        viewItemListParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME,"Related products");
        viewItemListParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggingsWithIndex, itemBootsWithIndex, itemSocksWithIndex
            });
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST,viewItemListParams);


            Bundle selectItemParams = new Bundle();
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_ID,"L001");
        selectItemParams.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME,"Related products");
        selectItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggings
            });
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM,selectItemParams);
            Bundle viewItemParams = new Bundle();
        viewItemParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        viewItemParams.putDouble(FirebaseAnalytics.Param.VALUE,9.99);
        viewItemParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggings
            });

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM,viewItemParams);
            Bundle itemJeggingsWishlist = new Bundle(itemJeggings);
        itemJeggingsWishlist.putLong(FirebaseAnalytics.Param.QUANTITY,2);
        itemJeggingsWishlist.putString("dimension1","20210224_myshin2");

            Bundle addToWishlistParams = new Bundle();
        addToWishlistParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        addToWishlistParams.putDouble(FirebaseAnalytics.Param.VALUE,2*9.99);
        addToWishlistParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggingsWishlist
            });

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST,addToWishlistParams);
            Bundle itemJeggingsCart = new Bundle(itemJeggings);
        itemJeggingsCart.putLong(FirebaseAnalytics.Param.QUANTITY,2);
        itemJeggingsCart.putString("dimension1","20210224_myshin3");

            Bundle itemBootsCart = new Bundle(itemBoots);
        itemBootsCart.putLong(FirebaseAnalytics.Param.QUANTITY,1);

            Bundle viewCartParams = new Bundle();
        viewCartParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        viewCartParams.putDouble(FirebaseAnalytics.Param.VALUE,(2*9.99)+(1*24.99));
        viewCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggingsCart, itemBootsCart
            });

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART,viewCartParams);
            Bundle removeCartParams = new Bundle();
        removeCartParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        removeCartParams.putDouble(FirebaseAnalytics.Param.VALUE,(1*24.99));
        removeCartParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemBootsCart
            });

            Bundle beginCheckoutParams = new Bundle();
        beginCheckoutParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        beginCheckoutParams.putDouble(FirebaseAnalytics.Param.VALUE,14.98);
        beginCheckoutParams.putString(FirebaseAnalytics.Param.COUPON,"SUMMER_FUN");
        beginCheckoutParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggingsCart
            });

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT,beginCheckoutParams);


            Bundle addShippingParams = new Bundle();
        addShippingParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        addShippingParams.putDouble(FirebaseAnalytics.Param.VALUE,14.98);
        addShippingParams.putString(FirebaseAnalytics.Param.COUPON,"SUMMER_FUN");
        addShippingParams.putString(FirebaseAnalytics.Param.SHIPPING_TIER,"Ground");
        addShippingParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggingsCart
            });

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO,addShippingParams);
            Bundle addPaymentParams = new Bundle();
        addPaymentParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        addPaymentParams.putDouble(FirebaseAnalytics.Param.VALUE,14.98);
        addPaymentParams.putString(FirebaseAnalytics.Param.COUPON,"SUMMER_FUN");
        addPaymentParams.putString(FirebaseAnalytics.Param.PAYMENT_TYPE,"Visa");
        addPaymentParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggingsCart
            });

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO,addPaymentParams);
            Bundle purchaseParams = new Bundle();
        purchaseParams.putString(FirebaseAnalytics.Param.TRANSACTION_ID,"T12345");
        purchaseParams.putString(FirebaseAnalytics.Param.AFFILIATION,"Google Store");
        purchaseParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        purchaseParams.putDouble(FirebaseAnalytics.Param.VALUE,14.98);
        purchaseParams.putDouble(FirebaseAnalytics.Param.TAX,2.58);
        purchaseParams.putDouble(FirebaseAnalytics.Param.SHIPPING,5.34);
        purchaseParams.putString(FirebaseAnalytics.Param.COUPON,"SUMMER_FUN");
        purchaseParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggingsCart
            });
        purchaseParams.putString("dimension3","dim3");
        purchaseParams.putString("dimension4","dim4");

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE,purchaseParams);
            Bundle refundParams = new Bundle();
        refundParams.putString(FirebaseAnalytics.Param.TRANSACTION_ID,"T12345");
        refundParams.putString(FirebaseAnalytics.Param.AFFILIATION,"Google Store");
        refundParams.putString(FirebaseAnalytics.Param.CURRENCY,"USD");
        refundParams.putDouble(FirebaseAnalytics.Param.VALUE,9.99);


        refundParams.putString(FirebaseAnalytics.Param.ITEM_ID,"SKU_123");
        refundParams.putLong(FirebaseAnalytics.Param.QUANTITY,1);

        refundParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggings
            });

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REFUND,refundParams);
            Bundle promoParams = new Bundle();
        promoParams.putString(FirebaseAnalytics.Param.PROMOTION_ID,"SUMMER_FUN");
        promoParams.putString(FirebaseAnalytics.Param.PROMOTION_NAME,"Summer Sale");
        promoParams.putString(FirebaseAnalytics.Param.CREATIVE_NAME,"summer2020_promo.jpg");
        promoParams.putString(FirebaseAnalytics.Param.CREATIVE_SLOT,"featured_app_1");
        promoParams.putString(FirebaseAnalytics.Param.LOCATION_ID,"HERO_BANNER");
        promoParams.putParcelableArray(FirebaseAnalytics.Param.ITEMS,
                    new Parcelable[]

            {
                itemJeggings
            });

// Promotion displayed
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_PROMOTION,promoParams);

// Promotion selected
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_PROMOTION,promoParams);
        }
         */
                }
            }//onComplete
        });
    }

    //수동화면추적
    // Override 를 통해 정의되어 있는 onResume 을 사용해야만 이 메소드가 동작한다.


    @Override
    protected void onResume(){
        super.onResume();
        /*
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //수동화면추적
        //SCREEN_VIEW 이벤트를 수동으로 로깅할 수 있습니다.

        Bundle bundle = new Bundle(); //Bundle 객체 생성
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "20210226_fmain1"); //보고서-페이지 제목 및 화면 이름 or 이벤트-firebase_screen

        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "20210226_fmain1"); //보고서-이벤트-screen_view-firebase_screen_class
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle); //logEvent(이벤트 명, bundle)

        */
        //더 이상 지원 안 되는 서비스3
        //mFirebaseAnalytics.setCurrentScreen(this, "FinalActivity", null ); //보고서-페이지 제목 및 화면 이름
    }

    //웹앱 인터페이스

    public static class WebAppInterface extends Activity {

        private FirebaseAnalytics mFirebaseAnalytics;

        @SuppressLint("InvalidAnalyticsName")
        @JavascriptInterface
        public void GA_DATA(String JsonData) throws JSONException { //Webview 내에서 자바스크립트 코드내에서 불러올 클래

            JSONObject data = new JSONObject(JsonData);
            Bundle bundle = new Bundle(); //Bundle 객체 생성
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

            String en = "",title="", location ="", type= "";

            if (data.has("eventname")) en = data.getString("eventname");
            if (data.has("title")) title = data.getString("title");
            if (data.has("location")) location = data.getString("location");
            if (data.has("type")) type = data.getString("type");

            // metric, dimension, userproperty
            Iterator<String> sIterator= data.keys();
            while(sIterator.hasNext()) {
                String a = String.valueOf(sIterator.next());
                if(a.contains("dimension")) bundle.putString(a, data.getString(a));
                if(a.contains("metric")) bundle.putString(a, data.getString(a));
                if(a.contains("user_property")) mFirebaseAnalytics.setUserProperty(a, data.getString(a));
            }
            if (type.contains("P")) { //스크린뷰 일때
                bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, title); //보고서-페이지 제목 및 화면 이름 or 이벤트-firebase_screen
                bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, location); //보고서-이벤트-screen_view-firebase_screen_class
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle); //logEvent(이벤트 명, bundle)
            }
            else { //이벤트일 때

                Bundle [] bArr = new Bundle[data.length()];
                int acnt =0;
                JSONObject transaction = data.getJSONObject("transaction");
                if (transaction.has("transaction_id")) bundle.putString(FirebaseAnalytics.Param.TRANSACTION_ID,transaction.getString("transaction_id"));
                if (transaction.has("affiliation")) bundle.putString(FirebaseAnalytics.Param.AFFILIATION,transaction.getString("affiliation"));
                if (transaction.has("value")) bundle.putDouble(FirebaseAnalytics.Param.VALUE,transaction.getDouble("value"));
                if (transaction.has("currency")) bundle.putString(FirebaseAnalytics.Param.CURRENCY,transaction.getString("currency"));
                if (transaction.has("tax")) bundle.putDouble(FirebaseAnalytics.Param.TAX,transaction.getDouble("tax"));
                if (transaction.has("shipping")) bundle.putDouble(FirebaseAnalytics.Param.SHIPPING,transaction.getDouble("shipping"));
                if (transaction.has("shipping_tier")) bundle.putString(FirebaseAnalytics.Param.SHIPPING_TIER,transaction.getString("shipping_tier"));
                if (transaction.has("payment_type")) bundle.putString(FirebaseAnalytics.Param.PAYMENT_TYPE,transaction.getString("payment_type"));
                if (transaction.has("coupon")) bundle.putString(FirebaseAnalytics.Param.COUPON,transaction.getString("coupon"));
                if (transaction.has("location_id")) bundle.putString(FirebaseAnalytics.Param.LOCATION_ID,transaction.getString("location_id"));
                if (transaction.has("item_list_name")) bundle.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME,transaction.getString("item_list_name"));
                if (transaction.has("item_list_id")) bundle.putString(FirebaseAnalytics.Param.ITEM_LIST_ID,transaction.getString("item_list_id"));

                JSONArray products = data.getJSONArray("Products");
                for (int i=0 ;i< products.length(); i++){
                    Bundle items = new Bundle();
                    JSONObject item = null;
                    item = products.getJSONObject(Integer.parseInt(String.valueOf(i)));
                    if (item == null) {break;}
                    if (item.has("item_id")) items.putString(FirebaseAnalytics.Param.ITEM_ID,item.getString("item_id"));
                    if (item.has("item_name")) items.putString(FirebaseAnalytics.Param.ITEM_NAME,item.getString("item_name"));
                    if (item.has("item_list_name")) items.putString(FirebaseAnalytics.Param.ITEM_LIST_NAME,item.getString("item_list_name"));
                    if (item.has("item_list_id")) items.putString(FirebaseAnalytics.Param.ITEM_LIST_ID,item.getString("item_list_id"));
                    if (item.has("index")) items.putLong(FirebaseAnalytics.Param.INDEX,item.getLong("index"));
                    if (item.has("item_brand")) items.putString(FirebaseAnalytics.Param.ITEM_BRAND,item.getString("item_brand"));
                    if (item.has("item_category")) items.putString(FirebaseAnalytics.Param.ITEM_CATEGORY,item.getString("item_category"));
                    if (item.has("item_category2")) items.putString(FirebaseAnalytics.Param.ITEM_CATEGORY2,item.getString("item_category2"));
                    if (item.has("item_category3")) items.putString(FirebaseAnalytics.Param.ITEM_CATEGORY3,item.getString("item_category3"));
                    if (item.has("item_category4")) items.putString(FirebaseAnalytics.Param.ITEM_CATEGORY4,item.getString("item_category4"));
                    if (item.has("item_category5")) items.putString(FirebaseAnalytics.Param.ITEM_CATEGORY5,item.getString("item_category5"));
                    if (item.has("item_variant")) items.putString(FirebaseAnalytics.Param.ITEM_VARIANT,item.getString("item_variant"));
                    if (item.has("affiliation")) items.putString(FirebaseAnalytics.Param.AFFILIATION,item.getString("affiliation"));
                    if (item.has("discount")) items.putDouble(FirebaseAnalytics.Param.DISCOUNT,item.getDouble("discount"));
                    if (item.has("coupon")) items.putDouble(FirebaseAnalytics.Param.COUPON,item.getDouble("coupon"));
                    if (item.has("price")) items.putDouble(FirebaseAnalytics.Param.PRICE,item.getDouble("price"));
                    if (item.has("currency")) items.putDouble(FirebaseAnalytics.Param.CURRENCY,item.getDouble("currency"));
                    if (item.has("quantity")) items.putLong(FirebaseAnalytics.Param.QUANTITY,item.getLong("quantity"));

                    // item 내에서의 diemension 분기 
//                    Iterator<String> Iterator= item.keys();
//                    while(Iterator.hasNext()) {
//                        try {
//                            String a = String.valueOf(Iterator.next());
//                            if (a.contains("dimension")) items.putString(a, item.getString(a));
//                            if (a.contains("metric")) items.putString(a, item.getString(a));
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }

                    bArr[i] = items;
                    items=null;
                    acnt++;
                }
                Bundle [] newbArr = new Bundle[acnt];
                for(int j=0; j<newbArr.length;j++)
                {
                    if(!bArr[j].isEmpty() || bArr[j] != null) {
                        newbArr[j] = bArr[j]; //newbArr 이 상품 bundle
                    }
                }

                bundle.putParcelableArray(FirebaseAnalytics.Param.ITEMS,newbArr);

                if (en.contains("view_item_list")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle);}
                else if (en.contains("select_item")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle);}
                else if (en.contains("view_item")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);}
                else if (en.contains("add_to_cart")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, bundle);}
                else if (en.contains("add_to_wishlist")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_WISHLIST, bundle);}
                else if (en.contains("remove_from_cart")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART, bundle);}
                else if (en.contains("view_cart")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART, bundle);}
                else if (en.contains("begin_checkout")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.BEGIN_CHECKOUT, bundle);}
                else if (en.contains("add_shipping_info")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_SHIPPING_INFO, bundle);}
                else if (en.contains("add_payment_info")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, bundle);}
                else if (en.contains("purchase")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, bundle);}
                else if (en.contains("refund")) {mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REFUND, bundle);}

            }
        }
    }
}
