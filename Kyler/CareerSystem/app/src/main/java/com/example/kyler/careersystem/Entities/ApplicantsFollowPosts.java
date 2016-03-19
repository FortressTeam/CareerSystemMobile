package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class ApplicantsFollowPosts {
    private int applicantID;
    private int postID;

    public ApplicantsFollowPosts(int applicantID, int postID) {
        this.applicantID = applicantID;
        this.postID = postID;
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
}
