package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * Created by kyler on 15/03/2016.
 */
public class JobAppliedListViewAdapterLoadInfinite  extends ArrayAdapter<JobAppliedListViewItem>{
    private Activity context;
    public ArrayList<JobAppliedListViewItem> jobAppliedListViewItems;
    private int count;
    private int jobID;
    private int stepNumber;
    private int startCount;

    public JobAppliedListViewAdapterLoadInfinite(Activity context, ArrayList<JobAppliedListViewItem> jobAppliedListViewItems, int startCount, int stepNumber) {
        super(context, R.layout.applicant_jobapplied_listviewitem,jobAppliedListViewItems);
        this.context = context;
        this.jobAppliedListViewItems = jobAppliedListViewItems;
        this.startCount = Math.min(startCount, jobAppliedListViewItems.size()); //don't try to show more views than we have
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.applicant_jobapplied_listviewitem,null);
        }
        LinearLayout job_applied_header = (LinearLayout) view.findViewById(R.id.job_applied_header);
        ImageView job_applied_companyicon = (ImageView) view.findViewById(R.id.job_applied_companyicon);
        TextView job_applied_listviewitem_companyname = (TextView) view.findViewById(R.id.job_applied_listviewitem_companyname);
        TextView job_applied_listviewitem_posttitle = (TextView) view.findViewById(R.id.job_applied_listviewitem_posttitle);
        TextView job_applied_listviewitem_content = (TextView) view.findViewById(R.id.job_applied_listviewitem_content);
        TextView job_applied_listviewitem_status = (TextView) view.findViewById(R.id.job_applied_listviewitem_status);
        TextView job_applied_listviewitem_address = (TextView) view.findViewById(R.id.job_applied_listviewitem_address);
        TextView job_applied_listviewitem_salary = (TextView) view.findViewById(R.id.job_applied_listviewitem_salary);
        int status=jobAppliedListViewItems.get(i).getStatus();
        String jobStatus = null;
        switch (status){
            case 1:
                jobStatus = "Pending...";
                job_applied_header.setBackgroundResource(R.color.jobappliedpending);
                break;
            case 2:
                jobStatus = "Accepted";
                job_applied_header.setBackgroundResource(R.color.jobappliedaccepted);
                break;
            case 3:
                jobStatus = "Rejected";
                job_applied_header.setBackgroundResource(R.color.jobappliedreject);
                break;
            default:break;
        }
        Picasso.with(context).load(jobAppliedListViewItems.get(i).getCompanyImage()).into(job_applied_companyicon);
        job_applied_listviewitem_companyname.setText(jobAppliedListViewItems.get(i).getCompanyName());
        job_applied_listviewitem_posttitle.setText(jobAppliedListViewItems.get(i).getPostTitle());
        job_applied_listviewitem_content.setText(jobAppliedListViewItems.get(i).getJobOverview());
        job_applied_listviewitem_status.setText(jobStatus);
        job_applied_listviewitem_address.setText(jobAppliedListViewItems.get(i).getCompanyAddress());
        job_applied_listviewitem_salary.setText(jobAppliedListViewItems.get(i).getSalary()+"");
        ImageView jobappliedDelete = (ImageView) view.findViewById(R.id.jobapplied_delete);
        jobappliedDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobID=i;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Deleting Jobapplied space:
                                jobAppliedListViewItems.remove(jobID);
                                notifyDataSetChanged();
                                count--;
                            }
                        })
                        .setNegativeButton("No",null);
                builder.show();
            }
        });
        return view;
    }

    public boolean showMore(){
        if(count == jobAppliedListViewItems.size()) {
            return true;
        }else{
            count = Math.min(count + stepNumber, jobAppliedListViewItems.size()); //don't go past the end
            notifyDataSetChanged(); //the count size has changed, so notify the super of the change
            return endReached();
        }
    }

    public boolean endReached(){
        return count == jobAppliedListViewItems.size();
    }
}
