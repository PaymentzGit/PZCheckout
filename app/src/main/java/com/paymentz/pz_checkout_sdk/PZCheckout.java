package com.paymentz.pz_checkout_sdk;

import android.app.Activity;
import android.content.Intent;

import com.paymentz.pz_checkout_sdk.model.PayRequest;
import com.paymentz.pz_checkout_sdk.model.PayResult;

/**
 * Created by Admin on 8/23/2018.
 */

public class PZCheckout {

    public interface WebCheckoutListener {
        void onTransactionSuccess(PayResult payResult);
        void onTransactionFail(PayResult payResult);
    }

    public static WebCheckoutListener webCheckoutListener;

    public void initPayment(Activity activity, PayRequest payRequest, WebCheckoutListener webCheckoutListener) {
        PZCheckout.webCheckoutListener = webCheckoutListener;
        Intent intent = new Intent(activity, WebCheckoutPayment.class);
        intent.putExtra("requestParameters", payRequest);
        activity.startActivity(intent);
    }
}
