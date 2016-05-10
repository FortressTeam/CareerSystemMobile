package com.example.kyler.careersystem.Applicant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by kyler on 07/03/2016.
 */
public class SearchFragment extends Fragment implements View.OnClickListener{
    private EditText searchJobInput;
    private SearchableSpinner searchJobLocation;
    private SearchableSpinner searchJobMajor;
    private Button searchJob;
    private ArrayList<Categories> categories = new ArrayList<>();
    private ArrayList<String> arrCategories = new ArrayList<>();

    public SearchFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_search_job_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");
        JSONArray jsonArrayCategories = Utilities.jsArrayCategories;
        for(int i=0;i<jsonArrayCategories.length();i++){
            try {
                categories.add(new Categories(jsonArrayCategories.getJSONObject(i)));
                arrCategories.add(categories.get(i).getCategoryName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        arrCategories.add("All");
        searchJobInput = (EditText) rootView.findViewById(R.id.applicant_search_job_edittext);
        searchJobLocation = (SearchableSpinner) rootView.findViewById(R.id.applicant_search_job_spinner_location);
        searchJobMajor = (SearchableSpinner) rootView.findViewById(R.id.applicant_search_job_spinner_major);
        searchJob = (Button) rootView.findViewById(R.id.applicant_search_job_button);
        String arrLocation[] = {"Da Nang","Quang Nam","All"};
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,arrLocation);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        searchJobLocation.setAdapter(locationAdapter);
        searchJobLocation.setTitle("Select Location");
        searchJobLocation.setSelection(arrLocation.length-1);
        ArrayAdapter<String> majorAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,arrCategories);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        searchJobMajor.setAdapter(majorAdapter);
        searchJobMajor.setTitle("Select Major");
        searchJobMajor.setSelection(arrCategories.size() - 1);
        searchJob.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.applicant_search_job_button:
                String urlSearch = UrlStatic.URLPosts + "?post_status=1&limit=10";
                if  (!searchJobInput.getText().toString().equals("")) {
                    urlSearch += "&q="+searchJobInput.getText().toString();
                }
                if (!searchJobMajor.getSelectedItem().toString().equals("All")) {
                    urlSearch += "&category_id=" + categories.get(searchJobMajor.getSelectedItemPosition()).getID()+"";
                }
                if (!searchJobLocation.getSelectedItem().toString().equals("All")) {
                    urlSearch += "&location=" + searchJobLocation.getSelectedItem().toString();
                }
                urlSearch += "&page=";
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "searchresult", urlSearch);
                break;
        }
    }
}
