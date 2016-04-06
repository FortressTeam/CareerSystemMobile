package com.example.kyler.careersystem;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ListView;

import com.example.kyler.careersystem.Applicant.Customize.JobAppliedListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Applicant.FindFragment;
import com.example.kyler.careersystem.Applicant.HomeFragment;
import com.example.kyler.careersystem.Applicant.JobappliedFragment;
import com.example.kyler.careersystem.Applicant.MyResumeFragment;
import com.example.kyler.careersystem.Applicant.NavigationListViewAdapter;
import com.example.kyler.careersystem.Applicant.NavigationListViewItem;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.HiringManager.AddPostFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Duck_Ky on 3/5/2016.
 */
public class Utilities {
    public static void loadNavigationView(Context context,ListView lv){
        ArrayList<NavigationListViewItem> listItem = new ArrayList<>();
        listItem.add(new NavigationListViewItem(R.drawable.navhomeicon,"Home"));
        listItem.add(new NavigationListViewItem(R.drawable.navmyresumeicon,"My Resume"));
        listItem.add(new NavigationListViewItem(R.drawable.navfindicon,"Find"));
        listItem.add(new NavigationListViewItem(R.drawable.navfavoriteicon,"Favorite"));
        listItem.add(new NavigationListViewItem(R.drawable.navjobappliedicon,"Job Applied"));
        listItem.add(new NavigationListViewItem(R.drawable.navnotificationicon,"Notifications",15,true));
        NavigationListViewAdapter adapter = new NavigationListViewAdapter(context.getApplicationContext(),listItem);
        lv.setAdapter(adapter);
    }

    public static JobAppliedListViewItem getJobAppliedLVItemfrom(JSONObject jsonObject){
        JobAppliedListViewItem jobAppliedListViewItem=null;
        String companyImage = null;
        String companyName = null;
        String postTitle = null;
        String postContent =null;
        String companyAddress = null;
        int salary = 0;
        int status = 0;
        try{
            if(jsonObject.has("company_image"))
                companyImage = jsonObject.getString("company_image");
            if(jsonObject.has("company_name"))
                companyName = jsonObject.getString("company_name");
            if(jsonObject.has("post_title"))
                postTitle = jsonObject.getString("post_title");
            if(jsonObject.has("post_content"))
                postContent = jsonObject.getString("post_content");
            if(jsonObject.has("company_address"))
                companyAddress = jsonObject.getString("company_address");
            if(jsonObject.has("salary"))
                salary = jsonObject.getInt("salary");
            if(jsonObject.has("status"))
                status = jsonObject.getInt("status");
            jobAppliedListViewItem = new JobAppliedListViewItem(companyImage,companyName,postTitle,postContent,status,companyAddress,salary);
        }catch (JSONException e){e.printStackTrace();}
        return jobAppliedListViewItem;
    }

    public static JobListViewItem getJobLVItemfrom(JSONObject jsonObject){
        JobListViewItem jobListViewItem=null;
        String title=null;
        String titleTime=null;
        String image=null;
        String salary=null;
        String company=null;
        String major=null;
        String description=null;
        try {
            if(jsonObject.has("post_title"))
                title=jsonObject.getString("post_title");
            if(jsonObject.has("post_title_time"))
                titleTime=jsonObject.getString("post_title_time");
            if(jsonObject.has("post_image"))
                image=jsonObject.getString("post_image");
            if(jsonObject.has("post_salary"))
                salary=jsonObject.getString("post_salary");
            if(jsonObject.has("company_name"))
                company=jsonObject.getString("company_name");
            if(jsonObject.has("category_name"))
                major=jsonObject.getString("category_name");
            if(jsonObject.has("post_content"))
                description=jsonObject.getString("post_content");
            jobListViewItem = new JobListViewItem(title,titleTime,image,salary,company,major,description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jobListViewItem;
    }

    public static String getDays(String datetime){
        if(datetime==null)
            return "";
        long days=1;
        try {
            Date date = new SimpleDateFormat("dd - LLL - yyyy").parse(datetime);
            Date current = new SimpleDateFormat("dd - LLL - yyyy").parse(new SimpleDateFormat("dd - LLL - yyyy").format(Calendar.getInstance().getTime()));
            long diff = current.getTime() - date.getTime();
            days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(days>0) {
            if (days == 1)
                return "yesterday";
            else
                return days + " days ago";
        }
        else {
            if(days ==0)
                return "today";
            else
                return "invalid";
        }
    }

    public static boolean validDatepicker(String dp1,String dp2){
        boolean result = false;
        long days = 0;
        try{
            Date date1 = new SimpleDateFormat("dd - LLL - yyyy").parse(dp1);
            Date date2 = new SimpleDateFormat("dd - LLL - yyyy").parse(dp2);
            long diff = date2.getTime() - date1.getTime();
            days = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(days>0)
            result = true;
        return result;
    }

    public static JobListViewItem getJobLVItemfrom(Posts post,HiringManagers hiringManager,Categories category){
        JobListViewItem jobListViewItem=null;
        String title=post.getPostTitle();
        String titleTime=getDays(post.getPostDate());
        String image= UrlStatic.URLimg+"company_img/"+hiringManager.getCompanyLogo();
        String salary="VND "+post.getPostSalary();
        String company=hiringManager.getCompanyName();
        String major=category.getCategoryName();
        String description=hiringManager.getCompanyAbout();
        jobListViewItem = new JobListViewItem(title,titleTime,image,salary,company,major,description);
        return jobListViewItem;
    }

    public static void displayView(Activity context,int position){
        Fragment fragment = null;
        switch (position){
            case 1://home
                fragment = new HomeFragment();
                break;
            case 2://myresume
                fragment = new MyResumeFragment();
                break;
            case 3://find
                fragment = new FindFragment();
                break;
            case 4://favorite
//                fragment = new HomeFragment();
                fragment = new AddPostFragment();
                break;
            case 5://jobapplied
                fragment = new JobappliedFragment();
                break;
            case 6://notifications
                break;
            default:
                fragment = new FailedConnectionFragment();
                break;
        }
        if(fragment != null){
            FragmentManager fragmentManager = context.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_main,fragment).commit();
        } else{
            Log.e("Applicant MainActivity", "Error in creating fragment");
        }
        DrawerLayout drawer = (DrawerLayout) context.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public static void startFragWith(Activity activitySend,Class activityReceive,String key,String sendData){ // sendData could be an Object
        Intent intent = new Intent(activitySend,activityReceive);
        String senddt=sendData; // could be changed by an Object
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        bundle.putString("sendData",senddt);
        intent.putExtra("sendBundle",bundle);
        activitySend.startActivity(intent);
    }

    public static String convertTime(String date){
        String result = "";
        try {
            Date resultDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            result = new SimpleDateFormat("dd - LLL - yyyy").format(resultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isCreateUpdateSuccess(JSONObject input){
        boolean result = false;
        try {
            if(input.getString("message").equals("Saved"))
                result = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void startActivity(Activity fromActivity, Class endActivity, int id){
        Intent intent = new Intent(fromActivity, endActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt("itemID",2);
        intent.putExtra("back", bundle);
        fromActivity.startActivity(intent);
        fromActivity.finish();
    }

    public static boolean checkConnect(JSONObject jsonObject){
        if(jsonObject!=null)
            return true;
        else
            return false;
    }

    public static boolean checkConnect(JSONArray jsonArray){
        if(jsonArray!=null)
            return true;
        else
            return false;
    }

}
