package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kyler.careersystem.Entities.SkillTypes;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.GetDataFromService.GetJsonArray;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyresumeSkillsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private Spinner skillMajor,skillSkills;
    private ImageView skillRateStar1,skillRateStar2,skillRateStar3,skillRateStar4,skillRateStar5;
    private ArrayList<SkillTypes> majors;
    private ArrayList<Skills> skills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_skills_fragment,container,false);
        skillMajor = (Spinner) rootView.findViewById(R.id.myresume_skill_major_spinner);
        skillSkills = (Spinner) rootView.findViewById(R.id.myresume_skill_skill_spinner);
        skillRateStar1 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar1);
        skillRateStar2 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar2);
        skillRateStar3 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar3);
        skillRateStar4 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar4);
        skillRateStar5 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar5);
        loadMajorSpinner();
        skillMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), "ID = "+majors.get(i).getID(), Toast.LENGTH_SHORT).show();
                loadSkillsSpinner(majors.get(i).getID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        skillSkills.setOnItemSelectedListener(this);
        skillRateStar1.setOnClickListener(this);
        skillRateStar2.setOnClickListener(this);
        skillRateStar3.setOnClickListener(this);
        skillRateStar4.setOnClickListener(this);
        skillRateStar5.setOnClickListener(this);
        return rootView;
    }

    private void loadMajorSpinner(){
        JSONArray jsonArray;
        try {
            jsonArray = new GetJsonArray(getActivity(),"skillTypes").execute(UrlStatic.URLSkillTypes).get();
            majors = new ArrayList<>();
            ArrayList<String> arrMajor = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                majors.add(new SkillTypes(jsonArray.getJSONObject(i)));
                arrMajor.add(majors.get(i).getSkillTypeName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item,arrMajor);
            skillMajor.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadSkillsSpinner(int id){
        JSONArray jsonArray;
        try {
            jsonArray = new GetJsonArray(getActivity(),"skills").execute(UrlStatic.URLSkillType+id+"/skills.json").get();
            skills = new ArrayList<>();
            ArrayList<String> arrSkills = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                skills.add(new Skills(jsonArray.getJSONObject(i)));
                arrSkills.add(skills.get(i).getSkillName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.spinner_item,arrSkills);
            adapter.notifyDataSetChanged();
            skillSkills.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void ratingstar(int input){
        ArrayList<ImageView> arr = new ArrayList<>();
        arr.add(skillRateStar1);
        arr.add(skillRateStar2);
        arr.add(skillRateStar3);
        arr.add(skillRateStar4);
        arr.add(skillRateStar5);
        for(int i=0;i<arr.size();i++){
            arr.get(i).setImageResource(R.drawable.ratestar);
        }
        for(int i=0;i<input;i++){
            arr.get(i).setImageResource(R.drawable.starfollow);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myresume_skill_ratestar1:
                ratingstar(1);
                break;
            case R.id.myresume_skill_ratestar2:
                ratingstar(2);
                break;
            case R.id.myresume_skill_ratestar3:
                ratingstar(3);
                break;
            case R.id.myresume_skill_ratestar4:
                ratingstar(4);
                break;
            case R.id.myresume_skill_ratestar5:
                ratingstar(5);
                break;

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(view.getId()) {
            case R.id.myresume_skill_major_spinner:
                loadSkillsSpinner(majors.get(i).getID());
                break;
            case R.id.myresume_skill_skill_spinner:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
