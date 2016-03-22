package com.example.kyler.careersystem.Applicant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.Customize.ExperienceListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.ExperienceListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.NonScrollListView;
import com.example.kyler.careersystem.R;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kyler on 10/03/2016.
 */
public class MyResumeFragment extends Fragment implements ObservableScrollViewCallbacks {
    private ExperienceListViewAdapter experienceListViewAdapter;
    private ArrayList<ExperienceListViewItem> experienceListViewItems;

    private NonScrollListView myresume_listview_experience;
    private ObservableScrollView myresumeFragment;
    private TextView myresumeExperience;
    private ImageView myresumeUser;

    public MyResumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.applicant_myresume_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Resumes");
        myresumeFragment = (ObservableScrollView) rootView.findViewById(R.id.myresume_fragment);
        myresumeUser = (ImageView) rootView.findViewById(R.id.myresume_user_image);
        myresumeExperience = (TextView) rootView.findViewById(R.id.myresume_experience);
        Picasso.with(getActivity().getApplicationContext()).load("https://cdn4.iconfinder.com/data/icons/rcons-user/32/child_boy-128.png").into(myresumeUser);
        myresume_listview_experience = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_experience);

        experienceListViewItems = new ArrayList<>();
        experienceListViewItems.add(new ExperienceListViewItem("Company a","Software Engineer","20/07/2012","20/08/2013","Nothing to say"));
        experienceListViewItems.add(new ExperienceListViewItem("Company b","Software Engineer","20/08/2013","20/08/2014","Nothing to say"));
        experienceListViewItems.add(new ExperienceListViewItem("Company c","Software Engineer","20/08/2014","20/08/2015","Nothing to say"));
        experienceListViewItems.add(new ExperienceListViewItem("Company d","Software Engineer","20/08/2015","20/08/2016","Nothing to say"));
        experienceListViewAdapter = new ExperienceListViewAdapter(getActivity().getApplicationContext(),experienceListViewItems);
        if(experienceListViewAdapter.getCount()!=0){
            myresumeExperience.setVisibility(View.GONE);
            myresume_listview_experience.setAdapter(experienceListViewAdapter);
        } else{
            myresumeExperience.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }
}
