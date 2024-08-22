package com.paymentz.pz_checkout_sdk;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;

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

import com.paymentz.pz_checkout_sdk.model.PayResult;
import com.paymentz.pz_checkout_sdk.model.PayRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.Objects;

/**
 * Created by Admin on 8/23/2018.
 */

 public class WebCheckoutPayment extends Activity {

    String params;
    WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        PayRequest payRequest = (PayRequest) Objects.requireNonNull(getIntent().getExtras()).get("requestParameters");
        webView = findViewById(R.id.webView_checkout);
        webView.getSettings();
        webView.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return shouldOverrideUrlLoading(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient());

        if(payRequest != null) {

            String hostname;
            try {
                hostname = getHostname(payRequest.getHostUrl());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            String merchantRedirectUrl = "https://"+hostname+"/transaction/InvoiceAppFrontEndServlet?deviceType=android";
            String dataForMd5Hash = payRequest.getMemberId() + "|" + payRequest.getToType()
                    + "|" + payRequest.getAmount() + "|" + payRequest.getMerchantTransactionId()
                    + "|" + merchantRedirectUrl + "|" + payRequest.getMemberKey();


            payRequest.setChecksum(generateMD5ChecksumDirectKit(dataForMd5Hash));

            params  = "&memberId="+ payRequest.getMemberId()
                    +"&totype="+ payRequest.getToType()
                    +"&mkey="+ payRequest.getMemberKey()
                    +"&merchantTransactionId="+ payRequest.getMerchantTransactionId()
                    +"&amount="+ payRequest.getAmount()
                    +"&TMPL_AMOUNT="+ payRequest.getAmount()
                    +"&orderDescription="+ payRequest.getOrderDescription()
                    +"&country="+ payRequest.getCountry()
                    +"&city="+ payRequest.getCity()
                    +"&state="+ payRequest.getState()
                    +"&postcode="+ payRequest.getPostCode()
                    +"&street="+ payRequest.getStreet()
                    +"&telnocc="+ payRequest.getTelnocc()
                    +"&phone="+ payRequest.getPhone()
                    +"&email="+ payRequest.getEmail()
                    +"&ip="+ payRequest.getIpAddress()
                    +"&ipaddr="+ payRequest.getIpAddress()
                    +"&currency="+ payRequest.getCurrency()
                    +"&TMPL_CURRENCY="+ payRequest.getCurrency()
                    +"&terminalid="+ payRequest.getTerminalId()
                    +"&notificationUrl="+ payRequest.getNotificationUrl()
                    +"&checksum="+ payRequest.getChecksum()
                    +"&paymentMode="+ payRequest.getPaymentMode()
                    +"&paymentBrand="+ payRequest.getPaymentBrand()
                    +"&merchantRedirectUrl="+merchantRedirectUrl
                    +"&device=android";

            webView.postUrl(payRequest.getHostUrl() + "?", params.getBytes());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.addJavascriptInterface(this,"android");

        }
    }

    @JavascriptInterface
    public void paymentResultListener(String jsonString)
    {
        System.out.println("Payment Result "+jsonString);
        PayResult payResult = new PayResult(jsonString);
        if (Objects.equals(payResult.getStatus(), "Y")) {
            PZCheckout.webCheckoutListener.onTransactionSuccess(payResult);
        }else {
            PZCheckout.webCheckoutListener.onTransactionFail(payResult);
        }
        this.finish();
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
            Log.e( this.getLocalClassName(),e.toString());
        }
        return message;
    }

    private String getString(byte[] buf)
    {
        StringBuilder sb = new StringBuilder(2 * buf.length);
        for (byte b : buf) {
            int h = (b & 0xf0) >> 4;
            int l = (b & 0x0f);
            sb.append(Character.valueOf((char) ((h > 9) ? 'a' + h - 10 : '0' + h)));
            sb.append(Character.valueOf((char) ((l > 9) ? 'a' + l - 10 : '0' + l)));
        }
        return sb.toString();
    }
}

