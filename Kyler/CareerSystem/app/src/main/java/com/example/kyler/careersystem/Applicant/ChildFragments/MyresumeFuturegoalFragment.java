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

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.GetDataFromService.PutDataWithJson;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MyresumeFuturegoalFragment extends Fragment {
    private EditText futuregoalContent;
    private Button futuregoalSave;
    private JSONObject jsReceive=null;
    private int applicantID = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_futuregoal_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Future Goal");
        Bundle bundle = getArguments();
        futuregoalContent = (EditText) rootView.findViewById(R.id.myresume_futuregoal_content);
        futuregoalSave = (Button) rootView.findViewById(R.id.myresume_futuregoal_save);
        try{
            jsReceive = new JSONObject(bundle.getString("sendData"));
            futuregoalContent.setText(jsReceive.getString("applicant_future_goals"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        futuregoalSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("applicant_future_goals",futuregoalContent.getText().toString().trim());
                    JSONObject jsresult = new PutDataWithJson(jsonObject,getActivity()).execute(UrlStatic.URLApplicant+applicantID+".json").get();
                    if(Utilities.isCreateUpdateSuccess(jsresult)){
                        Utilities.startActivity(getActivity(), ApplicantMainActivity.class,2);
                        Toast.makeText(getActivity().getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
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
}