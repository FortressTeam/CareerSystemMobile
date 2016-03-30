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

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.GetDataFromService.PutDataWithJson;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MyresumeProfileFragment extends Fragment implements View.OnClickListener{
    private EditText profileName,profileHometown,profileBirthday;
    private RadioButton rbMale,rbFemale;
    private Button profileSave;
    private JSONObject jsReceive = null;
    private int applicantID=4;

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
            if(jsonObject.has("applicant_address"))
                profileHometown.setText(jsonObject.getString("applicant_address"));
            if(jsonObject.has("applicant_sex"))
                if(jsonObject.getBoolean("applicant_sex"))
                    rbMale.setChecked(true);
                else
                    rbFemale.setChecked(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("applicant_name",profileName.getText().toString());
            jsonObject.put("applicant_address",profileHometown.getText().toString());
            Date date = new SimpleDateFormat("dd - MMM - yyyy").parse(profileBirthday.getText().toString());
            jsonObject.put("applicant_date_of_birth",new SimpleDateFormat("yyyy-MM-dd").format(date));
            jsonObject.put("applicant_sex",rbMale.isChecked());
            JSONObject jsresult =  new PutDataWithJson(jsonObject, getActivity()).execute(UrlStatic.URLApplicant + applicantID + ".json").get();
            if(Utilities.isCreateUpdateSuccess(jsresult)){
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
            }else{
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
