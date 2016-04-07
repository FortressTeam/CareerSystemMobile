package com.example.kyler.careersystem.Applicant;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kyler.careersystem.Applicant.Customize.HobbieListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.HobbieListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.NonScrollListView;
import com.example.kyler.careersystem.Applicant.Customize.PersonalHistoryListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.PersonalHistoryListViewItem;
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewItem;
import com.example.kyler.careersystem.Controller.MyresumeController;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.ApplicantsHasSkills;
import com.example.kyler.careersystem.Entities.Hobbies;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.Entities.SkillTypes;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.WorkWithService.GetJsonArray;
import com.example.kyler.careersystem.WorkWithService.GetJsonObject;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJson;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
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
    private HobbieListViewAdapter hobbieListViewAdapter;
    private ArrayList<HobbieListViewItem> hobbieListViewItems;

    private NonScrollListView myresume_listview_education,myresume_listview_experience,myresume_listview_activity,myresume_listview_award,myresume_listview_skill,myresume_listview_hobbie;
    private ObservableScrollView myresumeFragmentObservableScrollView;
    private TextView myresumeEducation,myresumeExperience,myresumeActivity,myresumeAward,myresumeSkill,myresumeHobbie;
    private TextView myresumeSex,myresumeHometown,myresumeBirthday,myresumePhone,myresumeEmail,myresumeAddress,myresumeAbout,myresumeFutureGoal;
    private ImageView myresumeUserImage,myresumeEditContact,myresumeEditAbout,myresumeAddEducation,myresumeAddExperience,myresumeAddActivity,myresumeAddAward,myresumeAddSkills,myresumeAddHobbies,myresumeEditFutureGoals;
    private LinearLayout myresumeEditProfile;
    private FloatingActionButton myresumeEditButton;

    private ArrayList<PersonalHistory> arrayEducation,arrayExperience,arrayActivity,arrayAward,personalHistories;
    private ArrayList<ApplicantsHasSkills> applicantsHasSkills;
    private ArrayList<Hobbies> hobbies,listhobbies;
    private ArrayList<SkillTypes> listSkillTypes;
    private ArrayList<Skills> skills,listskills;
    private JSONObject jsData;
    private int applicantID=4;
    private MyresumeController myresumeController;

    private Applicants applicant;
    private Users user;
    private boolean hideButton=true;
    private Handler mHandler;
    private ProgressDialog pDialog;
    private Dialog addHobbieDialog,addSkillDialog;

    public MyResumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHandler = new Handler();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading... ");
        pDialog.setCancelable(false);
        pDialog.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myresumeController = new MyresumeController();
                try {
                    jsData = new GetJsonObject(pDialog, "applicant").execute(UrlStatic.URLApplicant + applicantID + ".json").get();
                    JSONArray jshobbies = new GetJsonArray(pDialog,"hobbies").execute(UrlStatic.URLHobbies).get();
                    JSONArray jsSkillTypes = new GetJsonArray(pDialog, "skillTypes").execute(UrlStatic.URLSkillTypes).get();
                    if(Utilities.checkConnect(jsData)) {
                        applicant = myresumeController.getApplicant(jsData);
                        user = myresumeController.getUser(jsData.getJSONObject("user"));
                        personalHistories = myresumeController.getPersonalHistories(jsData.getJSONArray("personal_history"));
                        listSkillTypes = myresumeController.getSkillTypes(jsSkillTypes);
                        skills = myresumeController.getSkills(jsData.getJSONArray("skills"));
//                        listskills = myresumeController.getSkills();
                        hobbies = myresumeController.getHobbies(jsData.getJSONArray("hobbies"));
                        listhobbies = myresumeController.getHobbies(jshobbies);
                        applicantsHasSkills = myresumeController.getApplicantsHasSkills(jsData.getJSONArray("skills"));
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(applicant.getApplicantName());
                        loadInfo();
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(),"Connection got problem!",Toast.LENGTH_SHORT).show();
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
        myresumeHobbie = (TextView) rootView.findViewById(R.id.myresume_hobbie);
        myresume_listview_education = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_education);
        myresume_listview_experience = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_experience);
        myresume_listview_activity = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_activity);
        myresume_listview_award = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_award);
        myresume_listview_skill = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_skill);
        myresume_listview_hobbie = (NonScrollListView) rootView.findViewById(R.id.myresume_listview_hobbie);
        return rootView;
    }

    private void createAddSkillDialog(ArrayList<SkillTypes> listSkillTypes){
        addSkillDialog = new Dialog(getActivity());
        addSkillDialog.setContentView(R.layout.applicant_myresume_skill_customdialog);
        addSkillDialog.setTitle("Skills");
        skillListViewItemsAddSkill = new ArrayList<>();
        for(int i=0;i<skills.size();i++){
            skillListViewItemsAddSkill.add(new SkillListViewItem(applicantID,skills.get(i).getID(),skills.get(i).getSkillName(),1));
        }
        adapterAddSkill = new SkillListViewAdapter(getActivity(),skillListViewItemsAddSkill,false,true);
        Spinner addSkillMajorSpinner = (Spinner) addSkillDialog.findViewById(R.id.dialog_myresume_skill_major_spinner);
        addSkillSearch = (EditText) addSkillDialog.findViewById(R.id.dialog_myresume_skill_search);
        addSkillListView = (ListView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_listview);
        LinearLayout votePlace = (LinearLayout) addSkillDialog.findViewById(R.id.dialog_myresume_skill_rateplace);
        ImageView star1 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar1);
        ImageView star2 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar2);
        ImageView star3 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar3);
        ImageView star4 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar4);
        ImageView star5 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar5);
        View.OnClickListener rate = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.dialog_myresume_skill_ratestar1:
                        break;
                    case R.id.dialog_myresume_skill_ratestar2:
                        break;
                    case R.id.dialog_myresume_skill_ratestar3:
                        break;
                    case R.id.dialog_myresume_skill_ratestar4:
                        break;
                    case R.id.dialog_myresume_skill_ratestar5:
                        break;
                    default:
                        break;
                }
            }
        };
        Button addSkillCancel = (Button) addSkillDialog.findViewById(R.id.dialog_myresume_skill_cancel);

        addSkillListView.setAdapter(adapterAddSkill);
        addSkillCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSkillDialog.dismiss();
            }
        });

    }

    private ArrayList<SkillListViewItem> skillListViewItemsAddSkill,skillListViewItemsAddSkillSearch;
    private SkillListViewAdapter adapterAddSkill;
    private EditText addSkillSearch;
    private ListView addSkillListView;


    private void createAddHobbieDialog(ArrayList<Hobbies> hobbies){
        addHobbieDialog = new Dialog(getActivity());
        addHobbieDialog.setContentView(R.layout.applicant_myresume_hobbie_customdialogadd);
        addHobbieDialog.setTitle("Hobbies");
        hobbieListViewItemsAddHobbie = new ArrayList<>();
        for(int i=0;i<hobbies.size();i++){
            hobbieListViewItemsAddHobbie.add(new HobbieListViewItem(applicantID,hobbies.get(i).getID(),hobbies.get(i).getHobbyName()));
        }
        adapterAddHobbie = new HobbieListViewAdapter(getActivity(),hobbieListViewItemsAddHobbie,false,true);
        addHobbieSearch = (EditText) addHobbieDialog.findViewById(R.id.dialog_myresume_hobbie_search);
        addHobbieListView = (ListView) addHobbieDialog.findViewById(R.id.dialog_myresume_hobbie_listview);
        addHobbieListView.setAdapter(adapterAddHobbie);
        Button addHobbieCancel = (Button) addHobbieDialog.findViewById(R.id.dialog_myresume_hobbie_cancel);
        addHobbieCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addHobbieDialog.dismiss();
            }
        });
        addHobbieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(addHobbieSearch.getText().toString().equals(""))
                    doSaveAddHobbie(hobbieListViewItemsAddHobbie.get(i));
                else
                    doSaveAddHobbie(hobbieListViewItemsAddHobbieSearch.get(i));
                myresume_listview_hobbie.setAdapter(hobbieListViewAdapter);
            }
        });
        addHobbieSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    hobbieListViewItemsAddHobbieSearch = resultSearch(charSequence.toString());
                    adapterAddHobbie = new HobbieListViewAdapter(getActivity(),hobbieListViewItemsAddHobbieSearch,false,true);
                    adapterAddHobbie.notifyDataSetChanged();
                }else{
                    adapterAddHobbie = new HobbieListViewAdapter(getActivity(),hobbieListViewItemsAddHobbie,false,true);
                    adapterAddHobbie.notifyDataSetChanged();
                }
                addHobbieListView.setAdapter(adapterAddHobbie);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private EditText addHobbieSearch;
    private HobbieListViewAdapter adapterAddHobbie;
    private ArrayList<HobbieListViewItem> hobbieListViewItemsAddHobbie,hobbieListViewItemsAddHobbieSearch;
    private ListView addHobbieListView;
    private ArrayList<HobbieListViewItem> resultSearch(String input){
        ArrayList<HobbieListViewItem> resultSearch = new ArrayList<>();
        if(!input.equals("")){
            for(int i=0;i<listhobbies.size();i++){
                String hobbyName = listhobbies.get(i).getHobbyName();
                String inputSearch[] = hobbyName.split(" ");
                for(int j=0;j<inputSearch.length;j++) {
                    inputSearch[j] = inputSearch[j].length() >= input.length() ? inputSearch[j].substring(0, input.length()) : "";
                    if (inputSearch[j].equalsIgnoreCase(input)) {
                        resultSearch.add(new HobbieListViewItem(applicantID, listhobbies.get(i).getID(), hobbyName));
                        break;
                    }
                }
            }
        }
        return resultSearch;
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
            skillListViewAdapter = new SkillListViewAdapter(getActivity(),skillListViewItems,false,flat);
            myresume_listview_skill.setAdapter(skillListViewAdapter);
        } else{
            myresumeSkill.setVisibility(View.VISIBLE);
        }
    }

    private void reloadApplicantSkills(boolean flat){
        skillListViewAdapter = new SkillListViewAdapter(getActivity(),skillListViewItems,false,flat);
        myresume_listview_skill.setAdapter(skillListViewAdapter);
    }

    private void reloadApplicantHobbies(boolean flat){
        hobbieListViewAdapter = new HobbieListViewAdapter(getActivity(),hobbieListViewItems,true,flat);
        myresume_listview_hobbie.setAdapter(hobbieListViewAdapter);
    }
    private void loadApplicantHobbies(boolean flat){
        if(hobbies!=null){
            myresumeHobbie.setVisibility(View.GONE);
            hobbieListViewItems = new ArrayList<>();
            for(int i=0;i<hobbies.size();i++){
                int hobbieID = hobbies.get(i).getID();
                String hobbieName = hobbies.get(i).getHobbyName();
                hobbieListViewItems.add(new HobbieListViewItem(applicantID,hobbieID,hobbieName));
            }
            hobbieListViewAdapter = new HobbieListViewAdapter(getActivity(),hobbieListViewItems,true,flat);
            myresume_listview_hobbie.setAdapter(hobbieListViewAdapter);
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
        loadApplicantHobbies(hideButton);
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
        createAddHobbieDialog(listhobbies);
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
                reloadApplicantSkills(hideButton);
                reloadApplicantHobbies(hideButton);
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
                addHobbieDialog.show();
                break;
            case R.id.myresume_editfuturegoal:
                jsSendData.remove("user");
                jsSendData.remove("personal_history");
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditfuturegoal",jsSendData.toString());
                break;
            case R.id.dialog_myresume_hobbie_save:

                break;
            default :
                break;
        }
    }

    private void doSaveAddHobbie(HobbieListViewItem hobbieListViewItem){
        JSONObject jsSend = new JSONObject();
        boolean check=true;
        for(int i=0;i<hobbieListViewItems.size();i++){
            if(hobbieListViewItems.get(i).getHobbieID() == hobbieListViewItem.getHobbieID()) {
                check = false;
                return;
            }
        }
        if(check) {
            try {
                jsSend.put("applicant_id", applicantID);
                jsSend.put("hobby_id", hobbieListViewItem.getHobbieID());
                JSONObject result = new PostDataWithJson(jsSend, getActivity()).execute(UrlStatic.URLApplicantsHasHobbies).get();
                if (Utilities.isCreateUpdateSuccess(result)) {
                    addHobbieDialog.dismiss();
                    hobbieListViewItems.add(hobbieListViewItem);
                    hobbieListViewAdapter = new HobbieListViewAdapter(getActivity(), hobbieListViewItems, true, hideButton);
                    hobbieListViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity().getApplicationContext(), "Add Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "You choosed it already", Toast.LENGTH_SHORT).show();
        }
    }
}
