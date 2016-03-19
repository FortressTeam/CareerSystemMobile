package com.example.kyler.careersystem.Applicant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kyler.careersystem.R;


/**
 * Created by kyler on 07/03/2016.
 */
public class FindFragment extends Fragment {
    private EditText findJobInput;
    private Spinner findJobCity;
    private Spinner findJobTown;
    private Spinner findJobMajor;
    private Spinner findJobSalary;
    private Button findJobFind;

    public FindFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_find_job_fragment ,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Find");
        findJobInput = (EditText) rootView.findViewById(R.id.find_job_input_edittext);
        findJobCity = (Spinner) rootView.findViewById(R.id.find_job_city_spinner);
        findJobTown = (Spinner) rootView.findViewById(R.id.find_job_town_spinner);
        findJobMajor = (Spinner) rootView.findViewById(R.id.find_job_major_spinner);
        findJobSalary = (Spinner) rootView.findViewById(R.id.find_job_salary_spinner);
        findJobFind = (Button) rootView.findViewById(R.id.fin_job_button_find);
        findJobFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!findJobInput.getText().equals("")) {
                    Fragment fragment = new FindFragmentResult();
                    Bundle bundle = new Bundle();
                    bundle.putString("findJobResult", findJobInput.getText().toString());
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.frame_main, fragment).commit();
                }
            }
        });
        return rootView;
    }
}
