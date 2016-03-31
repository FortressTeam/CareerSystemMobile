package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.R;

import java.util.ArrayList;

/**
 * Created by kyler on 31/03/2016.
 */
public class SkillListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SkillListViewItem> skillListViewItemItems;
    private boolean hideButton;

    public SkillListViewAdapter(Context context, ArrayList<SkillListViewItem> skillListViewItemItems) {
        this.context = context;
        this.skillListViewItemItems = skillListViewItemItems;
    }

    public SkillListViewAdapter(Context context, ArrayList<SkillListViewItem> skillListViewItemItems, boolean hideButton) {
        this.context = context;
        this.skillListViewItemItems = skillListViewItemItems;
        this.hideButton = hideButton;
    }

    @Override
    public int getCount() {
        return skillListViewItemItems.size();
    }

    @Override
    public Object getItem(int i) {
        return skillListViewItemItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.applicant_myresume_skill_listviewitem,null);
        }
        TextView myresume_skill_name_listviewitem = (TextView) view.findViewById(R.id.myresume_skill_name_listviewitem);
        TextView myresume_skill_level_listviewitem = (TextView) view.findViewById(R.id.myresume_skill_level_listviewitem);
        ImageView myresume_skill_delete_listviewitem = (ImageView) view.findViewById(R.id.myresumer_skill_delete_listviewitem);
        myresume_skill_name_listviewitem.setText(skillListViewItemItems.get(i).getSkillName());
        float weight = (float) skillListViewItemItems.get(i).getSkillLevel()/5;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myresume_skill_level_listviewitem.getLayoutParams();
        params.weight = weight;
        myresume_skill_level_listviewitem.setLayoutParams(params);
        if(hideButton)
            myresume_skill_delete_listviewitem.setVisibility(View.INVISIBLE);
        else
            myresume_skill_delete_listviewitem.setVisibility(View.VISIBLE);
        myresume_skill_delete_listviewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do delete job

                Toast.makeText(context,skillListViewItemItems.get(i).getSkillName(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
