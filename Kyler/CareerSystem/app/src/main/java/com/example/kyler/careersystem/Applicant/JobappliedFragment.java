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
import com.example.kyler.careersystem.Applicant.Customize.JobListViewAdapterLoadInfinite;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Bussiness.PostController;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.Entities.PostsHasCurriculumVitaes;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.WorkWithService.GetJsonLoadMoreCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobappliedFragment extends Fragment implements AbsListView.OnScrollListener,ListView.OnItemClickListener{
    private JobListViewAdapterLoadInfinite jobAppliedListViewAdapterLoadInfinite;
    private ArrayList<JobListViewItem> jobAppliedListViewItems;

    private ListView jobappliedListView;
    private Handler mHandler;
    private ProgressBar progressBar;
    private ArrayList<Posts> posts = new ArrayList<>();
    private PostController postController = new PostController();
    private String url="";
    private int page=1;
    private boolean nomoreData = true;

    public JobappliedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_jobapplied_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Job Applied");
        url = UrlStatic.URLPosts + "?applicant_id="+Utilities.applicants.getID()+"&limit=10&posts_status=1&page=";
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetJsonArrayCallback getJsonArrayCallback = new GetJsonArrayCallback(getActivity(),"posts") {
                    @Override
                    public void receiveData(Object result) {
                        JSONArray jsonArray = (JSONArray) result;
                        if(Utilities.checkConnect(jsonArray)){
                            jobAppliedListViewItems = new ArrayList<>();
                            if(jsonArray.length() != 0){
                                try {
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Posts post = new Posts(jsonObject);
                                        posts.add(post);
                                        PostsHasCurriculumVitaes postsHasCurriculumVitaes = new PostsHasCurriculumVitaes(jsonObject.getJSONArray("posts_has_curriculum_vitaes").getJSONObject(0));
                                        HiringManagers hiringManager = new HiringManagers(jsonObject.getJSONObject("hiring_manager"));
                                        Categories categories = new Categories(jsonObject.getJSONObject("category"));
                                        jobAppliedListViewItems.add(postController.getJobAppliedListViewItem(post,postsHasCurriculumVitaes,hiringManager,categories));
                                    }
                                    jobAppliedListViewAdapterLoadInfinite = new JobListViewAdapterLoadInfinite(getActivity(), jobAppliedListViewItems, 10, 5);
                                    jobappliedListView.setAdapter(jobAppliedListViewAdapterLoadInfinite);
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
                getJsonArrayCallback.execute(url + page + "&sort=post_date&direction=desc");
            }
        },300);
        jobappliedListView = (ListView) rootView.findViewById(R.id.jobapplied_listview);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        jobappliedListView.addFooterView(footer);
        jobappliedListView.setOnScrollListener(this);
        jobappliedListView.setOnItemClickListener(this);
        return rootView;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i < jobAppliedListViewItems.size())
            Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "jobdetail", posts.get(i).getID() + "");
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount == totalItemCount && !nomoreData && !hasCallback){ //check if we've reached the bottom
            progressBar.setVisibility(View.VISIBLE);
            mHandler.postDelayed(showMore, 300);
            hasCallback = true;
        }
    }

    private boolean hasCallback;
    private Runnable showMore = new Runnable(){
        public void run(){
            if(jobAppliedListViewAdapterLoadInfinite!=null){
                if(jobAppliedListViewAdapterLoadInfinite.endReached()){
                    page++;
                    GetJsonLoadMoreCallback getJsonLoadMoreCallback = new GetJsonLoadMoreCallback(progressBar,"posts") {
                        @Override
                        public void receiveData(Object result) {
                            try {
                                JSONArray jsonArray = (JSONArray) result;
                                if(jsonArray!=null){
                                    nomoreData=false;
                                    for(int i=0;i<jsonArray.length();i++){
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Posts post = new Posts(jsonObject);
                                        posts.add(post);
                                        PostsHasCurriculumVitaes postsHasCurriculumVitaes = new PostsHasCurriculumVitaes(jsonObject.getJSONArray("posts_has_curriculum_vitaes").getJSONObject(0));
                                        HiringManagers hiringManager = new HiringManagers(jsonObject.getJSONObject("hiring_manager"));
                                        Categories categories = new Categories(jsonObject.getJSONObject("category"));
                                        jobAppliedListViewItems.add(postController.getJobAppliedListViewItem(post, postsHasCurriculumVitaes, hiringManager,categories));
                                        jobAppliedListViewAdapterLoadInfinite.setJobListViewItems(jobAppliedListViewItems);
                                        jobAppliedListViewAdapterLoadInfinite.notifyDataSetChanged();
                                    }
                                }else
                                    nomoreData=true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    getJsonLoadMoreCallback.execute(url + page + "&sort=post_date&direction=desc");
                }
                jobAppliedListViewAdapterLoadInfinite.showMore();
            }
            hasCallback = false;
        }
    };
}
