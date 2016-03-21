package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Applicants {
    private int ID;
    private String applicantName;
    private String applicantPhone;
    private String applicantDateOfBirth;
    private String applicantPlaceOfBirth;
    private String applicantAddress;
    private String applicantCountry;
    private String applicantAbout;
    private int applicantMaritalStatus;
    private String applicantFutureGoal;
    private String applicantWebsite;
    private int applicantStatus;
    private int careerPathID;

    public Applicants(int ID, String applicantName, String applicantPhone, String applicantDateOfBirth, String applicantPlaceOfBirth, String applicantAddress, String applicantCountry, String applicantAbout, int applicantMaritalStatus, String applicantFutureGoal, String applicantWebsite, int applicantStatus, int careerPathID) {
        this.ID = ID;
        this.applicantName = applicantName;
        this.applicantPhone = applicantPhone;
        this.applicantDateOfBirth = applicantDateOfBirth;
        this.applicantPlaceOfBirth = applicantPlaceOfBirth;
        this.applicantAddress = applicantAddress;
        this.applicantCountry = applicantCountry;
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

    public String getApplicantPlaceOfBirth() {
        return applicantPlaceOfBirth;
    }

    public void setApplicantPlaceOfBirth(String applicantPlaceOfBirth) {
        this.applicantPlaceOfBirth = applicantPlaceOfBirth;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public String getApplicantCountry() {
        return applicantCountry;
    }

    public void setApplicantCountry(String applicantCountry) {
        this.applicantCountry = applicantCountry;
    }

    public String getApplicantAbout() {
        return applicantAbout;
    }

    public void setApplicantAbout(String applicantAbout) {
        this.applicantAbout = applicantAbout;
    }

    public int getApplicantMaritalStatus() {
        return applicantMaritalStatus;
    }

    public void setApplicantMaritalStatus(int applicantMaritalStatus) {
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

    public int getApplicantStatus() {
        return applicantStatus;
    }

    public void setApplicantStatus(int applicantStatus) {
        this.applicantStatus = applicantStatus;
    }

    public int getCareerPathID() {
        return careerPathID;
    }

    public void setCareerPathID(int careerPathID) {
        this.careerPathID = careerPathID;
    }
}
