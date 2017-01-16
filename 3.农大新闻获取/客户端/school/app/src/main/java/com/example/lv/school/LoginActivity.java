package com.example.lv.school;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lv.school.Util.*;



public class LoginActivity extends AppCompatActivity  {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String response;
    String id;
    String password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(
                        InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    public void onDestory(){
        super.onDestroy();
    }
    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            realLogin();
        }
    }
    private void realLogin(){
        //先弹出一个进度条
        progressDialog=new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("验证界面");
        progressDialog.setMessage("正在验证……");
        progressDialog.setCancelable(true);
        progressDialog.show();

        //开启post请求
        id = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        final String date="id="+id+"&password="+password;
        new Thread(){
            public void run(){
               response=PostGetUtil.sendPost("http://121.42.211.211/school/login/CheckUser.php",date);
            //    response=PostGetUtil.sendGet("http://www.baidu.com","");
                handler.sendEmptyMessage(0x123);
            }
        }.start();
    }
    Handler handler=new Handler(){

        public void handleMessage(Message msg){
            if(msg.what == 0x123){
                Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                progressDialog.hide();
                if("ok".equals(response.trim())){
                    //跳转活动
                    Intent intent=new Intent(LoginActivity.this,UserMain.class);
                   // Intent intent=new Intent(LoginActivity.this,Test.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    finish();

                }
               else{

                }

            }
        }
    };

}

