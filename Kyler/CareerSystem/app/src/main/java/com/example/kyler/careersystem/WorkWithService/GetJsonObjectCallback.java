package com.example.kyler.careersystem.WorkWithService;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kyler.careersystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import dmax.dialog.SpotsDialog;

/**
 * Created by kyler on 19/04/2016.
 */
public abstract class GetJsonObjectCallback extends AsyncTask<String,Void,JSONObject> implements CallbackReciever {
    private AlertDialog mProgressDialog;
    private String key;
    private boolean dialog = false;
    Activity activity;

    public GetJsonObjectCallback(Activity activity,String key) {
        this.activity = activity;
        this.key = key;
        this.dialog = true;
        mProgressDialog = new SpotsDialog(activity, R.style.Custom);
        mProgressDialog.setCancelable(false);
    }

    public GetJsonObjectCallback(String key) {
        this.key = key;
        this.dialog = false;
    }

    @Override
    protected void onPreExecute() {
        if(dialog) {
            mProgressDialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(dialog){
            if (mProgressDialog != null || mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }
        receiveData(jsonObject);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        StringBuilder strBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestProperty("Authorization", "bearer " + UrlStatic.tokenAccess);
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

    public abstract void receiveData(Object result);
}
