package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class Hobbies {
    private int ID;
    private String hobbieName;
    private String hobbieDescription;

    public Hobbies(int ID, String hobbieName, String hobbieDescription) {
        this.ID = ID;
        this.hobbieName = hobbieName;
        this.hobbieDescription = hobbieDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getHobbieName() {
        return hobbieName;
    }

    public void setHobbieName(String hobbieName) {
        this.hobbieName = hobbieName;
    }

    public String getHobbieDescription() {
        return hobbieDescription;
    }

    public void setHobbieDescription(String hobbieDescription) {
        this.hobbieDescription = hobbieDescription;
    }
}
