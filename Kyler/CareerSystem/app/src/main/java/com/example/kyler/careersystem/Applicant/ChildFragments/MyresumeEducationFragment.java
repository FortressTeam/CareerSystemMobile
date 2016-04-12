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

public class MyresumeEducationFragment extends Fragment implements View.OnClickListener {
    private Calendar calendar = Calendar.getInstance();
    private EditText educationUniversity,educationDescription;
    private Button educationStart,educationEnd,educationSave,educationDelete;
    private TextView educationStartTextView,educationEndTextView;
    private int applicantID=Utilities.applicantID;
    private int historyTypeID;
    private JSONObject jsonData;
    private PersonalHistory personalHistory = null;
    private boolean editMode = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_education_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Education");
        Bundle bundle = getArguments();
        try {
            jsonData = new JSONObject(bundle.getString("sendData"));
            historyTypeID = jsonData.getInt("personalHistoryID");
            if(jsonData.has("personalHistoryData")){
                editMode = true;
                JSONObject jsPersonalHistory = jsonData.getJSONObject("personalHistoryData");
                personalHistory = new PersonalHistory(jsPersonalHistory);
                educationDelete = (Button) rootView.findViewById(R.id.myresume_education_delete);
                educationDelete.setVisibility(View.VISIBLE);
                educationDelete.setOnClickListener(this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        educationUniversity = (EditText) rootView.findViewById(R.id.myresume_education_university);
        educationDescription = (EditText) rootView.findViewById(R.id.myresume_education_description);
        educationStart = (Button) rootView.findViewById(R.id.myresume_education_start_button);
        educationEnd = (Button) rootView.findViewById(R.id.myresume_education_end_button);
        educationStartTextView = (TextView) rootView.findViewById(R.id.myresume_education_start_textview);
        educationEndTextView = (TextView) rootView.findViewById(R.id.myresume_education_end_textview);
        educationSave = (Button) rootView.findViewById(R.id.myresume_education_save);
        if(personalHistory!=null){
            educationUniversity.setText(personalHistory.getPersonalHistoryTitle());
            educationDescription.setText(personalHistory.getPersonalHistoryDetail());
            educationStartTextView.setText(personalHistory.getPersonalHistoryStart());
            educationEndTextView.setText(personalHistory.getPersonalHistoryEnd()+"");
        }
        educationStart.setOnClickListener(this);
        educationEnd.setOnClickListener(this);
        educationSave.setOnClickListener(this);
        return rootView;
    }

    DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String startDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            if(educationEndTextView.getText().toString().equals(""))
                educationStartTextView.setText(startDate);
            else{
                String endDate = educationEndTextView.getText().toString();
                if(Utilities.validDatepicker(startDate, endDate))
                    educationStartTextView.setText(startDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK",null).show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String endDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            if(educationStartTextView.getText().toString().equals(""))
                educationEndTextView.setText(endDate);
            else{
                String startDate = educationStartTextView.getText().toString();
                if(Utilities.validDatepicker(startDate, endDate))
                    educationEndTextView.setText(endDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK",null).show();
            }
        }
    };

    private boolean isValid(){
        if(educationUniversity.getText().toString().trim().equals("")||educationDescription.getText().toString().trim().equals("")||educationStartTextView.getText().toString().equals("")||educationEndTextView.getText().toString().equals("")) {
            new AlertDialog.Builder(getActivity()).setMessage("You missed something").setPositiveButton("OK",null).show();
            return false;
        }
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myresume_education_start_button:
                new DatePickerDialog(getActivity(),startListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_education_end_button:
                new DatePickerDialog(getActivity(),endListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_education_save:
                if(isValid()) {
                    if (editMode)
                        doEdit();
                    else
                        doSave();
                }
                break;
            case R.id.myresume_education_delete:
                doDelete();
                break;
            default:
                break;
        }
    }

    private void doSave(){
        JSONObject sendData = new JSONObject();
        try {
            sendData.put("personal_history_title",educationUniversity.getText());
            sendData.put("personal_history_detail",educationDescription.getText());
            sendData.put("personal_history_start", Utilities.convertTimePost(educationStartTextView.getText().toString()));
            sendData.put("personal_history_end", Utilities.convertTimePost(educationEndTextView.getText().toString()));
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
            jsEdit.put("personal_history_title",educationUniversity.getText());
            jsEdit.put("personal_history_detail",educationDescription.getText());
            jsEdit.put("personal_history_start", Utilities.convertTimePost(educationStartTextView.getText().toString()));
            jsEdit.put("personal_history_end", Utilities.convertTimePost(educationEndTextView.getText().toString()));
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
