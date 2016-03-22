package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class ApplicantsHasHobbies {
    private int applicantID;
    private int hobbieID;

    public ApplicantsHasHobbies(JSONObject jsonObject){
        try {
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
            if (jsonObject.has("hobbieID")) {
                this.hobbieID = jsonObject.getInt("hobbie_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ApplicantsHasHobbies(int applicantID, int hobbieID) {
        this.applicantID = applicantID;
        this.hobbieID = hobbieID;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getHobbieID() {
        return hobbieID;
    }

    public void setHobbieID(int hobbieID) {
        this.hobbieID = hobbieID;
    }
}
