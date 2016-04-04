package com.example.kyler.careersystem.Applicant;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kyler.careersystem.Applicant.Customize.NonScrollListView;
import com.example.kyler.careersystem.Applicant.Customize.PersonalHistoryListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.PersonalHistoryListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewItem;
import com.example.kyler.careersystem.Controller.Applicantcontroller;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.ApplicantsHasSkills;
import com.example.kyler.careersystem.Entities.Hobbies;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.WorkWithService.GetJsonObject;
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
public class MyResumeFragment extends Fragment implements View.OnClickListener,ObservableScrollViewCallbacks{
    private PersonalHistoryListViewAdapter educationListViewAdapter,experienceListViewAdapter,activityListViewAdapter,awardListViewAdapter;
    private ArrayList<PersonalHistoryListViewItem> educationListViewItems,experienceListViewItems,activityListViewItems,awardListViewItems;
    private SkillListViewAdapter skillListViewAdapter;
    private ArrayList<SkillListViewItem> skillListViewItems;

    private NonScrollListView myresume_listview_education,myresume_listview_experience,myresume_listview_activity,myresume_listview_award,myresume_listview_skill,myresume_listview_hobbie;
    private ObservableScrollView myresumeFragmentObservableScrollView;
    private TextView myresumeEducation,myresumeExperience,myresumeActivity,myresumeAward,myresumeSkill,myresumeHobbie;
    private TextView myresumeSex,myresumeHometown,myresumeBirthday,myresumePhone,myresumeEmail,myresumeAddress,myresumeAbout,myresumeFutureGoal;
    private ImageView myresumeUserImage,myresumeEditContact,myresumeEditAbout,myresumeAddEducation,myresumeAddExperience,myresumeAddActivity,myresumeAddAward,myresumeAddSkills,myresumeAddHobbies,myresumeEditFutureGoals;
    private LinearLayout myresumeEditProfile;
    private FloatingActionButton myresumeEditButton;

    private ArrayList<PersonalHistory> arrayEducation,arrayExperience,arrayActivity,arrayAward,personalHistories;
    private ArrayList<ApplicantsHasSkills> applicantsHasSkills;
    private ArrayList<Hobbies> hobbies;
    private ArrayList<Skills> skills;
    private JSONObject jsData;
    private int applicantID=4;
    private Applicantcontroller applicantcontroller;

    private Applicants applicant;
    private Users user;
    private boolean hideButton=true;
    private Handler mHandler;

    public MyResumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                applicantcontroller = new Applicantcontroller();
                try {
                    jsData = new GetJsonObject(getActivity(), "applicant").execute(UrlStatic.URLApplicant + applicantID + ".json").get();
                    if(Utilities.checkConnect(jsData)) {
                        applicant = applicantcontroller.getApplicant(jsData);
                        user = applicantcontroller.getUser(jsData.getJSONObject("user"));
                        personalHistories = applicantcontroller.getPersonalHistories(jsData.getJSONArray("personal_history"));
                        skills = applicantcontroller.getSkills(jsData.getJSONArray("skills"));
                        applicantsHasSkills = applicantcontroller.getApplicantsHasSkills(jsData.getJSONArray("skills"));
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(applicant.getApplicantName());
                        loadInfo();
                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),"Connection got problem!",Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 300);
        View rootView=inflater.inflate(R.layout.applicant_myresume_fragment,container,false);
        myresumeFragmentObservableScrollView = (ObservableScrollView) rootView.findViewById(R.id.myresume_fragment_observablescrollview);
        myresumeFragmentObservableScrollView.setScrollViewCallbacks(this);

        myresumeEditButton = (FloatingActionButton) rootView.findViewById(R.id.myresume_editbutton);
        myresumeUserImage = (ImageView) rootView.findViewById(R.id.myresume_user_image);
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
        myresumeEditFutureGoals = (ImageView) rootView.findViewById(R.id.myresume_editfuturegoal);
        myresumeEducation = (TextView) rootView.findViewById(R.id.myresume_education);
        myresumeExperience = (TextView) rootView.findViewById(R.id.myresume_experience);
        myresumeActivity = (TextView) rootView.findViewById(R.id.myresume_activity);
        myresumeAward = (TextView) rootView.findViewById(R.id.myresume_award);
        myresumeSkill = (TextView) rootView.findViewById(R.id.myresume_skill);

