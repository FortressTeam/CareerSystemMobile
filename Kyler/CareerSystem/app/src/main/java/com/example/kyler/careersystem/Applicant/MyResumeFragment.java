package com.example.kyler.careersystem.Applicant;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.kyler.careersystem.Applicant.Customize.NonScrollListView;
import com.example.kyler.careersystem.Applicant.Customize.PersonalHistoryListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.PersonalHistoryListViewItem;
import com.example.kyler.careersystem.Controller.Applicantcontroller;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.Hobbies;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.GetDataFromService.GetJsonObject;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by kyler on 10/03/2016.
 */
public class MyResumeFragment extends Fragment implements ObservableScrollViewCallbacks,View.OnClickListener{
    private PersonalHistoryListViewAdapter educationListViewAdapter,experienceListViewAdapter,activityListViewAdapter,awardListViewAdapter;
    private ArrayList<PersonalHistoryListViewItem> educationListViewItems,experienceListViewItems,activityListViewItems,awardListViewItems;

    private NonScrollListView myresume_listview_education,myresume_listview_experience,myresume_listview_activity,myresume_listview_award;
    private ObservableScrollView myresumeFragment;
    private TextView myresumeEducation,myresumeExperience,myresumeActivity,myresumeAward;
    private TextView myresumeName,myresumeSex,myresumeHometown,myresumeBirthday,myresumePhone,myresumeEmail,myresumeAddress,myresumeAbout,myresumeFutureGoal;
    private ImageView myresumeUserImage,myresumeEditContact,myresumeEditAbout,myresumeAddEducation,myresumeAddExperience,myresumeAddActivity,myresumeAddAward,myresumeAddSkills,myresumeAddHobbies;
    private LinearLayout myresumeEditProfile;

    private ArrayList<PersonalHistory> arrayEducation,arrayExperience,arrayActivity,arrayAward,personalHistories;
    private JSONObject jsData;
    private int applicantID=4;
    private Applicantcontroller applicantcontroller;

    private Applicants applicant;
    private Users user;
    private ArrayList<Hobbies> hobbies;
    private ArrayList<Skills> skills;

    public MyResumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.applicant_myresume_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Resumes");
        applicantcontroller = new Applicantcontroller();
        myresumeFragment = (ObservableScrollView) rootView.findViewById(R.id.myresume_fragment);

        myresumeUserImage = (ImageView) rootView.findViewById(R.id.myresume_user_image);
        myresumeName = (TextView) rootView.findViewById(R.id.myresume_name);
        myresumeSex = (TextView) rootView.findViewById(R.id.myresume_sex);
        myresumeHometown = (TextView) rootView.findViewById(R.id.myresume_hometown);
        myresumeBirthday = (TextView) rootView.findViewById(R.id.myresume_birthday);
        myresumePhone = (TextView) rootView.findViewById(R.id.myresume_phone);
        myresumeEmail = (TextView) rootView.findViewById(R.id.myresume_email);
        myresumeAddress = (TextView) rootView.findViewById(R.id.myresume_address);
        myresumeAbout = (TextView) rootView.findViewById(R.id.myresume_about);
        myresumeFutureGoal = (TextView) rootView.findViewById(R.id.myresume_futuregoal);

        myresumeEditProfile = (LinearLayout) rootView.findViewById(R.id.myresume_editprofile);
        myresumeEditContact = (ImageView) rootView.findViewById(R.id.myresume_editcontact);
        myresumeEditAbout = (ImageView) rootView.findViewById(R.id.myresume_editabout);
        myresumeAddEducation = (ImageView) rootView.findViewById(R.id.myresume_addeducation);
        myresumeAddExperience = (ImageView) rootView.findViewById(R.id.myresume_addexperience);
        myresumeAddActivity = (ImageView) rootView.findViewById(R.id.myresume_addactivity);
        myresumeAddAward = (ImageView) rootView.findViewById(R.id.myresume_addaward);
        myresumeAddSkills= (ImageView) rootView.findViewById(R.id.myresume_addskill);
        myresumeAddHobbies = (ImageView) rootView.findViewById(R.id.myresume_addhobbie);
        myresumeEducation = (TextView) rootView.findViewById(R.id.myresume_education);
        myresumeExperience = (TextView) rootView.findViewById(R.id.myresume_experience);
        myresumeActivity = (TextView) rootView.findViewById(R.id.myresume_activity);
        myresumeAward = (TextView) rootView.findViewById(R.id.myresume_award);

