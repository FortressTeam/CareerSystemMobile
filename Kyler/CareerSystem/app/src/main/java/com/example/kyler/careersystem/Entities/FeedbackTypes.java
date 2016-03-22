package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class FeedbackTypes {
    private int ID;
    private String feedbackTypeName;

    public FeedbackTypes(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("feedback_type_name")) {
                this.feedbackTypeName = jsonObject.getString("feedback_type_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
