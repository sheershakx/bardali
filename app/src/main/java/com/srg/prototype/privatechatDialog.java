package com.srg.prototype;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class privatechatDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        final EditText message=new EditText(getContext());
        message.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(message);


        builder.setTitle("Type your message here")
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      String messagecontent=message.getText().toString();
                      if (!TextUtils.isEmpty(messagecontent)) {
                          Toast.makeText(getContext(), messagecontent, Toast.LENGTH_LONG).show();
                          startActivity(new Intent(getContext(),inbox.class));


                      }

                      else Toast.makeText(getContext(), "Cant send blank message!!", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

        return builder.create();
    }

    }


