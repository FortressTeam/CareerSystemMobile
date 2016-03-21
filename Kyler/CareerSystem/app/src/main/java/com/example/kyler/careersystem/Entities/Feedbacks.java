package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Feedbacks {
    private int ID;
    private String feedbackTitle;
    private String feedbackCommend;
    private String feedbackDate;
    private String feedbackResult;
    private int feedbackTypeID;
    private int userID;

    public Feedbacks(int ID, String feedbackTitle, String feedbackCommend, String feedbackDate, String feedbackResult, int feedbackTypeID, int userID) {
        this.ID = ID;
        this.feedbackTitle = feedbackTitle;
        this.feedbackCommend = feedbackCommend;
        this.feedbackDate = feedbackDate;
        this.feedbackResult = feedbackResult;
        this.feedbackTypeID = feedbackTypeID;
        this.userID = userID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackCommend() {
        return feedbackCommend;
    }

    public void setFeedbackCommend(String feedbackCommend) {
        this.feedbackCommend = feedbackCommend;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getFeedbackResult() {
        return feedbackResult;
    }

    public void setFeedbackResult(String feedbackResult) {
        this.feedbackResult = feedbackResult;
    }

    public int getFeedbackTypeID() {
        return feedbackTypeID;
    }

    public void setFeedbackTypeID(int feedbackTypeID) {
        this.feedbackTypeID = feedbackTypeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
