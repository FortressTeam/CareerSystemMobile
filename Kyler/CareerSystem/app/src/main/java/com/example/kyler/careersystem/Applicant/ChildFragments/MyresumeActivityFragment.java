package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;

import org.json.JSONObject;

import java.util.Calendar;

public class MyresumeActivityFragment extends Fragment implements View.OnClickListener{
    private Calendar calendar = Calendar.getInstance();
    private EditText activityName,activityDescription;
    private Button activityStart,activityEnd,activitySave;
    private TextView activityStartTextView,activityEndTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_activity_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Activity");
        Bundle bundle = getArguments();
        activityName = (EditText) rootView.findViewById(R.id.myresume_activity_name);
        activityDescription = (EditText) rootView.findViewById(R.id.myresume_activity_description);
        activityStart = (Button) rootView.findViewById(R.id.myresume_activity_start_button);
        activityEnd = (Button) rootView.findViewById(R.id.myresume_activity_end_button);
        activitySave = (Button) rootView.findViewById(R.id.myresume_activity_save);
        activityStartTextView = (TextView) rootView.findViewById(R.id.myresume_activity_start_textview);
        activityEndTextView = (TextView) rootView.findViewById(R.id.myresume_activity_end_textview);
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
                if(Utilities.validDatepicker(startDate,endDate))
                    activityStartTextView.setText(startDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK", null).show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String endDate = Utilities.convertTime(i+"-"+(i1+1)+"-"+i2);
            if(activityStartTextView.getText().toString().equals(""))
                activityEndTextView.setText(endDate);
            else{
                String startDate = activityStartTextView.getText().toString();
                if(Utilities.validDatepicker(startDate,endDate))
                    activityEndTextView.setText(endDate);
            }
        }
    };


    private boolean isValid(){
        if(activityName.getText().toString().trim().equals("")||activityDescription.getText().toString().trim().equals("")||activityStartTextView.getText().toString().equals("")||activityEndTextView.getText().toString().equals("")) {
            new AlertDialog.Builder(getActivity()).setMessage("You missed something").setPositiveButton("OK",null).show();
            return false;
        }
        else
            return true;
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
                if(isValid()){
                    Toast.makeText(getActivity().getApplicationContext(),"Activity Name : "+ activityName.getText()+"\n"+activityDescription.getText()+"\nFrom "+activityStartTextView.getText()+" to "+activityEndTextView.getText(),Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
