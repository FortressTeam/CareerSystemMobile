package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class CurriculumVitaeTemplates {
    private int ID;
    private String CurriculumVitaeTemplateName;
    private String CurriculumVitaeTemplateDescription;
    private String CurriculumVitaeTemplateURL;

    public CurriculumVitaeTemplates(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("curriculum_vitae_template_name")) {
                this.CurriculumVitaeTemplateName = jsonObject.getString("curriculum_vitae_template_name");
            }
            if (jsonObject.has("curriculum_vitae_template_description")) {
                this.CurriculumVitaeTemplateDescription = jsonObject.getString("curriculum_vitae_template_description");
            }
            if (jsonObject.has("curriculum_vitae_template_url")) {
                this.CurriculumVitaeTemplateURL = jsonObject.getString("curriculum_vitae_template_url");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CurriculumVitaeTemplates(int ID, String curriculumVitaeTemplateName, String curriculumVitaeTemplateDescription, String curriculumVitaeTemplateURL) {
        this.ID = ID;
        CurriculumVitaeTemplateName = curriculumVitaeTemplateName;
        CurriculumVitaeTemplateDescription = curriculumVitaeTemplateDescription;
        CurriculumVitaeTemplateURL = curriculumVitaeTemplateURL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCurriculumVitaeTemplateName() {
        return CurriculumVitaeTemplateName;
    }

    public void setCurriculumVitaeTemplateName(String curriculumVitaeTemplateName) {
        CurriculumVitaeTemplateName = curriculumVitaeTemplateName;
    }

    public String getCurriculumVitaeTemplateDescription() {
        return CurriculumVitaeTemplateDescription;
    }

    public void setCurriculumVitaeTemplateDescription(String curriculumVitaeTemplateDescription) {
        CurriculumVitaeTemplateDescription = curriculumVitaeTemplateDescription;
    }

    public String getCurriculumVitaeTemplateURL() {
        return CurriculumVitaeTemplateURL;
    }

    public void setCurriculumVitaeTemplateURL(String curriculumVitaeTemplateURL) {
        CurriculumVitaeTemplateURL = curriculumVitaeTemplateURL;
    }
}
