package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class Groups {
    private int ID;
    private String groupName;
    private String groupDescription;

    public Groups(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("group_name")) {
                this.groupName = jsonObject.getString("group_name");
            }
            if (jsonObject.has("group_description")) {
                this.groupDescription = jsonObject.getString("group_description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Groups(int ID, String groupName, String groupDescription) {
        this.ID = ID;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
}
