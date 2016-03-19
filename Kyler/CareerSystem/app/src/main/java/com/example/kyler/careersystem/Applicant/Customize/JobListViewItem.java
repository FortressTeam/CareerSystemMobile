package com.example.kyler.careersystem.Applicant.Customize;

/**
 * Created by kyler on 04/03/2016.
 */
public class JobListViewItem {
    private String title;
    private String titleTime;
    private String image;
    private String salary;
    private String company;
    private String major;
    private String description;

    public JobListViewItem(String title, String titleTime, String image, String salary, String company, String major, String description) {
        this.title = title;
        this.titleTime = titleTime;
        this.image = image;
        this.salary = salary;
        this.company = company;
        this.major = major;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleTime() {
        return titleTime;
    }

    public void setTitleTime(String titleTime) {
        this.titleTime = titleTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
