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

public class chat extends AppCompatActivity {
    Button sendmsg;
    EditText typemessage;
    Integer flag = 0;
    public static String senderID;
    public static String sendername;
    public static String recvname;

    String sendid, recvid, message;
    ArrayList<String> sendID = new ArrayList<String>();
    ArrayList<String> recvID = new ArrayList<String>();
    ArrayList<String> MESSAGE = new ArrayList<String>();

    RecyclerView recyclerViewChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getIncomingIntent();

        sendmsg = findViewById(R.id.sendmessage);
        typemessage = findViewById(R.id.typemessage);
        recyclerViewChat = findViewById(R.id.recyclerviewChat);
        getInfo getInfo = new getInfo();
        getInfo.execute(login.sessionid, senderID);

        //calling handler function


        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = typemessage.getText().toString();

                if (messageContent.isEmpty()) {
                    Toast.makeText(chat.this, "You can't send a blank message", Toast.LENGTH_SHORT).show();
                } else {
                    sendmessage sendmessage = new sendmessage();
                    sendmessage.execute(login.sessionid, senderID, login.sessionname, messageContent);

                }

            }
        });

    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("senderID")) {
            senderID = getIntent().getStringExtra("senderID");
            sendername = getIntent().getStringExtra("sendername");
            recvname = getIntent().getStringExtra("recvname");

        }

    }


    public class getInfo extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url = "http://acosaf.000webhostapp.com/chat.php";

        }

        @Override
        protected String doInBackground(String... args) {
            String senderID = args[0];
            String receiverID = args[1];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data_string = URLEncoder.encode("senderID", "UTF-8") + "=" + URLEncoder.encode(senderID, "UTF-8") + "&" +
                        URLEncoder.encode("receiverID", "UTF-8") + "=" + URLEncoder.encode(receiverID, "UTF-8");

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
                        sendid = jsonObject.getString("senderId");
                        recvid = jsonObject.getString("receiverId");

                        MESSAGE.add(message);
                        sendID.add(sendid);
                        recvID.add(recvid);

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
            recyclerViewChat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerViewChat.setAdapter(new adapterChat(sendID, recvID, MESSAGE));
            recyclerViewChat.smoothScrollToPosition(MESSAGE.size());
        }
    }

    public class sendmessage extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {

            db_url = "http://acosaf.000webhostapp.com/sendmessage.php";

        }

        @Override
        protected String doInBackground(String... args) {
            String senderId, receiverId, sendername, messagecontent;
            senderId = args[0];
            receiverId = args[1];
            sendername = args[2];
            messagecontent = args[3];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("senderID", "UTF-8") + "=" + URLEncoder.encode(senderId, "UTF-8") + "&" +
                        URLEncoder.encode("receiverID", "UTF-8") + "=" + URLEncoder.encode(receiverId, "UTF-8") + "&" +
                        URLEncoder.encode("sendername", "UTF-8") + "=" + URLEncoder.encode(sendername, "UTF-8") + "&" +
                        URLEncoder.encode("receivername", "UTF-8") + "=" + URLEncoder.encode(recvname, "UTF-8") + "&" +
                        URLEncoder.encode("datetime", "UTF-8") + "=" + URLEncoder.encode("2019-10-24 00:00:00.000000", "UTF-8") + "&" +
                        URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(messagecontent, "UTF-8");
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
            Toast.makeText(chat.this, s, Toast.LENGTH_LONG).show();
            try {
                if (flag.equals(0)) {
                    activitylog activitylog = new activitylog();
                    activitylog.execute("You bargained with the user");

                    flag = 1;
                }
            } catch (RuntimeException e) {
                Toast.makeText(chat.this, "Runtime exception occured", Toast.LENGTH_SHORT).show();

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
            MESSAGE.clear();
            sendID.clear();
            recvID.clear();
            getInfo getInfo = new getInfo();
            getInfo.execute(login.sessionid, senderID);
            typemessage.setText("");



        }
    }

}
