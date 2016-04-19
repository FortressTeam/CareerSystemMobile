package com.example.kyler.careersystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Hobbies;
import com.example.kyler.careersystem.Entities.SkillTypes;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginData extends AppCompatActivity implements View.OnClickListener {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

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
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    String token = intent.getStringExtra("token");

                    Toast.makeText(getApplicationContext(), "GCM registration token: " + token, Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(SENT_TOKEN_TO_SERVER)) {
                    // gcm registration id is stored in our server's MySQL

                    Toast.makeText(getApplicationContext(), "GCM registration token is stored in server!", Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(PUSH_NOTIFICATION)) {
                    // new push notification is received

                    Toast.makeText(getApplicationContext(), "Push notification is received!", Toast.LENGTH_LONG).show();
                }
            }
        };

        registerGCM();

        checkDatatv = (TextView) findViewById(R.id.applicantData_textview);
        checkDatabt = (Button) findViewById(R.id.applicantData_button);
        checkDatabt.setOnClickListener(this);
        loginas= getIntent().getIntExtra("key",3);
        Bundle bundle = getIntent().getBundleExtra("sendData");
        JSONObject jsUser=null;
        try {
            jsUser = new JSONObject(bundle.getString("jsuser"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        users = new Users(jsUser);
        Utilities.users = users;
        switch (loginas){
            case 3:
                try {
                    applicants = new Applicants(jsUser.getJSONObject("applicant"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.applicants = applicants;
                startActivity(new Intent(this, ApplicantMainActivity.class));
                finish();
                break;
            case 2:
                try {
                    hiringManagers = new HiringManagers(jsUser.getJSONObject("hiring_manager"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.hiringManagers = hiringManagers;
                startActivity(new Intent(this, HiringManagerMainActivity.class));
                finish();
                break;
        }
    }

    // starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, LoginData.class).putExtra("key",loginas));
        this.finish();
    }
}
