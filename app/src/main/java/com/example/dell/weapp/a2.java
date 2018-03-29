package com.example.dell.weapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class a2 extends AppCompatActivity {

    private String TAG = a2.class.getSimpleName();
    private ProgressDialog pDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView  textView1;
    TextView textView3;
static int i;
static int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);
        new GetWeather().execute();

    }

    private class GetWeather extends AsyncTask<Void, Void, Double> {

        Intent intent = getIntent();
        String p1=intent.getStringExtra(a1.EXTRA_TEXT1);
        String p2=intent.getStringExtra(a1.EXTRA_TEXT2);
        String t1=p2.toString();
        String t2=p1.toString();
        private  String url ="http://api.openweathermap.org/data/2.5/weather?q="+t1+","+t2+"&appid=27cda6c32b5a9429145b008d0b23599f";
        // public Double result2;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(a2.this);
            pDialog.setMessage("please wait");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        public Double doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " +jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject main1 = jsonObj.getJSONObject("main");
                    Double temp = main1.getDouble("temp");
                    return temp;
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "check your internet connection!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }


        protected void onPostExecute(Double result) {

            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            final double r=result;
            final double c=(result-273.15);
            final TextView textView = findViewById(R.id.t1);
            final TextView textView3 = findViewById(R.id.tv);
            final TextView textView2 = findViewById(R.id.t2);
            final Switch cswitch=findViewById(R.id.s1);

            if (j>0){if(i==0){
                textView3.setText("C");
                textView.setText(c +" Celsius"); j++;}
            else{  textView3.setText("k");
                textView.setText( r +" Kelvin");j++;}}

            swipeRefreshLayout = findViewById(R.id.Swipe);
            textView1=findViewById(R.id.tttt);
            textView2.setText("Location:"+t1+","+t2);

            cswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean b ) {
                    if(b)
                    {i=0;
                        textView3.setText("C");
                        textView.setText(c +" Celsius");

                    }
                    else{
                        i=1;
                        textView3.setText("k");
                        textView.setText( r +" Kelvin");

                    }

                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    textView1.setText("swipe down to reload");
                    if (i==0){
                        j=0;
                        textView3.setText("C");
                        textView.setText(c +" Celsius");
                        j++;}
                    else{ j=0;
                        textView3.setText("k");
                        textView.setText( r +" Kelvin");
                        j++;}
                    new GetWeather().execute();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    },40);
                }
            });

        }
        }
    }



