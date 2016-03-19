package com.example.kyler.careersystem.GetDataFromService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kyler on 18/03/2016.
 */
public class GetJsonPostDetail extends AsyncTask<String, Void, String> {
    private Activity activity;
    private ProgressDialog pDialog;
    private TextView postTitle,postSalary,postContent;

    public GetJsonPostDetail(Activity activity, TextView postTitle, TextView postSalary, TextView postContent) {
        this.activity = activity;
        this.postTitle = postTitle;
        this.postSalary = postSalary;
        this.postContent = postContent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading profile ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            while((line=reader.readLine()) != null){
                result.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.dismiss();
        try {
            JSONObject parent = new JSONObject(s);
            JSONObject jsonObject = new JSONObject(parent.getString("post"));
            postTitle.setText(jsonObject.getString("post_title"));
            postSalary.setText(jsonObject.getString("post_salary"));
            postContent.setText(jsonObject.getString("post_content"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
