package com.gournet.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    AutoCompleteTextView mUsernameView;
    EditText mPasswordView;
    Button mEmailSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = findViewById(R.id.progressBar2);
        mUsernameView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);

        mEmailSignInButton.setOnClickListener(v -> {
            EnableDisable(false);
            Client.service.create(loginService.class).doLogin(new UserPass(mUsernameView.getText().toString(), mPasswordView.getText().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    Client.generateWToken(result.access.token);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", result.user);
                    startActivity(intent);
                    EnableDisable(true);
                });
            });
        mEmailSignInButton.performClick();
    }

    public void EnableDisable(boolean isEnable) {
        mProgressBar.setVisibility(isEnable ? View.INVISIBLE : View.VISIBLE);
        mUsernameView.setEnabled(isEnable);
        mPasswordView.setEnabled(isEnable);
        mEmailSignInButton.setEnabled(isEnable);
    }
}

