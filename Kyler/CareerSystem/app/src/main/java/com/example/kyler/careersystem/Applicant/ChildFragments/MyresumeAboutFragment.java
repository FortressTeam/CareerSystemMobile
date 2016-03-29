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

public class MyresumeAboutFragment extends Fragment {
    private EditText aboutContent;
    private Button aboutSave;
    private JSONObject jsReceive=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_about_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit About");
        Bundle bundle = getArguments();
        aboutContent = (EditText) rootView.findViewById(R.id.myresume_about_content);
        aboutSave = (Button) rootView.findViewById(R.id.myresume_about_save);
        try{
            jsReceive = new JSONObject(bundle.getString("sendData"));
            aboutContent.setText(jsReceive.getString("applicant_about"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        aboutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(),aboutContent.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
