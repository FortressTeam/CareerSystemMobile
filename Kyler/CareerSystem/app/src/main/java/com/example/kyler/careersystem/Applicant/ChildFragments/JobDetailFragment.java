package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
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
import com.example.kyler.careersystem.WorkWithService.GetJsonObject;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by kyler on 09/03/2016.
 */
public class JobDetailFragment extends Fragment implements ObservableScrollViewCallbacks,Button.OnClickListener {
    private ObservableScrollView scrollView;
    private TextView jobDetailPostTitle,jobDetailPostSalary,jobDetailPostLocation,jobDetailPostDate,jobDetailPostContent;
    private TextView jobDetailCompanyName,jobDetailCompanyAddress,jobDetailCompanySize,jobDetailHiringManagerName,jobDetailHiringManagerPhone;
    private Button btApply;
    private ImageView companyLogo;
    private FloatingActionButton jobDetailFloatactionbuttonFavorite;

    private JSONObject jsonSendData;
    private Handler mHandler;
    private String url;

    public JobDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHandler = new Handler();
        View rootView = inflater.inflate(R.layout.applicant_job_detail_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Job Detail");
        Bundle bundle = getArguments();
        url = UrlStatic.URLPost+bundle.getString("sendData")+".json";

        companyLogo = (ImageView) rootView.findViewById(R.id.job_detail_company_logo);
        btApply = (Button) rootView.findViewById(R.id.job_detail_btapply);
        jobDetailFloatactionbuttonFavorite = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton_favorite);
        companyLogo.setOnClickListener(this);
        jobDetailFloatactionbuttonFavorite.setOnClickListener(this);

        btApply.setVisibility(View.VISIBLE);
        btApply.setOnClickListener(this);

        jobDetailCompanyName = (TextView) rootView.findViewById(R.id.job_detail_company_name);
        jobDetailCompanyAddress = (TextView) rootView.findViewById(R.id.job_detail_company_address);
        jobDetailCompanySize = (TextView) rootView.findViewById(R.id.job_detail_company_size);
        jobDetailHiringManagerName = (TextView) rootView.findViewById(R.id.job_detail_hiringmanager_name);
        jobDetailHiringManagerPhone = (TextView) rootView.findViewById(R.id.job_detail_hiringmanager_phone);

        jobDetailPostTitle = (TextView) rootView.findViewById(R.id.job_detail_post_title);
        jobDetailPostSalary = (TextView) rootView.findViewById(R.id.job_detail_post_salary);
        jobDetailPostLocation = (TextView) rootView.findViewById(R.id.job_detail_post_location);
        jobDetailPostDate = (TextView) rootView.findViewById(R.id.job_detail_post_date);
        jobDetailPostContent = (TextView) rootView.findViewById(R.id.job_detail_post_content);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new GetJsonObject(getActivity(),"post").execute(url).get();
                    if(Utilities.checkConnect(jsonObject)) {
                        Posts post = new Posts(jsonObject);
                        jsonSendData = new JSONObject(jsonObject.getString("hiring_manager"));
                        HiringManagers hiringManager = new HiringManagers(jsonSendData);
                        jobDetailCompanyName.setText(hiringManager.getCompanyName());
                        jobDetailCompanyAddress.setText(hiringManager.getCompanyAddress());
                        jobDetailCompanySize.setText(hiringManager.getCompanySize() + "");
                        jobDetailHiringManagerName.setText(hiringManager.getHiringManagerName());
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
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Connection got problem!", Toast.LENGTH_SHORT).show();
                        Utilities.displayView(getActivity(),404);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },200);
        scrollView = (ObservableScrollView) rootView.findViewById(R.id.job_detail_scrollview);
        scrollView.setScrollViewCallbacks(this);
        return rootView;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
//        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        if (ab == null) {
//            return;
//        }
//        if (scrollState == ScrollState.UP) {
//            if (ab.isShowing()) {
//                ab.hide();
//            }
//        } else if (scrollState == ScrollState.DOWN) {
//            if (!ab.isShowing()) {
//                ab.show();
//            }
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.job_detail_btapply:
                Toast.makeText(getActivity().getApplicationContext(), "You applied this job successfull!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.job_detail_floatactionbutton_favorite:
                Toast.makeText(getActivity().getApplicationContext(), "FloatActionButton!", Toast.LENGTH_SHORT).show();
                jobDetailFloatactionbuttonFavorite.setImageResource(R.drawable.starfollow);
                break;
            case R.id.job_detail_company_logo:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "companydetail", jsonSendData.toString());
                break;
            default:
                break;
        }
    }
}
