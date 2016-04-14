package com.icaboalo.bicycle.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by icaboalo on 14/04/16.
 */
public class DialogAddBicycle extends DialogFragment {

    public DialogAddBicycle() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Bla");
        return alertDialog.create();
    }
}
