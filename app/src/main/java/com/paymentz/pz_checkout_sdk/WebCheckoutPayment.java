package com.paymentz.pz_checkout_sdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
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

import com.paymentz.pz_checkout_sdk.model.PZCheckout;
import com.paymentz.pz_checkout_sdk.model.PayResult;
import com.paymentz.pz_checkout_sdk.model.RequestParameters;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;

/**
 * Created by Admin on 8/23/2018.
 */

public class WebCheckoutPayment extends AppCompatActivity {

    String params = "null";

    public interface WebCheckoutListener {
        void onSuccessful(PayResult payResult);
        void onFail(String title, String message);
    }

    private WebCheckoutListener webCheckoutListener;

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

            String hostname = null;
            try {
                hostname = getHostname(requestParameters.getHostUrl());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            String merchantRedirectUrl = "https://"+hostname+"/transaction/InvoiceAppFrontEndServlet?deviceType=android";
            String dataForMd5Hash = requestParameters.getMemberId() + "|" + requestParameters.getToType()
                    + "|" + requestParameters.getAmount() + "|" + requestParameters.getMerchantTransactionId()
                    + "|" + merchantRedirectUrl + "|" +requestParameters.getMemberKey();


            requestParameters.setChecksum(generateMD5ChecksumDirectKit(dataForMd5Hash));

            params  = "&memberId="+requestParameters.getMemberId()
                    +"&totype="+requestParameters.getToType()
                    +"&mkey="+requestParameters.getMemberKey()
                    +"&merchantTransactionId="+requestParameters.getMerchantTransactionId()
                    +"&amount="+requestParameters.getAmount()
                    +"&TMPL_AMOUNT="+requestParameters.getAmount()
                    +"&orderDescription="+requestParameters.getOrderDescription()
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
                    +"&TMPL_CURRENCY="+requestParameters.getCurrency()
                    +"&terminalid="+requestParameters.getTerminalId()
                    +"&notificationUrl="+requestParameters.getNotificationUrl()
                    +"&checksum="+requestParameters.getChecksum()
                    +"&paymentMode="+requestParameters.getPaymentMode()
                    +"&paymentBrand="+requestParameters.getPaymentBrand()
                    +"&merchantRedirectUrl="+merchantRedirectUrl
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
        /*if (jsonString != null && !jsonString.isEmpty()) {
            PayResult payResult = new PayResult(jsonString);
            webCheckoutListener.onSuccessful(payResult);
        } else {
            webCheckoutListener.onFail("Fail", "Something went wrong");
        }*/
        Intent data = new Intent();
        data.putExtra(PZCheckout.PAYMENT_RESULT,new PayResult(jsonString));
        setResult(RESULT_OK,data);
        finish();
    }

    private String getHostname(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return uri.getHost();
    }

    private String generateMD5ChecksumDirectKit(String message) {

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return getString(messageDigest.digest(message.getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    private String getString(byte[] buf)
    {
        StringBuilder sb = new StringBuilder(2 * buf.length);
        for (byte b : buf) {
            int h = (b & 0xf0) >> 4;
            int l = (b & 0x0f);
            sb.append(new Character((char) ((h > 9) ? 'a' + h - 10 : '0' + h)));
            sb.append(new Character((char) ((l > 9) ? 'a' + l - 10 : '0' + l)));
        }
        return sb.toString();
    }
}
