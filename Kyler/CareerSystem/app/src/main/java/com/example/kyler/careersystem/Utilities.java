package com.example.kyler.careersystem;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.Customize.JobAppliedListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Applicant.FindFragment;
import com.example.kyler.careersystem.Applicant.HomeFragment;
import com.example.kyler.careersystem.Applicant.JobappliedFragment;
import com.example.kyler.careersystem.Applicant.MyResumeFragment;
import com.example.kyler.careersystem.Applicant.NavigationListViewAdapter;
import com.example.kyler.careersystem.Applicant.NavigationListViewItem;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.HiringManager.ChildFragments.AddPostFragment;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Duck_Ky on 3/5/2016.
 */
public class Utilities {
    public static Users users=null;
    public static Applicants applicants=null;
    public static HiringManagers hiringManagers=null;

    public static String SAVEING_FILE_LOGIN = "account";

    public static void clear(){
        users = null;
        applicants = null;
        hiringManagers = null;
    }


    public static void hideSoftKeyboard(final Activity activity, View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                hideSoftKeyboard(activity, innerView);
            }
        }
    }

    public static void loadNavigationViewApplicant(Context context,ListView lv){
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

    public static void displayViewApplicant(Activity context, int position){
        Fragment fragment = null;
        switch (position){
            case 0://home
                fragment = new MyResumeFragment();
                break;
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

    public static void loadNavigationViewHiringManager(Context context,ListView lv){
        ArrayList<NavigationListViewItem> listItem = new ArrayList<>();
        listItem.add(new NavigationListViewItem(R.drawable.hiringmanagerpost,"Posts"));
        listItem.add(new NavigationListViewItem(R.drawable.hiringmanagercvsubmitted,"Resume Submitted"));
        listItem.add(new NavigationListViewItem(R.drawable.hiringmanagerfind,"Find"));
        listItem.add(new NavigationListViewItem(R.drawable.hiringmanagercalendar,"Calendar"));
        listItem.add(new NavigationListViewItem(R.drawable.navjobappliedicon,"Job Applied"));
        listItem.add(new NavigationListViewItem(R.drawable.navnotificationicon,"Notifications",15,true));
        NavigationListViewAdapter adapter = new NavigationListViewAdapter(context.getApplicationContext(),listItem);
        lv.setAdapter(adapter);
    }

    public static void displayViewHiringManager(Activity context, int position){
        Fragment fragment = null;
        switch (position){
            case 0://home
                fragment = new com.example.kyler.careersystem.HiringManager.HomeFragment();
                break;
            case 1://home
                fragment = new com.example.kyler.careersystem.HiringManager.ManagePost();
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
            if (days == 1) {
                return "yesterday";
            }
            else {
                if(days > 30){
                    return datetime;
                }else {
                    return days + " days ago";
                }
            }
        }
        else {
            if(days ==0) {
                return "today";
            }
            else {
                return "invalid";
            }
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


    public static void startFragWith(Activity activitySend,Class activityReceive,String key,String sendData){ // sendData could be an Object
        Intent intent = new Intent(activitySend,activityReceive);
        String senddt=sendData; // could be changed by an Object
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        bundle.putString("sendData",senddt);
        intent.putExtra("sendBundle",bundle);
        activitySend.startActivity(intent);
    }

    public static boolean saveLogin(Activity activity,String data){
        boolean result = false;
        try {
            FileOutputStream fOut = activity.openFileOutput(Utilities.SAVEING_FILE_LOGIN, activity.MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkLogin(Activity activity){
        boolean result = false;
        try {
            FileInputStream fin = activity.openFileInput(Utilities.SAVEING_FILE_LOGIN);
            int c;
            String data="";
            while( (c = fin.read()) != -1){
                data = data + Character.toString((char)c);
            }
            fin.close();
            JSONObject jsData = new JSONObject(data);
            if(jsData.has("username") && jsData.has("password") && jsData.has("group_id")){
                result = doLoginNormal(activity, jsData.getString("username"), jsData.getString("password"), jsData.getInt("group_id"));
            }else{
                result = false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean doLoginNormal(Activity activity, String username, String password, int groupid){
        boolean rs = false;
        JSONObject sendData = new JSONObject();
        try{
            sendData.put("username",username);
            sendData.put("password", password);
            JSONObject result = new PostDataWithJson(sendData,activity).execute(UrlStatic.URLSignin).get();
            if(result!=null&&result.getString("message").equals("Success")){
                if(result.getJSONObject("user").getInt("group_id")==groupid) {
                    rs = true;
                    sendData.put("group_id",groupid);
                    if(Utilities.saveLogin(activity, sendData.toString())) {
                        Intent intent = new Intent(activity, LoginData.class).putExtra("key", groupid);
                        Bundle bundle = new Bundle();
                        bundle.putString("jsuser", result.getJSONObject("user").toString());
                        intent.putExtra("sendData", bundle);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                    else{
                        Log.e("Login","Can't save Login");
                    }
                }else{
                    rs = false;
                }
            }else{
                rs = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public static void logOut(Activity activity){
        clear();
        Intent intent = new Intent(activity,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.deleteFile(Utilities.SAVEING_FILE_LOGIN);
        activity.startActivity(intent);
        activity.finish();
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
    public static String convertTimeShorten(String date){
        String result = "";
        try {
            Date resultDate = new SimpleDateFormat("dd - LLL - yyyy").parse(date);
            result = new SimpleDateFormat("LLL yyyy").format(resultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String convertTimePost(String date){
        String result = "";
        try {
            Date resultDate = new SimpleDateFormat("dd - LLL - yyyy").parse(date);
            result = new SimpleDateFormat("yyyy-MM-dd").format(resultDate);
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

    public static boolean isDeleteSuccess(JSONObject input){
        boolean result =false;
        try{
            if(input.getString("message").equals("Deleted"))
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
