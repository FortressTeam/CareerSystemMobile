package com.example.kyler.careersystem.HiringManager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.GetDataFromService.GetJsonArray;
import com.example.kyler.careersystem.GetDataFromService.PostDataWithJson;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class AddPostFragment extends Fragment implements View.OnClickListener,Spinner.OnItemSelectedListener,ObservableScrollViewCallbacks,View.OnFocusChangeListener {
    private EditText addPostTitle,addPostSalary,addPostLocation,addPostContent;
    private ImageView addPostTitleIcon,addPostSalaryIcon,addPostLocationIcon,addPostCategoryIcon,addPostContentIcon;
    private Button addPostAdd,addPostCancel;
    private Spinner addPostCategory;
    private ObservableScrollView observableScrollView;

    private int page=1;
    private boolean noMoreData = false;
    private int categoryID,hiringManagerID=1;

    private ArrayList<Categories> arrayListCategories=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hiringmanager_addpost_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Post");
        observableScrollView = (ObservableScrollView) rootView.findViewById(R.id.hiringmanager_addpost_scrollview);
        addPostAdd = (Button) rootView.findViewById(R.id.hiringmanager_addpost_add);
        addPostCancel = (Button) rootView.findViewById(R.id.hiringmanager_addpost_cancel);
        addPostTitle = (EditText) rootView.findViewById(R.id.hiringmanager_addpost_title_edittext);
        addPostSalary = (EditText) rootView.findViewById(R.id.hiringmanager_addpost_salary_edittext);
        addPostLocation = (EditText) rootView.findViewById(R.id.hiringmanager_addpost_location_edittext);
        addPostContent = (EditText) rootView.findViewById(R.id.hiringmanager_addpost_content_edittext);
        addPostCategory = (Spinner) rootView.findViewById(R.id.hiringmanager_addpost_category_spinner);
        addPostTitleIcon = (ImageView) rootView.findViewById(R.id.hiringmanager_addpost_title_icon);
        addPostSalaryIcon = (ImageView) rootView.findViewById(R.id.hiringmanager_addpost_salary_icon);
        addPostLocationIcon = (ImageView) rootView.findViewById(R.id.hiringmanager_addpost_location_icon);
        addPostCategoryIcon = (ImageView) rootView.findViewById(R.id.hiringmanager_addpost_category_icon);
        addPostContentIcon = (ImageView) rootView.findViewById(R.id.hiringmanager_addpost_content_icon);
        arrayListCategories = new ArrayList<>();
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Category");
        try {
            do {
                JSONArray jsonArrayCategories = new GetJsonArray(getActivity(), "categories").execute(UrlStatic.URLCategories + page).get();
                if(jsonArrayCategories!=null){
                    for(int i=0;i<jsonArrayCategories.length();i++){
                        JSONObject jsonObjectCategories = jsonArrayCategories.getJSONObject(i);
                        Categories category = new Categories(jsonObjectCategories);
                        arrayListCategories.add(category);
                        arr.add(arrayListCategories.get(i).getCategoryName());
                    }
                    page++;
                }else
                    noMoreData = true;
            }while(!noMoreData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.spinner_item,arr);
        addPostCategory.setAdapter(adapter);
        addPostCategory.setOnItemSelectedListener(this);
        addPostAdd.setOnClickListener(this);
        observableScrollView.setScrollViewCallbacks(this);
        addPostTitle.setOnFocusChangeListener(this);
        addPostSalary.setOnFocusChangeListener(this);
        addPostLocation.setOnFocusChangeListener(this);
        addPostCategory.setOnFocusChangeListener(this);
        addPostContent.setOnFocusChangeListener(this);
        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i>0){
            categoryID=arrayListCategories.get(i-1).getID();
            Toast.makeText(getActivity().getApplicationContext(),arrayListCategories.get(i-1).getCategoryName()+" "+arrayListCategories.get(i-1).getID(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
            case R.id.hiringmanager_addpost_add:
                if(nothingIsNull()) {
                    if(!hasSalary())
                        new AlertDialog.Builder(getActivity()).setMessage("You didn't type the salary.\nYou want to Negatiable or Try again").setPositiveButton("Negatiable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                createAlertConfirm(true);
                            }
                        }).setNegativeButton("Try again",null).show();
                    else {
                        if (salaryIsNumber()) {
                            createAlertConfirm(false);
                        } else {
                            new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("Salary must be a number... ").setPositiveButton("OK", null).show();
                        }
                    }
                }else{
                    createAlertError();
                }
                break;
            default:
                break;
        }
    }

    private boolean hasTitle(){
        if(!addPostTitle.getText().toString().trim().equals(""))
            return true;
        else
            return false;
    }

    private boolean hasLocation(){
        if(!addPostLocation.getText().toString().trim().equals(""))
            return true;
        else
            return false;
    }

    private boolean hasCategory(){
        if(!addPostCategory.getSelectedItem().toString().equals("Category"))
            return true;
        else
            return false;
    }

    private boolean hasContent(){
        if(!addPostContent.getText().toString().trim().equals(""))
            return true;
        else
            return false;
    }

    private boolean hasSalary(){
        if(!addPostSalary.getText().toString().trim().equals(""))
            return true;
        else
            return false;

    }

    private boolean isNumber(String input){
        return input.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean nothingIsNull(){
        return hasTitle() && hasLocation() && hasCategory() && hasContent();
    }

    private boolean salaryIsNumber(){
        return isNumber(addPostSalary.getText().toString());
    }

    private boolean isCreatePostSuccess(JSONObject input){
        boolean result = false;
        try {
            if(input.getString("message").equals("Saved"))
                result = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void createAlertConfirm(final boolean isNegotiable){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm creating a post ...").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                JSONObject jsonObject = new JSONObject();
                Boolean isSuccess = false;
                try {
                    jsonObject.put("post_title", addPostTitle.getText());
                    jsonObject.put("post_content", addPostContent.getText());
                    if (!isNegotiable)
                        jsonObject.put("post_salary", Float.parseFloat(addPostSalary.getText() + ""));
                    jsonObject.put("post_location", addPostLocation.getText());
                    jsonObject.put("post_date", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    jsonObject.put("post_status", true);
                    jsonObject.put("category_id", categoryID);
                    jsonObject.put("hiring_manager_id", hiringManagerID);
                    isSuccess = isCreatePostSuccess(new PostDataWithJson(jsonObject, getActivity()).execute(UrlStatic.URLPosts).get());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if (isSuccess) {
                    Toast.makeText(getActivity().getApplicationContext(), "Create Post success ... ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Create Post fail ... Something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("No", null).show();
    }

    private void createAlertError(){
        String message = "You missed :";
        if(!hasTitle())
            message +=" \nTitle ";
        if(!hasLocation())
            message+=" \nLocation ";
        if(!hasCategory())
            message+=" \nCategory";
        if(!hasContent())
            message+=" \nContent";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Missing").setMessage(message).setPositiveButton("OK",null).show();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.hiringmanager_addpost_title_edittext:
                if(b){
                    addPostTitleIcon.setBackgroundResource(R.drawable.icon_title2);
                }else{
                    addPostTitleIcon.setBackgroundResource(R.drawable.icon_title);
                }
                break;
            case R.id.hiringmanager_addpost_salary_edittext:
                if(b){
                    addPostSalaryIcon.setBackgroundResource(R.drawable.icon_money2);
                }else{
                    addPostSalaryIcon.setBackgroundResource(R.drawable.icon_money);
                }
                break;
            case R.id.hiringmanager_addpost_location_edittext:
                if(b){
                    addPostLocationIcon.setBackgroundResource(R.drawable.icon_location2);
                }else{
                    addPostLocationIcon.setBackgroundResource(R.drawable.icon_location);
                }
                break;
            case R.id.hiringmanager_addpost_category_spinner:
                if(b){
                    addPostCategoryIcon.setBackgroundResource(R.drawable.icon_category2);
                }else{
                    addPostCategoryIcon.setBackgroundResource(R.drawable.icon_category);
                }
                break;
            case R.id.hiringmanager_addpost_content_edittext:
                if(b){
                    addPostContentIcon.setBackgroundResource(R.drawable.icon_content2);
                }else{
                    addPostContentIcon.setBackgroundResource(R.drawable.icon_content);
                }
                break;
            default:
                break;
        }
    }
}
