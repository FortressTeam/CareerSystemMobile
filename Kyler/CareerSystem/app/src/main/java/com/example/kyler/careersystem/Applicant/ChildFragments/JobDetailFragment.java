package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Applicant.Customize.MyCVListViewAdapter;
import com.example.kyler.careersystem.Entities.ApplicantsFollowPosts;
import com.example.kyler.careersystem.Entities.CurriculumVitaes;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.WorkWithService.GetJsonObjectCallback;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJsonCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 09/03/2016.
 */
public class JobDetailFragment extends Fragment implements ObservableScrollViewCallbacks, Button.OnClickListener, View.OnLayoutChangeListener {
    private ObservableScrollView scrollView;
    private TextView jobDetailPostTitle, jobDetailPostSalary, jobDetailPostLocation, jobDetailPostDate, jobDetailPostContent;
    private TextView jobDetailCompanyName, jobDetailCompanyAddress, jobDetailCompanySize, jobDetailHiringManagerPhone;
    private ImageView companyLogo;
    private FloatingActionButton jobDetailFloatactionbutton,jobDetailFloatactionbuttonApply,jobDetailFloatactionbuttonFavorite;
    private LinearLayout jobDetailCompanyLayout;
    private TextView jobDetailFloatactionApplytv, jobDetailFloatactionFavoritetv, jobDetailStatus;

    private JSONObject jsonSendData;
    private ArrayList<ApplicantsFollowPosts> applicantsFollowPosts = Utilities.applicantsFollowPosts;
    private Handler mHandler;
    private String url;
    private boolean fabPress = false;
    private boolean applied = false,follow = false;
    private JSONObject jsData = null;
    private Posts post;
    private int exist = -1 ;
    private ArrayList<CurriculumVitaes> curriculumVitaes = new ArrayList<>();
    private Dialog myCVDialog;
    private ListView myCVListView;