        myresume_listview_education = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_education);
        myresume_listview_experience = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_experience);
        myresume_listview_activity = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_activity);
        myresume_listview_award = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_award);
        myresume_listview_skill = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_skill);
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
            listViewAdapter = new PersonalHistoryListViewAdapter(getActivity().getApplicationContext(),listViewItems,hideButton);
            nonScrollListView.setAdapter(listViewAdapter);
        } else{
            personalHistorytv.setVisibility(View.VISIBLE);
        }
    }

    private void loadApplicantSkills(boolean flat){
        if(skills!=null){
            myresumeSkill.setVisibility(View.GONE);
            skillListViewItems = new ArrayList<>();
            for(int i=0;i<skills.size();i++){
                int skillID = skills.get(i).getID();
                String skillName = skills.get(i).getSkillName();
                int skillLevel = applicantsHasSkills.get(i).getSkillLevel();
                skillListViewItems.add(new SkillListViewItem(applicantID,skillID,skillName,skillLevel));
            }
            skillListViewAdapter = new SkillListViewAdapter(getActivity(),skillListViewItems,flat);
            myresume_listview_skill.setAdapter(skillListViewAdapter);
        }
        else{
            myresumeSkill.setVisibility(View.VISIBLE);
        }
    }


    private void loadInfo(){
        myresumeEditButton.setOnClickListener(this);
        Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg+"user_img/"+user.getUserAvatar()).into(myresumeUserImage);
        if(applicant.isApplicantSex())
            myresumeSex.setText("Male");
        else
            myresumeSex.setText("Female");
        myresumeHometown.setText(applicant.getApplicantAddress());
        myresumeBirthday.setText(applicant.getApplicantDateOfBirth());
        myresumePhone.setText(applicant.getApplicantPhone());
        myresumeEmail.setText(user.getUserEmail());
        myresumeAddress.setText(applicant.getApplicantAddress());
        myresumeAbout.setText(applicant.getApplicantAbout());
        myresumeFutureGoal.setText(applicant.getApplicantFutureGoal());
        arrayEducation = getListPersonalHistory(personalHistories, 1);
        arrayExperience = getListPersonalHistory(personalHistories,2);
        arrayActivity = getListPersonalHistory(personalHistories,3);
        arrayAward = getListPersonalHistory(personalHistories,4);
        loadAllPersonalHistory();
        loadApplicantSkills(hideButton);
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
        myresumeEditFutureGoals.setOnClickListener(this);
        setVisibleButton(hideButton);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myresumeFragmentObservableScrollView.scrollTo(0, 0);
            }
        },100);
    }
    private void loadAllPersonalHistory(){
        loadPersonalHistory(arrayEducation,educationListViewItems,educationListViewAdapter,myresume_listview_education,myresumeEducation);
        loadPersonalHistory(arrayExperience,experienceListViewItems,experienceListViewAdapter,myresume_listview_experience,myresumeExperience);
        loadPersonalHistory(arrayActivity,activityListViewItems,activityListViewAdapter,myresume_listview_activity,myresumeActivity);
        loadPersonalHistory(arrayAward,awardListViewItems,awardListViewAdapter,myresume_listview_award,myresumeAward);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        View view = (View) myresumeFragmentObservableScrollView.getChildAt(myresumeFragmentObservableScrollView.getChildCount()-1);
        int diff = (view.getHeight()-(myresumeFragmentObservableScrollView.getHeight()+myresumeFragmentObservableScrollView.getScrollY()));
        if(diff == 0){
            myresumeEditButton.hide();
        }else
            myresumeEditButton.show();

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    private void setVisibleButton(boolean flat){
        if(flat){
            myresumeEditContact.setVisibility(View.INVISIBLE);
            myresumeEditAbout.setVisibility(View.INVISIBLE);
            myresumeAddEducation.setVisibility(View.INVISIBLE);
            myresumeAddExperience.setVisibility(View.INVISIBLE);
            myresumeAddActivity.setVisibility(View.INVISIBLE);
            myresumeAddAward.setVisibility(View.INVISIBLE);
            myresumeAddSkills.setVisibility(View.INVISIBLE);
            myresumeAddHobbies.setVisibility(View.INVISIBLE);
            myresumeEditFutureGoals.setVisibility(View.INVISIBLE);
        }else{
            myresumeEditContact.setVisibility(View.VISIBLE);
            myresumeEditAbout.setVisibility(View.VISIBLE);
            myresumeAddEducation.setVisibility(View.VISIBLE);
            myresumeAddExperience.setVisibility(View.VISIBLE);
            myresumeAddActivity.setVisibility(View.VISIBLE);
            myresumeAddAward.setVisibility(View.VISIBLE);
            myresumeAddSkills.setVisibility(View.VISIBLE);
            myresumeAddHobbies.setVisibility(View.VISIBLE);
            myresumeEditFutureGoals.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        JSONObject jsSendData = jsData;
        switch (view.getId()){
            case R.id.myresume_editbutton:
                Handler handler = new Handler();
                if(hideButton) {
                    myresumeEditButton.hide();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myresumeEditButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.floatbuttonclose)));
                            myresumeEditButton.setImageResource(R.drawable.closeicon);
                            myresumeEditButton.show();
                        }
                    },400);
                    hideButton = !hideButton;
                }
                else{
                    myresumeEditButton.hide();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myresumeEditButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.floatbutton)));
                            myresumeEditButton.setImageResource(R.drawable.iconedit2);
                            myresumeEditButton.show();
                        }
                    },400);
                    hideButton=!hideButton;
                }
                loadApplicantSkills(hideButton);
                loadAllPersonalHistory();
                setVisibleButton(hideButton);
                break;
            case R.id.myresume_editprofile:
                JSONObject jsEditProfile = new JSONObject();
                try{
                    jsEditProfile.put("applicant_name",applicant.getApplicantName());
                    jsEditProfile.put("applicant_sex",applicant.isApplicantSex());
                    jsEditProfile.put("applicant_address",applicant.getApplicantAddress());
                    jsEditProfile.put("applicant_date_of_birth",applicant.getApplicantDateOfBirth());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditprofile",jsEditProfile.toString());
                break;
            case R.id.myresume_editcontact:
                JSONObject jsEditContact = new JSONObject();
                try {
                    jsEditContact.put("applicant_phone_number",applicant.getApplicantPhone());
                    jsEditContact.put("user_email",user.getUserEmail());
                    jsEditContact.put("applicant_address",applicant.getApplicantAddress());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditcontact",jsEditContact.toString());
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
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeaddexperience","");
                break;
            case R.id.myresume_addactivity:
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeaddactivity","");
                break;
            case R.id.myresume_addaward:
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeaddaward","");
                break;
            case R.id.myresume_addskill:
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeaddskill","");
                break;
            case R.id.myresume_addhobbie:

                break;
            case R.id.myresume_editfuturegoal:
                jsSendData.remove("user");
                jsSendData.remove("personal_history");
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditfuturegoal",jsSendData.toString());
                break;
            default :
                break;
        }
    }
}
