package com.example.kyler.careersystem.HiringManager.customize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.Notifications;
import com.example.kyler.careersystem.R;

import java.util.ArrayList;

/**
 * Created by kyler on 19/04/2016.
 */
public class NotificationListviewAdapter extends ArrayAdapter<Notifications> {
    private Context context;
    private ArrayList<Notifications> notificationListviewitems;
    private int count;
    private int stepNumber;
    private int startCount;

    public NotificationListviewAdapter(Context context, ArrayList<Notifications> hiringmanagerNotificationListviewitems, int startCount, int stepNumber) {
        super(context, R.layout.notification_itemlistview,hiringmanagerNotificationListviewitems);
        this.context = context;
        this.notificationListviewitems = hiringmanagerNotificationListviewitems;
        this.startCount = Math.min(startCount, hiringmanagerNotificationListviewitems.size());
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    @Override
    public int getCount() {
        return notificationListviewitems.size();
    }

    @Override
    public Notifications getItem(int i) {
        return notificationListviewitems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.notification_itemlistview,null);
        }
        LinearLayout notification_linearlayout = (LinearLayout) view.findViewById(R.id.notification_linearlayout);
        ImageView notification_image = (ImageView) view.findViewById(R.id.notification_image);
        TextView notification_title = (TextView) view.findViewById(R.id.notification_title);
        TextView notification_content = (TextView) view.findViewById(R.id.notification_content);
        TextView notification_date = (TextView) view.findViewById(R.id.notification_date);

        if(notificationListviewitems.get(i).isSeen()){
            notification_linearlayout.setBackgroundResource(R.color.White);
        }else{
            notification_linearlayout.setBackgroundResource(R.color.notificationunseen);
        }
        return view;
    }

    public void showMore(){
        count = Math.min(count + stepNumber, notificationListviewitems.size()); //don't go past the end
        notifyDataSetChanged(); //the count size has changed, so notify the super of the change

    }
    public boolean endReached(){
        return count == notificationListviewitems.size();
    }

}
