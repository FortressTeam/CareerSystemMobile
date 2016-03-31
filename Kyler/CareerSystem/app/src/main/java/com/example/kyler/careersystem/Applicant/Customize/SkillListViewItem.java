package com.example.kyler.careersystem.Applicant.Customize;

/**
 * Created by kyler on 31/03/2016.
 */
public class SkillListViewItem {
    private String skillName;
    private int skillLevel;

    public SkillListViewItem(String skillName, int skillLevel) {
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }
}
