package com.srg.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class terms extends AppCompatActivity {
    ImageView continueterms;
    CheckBox accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        continueterms = findViewById(R.id.continueTerms);
        accept = findViewById(R.id.termscheckbox);
        continueterms.setVisibility(View.GONE);

        accept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                continueterms.setVisibility(View.VISIBLE);
                if (isChecked == false) {
                    continueterms.setVisibility(View.GONE);

                }
            }

        });

        continueterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
