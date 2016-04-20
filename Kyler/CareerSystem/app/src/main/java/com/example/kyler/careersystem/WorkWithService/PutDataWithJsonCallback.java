package com.example.kyler.careersystem.WorkWithService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kyler on 20/04/2016.
 */
public abstract class PutDataWithJsonCallback extends AsyncTask<String,Void,JSONObject> implements CallbackReciever {
    private JSONObject jsonObject;
    private Activity activity;
    private ProgressDialog mProgressDialog;

    public PutDataWithJsonCallback(JSONObject jsonObject, Activity activity) {
        this.jsonObject = jsonObject;
        this.activity = activity;
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setMessage("Loading ...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    public abstract void receiveData(Object result);

    @Override
    protected void onPreExecute() {
        mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (mProgressDialog != null || mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
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