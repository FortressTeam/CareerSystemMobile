package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
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
import com.example.kyler.careersystem.Business.EntitiesCreating;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.GetDataFromService.GetJsonObject;
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
    private TextView jobDetailPostTitle,jobDetailPostSalary,jobDetailPostLocation,jobDetailPostDate,jobDetailPostContent,jobDetailPostMoreInfo;
    private TextView jobDetailCompanyName,jobDetailCompanyAddress,jobDetailCompanySize,jobDetailHiringManagerName,jobDetailHiringManagerPhone;
    private Button btApply;
    private ImageView companyLogo;
    private FloatingActionButton jobDetailFloatactionbuttonFavorite;

    private JSONObject jsonSendData;

    public JobDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_job_detail_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Job Detail");
        Bundle bundle = getArguments();
        String url = UrlStatic.URLtest1+bundle.getString("sendData")+".json";
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
        jobDetailPostMoreInfo= (TextView) rootView.findViewById(R.id.job_detail_post_moreinfo);

        try {
            JSONObject jsonObject = new GetJsonObject(getActivity(),"post").execute(url).get();
            Posts post = EntitiesCreating.createPost(jsonObject);
            jsonSendData = new JSONObject(jsonObject.getString("hiring_manager"));
            HiringManagers hiringManager = EntitiesCreating.createHiringManagers(jsonSendData);
            jobDetailCompanyName.setText(hiringManager.getCompanyName());
            jobDetailCompanyAddress.setText(hiringManager.getCompanyAddress());
            jobDetailCompanySize.setText(hiringManager.getCompanySize()+"");
            jobDetailHiringManagerName.setText(hiringManager.getHiringManagerName());
            jobDetailHiringManagerPhone.setText(hiringManager.getHiringManagerPhone());
            Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg+"/company_img/"+hiringManager.getCompanyLogo()).into(companyLogo);
            jobDetailPostTitle.setText(post.getPostTitle());
            jobDetailPostSalary.setText("VND "+post.getPostSalary());
            jobDetailPostLocation.setText(post.getPostLocation());
            jobDetailPostDate.setText(post.getPostDate());
            jobDetailPostContent.setText(Html.fromHtml(post.getPostContent()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        try {
//            jsJob1= new JSONObject("{ \"post_id\": \"1\"," +
//                    " \"post_title\": \"Job xxxx\"," +
//                    " \"post_required\": \"5 years experience\"," +
//                    " \"post_moreinfo\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\"," +
//                    "  \"post_content\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\",\n" +
//                    " \"post_salary\": 300," +
//                    " \"post_image\": \"https://cdn1.iconfinder.com/data/icons/avatar-3/512/Pilot-128.png\"," +
//                    " \"post_date\": \"12/12/2016\"," +
//                    " \"post_status\": 1," +
//                    " \"category_name\": \"Information Technology\"," +
//                    " \"company_overview\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\"," +
//                    " \"company_name\": \"Enclave\" }");
//            if(jsJob1.has("post_image"))
//                Picasso.with(getActivity().getApplicationContext()).load((jsJob1.getString("post_image"))).into(companyLogo);
//            if(jsJob1.has("company_overview"))
//                jobDetailCompanyInfo.setText(jsJob1.getString("company_overview"));
//            if(jsJob1.has("post_title"))
//                jobDetailPostTitle.setText(jsJob1.getString("post_title"));
//            if(jsJob1.has("post_salary"))
//                jobDetailPostSalary.setText(jsJob1.getString("post_salary"));
//            if(jsJob1.has("post_required"))
//                jobDetailPostLocation.setText(jsJob1.getString("post_required"));
//            if(jsJob1.has("post_content"))
//                jobDetailPostContent.setText(jsJob1.getString("post_content"));
//            if(jsJob1.has("post_moreinfo"))
//                jobDetailPostMoreInfo.setText(jsJob1.getString("post_moreinfo"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
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
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
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