        myresume_listview_education = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_education);
        myresume_listview_experience = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_experience);
        myresume_listview_activity = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_activity);
        myresume_listview_award = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_award);

        try{
            jsData = new GetJsonObject(getActivity(),"applicant").execute(UrlStatic.URLApplicant+applicantID+".json").get();
            applicant = applicantcontroller.getApplicant(jsData);
            user = applicantcontroller.getUser(jsData.getJSONObject("user"));
            personalHistories = applicantcontroller.getPersonalHistories(jsData.getJSONArray("personal_history"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadInfo();
        return rootView;
    }

    private ArrayList<PersonalHistory> getListPersonalHistory(ArrayList<PersonalHistory> personalHistories,int condition){
        ArrayList<PersonalHistory> result = new ArrayList<>();
        for(int i=0;i<personalHistories.size();i++){
            if(personalHistories.get(i).getPersonalHistoryTypeID()==condition)
                result.add(personalHistories.get(i));
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    private void loadPersonalHistory(ArrayList<PersonalHistory> personalHistories,ArrayList<PersonalHistoryListViewItem> listViewItems,PersonalHistoryListViewAdapter listViewAdapter,NonScrollListView nonScrollListView,TextView personalHistorytv){
        if(personalHistories!=null){
            personalHistorytv.setVisibility(View.GONE);
            listViewItems = new ArrayList<>();
            for(int i=0;i<personalHistories.size();i++){
                String historyTitle = personalHistories.get(i).getPersonalHistoryTitle();
                String historyDetail = personalHistories.get(i).getPersonalHistoryDetail();
                String historyStart = personalHistories.get(i).getPersonalHistoryStart();
                String historyEnd = personalHistories.get(i).getPersonalHistoryEnd();
                listViewItems.add(new PersonalHistoryListViewItem(historyTitle,historyDetail,historyStart,historyEnd));
            }
            listViewAdapter = new PersonalHistoryListViewAdapter(getActivity().getApplicationContext(),listViewItems);
            nonScrollListView.setAdapter(listViewAdapter);
        } else{
            personalHistorytv.setVisibility(View.VISIBLE);
        }
    }

    private void loadInfo(){
        Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg+"user_img/"+user.getUserAvatar()).into(myresumeUserImage);
        myresumeName.setText(applicant.getApplicantName());
        if(applicant.isApplicantSex())
            myresumeSex.setText("Male");
        else
            myresumeSex.setText("Female");
        myresumeHometown.setText(applicant.getApplicantAddress());
        myresumePhone.setText(applicant.getApplicantPhone());
        myresumeEmail.setText(user.getUserEmail());
        myresumeAddress.setText(applicant.getApplicantAddress());
        myresumeAbout.setText(applicant.getApplicantAbout());
        myresumeFutureGoal.setText(applicant.getApplicantFutureGoal());
        arrayEducation = getListPersonalHistory(personalHistories, 1);
        arrayExperience = getListPersonalHistory(personalHistories,2);
        arrayActivity = getListPersonalHistory(personalHistories,3);
        arrayAward = getListPersonalHistory(personalHistories,4);
        loadPersonalHistory(arrayEducation,educationListViewItems,educationListViewAdapter,myresume_listview_education,myresumeEducation);
        loadPersonalHistory(arrayExperience,experienceListViewItems,experienceListViewAdapter,myresume_listview_experience,myresumeExperience);
        loadPersonalHistory(arrayActivity,activityListViewItems,activityListViewAdapter,myresume_listview_activity,myresumeActivity);
        loadPersonalHistory(arrayAward,awardListViewItems,awardListViewAdapter,myresume_listview_award,myresumeAward);
        myresumeEditProfile.setOnClickListener(this);
        myresumeEditProfile.setOnClickListener(this);
        myresumeEditContact.setOnClickListener(this);
        myresumeEditAbout.setOnClickListener(this);
        myresumeAddEducation.setOnClickListener(this);
        myresumeAddExperience.setOnClickListener(this);
        myresumeAddActivity.setOnClickListener(this);
        myresumeAddAward.setOnClickListener(this);
        myresumeAddSkills.setOnClickListener(this);
        myresumeAddHobbies.setOnClickListener(this);
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
        JSONObject jsSendData = jsData;
        switch (view.getId()){
            case R.id.myresume_editprofile:
                jsSendData.remove("user");
                jsSendData.remove("personal_history");
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditprofile",jsSendData.toString());
                break;
            case R.id.myresume_editcontact:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("applicant_phone_number",applicant.getApplicantPhone());
                    jsonObject.put("user_email",user.getUserEmail());
                    jsonObject.put("applicant_address",applicant.getApplicantAddress());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditcontact",jsonObject.toString());
                break;
            case R.id.myresume_editabout:
                jsSendData.remove("user");
                jsSendData.remove("personal_history");
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditabout",jsSendData.toString());
                break;
            case R.id.myresume_addeducation:
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeaddeducation","");
                break;
            case R.id.myresume_addexperience:
                break;
            case R.id.myresume_addactivity:
                break;
            case R.id.myresume_addaward:
                break;
            default :
                break;
        }
    }
}
