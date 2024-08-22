package com.paymentz.pz_checkout_sdk.model;

import android.app.Activity;
import android.content.Intent;
import com.paymentz.pz_checkout_sdk.WebCheckoutPayment;
import java.io.Serializable;

/**
 * Created by Admin on 8/23/2018.
 */

public class PZCheckout implements Serializable {

    public static final int PAYMENT_REQUEST_CODE = 2568;
    public static final String PAYMENT_RESULT = "PAYMENT_RESULT";

    private PZCheckoutListener pzCheckoutListener;

    public void initPayment(Activity activity, PayRequest requestParameters) {
        Intent intent = new Intent(activity,WebCheckoutPayment.class);
        intent.putExtra("requestParameters",requestParameters);
        activity.startActivityForResult(intent,PAYMENT_REQUEST_CODE);
    }


    public interface PZCheckoutListener {
        void onSuccess(PayResult payResult);
        void onFailure(String title, String message);
    }

}
