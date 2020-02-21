package com.paymentz.pz_checkout_sdk.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.paymentz.pz_checkout_sdk.CheckoutActivity;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 * Created by Admin on 8/23/2018.
 */

public class PayEncrypt implements Serializable {

    public static final int PAYMENT_REQUEST_CODE = 2568;
    public static final String PAYMENT_RESULT = "PAYMENT_RESULT";

    public static void initPayment(AppCompatActivity activity, RequestParameters requestParameters) {

        Intent intent = new Intent(activity,CheckoutActivity.class);

        intent.putExtra("requestParameters",requestParameters);
        activity.startActivityForResult(intent,PAYMENT_REQUEST_CODE);
    }

    public static String generateMD5ChecksumDirectKit(String message) {

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return getString(messageDigest.digest(message.getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    private static String getString(byte buf[])
    {
        StringBuffer sb = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++)
        {
            int h = (buf[i] & 0xf0) >> 4;
            int l = (buf[i] & 0x0f);
            sb.append(new Character((char) ((h > 9) ? 'a' + h - 10 : '0' + h)));
            sb.append(new Character((char) ((l > 9) ? 'a' + l - 10 : '0' + l)));
        }
        return sb.toString();
    }
}
