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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;

import java.util.Calendar;

public class MyresumeExperienceFragment extends Fragment implements View.OnClickListener {

    private Calendar calendar = Calendar.getInstance();
    private EditText experienceCompany,experiencePosition;
    private Button experienceStart,experienceEnd;
    private Switch experienceCurrentJob;
    private TextView experienceStartTextview,experienceEndTextview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_experience_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Experience");
        experienceCompany = (EditText) rootView.findViewById(R.id.myresume_experience_companyname);
        experiencePosition = (EditText) rootView.findViewById(R.id.myresume_experience_position);
        experienceStartTextview = (TextView) rootView.findViewById(R.id.myresume_experience_start_textview);
        experienceEndTextview = (TextView) rootView.findViewById(R.id.myresume_experience_end_textview);
        experienceStart = (Button) rootView.findViewById(R.id.myresume_experience_start_button);
        experienceEnd = (Button) rootView.findViewById(R.id.myresume_experience_end_button);
        experienceCurrentJob = (Switch) rootView.findViewById(R.id.myresume_experience_currentjob);
        experienceCurrentJob.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    experienceEnd.setVisibility(View.GONE);
                    experienceEndTextview.setVisibility(View.GONE);
                }else{
                    experienceEnd.setVisibility(View.VISIBLE);
                    experienceEndTextview.setVisibility(View.VISIBLE);
                }
            }
        });
        experienceStart.setOnClickListener(this);
        experienceEnd.setOnClickListener(this);
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
                if(Utilities.validDatepicker(startDate,endDate))
                    experienceStartTextview.setText(startDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK", null).show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String endDate = Utilities.convertTime(i+"-"+(i1+1)+"-"+i2);
            if(experienceStartTextview.getText().toString().equals(""))
                experienceEndTextview.setText(endDate);
            else{
                String startDate = experienceStartTextview.getText().toString();
                if(Utilities.validDatepicker(startDate,endDate))
                    experienceEndTextview.setText(endDate);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myresume_experience_start_button:
                new DatePickerDialog(getActivity(), startListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_experience_end_button:
                new DatePickerDialog(getActivity(), endListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_education_save:
                break;
            default:
                break;
        }
    }
}
