package com.icaboalo.bicycle.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.icaboalo.bicycle.R;
import com.icaboalo.bicycle.io.ApiClient;
import com.icaboalo.bicycle.io.model.BicycleApiModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by icaboalo on 14/04/16.
 */
public class DialogAddBicycle extends DialogFragment implements LocationListener {

    LocationManager mLocationManager;
    String mLatitude, mLongitude;

    @Bind(R.id.track_spinner)
    Spinner mTrackSpinner;

    @Bind(R.id.year_spinner)
    Spinner mYearSpinner;

    @Bind(R.id.location_input)
    TextInputEditText mLocation;

    @Bind(R.id.brand_input)
    TextInputEditText mBrand;

    @Bind(R.id.model_input)
    TextInputEditText mModel;

    @Bind(R.id.color_input)
    TextInputEditText mColor;

    public interface DialogListener{
        void onDialogPositiveClick(DialogFragment dialog, TextInputEditText brand, TextInputEditText model, TextInputEditText color, TextInputEditText location, Spinner track, Spinner year, String latitude, String longitude);

    }

    DialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (DialogListener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater nInflater = getActivity().getLayoutInflater();
        View view = nInflater.inflate(R.layout.dialog_add_bicycle, null);
        ButterKnife.bind(this, view);
        alertDialog.setView(view).setCancelable(false);
        setupTrackSpinner();
        setupYearSpinner();
        location();
        alertDialog.setTitle(getString(R.string.alert_title));
        alertDialog.setPositiveButton("BLA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick(DialogAddBicycle.this, mBrand, mModel, mColor, mLocation, mTrackSpinner, mYearSpinner, mLatitude, mLongitude);
            }
        });
        alertDialog.setNegativeButton(getString(R.string.alert_negative_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return alertDialog.create();
    }


    //    Creating Location manager
    private void location() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, this);

        }
    }

    void setupTrackSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.track_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTrackSpinner.setAdapter(adapter);
    }

    void setupYearSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYearSpinner.setAdapter(adapter);
    }

    @Override
    public void onLocationChanged(Location location) {
//        Here I get the current location **updates while moving** setting the textView
        mLocation.setText("Location: " + location.getLatitude() + "/" + location.getLongitude());
        mLatitude = String.valueOf(location.getLatitude());
        mLongitude = String.valueOf(location.getLongitude());
        Log.d("LOCATION", mLocation.getText().toString());
        Log.d("LATITUDE", String.valueOf(location.getLatitude()));
        Log.d("LONGITUDE", String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
//        If location is deactivated user is sent to settings
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}
