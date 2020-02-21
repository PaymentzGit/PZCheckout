package com.paymentz.pz_checkout_sdk;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.paymentz.pz_checkout_sdk.model.PayEncrypt;
import com.paymentz.pz_checkout_sdk.model.PayResult;
import com.paymentz.pz_checkout_sdk.model.RequestParameters;

import java.net.URLEncoder;

/**
 * Created by Admin on 8/23/2018.
 */

public class CheckoutActivity extends AppCompatActivity {

    String params = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getSupportActionBar().setTitle("Payment");

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        RequestParameters requestParameters = (RequestParameters) getIntent().getExtras().get("requestParameters");

        WebView webView = (WebView) findViewById(R.id.webView_checkout);
        webView.getSettings();
        webView.setBackground(getResources().getDrawable(R.color.colorWhite));
       // webView.setBackgroundColor(R.drawable.background);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("WEB VIEW ERROR: ", error.toString());
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("WEB VIEW ERROR: ", error.toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("Current URL: ", url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient());

        if(requestParameters != null) {

            String dataForMd5Hash = requestParameters.getMemberId() + "|" + requestParameters.getToType()
                    + "|" + requestParameters.getAmount() + "|" + requestParameters.getMerchantTransactionId()
                    + "|" + requestParameters.getMerchantRedirectUrl() + "|" +requestParameters.getMemberKey();


            requestParameters.setChecksum(PayEncrypt.generateMD5ChecksumDirectKit(dataForMd5Hash));

            params  = "&device=android"
                    +"&memberId="+requestParameters.getMemberId()
                    +"&totype="+requestParameters.getToType()
                    +"&mkey="+requestParameters.getMemberKey()
                    +"&merchantTransactionId="+requestParameters.getMerchantTransactionId()
                    +"&amount="+requestParameters.getAmount()
                    +"&TMPL_AMOUNT="+requestParameters.getTmplAmount()
                    +"&orderDescription="+requestParameters.getOrderDescription()
                    +"&merchantRedirectUrl="+requestParameters.getMerchantRedirectUrl()
                    +"&country="+requestParameters.getCountry()
                    +"&city="+requestParameters.getCity()
                    +"&state="+requestParameters.getState()
                    +"&postcode="+requestParameters.getPostCode()
                    +"&street="+requestParameters.getStreet()
                    +"&telnocc="+requestParameters.getTelnocc()
                    +"&phone="+requestParameters.getPhone()
                    +"&email="+requestParameters.getEmail()
                    +"&ip="+requestParameters.getIpAddress()
                    +"&ipaddr="+requestParameters.getIpAddress()
                    +"&currency="+requestParameters.getCurrency()
                    +"&TMPL_CURRENCY="+requestParameters.getTmplCurrency()
                    +"&terminalid="+requestParameters.getTerminalId()
                    +"&notificationUrl="+requestParameters.getNotificationUrl()
                    +"&checksum="+requestParameters.getChecksum()
                    +"&paymentMode="+requestParameters.getPaymentMode()
                    +"&paymentBrand="+requestParameters.getPaymentBrand()
                    +"&device=android";

            if (!params.isEmpty()) {

                webView.postUrl(requestParameters.getHostUrl()+"?", params.getBytes());

                webView.getSettings().setJavaScriptEnabled(true);
                webView.addJavascriptInterface(this,"android");

            } else {
                Toast.makeText(this, "Please, define a valid payment brand", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @JavascriptInterface
    public void paymentResultListener(String jsonString)
    {
        System.out.println("Payment Result "+jsonString);
        Intent data = new Intent();
        data.putExtra(PayEncrypt.PAYMENT_RESULT,new PayResult(jsonString));
        setResult(RESULT_OK,data);
        finish();
    }
}
