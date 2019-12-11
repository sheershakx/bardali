package com.srg.prototype;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class openchat extends AppCompatActivity {
    Button sendmsg;
    EditText typemessage;
    Integer flag = 0;
RecyclerView recyclerViewOpenChat;

    String userid, message;

    ArrayList<String> MESSAGE = new ArrayList<String>();
    ArrayList<String> USERID = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openchat);
        recyclerViewOpenChat=findViewById(R.id.recyclerviewChatOpen);

        sendmsg = findViewById(R.id.sendmessageOpen);
        typemessage = findViewById(R.id.typemessageOpen);

        getInfo getInfo = new getInfo();
        getInfo.execute(ItemDescription.sid);

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = typemessage.getText().toString();

                if (messageContent.isEmpty()) {
                    Toast.makeText(openchat.this, "You can't send a blank message", Toast.LENGTH_SHORT).show();
                } else {
                    sendmessage sendmessage = new sendmessage();
                    sendmessage.execute(login.sessionid, ItemDescription.sid, messageContent);
                }



            }
        });

    }


    public class getInfo extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url = "http://acosaf.000webhostapp.com/getopenchat.php";

        }

        @Override
        protected String doInBackground(String... args) {
            String saleno = args[0];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data_string = URLEncoder.encode("saleno", "UTF-8") + "=" + URLEncoder.encode(saleno, "UTF-8");


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
                    if (jsonObject.getString("message") != null) {

                        message = jsonObject.getString("message");
                        userid = jsonObject.getString("userid");


                        MESSAGE.add(message);
                        USERID.add(userid);


                    } else {
                        MESSAGE.add("No Messages yet");
                        USERID.add("00");
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
            recyclerViewOpenChat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerViewOpenChat.setAdapter(new adapterOpenChat(USERID, MESSAGE));
            recyclerViewOpenChat.smoothScrollToPosition(MESSAGE.size());
        }
    }

    public class sendmessage extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url = "http://acosaf.000webhostapp.com/openchat.php";

        }

        @Override
        protected String doInBackground(String... args) {
            String userid, saleno, message;
            userid = args[0];
            saleno = args[1];
            message = args[2];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8") + "&" +
                        URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8") + "&" +
                        URLEncoder.encode("saleno", "UTF-8") + "=" + URLEncoder.encode(saleno, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Message sent succesfully";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            typemessage.setText("");
            Toast.makeText(openchat.this, s, Toast.LENGTH_LONG).show();
            MESSAGE.clear();
            USERID.clear();
            getInfo getInfo = new getInfo();
            getInfo.execute(ItemDescription.sid);

            try {
                if (flag.equals(0)) {
                    activitylog activitylog = new activitylog();
                    activitylog.execute("You entered into a public chat for Item: "+ItemDescription.sitemname );

                    flag = 1;
                }
            } catch (RuntimeException e) {
                Toast.makeText(openchat.this, "Runtime exception occured", Toast.LENGTH_SHORT).show();

            }


        }
    }

    //API calling
    public class activitylog extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
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



        }
    }

}
