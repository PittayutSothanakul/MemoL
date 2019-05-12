package com.example.memol;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class fetchCurrency extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    String date = "";
    String rate = "";

    int size = 0;
    String []arraycurrency = new String[size];

    @Override
    protected Void doInBackground(Void... voids) {
        try {
//            URL url = new URL("https://api.myjson.com/bins/j5f6b");
            URL url = new URL("https://api.exchangeratesapi.io/latest");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONObject jo = new JSONObject(data);
//            JSONObject jc = jo.getJSONObject("rate");

//            Iterator<String> iterator = jc.keys();
//            while (iterator.hasNext()) {
//                String key = iterator.next();
//                Log.i("TAG","key:"+key +"--Value::"+jc.optString(key));
//            }

            date = (String) jo.get("date");

//            rate = (String) jo.get("rate");


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
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        AddLedgerActivity.lastestUpdateText.setText("latest update " + this.date);
//        AddLedgerActivity.spinner.setOnItemSelectedListener(this.arraycurrency);

    }
}