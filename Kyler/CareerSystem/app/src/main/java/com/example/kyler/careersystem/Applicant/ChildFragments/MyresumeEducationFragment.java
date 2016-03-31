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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyresumeEducationFragment extends Fragment implements View.OnClickListener {
    private Calendar calendar = Calendar.getInstance();
    private EditText educationUniversity,educationMajor;
    private Spinner educationDegree;
    private Button educationStart,educationEnd,educationSave;
    private TextView educationStartTextView,educationEndTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_education_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Education");
        educationUniversity = (EditText) rootView.findViewById(R.id.myresume_education_university);
        educationMajor = (EditText) rootView.findViewById(R.id.myresume_education_major);
        educationDegree = (Spinner) rootView.findViewById(R.id.myresume_education_degree_spinner);
        educationStart = (Button) rootView.findViewById(R.id.myresume_education_start_button);
        educationEnd = (Button) rootView.findViewById(R.id.myresume_education_end_button);
        educationStartTextView = (TextView) rootView.findViewById(R.id.myresume_education_start_textview);
        educationEndTextView = (TextView) rootView.findViewById(R.id.myresume_education_end_textview);
        educationSave = (Button) rootView.findViewById(R.id.myresume_education_save);
        String[] arr = {"Colege","Bachelog","Master"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item,arr);
        educationDegree.setAdapter(adapter);
        educationStart.setOnClickListener(this);
        educationEnd.setOnClickListener(this);
        educationSave.setOnClickListener(this);
        return rootView;
    }

    DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String startDate = Utilities.convertTime(i+"-"+(i1+1)+"-"+i2);
            if(educationEndTextView.getText().toString().equals(""))
                educationStartTextView.setText(startDate);
            else{
                String endDate = educationEndTextView.getText().toString();
                if(Utilities.validDatepicker(startDate,endDate))
                    educationStartTextView.setText(startDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK",null).show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String endDate = Utilities.convertTime(i+"-"+(i1+1)+"-"+i2);
            if(educationStartTextView.getText().toString().equals(""))
                educationEndTextView.setText(endDate);
            else{
                String startDate = educationStartTextView.getText().toString();
                if(Utilities.validDatepicker(startDate,endDate))
                    educationEndTextView.setText(endDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK",null).show();
            }
        }
    };

    private boolean isValid(){
        if(educationUniversity.getText().toString().trim().equals("")||educationMajor.getText().toString().trim().equals("")||educationStartTextView.getText().toString().equals("")||educationEndTextView.getText().toString().equals("")) {
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
                if(isValid())
                    Toast.makeText(getActivity().getApplicationContext(),"University : "+ educationUniversity.getText()+"\n"+educationDegree.getSelectedItem().toString()+" - "+educationMajor.getText()+"\nFrom "+educationStartTextView.getText()+" to "+educationEndTextView.getText(),Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
