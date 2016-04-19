package com.example.kyler.careersystem.HiringManager;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.kyler.careersystem.Entities.Notifications;
import com.example.kyler.careersystem.HiringManager.customize.NotificationListviewAdapter;
import com.example.kyler.careersystem.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment implements  AbsListView.OnScrollListener{
    private NotificationListviewAdapter notificationListviewAdapter;
    private ArrayList<Notifications> notificationListviewitems;
    private ListView notification_listview;
    private ProgressBar progressBar;
    private Handler mHandler;
    private boolean nomoreData=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hiringmanager_notification_fragment, container, false);
        notificationListviewitems = new ArrayList<>();
        for(int i=0;i<24;i++){
            if(i%3==0) {
                notificationListviewitems.add(new Notifications(i, "abc", "abc", "abc", true, 1));
            }else{
                notificationListviewitems.add(new Notifications(i, "abc", "abc", "abc", false, 1));
            }
        }
        mHandler = new Handler();
        notification_listview = (ListView) rootView.findViewById(R.id.hiringmanager_notification_listview);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        notification_listview.addFooterView(footer);
        notificationListviewAdapter = new NotificationListviewAdapter(getActivity().getApplicationContext(),notificationListviewitems,10,5);
        notification_listview.setAdapter(notificationListviewAdapter);
        notification_listview.setOnScrollListener(this);
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
            mHandler.postDelayed(showMore,200);
            hasCallback = true;
        }
    }
    private Runnable showMore = new Runnable() {
        @Override
        public void run() {
            if(notificationListviewAdapter.endReached()){

            }
            notificationListviewAdapter.showMore();
            hasCallback = false;
        }
    };
}
