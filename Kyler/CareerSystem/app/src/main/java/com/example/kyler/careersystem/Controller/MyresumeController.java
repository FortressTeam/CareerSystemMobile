package com.example.kyler.careersystem.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.Customize.SkillListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewItem;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.ApplicantsHasSkills;
import com.example.kyler.careersystem.Entities.Hobbies;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.Entities.SkillTypes;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.WorkWithService.GetJsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by kyler on 29/03/2016.
 */
public class MyresumeController {
    public Users getUser(JSONObject jsonObject){
        return new Users(jsonObject);
    }
    public Applicants getApplicant(JSONObject jsonObject){
        return new Applicants(jsonObject);
    }
    public PersonalHistory getPersonalHistory(JSONObject jsonObject){
        return new PersonalHistory(jsonObject);
    }
    public ArrayList<PersonalHistory> getPersonalHistories(JSONArray jsonArray){
        ArrayList<PersonalHistory> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new PersonalHistory(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<ApplicantsHasSkills> getApplicantsHasSkills(JSONArray jsonArray) {
        ArrayList<ApplicantsHasSkills> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new ApplicantsHasSkills(jsonArray.getJSONObject(i).getJSONObject("_joinData")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<Skills> getSkills(JSONArray jsonArray) {
        ArrayList<Skills> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new Skills(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<Hobbies> getHobbies(JSONArray jsonArray){
        ArrayList<Hobbies> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new Hobbies(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<SkillTypes> getSkillTypes(JSONArray jsonArray){
        ArrayList<SkillTypes> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new SkillTypes(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }
}
