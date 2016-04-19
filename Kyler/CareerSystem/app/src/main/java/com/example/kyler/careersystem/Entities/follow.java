package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class follow {
    private int hiringManagerID;
    private int applicantID;
    private boolean followHiringManager;
    private boolean followApplicant;

    public follow(JSONObject jsonObject){
        try {
            if (jsonObject.has("hiring_manager_id")) {
                this.hiringManagerID = jsonObject.getInt("hiring_manager_id");
            }
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
            if (jsonObject.has("follow_hiring_manager")) {
                this.followHiringManager = jsonObject.getBoolean("follow_hiring_manager");
            }
            if (jsonObject.has("follow_applicant")) {
                this.followApplicant = jsonObject.getBoolean("follow_applicant");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public follow(int hiringManagerID, int applicantID, boolean followHiringManager, boolean followApplicant) {
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

    public boolean isFollowHiringManager() {
        return followHiringManager;
    }

    public void setFollowHiringManager(boolean followHiringManager) {
        this.followHiringManager = followHiringManager;
    }

    public boolean isFollowApplicant() {
        return followApplicant;
    }

    public void setFollowApplicant(boolean followApplicant) {
        this.followApplicant = followApplicant;
    }
}
