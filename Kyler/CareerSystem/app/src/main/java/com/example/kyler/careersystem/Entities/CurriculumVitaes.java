package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class CurriculumVitaes {
    private int ID;
    private int applicantID;
    private int CurriculumVitaeTemplateID;

    public CurriculumVitaes(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
            if (jsonObject.has("curriculum_vitae_template_id")) {
                this.CurriculumVitaeTemplateID = jsonObject.getInt("curriculum_vitae_template_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CurriculumVitaes(int ID, int applicantID, int curriculumVitaeTemplateID) {
        this.ID = ID;
        this.applicantID = applicantID;
        CurriculumVitaeTemplateID = curriculumVitaeTemplateID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getCurriculumVitaeTemplateID() {
        return CurriculumVitaeTemplateID;
    }

    public void setCurriculumVitaeTemplateID(int curriculumVitaeTemplateID) {
        CurriculumVitaeTemplateID = curriculumVitaeTemplateID;
    }
}
