package com.example.kyler.careersystem.Applicant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyler.careersystem.R;

import java.util.ArrayList;

/**
 * Created by kyler on 09/03/2016.
 */
public class NavigationListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NavigationListViewItem> navigationListViewItems;

    public NavigationListViewAdapter(Context context, ArrayList<NavigationListViewItem> navigationListViewItems) {
        this.context = context;
        this.navigationListViewItems = navigationListViewItems;
    }

    @Override
    public int getCount() {
        return navigationListViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return navigationListViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.navigation_listview_item,viewGroup,false);
        }
        ImageView navigationListViewItemImage = (ImageView) view.findViewById(R.id.navigation_listview_item_image);
        TextView navigationListViewItemTitle = (TextView) view.findViewById(R.id.navigation_listview_item_title);
        TextView navigationListViewItemCounter = (TextView) view.findViewById(R.id.navigation_listview_item_counter);
        navigationListViewItemImage.setBackgroundResource(navigationListViewItems.get(i).getImage());
        navigationListViewItemTitle.setText(navigationListViewItems.get(i).getTitle());
        if(navigationListViewItems.get(i).isCounterVisible()) {
            navigationListViewItemCounter.setText(navigationListViewItems.get(i).getCounter() + "");
            navigationListViewItemCounter.setBackgroundResource(R.drawable.counterbg);
        }
        else{
            navigationListViewItemCounter.setVisibility(View.GONE);
            navigationListViewItemCounter.setBackgroundResource(android.R.color.white);
        }
        return view;
    }
}