    public JobDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHandler = new Handler();
        View rootView = inflater.inflate(R.layout.applicant_job_detail_fragment, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Job Detail");
        Bundle bundle = getArguments();
        url = UrlStatic.URLEditPost + bundle.getString("sendData") + ".json";

        companyLogo = (ImageView) rootView.findViewById(R.id.job_detail_company_logo);
        jobDetailCompanyLayout = (LinearLayout) rootView.findViewById(R.id.job_detail_company_layout);
        jobDetailFloatactionbutton = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton);
        jobDetailFloatactionbuttonApply = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton_apply);
        jobDetailFloatactionbuttonFavorite = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton_favorite);
        jobDetailFloatactionApplytv = (TextView) rootView.findViewById(R.id.job_detail_apply_textview);
        jobDetailFloatactionFavoritetv = (TextView) rootView.findViewById(R.id.job_detail_favorite_textview);
        jobDetailCompanyLayout.setOnClickListener(this);
        jobDetailFloatactionbutton.setOnClickListener(this);
        jobDetailFloatactionbuttonApply.setOnClickListener(this);
        jobDetailFloatactionbuttonFavorite.setOnClickListener(this);

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.job_detail_scrollview);
        jobDetailCompanyName = (TextView) rootView.findViewById(R.id.job_detail_company_name);
        jobDetailCompanyAddress = (TextView) rootView.findViewById(R.id.job_detail_company_address);
        jobDetailCompanySize = (TextView) rootView.findViewById(R.id.job_detail_company_size);
        jobDetailHiringManagerPhone = (TextView) rootView.findViewById(R.id.job_detail_hiringmanager_phone);
        createMyCVDialog();
        jobDetailPostTitle = (TextView) rootView.findViewById(R.id.job_detail_post_title);
        jobDetailPostSalary = (TextView) rootView.findViewById(R.id.job_detail_post_salary);
        jobDetailPostLocation = (TextView) rootView.findViewById(R.id.job_detail_post_location);
        jobDetailPostDate = (TextView) rootView.findViewById(R.id.job_detail_post_date);
        jobDetailPostContent = (TextView) rootView.findViewById(R.id.job_detail_post_content);
        jobDetailStatus = (TextView) rootView.findViewById(R.id.job_detail_status);
        scrollView.setScrollViewCallbacks(this);
        jobDetailPostContent.addOnLayoutChangeListener(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GetJsonObjectCallback getJsonObjectCallback = new GetJsonObjectCallback(getActivity(),"post") {
                    @Override
                    public void receiveData(Object result) {
                        try {
                            jsData = (JSONObject) result;
                            if (Utilities.checkConnect(jsData)) {
                                post = new Posts(jsData);
                                for(int i=0;i<applicantsFollowPosts.size();i++){
                                    if(applicantsFollowPosts.get(i).getPostID()==post.getID()){
                                        exist=i;
                                        if(applicantsFollowPosts.get(i).isFollowStatus())
                                        {
                                            follow = true;
                                            jobDetailFloatactionbuttonFavorite.setImageResource(R.drawable.starfollow);
                                            break;
                                        }
                                    }
                                }
                                jsonSendData = new JSONObject(jsData.getString("hiring_manager"));
                                HiringManagers hiringManager = new HiringManagers(jsonSendData);
                                jobDetailCompanyName.setText(hiringManager.getCompanyName());
                                jobDetailCompanyAddress.setText(hiringManager.getCompanyAddress());
                                jobDetailCompanySize.setText(hiringManager.getCompanySize() + "");
                                jobDetailHiringManagerPhone.setText(hiringManager.getHiringManagerPhone());
                                Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg + "/company_img/" + hiringManager.getCompanyLogo()).into(companyLogo);
                                jobDetailPostTitle.setText(post.getPostTitle());
                                if (post.getPostSalary() != 0) {
                                    jobDetailPostSalary.setText("VND " + post.getPostSalary());
                                } else {
                                    jobDetailPostSalary.setText("Negotiable");
                                }
                                jobDetailPostLocation.setText(post.getPostLocation());
                                jobDetailPostDate.setText(post.getPostDate());
                                jobDetailPostContent.setText(Html.fromHtml(post.getPostContent()));
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Connection got problem!", Toast.LENGTH_SHORT).show();
                                Utilities.displayViewApplicant(getActivity(), 404);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                getJsonObjectCallback.execute(url,null,null);

                GetJsonArrayCallback getJsonArrayCallback = new GetJsonArrayCallback("curriculumVitaes") {
                    @Override
                    public void receiveData(Object result) {
                        JSONArray jsCurriculumVitaes = (JSONArray) result;
                        for(int i=0;i<jsCurriculumVitaes.length();i++){
                            try {
                                curriculumVitaes.add(new CurriculumVitaes(jsCurriculumVitaes.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                getJsonArrayCallback.execute(UrlStatic.URLCurriculumVitaes+Utilities.applicants.getID());
            }
        }, 200);
        return rootView;
    }

    private void createMyCVDialog(){
        myCVDialog = new Dialog(getActivity());
        myCVDialog.setContentView(R.layout.job_detail_mycv_customdialog);
        myCVDialog.setTitle("My Curriculum Vitaes");
        myCVListView = (ListView) myCVDialog.findViewById(R.id.job_detail_mycvdialog_listview);
        Button cancel = (Button) myCVDialog.findViewById(R.id.job_detail_mycvdialog_cancel);
        MyCVListViewAdapter myCVListViewAdapter = new MyCVListViewAdapter(getActivity(),curriculumVitaes);
        myCVListView.setAdapter(myCVListViewAdapter);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCVDialog.dismiss();
            }
        });
        myCVListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject jsSendData = new JSONObject();
                try {
                    jsSendData.put("post_id", post.getID());
                    jsSendData.put("curriculum_vitae_id", curriculumVitaes.get(i).getID());
                    jsSendData.put("applied_cv_status", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(jsSendData, getActivity()) {
                    @Override
                    public void receiveData(Object result) {
                        JSONObject jsResult = (JSONObject) result;
                        if (Utilities.isCreateUpdateSuccess(jsResult)) {
                            Toast.makeText(getActivity().getApplicationContext(), "Apply success!", Toast.LENGTH_SHORT).show();
                            applied = true;
                            jobDetailFloatactionbuttonApply.hide();
                            jobDetailFloatactionApplytv.setVisibility(View.INVISIBLE);
                            myCVDialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                postDataWithJsonCallback.execute(UrlStatic.URLPostsHasCurriculumVitaes);
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
        int diff = (view.getHeight() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff <= 0) {
            fabButtonListener(true);
            fabPress=true;
        } else {
            fabButtonListener(false);
            fabPress=false;
        }
    }


    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.job_detail_company_layout:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "companydetail", jsonSendData.toString());
                break;
            case R.id.job_detail_floatactionbutton:
                fabPress=!fabPress;
                fabButtonListener(fabPress);
                break;
            case R.id.job_detail_floatactionbutton_apply:
                myCVDialog.show();
                break;
            case R.id.job_detail_floatactionbutton_favorite:
                followListener(follow);
                break;
            default:
                break;
        }
    }

    public void followListener(boolean fl){
        JSONObject jsSendData = new JSONObject();
        try {
            jsSendData.put("applicant_id",Utilities.applicants.getID());
            jsSendData.put("post_id",post.getID());
            jsSendData.put("follow_status",!fl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JSONObject sendData = jsSendData;
        PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(sendData,getActivity()) {
            @Override
            public void receiveData(Object result) {
                if(Utilities.isCreateUpdateSuccess((JSONObject)result)){
                    if(exist != -1) {
                        if (follow) {
                            jobDetailFloatactionbuttonFavorite.setImageResource(R.drawable.star);
                        } else {
                            jobDetailFloatactionbuttonFavorite.setImageResource(R.drawable.starfollow);
                        }
                        applicantsFollowPosts.get(exist).setFollowStatus(!follow);
                    }else{
                        applicantsFollowPosts.add(new ApplicantsFollowPosts((sendData)));
                        jobDetailFloatactionbuttonFavorite.setImageResource(R.drawable.starfollow);
                        exist = applicantsFollowPosts.size()-1;
                    }
                    Utilities.applicantsFollowPosts = applicantsFollowPosts;
                    follow = !follow;
                }else{
                    Toast.makeText(getActivity(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        };
        postDataWithJsonCallback.execute(UrlStatic.URLApplicantsFollowPosts);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
    }

    private void fabButtonListener(boolean press){
        if(press){
            jobDetailFloatactionbutton.setImageResource(R.drawable.closeicon);
            jobDetailFloatactionbuttonFavorite.show();
            jobDetailFloatactionFavoritetv.setVisibility(View.VISIBLE);
            if(!applied){
                jobDetailFloatactionbuttonApply.show();
                jobDetailFloatactionApplytv.setVisibility(View.VISIBLE);
            }
        }else{
            jobDetailFloatactionbutton.setImageResource(R.drawable.fabadd);
            jobDetailFloatactionbuttonFavorite.setVisibility(View.GONE);
            jobDetailFloatactionFavoritetv.setVisibility(View.INVISIBLE);
            if(!applied){
                jobDetailFloatactionbuttonApply.setVisibility(View.GONE);
                jobDetailFloatactionApplytv.setVisibility(View.GONE);
            }
        }
    }
}
