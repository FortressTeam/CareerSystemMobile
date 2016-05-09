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
import com.example.kyler.careersystem.WorkWithService.PutDataWithJsonCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class MyresumeAboutFragment extends Fragment {
    private EditText aboutContent;
    private Button aboutSave;
    private JSONObject jsReceive=null;
    private int applicantID = Utilities.applicants.getID();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_about_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit About");
        Utilities.hideSoftKeyboard(getActivity(),rootView.findViewById(R.id.applicant_myresume_about));
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
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("applicant_about",aboutContent.getText().toString().trim());
                    PutDataWithJsonCallback putDataWithJsonCallback = new PutDataWithJsonCallback(jsonObject,getActivity()) {
                        @Override
                        public void receiveData(Object result) {
                            JSONObject jsresult = (JSONObject) result;
                            if(Utilities.isCreateUpdateSuccess(jsresult)){
                                Utilities.jsApplicant = null;
                                Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                                Toast.makeText(getActivity().getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    putDataWithJsonCallback.execute(UrlStatic.URLApplicant+applicantID+".json");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }
}
