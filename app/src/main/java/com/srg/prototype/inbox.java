package com.srg.prototype;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

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

public class inbox extends AppCompatActivity {
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> messaged = new ArrayList<String>();
    String sendid, recvid, message, sendername, receivername;
    ArrayList<String> sendID = new ArrayList<String>();
    ArrayList<String> recvID = new ArrayList<String>();
    ArrayList<String> RECVNAME = new ArrayList<String>();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        new getInfo().execute(login.sessionid);
//        username.add("Sheershak Raj Gautam");
//        messaged.add("alikati mulya ghatxa ki ghatdaina?");

    }


    public class getInfo extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(inbox.this, "", "Loading message..", true);
            db_url = "http://acosaf.000webhostapp.com/chatinbox.php";

        }

        @Override
        protected String doInBackground(String... args) {
            String receiverID = args[0];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data_string = URLEncoder.encode("receiverID", "UTF-8") + "=" + URLEncoder.encode(receiverID, "UTF-8");

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
                        sendername = jsonObject.getString("sendername");
                        // receivername=jsonObject.getString("receivername");


                        if (!sendername.equals(login.sessionname)) {

                            messaged.add("Click to see message");
                            sendID.add(sendid);
                            recvID.add(recvid);
                            username.add(sendername);
                            //  RECVNAME.add(receivername);

                        }

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

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            RecyclerView inboxview = findViewById(R.id.recyclerinbox);
            inboxview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            inboxview.setAdapter(new adapterInbox(sendID, recvID, username, messaged));
            progressDialog.dismiss();
        }
    }
}
