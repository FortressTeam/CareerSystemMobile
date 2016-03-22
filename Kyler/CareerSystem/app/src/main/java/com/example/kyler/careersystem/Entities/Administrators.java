package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 14/03/2016.
 */
public class Administrators {
    private int ID;
    private String AdministratorName;
    private String AdministratorPhone;
    private String AdministratorDateOfBirth;
    private String AdministratorAddress;

    public Administrators(JSONObject jsonObject) {
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("administrator_name")) {
                this.AdministratorName = jsonObject.getString("administrator_name");
            }
            if (jsonObject.has("administrator_phone_number")) {
                this.AdministratorPhone = jsonObject.getString("administrator_phone_number");
            }
            if (jsonObject.has("administrator_date_of_birth")) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("administrator_date_of_birth"));
                this.AdministratorDateOfBirth = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if (jsonObject.has("administrator_address")) {
                this.AdministratorAddress = jsonObject.getString("administrator_address");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Administrators(int ID, String administratorName, String administratorPhone, String administratorDateOfBirth, String administratorAddress) {
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

    public String getAdministratorDateOfBirth() {
        return AdministratorDateOfBirth;
    }

    public void setAdministratorDateOfBirth(String administratorDateOfBirth) {
        AdministratorDateOfBirth = administratorDateOfBirth;
    }

    public String getAdministratorAddress() {
        return AdministratorAddress;
    }

    public void setAdministratorAddress(String administratorAddress) {
        AdministratorAddress = administratorAddress;
    }
}
