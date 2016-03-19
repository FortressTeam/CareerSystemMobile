package com.example.kyler.careersystem.Entities;

import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Administrators {
    private int ID;
    private String AdministratorName;
    private String AdministratorPhone;
    private Date AdministratorDateOfBirth;
    private String AdministratorAddress;

    public Administrators(int ID, String administratorName, String administratorPhone, Date administratorDateOfBirth, String administratorAddress) {
        this.ID = ID;
        AdministratorName = administratorName;
        AdministratorPhone = administratorPhone;
        AdministratorDateOfBirth = administratorDateOfBirth;
        AdministratorAddress = administratorAddress;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAdministratorName() {
        return AdministratorName;
    }

    public void setAdministratorName(String administratorName) {
        AdministratorName = administratorName;
    }

    public String getAdministratorPhone() {
        return AdministratorPhone;
    }

    public void setAdministratorPhone(String administratorPhone) {
        AdministratorPhone = administratorPhone;
    }

    public Date getAdministratorDateOfBirth() {
        return AdministratorDateOfBirth;
    }

    public void setAdministratorDateOfBirth(Date administratorDateOfBirth) {
        AdministratorDateOfBirth = administratorDateOfBirth;
    }

    public String getAdministratorAddress() {
        return AdministratorAddress;
    }

    public void setAdministratorAddress(String administratorAddress) {
        AdministratorAddress = administratorAddress;
    }
}
