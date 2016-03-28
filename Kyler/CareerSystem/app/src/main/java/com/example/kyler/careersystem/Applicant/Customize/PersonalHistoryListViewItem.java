package com.example.kyler.careersystem.Applicant.Customize;

/**
 * Created by kyler on 28/03/2016.
 */
public class PersonalHistoryListViewItem {
    private String historyTitle;
    private String historyDetail;
    private String historyStart;
    private String historyEnd;

    public PersonalHistoryListViewItem(String historyTitle, String historyDetail, String historyStart, String historyEnd) {
        this.historyTitle = historyTitle;
        this.historyDetail = historyDetail;
        this.historyStart = historyStart;
        this.historyEnd = historyEnd;
    }

    public String getHistoryTitle() {
        return historyTitle;
    }

    public void setHistoryTitle(String historyTitle) {
        this.historyTitle = historyTitle;
    }

    public String getHistoryDetail() {
        return historyDetail;
    }

    public void setHistoryDetail(String historyDetail) {
        this.historyDetail = historyDetail;
    }

    public String getHistoryStart() {
        return historyStart;
    }

    public void setHistoryStart(String historyStart) {
        this.historyStart = historyStart;
    }

    public String getHistoryEnd() {
        return historyEnd;
    }

    public void setHistoryEnd(String historyEnd) {
        this.historyEnd = historyEnd;
    }
}
