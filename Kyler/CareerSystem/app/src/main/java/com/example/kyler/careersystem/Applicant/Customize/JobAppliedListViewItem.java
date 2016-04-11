package com.example.kyler.careersystem.Applicant.Customize;

/**
 * Created by kyler on 15/03/2016.
 */
public class JobAppliedListViewItem {
    private String companyImage;
    private String companyName;
    private String postTitle;
    private String jobOverview;
    private int status;
    private String companyAddress;
    private int salary;

    public JobAppliedListViewItem(String companyImage, String companyName, String postTitle, String jobOverview, int status, String companyAddress, int salary) {
        this.companyImage = companyImage;
        this.companyName = companyName;
        this.postTitle = postTitle;
        this.jobOverview = jobOverview;
        this.status = status;
        this.companyAddress = companyAddress;
        this.salary = salary;
    }

    public String getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(String companyImage) {
        this.companyImage = companyImage;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getJobOverview() {
        return jobOverview;
    }

    public void setJobOverview(String jobOverview) {
        this.jobOverview = jobOverview;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
