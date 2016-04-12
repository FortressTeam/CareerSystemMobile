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
    private Users users;
    private Applicants applicants;
    private HiringManagers hiringManagers;
    private TextView checkDatatv;
    private Button checkDatabt;
    private int loginas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicantdata);
        checkDatatv = (TextView) findViewById(R.id.applicantData_textview);
        checkDatabt = (Button) findViewById(R.id.applicantData_button);
        checkDatabt.setOnClickListener(this);
        loginas= getIntent().getIntExtra("key",0);
        Bundle bundle = getIntent().getBundleExtra("sendData");
        JSONObject jsUser=null;
        try {
            jsUser = new JSONObject(bundle.getString("jsuser"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        users = new Users(jsUser);
        switch (loginas){
            case 1:
                try {
                    applicants = new Applicants(jsUser.getJSONObject("applicant"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.userID = users.getID();
                Utilities.applicantID = applicants.getID();
                Utilities.users = users;
                Utilities.applicants = applicants;
                startActivity(new Intent(this, ApplicantMainActivity.class));
                this.finish();
                break;
            case 2:
                try {
                    hiringManagers = new HiringManagers(jsUser.getJSONObject("hiring_manager"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.userID = users.getID();
                Utilities.hiringmanagerID = hiringManagers.getID();
                Utilities.users = users;
                Utilities.hiringManagers = hiringManagers;
                startActivity(new Intent(this, HiringManagerMainActivity.class));
                this.finish();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, LoginData.class).putExtra("key",loginas));
        this.finish();
    }
}
