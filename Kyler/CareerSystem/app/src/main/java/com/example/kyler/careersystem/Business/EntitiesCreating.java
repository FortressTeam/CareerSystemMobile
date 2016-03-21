package com.example.kyler.careersystem.Business;

import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kyler on 21/03/2016.
 */
public class EntitiesCreating {
    public static Posts createPost(JSONObject jsonObject){
        int ID = 0;
        String postTitle = null;
        String postContent = null;
        int postSalary = 0;
        String postLocation = null;
        String postDate = null;
        int postStatus = 0;
        int categoryID = 0;
        int hiringManagerID = 0;
        try {
            if(jsonObject.has("id")){
                ID=jsonObject.getInt("id");
            }
            if(jsonObject.has("post_title")){
                postTitle=jsonObject.getString("post_title");
            }
            if(jsonObject.has("post_content")){
                postContent=jsonObject.getString("post_content");
            }
            if(jsonObject.has("post_salary")){
                postSalary=jsonObject.getInt("post_salary");
            }
            if(jsonObject.has("post_location")){
                postLocation=jsonObject.getString("post_location");
            }
            if(jsonObject.has("post_date")){
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("post_date"));
                postDate=new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if(jsonObject.has("post_content")){
                postStatus=jsonObject.getInt("post_status");
            }
            if(jsonObject.has("post_content")){
                categoryID=jsonObject.getInt("category_id");
            }
            if(jsonObject.has("post_content")){
                hiringManagerID=jsonObject.getInt("hiring_manager_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Posts(ID, postTitle, postContent, postSalary, postLocation, postDate, postStatus, categoryID, hiringManagerID);
    }

    public static HiringManagers createHiringManagers(JSONObject jsonObject){
        int ID = 0;
        String hiringManagerName = null;
        String hiringManagerPhone = null;
        String companyName = null;
        String companyAddress = null;
        int companySize = 0;
        String companyAbout = null;
        String companyLogo = null;
        try {
            if(jsonObject.has("id")){
                ID=jsonObject.getInt("id");
            }
            if(jsonObject.has("hiring_manager_name")){
                hiringManagerName=jsonObject.getString("hiring_manager_name");
            }
            if(jsonObject.has("hiring_manager_phone_number")){
                hiringManagerPhone=jsonObject.getString("hiring_manager_phone_number");
            }
            if(jsonObject.has("company_name")){
                companyName=jsonObject.getString("company_name");
            }
            if(jsonObject.has("company_address")){
                companyAddress=jsonObject.getString("company_address");
            }
            if(jsonObject.has("company_size")){
                companySize=jsonObject.getInt("company_size");
            }
            if(jsonObject.has("company_about")){
                companyAbout=jsonObject.getString("company_about");
            }
            if(jsonObject.has("company_logo")){
                companyLogo=jsonObject.getString("company_logo");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new HiringManagers(ID,hiringManagerName,hiringManagerPhone,companyName,companyAddress,companySize,companyAbout,companyLogo);
    }

    public static Categories createCategories(JSONObject jsonObject){
        int ID = 0;
        String categoryName = null;
        String categoryDescription = null;
        int parentID = 0;
        int left = 0;
        int right = 0;
        try{
            if(jsonObject.has("id")){
                ID=jsonObject.getInt("id");
            }
            if(jsonObject.has("category_name")){
                categoryName=jsonObject.getString("category_name");
            }
            if(jsonObject.has("category_description")){
                categoryDescription=jsonObject.getString("category_description");
            }
            if(jsonObject.has("parent_id")){
                parentID=jsonObject.getInt("parent_id");
            }
            if(jsonObject.has("left")){
                left=jsonObject.getInt("left");
            }
            if(jsonObject.has("right")){
                right=jsonObject.getInt("right");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Categories(ID,categoryName,categoryDescription,parentID,left,right);
    }

    public static Applicants createApplicants(JSONObject jsonObject){
        int ID = 0;
        String applicantName = null;
        String applicantPhone = null;
        String applicantDateOfBirth = null;
        String applicantPlaceOfBirth = null;
        String applicantAddress = null;
        String applicantCountry = null;
        String applicantAbout = null;
        int applicantMaritalStatus = 0;
        String applicantFutureGoal = null;
        String applicantWebsite = null;
        int applicantStatus = 0;
        int careerPathID = 0;
        try{
            if(jsonObject.has("id")){
                ID = jsonObject.getInt("id");
            }
            if(jsonObject.has("applicant_name")){
                applicantName = jsonObject.getString("applicant_name");
            }
            if(jsonObject.has("applicant_phone_number")){
                applicantPhone = jsonObject.getString("applicant_phone_number");
            }
            if(jsonObject.has("applicant_date_of_birth")){
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("applicant_date_of_birth"));
                applicantDateOfBirth = new SimpleDateFormat("d - LLL - yyyy").format(date);
            }
            if(jsonObject.has("applicant_place_of_birth")){
                applicantPlaceOfBirth = jsonObject.getString("applicant_place_of_birth");
            }
            if(jsonObject.has("applicant_address")){
                applicantAddress = jsonObject.getString("applicant_address");
            }
            if(jsonObject.has("applicant_country")){
                applicantCountry = jsonObject.getString("applicant_country");
            }
            if(jsonObject.has("applicant_about")){
                applicantAbout = jsonObject.getString("applicant_about");
            }
            if(jsonObject.has("applicant_marital_status")){
                applicantMaritalStatus = jsonObject.getInt("applicant_marital_statis");
            }
            if(jsonObject.has("applicant_future_goals")){
                applicantFutureGoal = jsonObject.getString("applicant_future_goals");
            }
            if(jsonObject.has("applicant_website")){
                applicantWebsite = jsonObject.getString("applicant_website");
            }
            if(jsonObject.has("applicant_status")){
                applicantStatus = jsonObject.getInt("applicant_status");
            }
            if(jsonObject.has("applicant_path_id")){
                careerPathID = jsonObject.getInt("applicant_path_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Applicants(ID,applicantName,applicantPhone,applicantDateOfBirth,applicantPlaceOfBirth,applicantAddress,applicantCountry,applicantAbout,applicantMaritalStatus,applicantFutureGoal,applicantWebsite,applicantStatus,careerPathID);
    }


}
