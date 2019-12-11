package com.srg.prototype;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class buttomnav extends AppCompatActivity {
    String id, itemname,itemunit, Quantity, Rate, Total,imageurl;
    ImageView imageView;
    Bitmap bitmap;
    EditText name, desc, quantity, rate, total;
    CheckBox delivery;
    Spinner unit;
    FloatingActionButton floatingActionButton;

    ProgressDialog progressDialog;


    //sellers tab end

    ArrayList<String> ID = new ArrayList<String>();
    ArrayList<String> ITEMUNIT = new ArrayList<String>();
    ArrayList<String> ITEMNAME = new ArrayList<String>();
    ArrayList<String> QUANTITY = new ArrayList<String>();
    ArrayList<String> RATE = new ArrayList<String>();
    ArrayList<String> TOTAL = new ArrayList<String>();
    ArrayList<String> IMAGEURL = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttomnav);
        getInfo getInfo=new getInfo();
        getInfo.execute();

        //


        //
        BottomNavigationView bottomnav = findViewById(R.id.buttomnav);
        bottomnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                new newsFragment()).commit();

        //calling getInfo class to fetch all sellers data and add into the arrayList to show it in recyclerView in newsfeed Fragment

        //sellers tab
        //type casting values
        name = findViewById(R.id.itemName);
        desc = findViewById(R.id.itemDesc);
        quantity = findViewById(R.id.itemQuantity);
        unit = findViewById(R.id.unit1);  //spinner
        rate = findViewById(R.id.itemRate);
        total = findViewById(R.id.itemTotal);
        delivery = findViewById(R.id.homedelivery);    //checkbox
        imageView=findViewById(R.id.sellimageview);
        //button
        floatingActionButton=findViewById(R.id.fab);
        
        
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getApplicationContext(),inbox.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
       logoutDialog logoutDialog=new logoutDialog();
       logoutDialog.show(getSupportFragmentManager(),"logoutdialog");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                  Fragment selectedFragment =null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_feed:
                            selectedFragment = new newsFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, selectedFragment).commit();
                            ID.clear();
                            ITEMUNIT.clear();
                            ITEMNAME.clear();
                            QUANTITY.clear();
                            RATE.clear();
                            TOTAL.clear();
                            IMAGEURL.clear();
                            new getInfo().execute();
                            break;

                        case R.id.nav_sell:
                          startActivity(new Intent(getApplicationContext(),seller_tab.class));
                            break;

                        case R.id.nav_test:
                            startActivity(new Intent(getApplicationContext(),logactivity.class ));
                            break;
                    }

                    return true;

                }

            };



    public void chooseimage(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 0);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            try {

                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),path);


                if (bitmap!=null){
                    Toast.makeText(this, "not null", Toast.LENGTH_SHORT).show();



                }
                else Toast.makeText(this, "yes null", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


public void postitem(View view){

    String Name=name.getText().toString();
    String Desc=desc.getText().toString();
    String Quantity=quantity.getText().toString();
    String Unit=unit.getSelectedItem().toString();      //spinner
    String Rate=rate.getText().toString();
    String Total=total.getText().toString();
    Boolean Delivery=delivery.isChecked();//checkbox
    String DeliveryString=Delivery.toString();

    String uid=login.sessionid;
    String uname=login.sessionname;


    if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Desc) && !TextUtils.isEmpty(Quantity) && !TextUtils.isEmpty(Unit) && !TextUtils.isEmpty(Rate) && !TextUtils.isEmpty(Total)){
        postItem postItem=new postItem();
        postItem.execute(Name,Desc,Quantity,Unit,Rate,Total,DeliveryString,uid,uname);
    }
    else Toast.makeText(this, "Some fields cannot be blank", Toast.LENGTH_LONG).show();

}


    public class getInfo extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
      progressDialog=ProgressDialog.show(buttomnav.this,"","Loading items..",true);
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
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
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
                        itemunit=jsonObject.getString("ItemUnit");
                        Quantity = jsonObject.getString("ItemQuantity");
                        Rate = jsonObject.getString("ItemRate");
                        Total = jsonObject.getString("ItemTotal");
                        imageurl = jsonObject.getString("imageurl");
                        ID.add(id);
                        ITEMUNIT.add(itemunit);
                        ITEMNAME.add(itemname);
                        QUANTITY.add(Quantity);
                        RATE.add(Rate);
                        TOTAL.add(Total);
                        IMAGEURL.add(imageurl);

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
            RecyclerView itemlist = findViewById(R.id.recyclerview);
            itemlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            itemlist.setAdapter(new adapterNewsfeed(ID, ITEMNAME,ITEMUNIT, QUANTITY, RATE, TOTAL,IMAGEURL));
            progressDialog.dismiss();

        }
    }



    public class postItem extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url="http://acosaf.000webhostapp.com/sellers.php";



        }

        @Override
        protected String doInBackground(String... args) {
            String name,desc,quantity,unit,rate,total,delivery,uid,uname;
            name=args[0];
            desc=args[1];
            quantity=args[2];
            unit=args[3];
            rate=args[4];
            total=args[5];
            delivery=args[6];
            uid=args[7];
            uname=args[8];


            try {
                URL url=new URL(db_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("desc","UTF-8")+"="+URLEncoder.encode(desc,"UTF-8")+"&"+
                        URLEncoder.encode("quantity","UTF-8")+"="+URLEncoder.encode(quantity,"UTF-8")+"&"+
                        URLEncoder.encode("unit","UTF-8")+"="+URLEncoder.encode(unit,"UTF-8")+"&"+
                        URLEncoder.encode("rate","UTF-8")+"="+URLEncoder.encode(rate,"UTF-8")+"&"+
                        URLEncoder.encode("total","UTF-8")+"="+URLEncoder.encode(total,"UTF-8")+"&"+
                        URLEncoder.encode("delivery","UTF-8")+"="+URLEncoder.encode(delivery,"UTF-8")+"&"+
                        URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(uid,"UTF-8")+"&"+
                        URLEncoder.encode("uname","UTF-8")+"="+URLEncoder.encode(uname,"UTF-8");


                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Uploaded Succesfully";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(buttomnav.this, s, Toast.LENGTH_LONG).show();
            activitylog activitylog=new activitylog();
            activitylog.execute("You posted an item for sale");
            startActivity(new Intent(getApplicationContext(),buttomnav.class));



        }
    }


    public class activitylog extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url="http://acosaf.000webhostapp.com/sendlog.php";




        }

        @Override
        protected String doInBackground(String... args) {
            String logmessage=args[0];


            try {
                URL url=new URL(db_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(login.sessionid,"UTF-8")+"&"+
                        URLEncoder.encode("logstring","UTF-8")+"="+URLEncoder.encode(logmessage,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Log saved.";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }


        @Override
        protected void onPostExecute(String s) {



        }
    }
}


