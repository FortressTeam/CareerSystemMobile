package com.example.kyler.careersystem.Applicant.ChildFragments;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonObjectCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 09/03/2016.
 */
public class JobDetailFragment extends Fragment implements ObservableScrollViewCallbacks, Button.OnClickListener, View.OnLayoutChangeListener {
    private ObservableScrollView scrollView;
    private TextView jobDetailPostTitle, jobDetailPostSalary, jobDetailPostLocation, jobDetailPostDate, jobDetailPostContent;
    private TextView jobDetailCompanyName, jobDetailCompanyAddress, jobDetailCompanySize, jobDetailHiringManagerPhone;
    private ImageView companyLogo;
    private FloatingActionButton jobDetailFloatactionbutton,jobDetailFloatactionbuttonApply,jobDetailFloatactionbuttonFavorite;
    private ProgressDialog pDialog;
    private TextView jobDetailFloatactionApplytv, jobDetailFloatactionFavoritetv;

    private JSONObject jsonSendData;
    private Handler mHandler;
    private String url;
    private boolean fabPress = false;
    private boolean applied = false;
    private JSONObject jsData = null;

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
        jobDetailFloatactionbutton = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton);
        jobDetailFloatactionbuttonApply = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton_apply);
        jobDetailFloatactionbuttonFavorite = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton_favorite);
        jobDetailFloatactionApplytv = (TextView) rootView.findViewById(R.id.job_detail_apply_textview);
        jobDetailFloatactionFavoritetv = (TextView) rootView.findViewById(R.id.job_detail_favorite_textview);
        companyLogo.setOnClickListener(this);
        jobDetailFloatactionbutton.setOnClickListener(this);
        jobDetailFloatactionbuttonApply.setOnClickListener(this);
        jobDetailFloatactionbuttonFavorite.setOnClickListener(this);

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.job_detail_scrollview);
        jobDetailCompanyName = (TextView) rootView.findViewById(R.id.job_detail_company_name);
        jobDetailCompanyAddress = (TextView) rootView.findViewById(R.id.job_detail_company_address);
        jobDetailCompanySize = (TextView) rootView.findViewById(R.id.job_detail_company_size);
        jobDetailHiringManagerPhone = (TextView) rootView.findViewById(R.id.job_detail_hiringmanager_phone);

        jobDetailPostTitle = (TextView) rootView.findViewById(R.id.job_detail_post_title);
        jobDetailPostSalary = (TextView) rootView.findViewById(R.id.job_detail_post_salary);
        jobDetailPostLocation = (TextView) rootView.findViewById(R.id.job_detail_post_location);
        jobDetailPostDate = (TextView) rootView.findViewById(R.id.job_detail_post_date);
        jobDetailPostContent = (TextView) rootView.findViewById(R.id.job_detail_post_content);

//        pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading... ");
//        pDialog.setCancelable(false);
//        pDialog.show();
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
                                Posts post = new Posts(jsData);
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


//                try {
//                    JSONObject jsonObject = new GetJsonObject(pDialog, "post").execute(url).get();
//                    if (Utilities.checkConnect(jsonObject)) {
//                        Posts post = new Posts(jsonObject);
//                        jsonSendData = new JSONObject(jsonObject.getString("hiring_manager"));
//                        HiringManagers hiringManager = new HiringManagers(jsonSendData);
//                        jobDetailCompanyName.setText(hiringManager.getCompanyName());
//                        jobDetailCompanyAddress.setText(hiringManager.getCompanyAddress());
//                        jobDetailCompanySize.setText(hiringManager.getCompanySize() + "");
//                        jobDetailHiringManagerPhone.setText(hiringManager.getHiringManagerPhone());
//                        Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg + "/company_img/" + hiringManager.getCompanyLogo()).into(companyLogo);
//                        jobDetailPostTitle.setText(post.getPostTitle());
//                        if (post.getPostSalary() != 0) {
//                            jobDetailPostSalary.setText("VND " + post.getPostSalary());
//                        } else {
//                            jobDetailPostSalary.setText("Negotiable");
//                        }
//                        jobDetailPostLocation.setText(post.getPostLocation());
//                        jobDetailPostDate.setText(post.getPostDate());
//                        jobDetailPostContent.setText(Html.fromHtml(post.getPostContent()));
//                    } else {
//                        Toast.makeText(getActivity().getApplicationContext(), "Connection got problem!", Toast.LENGTH_SHORT).show();
//                        Utilities.displayViewApplicant(getActivity(), 404);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, 200);
        return rootView;
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
            case R.id.job_detail_company_logo:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "companydetail", jsonSendData.toString());
                break;
            case R.id.job_detail_floatactionbutton:
                fabPress=!fabPress;
                fabButtonListener(fabPress);
                break;
            case R.id.job_detail_floatactionbutton_apply:
                Toast.makeText(getActivity().getApplicationContext(), "Apply success!", Toast.LENGTH_SHORT).show();
                applied = true;
                jobDetailFloatactionbuttonApply.hide();
                jobDetailFloatactionApplytv.setVisibility(View.INVISIBLE);
                break;
            case R.id.job_detail_floatactionbutton_favorite:
                Toast.makeText(getActivity().getApplicationContext(), "FloatActionButton!", Toast.LENGTH_SHORT).show();
                jobDetailFloatactionbuttonFavorite.setImageResource(R.drawable.starfollow);
                break;
            default:
                break;
        }
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
            jobDetailFloatactionbuttonFavorite.hide();
            jobDetailFloatactionFavoritetv.setVisibility(View.INVISIBLE);
            if(!applied){
                jobDetailFloatactionbuttonApply.setVisibility(View.GONE);
                jobDetailFloatactionApplytv.setVisibility(View.GONE);
            }
        }
    }
}
