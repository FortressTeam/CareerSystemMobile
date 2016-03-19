package com.example.kyler.careersystem.Entities;

/**
 * Created by kyler on 09/03/2016.
 */
public class Categories {
    private int ID;
    private String categoryName;
    private String categoryDescription;
    private int parentID;
    private int left;
    private int right;

    public Categories(int ID, String categoryName, String categoryDescription, int parentID, int left, int right) {
        this.ID = ID;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parentID = parentID;
        this.left = left;
        this.right = right;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
