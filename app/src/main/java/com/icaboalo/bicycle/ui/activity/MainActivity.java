package com.icaboalo.bicycle.ui.activity;

import com.icaboalo.bicycle.ui.fragment.DialogAddBicycle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.icaboalo.bicycle.R;
import com.icaboalo.bicycle.io.ApiClient;
import com.icaboalo.bicycle.io.model.BicycleApiModel;
import com.icaboalo.bicycle.ui.adapter.BicycleRecyclerAdapter;
import com.icaboalo.bicycle.ui.fragment.DialogAddBicycle;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {

    @Bind(R.id.bicycle_list)
    RecyclerView mBicycleRecycler;

    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getBicycleList("");

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
                AddBicycle();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void AddBicycle(){
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, this);
        showDialog();
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

    @Override
    public void onLocationChanged(Location location) {
        String sLocation = "Location: " + location.getLatitude() + "/" + location.getLongitude();
        Toast.makeText(MainActivity.this, sLocation, Toast.LENGTH_SHORT).show();
        Log.d("LOCATION", sLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
    void showDialog(){
        FragmentManager nFragmentManager = getSupportFragmentManager();
        DialogAddBicycle nDialogAddBicycle = new DialogAddBicycle();
        nDialogAddBicycle.show(nFragmentManager, "ADD_BICYCLE");
    }
}
