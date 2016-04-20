package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.DeleteDataWithJsonCallback;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 31/03/2016.
 */
public class SkillListViewAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<SkillListViewItem> skillListViewItemItems;
    private boolean isDialogMode;
    private boolean hideButton;

    public SkillListViewAdapter(Activity context, ArrayList<SkillListViewItem> skillListViewItemItems, boolean isDialogMode, boolean hideButton) {
        this.context = context;
        this.skillListViewItemItems = skillListViewItemItems;
        this.isDialogMode = isDialogMode;
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
        ImageView myresume_skill_delete_listviewitem = (ImageView) view.findViewById(R.id.myresume_skill_delete_listviewitem);
        myresume_skill_name_listviewitem.setText(skillListViewItemItems.get(i).getSkillName());
        float weight = (float) skillListViewItemItems.get(i).getSkillLevel()/5;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) myresume_skill_level_listviewitem.getLayoutParams();
        params.weight = weight;
        myresume_skill_level_listviewitem.setLayoutParams(params);
        if(isDialogMode)
            myresume_skill_level_listviewitem.setVisibility(View.GONE);
        else
            myresume_skill_level_listviewitem.setVisibility(View.VISIBLE);
        if(hideButton)
            myresume_skill_delete_listviewitem.setVisibility(View.INVISIBLE);
        else
            myresume_skill_delete_listviewitem.setVisibility(View.VISIBLE);
        myresume_skill_delete_listviewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do delete job
                final int applicantID=skillListViewItemItems.get(i).getApplicantID();
                final int skillID=skillListViewItemItems.get(i).getSkillID();
                final int listViewID=i;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteDataWithJsonCallback deleteDataWithJsonCallback = new DeleteDataWithJsonCallback(context) {
                                    @Override
                                    public void receiveData(Object result) {
                                        JSONObject jsResult = (JSONObject) result;
                                        if(Utilities.isDeleteSuccess(jsResult)){
                                            skillListViewItemItems.remove(listViewID);
                                            notifyDataSetChanged();
                                            Toast.makeText(context.getApplicationContext(),"Delete success",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(context.getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                };
                                deleteDataWithJsonCallback.execute(UrlStatic.URLApplicantsHasSkills+"?applicant_id="+applicantID+"&skill_id="+skillID);
                            }
                        })
                        .setNegativeButton("No",null).show();
            }
        });
        return view;
    }
}
