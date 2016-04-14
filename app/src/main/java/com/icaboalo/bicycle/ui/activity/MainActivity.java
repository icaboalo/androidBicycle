package com.icaboalo.bicycle.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.icaboalo.bicycle.R;
import com.icaboalo.bicycle.io.ApiClient;
import com.icaboalo.bicycle.io.ApiService;
import com.icaboalo.bicycle.io.model.BicycleApiModel;
import com.icaboalo.bicycle.ui.adapter.BicycleRecyclerAdapter;
import com.icaboalo.bicycle.ui.fragment.DialogAddBicycle;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.bicycle_list)
    RecyclerView mBicycleRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getBicycleList("");
    }

    void getBicycleList(String token){
        Call<ArrayList<BicycleApiModel>> call = ApiClient.getApiService().getBicycleList(token);
        call.enqueue(new Callback<ArrayList<BicycleApiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BicycleApiModel>> call, Response<ArrayList<BicycleApiModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<BicycleApiModel> bicycleList = response.body();
                    setupRecycler(bicycleList);
                    Log.e("SUCCESS", "success");
                } else {
                    Log.e("ERROR", "error");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BicycleApiModel>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    void setupRecycler(ArrayList<BicycleApiModel> bicycleList){
        LinearLayoutManager nLinearLayoutManager = new LinearLayoutManager(this);
        BicycleRecyclerAdapter nBicycleRecyclerAdapter = new BicycleRecyclerAdapter(this, bicycleList);
        mBicycleRecycler.setLayoutManager(nLinearLayoutManager);
        mBicycleRecycler.setAdapter(nBicycleRecyclerAdapter);


    }

    void showDialog(){
        FragmentManager nFragmentManager = getSupportFragmentManager();
        DialogAddBicycle nDialogAddBicycle = new DialogAddBicycle();
        nDialogAddBicycle.show(nFragmentManager, "ADD_BICYCLE");
    }
}
