package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Entities.PersonalHistory;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 28/03/2016.
 */
public class PersonalHistoryListViewAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<PersonalHistory> personalHistoryListViewItems;
    private boolean hideButton;

    public PersonalHistoryListViewAdapter(Activity context, ArrayList<PersonalHistory> personalHistoryListViewItems, boolean hideButton) {
        this.context = context;
        this.personalHistoryListViewItems = personalHistoryListViewItems;
        this.hideButton = hideButton;
    }

    @Override
    public int getCount() {
        return personalHistoryListViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return personalHistoryListViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.applicant_myresume_personal_history_listviewitem,null);
        }
        TextView myresume_history_title = (TextView) view.findViewById(R.id.myresume_history_title_listviewitem);
        TextView myresume_history_detail = (TextView) view.findViewById(R.id.myresume_history_detail_listviewitem);
        TextView myresume_history_time = (TextView) view.findViewById(R.id.myresume_history_time_listviewitem);
        ImageView myresume_history_edit = (ImageView) view.findViewById(R.id.myresumer_history_edit_listviewitem);
        myresume_history_title.setText(personalHistoryListViewItems.get(i).getPersonalHistoryTitle());
        myresume_history_detail.setText(personalHistoryListViewItems.get(i).getPersonalHistoryDetail());
        if(personalHistoryListViewItems.get(i).getPersonalHistoryEnd()!=null) {
            String startEvent = Utilities.convertTimeShorten(personalHistoryListViewItems.get(i).getPersonalHistoryStart().toString());
            String EndEvent = Utilities.convertTimeShorten(personalHistoryListViewItems.get(i).getPersonalHistoryEnd().toString());
            myresume_history_time.setText(startEvent + " - " + EndEvent);
        }else{
            if(personalHistoryListViewItems.get(i).getPersonalHistoryTypeID()!=4) {
                String startEvent = Utilities.convertTimeShorten(personalHistoryListViewItems.get(i).getPersonalHistoryStart().toString());
                myresume_history_time.setText(startEvent + " - Now");
            }else {
                String timeAward = Utilities.convertTimeShorten(personalHistoryListViewItems.get(i).getPersonalHistoryStart().toString());
                myresume_history_time.setText(timeAward);
            }
        }
        if(hideButton)
            myresume_history_edit.setVisibility(View.INVISIBLE);
        else
            myresume_history_edit.setVisibility(View.VISIBLE);
        myresume_history_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do edit thing in here;
                JSONObject sendData = new JSONObject();
                JSONObject jsPersonalHistory = new JSONObject();
                try {
                    jsPersonalHistory.put("id",personalHistoryListViewItems.get(i).getID());
                    jsPersonalHistory.put("personal_history_title", personalHistoryListViewItems.get(i).getPersonalHistoryTitle());
                    jsPersonalHistory.put("personal_history_detail",personalHistoryListViewItems.get(i).getPersonalHistoryDetail());
                    jsPersonalHistory.put("personal_history_start", Utilities.convertTimePost(personalHistoryListViewItems.get(i).getPersonalHistoryStart()));
                    if(personalHistoryListViewItems.get(i).getPersonalHistoryEnd()!=null)
                        jsPersonalHistory.put("personal_history_end", Utilities.convertTimePost(personalHistoryListViewItems.get(i).getPersonalHistoryEnd()));
                    jsPersonalHistory.put("personal_history_type_id",personalHistoryListViewItems.get(i).getPersonalHistoryTypeID());
                    jsPersonalHistory.put("applicant_id",personalHistoryListViewItems.get(i).getApplicantID());
                    sendData.put("personalHistoryID",personalHistoryListViewItems.get(i).getPersonalHistoryTypeID());
                    sendData.put("personalHistoryData",jsPersonalHistory);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (personalHistoryListViewItems.get(i).getPersonalHistoryTypeID()){
                    case 1:
                        Utilities.startFragWith(context, ChildApplicantActivity.class, "myresumeaddeducation", sendData.toString());
                        break;
                    case 2:
                        Utilities.startFragWith(context, ChildApplicantActivity.class, "myresumeaddexperience", sendData.toString());
                        break;
                    case 3:
                        Utilities.startFragWith(context, ChildApplicantActivity.class, "myresumeaddactivity", sendData.toString());
                        break;
                    case 4:
                        Utilities.startFragWith(context, ChildApplicantActivity.class, "myresumeaddaward", sendData.toString());
                        break;
                }
            }
        });
        return view;
    }
}
