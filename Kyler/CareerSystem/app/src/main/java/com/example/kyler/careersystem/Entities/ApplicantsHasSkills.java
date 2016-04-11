package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class ApplicantsHasSkills {
    private int applicantID;
    private int skillID;
    private int skillLevel;

    public ApplicantsHasSkills(JSONObject jsonObject){
        try {
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
            if (jsonObject.has("skill_id")) {
                this.skillID = jsonObject.getInt("skill_id");
            }
            if (jsonObject.has("skill_level")) {
                this.skillLevel = jsonObject.getInt("skill_level");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ApplicantsHasSkills(int applicantID, int skillID, int skillLevel) {
        this.applicantID = applicantID;
        this.skillID = skillID;
        this.skillLevel = skillLevel;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
}
