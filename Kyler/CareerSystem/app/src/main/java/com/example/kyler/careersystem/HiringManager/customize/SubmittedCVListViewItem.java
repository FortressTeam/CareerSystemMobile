package com.example.kyler.careersystem.HiringManager.customize;

import com.example.kyler.careersystem.Entities.PostsHasCurriculumVitaes;

/**
 * Created by kyler on 22/04/2016.
 */
public class SubmittedCVListViewItem {
    private int applicantID;
    private String applicantName;
    private int cvID;
    private String cvName;
    private PostsHasCurriculumVitaes postsHasCurriculumVitaes;

    public SubmittedCVListViewItem(int applicantID, String applicantName, int cvID, String cvName, PostsHasCurriculumVitaes postsHasCurriculumVitaes) {
        this.applicantID = applicantID;
        this.applicantName = applicantName;
        this.cvID = cvID;
        this.cvName = cvName;
        this.postsHasCurriculumVitaes = postsHasCurriculumVitaes;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public int getCvID() {
        return cvID;
    }

    public void setCvID(int cvID) {
        this.cvID = cvID;
    }

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public PostsHasCurriculumVitaes getPostsHasCurriculumVitaes() {
        return postsHasCurriculumVitaes;
    }

    public void setPostsHasCurriculumVitaes(PostsHasCurriculumVitaes postsHasCurriculumVitaes) {
        this.postsHasCurriculumVitaes = postsHasCurriculumVitaes;
    }
}
