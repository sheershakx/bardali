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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class signup extends AppCompatActivity {
    EditText name, mobile, pass, repass;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //typecasting
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.confirmPassword);

        register = findViewById(R.id.register);

        //passing the edittext values to some strings

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Mobile = mobile.getText().toString();
                String password = pass.getText().toString();
                String repassword=repass.getText().toString();

                if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Mobile) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {

                    if (password.equals(repassword)) {

                        Doregister doregister = new Doregister();
                        doregister.execute(Name, Mobile, password);
                    }
                    else Toast.makeText(signup.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(signup.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void loginRedirect(View view) {

        startActivity(new Intent(getApplicationContext(),login.class));
    }

    public class Doregister extends AsyncTask<String, String, String>  {
        String db_url;


        @Override
        protected void onPreExecute() {
            Toast.makeText(signup.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url="http://testprasis.000webhostapp.com/register.php";



        }

        @Override
        protected String doInBackground(String... args) {
            String name,mobile,password;
            name=args[0];
            mobile=args[1];
            password=args[2];

            try {
                URL url=new URL(db_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "You have registered succesfully";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return  null;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(signup.this, s, Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), login.class));


        }
    }


}






