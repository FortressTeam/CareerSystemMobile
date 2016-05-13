package com.example.kyler.careersystem.WorkWithService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.kyler.careersystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import dmax.dialog.SpotsDialog;

/**
 * Created by kyler on 20/04/2016.
 */
public abstract class PutDataWithJsonCallback extends AsyncTask<String,Void,JSONObject> implements CallbackReciever {
    private JSONObject jsonObject;
    private Activity activity;
    private AlertDialog mProgressDialog;
    private boolean dialog = true;

    public PutDataWithJsonCallback(JSONObject jsonObject, Activity activity) {
        this.jsonObject = jsonObject;
        this.activity = activity;
        mProgressDialog = new SpotsDialog(activity, R.style.Custom);
        mProgressDialog.setCancelable(false);
    }

    public PutDataWithJsonCallback(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.dialog = false;
    }

    public abstract void receiveData(Object result);

    @Override
    protected void onPreExecute() {
        if(dialog) {
            mProgressDialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(dialog) {
            if (mProgressDialog != null || mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
        receiveData(jsonObject);
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        JSONObject result = null;
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        try{
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setRequestProperty("Authorization", "bearer "+ UrlStatic.tokenAccess);
            connection.connect();

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();
            int HttpResult =connection.getResponseCode();
            //"message": "Saved"
            if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                reader.close();
            }else{
                System.out.println(connection.getResponseMessage());
            }
            result = new JSONObject(stringBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e){
            result = null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return result;
    }
}
