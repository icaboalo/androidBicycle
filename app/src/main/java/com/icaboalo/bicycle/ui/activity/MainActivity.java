package com.icaboalo.bicycle.ui.activity;

import com.icaboalo.bicycle.domain.PrefConstants;
import com.icaboalo.bicycle.ui.fragment.DialogAddBicycle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.icaboalo.bicycle.R;
import com.icaboalo.bicycle.io.ApiClient;
import com.icaboalo.bicycle.io.model.BicycleApiModel;
import com.icaboalo.bicycle.ui.adapter.BicycleRecyclerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DialogAddBicycle.DialogListener{

    @Bind(R.id.bicycle_list)
    RecyclerView mBicycleRecycler;

    @Override
    protected void onStart() {
        if (!getToken().contains("Token")){
            Intent goToLogin = new Intent(this, LoginActivity.class);
            startActivity(goToLogin);
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("TOKEN", getToken());
        getBicycleList(getToken());

    }

    private String getToken() {
        SharedPreferences nSharedPreferences = getSharedPreferences(PrefConstants.TOKEN_FILE, MODE_PRIVATE);
        String token = nSharedPreferences.getString(PrefConstants.TOKEN_PREF, "");
        return token;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.add:
                showDialog();
                return true;
            case R.id.action_logout:
                SharedPreferences nSharedPreferences = getSharedPreferences(PrefConstants.TOKEN_FILE, MODE_PRIVATE);
                nSharedPreferences.edit().putString(PrefConstants.TOKEN_PREF, " ").apply();
                Intent goToLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(goToLogin);
                finish();
                return true;
            case R.id.action_refresh:
                getBicycleList(getToken());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    Getting bicycle list with a retrofit request and passing it to the recycler
    void getBicycleList(String token){
        Call<ArrayList<BicycleApiModel>> call = ApiClient.getApiService().getBicycleList(token);
        call.enqueue(new Callback<ArrayList<BicycleApiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BicycleApiModel>> call, Response<ArrayList<BicycleApiModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<BicycleApiModel> bicycleList = response.body();
                    setupRecycler(bicycleList);
                    Log.e("RETROFIT", "success");
                } else {
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
            public void onFailure(Call<ArrayList<BicycleApiModel>> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
            }
        });
    }

    void saveNewBicycle(String token, BicycleApiModel bicycle) {

        Call<BicycleApiModel> call = ApiClient.getApiService().postBicycle(token, bicycle);
        call.enqueue(new Callback<BicycleApiModel>() {
            @Override
            public void onResponse(Call<BicycleApiModel> call, Response<BicycleApiModel> response) {
                if (response.isSuccessful()) {
                    Log.e("RETROFIT", "success");
                    getBicycleList(getToken());

                } else {
                    int statusCode = response.code();

                }
            }

            @Override
            public void onFailure(Call<BicycleApiModel> call, Throwable t) {
                Log.e("RETROFIT", t.getMessage());
            }
        });
    }

//    function for creating and populating the recyclerView
    void setupRecycler(ArrayList<BicycleApiModel> bicycleList){
        LinearLayoutManager nLinearLayoutManager = new LinearLayoutManager(this);
        BicycleRecyclerAdapter nBicycleRecyclerAdapter = new BicycleRecyclerAdapter(this, bicycleList);
        mBicycleRecycler.setLayoutManager(nLinearLayoutManager);
        mBicycleRecycler.setAdapter(nBicycleRecyclerAdapter);


    }

//    function so when button on clicked the alertDialog appears
    void showDialog(){
        FragmentManager nFragmentManager = getSupportFragmentManager();
        DialogAddBicycle nDialogAddBicycle = new DialogAddBicycle();
        nDialogAddBicycle.show(nFragmentManager, "ADD_BICYCLE");
    }


//    Controls the dialog save button
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, TextInputEditText brand, TextInputEditText model, TextInputEditText color, TextInputEditText location, Spinner track, Spinner year, String latitude, String longitude) {

//                    get texts for bicycle model
        String bicycleBrand = brand.getText().toString();
        String bicycleModel = model.getText().toString();
        String bicycleColor = color.getText().toString();

        String bicycleTrack = track.getSelectedItem().toString();
        String bicycleYear = year.getSelectedItem().toString();

//                    create new bicycle object
        BicycleApiModel newBicycle = new BicycleApiModel(bicycleBrand, bicycleModel, bicycleTrack, bicycleColor, bicycleYear, latitude, longitude);
//                    make the retrofit post request

            saveNewBicycle(getToken(), newBicycle);

    }

}
