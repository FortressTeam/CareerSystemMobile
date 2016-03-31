package com.example.kyler.careersystem.Controller;

import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.ApplicantsHasSkills;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.Entities.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 29/03/2016.
 */
public class Applicantcontroller {
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
}