package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class PersonalHistory {
    private int ID;
    private String personalHistoryTitle;
    private String personalHistoryDetail;
    private String personalHistoryStart;
    private String personalHistoryEnd;
    private int personalHistoryTypeID;
    private int applicantID;

    public PersonalHistory(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("personal_history_title")) {
                this.personalHistoryTitle = jsonObject.getString("personal_history_title");
            }
            if (jsonObject.has("personal_history_detail")) {
                this.personalHistoryDetail = jsonObject.getString("personal_history_detail");
            }
            if (jsonObject.has("personal_history_start")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("personal_history_start"));
                this.personalHistoryStart = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("personal_history_end")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("personal_history_end"));
                this.personalHistoryEnd = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("personal_history_type_id")) {
                this.personalHistoryTypeID = jsonObject.getInt("personal_history_type_id");
            }
            if (jsonObject.has("applicant_id")) {
                this.applicantID = jsonObject.getInt("applicant_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public PersonalHistory(int ID, String personalHistoryTitle, String personalHistoryDetail, String personalHistoryStart, String personalHistoryEnd, int personalHistoryTypeID, int applicantID) {
        this.ID = ID;
        this.personalHistoryTitle = personalHistoryTitle;
        this.personalHistoryDetail = personalHistoryDetail;
        this.personalHistoryStart = personalHistoryStart;
        this.personalHistoryEnd = personalHistoryEnd;
        this.personalHistoryTypeID = personalHistoryTypeID;
        this.applicantID = applicantID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPersonalHistoryTitle() {
        return personalHistoryTitle;
    }

    public void setPersonalHistoryTitle(String personalHistoryTitle) {
        this.personalHistoryTitle = personalHistoryTitle;
    }

    public String getPersonalHistoryDetail() {
        return personalHistoryDetail;
    }

    public void setPersonalHistoryDetail(String personalHistoryDetail) {
        this.personalHistoryDetail = personalHistoryDetail;
    }

    public String getPersonalHistoryStart() {
        return personalHistoryStart;
    }

    public void setPersonalHistoryStart(String personalHistoryStart) {
        this.personalHistoryStart = personalHistoryStart;
    }

    public String getPersonalHistoryEnd() {
        return personalHistoryEnd;
    }

    public void setPersonalHistoryEnd(String personalHistoryEnd) {
        this.personalHistoryEnd = personalHistoryEnd;
    }

    public int getPersonalHistoryTypeID() {
        return personalHistoryTypeID;
    }

    public void setPersonalHistoryTypeID(int personalHistoryTypeID) {
        this.personalHistoryTypeID = personalHistoryTypeID;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }
}
