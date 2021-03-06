package com.mrym.newsbulletion.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mrym.newsbulletion.R;
import com.mrym.newsbulletion.authenticator.account.AccountTool;
import com.mrym.newsbulletion.domain.modle.UserBean;
import com.mrym.newsbulletion.mvp.activity.login.LoginPresenter;
import com.mrym.newsbulletion.mvp.activity.login.LoginView;
import com.mrym.newsbulletion.ui.BaseActivity;
import com.mrym.newsbulletion.widget.DialogView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Jian on 2016/8/30.
 * Email: 798774875@qq.com
 * Github: https://github.com/moruoyiming
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
    public static final String TAG = LoginActivity.class.getCanonicalName();
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.bt_verification)
    Button btVerification;
    @BindView(R.id.et_verification)
    EditText etVerification;
    private Dialog loadingDialog;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected String getTag() {
        return TAG;
    }


    @OnClick({R.id.et_tel, R.id.bt_verification, R.id.et_verification, R.id.tv_agreement, R.id.bt_sure, R.id.tv_wx_login, R.id.tv_qq_login, R.id.tv_weibo_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_verification:
//                Log.d(TAG, "click----->bt_get_verification");
                mvpPresenter.sendVerification(etTel.getText().toString());
                break;
            case R.id.tv_agreement:
//                Log.d(TAG, "click----->tv_agreement");
                mvpPresenter.readAgreement(LoginActivity.this);
                break;
            case R.id.bt_sure:
//                Log.d(TAG, "click----->bt_login" + btLogin.isClickable());
//                mvpPresenter.login(etVerification.getText().toString(), etTel.getText().toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_wx_login:

                break;
            case R.id.tv_qq_login:
                mvpPresenter.thirdLogin(Wechat.NAME);
                break;
            case R.id.tv_weibo_login:
//                Log.d(TAG, "click----->tv_weibo_login");
//                mLoginPresenter.thirdLogin(SinaWeibo.NAME);
                break;
        }
    }

    @Override
    public void showLoading() {
        loadingDialog = DialogView.createLoadingDialog(LoginActivity.this, "正在发送验证码，请稍候...", false);
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void loginFailure(int code, String msg) {
        Toast.makeText(LoginActivity.this, "登陆失败!", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void loginSuccess(UserBean userBean) {
        AccountTool accountTool = AccountTool.getInstance();
        accountTool.saveAccount(etTel.getText().toString(), userBean.getPassWord(), userBean.toString());
        startMain();
    }

    @Override
    public void startMain() {
//        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//        startActivity(intent);
        Toast.makeText(LoginActivity.this, "MainActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btVerification.setEnabled(false);
        btVerification.setText(millisUntilFinished / 1000 + getText(R.string.second).toString());
    }

    @Override
    public void onFinish() {
        btVerification.setText(R.string.obtain_again);
        btVerification.setEnabled(true);
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void destroyActivityBefore() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
