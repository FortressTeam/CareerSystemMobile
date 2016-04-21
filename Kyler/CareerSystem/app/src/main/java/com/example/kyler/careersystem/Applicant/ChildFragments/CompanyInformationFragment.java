package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Entities.Follow;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJsonCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompanyInformationFragment extends Fragment implements View.OnClickListener,ObservableScrollViewCallbacks,View.OnLayoutChangeListener{
    private ObservableScrollView scrollView;
    private ImageView companyImage;
    private TextView companyName,companySize,companyAdress,companyPhone,companyAbout,hiringManagerName;
    private Button followButton,unfollowButton;
    private LinearLayout linearLayoutFollow;
    private ArrayList<Follow> follows = Utilities.follows;
    private HiringManagers hiringManager;
    private boolean followed = false;
    private int exist = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_company_information_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Company Information");
        Bundle bundle = getArguments();
        JSONObject jsonreceive = null;
        try {
            jsonreceive = new JSONObject(bundle.getString("sendData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        scrollView = (ObservableScrollView) rootView.findViewById(R.id.company_information_scrollview);
        linearLayoutFollow = (LinearLayout) rootView.findViewById(R.id.company_information_subscribe_layout);
        hiringManagerName =(TextView) rootView.findViewById(R.id.company_information_hiringmanagername);
        companyImage = (ImageView) rootView.findViewById(R.id.company_information_image);
        companyName = (TextView) rootView.findViewById(R.id.company_information_companyname);
        companySize = (TextView) rootView.findViewById(R.id.company_information_companysize);
        companyAdress = (TextView) rootView.findViewById(R.id.company_information_companyaddress);
        companyPhone = (TextView) rootView.findViewById(R.id.company_information_companyphone);
        companyAbout = (TextView) rootView.findViewById(R.id.company_information_companyabout);
        followButton = (Button) rootView.findViewById(R.id.company_information_follow);
        unfollowButton = (Button) rootView.findViewById(R.id.company_information_unfollow);
        followButton.setOnClickListener(this);
        unfollowButton.setOnClickListener(this);
        companyAbout.addOnLayoutChangeListener(this);
        hiringManager = new HiringManagers(jsonreceive);
        Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg+"company_img/"+hiringManager.getCompanyLogo()).into(companyImage);
        hiringManagerName.setText(hiringManager.getHiringManagerName());
        companyName.setText(hiringManager.getCompanyName());
        companySize.setText(hiringManager.getCompanySize()+"");
        companyAdress.setText(hiringManager.getCompanyAddress());
        companyAbout.setText(hiringManager.getCompanyAbout());
        companyPhone.setText(hiringManager.getHiringManagerPhone());
        scrollView.setScrollViewCallbacks(this);
        for(int i=0;i<follows.size();i++){
            if(follows.get(i).getHiringManagerID() == hiringManager.getID()){
                exist = i;
                if(follows.get(i).isFollowHiringManager()) {
                    followed = true;
                    setButtonFollow(followed);
                    break;
                }
            }
        }
        return rootView;
    }


    private boolean canScroll(){
        View child = (View) scrollView.getChildAt(scrollView.getChildCount()-1);
        if (child != null) {
            int childHeight = (child).getHeight();
            return scrollView.getHeight() < childHeight + scrollView.getPaddingTop() + scrollView.getPaddingBottom();
        }
        return false;
    }

    private void setButtonFollow(boolean followed){
        if(followed){
            followButton.setVisibility(View.GONE);
            unfollowButton.setVisibility(View.VISIBLE);
        }else{
            followButton.setVisibility(View.VISIBLE);
            unfollowButton.setVisibility(View.GONE);
        }
    }

    private void followListener(final boolean follow){
        JSONObject jsSendData = new JSONObject();
        try {
            jsSendData.put("applicant_id",Utilities.applicants.getID());
            jsSendData.put("hiring_manager_id",hiringManager.getID());
            jsSendData.put("follow_hiring_manager",!follow);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JSONObject sendData = jsSendData;
        PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(sendData,getActivity()) {
            @Override
            public void receiveData(Object result) {
                if(Utilities.isCreateUpdateSuccess((JSONObject) result)){
                    followed = !followed;
                    if(exist != -1){
                        setButtonFollow(followed);
                        follows.get(exist).setFollowHiringManager(followed);
                    }else{
                        setButtonFollow(followed);
                        follows.add(new Follow(sendData));
                    }
                    Utilities.follows = follows;
                }else{
                    Toast.makeText(getActivity(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        };
        postDataWithJsonCallback.execute(UrlStatic.URLfollow);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.company_information_follow:
                followListener(followed);
                break;
            case R.id.company_information_unfollow:
                followListener(followed);
                break;
            default:break;
        }
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        View view = (View) scrollView.getChildAt(scrollView.getChildCount()-1);
        int diff = (view.getHeight()-(scrollView.getHeight()+scrollView.getScrollY()));
        if(diff <= 0){
            linearLayoutFollow.setVisibility(View.VISIBLE);
        }else
            linearLayoutFollow.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(canScroll())
            linearLayoutFollow.setVisibility(View.INVISIBLE);
    }
}
