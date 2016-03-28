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
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;
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
public class MyResumeFragment extends Fragment implements ObservableScrollViewCallbacks,View.OnClickListener{
    private PersonalHistoryListViewAdapter educationListViewAdapter,experienceListViewAdapter,activityListViewAdapter,awardListViewAdapter;
    private ArrayList<PersonalHistoryListViewItem> educationListViewItems,experienceListViewItems,activityListViewItems,awardListViewItems;

    private NonScrollListView myresume_listview_education,myresume_listview_experience,myresume_listview_activity,myresume_listview_award;
    private ObservableScrollView myresumeFragment;
    private TextView myresumeEducation,myresumeExperience,myresumeActivity,myresumeAward;
    private ImageView myresumeUser,myresumeEditContact,myresumeEditAbout,myresumeAddEducation,myresumeAddExperience,myresumeAddActivity,myresumeAddAward,myresumeAddSkills,myresumeAddHobbies;
    private LinearLayout myresumeEditProfile;

    private JSONArray jsArrayEducation,jsArrayExperience,jsArrayActivity,jsArrayAward;
    private JSONArray jsArrayData = null;

    private String data="{\n" +
            "  \"personal_history\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"personal_history_title\": \"SUMMER INTERN1\",\n" +
            "      \"personal_history_detail\": \"aaaaaaaaaaaaaaaa\",\n" +
            "      \"personal_history_start\": \"2016-03-22T00:00:00+0000\",\n" +
            "      \"personal_history_end\": \"2016-02-19T00:00:00+0000\",\n" +
            "      \"personal_history_type_id\": 1,\n" +
            "      \"applicant_id\": 1,\n" +
            "      \"applicant\": {\n" +
            "        \"id\": 1,\n" +
            "        \"applicant_name\": \"Kyler\",\n" +
            "        \"applicant_phone\": \"123123123\",\n" +
            "        \"applicant_date_of_birth\": \"1993-07-20T00:00:00+0000\",\n" +
            "        \"applicant_place_of_birth\": \"Quang Nam\",\n" +
            "        \"applicant_address\": \"Da nang\",\n" +
            "        \"applicant_country\": \"Viet nam\",\n" +
            "        \"applicant_about\": \"Kyler abc\",\n" +
            "        \"applicant_marital\": 1,\n" +
            "        \"applicant_future_goal\": \"be Kyler\",\n" +
            "        \"applicant_website\": \"www.facebook.com/hkkhoa\",\n" +
            "        \"applicant_status\": 1,\n" +
            "        \"career_path_id\": 1\n" +
            "      },\n" +
            "      \"personal_history_types\": {\n" +
            "        \"id\": 1,\n" +
            "        \"personal_history_type_name\": \"Education\",\n" +
            "        \"personal_history_type_description\": \"Tell about your education\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"personal_history_title\": \"SUMMER INTERN2\",\n" +
            "      \"personal_history_detail\": \"aaaaaaaaaaaaaaaa\",\n" +
            "      \"personal_history_start\": \"2016-03-22T00:00:00+0000\",\n" +
            "      \"personal_history_end\": \"2016-02-19T00:00:00+0000\",\n" +
            "      \"personal_history_type_id\": 2,\n" +
            "      \"applicant_id\": 1,\n" +
            "      \"applicant\": {\n" +
            "        \"id\": 1,\n" +
            "        \"applicant_name\": \"Kyler\",\n" +
            "        \"applicant_phone\": \"123123123\",\n" +
            "        \"applicant_date_of_birth\": \"1993-07-20T00:00:00+0000\",\n" +
            "        \"applicant_place_of_birth\": \"Quang Nam\",\n" +
            "        \"applicant_address\": \"Da nang\",\n" +
            "        \"applicant_country\": \"Viet nam\",\n" +
            "        \"applicant_about\": \"Kyler abc\",\n" +
            "        \"applicant_marital\": 1,\n" +
            "        \"applicant_future_goal\": \"be Kyler\",\n" +
            "        \"applicant_website\": \"www.facebook.com/hkkhoa\",\n" +
            "        \"applicant_status\": 1,\n" +
            "        \"career_path_id\": 1\n" +
            "      },\n" +
            "      \"personal_history_types\": {\n" +
            "        \"id\": 2,\n" +
            "        \"personal_history_type_name\": \"Experience\",\n" +
            "        \"personal_history_type_description\": \"Tell about your experience\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"personal_history_title\": \"SUMMER INTERN3\",\n" +
            "      \"personal_history_detail\": \"aaaaaaaaaaaaaaaa\",\n" +
            "      \"personal_history_start\": \"2016-03-22T00:00:00+0000\",\n" +
            "      \"personal_history_end\": \"2016-02-19T00:00:00+0000\",\n" +
            "      \"personal_history_type_id\": 3,\n" +
            "      \"applicant_id\": 1,\n" +
            "      \"applicant\": {\n" +
            "        \"id\": 1,\n" +
            "        \"applicant_name\": \"Kyler\",\n" +
            "        \"applicant_phone\": \"123123123\",\n" +
            "        \"applicant_date_of_birth\": \"1993-07-20T00:00:00+0000\",\n" +
            "        \"applicant_place_of_birth\": \"Quang Nam\",\n" +
            "        \"applicant_address\": \"Da nang\",\n" +
            "        \"applicant_country\": \"Viet nam\",\n" +
            "        \"applicant_about\": \"Kyler abc\",\n" +
            "        \"applicant_marital\": 1,\n" +
            "        \"applicant_future_goal\": \"be Kyler\",\n" +
            "        \"applicant_website\": \"www.facebook.com/hkkhoa\",\n" +
            "        \"applicant_status\": 1,\n" +
            "        \"career_path_id\": 1\n" +
            "      },\n" +
            "      \"personal_history_types\": {\n" +
            "        \"id\": 3,\n" +
            "        \"personal_history_type_name\": \"Activity\",\n" +
            "        \"personal_history_type_description\": \"Tell about your activity\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"personal_history_title\": \"SUMMER INTERN4\",\n" +
            "      \"personal_history_detail\": \"aaaaaaaaaaaaaaaa\",\n" +
            "      \"personal_history_start\": \"2016-03-22T00:00:00+0000\",\n" +
            "      \"personal_history_end\": \"2016-02-19T00:00:00+0000\",\n" +
            "      \"personal_history_type_id\": 1,\n" +
            "      \"applicant_id\": 1,\n" +
            "      \"applicant\": {\n" +
            "        \"id\": 1,\n" +
            "        \"applicant_name\": \"Kyler\",\n" +
            "        \"applicant_phone\": \"123123123\",\n" +
            "        \"applicant_date_of_birth\": \"1993-07-20T00:00:00+0000\",\n" +
            "        \"applicant_place_of_birth\": \"Quang Nam\",\n" +
            "        \"applicant_address\": \"Da nang\",\n" +
            "        \"applicant_country\": \"Viet nam\",\n" +
            "        \"applicant_about\": \"Kyler abc\",\n" +
            "        \"applicant_marital\": 1,\n" +
            "        \"applicant_future_goal\": \"be Kyler\",\n" +
            "        \"applicant_website\": \"www.facebook.com/hkkhoa\",\n" +
            "        \"applicant_status\": 1,\n" +
            "        \"career_path_id\": 1\n" +
            "      },\n" +
            "      \"personal_history_types\": {\n" +
            "        \"id\": 1,\n" +
            "        \"personal_history_type_name\": \"Education\",\n" +
            "        \"personal_history_type_description\": \"Tell about your education\"\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"personal_history_title\": \"SUMMER INTERN5\",\n" +
            "      \"personal_history_detail\": \"aaaaaaaaaaaaaaaa\",\n" +
            "      \"personal_history_start\": \"2016-03-22T00:00:00+0000\",\n" +
            "      \"personal_history_end\": \"2016-02-19T00:00:00+0000\",\n" +
            "      \"personal_history_type_id\": 1,\n" +
            "      \"applicant_id\": 1,\n" +
            "      \"applicant\": {\n" +
            "        \"id\": 1,\n" +
            "        \"applicant_name\": \"Kyler\",\n" +
            "        \"applicant_phone\": \"123123123\",\n" +
            "        \"applicant_date_of_birth\": \"1993-07-20T00:00:00+0000\",\n" +
            "        \"applicant_place_of_birth\": \"Quang Nam\",\n" +
            "        \"applicant_address\": \"Da nang\",\n" +
            "        \"applicant_country\": \"Viet nam\",\n" +
            "        \"applicant_about\": \"Kyler abc\",\n" +
            "        \"applicant_marital\": 1,\n" +
            "        \"applicant_future_goal\": \"be Kyler\",\n" +
            "        \"applicant_website\": \"www.facebook.com/hkkhoa\",\n" +
            "        \"applicant_status\": 1,\n" +
            "        \"career_path_id\": 1\n" +
            "      },\n" +
            "      \"personal_history_types\": {\n" +
            "        \"id\": 1,\n" +
            "        \"personal_history_type_name\": \"Education\",\n" +
            "        \"personal_history_type_description\": \"Tell about your education\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public MyResumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.applicant_myresume_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Resumes");
        myresumeFragment = (ObservableScrollView) rootView.findViewById(R.id.myresume_fragment);
        myresumeUser = (ImageView) rootView.findViewById(R.id.myresume_user_image);
        Picasso.with(getActivity().getApplicationContext()).load("https://cdn4.iconfinder.com/data/icons/rcons-user/32/child_boy-128.png").into(myresumeUser);
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

        try {
            jsArrayData = new JSONArray(new JSONObject(data).getString("personal_history"));
            jsArrayEducation = getJsonArray(jsArrayData,1);
            jsArrayExperience = getJsonArray(jsArrayData,2);
            jsArrayActivity = getJsonArray(jsArrayData,3);
            jsArrayAward = getJsonArray(jsArrayData,4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loadPersonalHistory(jsArrayEducation,educationListViewItems,educationListViewAdapter,myresume_listview_education,myresumeEducation);
        loadPersonalHistory(jsArrayExperience,experienceListViewItems,experienceListViewAdapter,myresume_listview_experience,myresumeExperience);
        loadPersonalHistory(jsArrayActivity,activityListViewItems,activityListViewAdapter,myresume_listview_activity,myresumeActivity);
        loadPersonalHistory(jsArrayAward,awardListViewItems,awardListViewAdapter,myresume_listview_award,myresumeAward);
        myresumeEditProfile.setOnClickListener(this);
        myresumeEditContact.setOnClickListener(this);
        return rootView;
    }

    private JSONArray getJsonArray(JSONArray jsonArray,int condition){
        JSONArray result = new JSONArray();
        try{
            for(int i=0;i<jsonArray.length();i++){
                if(jsonArray.getJSONObject(i).getInt("personal_history_type_id")==condition){
                    result.put(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(result.length()>0)
            return result;
        else
            return null;
    }

    private void loadPersonalHistory(JSONArray jsonArray,ArrayList<PersonalHistoryListViewItem> listViewItems,PersonalHistoryListViewAdapter listViewAdapter,NonScrollListView nonScrollListView,TextView personalHistorytv){
        if(jsonArray!=null){
            personalHistorytv.setVisibility(View.GONE);
            listViewItems = new ArrayList<>();
            try{
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String historyTitle = jsonObject.getString("personal_history_title");
                    String historyDetail = jsonObject.getString("personal_history_detail");
                    String historyStart = Utilities.convertTime(jsonObject.getString("personal_history_start"));
                    String historyEnd = Utilities.convertTime(jsonObject.getString("personal_history_end"));
                    listViewItems.add(new PersonalHistoryListViewItem(historyTitle,historyDetail,historyStart,historyEnd));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listViewAdapter = new PersonalHistoryListViewAdapter(getActivity().getApplicationContext(),listViewItems);
            nonScrollListView.setAdapter(listViewAdapter);
        } else{
            personalHistorytv.setVisibility(View.VISIBLE);
        }
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
        switch (view.getId()){
            case R.id.myresume_editprofile:
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsArrayData.getJSONObject(1).getJSONObject("applicant");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditprofile",jsonObject.toString());
                break;
            case R.id.myresume_editcontact:
                jsonObject = null;
                try {
                    jsonObject = jsArrayData.getJSONObject(1).getJSONObject("applicant");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utilities.startFragWith(getActivity(),ChildApplicantActivity.class,"myresumeeditcontact",jsonObject.toString());
                break;
            case R.id.myresume_editabout:
                break;
            case R.id.myresume_addeducation:
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
