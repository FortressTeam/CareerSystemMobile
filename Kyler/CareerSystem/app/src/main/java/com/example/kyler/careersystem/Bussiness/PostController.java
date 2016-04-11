package com.example.kyler.careersystem.Bussiness;

import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Entities.ApplicantsHasSkills;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by kyler on 22/03/2016.
 */
public class PostController {
    public JobListViewItem getJobListView(Posts post, HiringManagers hiringManager, Categories category){
        JobListViewItem jobListViewItem=null;
        String title=post.getPostTitle();
        String titleTime= Utilities.getDays(post.getPostDate());
        String image= UrlStatic.URLimg+"company_img/"+hiringManager.getCompanyLogo();
        String salary = "";
        if(post.getPostSalary() == 0){
            salary = "Negotiable";
        }else{
            salary="VND "+post.getPostSalary();
        }
        String company=hiringManager.getCompanyName();
        String major=category.getCategoryName();
        String description=hiringManager.getCompanyAbout();
        jobListViewItem = new JobListViewItem(title,titleTime,image,salary,company,major,description);
        return jobListViewItem;
    }

    public ArrayList<Posts> getPosts(JSONArray jsonArray){
        ArrayList<Posts> result = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                result.add(new Posts(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }
}
