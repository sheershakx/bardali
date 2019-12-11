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

public class login extends AppCompatActivity {
    EditText mob,pass;
    Button login;
   public static String sessionname,sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //typecasting values
        mob=findViewById(R.id.mobile);
        pass=findViewById(R.id.password);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Mobile=mob.getText().toString();
                String Password=pass.getText().toString();

                if (!TextUtils.isEmpty(Mobile) && !TextUtils.isEmpty(Password)){
                    Dologin dologin=new Dologin();
                    dologin.execute(Mobile,Password);

                }
                else Toast.makeText(login.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void resetpassword(View view) {
        startActivity(new Intent(getApplicationContext(),forgotPassword.class));
        finish();
    }

    public void signupredirect(View view) {
        startActivity(new Intent(getApplicationContext(),signup.class));
        finish();
    }


    public class Dologin extends AsyncTask<String,String,String> {
        String db_url;

        @Override
        protected void onPreExecute() {

            db_url="http://acosaf.000webhostapp.com/login.php";

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
               sessionname=jsonObject.getString("sessionname");
               sessionid =jsonObject.getString("sessionid");
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
                if (s!=null && s.contains("Succesful")) {
                    Toast.makeText(login.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(login.this,"Sorry,Login failed", Toast.LENGTH_SHORT).show();


        }
    }
}
