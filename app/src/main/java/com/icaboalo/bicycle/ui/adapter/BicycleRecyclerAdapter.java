package com.icaboalo.bicycle.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.icaboalo.bicycle.R;
import com.icaboalo.bicycle.io.model.BicycleApiModel;
import com.icaboalo.bicycle.ui.activity.MainActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by icaboalo on 13/04/16.
 */
public class BicycleRecyclerAdapter extends RecyclerView.Adapter<BicycleRecyclerAdapter.BicycleViewHolder> {

    Context mContext;
    ArrayList<BicycleApiModel> mBicycleList;
    LayoutInflater mInflater;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    public BicycleRecyclerAdapter(Context context, ArrayList<BicycleApiModel> bicycleList) {
        mContext = context;
        mBicycleList = bicycleList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public BicycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bicycle_list, parent, false);
        BicycleViewHolder nViewHolder = new BicycleViewHolder(view, R.id.brand, R.id.model, R.id.track, R.id.color, R.id.year, R.id.maps_button, R.id.photo_button);
        return nViewHolder;
    }

    @Override
    public void onBindViewHolder(final BicycleViewHolder holder, int position) {
        final BicycleApiModel bicycle = mBicycleList.get(position);
        holder.setBrand(bicycle.getBicycleBrand());
        holder.setModel(bicycle.getBicycleModel());
        holder.setColor(bicycle.getBicycleColor());
        holder.setTrack(bicycle.getBicycleTrack());
        holder.setYear(bicycle.getBicycleYear());

        holder.setMapButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = bicycle.getBicycleLatitude();
                String longitude = bicycle.getBicycleLongitude();
                String label = "(" + bicycle.getBicycleBrand() + " " + bicycle.getBicycleModel() + ")";
                Uri location = Uri.parse("geo:0,0&?q=" + latitude + "," + longitude + label);
                showMap(location);
            }
        });

        holder.setPhotoButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String path = "/sdcard/";
                final String fileName = bicycle.getBicycleBrand() + "-" + bicycle.getBicycleModel() + ".png";

                final File file = new File(path, fileName);

                if (fileExistence(file)){
                    Toast.makeText(mContext, "Image found in " + file, Toast.LENGTH_SHORT).show();
                    Uri fileUri = Uri.fromFile(file);
                    Intent galleryIntent = new Intent(Intent.ACTION_VIEW);
                    galleryIntent.setDataAndType(fileUri, "image/*");
                    mContext.startActivity(galleryIntent);


                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("NO IMAGE");
                    alertDialog.setMessage("No image for " + bicycle.getBicycleModel() + " was found. \n Would you like to take one?");
                    alertDialog.setPositiveButton("TAKE IMAGE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // create Intent to take a picture and return control to the calling application
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            Uri fileUri = Uri.fromFile(file); // create a file to save the image
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                            // start the image capture Intent
                            ((MainActivity) mContext).startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                        }
                    });
                    alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }

            }
        });

    }

    public boolean fileExistence(File imageFile){
        return imageFile.exists();
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {
        return mBicycleList.size();
    }

    class BicycleViewHolder extends RecyclerView.ViewHolder{

        TextView mBrand, mModel, mTrack, mColor, mYear;
        Button mMapsButton, mPhotoButton;

        public BicycleViewHolder(View itemView, int brandId, int modelId, int trackId, int colorId, int yearId, int mapsButtonId, int photoButtonId) {
            super(itemView);
            mBrand = (TextView) itemView.findViewById(brandId);
            mModel= (TextView) itemView.findViewById(modelId);
            mTrack = (TextView) itemView.findViewById(trackId);
            mColor = (TextView) itemView.findViewById(colorId);
            mYear = (TextView) itemView.findViewById(yearId);
            mMapsButton = (Button) itemView.findViewById(mapsButtonId);
            mPhotoButton = (Button) itemView.findViewById(photoButtonId);
        }

        public void setBrand(String brand) {
            mBrand.setText(brand);
        }

        public void setModel(String model) {
            mModel.setText(model);
        }

        public void setTrack(String track) {
            mTrack.setText(track);
        }

        public void setColor(String color) {
            mColor.setText(color);
        }

        public void setYear(String year) {
            mYear.setText(year);
        }

        public void setMapButton(View.OnClickListener onClickListener){
            mMapsButton.setOnClickListener(onClickListener);
        }

        public void setPhotoButton(View.OnClickListener onClickListener){
            mPhotoButton.setOnClickListener(onClickListener);
        }
    }
}
