package com.srg.prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class choosechatDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Which chatroom do you want to enter")
                .setPositiveButton("Private Chat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (login.sessionid.equals(ItemDescription.suid)) {
                            Toast.makeText(getContext(), "Cannot send message to yourself", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(getContext(), chatdesc.class));

                        }

                    }
                })
                .setNegativeButton("Public Chat", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getContext(), openchat.class));
                        getActivity().finish();

                    }
                });

        return builder.create();
    }
}

