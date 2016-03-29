package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Applicants {
    private int ID;
    private String applicantName;
    private String applicantPhone;
    private String applicantDateOfBirth;
    private boolean applicantSex;
    private String applicantAddress;
    private String applicantAbout;
    private boolean applicantMaritalStatus;
    private String applicantFutureGoal;
    private String applicantWebsite;
    private boolean applicantStatus;
    private int careerPathID;

    public Applicants(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("applicant_name")) {
                this.applicantName = jsonObject.getString("applicant_name");
            }
            if (jsonObject.has("applicant_phone_number")) {
                this.applicantPhone = jsonObject.getString("applicant_phone_number");
            }
            if (jsonObject.has("applicant_date_of_birth")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("applicant_date_of_birth"));
                this.applicantDateOfBirth = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("applicant_sex")) {
                this.applicantSex = jsonObject.getBoolean("applicant_sex");
            }
            if (jsonObject.has("applicant_address")) {
                this.applicantAddress = jsonObject.getString("applicant_address");
            }
            if (jsonObject.has("applicant_about")) {
                this.applicantAbout = jsonObject.getString("applicant_about");
            }
            if (jsonObject.has("applicant_marital_status")) {
                this.applicantMaritalStatus = jsonObject.getBoolean("applicant_marital_status");
            }
            if (jsonObject.has("applicant_future_goals")) {
                this.applicantFutureGoal = jsonObject.getString("applicant_future_goals");
            }
            if (jsonObject.has("applicant_website")) {
                this.applicantWebsite = jsonObject.getString("applicant_website");
            }
            if (jsonObject.has("applicant_status")) {
                this.applicantStatus = jsonObject.getBoolean("applicant_status");
            }
            if (jsonObject.has("career_path_id")) {
                this.careerPathID = jsonObject.getInt("career_path_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Applicants(int ID, String applicantName, String applicantPhone, String applicantDateOfBirth, boolean applicantSex, String applicantAddress, String applicantAbout, boolean applicantMaritalStatus, String applicantFutureGoal, String applicantWebsite, boolean applicantStatus, int careerPathID) {
        this.ID = ID;
        this.applicantName = applicantName;
        this.applicantPhone = applicantPhone;
        this.applicantDateOfBirth = applicantDateOfBirth;
        this.applicantSex = applicantSex;
        this.applicantAddress = applicantAddress;
        this.applicantAbout = applicantAbout;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.applicantFutureGoal = applicantFutureGoal;
        this.applicantWebsite = applicantWebsite;
        this.applicantStatus = applicantStatus;
        this.careerPathID = careerPathID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public String getApplicantDateOfBirth() {
        return applicantDateOfBirth;
    }

    public void setApplicantDateOfBirth(String applicantDateOfBirth) {
        this.applicantDateOfBirth = applicantDateOfBirth;
    }

    public boolean isApplicantSex() {
        return applicantSex;
    }

    public void setApplicantSex(boolean applicantSex) {
        this.applicantSex = applicantSex;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public String getApplicantAbout() {
        return applicantAbout;
    }

    public void setApplicantAbout(String applicantAbout) {
        this.applicantAbout = applicantAbout;
    }

    public boolean isApplicantMaritalStatus() {
        return applicantMaritalStatus;
    }

    public void setApplicantMaritalStatus(boolean applicantMaritalStatus) {
        this.applicantMaritalStatus = applicantMaritalStatus;
    }

    public String getApplicantFutureGoal() {
        return applicantFutureGoal;
    }

    public void setApplicantFutureGoal(String applicantFutureGoal) {
        this.applicantFutureGoal = applicantFutureGoal;
    }

    public String getApplicantWebsite() {
        return applicantWebsite;
    }

    public void setApplicantWebsite(String applicantWebsite) {
        this.applicantWebsite = applicantWebsite;
    }

    public boolean isApplicantStatus() {
        return applicantStatus;
    }

    public void setApplicantStatus(boolean applicantStatus) {
        this.applicantStatus = applicantStatus;
    }

    public int getCareerPathID() {
        return careerPathID;
    }

    public void setCareerPathID(int careerPathID) {
        this.careerPathID = careerPathID;
    }
}
