package com.example.kyler.careersystem.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.Customize.SkillListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewItem;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.ApplicantsHasSkills;
import com.example.kyler.careersystem.Entities.Hobbies;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.Entities.SkillTypes;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.WorkWithService.GetJsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by kyler on 29/03/2016.
 */
public class MyresumeController {
    public Users getUser(JSONObject jsonObject){
        return new Users(jsonObject);
    }
    public Applicants getApplicant(JSONObject jsonObject){
        return new Applicants(jsonObject);
    }
    public PersonalHistory getPersonalHistory(JSONObject jsonObject){
        return new PersonalHistory(jsonObject);
    }
    public ArrayList<PersonalHistory> getPersonalHistories(JSONArray jsonArray){
        ArrayList<PersonalHistory> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new PersonalHistory(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<ApplicantsHasSkills> getApplicantsHasSkills(JSONArray jsonArray) {
        ArrayList<ApplicantsHasSkills> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new ApplicantsHasSkills(jsonArray.getJSONObject(i).getJSONObject("_joinData")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<Skills> getSkills(JSONArray jsonArray) {
        ArrayList<Skills> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new Skills(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<Hobbies> getHobbies(JSONArray jsonArray){
        ArrayList<Hobbies> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new Hobbies(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    public ArrayList<SkillTypes> getSkillTypes(JSONArray jsonArray){
        ArrayList<SkillTypes> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new SkillTypes(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

//    public void createDialogAddSkill(Dialog dialog, AddSkillObject addSkillObject, ListView myresume_listview_skill,  ArrayList<Skills> skills){
//        dialog.setContentView(R.layout.applicant_myresume_skill_customdialog);
//        addSkillObject.dialogAddSkillMajor = (Spinner) dialog.findViewById(R.id.dialog_myresume_skill_major_spinner);
//        addSkillObject.dialogAddSkillSearch = (EditText) dialog.findViewById(R.id.dialog_myresume_skill_search);
//        addSkillObject.dialogAddSkillSkills = (ListView) dialog.findViewById(R.id.dialog_myresume_skill_listview);
//        addSkillObject.dialogRatePlace = (LinearLayout) dialog.findViewById(R.id.dialog_myresume_skill_rateplace);
//        addSkillObject.dialogRateStar1 = (ImageView) dialog.findViewById(R.id.dialog_myresume_skill_ratestar1);
//        addSkillObject.dialogRateStar2 = (ImageView) dialog.findViewById(R.id.dialog_myresume_skill_ratestar2);
//        addSkillObject.dialogRateStar3 = (ImageView) dialog.findViewById(R.id.dialog_myresume_skill_ratestar3);
//        addSkillObject.dialogRateStar4 = (ImageView) dialog.findViewById(R.id.dialog_myresume_skill_ratestar4);
//        addSkillObject.dialogRateStar5 = (ImageView) dialog.findViewById(R.id.dialog_myresume_skill_ratestar5);
//        addSkillObject.dialogRateText = (TextView) dialog.findViewById(R.id.dialog_myresume_skill_rate_textview);
//        addSkillObject.dialogAddSkillCancel = (Button) dialog.findViewById(R.id.dialog_myresume_skill_cancel);
//
//
//    }

    public void ratingstar(int input, ArrayList<ImageView> arr, TextView rateText){
        for(int i=0;i<arr.size();i++){
            arr.get(i).setImageResource(R.drawable.ratestar);
        }
        for(int i=0;i<input;i++){
            arr.get(i).setImageResource(R.drawable.starfollow);
        }
        rateText.setVisibility(View.VISIBLE);
        switch (input){
            case 1:
                rateText.setText("Newbie");
                break;
            case 2:
                rateText.setText("Beginner");
                break;
            case 3:
                rateText.setText("Experience");
                break;
            case 4:
                rateText.setText("Senior");
                break;
            case 5:
                rateText.setText("Master");
                break;
        }
    }


//    public class AddSkillObject{
//        private Spinner dialogAddSkillMajor;
//        private EditText dialogAddSkillSearch;
//        private ListView dialogAddSkillSkills;
//        private LinearLayout dialogRatePlace;
//        private ImageView dialogRateStar1,dialogRateStar2,dialogRateStar3,dialogRateStar4,dialogRateStar5;
//        private TextView dialogRateText;
//        private Button dialogAddSkillCancel;
//
//        public AddSkillObject(Spinner dialogAddSkillMajor, EditText dialogAddSkillSearch, ListView dialogAddSkillSkills, LinearLayout dialogRatePlace, ImageView dialogRateStar1, ImageView dialogRateStar2, ImageView dialogRateStar3, ImageView dialogRateStar4, ImageView dialogRateStar5, TextView dialogRateText, Button dialogAddSkillCancel) {
//            this.dialogAddSkillMajor = dialogAddSkillMajor;
//            this.dialogAddSkillSearch = dialogAddSkillSearch;
//            this.dialogAddSkillSkills = dialogAddSkillSkills;
//            this.dialogRatePlace = dialogRatePlace;
//            this.dialogRateStar1 = dialogRateStar1;
//            this.dialogRateStar2 = dialogRateStar2;
//            this.dialogRateStar3 = dialogRateStar3;
//            this.dialogRateStar4 = dialogRateStar4;
//            this.dialogRateStar5 = dialogRateStar5;
//            this.dialogRateText = dialogRateText;
//            this.dialogAddSkillCancel = dialogAddSkillCancel;
//        }
//    }
}
