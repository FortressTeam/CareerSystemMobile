package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kyler.careersystem.R;

import java.util.ArrayList;

/**
 * Created by kyler on 10/03/2016.
 */
public class ExperienceListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ExperienceListViewItem> experienceListViewItems;

    public ExperienceListViewAdapter(Context context, ArrayList<ExperienceListViewItem> experienceListViewItems) {
        this.context = context;
        this.experienceListViewItems = experienceListViewItems;
    }

    @Override
    public int getCount() {
        return experienceListViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return experienceListViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.applicant_myresume_experience_listviewitem,null);
        }
        TextView myresume_experience_listviewitem_conpanyname = (TextView) view.findViewById(R.id.myresume_experience_listviewitem_conpanyname);
        TextView myresume_experience_listviewitem_position = (TextView) view.findViewById(R.id.myresume_experience_listviewitem_position);
        TextView myresume_experience_listviewitem_time = (TextView) view.findViewById(R.id.myresume_experience_listviewitem_time);
        TextView myresume_experience_listviewitem_description = (TextView) view.findViewById(R.id.myresume_experience_listviewitem_description);
        myresume_experience_listviewitem_conpanyname.setText(experienceListViewItems.get(i).getCompany());
        myresume_experience_listviewitem_position.setText(experienceListViewItems.get(i).getPosition());
        myresume_experience_listviewitem_time.setText("From "+experienceListViewItems.get(i).getStart().toString()+" to "+experienceListViewItems.get(i).getEnd().toString());
        myresume_experience_listviewitem_description.setText(experienceListViewItems.get(i).getDescription());
//        if(i%2==0)
//            view.setBackgroundResource(R.color.White);
//        else
//            view.setBackgroundResource(R.color.listviewitemexperiencebackground);
        return view;
    }
}
