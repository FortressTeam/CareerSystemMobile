package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewAdapterLoadInfinite;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.ViewHolder;
import com.example.kyler.careersystem.Bussiness.PostController;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.WorkWithService.GetJsonLoadMoreCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Duck_Ky on 3/7/2016.
 */
public class SearchFragmentResult extends Fragment implements AbsListView.OnScrollListener, ListView.OnItemClickListener, View.OnClickListener {
    private ObservableListView search_job_listview;

    private JobListViewAdapterLoadInfinite jobListViewAdapterLoadInfinite;
    private ArrayList<JobListViewItem> jobListViewItems;
    private Handler mHandler;
    private ProgressBar progressBar;
    private ArrayList<Posts> arrPost;
    private JSONArray jsArraySearchResult;
    private PostController postController;
    private String urlSearch;
    private boolean nomoreData=true;
    private int page=1;
    private Button jobSearchResultTryAgain;
    private LinearLayout jobSearchResultNoResult;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_search_job_result_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search Result");
        arrPost = new ArrayList<>();
        mHandler = new Handler();
        Bundle bundle = getArguments();
        urlSearch = bundle.getString("sendData");
        postController = new PostController();
        jobListViewItems = new ArrayList<>();
        search_job_listview = (ObservableListView) rootView.findViewById(R.id.applicant_search_job_result_listview);
        jobSearchResultNoResult = (LinearLayout) rootView.findViewById(R.id.applicant_search_job_result_noresult);
        jobSearchResultTryAgain = (Button) rootView.findViewById(R.id.applicant_search_job_result_tryagain);
        jobSearchResultNoResult.setVisibility(View.GONE);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        search_job_listview.addFooterView(footer);
        search_job_listview.setVisibility(View.GONE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetJsonArrayCallback jsonArrayCallback = new GetJsonArrayCallback(getActivity(), "posts") {
                    @Override
                    public void receiveData(Object result) {
                        try {
                            jsArraySearchResult = (JSONArray) result;
                            if (jsArraySearchResult.length() != 0) {
                                for (int i = 0; i < jsArraySearchResult.length(); i++) {
                                    JSONObject jsonObject = jsArraySearchResult.getJSONObject(i);
                                    Posts post = new Posts(jsonObject);
                                    arrPost.add(post);
                                    HiringManagers hiringManager = new HiringManagers(jsonObject.getJSONObject("hiring_manager"));
                                    Categories category = new Categories(jsonObject.getJSONObject("category"));
                                    jobListViewItems.add(postController.getJobListViewItem(post, hiringManager, category));
                                }
                                jobListViewAdapterLoadInfinite = new JobListViewAdapterLoadInfinite(getActivity().getApplicationContext(), jobListViewItems, 10, 5);
                                search_job_listview.setAdapter(jobListViewAdapterLoadInfinite);
                                progressBar.setVisibility(View.VISIBLE);
                                nomoreData = false;
                                search_job_listview.setVisibility(View.VISIBLE);
                            } else {
                                nomoreData = true;
                                jobSearchResultNoResult.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                jsonArrayCallback.execute(urlSearch + page + "&sort=post_date&direction=desc");
            }
        }, 300);
        search_job_listview.setOnScrollListener(this);
        search_job_listview.setOnItemClickListener(this);
        jobSearchResultTryAgain.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //Instagram listview
        for(int i=0;i<search_job_listview.getChildCount()-1;i++){
            View child = search_job_listview.getChildAt(i);
            ViewHolder holder = (ViewHolder) child.getTag();
            if(i==0){
                boolean isAtBottom = child.getHeight() <= holder.header.getBottom();
                int offset = holder.previousTop - child.getTop();
                if(!(isAtBottom && offset > 0)){
                    holder.previousTop = child.getTop();
                    holder.header.offsetTopAndBottom(offset);
                    holder.header.invalidate();
                }else{
                    holder.header.invalidate();
                }
            }
            else if (holder.header.getTop() != 0) {
                int offset = -1 * holder.header.getTop();
                holder.header.offsetTopAndBottom(offset);
                holder.previousTop = 0;
                holder.header.invalidate();
            }
        }

        //Load more
        if(firstVisibleItem + visibleItemCount == totalItemCount && !nomoreData && !hasCallback){ //check if we've reached the bottom
            progressBar.setVisibility(View.VISIBLE);
            mHandler.postDelayed(showMore, 300);
            hasCallback = true;
        }
    }

    private boolean hasCallback;
    private Runnable showMore = new Runnable(){
        public void run(){
            if(jobListViewAdapterLoadInfinite!=null){
                if(jobListViewAdapterLoadInfinite.endReached()){
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
                                        arrPost.add(post);
                                        HiringManagers hiringManager = new HiringManagers(new JSONObject(jsonObject.getString("hiring_manager")));
                                        Categories category = new Categories(new JSONObject(jsonObject.getString("category")));
                                        jobListViewItems.add(postController.getJobListViewItem(post, hiringManager, category));
                                        jobListViewAdapterLoadInfinite.setJobListViewItems(jobListViewItems);
                                        jobListViewAdapterLoadInfinite.notifyDataSetChanged();
                                    }
                                }else
                                    nomoreData=true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    getJsonLoadMoreCallback.execute(urlSearch);
                }
                boolean noMoreToShow = jobListViewAdapterLoadInfinite.showMore();
            }
            hasCallback = false;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "jobdetail", arrPost.get(i).getID() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.applicant_search_job_result_tryagain:
                getActivity().onBackPressed();
                break;
        }
    }
}
