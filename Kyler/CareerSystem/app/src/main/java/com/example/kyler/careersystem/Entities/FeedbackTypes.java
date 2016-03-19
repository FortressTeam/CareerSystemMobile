package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class FeedbackTypes {
    private int ID;
    private String feedbackTypeName;

    public FeedbackTypes(int ID, String feedbackTypeName) {
        this.ID = ID;
        this.feedbackTypeName = feedbackTypeName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFeedbackTypeName() {
        return feedbackTypeName;
    }

    public void setFeedbackTypeName(String feedbackTypeName) {
        this.feedbackTypeName = feedbackTypeName;
    }
}
