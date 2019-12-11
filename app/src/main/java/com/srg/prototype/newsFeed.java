package com.srg.prototype;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class newsFeed extends AppCompatActivity implements LocationListener {
    private int STORAGE_PERMISSION_CODE = 1;
    private LocationManager locationManager;
   double longitude;
   double latitude;
   Button displaynews;


    String id, itemname, itemunit,Quantity, Rate, Total;
    ArrayList<String> ID = new ArrayList<String>();
    ArrayList<String> ITEMNAME = new ArrayList<String>();
    ArrayList<String> ITEMUNIT = new ArrayList<String>();
    ArrayList<String> QUANTITY = new ArrayList<String>();
    ArrayList<String> RATE = new ArrayList<String>();
    ArrayList<String> TOTAL = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        requestStoragePermission();

        getInfo getInfo = new getInfo();
        getInfo.execute();

        displaynews=findViewById(R.id.displaynews);
        displaynews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context=displaynews.getContext();
                RecyclerView itemlist = findViewById(R.id.recyclerview);
                itemlist.setLayoutManager(new LinearLayoutManager(context));
               // itemlist.setAdapter(new adapterNewsfeed(ID,ITEMNAME,ITEMUNIT,QUANTITY, RATE, TOTAL));

                Toast.makeText(context, "Lon="+longitude+"\n"+"Lat="+latitude, Toast.LENGTH_SHORT).show();
            }
        });

        //location
        //location
        //location
//        ||  !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER
        //location
        //location
        //location

//        if (ContextCompat.checkSelfPermission(newsFeed.this,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(newsFeed.this, "You have already granted this permission!",
//                    Toast.LENGTH_SHORT).show();
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                    return;
//                }
//                Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
//                onLocationChanged(location);
//            }
//        } else {
//            requestStoragePermission();
//        }
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED  ||  !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                return;
//            } else {
//                Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
//                onLocationChanged(location);
//            }
//        }

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                pullToRefresh.setRefreshing(false);
            }
        });


    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(newsFeed.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Location longitude and latitude fetch
    //Location longitude and latitude fetch
    //Location longitude and latitude fetch
    //Location longitude and latitude fetch
    //Location longitude and latitude fetch
    //Location longitude and latitude fetch
    //Location longitude and latitude fetch
    //Location longitude and latitude fetch



    @Override
    public void onLocationChanged(Location location) {
         longitude=location.getLongitude();
         latitude=location.getLatitude();

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

//    public void seenews(View view) {
//        Toast.makeText(this, "seenews", Toast.LENGTH_SHORT).show();
//        Context context=view.getContext();
//        RecyclerView itemlist = findViewById(R.id.recyclerview);
//                itemlist.setLayoutManager(new LinearLayoutManager(context));
//                itemlist.setAdapter(new adapterNewsfeed(ID, ITEMNAME, QUANTITY, RATE, TOTAL));
//
//                Toast.makeText(context, "Lon="+longitude+"\n"+"Lat="+latitude, Toast.LENGTH_SHORT).show();
//    }


    public class getInfo extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url = "http://acosaf.000webhostapp.com/test.php";

        }

        @Override
        protected String doInBackground(String... args) {

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuffer buffer = new StringBuffer();
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String data = stringBuilder.toString().trim();

                String json;

                InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
                int size = stream.available();
                byte[] buffer1 = new byte[size];
                stream.read(buffer1);
                stream.close();

                json = new String(buffer1, "UTF-8");
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("id") != null) {
                        id = jsonObject.getString("id");
                        itemname = jsonObject.getString("ItemName");
                        itemunit = jsonObject.getString("ItemUnit");
                        Quantity = jsonObject.getString("ItemQuantity");
                        Rate = jsonObject.getString("ItemRate");
                        Total = jsonObject.getString("ItemTotal");
                        ID.add(id);
                        ITEMUNIT.add(itemunit);
                        ITEMNAME.add(itemname);
                        QUANTITY.add(Quantity);
                        RATE.add(Rate);
                        TOTAL.add(Total);

                    }
                }


                return null;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
          //  Toast.makeText(newsFeed.this,ID.get(2), Toast.LENGTH_SHORT).show();
        }
    }


    //API calling





}
