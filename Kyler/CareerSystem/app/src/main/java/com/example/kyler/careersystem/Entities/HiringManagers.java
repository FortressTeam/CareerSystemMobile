package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 09/03/2016.
 */
public class HiringManagers {
    private int ID;
    private String hiringManagerName;
    private String hiringManagerPhone;
//    private boolean hiringManagerStatus;
    private String companyName;
    private String companyAddress;
//    private String companyEmail;
    private int companySize;
    private String companyAbout;
    private String companyLogo;

    public HiringManagers(JSONObject jsonObject){
        try {
            if(jsonObject.has("id")){
                this.ID=jsonObject.getInt("id");
            }
            if(jsonObject.has("hiring_manager_name")){
                this.hiringManagerName=jsonObject.getString("hiring_manager_name");
            }
            if(jsonObject.has("hiring_manager_phone_number")){
                this.hiringManagerPhone=jsonObject.getString("hiring_manager_phone_number");
            }
//            if(jsonObject.has("hiring_manager_status")){
//                this.hiringManagerStatus=jsonObject.getBoolean("hiring_manager_status");
//            }
            if(jsonObject.has("company_name")){
                this.companyName=jsonObject.getString("company_name");
            }
            if(jsonObject.has("company_address")){
                this.companyAddress=jsonObject.getString("company_address");
            }
//            if(jsonObject.has("company_email")){
//                this.companyEmail=jsonObject.getString("company_email");
//            }
            if(jsonObject.has("company_size")){
                this.companySize=jsonObject.getInt("company_size");
            }
            if(jsonObject.has("company_about")){
                this.companyAbout=jsonObject.getString("company_about");
            }
            if(jsonObject.has("company_logo")){
                this.companyLogo=jsonObject.getString("company_logo");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HiringManagers(int ID, String hiringManagerName, String hiringManagerPhone, boolean hiringManagerStatus, String companyName, String companyAddress, String companyEmail, int companySize, String companyAbout, String companyLogo) {
        this.ID = ID;
        this.hiringManagerName = hiringManagerName;
        this.hiringManagerPhone = hiringManagerPhone;
//        this.hiringManagerStatus = hiringManagerStatus;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
//        this.companyEmail = companyEmail;
        this.companySize = companySize;
        this.companyAbout = companyAbout;
        this.companyLogo = companyLogo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHiringManagerName() {
        return hiringManagerName;
    }

    public void setHiringManagerName(String hiringManagerName) {
        this.hiringManagerName = hiringManagerName;
    }

    public String getHiringManagerPhone() {
        return hiringManagerPhone;
    }

    public void setHiringManagerPhone(String hiringManagerPhone) {
        this.hiringManagerPhone = hiringManagerPhone;
    }
//
//    public boolean isHiringManagerStatus() {
//        return hiringManagerStatus;
//    }
//
//    public void setHiringManagerStatus(boolean hiringManagerStatus) {
//        this.hiringManagerStatus = hiringManagerStatus;
//    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

//    public String getCompanyEmail() {
//        return companyEmail;
//    }
//
//    public void setCompanyEmail(String companyEmail) {
//        this.companyEmail = companyEmail;
//    }

    public int getCompanySize() {
        return companySize;
    }

    public void setCompanySize(int companySize) {
        this.companySize = companySize;
    }

    public String getCompanyAbout() {
        return companyAbout;
    }

    public void setCompanyAbout(String companyAbout) {
        this.companyAbout = companyAbout;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}
