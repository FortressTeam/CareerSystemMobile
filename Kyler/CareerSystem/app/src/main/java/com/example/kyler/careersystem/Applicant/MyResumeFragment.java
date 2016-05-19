package com.example.kyler.careersystem.Applicant;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewAdapter;
import com.example.kyler.careersystem.Applicant.Customize.SkillListViewItem;
import com.example.kyler.careersystem.Bussiness.MyresumeController;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.ApplicantsHasSkills;
import com.example.kyler.careersystem.Entities.Hobbies;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.Entities.SkillTypes;
import com.example.kyler.careersystem.Entities.Skills;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.Helper.OrientationHepler;
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
 * Created by kyler on 10/03/2016.
 */
public class MyResumeFragment extends Fragment implements View.OnClickListener,ObservableScrollViewCallbacks {
    private PersonalHistoryListViewAdapter educationListViewAdapter,experienceListViewAdapter,activityListViewAdapter,awardListViewAdapter;
    private SkillListViewAdapter skillListViewAdapter;
    private ArrayList<SkillListViewItem> skillListViewItems;
    private HobbieListViewAdapter hobbieListViewAdapter;
    private ArrayList<HobbieListViewItem> hobbieListViewItems;

    private NonScrollListView myresume_listview_education,myresume_listview_experience,myresume_listview_activity,myresume_listview_award,myresume_listview_skill,myresume_listview_hobbie;
    private ObservableScrollView myresumeFragmentObservableScrollView;
    private TextView myresumeEducation,myresumeExperience,myresumeActivity,myresumeAward,myresumeSkill,myresumeHobbie,myresumeSex,myresumeHometown,myresumeBirthday,myresumePhone,myresumeEmail,myresumeAddress,myresumeAbout, myresumeObjective, myresumeBlank;
    private ImageView myresumeUserImage,myresumeEditContact,myresumeEditAbout,myresumeAddEducation,myresumeAddExperience,myresumeAddActivity,myresumeAddAward,myresumeAddSkills,myresumeAddHobbies, myresumeEditObjective;
    private LinearLayout myresumeEditProfile;
    private FloatingActionButton myresumeEditButton;

    private ArrayList<PersonalHistory> arrayEducation,arrayExperience,arrayActivity,arrayAward,personalHistories;
    private ArrayList<ApplicantsHasSkills> applicantsHasSkills;
    private ArrayList<Hobbies> hobbies,listhobbies;
    private ArrayList<SkillTypes> listSkillTypes;
    private ArrayList<Skills> skills,listskills;
    private JSONObject jsData;
    private JSONArray jshobbies, jsSkillTypes;
    private int applicantID=Utilities.applicants.getID();
    private MyresumeController myresumeController;

    private Applicants applicant;
    private Users user;
    private boolean hideButton=true;
    private Handler mHandler;
    private Dialog addHobbieDialog,addSkillDialog;
    private OrientationHepler orientationHepler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHandler = new Handler();
        orientationHepler = new OrientationHepler();
        myresumeController = new MyresumeController();
        View rootView=inflater.inflate(R.layout.applicant_myresume_fragment,container,false);
        myresumeFragmentObservableScrollView = (ObservableScrollView) rootView.findViewById(R.id.myresume_fragment_observablescrollview);
        myresumeFragmentObservableScrollView.setScrollViewCallbacks(this);
        myresumeBlank = (TextView) rootView.findViewById(R.id.myresume_blank);
        myresumeBlank.setVisibility(View.VISIBLE);

        myresumeEditButton = (FloatingActionButton) rootView.findViewById(R.id.myresume_editbutton);
        myresumeUserImage = (ImageView) rootView.findViewById(R.id.myresume_user_image);
        myresumeSex = (TextView) rootView.findViewById(R.id.myresume_sex);
        myresumeHometown = (TextView) rootView.findViewById(R.id.myresume_hometown);
        myresumeBirthday = (TextView) rootView.findViewById(R.id.myresume_birthday);
        myresumePhone = (TextView) rootView.findViewById(R.id.myresume_phone);
        myresumeEmail = (TextView) rootView.findViewById(R.id.myresume_email);
        myresumeAddress = (TextView) rootView.findViewById(R.id.myresume_address);
        myresumeAbout = (TextView) rootView.findViewById(R.id.myresume_about);
        myresumeObjective = (TextView) rootView.findViewById(R.id.myresume_objective);

