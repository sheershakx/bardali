package com.srg.prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class apk_updateDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("There is a new Update of app on Google Play Store.Please update\n so that you don't miss a feature")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.srg.prototype"));
                        startActivity(intent);
                        getActivity().finish();
                    }
                });


        return builder.create();
    }
}

