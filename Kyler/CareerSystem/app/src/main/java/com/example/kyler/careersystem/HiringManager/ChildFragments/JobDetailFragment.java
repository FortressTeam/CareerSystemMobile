package com.example.kyler.careersystem.HiringManager.ChildFragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.HiringManager.ChildHiringManagerActivity;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 14/04/2016.
 */
public class JobDetailFragment extends Fragment {
    private TextView jobDetailPostTitle, jobDetailPostSalary, jobDetailPostLocation, jobDetailPostDate, jobDetailPostContent;
    private TextView jobDetailCompanyName, jobDetailCompanyAddress, jobDetailCompanySize, jobDetailHiringManagerPhone;
    private ImageView companyLogo;
    private FloatingActionButton jobDetailFloatactionbutton;
    private JSONObject receiveData;
    private Posts post;
    private HiringManagers hiringManager = Utilities.hiringManagers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_job_detail_fragment,container,false);
        Bundle bundle = getArguments();
        try {
            receiveData = new JSONObject(bundle.getString("sendData"));
            post = new Posts(receiveData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        companyLogo = (ImageView) rootView.findViewById(R.id.job_detail_company_logo);
        jobDetailCompanyName = (TextView) rootView.findViewById(R.id.job_detail_company_name);
        jobDetailCompanyAddress = (TextView) rootView.findViewById(R.id.job_detail_company_address);
        jobDetailCompanySize = (TextView) rootView.findViewById(R.id.job_detail_company_size);
        jobDetailHiringManagerPhone = (TextView) rootView.findViewById(R.id.job_detail_hiringmanager_phone);
        jobDetailPostTitle = (TextView) rootView.findViewById(R.id.job_detail_post_title);
        jobDetailPostSalary = (TextView) rootView.findViewById(R.id.job_detail_post_salary);
        jobDetailPostLocation = (TextView) rootView.findViewById(R.id.job_detail_post_location);
        jobDetailPostDate = (TextView) rootView.findViewById(R.id.job_detail_post_date);
        jobDetailPostContent = (TextView) rootView.findViewById(R.id.job_detail_post_content);
        loadData();

        jobDetailFloatactionbutton = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton);
        jobDetailFloatactionbutton.setImageResource(R.drawable.editicon2);
        jobDetailFloatactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPost(post);
            }
        });
        return rootView;
    }

    private void loadData(){
        Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg + "/company_img/" + hiringManager.getCompanyLogo()).into(companyLogo);
        jobDetailCompanyName.setText(hiringManager.getCompanyName());
        jobDetailCompanyAddress.setText(hiringManager.getCompanyAddress());
        jobDetailCompanySize.setText(hiringManager.getCompanySize() + "");
        jobDetailHiringManagerPhone.setText(hiringManager.getHiringManagerPhone());

        jobDetailPostTitle.setText(post.getPostTitle());
        if (post.getPostSalary() != 0) {
            jobDetailPostSalary.setText("VND " + post.getPostSalary());
        } else {
            jobDetailPostSalary.setText("Negotiable");
        }
        jobDetailPostLocation.setText(post.getPostLocation());
        jobDetailPostDate.setText(post.getPostDate());
        jobDetailPostContent.setText(Html.fromHtml(post.getPostContent()));
    }

    private void editPost(Posts post){
        JSONObject jsSendData = new JSONObject();
        try {
            jsSendData.put("id",post.getID());
            jsSendData.put("post_title",post.getPostTitle());
            jsSendData.put("post_content",post.getPostContent());
            jsSendData.put("post_salary",post.getPostSalary());
            jsSendData.put("post_location",post.getPostLocation());
            jsSendData.put("post_date", Utilities.convertTimePost(post.getPostDate()));
            jsSendData.put("post_status",post.isPostStatus());
            jsSendData.put("category_id",post.getCategoryID());
            jsSendData.put("hiring_manager_id",post.getHiringManagerID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utilities.startFragWith(getActivity(), ChildHiringManagerActivity.class, "editpost", jsSendData.toString());
    }
}
