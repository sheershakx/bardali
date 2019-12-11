package com.srg.prototype;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class sellFragment extends Fragment {
    EditText name, desc, Quantity, Rate, Total;
    CheckBox delivery;
    Spinner Unit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sell,container,false);
        final Spinner unit=(Spinner)view.findViewById(R.id.unit1);
        Button postitem=view.findViewById(R.id.postitem);

        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.unit));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(myadapter);


        final EditText quantity = view.findViewById(R.id.itemQuantity);
        final EditText rate = view.findViewById(R.id.itemRate);
        final EditText total = view.findViewById(R.id.itemTotal);

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
                } catch (RuntimeException e){
                    Toast.makeText(getContext(), "Exception just occured", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        postitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttomnav buttomnav=new buttomnav();
                buttomnav.postitem(view);

            }
        });

        return view;

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
            Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

            startActivity(new Intent(getContext(),buttomnav.class));



        }
    }

}
