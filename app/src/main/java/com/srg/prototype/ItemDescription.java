package com.srg.prototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class ItemDescription extends AppCompatActivity {
    String sdesc, squantity, sunit, stotal, sdelivery, simageurl;
    public static String suid;
    public static String sid;
    public static String sitemname;
    public static String suname;
    TextView name, quantity, deliverysts, total, desc, sellersname;
    ImageView imageView;
    Button OpenChat, Sold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        //typecating and declaration
        name = findViewById(R.id.textviewitem);
        quantity = findViewById(R.id.textviewquantity);
        total = findViewById(R.id.textviewtotal);
        desc = findViewById(R.id.textviewdescresult);
        sellersname = findViewById(R.id.textviewsellersname);
        imageView = findViewById(R.id.imageview);
        deliverysts = findViewById(R.id.deliverystatus);
        OpenChat = findViewById(R.id.openchat);
        Sold = findViewById(R.id.sold);
        Sold.setVisibility(View.GONE);


        Sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new sold().execute();
            }
        });
        OpenChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosechatDialog choosechatDialog = new choosechatDialog();
                choosechatDialog.show(getSupportFragmentManager(), "choose chat");
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast imagetoast = new Toast(ItemDescription.this);
                ImageView toastimageview = new ImageView(ItemDescription.this);
                Picasso.get().load(simageurl).into(toastimageview);
                imagetoast.setView(toastimageview);
                imagetoast.setGravity(Gravity.CENTER, 0, 0);
                imagetoast.show();
            }
        });

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("itemid")) {
            String ItemId = getIntent().getStringExtra("itemid");
            getInfo getInfo = new getInfo();
            getInfo.execute(ItemId);
        }

    }

    public void gobacktomain(View view) {
        finish();
    }

    public class getInfo extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url = "http://acosaf.000webhostapp.com/getInfo.php";

        }

        @Override
        protected String doInBackground(String... args) {
            String itemid;
            itemid = args[0];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("itemid", "UTF-8") + "=" + URLEncoder.encode(itemid, "UTF-8");

                bufferedWriter.write(data_string);
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

                JSONObject jsonObject = new JSONObject(data);
                sid = jsonObject.getString("sid");
                suid = jsonObject.getString("suid");
                suname = jsonObject.getString("suname");
                sitemname = jsonObject.getString("sitemname");
                sdesc = jsonObject.getString("sdesc");
                squantity = jsonObject.getString("squantity");
                sunit = jsonObject.getString("sunit");
                stotal = jsonObject.getString("stotal");
                sdelivery = jsonObject.getString("sdelivery");
                simageurl = jsonObject.getString("simageurl");

//
                return data;


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
            if (s != null && s.contains("yes")) {
                name.setText(sitemname);
                quantity.setText(squantity);
                total.setText(stotal);
                desc.setText(sdesc);
                sellersname.setText(suname);
                if (sdelivery.equals("true")) {
                    deliverysts.setText("Yes");
                } else if (sdelivery.equals("false")) deliverysts.setText("No");
                if (simageurl != null && !TextUtils.isEmpty(simageurl))
                    Picasso.get().load(simageurl).into(imageView);
            } else
                Toast.makeText(ItemDescription.this, "Get Method failed", Toast.LENGTH_SHORT).show();
            if (login.sessionid.equals(suid)) {
                Sold.setVisibility(View.VISIBLE);
            }


        }
    }
    public class sold extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url = "http://acosaf.000webhostapp.com/sold.php";

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
                String data_string = URLEncoder.encode("itemid", "UTF-8") + "=" + URLEncoder.encode(sid, "UTF-8");

                bufferedWriter.write(data_string);
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


//
                return null;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
                Toast.makeText(ItemDescription.this, "Item status updated to SOLD", Toast.LENGTH_SHORT).show();


        }
    }
}
