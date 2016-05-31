//package com.exc.ui.activity;
//
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.exc.R;
//import com.exc.app.LocalStorage;
//import com.exc.http.Api;
//import com.exc.http.GsonResponseHandler;
//import com.exc.model.BaseResponse;
//import com.exc.model.LoginBean;
//import com.exc.ui.view.MyAppTitle;
//import com.exc.utils.Tip;
//
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.android.api.TagAliasCallback;
//import cz.msebera.android.httpclient.Header;
//import me.walkonly.lib.activity.BaseActivity;
//
//public class LoginActivity extends BaseActivity {
//
//    @Bind(R.id.app_title)
//    MyAppTitle mMyAppTitle;
//
//    @Bind(R.id.phone_num)
//    EditText phone_num;
//
//    @Bind(R.id.verify_code)
//    EditText verify_code;
//
//    @Bind(R.id.get_verify_code)
//    Button get_verify_code;
//
//    @Bind(R.id.login_btn)
//    Button login_btn;
//    private boolean isLoading = false;
//
//    @Override
//    public int getContentViewID() {
//        return R.layout.activity_login;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setMyAppTitle();
//        initView();
//    }
//
//    private void initView() {
//        this.phone_num.addTextChangedListener(mPhoneTextWatcher);
//        this.verify_code.addTextChangedListener(mVerifyCodeTextWatcher);
//        this.verify_code.setEnabled(false);
//        this.login_btn.setEnabled(false);
//        phone_num.setText("");
//        verify_code.setText("");
//    }
//
//    private TextWatcher mPhoneTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            String tempPhone = s.toString().trim();
//            if (checkCellPhone(tempPhone, false)) {
//                setmSendVerifyEnable(true);
//                phone_num.removeTextChangedListener(mPhoneTextWatcher);
//            }
//        }
//    };
//
//    private TextWatcher mVerifyCodeTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            String tempPhone = s.toString().trim();
//            Pattern pattern = Pattern.compile("\\d{6}");
//            Matcher matcher = pattern.matcher(tempPhone);
//            if (matcher.matches()) {
//                setmLoginEnable(true);
//                verify_code.removeTextChangedListener(mVerifyCodeTextWatcher);
//            }
//        }
//    };
//
//    private void setmSendVerifyEnable(boolean isEnable) {
//        this.verify_code.setEnabled(isEnable);
//    }
//
//    private void setmLoginEnable(boolean isEnable) {
//        this.login_btn.setEnabled(isEnable);
//        if (isEnable) {
//            this.login_btn.setTextColor(getResources().getColor(R.color.black));
//            this.login_btn.setBackgroundResource(R.drawable.login_btn_bg);
//        } else {
//            this.login_btn.setTextColor(getResources().getColor(R.color.hint_gray));
//            this.login_btn.setBackgroundResource(R.drawable.login_btn_bg_pressed);
//        }
//    }
//
//    /**
//     * 倒计时
//     */
//    private CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            if (get_verify_code == null) return;
//            get_verify_code.setText(millisUntilFinished / 1000 + getString(R.string.after_send));
//            get_verify_code.setTextColor(getResources().getColor(R.color.black));
//            get_verify_code.setBackgroundResource(R.drawable.big_btn_bg);
//            get_verify_code.setClickable(false);
//        }
//
//        @Override
//        public void onFinish() {
//            get_verify_code.setText(getString(R.string.resend));
//            get_verify_code.setTextColor(getResources().getColor(R.color.black));
//            get_verify_code.setBackgroundResource(R.drawable.big_btn_bg);
//            get_verify_code.setClickable(true);
//        }
//    };
//
//    @Override
//    protected void onDestroy() {
//        countDownTimer.cancel();
//        super.onDestroy();
//    }
//
//    /**
//     * 检查手机号是否合法
//     *
//     * @param cellPhone
//     * @return
//     */
//    private boolean checkCellPhone(String cellPhone, boolean isToast) {
//        if (TextUtils.isEmpty(cellPhone)) {
//            if (isToast) {
//                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_phone_null_tip), Toast.LENGTH_SHORT).show();
//                hideKeyboard();
//            }
//            return false;
//        }
//
//        Pattern pattern = Pattern.compile("^1[34578]\\d{9}$");
//        Matcher matcher = pattern.matcher(cellPhone);
//
//        if (!matcher.matches()) {
//            if (isToast) {
//                Toast.makeText(LoginActivity.this, getResources().getString(R.string.login_phone_wrong_tip), Toast.LENGTH_SHORT).show();
//            }
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 检查验证码
//     *
//     * @param verifyCode
//     * @return
//     */
//    private boolean checkVerify(String verifyCode) {
//        if (TextUtils.isEmpty(verifyCode)) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 登录操作
//     */
//    @OnClick(R.id.login_btn)
//    public void doLogin() {
//        if (!isLoading) {
//            setLogin();
//        }
//    }
//
//    private void setLogin() {
//        final String phoneStr = phone_num.getText().toString();
//        if (!checkCellPhone(phoneStr, true)) {
//            return;
//        }
//
//        String verifyStr = verify_code.getText().toString();
//        if (!checkVerify(verifyStr)) {
//            return;
//        }
//
//        Api.doLogin(phoneStr, verifyStr, new GsonResponseHandler<LoginBean>(LoginBean.class, LoginActivity.this) {
//            @Override
//            public void onStart() {
//                super.onStart();
//                isLoading = true;
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                super.onFailure(statusCode, headers, responseBody, error);
//                isLoading = false;
//            }
//
//            @Override
//            public void failed(String error) {
//                //super.failed(error);
//                isLoading = false;
//                Tip.show(error);
//            }
//
//            @Override
//            public void succeed(LoginBean loginBean) {
//                LocalStorage.setSID(loginBean.getData().getSid());
//
//                // 设置JPush别名
//                JPushInterface.setAlias(LoginActivity.this, phoneStr, new TagAliasCallback() {
//                    @Override
//                    public void gotResult(int i, String s, Set<String> set) {
//                        Log.e("AAA", "JPushInterface.setAlias(): " + i + " " + s);
//                    }
//                });
//
//                getUserInfo();
//            }
//        }.setHttpTag(getHttpTag()));
//    }
//
//    private void getUserInfo() {
//        startActivity(HomeActivity.class, null);
//        finish();
//    }
//
//    @OnClick(R.id.get_verify_code)
//    public void getVerifyCode() {
//        final String phoneStr = phone_num.getText().toString();
//        if (!checkCellPhone(phoneStr, true)) {
//            return;
//        }
//        countDownTimer.start();
//        Api.getVerifyCode(phoneStr, new GsonResponseHandler<BaseResponse>(BaseResponse.class) {
//            @Override
//            public void succeed(BaseResponse baseResponse) {
//                setmSendVerifyEnable(true);
//            }
//        }.setHttpTag(getHttpTag()));
//    }
//
//    @OnClick(R.id.user_agreement)
//    public void showLoginService() {
//        WebViewActivity.start(LoginActivity.this, "http://open.hohoxc.com/doc/bm_rule.html", getResources().getString(R.string.user_agreement_term));
//    }
//
//    private void setMyAppTitle() {
//        mMyAppTitle.initViewsVisible(true, false, true, false, true);
//        mMyAppTitle.setAppTitle(getResources().getString(R.string.login_title));
//    }
//}
