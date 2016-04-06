package com.example.kyler.careersystem.WorkWithService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kyler on 21/03/2016.
 */
public class GetJsonObject extends AsyncTask<String,Void,JSONObject> {
    private ProgressDialog pDialog;
    private String key;

    public GetJsonObject(ProgressDialog pDialog, String key) {
        this.pDialog = pDialog;
        this.key = key;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        StringBuilder strBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            if(reader != null){
                while((line = reader.readLine()) != null){
                    strBuilder.append(line);
                }
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        JSONObject result = null;
        try {
            JSONObject jsonObject = new JSONObject(strBuilder.toString());
            if(jsonObject.has(key)) {
                result = new JSONObject(jsonObject.getString(key));
            }
            else
                Log.e("Get JSONObject", "Error in getString with " + key);
            if(result == null )
                Log.e("Get JSONObject", "Error in getting JSONArray");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);
        pDialog.dismiss();
    }
}
