package com.example.kyler.careersystem.Helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.example.kyler.careersystem.Applicant.FollowJobFragment;
import com.example.kyler.careersystem.Applicant.HomeFragment;
import com.example.kyler.careersystem.Applicant.JobappliedFragment;
import com.example.kyler.careersystem.Applicant.MyResumeFragment;
import com.example.kyler.careersystem.Applicant.NavigationListViewAdapter;
import com.example.kyler.careersystem.Applicant.NavigationListViewItem;
import com.example.kyler.careersystem.FailedConnectionFragment;
import com.example.kyler.careersystem.LoginActivity;
import com.example.kyler.careersystem.NotificationFragment;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJsonCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 19/05/2016.
 */
public class OrientationHepler {
    public void startFragWith(Activity activitySend,Class activityReceive,String key,String sendData){ // sendData could be an Object
        Intent intent = new Intent(activitySend,activityReceive);
        String senddt=sendData; // could be changed by an Object
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        bundle.putString("sendData",senddt);
        intent.putExtra("sendBundle",bundle);
        activitySend.startActivity(intent);
    }

    public void displayViewHiringManager(Activity context, int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new com.example.kyler.careersystem.HiringManager.HomeFragment();
                break;
            case 1:
                fragment = new com.example.kyler.careersystem.HiringManager.ManagePost();
                break;
            case 2:
                fragment = new NotificationFragment();
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

    public void displayViewApplicant(Activity context, int position){
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
            case 3://favorite
                fragment = new FollowJobFragment();
                break;
            case 4://jobapplied
                fragment = new JobappliedFragment();
                break;
            case 5://notifications
                fragment = new NotificationFragment();
                break;
            case 99:
                logOut(context);
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

    public void loadNavigationViewApplicant(Context context){
        ArrayList<NavigationListViewItem> listItem = new ArrayList<>();
        listItem.add(new NavigationListViewItem(R.drawable.navhomeicon,"Home"));
        listItem.add(new NavigationListViewItem(R.drawable.navmyresumeicon,"My Resume"));
        listItem.add(new NavigationListViewItem(R.drawable.navfavoriteicon,"Favorite"));
        listItem.add(new NavigationListViewItem(R.drawable.navjobappliedicon,"Job Applied"));
        if(Utilities.notificationCount > 0) {
            listItem.add(new NavigationListViewItem(R.drawable.navnotificationicon, "Notifications", Utilities.notificationCount, true));
        }else{
            listItem.add(new NavigationListViewItem(R.drawable.navnotificationicon, "Notifications"));
        }
        NavigationListViewAdapter adapter = new NavigationListViewAdapter(context,listItem);
        Utilities.navigationViewMenu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void logOut(final Activity activity){
        JSONObject jsSendData = new JSONObject();
        try {
            jsSendData.put("user_android_token","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JSONObject sendData = jsSendData;
        PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(sendData,activity) {
            @Override
            public void receiveData(Object result) {
                Utilities.clear();
                Intent intent = new Intent(activity,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.deleteFile(Utilities.SAVING_FILE_LOGIN);
                activity.startActivity(intent);
                activity.finish();
            }
        };
        postDataWithJsonCallback.execute(UrlStatic.URLUpdateToken + Utilities.users.getID() + ".json");
    }

    public void loadNavigationViewHiringManager(Context context){
        ArrayList<NavigationListViewItem> listItem = new ArrayList<>();
        listItem.add(new NavigationListViewItem(R.drawable.hiringmanagerpost,"Posts"));
        if(Utilities.notificationCount > 0) {
            listItem.add(new NavigationListViewItem(R.drawable.navnotificationicon, "Notifications", Utilities.notificationCount, true));
        }else{
            listItem.add(new NavigationListViewItem(R.drawable.navnotificationicon, "Notifications"));
        }
        NavigationListViewAdapter adapter = new NavigationListViewAdapter(context.getApplicationContext(),listItem);
        Utilities.navigationViewMenu.setAdapter(adapter);
    }

}
