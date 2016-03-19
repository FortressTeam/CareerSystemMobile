package com.example.kyler.careersystem.Applicant;

/**
 * Created by kyler on 09/03/2016.
 */
public class NavigationListViewItem {
    private int image;
    private String title;
    private int counter=0;
    private boolean isCounterVisible=false;

    public NavigationListViewItem(int image, String title, int counter, boolean isCounterVisible) {
        this.image = image;
        this.title = title;
        this.counter = counter;
        this.isCounterVisible = isCounterVisible;
    }

    public NavigationListViewItem(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public void setIsCounterVisible(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }
}
