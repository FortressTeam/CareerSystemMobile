package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class CurriculumVitaes {
    private int ID;
    private int applicantID;
    private String curriculumVitaeName;
    private int CurriculumVitaeTemplateID;
    private String curriculumVitaeData;

    public CurriculumVitaes(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
//            if (jsonObject.has("applicant_id")) {
//                this.applicantID = jsonObject.getInt("applicant_id");
//            }
            if (jsonObject.has("curriculum_vitae_name")) {
                this.curriculumVitaeName = jsonObject.getString("curriculum_vitae_name");
            }
//            if (jsonObject.has("curriculum_vitae_template_id")) {
//                this.CurriculumVitaeTemplateID = jsonObject.getInt("curriculum_vitae_template_id");
//            }
//            if (jsonObject.has("curriculum_vitae_data")) {
//                this.curriculumVitaeData = jsonObject.getString("curriculum_vitae_data");
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CurriculumVitaes(int ID, String curriculumVitaeName) {
        this.ID = ID;
        this.curriculumVitaeName = curriculumVitaeName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCurriculumVitaeName() {
        return curriculumVitaeName;
    }

    public void setCurriculumVitaeName(String curriculumVitaeName) {
        this.curriculumVitaeName = curriculumVitaeName;
    }
}
