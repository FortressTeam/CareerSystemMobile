package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.DeleteDataWithJsonCallback;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJsonCallback;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJsonCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MyresumeAwardFragment extends Fragment implements View.OnClickListener {
    private Calendar calendar = Calendar.getInstance();
    private EditText awardName,awardDescription;
    private Button awardTime,awardSave,awardDelete;
    private TextView awardTimeTextView;
    private int applicantID=Utilities.applicants.getID();
    private int historyTypeID;
    private JSONObject jsonData;
    private PersonalHistory personalHistory = null;
    private boolean editMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_award_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Award");

        Utilities.hideSoftKeyboard(getActivity(), rootView.findViewById(R.id.applicant_myresume_award));
        Bundle bundle = getArguments();
        try {
            jsonData = new JSONObject(bundle.getString("sendData"));
            historyTypeID = jsonData.getInt("personalHistoryID");
            if(jsonData.has("personalHistoryData")){
                editMode = true;
                JSONObject jsPersonalHistory = jsonData.getJSONObject("personalHistoryData");
                personalHistory = new PersonalHistory(jsPersonalHistory);
                awardDelete = (Button) rootView.findViewById(R.id.myresume_award_delete);
                awardDelete.setVisibility(View.VISIBLE);
                awardDelete.setOnClickListener(this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        awardName = (EditText) rootView.findViewById(R.id.myresume_award_name);
        awardDescription = (EditText) rootView.findViewById(R.id.myresume_award_description);
        awardTime = (Button) rootView.findViewById(R.id.myresume_award_time_button);
        awardSave = (Button) rootView.findViewById(R.id.myresume_award_save);
        awardTimeTextView = (TextView) rootView.findViewById(R.id.myresume_award_time_textview);
        if(personalHistory!=null){
            awardName.setText(personalHistory.getPersonalHistoryTitle());
            awardDescription.setText(personalHistory.getPersonalHistoryDetail());
            awardTimeTextView.setText(personalHistory.getPersonalHistoryStart());
        }
        awardTime.setOnClickListener(this);
        awardSave.setOnClickListener(this);
        if(!editMode){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) awardSave.getLayoutParams();
            params.weight = 1;
        }
        return rootView;
    }


    DatePickerDialog.OnDateSetListener timeListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String startDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            awardTimeTextView.setText(startDate);
        }
    };

    private boolean isValid(){
        if(awardName.getText().toString().trim().equals("")||awardDescription.getText().toString().trim().equals("")||awardTimeTextView.getText().toString().trim().equals("")){
            new AlertDialog.Builder(getActivity()).setMessage("You missed something").setPositiveButton("OK",null).show();
            return false;
        }
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.myresume_award_time_button:
                new DatePickerDialog(getActivity(),timeListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_award_save:
                if(isValid()) {
                    if (editMode)
                        doEdit();
                    else
                        doSave();
                }
                break;
            case R.id.myresume_award_delete:
                doDelete();
                break;
            default:
                break;
        }
    }

    private void doSave(){
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("personal_history_title",awardName.getText());
            sendData.put("personal_history_detail",awardDescription.getText());
            sendData.put("personal_history_start", Utilities.convertTimePost(awardTimeTextView.getText().toString()));
            sendData.put("personal_history_type_id",historyTypeID);
            sendData.put("applicant_id",applicantID);
            PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(sendData,getActivity()) {
                @Override
                public void receiveData(Object result) {
                    JSONObject jsResult = (JSONObject) result;
                    if(Utilities.isCreateUpdateSuccess(jsResult)){
                        Utilities.jsApplicant = null;
                        Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                        Toast.makeText(getActivity().getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                    }
                }
            };
            postDataWithJsonCallback.execute(UrlStatic.URLPersonalHistory);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doEdit(){
        JSONObject jsEdit = new JSONObject();
        try {
            jsEdit.put("id",personalHistory.getID());
            jsEdit.put("personal_history_title", awardName.getText());
            jsEdit.put("personal_history_detail",awardDescription.getText());
            jsEdit.put("personal_history_start", Utilities.convertTimePost(awardTimeTextView.getText().toString()));
            PutDataWithJsonCallback putDataWithJsonCallback = new PutDataWithJsonCallback(jsEdit,getActivity()) {
                @Override
                public void receiveData(Object result) {
                    JSONObject jsResult = (JSONObject) result;
                    if(Utilities.isCreateUpdateSuccess(jsResult)){
                        Utilities.jsApplicant = null;
                        Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                        Toast.makeText(getActivity().getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                    }
                }
            };
            putDataWithJsonCallback.execute(UrlStatic.URLPersonalHistory2+personalHistory.getID()+".json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteDataWithJsonCallback deleteDataWithJsonCallback = new DeleteDataWithJsonCallback(getActivity()) {
                            @Override
                            public void receiveData(Object result) {
                                JSONObject jsDelete = (JSONObject) result;
                                if(Utilities.isDeleteSuccess(jsDelete)){
                                    Utilities.jsApplicant = null;
                                    Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                                    Toast.makeText(getActivity().getApplicationContext(),"Deleted success",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        deleteDataWithJsonCallback.execute(UrlStatic.URLPersonalHistory2+personalHistory.getID()+".json");
                    }
                })
                .setNegativeButton("No",null).show();
    }
}
