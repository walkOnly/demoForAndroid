package com.hhxc.demo.pay.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.hhxc.demo.event.OnAlipayEvent;
import com.hhxc.demo.event.OnWxpayEvent;

import org.greenrobot.eventbus.EventBus;

public class AlipayTask extends AsyncTask<Void, Void, Integer> {

    private Context context;
    private String title, price, orderNumber, extras, notifyUrl;

    public AlipayTask(Context context, String title, String price, String orderNumber, String extras, String notifyUrl) {
        this.context = context;
        this.title = title;
        this.price = price;
        this.orderNumber = orderNumber;
        this.extras = extras;
        this.notifyUrl = notifyUrl;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int payResult = AlipayUtils.pay((Activity) context, title, extras, price, orderNumber, extras, notifyUrl);
        return payResult;
    }

    @Override
    protected void onPostExecute(Integer result) {
        int errCode = result;
        String errMsg = AlipayUtils.getErrorMsg(errCode);

        OnAlipayEvent event = new OnAlipayEvent(errCode, errMsg);
        EventBus.getDefault().post(event);
        //finish();
    }

}
