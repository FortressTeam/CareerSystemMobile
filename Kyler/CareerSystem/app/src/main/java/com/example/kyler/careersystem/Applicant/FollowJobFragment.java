package com.example.kyler.careersystem.Applicant;


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
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.Customize.JobFollowAdapterLoadInfinite;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewAdapterLoadInfinite;
import com.example.kyler.careersystem.Bussiness.PostController;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.Entities.PostsHasCurriculumVitaes;
import com.example.kyler.careersystem.Helper.OrientationHepler;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.WorkWithService.GetJsonLoadMoreCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowJobFragment extends Fragment implements AbsListView.OnScrollListener,ListView.OnItemClickListener{

    private JobFollowAdapterLoadInfinite jobFollowAdapterLoadInfinite;
    private ArrayList<Posts> posts;

    private ListView jobfollowListView;
    private Handler mHandler;
    private ProgressBar progressBar;
    private PostController postController = new PostController();
    private String url="";
    private int page=1;
    private boolean nomoreData = true;
    private OrientationHepler orientationHepler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_follow_job_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Follow");
        orientationHepler = new OrientationHepler();
        url = UrlStatic.URLApplicantsFollowPosts + "?follow_status=1&limit=10&applicant_id="+ Utilities.applicants.getID()+"&page=";
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetJsonArrayCallback getJsonArrayCallback = new GetJsonArrayCallback(getActivity(),"applicantsFollowPost") {
                    @Override
                    public void receiveData(Object result) {
                        JSONArray jsonArray = (JSONArray) result;
                        if(Utilities.checkConnect(jsonArray)){
                            posts = new ArrayList<>();
                            if(jsonArray.length() != 0){
                                try {
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Posts post = new Posts(jsonObject.getJSONObject("post"));
                                        posts.add(post);
                                    }
                                    jobFollowAdapterLoadInfinite = new JobFollowAdapterLoadInfinite(getActivity(), posts, 10, 5);
                                    jobfollowListView.setAdapter(jobFollowAdapterLoadInfinite);
                                    nomoreData=false;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                nomoreData=true;
                            }
                        }else {
                            Toast.makeText(getActivity().getApplicationContext(), "Connection got problem!", Toast.LENGTH_SHORT).show();
                            orientationHepler.displayViewApplicant(getActivity(), 404);
                        }
                    }
                };
                getJsonArrayCallback.execute(url + page );
            }
        },300);
        jobfollowListView = (ListView) rootView.findViewById(R.id.follow_job_listview);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        jobfollowListView.addFooterView(footer);
        jobfollowListView.setOnScrollListener(this);
        jobfollowListView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i < posts.size())
            Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "jobdetail", posts.get(i).getID() + "");
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount == totalItemCount && !nomoreData && !hasCallback){ //check if we've reached the bottom
            progressBar.setVisibility(View.VISIBLE);
            mHandler.postDelayed(showMore, 300);
            hasCallback = true;
        }
    }
    private boolean hasCallback;
    private Runnable showMore = new Runnable(){
        public void run(){
            if(jobFollowAdapterLoadInfinite!=null){
                if(jobFollowAdapterLoadInfinite.endReached()){
                    page++;
                    GetJsonLoadMoreCallback getJsonLoadMoreCallback = new GetJsonLoadMoreCallback(progressBar,"applicantsFollowPost") {
                        @Override
                        public void receiveData(Object result) {
                            try {
                                JSONArray jsonArray = (JSONArray) result;
                                if(jsonArray!=null){
                                    nomoreData=false;
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Posts post = new Posts(jsonObject.getJSONObject("post"));
                                        posts.add(post);
                                        jobFollowAdapterLoadInfinite.setJobListViewItems(posts);
                                        jobFollowAdapterLoadInfinite.notifyDataSetChanged();
                                    }
                                }else
                                    nomoreData=true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    getJsonLoadMoreCallback.execute(url + page );
                }
                jobFollowAdapterLoadInfinite.showMore();
            }
            hasCallback = false;
        }
    };
}