        myresumeEditProfile = (LinearLayout) rootView.findViewById(R.id.myresume_editprofile);
        myresumeEditContact = (ImageView) rootView.findViewById(R.id.myresume_editcontact);
        myresumeEditAbout = (ImageView) rootView.findViewById(R.id.myresume_editabout);
        myresumeAddEducation = (ImageView) rootView.findViewById(R.id.myresume_addeducation);
        myresumeAddExperience = (ImageView) rootView.findViewById(R.id.myresume_addexperience);
        myresumeAddActivity = (ImageView) rootView.findViewById(R.id.myresume_addactivity);
        myresumeAddAward = (ImageView) rootView.findViewById(R.id.myresume_addaward);
        myresumeAddSkills= (ImageView) rootView.findViewById(R.id.myresume_addskill);
        myresumeAddHobbies = (ImageView) rootView.findViewById(R.id.myresume_addhobbie);
        myresumeEditObjective = (ImageView) rootView.findViewById(R.id.myresume_editobjective);
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
        myresumeEditButton.setVisibility(View.GONE);
        if(Utilities.jsApplicant == null || Utilities.jsArraySkillTypes == null || Utilities.jsArrayHobbies == null){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    GetJsonArrayCallback getHoobies = new GetJsonArrayCallback("hobbies") {
                        @Override
                        public void receiveData(Object result) {
                            jshobbies = (JSONArray) result;
                            Utilities.jsArrayHobbies = jshobbies;
                            if (Utilities.checkConnect(jshobbies)) {
                                listhobbies = myresumeController.getHobbies(jshobbies);
                            }
                        }
                    };
                    getHoobies.execute(UrlStatic.URLHobbies);

                    GetJsonArrayCallback getSkills = new GetJsonArrayCallback("skillTypes") {
                        @Override
                        public void receiveData(Object result) {
                            jsSkillTypes = (JSONArray) result;
                            Utilities.jsArraySkillTypes = jsSkillTypes;
                            if (Utilities.checkConnect(jsSkillTypes)) {
                                listSkillTypes = myresumeController.getSkillTypes(jsSkillTypes);
                            }
                        }
                    };
                    getSkills.execute(UrlStatic.URLSkillTypes);

                    GetJsonObjectCallback getJsonObjectCallback = new GetJsonObjectCallback(getActivity(), "applicant") {
                        @Override
                        public void receiveData(Object result) {
                            try {
                                jsData = (JSONObject) result;
                                Utilities.jsApplicant = jsData;
                                if (Utilities.checkConnect(jsData)) {
                                    applicant = myresumeController.getApplicant(jsData);
                                    user = myresumeController.getUser(jsData.getJSONObject("user"));
                                    personalHistories = myresumeController.getPersonalHistories(jsData.getJSONArray("personal_history"));
                                    skills = myresumeController.getSkills(jsData.getJSONArray("skills"));
                                    hobbies = myresumeController.getHobbies(jsData.getJSONArray("hobbies"));
                                    applicantsHasSkills = myresumeController.getApplicantsHasSkills(jsData.getJSONArray("skills"));
                                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(applicant.getApplicantName());
                                    loadInfo();
                                    myresumeBlank.setVisibility(View.GONE);
                                    myresumeEditButton.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Connection got problem!", Toast.LENGTH_SHORT).show();
                                    orientationHepler.displayViewApplicant(getActivity(), 404);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    getJsonObjectCallback.execute(UrlStatic.URLApplicant + applicantID + ".json");
                }
            }, 300);
        }
        else{
            try {
                jshobbies = Utilities.jsArrayHobbies;
                jsSkillTypes = Utilities.jsArraySkillTypes;
                jsData = Utilities.jsApplicant;
                listhobbies = myresumeController.getHobbies(jshobbies);
                listSkillTypes = myresumeController.getSkillTypes(jsSkillTypes);
                applicant = myresumeController.getApplicant(jsData);
                user = myresumeController.getUser(jsData.getJSONObject("user"));
                personalHistories = myresumeController.getPersonalHistories(jsData.getJSONArray("personal_history"));
                skills = myresumeController.getSkills(jsData.getJSONArray("skills"));
                hobbies = myresumeController.getHobbies(jsData.getJSONArray("hobbies"));
                applicantsHasSkills = myresumeController.getApplicantsHasSkills(jsData.getJSONArray("skills"));
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(applicant.getApplicantName());
                loadInfo();
                myresumeBlank.setVisibility(View.GONE);
                myresumeEditButton.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    private void createAddSkillDialog(final ArrayList<SkillTypes> listSkillTypes){
        addSkillDialog = new Dialog(getActivity());
        addSkillDialog.setContentView(R.layout.applicant_myresume_skill_customdialog);
        addSkillDialog.setTitle("Skills");
        adapterAddSkill = new SkillListViewAdapter(getActivity(),skillListViewItemsAddSkill,false,true);
        final Spinner addSkillMajorSpinner = (Spinner) addSkillDialog.findViewById(R.id.dialog_myresume_skill_major_spinner);
        addSkillSearch = (EditText) addSkillDialog.findViewById(R.id.dialog_myresume_skill_search);
        addSkillListView = (ListView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_listview);
        addSkillVotePlace = (LinearLayout) addSkillDialog.findViewById(R.id.dialog_myresume_skill_rateplace);
        ImageView star1 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar1);
        ImageView star2 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar2);
        ImageView star3 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar3);
        ImageView star4 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar4);
        ImageView star5 = (ImageView) addSkillDialog.findViewById(R.id.dialog_myresume_skill_ratestar5);
        final ImageView[] stars = {star1,star2,star3,star4,star5};
        View.OnClickListener rate = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.dialog_myresume_skill_ratestar1:
                        doRating(stars, 1);
                        addSkillChosenSkillListViewItem.setSkillLevel(1);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doSaveAddSkill(addSkillChosenSkillListViewItem);
                            }
                        },300);
                        break;
                    case R.id.dialog_myresume_skill_ratestar2:
                        doRating(stars, 2);
                        addSkillChosenSkillListViewItem.setSkillLevel(2);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doSaveAddSkill(addSkillChosenSkillListViewItem);
                            }
                        }, 300);
                        break;
                    case R.id.dialog_myresume_skill_ratestar3:
                        doRating(stars, 3);
                        addSkillChosenSkillListViewItem.setSkillLevel(3);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doSaveAddSkill(addSkillChosenSkillListViewItem);
                            }
                        }, 300);
                        break;
                    case R.id.dialog_myresume_skill_ratestar4:
                        doRating(stars, 4);
                        addSkillChosenSkillListViewItem.setSkillLevel(4);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doSaveAddSkill(addSkillChosenSkillListViewItem);
                            }
                        }, 300);
                        break;
                    case R.id.dialog_myresume_skill_ratestar5:
                        doRating(stars, 5);
                        addSkillChosenSkillListViewItem.setSkillLevel(5);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                doSaveAddSkill(addSkillChosenSkillListViewItem);
                            }
                        }, 300);
                        break;
                    default:
                        break;
                }
            }
        };
        for(int i=0;i<stars.length;i++){
            stars[i].setOnClickListener(rate);
        }
        ArrayList<String> arrStringSkillTypes = new ArrayList<>();
        for(int i=0;i<listSkillTypes.size();i++){
            arrStringSkillTypes.add(listSkillTypes.get(i).getSkillTypeName());
        }
        ArrayAdapter<String> adapterSkillTypes = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item,arrStringSkillTypes);
        addSkillMajorSpinner.setAdapter(adapterSkillTypes);
        addSkillMajorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                majorChanged=true;
                addSkillSearch.setText("");
                GetJsonArrayCallback getJsonArrayWithoutDialog = new GetJsonArrayCallback("skills") {
                    @Override
                    public void receiveData(Object result) {
                        JSONArray jsArrSkills = (JSONArray) result;
                        listskills = myresumeController.getSkills(jsArrSkills);
                        skillListViewItemsAddSkill = new ArrayList<SkillListViewItem>();
                        for (int j = 0; j < listskills.size(); j++) {
                            skillListViewItemsAddSkill.add(new SkillListViewItem(applicantID, listskills.get(j).getID(), listskills.get(j).getSkillName(), 1));
                        }
                        adapterAddSkill = new SkillListViewAdapter(getActivity(), skillListViewItemsAddSkill, true, true);
                        addSkillListView.setAdapter(adapterAddSkill);
                    }
                };
                getJsonArrayWithoutDialog.execute(UrlStatic.URLSkills + "?skill_type_id=" + listSkillTypes.get(i).getID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addSkillSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    skillListViewItemsAddSkillSearch = addSkillResultSearch(charSequence.toString());
                    adapterAddSkill = new SkillListViewAdapter(getActivity(), skillListViewItemsAddSkillSearch, true, true);
                    addSkillListView.setAdapter(adapterAddSkill);
                } else {
                    if(!majorChanged) {
                        adapterAddSkill = new SkillListViewAdapter(getActivity(), skillListViewItemsAddSkill, true, true);
                        addSkillListView.setAdapter(adapterAddSkill);
                    }else
                        majorChanged = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addSkillListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (addSkillSearch.getText().toString().equals("")) {
                    addSkillChosenSkillListViewItem = skillListViewItemsAddSkill.get(i);
                } else {
                    addSkillChosenSkillListViewItem = skillListViewItemsAddSkillSearch.get(i);
                }
                if(checkExist(addSkillChosenSkillListViewItem)){
                    addSkillSearch.setText(addSkillChosenSkillListViewItem.getSkillName());
                    addSkillSearch.setEnabled(false);
                    addSkillMajorSpinner.setEnabled(false);
                    addSkillListView.setVisibility(View.GONE);
                    addSkillVotePlace.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "You choosed it already", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button addSkillCancel = (Button) addSkillDialog.findViewById(R.id.dialog_myresume_skill_cancel);
        addSkillCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSkillDialog.cancel();
            }
        });
    }

    private ArrayList<SkillListViewItem> skillListViewItemsAddSkill,skillListViewItemsAddSkillSearch;
    private SkillListViewAdapter adapterAddSkill;
    private boolean majorChanged = false;
    private EditText addSkillSearch;
    private ListView addSkillListView;
    private LinearLayout addSkillVotePlace;
    private SkillListViewItem addSkillChosenSkillListViewItem;
    private ArrayList<SkillListViewItem> addSkillResultSearch(String input){
        ArrayList<SkillListViewItem> resultSearch = new ArrayList<>();
        if(!input.equals("")){
            for(int i=0;i<listskills.size();i++){
                String skillName = listskills.get(i).getSkillName();
                String inputSearch[] = skillName.split(" ");
                for(int j=0;j<inputSearch.length;j++) {
                    inputSearch[j] = inputSearch[j].length() >= input.length() ? inputSearch[j].substring(0, input.length()) : "";
                    if (inputSearch[j].equalsIgnoreCase(input)) {
                        resultSearch.add(new SkillListViewItem(applicantID,listskills.get(i).getID(),listskills.get(i).getSkillName(),1));
                        break;
                    }
                }
            }
        }
        return resultSearch;
    }
    private void doRating(ImageView[] stars,int rate){
        addSkillVotePlace.setEnabled(false);
        for(int i=0;i<rate;i++){
            stars[i].setImageResource(R.drawable.starfollow);
        }
    }


    private void createAddHobbieDialog(ArrayList<Hobbies> hobbies){
        addHobbieDialog = new Dialog(getActivity());
        addHobbieDialog.setContentView(R.layout.applicant_myresume_hobbie_customdialogadd);
        addHobbieDialog.setTitle("Hobbies");
        hobbieListViewItemsAddHobbie = new ArrayList<>();
        for(int i=0;i<hobbies.size();i++){
            hobbieListViewItemsAddHobbie.add(new HobbieListViewItem(applicantID,hobbies.get(i).getID(),hobbies.get(i).getHobbyName()));
        }
        adapterAddHobbie = new HobbieListViewAdapter(getActivity(),hobbieListViewItemsAddHobbie,true,true);
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
            }
        });
        addHobbieSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().equals("")){
                    hobbieListViewItemsAddHobbieSearch = addHobbieResultSearch(charSequence.toString());
                    adapterAddHobbie = new HobbieListViewAdapter(getActivity(),hobbieListViewItemsAddHobbieSearch,true,true);
                    adapterAddHobbie.notifyDataSetChanged();
                }else{
                    adapterAddHobbie = new HobbieListViewAdapter(getActivity(),hobbieListViewItemsAddHobbie,true,true);
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
    private ArrayList<HobbieListViewItem> addHobbieResultSearch(String input){
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
        if(personalHistories==null)
            return null;
        ArrayList<PersonalHistory> result = new ArrayList<>();
        for (int i = 0; i < personalHistories.size(); i++) {
            if (personalHistories.get(i).getPersonalHistoryTypeID() == condition)
                result.add(personalHistories.get(i));
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

    private void loadPersonalHistory(ArrayList<PersonalHistory> personalHistories,PersonalHistoryListViewAdapter listViewAdapter,NonScrollListView nonScrollListView,TextView personalHistorytv){
        if(personalHistories!=null){
            personalHistorytv.setVisibility(View.GONE);
            listViewAdapter = new PersonalHistoryListViewAdapter(getActivity(),personalHistories,hideButton);
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
        if(skillListViewItems!=null) {
            if(skillListViewItems.size()==0){
                myresumeSkill.setVisibility(View.VISIBLE);
            }
            skillListViewAdapter = new SkillListViewAdapter(getActivity(), skillListViewItems, false, flat);
            myresume_listview_skill.setAdapter(skillListViewAdapter);
        }
    }

    private void reloadApplicantHobbies(boolean flat){
        if(hobbieListViewItems!=null) {
            if(hobbieListViewItems.size()==0){
                myresumeHobbie.setVisibility(View.VISIBLE);
            }
            hobbieListViewAdapter = new HobbieListViewAdapter(getActivity(), hobbieListViewItems, false, flat);
            myresume_listview_hobbie.setAdapter(hobbieListViewAdapter);

        }
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
            hobbieListViewAdapter = new HobbieListViewAdapter(getActivity(),hobbieListViewItems,false,flat);
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
        myresumeObjective.setText(applicant.getApplicantObjective());
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
        myresumeEditObjective.setOnClickListener(this);
        setVisibleButton(hideButton);
        createAddHobbieDialog(listhobbies);
        createAddSkillDialog(listSkillTypes);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myresumeFragmentObservableScrollView.scrollTo(0, 0);
            }
        },100);
    }
    private void loadAllPersonalHistory(){
        loadPersonalHistory(arrayEducation,educationListViewAdapter,myresume_listview_education,myresumeEducation);
        loadPersonalHistory(arrayExperience,experienceListViewAdapter,myresume_listview_experience,myresumeExperience);
        loadPersonalHistory(arrayActivity,activityListViewAdapter,myresume_listview_activity,myresumeActivity);
        loadPersonalHistory(arrayAward,awardListViewAdapter,myresume_listview_award,myresumeAward);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        View view = (View) myresumeFragmentObservableScrollView.getChildAt(myresumeFragmentObservableScrollView.getChildCount()-1);
        int diff = (view.getHeight()-(myresumeFragmentObservableScrollView.getHeight()+myresumeFragmentObservableScrollView.getScrollY()));
        if (diff == 0) {
            myresumeEditButton.hide();
        } else
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
            myresumeEditProfile.setEnabled(false);
            myresumeEditContact.setVisibility(View.INVISIBLE);
            myresumeEditAbout.setVisibility(View.INVISIBLE);
            myresumeAddEducation.setVisibility(View.INVISIBLE);
            myresumeAddExperience.setVisibility(View.INVISIBLE);
            myresumeAddActivity.setVisibility(View.INVISIBLE);
            myresumeAddAward.setVisibility(View.INVISIBLE);
            myresumeAddSkills.setVisibility(View.INVISIBLE);
            myresumeAddHobbies.setVisibility(View.INVISIBLE);
            myresumeEditObjective.setVisibility(View.INVISIBLE);
        }else{
            myresumeEditProfile.setEnabled(true);
            myresumeEditContact.setVisibility(View.VISIBLE);
            myresumeEditAbout.setVisibility(View.VISIBLE);
            myresumeAddEducation.setVisibility(View.VISIBLE);
            myresumeAddExperience.setVisibility(View.VISIBLE);
            myresumeAddActivity.setVisibility(View.VISIBLE);
            myresumeAddAward.setVisibility(View.VISIBLE);
            myresumeAddSkills.setVisibility(View.VISIBLE);
            myresumeAddHobbies.setVisibility(View.VISIBLE);
            myresumeEditObjective.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        JSONObject jsSendData = jsData;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
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
                    },500);
                    hideButton = !hideButton;
                }
                else{
                    myresumeEditButton.hide();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myresumeEditButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.floatbutton)));
                            myresumeEditButton.setImageResource(R.drawable.editicon2);
                            myresumeEditButton.show();
                        }
                    },500);
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
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeeditprofile", jsEditProfile.toString());
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
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeeditcontact", jsEditContact.toString());
                break;
            case R.id.myresume_editabout:
                jsSendData.remove("user");
                jsSendData.remove("personal_history");
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeeditabout", jsSendData.toString());
                break;
            case R.id.myresume_addeducation:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeaddeducation", sendDataPersonalHistory(1).toString());
                break;
            case R.id.myresume_addexperience:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeaddexperience", sendDataPersonalHistory(2).toString());
                break;
            case R.id.myresume_addactivity:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeaddactivity", sendDataPersonalHistory(3).toString());
                break;
            case R.id.myresume_addaward:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeaddaward", sendDataPersonalHistory(4).toString());
                break;
            case R.id.myresume_addskill:
                createAddSkillDialog(listSkillTypes);
                addSkillDialog.show();
                addSkillDialog.getWindow().setLayout(width, (4 * height) / 5);
                break;
            case R.id.myresume_addhobbie:
                addHobbieDialog.show();
                addHobbieDialog.getWindow().setLayout(width, (4 * height) / 5);
                break;
            case R.id.myresume_editobjective:
                jsSendData.remove("user");
                jsSendData.remove("personal_history");
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "myresumeeditobjectivegoal", jsSendData.toString());
                break;
            default :
                break;
        }
    }

    private JSONObject sendDataPersonalHistory(int i){
        JSONObject result = new JSONObject();
        try {
            result.put("personalHistoryID",i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    private boolean checkExist(SkillListViewItem skillListViewItem){
        boolean checkExist=true;
        if(skillListViewItems==null)
            skillListViewItems = new ArrayList<>();
        for(int i=0;i<skillListViewItems.size();i++){
            if(skillListViewItems.get(i).getSkillID() == skillListViewItem.getSkillID()){
                checkExist = false;
                break;
            }
        }
        return checkExist;
    }
    private void doSaveAddSkill(final SkillListViewItem skillListViewItem){
        final JSONObject jsSend = new JSONObject();
        boolean checkExist=true;
        if(skillListViewItems!=null) {
            for (int i = 0; i < skillListViewItems.size(); i++) {
                if (skillListViewItems.get(i).getSkillID() == skillListViewItem.getSkillID()) {
                    checkExist = false;
                    break;
                }
            }
        }else{
            skillListViewItems = new ArrayList<>();
        }
        if(checkExist){
            try{
                jsSend.put("applicant_id",skillListViewItem.getApplicantID());
                jsSend.put("skill_id",skillListViewItem.getSkillID());
                jsSend.put("skill_level",skillListViewItem.getSkillLevel());
                PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(jsSend,getActivity()) {
                    @Override
                    public void receiveData(Object result) {
                        JSONObject jsResult = (JSONObject) result;
                        if(Utilities.isCreateUpdateSuccess(jsResult)){
                            Utilities.jsApplicant = null;
                            addSkillDialog.dismiss();
                            skillListViewItems.add(skillListViewItem);
                            skillListViewAdapter = new SkillListViewAdapter(getActivity(),skillListViewItems,false,hideButton);
                            skillListViewAdapter.notifyDataSetChanged();
                            myresume_listview_skill.setAdapter(skillListViewAdapter);
                            if(skillListViewItems.size()==1) {
                                myresumeSkill.setVisibility(View.GONE);
                            }
                            Toast.makeText(getActivity().getApplicationContext(), "Add Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                postDataWithJsonCallback.execute(UrlStatic.URLApplicantsHasSkills);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "You choosed it already", Toast.LENGTH_SHORT).show();
        }
    }

    private void doSaveAddHobbie(final HobbieListViewItem hobbieListViewItem){
        final JSONObject jsSend = new JSONObject();
        boolean checkExist=true;
        if(hobbieListViewItems!=null) {
            for (int i = 0; i < hobbieListViewItems.size(); i++) {
                if (hobbieListViewItems.get(i).getHobbieID() == hobbieListViewItem.getHobbieID()) {
                    checkExist = false;
                    break;
                }
            }
        }else{
            hobbieListViewItems = new ArrayList<>();
        }
        if(checkExist) {
            try {
                jsSend.put("applicant_id", applicantID);
                jsSend.put("hobby_id", hobbieListViewItem.getHobbieID());
                PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(jsSend,getActivity()) {
                    @Override
                    public void receiveData(Object result) {
                        JSONObject jsResult = (JSONObject) result;
                        if (Utilities.isCreateUpdateSuccess(jsResult)) {
                            Utilities.jsApplicant = null;
                            addHobbieDialog.dismiss();
                            hobbieListViewItems.add(hobbieListViewItem);
                            hobbieListViewAdapter = new HobbieListViewAdapter(getActivity(), hobbieListViewItems, false, hideButton);
                            hobbieListViewAdapter.notifyDataSetChanged();
                            myresume_listview_hobbie.setAdapter(hobbieListViewAdapter);
                            if(hobbieListViewItems.size()==1) {
                                myresumeHobbie.setVisibility(View.GONE);
                            }
                            Toast.makeText(getActivity().getApplicationContext(), "Add Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                postDataWithJsonCallback.execute(UrlStatic.URLApplicantsHasHobbies);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "You choosed it already", Toast.LENGTH_SHORT).show();
        }
    }
}
