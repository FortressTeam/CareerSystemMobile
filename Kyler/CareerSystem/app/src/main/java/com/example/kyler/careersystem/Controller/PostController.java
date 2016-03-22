package com.example.kyler.careersystem.Controller;

import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;

/**
 * Created by kyler on 22/03/2016.
 */
public class PostController {
    public JobListViewItem getJobListView(Posts post, HiringManagers hiringManager, Categories category){
        JobListViewItem jobListViewItem=null;
        String title=post.getPostTitle();
        String titleTime= Utilities.getDays(post.getPostDate());
        String image= UrlStatic.URLimg+"company_img/"+hiringManager.getCompanyLogo();
        String salary="VND "+post.getPostSalary();
        String company=hiringManager.getCompanyName();
        String major=category.getCategoryName();
        String description=hiringManager.getCompanyAbout();
        jobListViewItem = new JobListViewItem(title,titleTime,image,salary,company,major,description);
        return jobListViewItem;
    }
}
