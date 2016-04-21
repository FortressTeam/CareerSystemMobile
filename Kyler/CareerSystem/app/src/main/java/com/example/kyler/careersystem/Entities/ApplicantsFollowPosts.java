package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class ApplicantsFollowPosts {
    private int applicantID;
    private int postID;
    private boolean followStatus;

    public ApplicantsFollowPosts(JSONObject jsonObject){
        try {
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
            if (jsonObject.has("post_id")) {
                this.postID = jsonObject.getInt("post_id");
            }
            if (jsonObject.has("follow_status")) {
                this.followStatus = jsonObject.getBoolean("follow_status");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ApplicantsFollowPosts(int applicantID, int postID, boolean followStatus) {
        this.applicantID = applicantID;
        this.postID = postID;
        this.followStatus = followStatus;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public boolean isFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(boolean followStatus) {
        this.followStatus = followStatus;
    }
}
