package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.CurriculumVitaes;
import com.example.kyler.careersystem.R;

import java.util.ArrayList;

/**
 * Created by kyler on 27/04/2016.
 */
public class MyCVListViewAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<CurriculumVitaes> curriculumVitaes;

    public MyCVListViewAdapter(Activity context, ArrayList<CurriculumVitaes> curriculumVitaes) {
        this.context = context;
        this.curriculumVitaes = curriculumVitaes;
    }


    @Override
    public int getCount() {
        return curriculumVitaes.size();
    }

    @Override
    public Object getItem(int i) {
        return curriculumVitaes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.applicant_myresume_hobbie_listviewitem,null);
        }
        TextView myresume_hobbie_name_listviewitem = (TextView) view.findViewById(R.id.myresume_hobbie_name_listviewitem);
        ImageView myresume_hobbie_icon_listviewitem = (ImageView) view.findViewById(R.id.myresume_hobbie_icon_listviewitem);
        ImageView myresume_hobbie_delete_listviewitem = (ImageView) view.findViewById(R.id.myresume_hobbie_delete_listviewitem);
        myresume_hobbie_icon_listviewitem.setVisibility(View.INVISIBLE);
        myresume_hobbie_delete_listviewitem.setVisibility(View.INVISIBLE);
        myresume_hobbie_name_listviewitem.setText(curriculumVitaes.get(i).getCurriculumVitaeName());
        return view;
    }
}
