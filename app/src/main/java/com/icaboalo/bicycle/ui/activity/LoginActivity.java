package com.icaboalo.bicycle.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.icaboalo.bicycle.R;
import com.icaboalo.bicycle.domain.PrefConstants;
import com.icaboalo.bicycle.io.ApiClient;
import com.icaboalo.bicycle.io.model.TokenApiModel;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.username_input)
    TextInputEditText mUsernameInput;

    @Bind(R.id.password_input)
    TextInputEditText mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button)
    void login(){
        attemptLogin();
    }

    void attemptLogin() {

        String username = mUsernameInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        Log.d("USERNAME", username);
        Log.d("PASSWORD", password);
        if (TextUtils.isEmpty(username)) {
            mUsernameInput.setError(getString(R.string.error_field_required));
        }else if (TextUtils.isEmpty(password)) {
            mPasswordInput.setError(getString(R.string.error_field_required));
        }else {

            TokenApiModel user = new TokenApiModel(username, password);

            Call<TokenApiModel> call = ApiClient.getApiService().getToken(user);
            call.enqueue(new Callback<TokenApiModel>() {
                @Override
                public void onResponse(Call<TokenApiModel> call, Response<TokenApiModel> response) {
                    if (response.isSuccessful()) {
                        Log.d("RETROFIT", "success");
                        TokenApiModel nTokenApiModel = response.body();
                        String token = nTokenApiModel.getToken();
                        Intent goToMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMain);
                        saveToken(token);
                        finish();
                    }else {
                        String error = null;
                        try {
                            error = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.e("RETROFIT", error);
                    }
                }

                @Override
                public void onFailure(Call<TokenApiModel> call, Throwable t) {

                }
            });
        }
    }

    void saveToken(String token){
        SharedPreferences nSharedPreferences = getSharedPreferences(PrefConstants.TOKEN_FILE, MODE_PRIVATE);
        nSharedPreferences.edit().putString(PrefConstants.TOKEN_PREF, "Token " + token).apply();
    }
}
