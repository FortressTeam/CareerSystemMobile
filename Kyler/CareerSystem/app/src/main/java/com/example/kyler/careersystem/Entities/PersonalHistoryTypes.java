package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class PersonalHistoryTypes {
    private int ID;
    private String personalHistoryTypeName;
    private String personalHistoryTypeDescription;

    public PersonalHistoryTypes(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("personal_history_type_name")) {
                this.personalHistoryTypeName = jsonObject.getString("personal_history_type_name");
            }
            if (jsonObject.has("personal_history_type_description")) {
                this.personalHistoryTypeDescription = jsonObject.getString("personal_history_type_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public PersonalHistoryTypes(int ID, String personalHistoryTypeName, String personalHistoryTypeDescription) {
        this.ID = ID;
        this.personalHistoryTypeName = personalHistoryTypeName;
        this.personalHistoryTypeDescription = personalHistoryTypeDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPersonalHistoryTypeName() {
        return personalHistoryTypeName;
    }

    public void setPersonalHistoryTypeName(String personalHistoryTypeName) {
        this.personalHistoryTypeName = personalHistoryTypeName;
    }

    public String getPersonalHistoryTypeDescription() {
        return personalHistoryTypeDescription;
    }

    public void setPersonalHistoryTypeDescription(String personalHistoryTypeDescription) {
        this.personalHistoryTypeDescription = personalHistoryTypeDescription;
    }
}
