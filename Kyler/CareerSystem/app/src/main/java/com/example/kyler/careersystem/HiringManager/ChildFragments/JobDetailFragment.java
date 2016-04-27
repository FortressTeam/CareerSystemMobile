package com.example.kyler.careersystem.HiringManager.ChildFragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.Customize.NonScrollListView;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.Entities.PostsHasCurriculumVitaes;
import com.example.kyler.careersystem.HiringManager.ChildHiringManagerActivity;
import com.example.kyler.careersystem.HiringManager.customize.SubmittedCVListViewAdapter;
import com.example.kyler.careersystem.HiringManager.customize.SubmittedCVListViewItem;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonObjectCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 14/04/2016.
 */
public class JobDetailFragment extends Fragment implements ObservableScrollViewCallbacks {
    private TextView jobDetailPostTitle, jobDetailPostSalary, jobDetailPostLocation, jobDetailPostDate, jobDetailPostContent;
    private TextView jobDetailCompanyName, jobDetailCompanyAddress, jobDetailCompanySize, jobDetailHiringManagerPhone;
    private ImageView companyLogo;
    private FloatingActionButton jobDetailFloatactionbutton;
    private JSONObject receiveData;
    private Posts post;
    private HiringManagers hiringManager = Utilities.hiringManagers;
    private LinearLayout jobDetailCVSubmitLayout;
    private ListView jobDetailListCVSubmit;
    private LinearLayout jobDetailListCVHolder;
    private JSONObject jsPost;
    private ArrayList<SubmittedCVListViewItem> submittedCVListViewItems;
    private SubmittedCVListViewAdapter submittedCVListViewAdapter;
    private JSONArray curriculumVitaes;
    private Space spaceView;
    private ObservableScrollView jobDetailScrollView;
    private TextView jobDetailSubmitAlert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_job_detail_fragment, container, false);
        Bundle bundle = getArguments();
        try {
            receiveData = new JSONObject(bundle.getString("sendData"));
            post = new Posts(receiveData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jobDetailScrollView = (ObservableScrollView) rootView.findViewById(R.id.job_detail_scrollview);
        jobDetailCVSubmitLayout = (LinearLayout) rootView.findViewById(R.id.hiringmanager_listcv_submit_layout);
        jobDetailListCVSubmit = (ListView) rootView.findViewById(R.id.hiringmanager_listcv_submit);
        jobDetailListCVHolder = (LinearLayout) rootView.findViewById(R.id.hiringmanager_listcv_holder);
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
        spaceView = (Space) rootView.findViewById(R.id.job_detail_spaceview);
        jobDetailSubmitAlert = (TextView) rootView.findViewById(R.id.hiringmanager_listcv_submit_alert);
        spaceView.setVisibility(View.GONE);
        jobDetailCVSubmitLayout.setVisibility(View.VISIBLE);
        jobDetailScrollView.setScrollViewCallbacks(this);
        loadData();

        jobDetailFloatactionbutton = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton);
        jobDetailFloatactionbutton.setImageResource(R.drawable.editicon2);
        jobDetailFloatactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPost(post);
            }
        });

        GetJsonObjectCallback getJsonObjectCallback = new GetJsonObjectCallback(getActivity(),"post") {
            @Override
            public void receiveData(Object result) {
                jsPost = (JSONObject) result;
                loadListCVSubmit(jsPost);
            }
        };
        getJsonObjectCallback.execute(UrlStatic.URLEditPost + post.getID() + ".json");
        return rootView;
    }

    private int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private void loadListCVSubmit(JSONObject jsonObject){
        try {
            curriculumVitaes = jsonObject.getJSONArray("curriculum_vitaes");
            submittedCVListViewItems = new ArrayList<>();
            for(int i=0;i<curriculumVitaes.length();i++){
                int cvID = curriculumVitaes.getJSONObject(i).getInt("id");
                String cvName = curriculumVitaes.getJSONObject(i).getString("curriculum_vitae_name");
                int applicantID = curriculumVitaes.getJSONObject(i).getJSONObject("applicant").getInt("id");
                String applicantName = curriculumVitaes.getJSONObject(i).getJSONObject("applicant").getString("applicant_name");
                PostsHasCurriculumVitaes postsHasCurriculumVitae = new PostsHasCurriculumVitaes(curriculumVitaes.getJSONObject(i).getJSONObject("_joinData"));
                submittedCVListViewItems.add(new SubmittedCVListViewItem(applicantID,applicantName,cvID,cvName,postsHasCurriculumVitae));
            }
            if(submittedCVListViewItems.size()>0){
                submittedCVListViewAdapter = new SubmittedCVListViewAdapter(getActivity(),submittedCVListViewItems);
                jobDetailListCVSubmit.setAdapter(submittedCVListViewAdapter);
                jobDetailListCVSubmit.setVisibility(View.VISIBLE);
                jobDetailSubmitAlert.setVisibility(View.GONE);
                jobDetailListCVHolder.setLayoutParams(new LinearLayout.LayoutParams(jobDetailListCVHolder.getLayoutParams().width, dpToPx( submittedCVListViewItems.size() * 69 )));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            jsSendData.put("post_status",post.getPostStatus());
            jsSendData.put("category_id",post.getCategoryID());
            jsSendData.put("hiring_manager_id",post.getHiringManagerID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utilities.startFragWith(getActivity(), ChildHiringManagerActivity.class, "editpost", jsSendData.toString());
        getActivity().finish();
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        View view = (View) jobDetailScrollView.getChildAt(jobDetailScrollView.getChildCount()-1);
        int diff = (view.getHeight()-(jobDetailScrollView.getHeight()+jobDetailScrollView.getScrollY()));
        if (diff == 0) {
            jobDetailFloatactionbutton.hide();
        } else
            jobDetailFloatactionbutton.show();
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
