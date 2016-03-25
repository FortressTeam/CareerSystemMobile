package com.example.kyler.careersystem.Applicant;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.example.kyler.careersystem.Applicant.Customize.ViewHolder;
import com.example.kyler.careersystem.Controller.PostController;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.GetDataFromService.GetJsonArray;
import com.example.kyler.careersystem.GetDataFromService.GetJsonLoadMore;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener,ListView.OnItemClickListener {
    private ListView home_job_listview;

    private JobListViewAdapterLoadInfinite jobListViewAdapterLoadInfinite;
    private ArrayList<JobListViewItem> jobListViewItems;
    private Handler mHandler;
    private ProgressBar progressBar;

    private PostController postController;

    private int page=1;
    private boolean nomoreData=false;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_home_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        postController = new PostController();
        mHandler = new Handler();
        home_job_listview = (ListView) rootView.findViewById(R.id.home_job_listview);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        home_job_listview.addFooterView(footer);
        jobListViewItems = new ArrayList<JobListViewItem>();
        try {
            JSONArray jsonArray = new GetJsonArray(getActivity(),"posts").execute(UrlStatic.URLHomefragment+page).get();
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Posts post = new Posts(jsonObject);
                HiringManagers hiringManager = new HiringManagers(new JSONObject(jsonObject.getString("hiring_manager")));
                Categories category = new Categories(new JSONObject(jsonObject.getString("category")));
                jobListViewItems.add(postController.getJobListView(post,hiringManager,category));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jobListViewAdapterLoadInfinite = new JobListViewAdapterLoadInfinite(getActivity().getApplicationContext(),jobListViewItems,10,5);
        home_job_listview.setAdapter(jobListViewAdapterLoadInfinite);
        home_job_listview.setOnScrollListener(this);
        home_job_listview.setOnItemClickListener(this);
        progressBar.setVisibility((4 < jobListViewItems.size()) ? View.VISIBLE : View.GONE);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.home_swipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refresh code here:

                Toast.makeText(getActivity().getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);
            }
        });
        return rootView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //Instagram listview
        for(int i=0;i<home_job_listview.getChildCount()-1;i++){
            View child = home_job_listview.getChildAt(i);
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
            Toast.makeText(getActivity().getApplicationContext(), "Loading", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(showMore, 3000);
            hasCallback = true;
        }
    }

    private boolean hasCallback;
    private Runnable showMore = new Runnable(){
        public void run(){
            if(jobListViewAdapterLoadInfinite.endReached()){
                page++;
                try {
                    JSONArray jsonArray = new GetJsonLoadMore(progressBar,"posts").execute(UrlStatic.URLHomefragment+page).get();
                    if(jsonArray!=null){
                        nomoreData=false;
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Posts post = new Posts(jsonObject);
                            HiringManagers hiringManager = new HiringManagers(new JSONObject(jsonObject.getString("hiring_manager")));
                            Categories category = new Categories(new JSONObject(jsonObject.getString("category")));
                            jobListViewItems.add(postController.getJobListView(post,hiringManager,category));
                            jobListViewAdapterLoadInfinite.setJobListViewItems(jobListViewItems);
                        }
                    }else
                        nomoreData=true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            boolean noMoreToShow = jobListViewAdapterLoadInfinite.showMore(); //show more views and find out if
            hasCallback = false;
            if(nomoreData)
                progressBar.setVisibility(View.GONE);
            else
                progressBar.setVisibility(View.VISIBLE);
//            progressBar.setVisibility((hasData&&!noMoreToShow)? View.VISIBLE : View.GONE);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "jobdetail", i+1 + "");
    }
}
