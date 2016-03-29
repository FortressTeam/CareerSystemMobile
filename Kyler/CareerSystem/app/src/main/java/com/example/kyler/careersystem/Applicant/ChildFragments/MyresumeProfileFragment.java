package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.kyler.careersystem.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MyresumeProfileFragment extends Fragment implements View.OnClickListener{
    private EditText profileName,profileHometown,profileBirthday;
    private RadioButton rbMale,rbFemale;
    private Button profileSave;
    private JSONObject jsReceive = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.applicant_myresume_profile_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Profile");
        Bundle bundle = getArguments();
        try{ jsReceive= new JSONObject(bundle.getString("sendData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        profileName = (EditText) rootView.findViewById(R.id.myresume_profile_name);
        profileHometown = (EditText) rootView.findViewById(R.id.myresume_profile_hometown);
        profileBirthday = (EditText) rootView.findViewById(R.id.myresume_profile_birthday);
        rbMale = (RadioButton) rootView.findViewById(R.id.myresume_profile_rbmale);
        rbFemale = (RadioButton) rootView.findViewById(R.id.myresume_profile_rbfemale);
        profileSave = (Button) rootView.findViewById(R.id.myresume_profile_save);
        profileSave.setOnClickListener(this);
        loadOldData(jsReceive);
        return rootView;
    }

    private void loadOldData(JSONObject jsonObject){
        try {
            if (jsonObject.has("applicant_name"))
                profileName.setText(jsonObject.getString("applicant_name"));
            if (jsonObject.has("applicant_date_of_birth"))
                profileBirthday.setText(jsonObject.getString("applicant_date_of_birth"));
            if(jsonObject.has("applicant_country"))
                profileHometown.setText(jsonObject.getString("applicant_country"));
            rbMale.setChecked(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        String userName=profileName.getText().toString();
        String userHometown=profileHometown.getText().toString();
        String userBirthday=profileBirthday.getText().toString();
        String userSex="";
        if(rbMale.isChecked())
            userSex="Male";
        else
            userSex="Female";
        Toast.makeText(getActivity().getApplicationContext(),userName + "\n" + userSex + "\n" + userHometown + "\n" + userBirthday,Toast.LENGTH_SHORT).show();
    }
}
