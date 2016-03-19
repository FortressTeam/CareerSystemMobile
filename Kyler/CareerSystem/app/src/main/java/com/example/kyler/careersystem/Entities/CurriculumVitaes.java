package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class CurriculumVitaes {
    private int ID;
    private int applicantID;
    private int CurriculumVitaeTemplateID;

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
