package com.example.kyler.careersystem.HiringManager.customize;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;

import java.util.ArrayList;

/**
 * Created by kyler on 11/04/2016.
 */
public class ManagePostAdapter extends ArrayAdapter<Posts> {
    private Context context;
    private ArrayList<Posts> posts;
    private int count;
    private int stepNumber;
    private int startCount;

    public ManagePostAdapter(Context context, ArrayList<Posts> posts, int startCount, int stepNumber) {
        super(context,R.layout.hiringmanager_managepost_listviewitem,posts);
        this.context = context;
        this.posts = posts;
        this.startCount = Math.min(startCount, posts.size());
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    public void setPosts(ArrayList<Posts> posts) {
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Posts getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.hiringmanager_managepost_listviewitem,null);
        }
        TextView managepost_title_listviewitem = (TextView) view.findViewById(R.id.managepost_title_listviewitem);
        TextView managepost_content_listviewitem = (TextView) view.findViewById(R.id.managepost_content_listviewitem);
        TextView managepost_created_listviewitem = (TextView) view.findViewById(R.id.managepost_created_listviewitem);
        managepost_title_listviewitem.setText(posts.get(i).getPostTitle());
        managepost_content_listviewitem.setText(Html.fromHtml(posts.get(i).getPostContent()));
        managepost_created_listviewitem.setText(Utilities.getDays(posts.get(i).getPostDate()));
        return view;
    }

    public void showMore(){
        count = Math.min(count + stepNumber, posts.size()); //don't go past the end
        notifyDataSetChanged(); //the count size has changed, so notify the super of the change

    }
    public boolean endReached(){
        return count == posts.size();
    }
}
