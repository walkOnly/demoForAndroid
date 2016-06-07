package com.hhxc.demo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hhxc.demo.event.OnWxpayEvent;
import com.hhxc.demo.pay.Config;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Config.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调该方法
    @Override
    public void onReq(BaseReq req) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调该方法
    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle(R.string.app_tip);
//            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//            builder.show();

            OnWxpayEvent event = new OnWxpayEvent(resp.errCode, getErrorMsg(resp.errCode));
            EventBus.getDefault().post(event);
            finish();
        }
    }

    private static String getErrorMsg(int errCode) {
        String ret = "";
        switch (errCode) {
            case 0:
                ret = "支付成功";
                break;
            case -1:
                ret = "支付出错";
                break;
            case -2:
                ret = "支付被取消";
                break;
            default:
                ret = "未知错误";
                break;
        }
        return ret;
    }

}
