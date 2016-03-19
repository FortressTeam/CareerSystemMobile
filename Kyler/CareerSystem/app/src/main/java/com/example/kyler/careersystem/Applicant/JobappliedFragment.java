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

import com.example.kyler.careersystem.Applicant.Customize.JobAppliedListViewAdapterLoadInfinite;
import com.example.kyler.careersystem.Applicant.Customize.JobAppliedListViewItem;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobappliedFragment extends Fragment implements AbsListView.OnScrollListener,ListView.OnItemClickListener{
    private JobAppliedListViewAdapterLoadInfinite jobAppliedListViewAdapterLoadInfinite;
    private ArrayList<JobAppliedListViewItem> jobAppliedListViewItems;

    private ListView jobappliedListView;
    private Handler mHandler;
    private ProgressBar progressBar;

    public JobappliedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_jobapplied_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Job Applied");
        mHandler = new Handler();
        JSONObject jsJob1=null,jsJob2=null,jsJob3=null;
        try {
            jsJob1 = new JSONObject("{\n" +
                    "  \"company_image\": \"https://www.fs-net.de/assets/Uploads/home/_resampled/ResizedImage130130-icon-fabrik-blue.png\",\n" +
                    "  \"company_name\": \"Enclave\",\n" +
                    "  \"post_title\": \"Software Engineer\",\n" +
                    "  \"post_content\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\",\n" +
                    "  \"company_address\": \"Da Nang City\",\n" +
                    "  \"salary\": 5500000,\n" +
                    "  \"status\": 1\n" +
                    "}");
            jsJob2 = new JSONObject("{\n" +
                    "  \"company_image\": \"http://www.microindustrialmart.com/wp-content/uploads/2015/12/parentcompany.png\",\n" +
                    "  \"company_name\": \"Enclave\",\n" +
                    "  \"post_title\": \"Software Engineer\",\n" +
                    "  \"post_content\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\",\n" +
                    "  \"company_address\": \"Da Nang City\",\n" +
                    "  \"salary\": 5500000,\n" +
                    "  \"status\": 2\n" +
                    "}");
            jsJob3 = new JSONObject("{\n" +
                    "  \"company_image\": \"http://www.microindustrialmart.com/wp-content/uploads/2015/12/parentcompany.png\",\n" +
                    "  \"company_name\": \"Enclave\",\n" +
                    "  \"post_title\": \"Software Engineer\",\n" +
                    "  \"post_content\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\",\n" +
                    "  \"company_address\": \"Da Nang City\",\n" +
                    "  \"salary\": 5500000,\n" +
                    "  \"status\": 3\n" +
                    "}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jobappliedListView = (ListView) rootView.findViewById(R.id.jobapplied_listview);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        jobappliedListView.addFooterView(footer);
        jobAppliedListViewItems = new ArrayList<JobAppliedListViewItem>();
        for(int i=0;i<14;i++){
            if(i%3==0)
                jobAppliedListViewItems.add(Utilities.getJobAppliedLVItemfrom(jsJob1));
            else if(i%2==0)
                jobAppliedListViewItems.add(Utilities.getJobAppliedLVItemfrom(jsJob2));
            else
                jobAppliedListViewItems.add(Utilities.getJobAppliedLVItemfrom(jsJob3));
        }
        jobAppliedListViewAdapterLoadInfinite = new JobAppliedListViewAdapterLoadInfinite(getActivity(), jobAppliedListViewItems, 8, 2);
        jobappliedListView.setAdapter(jobAppliedListViewAdapterLoadInfinite);
        progressBar.setVisibility((8 < jobAppliedListViewItems.size()) ? View.VISIBLE : View.GONE);
        jobappliedListView.setOnScrollListener(this);
        jobappliedListView.setOnItemClickListener(this);
        return rootView;
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Show JobApplied Information space:
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount == totalItemCount && !jobAppliedListViewAdapterLoadInfinite.endReached() && !hasCallback){ //check if we've reached the bottom
            Toast.makeText(getActivity().getApplicationContext(), "Loading", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(showMore, 1000);
            hasCallback = true;
        }
    }

    private boolean hasCallback;
    private Runnable showMore = new Runnable(){
        public void run(){
            boolean noMoreToShow = jobAppliedListViewAdapterLoadInfinite.showMore(); //show more views and find out if
            hasCallback = false;
            progressBar.setVisibility(noMoreToShow? View.GONE : View.VISIBLE);
        }
    };
}
