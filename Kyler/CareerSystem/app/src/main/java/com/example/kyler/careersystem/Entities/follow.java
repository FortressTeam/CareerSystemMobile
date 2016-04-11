package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class follow {
    private int hiringManagerID;
    private int applicantID;
    private int followHiringManager;
    private int followApplicant;

    public follow(JSONObject jsonObject){
        try {
            if (jsonObject.has("hiring_manager_id")) {
                this.hiringManagerID = jsonObject.getInt("hiring_manager_id");
            }
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
            if (jsonObject.has("follow_hiring_manager")) {
                this.followHiringManager = jsonObject.getInt("follow_hiring_manager");
            }
            if (jsonObject.has("follow_applicant")) {
                this.followApplicant = jsonObject.getInt("follow_applicant");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
