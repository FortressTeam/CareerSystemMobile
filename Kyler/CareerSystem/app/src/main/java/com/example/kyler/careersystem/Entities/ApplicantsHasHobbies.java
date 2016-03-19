package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class ApplicantsHasHobbies {
    private int applicantID;
    private int hobbieID;

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
