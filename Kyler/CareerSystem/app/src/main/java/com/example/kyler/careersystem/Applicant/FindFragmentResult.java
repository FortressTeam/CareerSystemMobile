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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.Customize.JobListViewAdapterLoadInfinite;
import com.example.kyler.careersystem.Applicant.Customize.JobListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.ViewHolder;
import com.example.kyler.careersystem.R;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Duck_Ky on 3/7/2016.
 */
public class FindFragmentResult extends Fragment implements AbsListView.OnScrollListener {
    private TextView findResult;
    private ObservableListView search_job_listview;

    private JobListViewAdapterLoadInfinite jobListViewAdapterLoadInfinite;
    private ArrayList<JobListViewItem> jobListViewItems;
    private Handler mHandler;
    private ProgressBar progressBar;

    public FindFragmentResult(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_find_job_result_fragment ,container,false);
        JSONObject jsJob1=null,jsJob2=null;
        try{
            jsJob1= new JSONObject("{ \"post_title\": \"Job xxxx\", \"post_content\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\", \"post_salary\": 300, \"post_image\": \"http://e08595.medialib.glogster.com/lornajane123/media/2e/2e77d71a7a7ac48f620e2180c18d381f19f2d250/working-togefa.jpg\", \"post_date\": \"12/12/2016\", \"post_status\": 1, \"category_name\": \"Information Technology\", \"company_name\": \"Enclave\" }");
            jsJob2= new JSONObject("{ \"post_title\": \"Job xxxx\", \"post_content\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\", \"post_salary\": 300, \"post_image\": \"http://a1.mzstatic.com/us/r30/Purple69/v4/93/9a/fd/939afdda-8637-2abb-52a5-ba1e9df8dfe6/icon175x175.jpeg\", \"post_date\": \"12/12/2016\", \"post_status\": 1, \"category_name\": \"Information Technology\", \"company_name\": \"Enclave\" }");
        }catch(JSONException e){e.printStackTrace();}
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Find Result");
        Bundle bundle = this.getArguments();
        String myresult = bundle.getString("findJobResult");
        final String link1= "http://e08595.medialib.glogster.com/lornajane123/media/2e/2e77d71a7a7ac48f620e2180c18d381f19f2d250/working-togefa.jpg";
        final String link2= "http://a1.mzstatic.com/us/r30/Purple69/v4/93/9a/fd/939afdda-8637-2abb-52a5-ba1e9df8dfe6/icon175x175.jpeg";
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.search_swipe);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity().getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                jobListViewItems.add(new JobListViewItem("Job " + new Random().nextInt(100)+ "xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
                swipeLayout.setRefreshing(false);
            }
        });
        mHandler = new Handler();
        search_job_listview = (ObservableListView) rootView.findViewById(R.id.search_job_listview);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        search_job_listview.addFooterView(footer);
        findResult = (TextView) rootView.findViewById(R.id.search_job_textview_result);
        findResult.setText(findResult.getText()+myresult+" :");
        jobListViewItems = new ArrayList<JobListViewItem>();
        jobListViewItems.add(new JobListViewItem("Job 1xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 2xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 3xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 4xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 5xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 6xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 7xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 8xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 9xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 10xxx","3 days", link2, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 11xxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. "));
        jobListViewItems.add(new JobListViewItem("Job 12xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 13xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 14xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 15xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 16xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 17xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 18xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 19xxxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 20xxxx","3 days", link2, "500 USD", "ABC", "Information Technology", " You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 21xxx","3 days", link2, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview."));
        jobListViewItems.add(new JobListViewItem("Job 22xxx","3 days", link1, "500 USD", "ABC", "Information Technology", "You just need to defiview. You just need to defiview. "));
        jobListViewAdapterLoadInfinite = new JobListViewAdapterLoadInfinite(getActivity().getApplicationContext(),jobListViewItems,10,5);
        search_job_listview.setAdapter(jobListViewAdapterLoadInfinite);
        search_job_listview.setOnScrollListener(this);
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
        if(firstVisibleItem + visibleItemCount == totalItemCount && !jobListViewAdapterLoadInfinite.endReached() && !hasCallback){ //check if we've reached the bottom
            mHandler.postDelayed(showMore, 300);
            hasCallback = true;
        }
    }

    private boolean hasCallback;
    private Runnable showMore = new Runnable(){
        public void run(){
            boolean noMoreToShow = jobListViewAdapterLoadInfinite.showMore(); //show more views and find out if
            hasCallback = false;
            progressBar.setVisibility(noMoreToShow? View.GONE : View.VISIBLE);
        }
    };
}
