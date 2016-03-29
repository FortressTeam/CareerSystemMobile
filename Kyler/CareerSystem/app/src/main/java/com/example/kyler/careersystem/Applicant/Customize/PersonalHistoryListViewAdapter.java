package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.R;

import java.util.ArrayList;

/**
 * Created by kyler on 28/03/2016.
 */
public class PersonalHistoryListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PersonalHistoryListViewItem> personalHistoryListViewItems;

    public PersonalHistoryListViewAdapter(Context context, ArrayList<PersonalHistoryListViewItem> personalHistoryListViewItems) {
        this.context = context;
        this.personalHistoryListViewItems = personalHistoryListViewItems;
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
        TextView myresume_history_title = (TextView) view.findViewById(R.id.myresume_history_title);
        TextView myresume_history_detail = (TextView) view.findViewById(R.id.myresume_history_detail);
        TextView myresume_history_time = (TextView) view.findViewById(R.id.myresume_history_time);
        ImageView myresume_history_edit = (ImageView) view.findViewById(R.id.myresumer_history_edit);
        myresume_history_title.setText(personalHistoryListViewItems.get(i).getHistoryTitle());
        myresume_history_detail.setText(personalHistoryListViewItems.get(i).getHistoryDetail());
        myresume_history_time.setText("From "+personalHistoryListViewItems.get(i).getHistoryStart().toString()+" to "+personalHistoryListViewItems.get(i).getHistoryEnd().toString());
        myresume_history_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do edit thing in here;
                Toast.makeText(context.getApplicationContext(),personalHistoryListViewItems.get(i).getHistoryTitle()+"\n"+personalHistoryListViewItems.get(i).getHistoryStart(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
