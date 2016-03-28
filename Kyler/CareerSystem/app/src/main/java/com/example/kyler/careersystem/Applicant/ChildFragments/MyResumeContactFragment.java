package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kyler.careersystem.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MyResumeContactFragment extends Fragment {

    private EditText contactPhone,contactEmail,contactAddress;
    private Button contactSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_contact_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Contact");
        Bundle bundle = getArguments();
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(bundle.getString("sendData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        contactPhone = (EditText) rootView.findViewById(R.id.myresume_contact_phone);
        contactEmail = (EditText) rootView.findViewById(R.id.myresume_contact_email);
        contactAddress = (EditText) rootView.findViewById(R.id.myresume_contact_Address);
        contactSave = (Button) rootView.findViewById(R.id.myresume_contact_save);
        loadOldData(jsonObject);
        contactSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),contactPhone.getText()+"\n"+contactAddress.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
    private void loadOldData(JSONObject jsonObject){
        try{
            if(jsonObject.has("applicant_phone"))
                contactPhone.setText(jsonObject.getString("applicant_phone"));
            if(jsonObject.has("applicant_address"))
                contactAddress.setText(jsonObject.getString("applicant_address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
