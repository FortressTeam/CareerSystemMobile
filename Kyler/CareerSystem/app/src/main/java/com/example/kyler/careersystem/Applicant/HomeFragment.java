package com.example.kyler.careersystem.Applicant;

import android.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.Customize.JobListViewAdapterLoadInfinite;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.ViewHolder;
import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.Bussiness.PostController;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.WorkWithService.GetJsonLoadMoreCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements AbsListView.OnScrollListener,ListView.OnItemClickListener {
    private ListView home_job_listview;
    private TextView homeBlank;

    private JobListViewAdapterLoadInfinite jobListViewAdapterLoadInfinite;
    private ArrayList<JobListViewItem> jobListViewItems;
    private ArrayList<Posts> arrPost;
    private Handler mHandler;
    private ProgressBar progressBar;
    private JSONArray jsonArray = null;

    private PostController postController;

    private int page=1;
    private boolean nomoreData=false;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHandler = new Handler();
        arrPost = new ArrayList<>();
        jobListViewItems = new ArrayList<JobListViewItem>();
        View rootView = inflater.inflate(R.layout.applicant_home_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        home_job_listview = (ListView) rootView.findViewById(R.id.home_job_listview);
        homeBlank = (TextView) rootView.findViewById(R.id.home_job_blank);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        home_job_listview.addFooterView(footer);
        home_job_listview.setOnScrollListener(this);
        home_job_listview.setOnItemClickListener(this);
        progressBar.setVisibility((4 < jobListViewItems.size()) ? View.VISIBLE : View.GONE);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.home_swipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Utilities.jsArrayPost=null;
                Utilities.startActivity(getActivity(), ApplicantMainActivity.class,1);
                getActivity().finish();
            }
        });
        postController = new PostController();
        if(Utilities.jsArrayPost == null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetJsonArrayCallback getJsonArrayCallback = new GetJsonArrayCallback(getActivity(), "posts") {
                        @Override
                        public void receiveData(Object result) {
                            try {
                                jsonArray = (JSONArray) result;
                                Utilities.jsArrayPost = jsonArray;
                                if (Utilities.checkConnect(jsonArray)) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Posts post = new Posts(jsonObject);
                                        arrPost.add(post);
                                        HiringManagers hiringManager = new HiringManagers(jsonObject.getJSONObject("hiring_manager"));
                                        Categories category = new Categories(jsonObject.getJSONObject("category"));
                                        jobListViewItems.add(postController.getJobListView(post, hiringManager, category));
                                    }
                                    jobListViewAdapterLoadInfinite = new JobListViewAdapterLoadInfinite(getActivity().getApplicationContext(), jobListViewItems, 10, 10);
                                    home_job_listview.setAdapter(jobListViewAdapterLoadInfinite);
                                    homeBlank.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Connection got problem!", Toast.LENGTH_SHORT).show();
                                    Utilities.displayViewApplicant(getActivity(), 404);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    getJsonArrayCallback.execute(UrlStatic.URLHomefragment + page + "&sort=post_date&direction=desc");
                }
            }, 300);
        }else {
            try {
                jsonArray = Utilities.jsArrayPost;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Posts post = new Posts(jsonObject);
                    arrPost.add(post);
                    HiringManagers hiringManager = new HiringManagers(jsonObject.getJSONObject("hiring_manager"));
                    Categories category = new Categories(jsonObject.getJSONObject("category"));
                    jobListViewItems.add(postController.getJobListView(post, hiringManager, category));
                }
                jobListViewAdapterLoadInfinite = new JobListViewAdapterLoadInfinite(getActivity().getApplicationContext(), jobListViewItems, 10, 10);
                home_job_listview.setAdapter(jobListViewAdapterLoadInfinite);
                homeBlank.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
                                        jobListViewItems.add(postController.getJobListView(post, hiringManager, category));
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
                    getJsonLoadMoreCallback.execute(UrlStatic.URLHomefragment+page+"&sort=post_date&direction=desc");
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
}
