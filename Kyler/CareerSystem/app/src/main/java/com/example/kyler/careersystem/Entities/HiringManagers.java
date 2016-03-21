package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 09/03/2016.
 */
public class HiringManagers {
    private int ID;
    private String hiringManagerName;
    private String hiringManagerPhone;
    private String companyName;
    private String companyAddress;
    private int companySize;
    private String companyAbout;
    private String companyLogo;

    public HiringManagers(int ID, String hiringManagerName, String hiringManagerPhone, String companyName, String companyAddress, int companySize, String companyAbout, String companyLogo) {
        this.ID = ID;
        this.hiringManagerName = hiringManagerName;
        this.hiringManagerPhone = hiringManagerPhone;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
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
