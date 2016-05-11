package com.example.kyler.careersystem.Applicant;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.Customize.JobListViewAdapterLoadInfinite;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.ViewHolder;
import com.example.kyler.careersystem.Bussiness.PostController;
import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.WorkWithService.GetJsonLoadMoreCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by kyler on 07/03/2016.
 */
public class SearchFragment extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener, ListView.OnItemClickListener, View.OnKeyListener{
    private ObservableListView search_job_listview;
    private SearchableSpinner searchJobLocation;
    private SearchableSpinner searchJobMajor;
    private Button searchJob;
    private ArrayList<Categories> categories = new ArrayList<>();
    private ArrayList<String> arrCategories = new ArrayList<>();
    private String urlSearch="";
    private ImageView searchJobFilter;
    private boolean filter=false;
    private LinearLayout searchJobFilterLayout;
    private EditText searchJobEditText;
    private JobListViewAdapterLoadInfinite jobListViewAdapterLoadInfinite;
    private ArrayList<JobListViewItem> jobListViewItems;
    private Handler mHandler;
    private ProgressBar progressBar;
    private ArrayList<Posts> arrPost;
    private JSONArray jsArraySearchResult;
    private PostController postController;
    private boolean nomoreData=true;
    private LinearLayout jobSearchResultNoResult;
    private int page=1;


    public SearchFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_search_job_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");
        Bundle bundle = getArguments();
        String searchContent = bundle.getString("searchContent");
        urlSearch = UrlStatic.URLPosts + "?post_status=1&limit=10"+"&q="+Utilities.replaceSpecialCharacter(searchContent)+"&page=";
        JSONArray jsonArrayCategories = Utilities.jsArrayCategories;
        arrCategories.add("All");
        for(int i=0;i<jsonArrayCategories.length();i++){
            try {
                categories.add(new Categories(jsonArrayCategories.getJSONObject(i)));
                arrCategories.add(categories.get(i).getCategoryName());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        search_job_listview = (ObservableListView) rootView.findViewById(R.id.applicant_search_job_result_listview);
        searchJobEditText = (EditText) rootView.findViewById(R.id.applicant_search_job_edittext);
        searchJobFilterLayout = (LinearLayout) rootView.findViewById(R.id.applicant_search_job_filter_layout);
        searchJobFilter = (ImageView) rootView.findViewById(R.id.applicant_search_job_filter);
        searchJobLocation = (SearchableSpinner) rootView.findViewById(R.id.applicant_search_job_spinner_location);
        searchJobMajor = (SearchableSpinner) rootView.findViewById(R.id.applicant_search_job_spinner_major);
        searchJob = (Button) rootView.findViewById(R.id.applicant_search_job_button);
        mHandler = new Handler();
        postController = new PostController();
        jobListViewItems = new ArrayList<>();
        search_job_listview = (ObservableListView) rootView.findViewById(R.id.applicant_search_job_result_listview);
        jobSearchResultNoResult = (LinearLayout) rootView.findViewById(R.id.applicant_search_job_result_noresult);
        jobSearchResultNoResult.setVisibility(View.GONE);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        search_job_listview.addFooterView(footer);
        search_job_listview.setVisibility(View.GONE);
        searchJobEditText.setText(searchContent);
        String arrLocation[] = {"All","Da Nang","Quang Nam"};
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,arrLocation);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        searchJobLocation.setAdapter(locationAdapter);
        searchJobLocation.setTitle("Select Location");
        searchJobLocation.setSelection(0);
        ArrayAdapter<String> majorAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_item,arrCategories);
        majorAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        searchJobMajor.setAdapter(majorAdapter);
        searchJobMajor.setTitle("Select Major");
        searchJobMajor.setSelection(0);
        searchJobEditText.setOnKeyListener(this);
        searchJob.setOnClickListener(this);
        searchJobFilter.setOnClickListener(this);
        search_job_listview.setOnScrollListener(this);
        search_job_listview.setOnItemClickListener(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(urlSearch);
            }
        }, 300);
        showFilter();
        return rootView;
    }

    private void getData(String url){
        GetJsonArrayCallback jsonArrayCallback = new GetJsonArrayCallback(getActivity(), "posts") {
            @Override
            public void receiveData(Object result) {
                try {
                    arrPost = new ArrayList<>();
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
                        jobSearchResultNoResult.setVisibility(View.GONE);
                    } else {
                        nomoreData = true;
                        jobSearchResultNoResult.setVisibility(View.VISIBLE);
                        search_job_listview.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        jsonArrayCallback.execute(url.replaceAll(" ","+") + page + "&sort=post_date&direction=desc");
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.applicant_search_job_button:
                jobListViewItems = new ArrayList<>();
                page=1;
                String url = UrlStatic.URLPosts + "?post_status=1&limit=10";
                if(!searchJobEditText.toString().trim().equals("")){
                    url += "&q="+Utilities.replaceSpecialCharacter(searchJobEditText.getText().toString().trim());
                }
                if (!searchJobMajor.getSelectedItem().toString().equals("All")) {
                    url += "&category_id=" + categories.get(searchJobMajor.getSelectedItemPosition()-1).getID()+"";
                }
                if (!searchJobLocation.getSelectedItem().toString().equals("All")) {
                    url += "&location=" + searchJobLocation.getSelectedItem().toString();
                }
                url+="&page=";
                filter= !filter;
                showFilter();
                urlSearch = url;
                getData(urlSearch);
                break;
            case R.id.applicant_search_job_filter:
                filter=!filter;
                showFilter();
                break;
        }
    }

    private void showFilter(){
        if(filter){
            searchJobFilterLayout.setVisibility(View.VISIBLE);
        }else{
            searchJobFilterLayout.setVisibility(View.GONE);
        }
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
                    getJsonLoadMoreCallback.execute(urlSearch + page + "&sort=post_date&direction=desc");
                }
                boolean noMoreToShow = jobListViewAdapterLoadInfinite.showMore();
            }
            hasCallback = false;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i < jobListViewItems.size())
            Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "jobdetail", arrPost.get(i).getID() + "");
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        switch (view.getId()){
            case R.id.applicant_search_job_edittext:
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    if(!searchJobEditText.toString().trim().equals("")) {
                        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        jobListViewItems = new ArrayList<>();
                        page = 1;
                        String url = UrlStatic.URLPosts + "?post_status=1&limit=10";
                        url += "&q=" + Utilities.replaceSpecialCharacter(searchJobEditText.getText().toString().trim());
                        url += "&page=";
                        filter=false;
                        showFilter();
                        urlSearch = url;
                        getData(urlSearch);
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
}
