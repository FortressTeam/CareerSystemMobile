package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MyresumeContactFragment extends Fragment {

    private EditText contactPhone,contactEmail,contactAddress;
    private Button contactSave;
    private JSONObject jsReceive=null;
    private int applicantID=4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_contact_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Contact");
        Bundle bundle = getArguments();
        try{
            jsReceive = new JSONObject(bundle.getString("sendData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        contactPhone = (EditText) rootView.findViewById(R.id.myresume_contact_phone);
        contactEmail = (EditText) rootView.findViewById(R.id.myresume_contact_email);
        contactAddress = (EditText) rootView.findViewById(R.id.myresume_contact_Address);
        contactSave = (Button) rootView.findViewById(R.id.myresume_contact_save);
        loadOldData(jsReceive);
        contactSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("applicant_phone_number", contactPhone.getText().toString());
                    jsonObject.put("user_email", contactEmail.getText().toString());
                    jsonObject.put("applicant_address", contactAddress.getText().toString());
                    JSONObject jsresult = new PutDataWithJson(jsonObject, getActivity()).execute(UrlStatic.URLApplicant + applicantID + ".json").get();
                    if (Utilities.isCreateUpdateSuccess(jsresult)) {
                        Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }
    private void loadOldData(JSONObject jsonObject){
        try{
            if(jsonObject.has("applicant_phone_number"))
                contactPhone.setText(jsonObject.getString("applicant_phone_number"));
            if(jsonObject.has("user_email"))
                contactEmail.setText(jsonObject.getString("user_email"));
            if(jsonObject.has("applicant_address"))
                contactAddress.setText(jsonObject.getString("applicant_address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
