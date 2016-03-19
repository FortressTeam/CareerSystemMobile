package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class CurriculumVitaeTemplates {
    private int ID;
    private String TemplateName;
    private String TemplateDescription;
    private String TemplateURL;

    public CurriculumVitaeTemplates(int ID, String templateName, String templateDescription, String templateURL) {
        this.ID = ID;
        TemplateName = templateName;
        TemplateDescription = templateDescription;
        TemplateURL = templateURL;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        TemplateName = templateName;
    }

    public String getTemplateDescription() {
        return TemplateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        TemplateDescription = templateDescription;
    }

    public String getTemplateURL() {
        return TemplateURL;
    }

    public void setTemplateURL(String templateURL) {
        TemplateURL = templateURL;
    }
}
