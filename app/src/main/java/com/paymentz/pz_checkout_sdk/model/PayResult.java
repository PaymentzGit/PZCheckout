package com.paymentz.pz_checkout_sdk.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Admin on 8/27/2018.
 */

public class PayResult implements Serializable {

    private String trackingid;
    private String paymentid;
    private String desc;
    private String merchantTransactionid;
    private String status;
    private String checksum;
    private String descriptor;
    private String token;
    private String registrationid;
    private String firstname;
    private String lastname;
    private String currency;
    private String amount;
    private String tmpl_currency;
    private String tmpl_amount;
    private String timestamp;
    private String resultcode;
    private String resultdesc;
    private String cardbin;
    private String cardlastdigit;
    private String custemail;
    private String paymentmode;
    private String paymentbrand;

    public PayResult(String jsonString){

        try{

            JSONObject json = new JSONObject(jsonString);

            trackingid = json.getString("trackingid");
            status = json.getString("status");
            firstname = json.getString("firstName");
            lastname = json.getString("lastName");
            checksum = json.getString("checksum");
            desc = json.getString("desc");
            currency = json.getString("currency");
            amount = json.getString("amount");
            tmpl_currency = json.getString("tmpl_currency");
            tmpl_amount = json.getString("tmpl_amount");
            timestamp = json.getString("timestamp");
            resultcode = json.getString("resultCode");
            resultdesc = json.getString("resultDescription");
            if (json.has("cardBin")) {
                cardbin = json.getString("cardBin");
            }
            if (json.has("cardLast4Digits")) {
                cardlastdigit = json.getString("cardLast4Digits");
            }
            custemail = json.getString("custEmail");
            paymentmode = json.getString("paymentMode");
            paymentbrand = json.getString("paymentBrand");
            paymentid = json.getString("paymentId");
            merchantTransactionid = json.getString("merchantTransactionId");
            descriptor = json.getString("descriptor");

        } catch (JSONException e){
            Log.e(getClass().getSimpleName(), e.toString());
        }
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("trackingId", this.trackingid);
            jsonObject.put("status", this.status);
            jsonObject.put("firstName", this.firstname);
            jsonObject.put("lastName", this.lastname);
            jsonObject.put("checksum", this.checksum);
            jsonObject.put("desc", this.desc);
            jsonObject.put("currency", this.currency);
            jsonObject.put("amount", this.amount);
            jsonObject.put("tmpl_currency", this.tmpl_currency);
            jsonObject.put("tmpl_amount", this.tmpl_amount);
            jsonObject.put("timestamp", this.timestamp);
            jsonObject.put("resultCode", this.resultcode);
            jsonObject.put("resultDescription", this.resultdesc);
            jsonObject.put("cardBin", this.cardbin);
            jsonObject.put("cardLast4Digits", this.cardlastdigit);
            jsonObject.put("custEmail", this.custemail);
            jsonObject.put("paymentMode", this.paymentmode);
            jsonObject.put("paymentBrand", this.paymentbrand);
            jsonObject.put("paymentId", this.paymentid);
            jsonObject.put("merchantTransactionId", this.merchantTransactionid);
            jsonObject.put("descriptor", this.descriptor);

        } catch (JSONException e) {
            Log.e(getClass().getSimpleName(), e.toString());
        }

        return jsonObject.toString();
    }

    public String getTrackingid() {
        return trackingid;
    }

    public void setTrackingid(String trackingid) {
        this.trackingid = trackingid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMerchantTransactionid() {
        return merchantTransactionid;
    }

    public void setMerchantTransactionid(String merchantTransactionid) {
        this.merchantTransactionid = merchantTransactionid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRegistrationid() {
        return registrationid;
    }

    public void setRegistrationid(String registrationid) {
        this.registrationid = registrationid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTmpl_currency() {
        return tmpl_currency;
    }

    public void setTmpl_currency(String tmpl_currency) {
        this.tmpl_currency = tmpl_currency;
    }

    public String getTmpl_amount() {
        return tmpl_amount;
    }

    public void setTmpl_amount(String tmpl_amount) {
        this.tmpl_amount = tmpl_amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getResultdesc() {
        return resultdesc;
    }

    public void setResultdesc(String resultdesc) {
        this.resultdesc = resultdesc;
    }

    public String getCardbin() {
        return cardbin;
    }

    public void setCardbin(String cardbin) {
        this.cardbin = cardbin;
    }

    public String getCardlastdigit() {
        return cardlastdigit;
    }

    public void setCardlastdigit(String cardlastdigit) {
        this.cardlastdigit = cardlastdigit;
    }

    public String getCustemail() {
        return custemail;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getPaymentbrand() {
        return paymentbrand;
    }

    public void setPaymentbrand(String paymentbrand) {
        this.paymentbrand = paymentbrand;
    }
}
