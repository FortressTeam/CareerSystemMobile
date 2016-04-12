package com.example.kyler.careersystem.HiringManager;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.kyler.careersystem.Bussiness.PostController;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.HiringManager.customize.ManagePostAdapter;
import com.example.kyler.careersystem.HiringManagerMainActivity;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.GetJsonArray;
import com.example.kyler.careersystem.WorkWithService.GetJsonLoadMore;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagePost extends Fragment implements AbsListView.OnScrollListener,View.OnClickListener {
    private SwipeMenuListView managepost_listview;
    private FloatingActionButton managePostAddPost;
    private Handler mHandler;
    private ProgressDialog pDialog;
    private ArrayList<Posts> posts,postsLoadMore;
    private ProgressBar progressBar;
    private ManagePostAdapter managePostAdapter;
    private JSONArray jsPosts;
    private PostController postController;
    private int hiringmanagerID=Utilities.hiringmanagerID;
    private int page=1;
    private boolean nomoreData=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hiringmanager_managepost_fragment, container, false);
        posts = new ArrayList<>();
        postController = new PostController();
        managepost_listview = (SwipeMenuListView) rootView.findViewById(R.id.hiringmanager_managepost_listview);
        managePostAddPost = (FloatingActionButton) rootView.findViewById(R.id.hiringmanager_managepost_addpost);
        managePostAddPost.setOnClickListener(this);
        mHandler = new Handler();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.progress_bar_footer, null);
        progressBar = (ProgressBar) footer.findViewById(R.id.progressBar);
        managepost_listview.addFooterView(footer);
        managepost_listview.setOnScrollListener(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    jsPosts = new GetJsonArray(pDialog, "posts").execute(UrlStatic.URLManagePost +hiringmanagerID+"&page="+page).get();
                    posts = postController.getPosts(jsPosts);
                    managePostAdapter = new ManagePostAdapter(getActivity().getApplicationContext(), posts,20,10);
                    managepost_listview.setAdapter(managePostAdapter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, 300);


        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0x52, 0x52)));
                deleteItem.setWidth(size.x * 20 / 100);
                deleteItem.setTitle("Delete");
                deleteItem.setIcon(R.drawable.deleteiconbig);
                menu.addMenuItem(deleteItem);

                SwipeMenuItem editItem = new SwipeMenuItem(getActivity().getApplicationContext());
                editItem.setBackground(new ColorDrawable(Color.rgb(0x11, 0x78, 0xAB)));
                editItem.setWidth(size.x * 20 / 100);
                editItem.setTitle("Edit");
                editItem.setIcon(R.drawable.editiconbig);
                menu.addMenuItem(editItem);
            }
        };
        managepost_listview.setMenuCreator(creator);
        managepost_listview.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        managepost_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int i, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        deletePost(i);
                        break;
                    case 1:
                        editPost(posts.get(i));
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        managepost_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return rootView;
    }

    private void editPost(Posts post){
        JSONObject jsSendData = new JSONObject();
        try {
            jsSendData.put("id",post.getID());
            jsSendData.put("post_title",post.getPostTitle());
            jsSendData.put("post_content",post.getPostContent());
            jsSendData.put("post_salary",post.getPostSalary());
            jsSendData.put("post_location",post.getPostLocation());
            jsSendData.put("post_date",Utilities.convertTimePost(post.getPostDate()));
            jsSendData.put("post_status",post.isPostStatus());
            jsSendData.put("category_id",post.getCategoryID());
            jsSendData.put("hiring_manager_id",post.getHiringManagerID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utilities.startFragWith(getActivity(), ChildHiringManagerActivity.class,"editpost",jsSendData.toString());
    }

    private void deletePost(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JSONObject jssendData = new JSONObject();
                        try {
                            jssendData.put("id",posts.get(id).getID());
                            jssendData.put("post_status",false);
                            JSONObject result = new PutDataWithJson(jssendData,getActivity()).execute(UrlStatic.URLEditPost+posts.get(id).getID()+".json").get();
                            if(Utilities.isCreateUpdateSuccess(result)){
                                posts.remove(id);
                                managePostAdapter.setPosts(posts);
                                managePostAdapter.notifyDataSetChanged();
                                Toast.makeText(getActivity().getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", null).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount == totalItemCount && !nomoreData && !hasCallback){ //check if we've reached the bottom
            progressBar.setVisibility(View.VISIBLE);
            mHandler.postDelayed(showMore,200);
            hasCallback = true;
        }
    }

    private boolean hasCallback;

    private Runnable showMore = new Runnable() {
        @Override
        public void run() {
            if(managePostAdapter!=null){
                if(managePostAdapter.endReached()){
                    page++;
                    try {
                        JSONArray jsonArray = new GetJsonLoadMore(progressBar,"posts").execute(UrlStatic.URLManagePost +hiringmanagerID+"&page="+page).get();
                        if(jsonArray!=null){
                            nomoreData=false;
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Posts post = new Posts(jsonObject);
                                posts.add(post);
                                managePostAdapter.setPosts(posts);
                            }
                        }else
                            nomoreData=true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                managePostAdapter.showMore();
            }
            hasCallback = false;
        }
    };

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.hiringmanager_managepost_addpost:
                Utilities.startFragWith(getActivity(),ChildHiringManagerActivity.class,"addpost","");
                break;

            default:break;
        }
    }
}
