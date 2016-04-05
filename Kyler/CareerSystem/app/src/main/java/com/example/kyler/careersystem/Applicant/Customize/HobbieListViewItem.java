package com.example.kyler.careersystem.Applicant.Customize;

/**
 * Created by kyler on 05/04/2016.
 */
public class HobbieListViewItem {
    private int applicantID;
    private int hobbieID;
    private String hobbieName;

    public HobbieListViewItem(int applicantID, int hobbieID, String hobbieName) {
        this.applicantID = applicantID;
        this.hobbieID = hobbieID;
        this.hobbieName = hobbieName;
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

    public String getHobbieName() {
        return hobbieName;
    }

    public void setHobbieName(String hobbieName) {
        this.hobbieName = hobbieName;
    }
}
