package com.srg.prototype;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

public class seller_tab extends AppCompatActivity {
    public static String Name;
    EditText name, desc, quantity, rate, total;
    CheckBox delivery;
    Spinner unit;
    Button upload, choosephoto;
    String DeliveryString;
    ImageView imageView;
    private StorageReference storageReference;
    private Uri photouri;
    String downloadurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_tab);
        // Snackbar.make(findViewById(R.id.rootlayout), "Name:" + login.sessionname + "\n" + "Id:" + login.sessionid, Snackbar.LENGTH_LONG).show();
        //type casting values
        name = findViewById(R.id.itemName1);
        desc = findViewById(R.id.itemDesc1);
        quantity = findViewById(R.id.itemQuantity1);
        unit = findViewById(R.id.unit);  //spinner
        rate = findViewById(R.id.itemRate1);
        imageView = findViewById(R.id.choosedimageview);      //imageview
        total = findViewById(R.id.itemTotal1);
        delivery = findViewById(R.id.homedelivery1);    //checkbox
        upload = findViewById(R.id.upload1);           //button
        choosephoto = findViewById(R.id.choosephotobtn);           //button
        storageReference = FirebaseStorage.getInstance().getReference();     //firebase storage ref

        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.unit));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(myadapter);

        rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    int q = Integer.parseInt(quantity.getText().toString());
                    int r = Integer.parseInt(rate.getText().toString());
                    String t = String.valueOf(q * r);
                    total.setText(t);
                } catch (RuntimeException e) {
                   // Toast.makeText(seller_tab.this, "Exception just occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        choosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), 0);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = name.getText().toString();
                String Desc = desc.getText().toString();
                String Quantity = quantity.getText().toString();
                String Unit = unit.getSelectedItem().toString();      //spinner
                String Rate = rate.getText().toString();
                String Total = total.getText().toString();
                Boolean Delivery = delivery.isChecked();//checkbox
                if (Delivery.toString() == "true") {
                    DeliveryString = "true";
                } else DeliveryString = "false";


                String uid = login.sessionid;
                String uname = login.sessionname;


                if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Desc) && !TextUtils.isEmpty(Quantity) && !TextUtils.isEmpty(Unit) && !TextUtils.isEmpty(Rate) && !TextUtils.isEmpty(Total)) {
                    postItem postItem = new postItem();
                    postItem.execute(Name, Desc, Quantity, Unit, Rate, Total, DeliveryString, uid, uname);
                } else
                    Toast.makeText(seller_tab.this, "Some fields cannot be blank", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            photouri = data.getData();
            imageView.setImageURI(photouri);
            Snackbar.make(findViewById(R.id.rootlayout), "Photo Upload Success भन्ने मेसेज आएपछि मात्र 'सेभ गर्नुहोस' बटन थिच्नु होला", Snackbar.LENGTH_LONG).show();
            uploadtofirebase();


        }
    }

    private void uploadtofirebase() {
        StorageReference riversRef = storageReference.child("images/" + UUID.randomUUID());

        riversRef.putFile(photouri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                downloadurl = uri.toString();
                                Toast toast = Toast.makeText(seller_tab.this, "Photo Upload Success", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(seller_tab.this, "Photo upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public void goback(View view) {
        finish();
    }

    public class postItem extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
            Toast.makeText(seller_tab.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url = "http://acosaf.000webhostapp.com/sellers.php";


        }

        @Override
        protected String doInBackground(String... args) {
            String name, desc, quantity, unit, rate, total, delivery, uid, uname;
            name = args[0];
            desc = args[1];
            quantity = args[2];
            unit = args[3];
            rate = args[4];
            total = args[5];
            delivery = args[6];
            uid = args[7];
            uname = args[8];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("desc", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8") + "&" +
                        URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&" +
                        URLEncoder.encode("unit", "UTF-8") + "=" + URLEncoder.encode(unit, "UTF-8") + "&" +
                        URLEncoder.encode("rate", "UTF-8") + "=" + URLEncoder.encode(rate, "UTF-8") + "&" +
                        URLEncoder.encode("total", "UTF-8") + "=" + URLEncoder.encode(total, "UTF-8") + "&" +
                        URLEncoder.encode("delivery", "UTF-8") + "=" + URLEncoder.encode(delivery, "UTF-8") + "&" +
                        URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8") + "&" +
                        URLEncoder.encode("imageurl", "UTF-8") + "=" + URLEncoder.encode(downloadurl, "UTF-8") + "&" +
                        URLEncoder.encode("uname", "UTF-8") + "=" + URLEncoder.encode(uname, "UTF-8");


                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Uploaded Succesfully";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(seller_tab.this, s, Toast.LENGTH_LONG).show();
                new activitylog().execute("You posted sale for an item :" +Name);


            startActivity(new Intent(getApplicationContext(), buttomnav.class));


        }
    }

    public class activitylog extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
            Toast.makeText(seller_tab.this, "Loading...", Toast.LENGTH_SHORT).show();
            db_url = "http://acosaf.000webhostapp.com/sendlog.php";


        }

        @Override
        protected String doInBackground(String... args) {
            String logmessage = args[0];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(login.sessionid, "UTF-8") + "&" +
                        URLEncoder.encode("logstring", "UTF-8") + "=" + URLEncoder.encode(logmessage, "UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Log saved.";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(seller_tab.this, s, Toast.LENGTH_LONG).show();


        }
    }
}
