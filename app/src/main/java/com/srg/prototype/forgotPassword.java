package com.srg.prototype;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class forgotPassword extends AppCompatActivity {
    EditText mobilenum,pass,confirmpass;
    Button checknum,updatepass;
    String servermobile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        //typecasting

        mobilenum=findViewById(R.id.mobilenum);
        checknum=findViewById(R.id.checknum);
         pass=findViewById(R.id.pass);
        confirmpass=findViewById(R.id.confirmpass);
        updatepass=findViewById(R.id.updatepass);


      //diabling and hiding buttons
        pass.setVisibility(View.GONE);
        confirmpass.setVisibility(View.GONE);
        updatepass.setVisibility(View.GONE);




        checknum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobilenumber=mobilenum.getText().toString();
                if (!TextUtils.isEmpty(mobilenumber)){
                    checknum checknum=new checknum();
                    checknum.execute(mobilenumber);
                }
                else Toast.makeText(forgotPassword.this, "Mobile number cannot be blank", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void updatepass(View view) {
        String password=pass.getText().toString();
        String confirmpassword=confirmpass.getText().toString();
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmpassword)){
            if (password.equals(confirmpassword)){
                updatepass updatepass=new updatepass();
                updatepass.execute(servermobile,password);
                
            }
            else Toast.makeText(this, "Password doesnt match", Toast.LENGTH_SHORT).show();
            
        }
        else Toast.makeText(this, "password cannot be blank", Toast.LENGTH_SHORT).show();
    }

    public class checknum extends AsyncTask<String,String,String> {
        String db_url;

        @Override
        protected void onPreExecute() {
            Toast.makeText(forgotPassword.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url="http://acosaf.000webhostapp.com/checknum.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String mobile;
            mobile=params[0];


            try {
                URL url=new URL(db_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuffer buffer=new StringBuffer();
                StringBuilder stringBuilder=new StringBuilder();
                String line="";

                while((line = bufferedReader.readLine())!= null) {
                    stringBuilder.append(line);

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String data=stringBuilder.toString().trim();

                JSONObject jsonObject=new JSONObject(data);
               servermobile =jsonObject.getString("mobilenumber");

//                JSONArray jsonArray=new JSONArray(data);
//                for (int i=0;i<=jsonArray.length();i++) {
//                    JSONObject responseJSON = jsonArray.getJSONObject(i);
//                    String name = responseJSON.getString("sessionname");
//                    String id = responseJSON.getString("sessionid");
//
//
//                }
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
            if (servermobile!=null &&  servermobile.equals(mobilenum.getText().toString())) {
                Toast.makeText(forgotPassword.this,"Mobile nunber found..", Toast.LENGTH_SHORT).show();
                mobilenum.setVisibility(View.GONE);
                checknum.setVisibility(View.GONE);

                pass.setVisibility(View.VISIBLE);
                confirmpass.setVisibility(View.VISIBLE);
                updatepass.setVisibility(View.VISIBLE);



            }
            else
                Toast.makeText(forgotPassword.this,"Sorry,Mobile number not found..", Toast.LENGTH_SHORT).show();


        }
    }
    public class updatepass extends AsyncTask<String,String,String> {
        String db_url;

        @Override
        protected void onPreExecute() {
            Toast.makeText(forgotPassword.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url="http://acosaf.000webhostapp.com/updatepass.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String mobile,password;
            mobile=params[0];
            password=params[1];


            try {
                URL url=new URL(db_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
                URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuffer buffer=new StringBuffer();
                StringBuilder stringBuilder=new StringBuilder();
                String line="";

                while((line = bufferedReader.readLine())!= null) {
                    stringBuilder.append(line);

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                String data=stringBuilder.toString().trim();

                JSONObject jsonObject=new JSONObject(data);
                String status =jsonObject.getString("status");

//                JSONArray jsonArray=new JSONArray(data);
//                for (int i=0;i<=jsonArray.length();i++) {
//                    JSONObject responseJSON = jsonArray.getJSONObject(i);
//                    String name = responseJSON.getString("sessionname");
//                    String id = responseJSON.getString("sessionid");
//
//
//                }
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
            if (s.contains("successful")) {
                Toast.makeText(forgotPassword.this,"Password Updated", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();

            }
            else
                Toast.makeText(forgotPassword.this,"Sorry,Please try again", Toast.LENGTH_SHORT).show();


        }
    }



}
