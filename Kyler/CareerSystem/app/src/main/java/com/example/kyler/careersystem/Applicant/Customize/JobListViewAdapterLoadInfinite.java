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

import com.example.kyler.careersystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kyler on 07/03/2016.
 */
public class JobListViewAdapterLoadInfinite extends ArrayAdapter<JobListViewItem> {
    private Context context;
    private ArrayList<JobListViewItem> jobListViewItems;
    private int count;
    private int stepNumber;
    private int startCount;

    public JobListViewAdapterLoadInfinite(Context context,  ArrayList<JobListViewItem> jobListViewItems, int startCount, int stepNumber) {
        super(context, R.layout.applicant_job_listviewitem,jobListViewItems);
        this.context = context;
        this.jobListViewItems = jobListViewItems;
        this.startCount = Math.min(startCount, jobListViewItems.size()); //don't try to show more views than we have
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    public void setJobListViewItems(ArrayList<JobListViewItem> jobListViewItems) {
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
            view = inflater.inflate(R.layout.applicant_job_listviewitem,null);
        }
        LinearLayout job_listviewitem_holderview = (LinearLayout) view.findViewById(R.id.job_listviewitem_holderview);
        TextView job_listviewitem_title = (TextView) view.findViewById(R.id.job_listviewitem_title);
        TextView job_listviewitem_title_time = (TextView) view.findViewById(R.id.job_listviewitem_title_time);
        ImageView job_listviewitem_image = (ImageView) view.findViewById(R.id.job_listviewitem_image);
        TextView job_listviewitem_salary = (TextView) view.findViewById(R.id.job_listviewitem_salary);
        TextView job_listviewitem_company = (TextView) view.findViewById(R.id.job_listviewitem_company);
        TextView job_listviewitem_major = (TextView) view.findViewById(R.id.job_listviewitem_major);
        TextView job_listviewitem_location = (TextView) view.findViewById(R.id.job_listviewitem_location);
        TextView job_listviewitem_postContent = (TextView) view.findViewById(R.id.job_listviewitem_postContent);
        int status=jobListViewItems.get(i).getStatus();
        if(status != -1){
            TextView job_applied_listviewitem_status = (TextView) view.findViewById(R.id.job_applied_listviewitem_status);
            job_applied_listviewitem_status.setVisibility(View.VISIBLE);
            String jobStatus = "";
            switch (status){
                case 0:
                    jobStatus = "Pending...";
                    job_applied_listviewitem_status.setBackgroundResource(R.drawable.customborderpending);
                    break;
                case 1:
                    jobStatus = "Accepted";
                    job_applied_listviewitem_status.setBackgroundResource(R.drawable.customborderaccepted);
                    break;
                case 2:
                    jobStatus = "Rejected";
                    job_applied_listviewitem_status.setBackgroundResource(R.drawable.customborderrejected);
                    break;
                default:break;
            }
            job_applied_listviewitem_status.setText(jobStatus);
        }
        else{
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) job_listviewitem_title.getLayoutParams();
            params.weight = 1;
            job_listviewitem_title.setLayoutParams(params);
        }
        job_listviewitem_title.setText(jobListViewItems.get(i).getTitle());
        job_listviewitem_title_time.setText(jobListViewItems.get(i).getTitleTime());
        Picasso.with(context).load(jobListViewItems.get(i).getImage()).into(job_listviewitem_image);
        job_listviewitem_salary.setText(jobListViewItems.get(i).getSalary());
        job_listviewitem_company.setText(jobListViewItems.get(i).getCompany());
        job_listviewitem_major.setText(jobListViewItems.get(i).getMajor());
        job_listviewitem_location.setText(jobListViewItems.get(i).getLocation());
        job_listviewitem_postContent.setText(Html.fromHtml(jobListViewItems.get(i).getPostContent()));
        ViewHolder holder = new ViewHolder();
        holder.header = job_listviewitem_holderview;
        view.setTag(holder);
        return view;
    }

    public boolean showMore(){
        if(count == jobListViewItems.size()) {
            return true;
        }else{
            count = Math.min(count + stepNumber, jobListViewItems.size()); //don't go past the end
            notifyDataSetChanged(); //the count size has changed, so notify the super of the change
            return endReached();
        }
    }

    public boolean endReached(){
        return count == jobListViewItems.size();
    }
}
