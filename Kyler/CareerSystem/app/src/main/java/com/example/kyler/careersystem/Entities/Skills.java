package com.example.kyler.careersystem.Entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/03/2016.
 */
public class Skills {
    private int ID;
    private String skillName;
    private int skillTypeID;

    public Skills(JSONObject jsonObject){
        try {
            if (jsonObject.has("id")) {
                this.ID = jsonObject.getInt("id");
            }
            if (jsonObject.has("skill_name")) {
                this.skillName = jsonObject.getString("skill_name");
            }
            if (jsonObject.has("skill_type_id")) {
                this.skillTypeID = jsonObject.getInt("skill_type_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Skills(int ID, String skillName, int skillTypeID) {
        this.ID = ID;
        this.skillName = skillName;
        this.skillTypeID = skillTypeID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillTypeID() {
        return skillTypeID;
    }

    public void setSkillTypeID(int skillTypeID) {
        this.skillTypeID = skillTypeID;
    }
}
