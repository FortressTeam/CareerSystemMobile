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
    private String postContent;
    private String location;
    private int status=-1;

    public JobListViewItem(String title, String titleTime, String image, String salary, String company, String major, String postContent, String location) {
        this.title = title;
        this.titleTime = titleTime;
        this.image = image;
        this.salary = salary;
        this.company = company;
        this.major = major;
        this.postContent = postContent;
        this.location = location;
    }

    public JobListViewItem(String title, String titleTime, String image, String salary, String company, String major, String postContent, String location, int status) {
        this.title = title;
        this.titleTime = titleTime;
        this.image = image;
        this.salary = salary;
        this.company = company;
        this.major = major;
        this.postContent = postContent;
        this.location = location;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}
