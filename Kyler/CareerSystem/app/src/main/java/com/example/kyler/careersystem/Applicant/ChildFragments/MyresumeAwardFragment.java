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

import java.util.Calendar;

public class MyresumeAwardFragment extends Fragment implements View.OnClickListener {
    private Calendar calendar = Calendar.getInstance();
    private EditText awardName,awardCompetition;
    private Button awardStart,awardEnd,awardSave;
    private TextView awardStartTextView,awardEndTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_award_fragment,container,false);
        awardName = (EditText) rootView.findViewById(R.id.myresume_award_name);
        awardCompetition = (EditText) rootView.findViewById(R.id.myresume_award_description);
        awardStart = (Button) rootView.findViewById(R.id.myresume_award_start_button);
        awardEnd = (Button) rootView.findViewById(R.id.myresume_award_end_button);
        awardSave = (Button) rootView.findViewById(R.id.myresume_award_save);
        awardStartTextView = (TextView) rootView.findViewById(R.id.myresume_award_start_textview);
        awardEndTextView = (TextView) rootView.findViewById(R.id.myresume_award_end_textview);
        awardStart.setOnClickListener(this);
        awardEnd.setOnClickListener(this);
        awardSave.setOnClickListener(this);
        return rootView;
    }


    DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String startDate = Utilities.convertTime(i + "-" + (i1 + 1) + "-" + i2);
            if(awardEndTextView.getText().toString().equals(""))
                awardStartTextView.setText(startDate);
            else{
                String endDate = awardEndTextView.getText().toString();
                if(Utilities.validDatepicker(startDate,endDate))
                    awardStartTextView.setText(startDate);
                else
                    new AlertDialog.Builder(getActivity()).setMessage("Invalid Date").setPositiveButton("OK", null).show();
            }
        }
    };

    DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String endDate = Utilities.convertTime(i+"-"+(i1+1)+"-"+i2);
            if(awardStartTextView.getText().toString().equals(""))
                awardEndTextView.setText(endDate);
            else{
                String startDate = awardStartTextView.getText().toString();
                if(Utilities.validDatepicker(startDate,endDate))
                    awardEndTextView.setText(endDate);
            }
        }
    };

    private boolean isValid(){
        if(awardName.getText().toString().trim().equals("")||awardCompetition.getText().toString().trim().equals("")||awardStartTextView.getText().toString().trim().equals("")||awardEndTextView.getText().toString().trim().equals("")){
            new AlertDialog.Builder(getActivity()).setMessage("You missed something").setPositiveButton("OK",null).show();
            return false;
        }
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.myresume_award_start_button:
                new DatePickerDialog(getActivity(),startListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_award_end_button:
                new DatePickerDialog(getActivity(),startListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.myresume_award_save:
                if(isValid()){
                    Toast.makeText(getActivity().getApplicationContext(),"Fine!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}