package com.paymentz.pz_checkout_sdk.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.paymentz.pz_checkout_sdk.WebCheckoutPayment;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 * Created by Admin on 8/23/2018.
 */

public class PZCheckout implements Serializable {

    public static final int PAYMENT_REQUEST_CODE = 2568;
    public static final String PAYMENT_RESULT = "PAYMENT_RESULT";

    private PZCheckoutListener pzCheckoutListener;

    public void initPayment(Activity activity, RequestParameters requestParameters) {
        Intent intent = new Intent(activity,WebCheckoutPayment.class);
        intent.putExtra("requestParameters",requestParameters);
        activity.startActivityForResult(intent,PAYMENT_REQUEST_CODE);
    }

    /*@Override
    public void onSuccessful(PayResult payResult) {
        System.out.println("onSuccess::::");
        pzCheckoutListener.onSuccess(payResult);
    }

    @Override
    public void onFail(String title, String message) {
        System.out.println("onFail::::");
        pzCheckoutListener.onFailure(title, message);
    }*/

    public interface PZCheckoutListener {
        void onSuccess(PayResult payResult);
        void onFailure(String title, String message);
    }

}
