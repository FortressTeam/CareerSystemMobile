package com.example.kyler.careersystem.WorkWithService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kyler.careersystem.R;

import org.json.JSONArray;
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
public abstract class GetJsonArrayCallback extends AsyncTask<String,Void,JSONArray> implements CallbackReciever {
    private Activity activity;
    private String key;
    private AlertDialog mProgressDialog;
    private boolean dialog = true;

    public GetJsonArrayCallback(Activity activity, String key) {
        this.activity = activity;
        this.key = key;
        mProgressDialog = new SpotsDialog(activity, R.style.Custom);
        mProgressDialog.setCancelable(false);
    }

    public GetJsonArrayCallback(String key) {
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

    public abstract void receiveData(Object result);

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        if(dialog) {
            if (mProgressDialog != null || mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
        receiveData(jsonArray);
    }

    @Override
    protected JSONArray doInBackground(String... strings) {
        StringBuilder strBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestProperty("Authorization", "bearer "+ UrlStatic.tokenAccess);
            connection.connect();
            if(connection.getResponseCode()==404){
                return null;
            }
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
        JSONArray result = null;
        try {
            JSONObject jsonObject = new JSONObject(strBuilder.toString());
            if(jsonObject.has(key))
                result = new JSONArray(jsonObject.getString(key));
            else
                Log.e("Get JSONArray", "Error in getString with " + key);
            if(result == null )
                Log.e("Get JSONArray", "Error in getting JSONArray");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
