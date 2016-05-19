package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kyler on 13/05/2016.
 */
public class JobFollowAdapterLoadInfinite extends ArrayAdapter<Posts> {
    private Context context;
    private ArrayList<Posts> jobListViewItems;
    private int count;
    private int stepNumber;
    private int startCount;

    public JobFollowAdapterLoadInfinite(Context context,  ArrayList<Posts> jobListViewItems, int startCount, int stepNumber) {
        super(context, R.layout.applicant_follow_job_listviewitem,jobListViewItems);
        this.context = context;
        this.jobListViewItems = jobListViewItems;
        this.startCount = Math.min(startCount, jobListViewItems.size()); //don't try to show more views than we have
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    public void setJobListViewItems(ArrayList<Posts> jobListViewItems) {
        this.jobListViewItems = jobListViewItems;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.applicant_follow_job_listviewitem,null);
        }
        TextView job_follow_listviewitem_title = (TextView) view.findViewById(R.id.job_follow_listviewitem_title);
        TextView job_follow_listviewitem_title_time = (TextView) view.findViewById(R.id.job_follow_listviewitem_title_time);
        TextView job_follow_listviewitem_salary = (TextView) view.findViewById(R.id.job_follow_listviewitem_salary);
        TextView job_follow_listviewitem_location = (TextView) view.findViewById(R.id.job_follow_listviewitem_location);
        TextView job_follow_listviewitem_postContent = (TextView) view.findViewById(R.id.job_follow_listviewitem_postContent);
        job_follow_listviewitem_title.setText(jobListViewItems.get(i).getPostTitle());
        job_follow_listviewitem_title_time.setText(jobListViewItems.get(i).getPostDate());
        job_follow_listviewitem_salary.setText(jobListViewItems.get(i).getPostSalary()+"");
        job_follow_listviewitem_location.setText(jobListViewItems.get(i).getPostLocation());
        job_follow_listviewitem_postContent.setText(Html.fromHtml(jobListViewItems.get(i).getPostContent()));
        return view;
    }

    public void showMore(){
        if(!(count == jobListViewItems.size())) {
            count = Math.min(count + stepNumber, jobListViewItems.size()); //don't go past the end
            notifyDataSetChanged(); //the count size has changed, so notify the super of the change
        }
    }

    public boolean endReached(){
        return count == jobListViewItems.size();
    }
}
