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

public class MyresumeActivityFragment extends Fragment implements View.OnClickListener{
    private Calendar calendar = Calendar.getInstance();
    private EditText activityName,activityDescription;
    private Switch activityCurrentActivity;
    private Button activityStart,activityEnd,activitySave,activityDelete;
    private TextView activityStartTextView,activityEndTextView;
    private boolean currentActivity=false;
    private int applicantID=4;
    private int historyTypeID;
    private JSONObject jsonData;
    private PersonalHistory personalHistory = null;
    private boolean editMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_activity_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Activity");
        Bundle bundle = getArguments();
        try {
            jsonData = new JSONObject(bundle.getString("sendData"));
            historyTypeID = jsonData.getInt("personalHistoryID");
            if(jsonData.has("personalHistoryData")){
                editMode = true;
                JSONObject jsPersonalHistory = jsonData.getJSONObject("personalHistoryData");
                personalHistory = new PersonalHistory(jsPersonalHistory);
                activityDelete = (Button) rootView.findViewById(R.id.myresume_activity_delete);
                activityDelete.setVisibility(View.VISIBLE);
                activityDelete.setOnClickListener(this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        activityName = (EditText) rootView.findViewById(R.id.myresume_activity_name);
        activityDescription = (EditText) rootView.findViewById(R.id.myresume_activity_description);
        activityStart = (Button) rootView.findViewById(R.id.myresume_activity_start_button);
        activityEnd = (Button) rootView.findViewById(R.id.myresume_activity_end_button);
        activitySave = (Button) rootView.findViewById(R.id.myresume_activity_save);
        activityStartTextView = (TextView) rootView.findViewById(R.id.myresume_activity_start_textview);
        activityEndTextView = (TextView) rootView.findViewById(R.id.myresume_activity_end_textview);
        activityCurrentActivity = (Switch) rootView.findViewById(R.id.myresume_activity_currentactivity);
        activityCurrentActivity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    currentActivity=true;
                    activityEndTextView.setText("");
                    activityEnd.setVisibility(View.GONE);
                    activityEndTextView.setVisibility(View.GONE);
                }else{
                    currentActivity=false;
                    activityEnd.setVisibility(View.VISIBLE);
                    activityEndTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        if(personalHistory!=null){
            activityName.setText(personalHistory.getPersonalHistoryTitle());
            activityDescription.setText(personalHistory.getPersonalHistoryDetail());
            activityStartTextView.setText(personalHistory.getPersonalHistoryStart());
            if(personalHistory.getPersonalHistoryEnd()!=null)
                activityEndTextView.setText(personalHistory.getPersonalHistoryEnd()+"");
            else
                activityCurrentActivity.setChecked(true);
        }
        activityStart.setOnClickListener(this);
        activityEnd.setOnClickListener(this);
        activitySave.setOnClickListener(this);
        return rootView;
    }
    DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String startDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            if(activityEndTextView.getText().toString().equals(""))
                activityStartTextView.setText(startDate);
            else{
                String endDate = activityEndTextView.getText().toString();
                if(Utilities.validDatepicker(startDate, endDate))
                    activityStartTextView.setText(startDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK", null).show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String endDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            if(activityStartTextView.getText().toString().equals(""))
                activityEndTextView.setText(endDate);
            else{
                String startDate = activityStartTextView.getText().toString();
                if(Utilities.validDatepicker(startDate, endDate))
                    activityEndTextView.setText(endDate);
            }
        }
    };


    private boolean isValid(){
        boolean result = false;
        if(activityName.getText().toString().trim().equals("") || activityDescription.getText().toString().trim().equals("")||activityStartTextView.getText().toString().equals("")) {
            result = false;
        }
        else {
            if(!activityEndTextView.getText().toString().equals("")) {
                result = true;
            }else {
                if (currentActivity){
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
        switch (view.getId()){
            case R.id.myresume_activity_start_button:
                new DatePickerDialog(getActivity(),startListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_activity_end_button:
                new DatePickerDialog(getActivity(),endListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_activity_save:
                if(isValid()) {
                    if (editMode)
                        doEdit();
                    else
                        doSave();
                }
                break;
            case R.id.myresume_activity_delete:
                doDelete();
                break;
            default:
                break;
        }
    }

    private void doSave(){
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("personal_history_title",activityName.getText());
            sendData.put("personal_history_detail",activityDescription.getText());
            sendData.put("personal_history_start", Utilities.convertTimePost(activityStartTextView.getText().toString()));
            sendData.put("personal_history_end", Utilities.convertTimePost(activityEndTextView.getText().toString()));
            sendData.put("personal_history_type_id",historyTypeID);
            sendData.put("applicant_id",applicantID);
            JSONObject jsResult = new PostDataWithJson(sendData,getActivity()).execute(UrlStatic.URLPersonalHistory).get();
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
    private void doEdit(){
        JSONObject jsEdit = new JSONObject();
        try {
            jsEdit.put("id",personalHistory.getID());
            jsEdit.put("personal_history_title",activityName.getText());
            jsEdit.put("personal_history_detail",activityDescription.getText());
            jsEdit.put("personal_history_start", Utilities.convertTimePost(activityStartTextView.getText().toString()));
            jsEdit.put("personal_history_end", Utilities.convertTimePost(activityEndTextView.getText().toString()));
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
