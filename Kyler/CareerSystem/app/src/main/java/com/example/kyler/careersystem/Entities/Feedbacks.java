package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Feedbacks {
    private int ID;
    private String feedbackTitle;
    private String feedbackComment;
    private String feedbackDate;
    private String feedbackResult;
    private int feedbackTypeID;
    private int userID;

    public Feedbacks(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("feedback_title")) {
                this.feedbackTitle = jsonObject.getString("feedback_title");
            }
            if (jsonObject.has("feedback_comment")) {
                this.feedbackComment = jsonObject.getString("feedback_comment");
            }
            if (jsonObject.has("feedback_date")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("feedback_date"));
                this.feedbackDate = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("feedback_result")) {
                this.feedbackResult = jsonObject.getString("feedback_result");
            }
            if (jsonObject.has("feedback_type_id")) {
                this.feedbackTypeID = jsonObject.getInt("feedback_type_id");
            }
            if (jsonObject.has("user_id")) {
                this.userID = jsonObject.getInt("user_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Feedbacks(int ID, String feedbackTitle, String feedbackComment, String feedbackDate, String feedbackResult, int feedbackTypeID, int userID) {
        this.ID = ID;
        this.feedbackTitle = feedbackTitle;
        this.feedbackComment = feedbackComment;
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

    public String getFeedbackComment() {
        return feedbackComment;
    }

    public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment;
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
