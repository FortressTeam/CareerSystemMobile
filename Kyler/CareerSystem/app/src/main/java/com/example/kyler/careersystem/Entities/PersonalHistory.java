package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class PersonalHistory {
    private int ID;
    private String personalHistoryTitle;
    private String personalHistoryDetail;
    private Date personalHistoryStart;
    private Date personalHistoryEnd;
    private int applicantID;

    public PersonalHistory(int ID, String personalHistoryTitle, String personalHistoryDetail, Date personalHistoryStart, Date personalHistoryEnd, int applicantID) {
        this.ID = ID;
        this.personalHistoryTitle = personalHistoryTitle;
        this.personalHistoryDetail = personalHistoryDetail;
        this.personalHistoryStart = personalHistoryStart;
        this.personalHistoryEnd = personalHistoryEnd;
        this.applicantID = applicantID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPersonalHistoryTitle() {
        return personalHistoryTitle;
    }

    public void setPersonalHistoryTitle(String personalHistoryTitle) {
        this.personalHistoryTitle = personalHistoryTitle;
    }

    public String getPersonalHistoryDetail() {
        return personalHistoryDetail;
    }

    public void setPersonalHistoryDetail(String personalHistoryDetail) {
        this.personalHistoryDetail = personalHistoryDetail;
    }

    public Date getPersonalHistoryStart() {
        return personalHistoryStart;
    }

    public void setPersonalHistoryStart(Date personalHistoryStart) {
        this.personalHistoryStart = personalHistoryStart;
    }

    public Date getPersonalHistoryEnd() {
        return personalHistoryEnd;
    }

    public void setPersonalHistoryEnd(Date personalHistoryEnd) {
        this.personalHistoryEnd = personalHistoryEnd;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }
}
