package com.example.kyler.careersystem.Applicant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.FailedConnectionFragment;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ApplicantData extends AppCompatActivity implements View.OnClickListener{
    private int applicantID=4;
    public static JSONObject jsApplicant;
    public static Users users;
    public static Applicants applicants;
    private TextView applicantDatatv;
    private Button applicantDatabt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicantdata);
        applicantDatatv = (TextView) findViewById(R.id.applicantData_textview);
        applicantDatabt = (Button) findViewById(R.id.applicantData_button);
        applicantDatabt.setOnClickListener(this);
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        try {
            jsApplicant = new GetJsonObject(pDialog, "applicant").execute(UrlStatic.URLApplicant + applicantID + ".json").get();
            users = new Users(jsApplicant.getJSONObject("user"));
            applicants = new Applicants(jsApplicant);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Utilities.checkConnect(jsApplicant)){
            startActivity(new Intent(this, ApplicantMainActivity.class));
        }else{
            applicantDatatv.setVisibility(View.VISIBLE);
            applicantDatabt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,ApplicantData.class));
        this.finish();
    }
}
