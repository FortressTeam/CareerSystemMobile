package com.example.kyler.careersystem.Applicant.Customize;

/**
 * Created by kyler on 10/03/2016.
 */
public class ExperienceListViewItem {
    private String company;
    private String position;
    private String start;
    private String end;
    private String description;

    public ExperienceListViewItem(String company, String position, String start, String end, String description) {
        this.company = company;
        this.position = position;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
