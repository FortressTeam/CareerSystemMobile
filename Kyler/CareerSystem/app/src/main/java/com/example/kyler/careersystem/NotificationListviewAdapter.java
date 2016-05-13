package com.example.kyler.careersystem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Entities.Notifications;
import com.example.kyler.careersystem.HiringManager.ChildHiringManagerActivity;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJsonCallback;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 19/04/2016.
 */
public class NotificationListviewAdapter extends ArrayAdapter<Notifications> {
    private Activity context;
    private ArrayList<Notifications> notifications;
    private int count;
    private int stepNumber;
    private int startCount;

    public NotificationListviewAdapter(Activity context, ArrayList<Notifications> hiringmanagerNotificationListviewitems, int startCount, int stepNumber) {
        super(context, R.layout.notification_itemlistview,hiringmanagerNotificationListviewitems);
        this.context = context;
        this.notifications = hiringmanagerNotificationListviewitems;
        this.startCount = Math.min(startCount, hiringmanagerNotificationListviewitems.size());
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Notifications getItem(int i) {
        return notifications.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.notification_itemlistview,null);
        }
        final LinearLayout notification_linearlayout = (LinearLayout) view.findViewById(R.id.notification_linearlayout);
        TextView notification_title = (TextView) view.findViewById(R.id.notification_title);
        TextView notification_content = (TextView) view.findViewById(R.id.notification_content);
        notification_title.setText(notifications.get(i).getNotificationTitle());
        notification_content.setText(notifications.get(i).getNotificationDetail());
        if(notifications.get(i).isSeen()){
            notification_linearlayout.setBackgroundResource(R.color.White);
        }else{
            notification_linearlayout.setBackgroundResource(R.color.notificationunseen);
        }
        notification_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PutDataWithJsonCallback putDataWithJsonCallback = new PutDataWithJsonCallback(new JSONObject()) {
                    @Override
                    public void receiveData(Object result) {
                        notification_linearlayout.setBackgroundResource(R.color.White);
                        int count = Utilities.notificationCount;
                        count--;
                        Utilities.notificationCount = count;
                        Utilities.getNotificationCount(context.getApplicationContext());
                    }
                };
                putDataWithJsonCallback.execute(UrlStatic.URLNotificationSeen + notifications.get(i).getID() + ".json");
                switch (notifications.get(i).getNotificationType()) {
                    case 1: // Post
                        if (Utilities.users.getGroupID() == 3) {
                            Utilities.startFragWith(context, ChildApplicantActivity.class, "jobdetail", notifications.get(i).getNotificationData() + "");
                        } else if (Utilities.users.getGroupID() == 2) {
                            Utilities.startFragWith(context, ChildHiringManagerActivity.class, "jobdetail", notifications.get(i).getNotificationData() + "");
                        }
                        break;
                    case 2: // Applicant
                        break;
                    case 3: // Hiringmanager
                        break;
                }
            }
        });
        return view;
    }

    public void showMore(){
        count = Math.min(count + stepNumber, notifications.size()); //don't go past the end
        notifyDataSetChanged(); //the count size has changed, so notify the super of the change

    }
    public boolean endReached(){
        return count == notifications.size();
    }

}
