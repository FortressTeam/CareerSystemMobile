package com.example.kyler.careersystem;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Entities.Notifications;
import com.example.kyler.careersystem.HiringManager.ChildHiringManagerActivity;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.WorkWithService.GetJsonLoadMoreCallback;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJsonCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements  AbsListView.OnScrollListener, ListView.OnItemClickListener{
    private NotificationListviewAdapter notificationListviewAdapter;
    private ArrayList<Notifications> notificationListviewitems;
    private ListView notification_listview;
    private ProgressBar progressBar;
    private Handler mHandler;
    private JSONArray jsArrayNotifications;
    private boolean nomoreData=true;
    private int page = 1;
    private ArrayList<Notifications> notifications = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hiringmanager_notification_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notifications");
        notificationListviewitems = new ArrayList<>();
        mHandler = new Handler();
        notification_listview = (ListView) rootView.findViewById(R.id.hiringmanager_notification_listview);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        notification_listview.addFooterView(footer);
        notificationListviewAdapter = new NotificationListviewAdapter(getActivity(),notificationListviewitems,20,10);
        notification_listview.setAdapter(notificationListviewAdapter);
        notification_listview.setOnScrollListener(this);
        notification_listview.setOnItemClickListener(this);
        GetJsonArrayCallback getJsonArrayCallback = new GetJsonArrayCallback(getActivity(),"notifications") {
            @Override
            public void receiveData(Object result) {
                jsArrayNotifications = (JSONArray) result;
                if(Utilities.checkConnect(jsArrayNotifications)){
                    if(jsArrayNotifications.length()!=0){
                        nomoreData=false;
                        try {
                            for (int i = 0; i < jsArrayNotifications.length(); i++) {
                                JSONObject jsonObject = jsArrayNotifications.getJSONObject(i);
                                notifications.add(new Notifications(jsonObject));
                            }
                            notificationListviewAdapter = new NotificationListviewAdapter(getActivity(),notifications,10,5);
                            notification_listview.setAdapter(notificationListviewAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        nomoreData = true;
                        // don't have any notifications
                    }
                }else{
                    nomoreData=true;
                    if(Utilities.users.getGroupID() == 2){
                        Utilities.displayViewHiringManager(getActivity(),404);
                    }else if(Utilities.users.getGroupID() == 3){
                        Utilities.displayViewApplicant(getActivity(),404);
                    }
                }
            }
        };
        getJsonArrayCallback.execute(UrlStatic.URLNotifications + "?user_id=" + Utilities.users.getID()+"&page="+page);
        return rootView;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    private boolean hasCallback;

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount == totalItemCount && !nomoreData && !hasCallback){ //check if we've reached the bottom
            progressBar.setVisibility(View.VISIBLE);
            mHandler.postDelayed(showMore, 300);
            hasCallback = true;
        }
    }
    private Runnable showMore = new Runnable() {
        @Override
        public void run() {
            if(notificationListviewAdapter.endReached()){
                page++;
                GetJsonLoadMoreCallback getJsonLoadMoreCallback = new GetJsonLoadMoreCallback(progressBar,"posts") {
                    @Override
                    public void receiveData(Object result) {
                        JSONArray jsArrayNotifications = (JSONArray) result;
                        if(Utilities.checkConnect(jsArrayNotifications)){
                            if(jsArrayNotifications.length()!=0){
                                try {
                                    for (int i = 0; i < jsArrayNotifications.length(); i++) {
                                        JSONObject jsonObject = jsArrayNotifications.getJSONObject(i);
                                        notifications.add(new Notifications(jsonObject));
                                    }
                                    notificationListviewAdapter = new NotificationListviewAdapter(getActivity(),notifications,10,5);
                                    notification_listview.setAdapter(notificationListviewAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                nomoreData=true;
                                // don't have any notifications
                            }
                        }else{
                            nomoreData=true;
                        }
                    }
                };
                getJsonLoadMoreCallback.execute(UrlStatic.URLNotifications + "?user_id=" + Utilities.users.getID()+"&limit=20&page="+page);
            }
            notificationListviewAdapter.showMore();
            hasCallback = false;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
