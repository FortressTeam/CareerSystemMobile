package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class PersonalHistoryTypes {
    private int ID;
    private String personalHistoryTypeName;
    private String personalHistoryTypeDescription;

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
