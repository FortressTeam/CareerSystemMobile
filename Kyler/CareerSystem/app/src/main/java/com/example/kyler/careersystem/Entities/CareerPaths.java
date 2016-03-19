package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 14/03/2016.
 */
public class CareerPaths {
    private int ID;
    private String careerPathName;
    private String careerPathDescription;

    public CareerPaths(int ID, String careerPathName, String careerPathDescription) {
        this.ID = ID;
        this.careerPathName = careerPathName;
        this.careerPathDescription = careerPathDescription;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCareerPathName() {
        return careerPathName;
    }

    public void setCareerPathName(String careerPathName) {
        this.careerPathName = careerPathName;
    }

    public String getCareerPathDescription() {
        return careerPathDescription;
    }

    public void setCareerPathDescription(String careerPathDescription) {
        this.careerPathDescription = careerPathDescription;
    }
}
