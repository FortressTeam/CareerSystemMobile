package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class follow {
    private int hiringManagerID;
    private int applicantID;
    private int followHiringManager;
    private int followApplicant;

    public follow(int hiringManagerID, int applicantID, int followHiringManager, int followApplicant) {
        this.hiringManagerID = hiringManagerID;
        this.applicantID = applicantID;
        this.followHiringManager = followHiringManager;
        this.followApplicant = followApplicant;
    }

    public int getHiringManagerID() {
        return hiringManagerID;
    }

    public void setHiringManagerID(int hiringManagerID) {
        this.hiringManagerID = hiringManagerID;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getFollowHiringManager() {
        return followHiringManager;
    }

    public void setFollowHiringManager(int followHiringManager) {
        this.followHiringManager = followHiringManager;
    }

    public int getFollowApplicant() {
        return followApplicant;
    }

    public void setFollowApplicant(int followApplicant) {
        this.followApplicant = followApplicant;
    }
}
