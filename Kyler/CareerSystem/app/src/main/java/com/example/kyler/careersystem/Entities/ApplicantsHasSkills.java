package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class ApplicantsHasSkills {
    private int applicantID;
    private int skillID;
    private int skillLevel;

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
