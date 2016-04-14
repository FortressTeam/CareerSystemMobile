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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.DeleteDataWithJson;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJson;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class MyresumeExperienceFragment extends Fragment implements View.OnClickListener {

    private Calendar calendar = Calendar.getInstance();
    private EditText experienceCompany,experienceDescription;
    private Button experienceStart,experienceEnd,experienceSave,experienceDelete;
    private Switch experienceCurrentJob;
    private TextView experienceStartTextview,experienceEndTextview;
    private int applicantID=Utilities.applicants.getID();
    private int historyTypeID;
    private boolean currentJob=false;
    private JSONObject jsonData;
    private PersonalHistory personalHistory = null;
    private boolean editMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_experience_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Experience");
        Bundle bundle = getArguments();
        try {
            jsonData = new JSONObject(bundle.getString("sendData"));
            historyTypeID = jsonData.getInt("personalHistoryID");
            if(jsonData.has("personalHistoryData")){
                editMode = true;
                JSONObject jsPersonalHistory = jsonData.getJSONObject("personalHistoryData");
                personalHistory = new PersonalHistory(jsPersonalHistory);
                experienceDelete = (Button) rootView.findViewById(R.id.myresume_experience_delete);
                experienceDelete.setVisibility(View.VISIBLE);
                experienceDelete.setOnClickListener(this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        experienceCompany = (EditText) rootView.findViewById(R.id.myresume_experience_companyname);
        experienceDescription = (EditText) rootView.findViewById(R.id.myresume_experience_description);
        experienceStartTextview = (TextView) rootView.findViewById(R.id.myresume_experience_start_textview);
        experienceEndTextview = (TextView) rootView.findViewById(R.id.myresume_experience_end_textview);
        experienceStart = (Button) rootView.findViewById(R.id.myresume_experience_start_button);
        experienceEnd = (Button) rootView.findViewById(R.id.myresume_experience_end_button);
        experienceSave = (Button) rootView.findViewById(R.id.myresume_experience_save);
        experienceCurrentJob = (Switch) rootView.findViewById(R.id.myresume_experience_currentjob);
        experienceCurrentJob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    currentJob=true;
                    experienceEndTextview.setText("");
                    experienceEnd.setVisibility(View.GONE);
                    experienceEndTextview.setVisibility(View.GONE);
                }else{
                    currentJob=false;
                    experienceEnd.setVisibility(View.VISIBLE);
                    experienceEndTextview.setVisibility(View.VISIBLE);
                }
            }
        });
        if(personalHistory!=null){
            experienceCompany.setText(personalHistory.getPersonalHistoryTitle());
            experienceDescription.setText(personalHistory.getPersonalHistoryDetail());
            experienceStartTextview.setText(personalHistory.getPersonalHistoryStart());
            if(personalHistory.getPersonalHistoryEnd()!=null)
                experienceEndTextview.setText(personalHistory.getPersonalHistoryEnd());
            else
                experienceCurrentJob.setChecked(true);
        }
        experienceStart.setOnClickListener(this);
        experienceEnd.setOnClickListener(this);
        experienceSave.setOnClickListener(this);
        return rootView;
    }


    DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String startDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            if(experienceEndTextview.getText().toString().equals(""))
                experienceStartTextview.setText(startDate);
            else{
                String endDate = experienceEndTextview.getText().toString();
                if(Utilities.validDatepicker(startDate, endDate))
                    experienceStartTextview.setText(startDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK", null).show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String endDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            if(experienceStartTextview.getText().toString().equals(""))
                experienceEndTextview.setText(endDate);
            else{
                String startDate = experienceStartTextview.getText().toString();
                if(Utilities.validDatepicker(startDate, endDate))
                    experienceEndTextview.setText(endDate);
            }
        }
    };

    private boolean isValid(){
        boolean result = false;
        if(experienceCompany.getText().toString().trim().equals("") || experienceDescription.getText().toString().trim().equals("")||experienceStartTextview.getText().toString().equals("")) {
            result = false;
        }
        else {
            if(!experienceEndTextview.getText().toString().equals("")) {
                result = true;
            }else {
                if (currentJob){
                    result = true;
                }else {
                    result = false;
                }
            }
        }
        if(!result){
            new AlertDialog.Builder(getActivity()).setMessage("You missed something").setPositiveButton("OK",null).show();
        }
        return result;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myresume_experience_start_button:
                new DatePickerDialog(getActivity(), startListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_experience_end_button:
                new DatePickerDialog(getActivity(), endListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_experience_save:
                if(isValid()) {
                    if (editMode)
                        doEdit();
                    else
                        doSave();
                }
                break;
            case R.id.myresume_experience_delete:
                doDelete();
                break;
            default:
                break;
        }
    }

    private void doSave(){
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("personal_history_title", experienceCompany.getText());
            sendData.put("personal_history_detail",experienceDescription.getText());
            sendData.put("personal_history_start", Utilities.convertTimePost(experienceStartTextview.getText().toString()));
            if(!currentJob)
                sendData.put("personal_history_end", Utilities.convertTimePost(experienceEndTextview.getText().toString()));
            sendData.put("personal_history_type_id",historyTypeID);
            sendData.put("applicant_id",applicantID);
            JSONObject jsResult = new PostDataWithJson(sendData,getActivity()).execute(UrlStatic.URLPersonalHistory).get();
            if(Utilities.isCreateUpdateSuccess(jsResult)){
                Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                Toast.makeText(getActivity().getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
            }else{
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

    private void doEdit(){
        JSONObject jsEdit = new JSONObject();
        try {
            jsEdit.put("id",personalHistory.getID());
            jsEdit.put("personal_history_title",experienceCompany.getText());
            jsEdit.put("personal_history_detail",experienceDescription.getText());
            jsEdit.put("personal_history_start", Utilities.convertTimePost(experienceStartTextview.getText().toString()));
            jsEdit.put("personal_history_end", Utilities.convertTimePost(experienceEndTextview.getText().toString()));
            JSONObject jsResult = new PutDataWithJson(jsEdit,getActivity()).execute(UrlStatic.URLPersonalHistory2+personalHistory.getID()+".json").get();
            if(Utilities.isCreateUpdateSuccess(jsResult)){
                Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                Toast.makeText(getActivity().getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            }else{
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

    private void doDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            JSONObject jsDelete = new DeleteDataWithJson(getActivity()).execute(UrlStatic.URLPersonalHistory2+personalHistory.getID()+".json").get();
                            if(Utilities.isDeleteSuccess(jsDelete)){
                                Utilities.startActivity(getActivity(), ApplicantMainActivity.class, 2);
                                Toast.makeText(getActivity().getApplicationContext(),"Deleted success",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No",null).show();
    }
}
