package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.Entities.SkillTypes;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArray;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyresumeSkillsFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private SearchableSpinner skillMajor,skillSkills;
    private ArrayAdapter<String> adapterSkill;
    private ImageView skillRateStar1,skillRateStar2,skillRateStar3,skillRateStar4,skillRateStar5;
    private Button skillSave;
    private TextView skillRateTextView;
    private ArrayList<SkillTypes> majors;
    private ArrayList<Skills> skills;
    private int applicantID = 4;
    private int skillLevel = 0;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_myresume_skills_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Skills");
        skillMajor = (SearchableSpinner) rootView.findViewById(R.id.myresume_skill_major_spinner);
        skillSkills = (SearchableSpinner) rootView.findViewById(R.id.myresume_skill_skill_spinner);
        skillRateStar1 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar1);
        skillRateStar2 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar2);
        skillRateStar3 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar3);
        skillRateStar4 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar4);
        skillRateStar5 = (ImageView) rootView.findViewById(R.id.myresume_skill_ratestar5);
        skillRateTextView = (TextView) rootView.findViewById(R.id.myresume_skill_rate_textview);
        skillSave = (Button) rootView.findViewById(R.id.myresume_skill_save);
        loadMajorSpinner();
        skillMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), "ID = " + majors.get(i).getID(), Toast.LENGTH_SHORT).show();
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
        skillSave.setOnClickListener(this);
        return rootView;
    }

    private void loadMajorSpinner(){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        JSONArray jsonArray;
        try {
            jsonArray = new GetJsonArray(pDialog, "skillTypes").execute(UrlStatic.URLSkillTypes).get();
            majors = new ArrayList<>();
            ArrayList<String> arrMajor = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                majors.add(new SkillTypes(jsonArray.getJSONObject(i)));
                arrMajor.add(majors.get(i).getSkillTypeName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item,arrMajor);
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
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        JSONArray jsonArray;
        try {
            jsonArray = new GetJsonArray(pDialog, "skills").execute(UrlStatic.URLSkills+"?limit=1000&skill_type_id="+id).get();
            skills = new ArrayList<>();
            ArrayList<String> arrSkills = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                skills.add(new Skills(jsonArray.getJSONObject(i)));
                arrSkills.add(skills.get(i).getSkillName());
            }
            adapterSkill = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item,arrSkills);
            adapterSkill.notifyDataSetChanged();
            skillSkills.setAdapter(adapterSkill);
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
        skillRateTextView.setVisibility(View.VISIBLE);
        switch (input){
            case 1:
                skillRateTextView.setText("Newbie");
                break;
            case 2:
                skillRateTextView.setText("Beginner");
                break;
            case 3:
                skillRateTextView.setText("Experience");
                break;
            case 4:
                skillRateTextView.setText("Senior");
                break;
            case 5:
                skillRateTextView.setText("Master");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myresume_skill_ratestar1:
                skillLevel = 1;
                ratingstar(skillLevel);
                break;
            case R.id.myresume_skill_ratestar2:
                skillLevel = 2;
                ratingstar(skillLevel);
                break;
            case R.id.myresume_skill_ratestar3:
                skillLevel = 3;
                ratingstar(skillLevel);
                break;
            case R.id.myresume_skill_ratestar4:
                skillLevel = 4;
                ratingstar(skillLevel);
                break;
            case R.id.myresume_skill_ratestar5:
                skillLevel = 5;
                ratingstar(skillLevel);
                break;
            case R.id.myresume_skill_save:
                doSave();
                break;
            default:
                break;
        }

    }

    private void doSave(){
        if(skillLevel!=0) {
            JSONObject jsSendData = new JSONObject();
            try {
                jsSendData.put("applicant_id", applicantID);
                jsSendData.put("skill_id", skills.get(skillSkills.getSelectedItemPosition()).getID());
                jsSendData.put("skill_level", skillLevel);
                JSONObject jsResponse = new PostDataWithJson(jsSendData,getActivity()).execute(UrlStatic.URLApplicantsHasSkills).get();
                if(Utilities.isCreateUpdateSuccess(jsResponse)){
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(view.getId()) {
            case R.id.myresume_skill_major_spinner:
                loadSkillsSpinner(majors.get(i).getID());
                break;
            case R.id.myresume_skill_skill_spinner:
                Toast.makeText(getActivity().getApplicationContext(),"skill_id : "+skills.get(i).getID(),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
