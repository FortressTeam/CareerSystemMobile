package com.example.kyler.careersystem.Applicant.Customize;

/**
 * Created by kyler on 31/03/2016.
 */
public class SkillListViewItem {
    private int applicantID;
    private int skillID;
    private String skillName;
    private int skillLevel;

    public SkillListViewItem(int applicantID, int skillID, String skillName, int skillLevel) {
        this.applicantID = applicantID;
        this.skillID = skillID;
        this.skillName = skillName;
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

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
}
