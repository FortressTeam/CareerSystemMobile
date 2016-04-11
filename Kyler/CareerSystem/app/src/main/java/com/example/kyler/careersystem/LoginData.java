package com.example.kyler.careersystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.WorkWithService.GetJsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginData extends AppCompatActivity implements View.OnClickListener {
    private int applicantID = 4;
    private int hiringManagerID =1;
    public static JSONObject jsApplicant,jsHiringManager;
    public static Users users;
    public static Applicants applicants;
    public static HiringManagers hiringManagers;
    private TextView checkDatatv;
    private Button checkDatabt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicantdata);
        checkDatatv = (TextView) findViewById(R.id.applicantData_textview);
        checkDatabt = (Button) findViewById(R.id.applicantData_button);
        checkDatabt.setOnClickListener(this);
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(false);
        pDialog.show();
        int loginas = getIntent().getIntExtra("key",0);
        switch (loginas){
            case 1:
                try {
                jsApplicant = new GetJsonObject(pDialog, "applicant").execute(UrlStatic.URLApplicant + applicantID + ".json").get();
                if (Utilities.checkConnect(jsApplicant)) {
                    startActivity(new Intent(this, ApplicantMainActivity.class));
                    users = new Users(jsApplicant.getJSONObject("user"));
                    applicants = new Applicants(jsApplicant);
                } else {
                    checkDatatv.setVisibility(View.VISIBLE);
                    checkDatabt.setVisibility(View.VISIBLE);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
                break;
            case 2:
//                try {
//                    jsHiringManager = new GetJsonObject(pDialog, "hiringmanager").execute(UrlStatic.URLHiringManager + hiringManagerID + ".json").get();
//                    if (Utilities.checkConnect(jsHiringManager)) {
                        startActivity(new Intent(this, HiringManagerMainActivity.class));
//                        users = new Users(jsHiringManager.getJSONObject("user"));
//                        hiringManagers = new HiringManagers(jsHiringManager);
//                    } else {
//                        checkDatatv.setVisibility(View.VISIBLE);
//                        checkDatabt.setVisibility(View.VISIBLE);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, LoginData.class));
        this.finish();
    }
}
