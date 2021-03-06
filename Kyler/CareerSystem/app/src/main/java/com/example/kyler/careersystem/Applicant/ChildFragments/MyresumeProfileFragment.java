package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJsonCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyresumeProfileFragment extends Fragment implements View.OnClickListener{
    private Calendar calendar = Calendar.getInstance();
    private EditText profileName,profileHometown,profileBirthday;
    private ImageView profileBirthdayImageView;
    private RadioButton rbMale,rbFemale;
    private Button profileSave;
    private JSONObject jsReceive = null;
    private int applicantID=Utilities.applicants.getID();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.applicant_myresume_profile_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Profile");
        Utilities.hideSoftKeyboard(getActivity(), rootView.findViewById(R.id.applicant_myresume_profile));
        Bundle bundle = getArguments();
        try{ jsReceive= new JSONObject(bundle.getString("sendData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        profileName = (EditText) rootView.findViewById(R.id.myresume_profile_name);
        profileBirthdayImageView = (ImageView) rootView.findViewById(R.id.myresume_profile_birthday_imageview);
        profileHometown = (EditText) rootView.findViewById(R.id.myresume_profile_hometown);
        profileBirthday = (EditText) rootView.findViewById(R.id.myresume_profile_birthday);
        rbMale = (RadioButton) rootView.findViewById(R.id.myresume_profile_rbmale);
        rbFemale = (RadioButton) rootView.findViewById(R.id.myresume_profile_rbfemale);
        profileSave = (Button) rootView.findViewById(R.id.myresume_profile_save);
        profileSave.setOnClickListener(this);
        profileBirthdayImageView.setOnClickListener(this);
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

    DatePickerDialog.OnDateSetListener datePickerListenner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String dateBirthday = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            profileBirthday.setText(dateBirthday);
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myresume_profile_save:
                doSave();
                break;
            case R.id.myresume_profile_birthday_imageview:
                new DatePickerDialog(getActivity(),datePickerListenner,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }

    }

    private void doSave(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("applicant_name",profileName.getText().toString());
            jsonObject.put("applicant_address",profileHometown.getText().toString());
            Date date = new SimpleDateFormat("dd - MMM - yyyy").parse(profileBirthday.getText().toString());
            jsonObject.put("applicant_date_of_birth",new SimpleDateFormat("yyyy-MM-dd").format(date));
            jsonObject.put("applicant_sex",rbMale.isChecked());
            PutDataWithJsonCallback putDataWithJsonCallback = new PutDataWithJsonCallback(jsonObject,getActivity()) {
                @Override
                public void receiveData(Object result) {
                    JSONObject jsresult =  (JSONObject) result;
                    Utilities.applicants.setApplicantName(profileName.getText().toString());
                    if(Utilities.isCreateUpdateSuccess(jsresult)){
                        Utilities.jsApplicant = null;
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                    }else{
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            putDataWithJsonCallback.execute(UrlStatic.URLApplicant + applicantID + ".json");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
