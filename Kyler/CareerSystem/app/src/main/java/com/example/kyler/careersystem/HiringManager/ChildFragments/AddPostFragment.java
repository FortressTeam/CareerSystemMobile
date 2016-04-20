package com.example.kyler.careersystem.HiringManager.ChildFragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kyler.careersystem.Entities.Categories;
import com.example.kyler.careersystem.Entities.Posts;
import com.example.kyler.careersystem.HiringManager.ChildHiringManagerActivity;
import com.example.kyler.careersystem.WorkWithService.GetJsonArrayCallback;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJsonCallback;
import com.example.kyler.careersystem.WorkWithService.PutDataWithJsonCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddPostFragment extends Fragment implements View.OnClickListener,Spinner.OnItemSelectedListener,ObservableScrollViewCallbacks,View.OnFocusChangeListener {
    private EditText addPostTitle,addPostSalary,addPostLocation,addPostContent;
    private ImageView addPostTitleIcon,addPostSalaryIcon,addPostLocationIcon,addPostCategoryIcon,addPostContentIcon;
    private Button addPostAdd;
    private Spinner addPostCategory;
    private ObservableScrollView observableScrollView;

    private int page=1;
    private boolean noMoreData = false;
    private int categoryID,categoryDefaultID,hiringManagerID=Utilities.hiringManagers.getID();
    private Posts postEdit;
    private boolean editMode=false;
    private ArrayList<String> arr;

    private ArrayList<Categories> arrayListCategories=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hiringmanager_addpost_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Post");
        Utilities.hideSoftKeyboard(getActivity(), rootView.findViewById(R.id.hiringmanager_addpost_layout));
        observableScrollView = (ObservableScrollView) rootView.findViewById(R.id.hiringmanager_addpost_scrollview);
        addPostAdd = (Button) rootView.findViewById(R.id.hiringmanager_addpost_add);
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
        arr = new ArrayList<>();
        do {
            GetJsonArrayCallback getJsonArrayCallback = new GetJsonArrayCallback(getActivity(),"categories") {
                @Override
                public void receiveData(Object result) {
                    try {
                        JSONArray jsonArrayCategories = (JSONArray) result;
                        if (jsonArrayCategories != null) {
                            for (int i = 0; i < jsonArrayCategories.length(); i++) {
                                JSONObject jsonObjectCategories = jsonArrayCategories.getJSONObject(i);
                                Categories category = new Categories(jsonObjectCategories);
                                arrayListCategories.add(category);
                                arr.add(arrayListCategories.get(i).getCategoryName());
                            }
                            arr.add("Category");
                            categoryDefaultID = arr.size() - 1;
                            page++;
                        } else
                            noMoreData = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            getJsonArrayCallback.execute(UrlStatic.URLCategories + page);
        }while(!noMoreData);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.spinner_item,arr);
        addPostCategory.setAdapter(adapter);
        addPostCategory.setSelection(categoryDefaultID);
        addPostCategory.setOnItemSelectedListener(this);
        addPostAdd.setOnClickListener(this);
        observableScrollView.setScrollViewCallbacks(this);
        addPostTitle.setOnFocusChangeListener(this);
        addPostSalary.setOnFocusChangeListener(this);
        addPostLocation.setOnFocusChangeListener(this);
        addPostCategory.setOnFocusChangeListener(this);
        addPostContent.setOnFocusChangeListener(this);
        adapter.notifyDataSetChanged();
        Bundle bundle = getArguments();
        if(!bundle.getString("sendData").equals("")){
            try {
                JSONObject received = new JSONObject(bundle.getString("sendData"));
                postEdit = new Posts(received);
                addPostTitle.setText(postEdit.getPostTitle());
                addPostSalary.setText(postEdit.getPostSalary()+"");
                addPostLocation.setText(postEdit.getPostLocation());
                addPostContent.setText(postEdit.getPostContent());
                for(int i=0;i<arrayListCategories.size();i++){
                    if(arrayListCategories.get(i).getID()==postEdit.getCategoryID()) {
                        addPostCategory.setSelection(i);
                        break;
                    }
                }
                editMode=true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(editMode)
            addPostAdd.setText("Save");
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i!=categoryDefaultID) {
            categoryID = arrayListCategories.get(i).getID();
            Toast.makeText(getActivity().getApplicationContext(), arrayListCategories.get(i).getCategoryName() + " " + arrayListCategories.get(i).getID(), Toast.LENGTH_SHORT).show();
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
        if(!(addPostCategory.getSelectedItemPosition()==categoryDefaultID))
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


    private JSONObject sendData = new JSONObject();
    private void createAlertConfirm(final boolean isNegotiable){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Boolean isSuccess = false;
        if(editMode) {
            builder.setMessage("Confirm editing a post ...");
        }
        else {
            builder.setMessage("Confirm creating a post ...");
        }
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    sendData.put("post_title", addPostTitle.getText());
                    sendData.put("post_content", addPostContent.getText());
                    if (!isNegotiable)
                        sendData.put("post_salary", Float.parseFloat(addPostSalary.getText() + ""));
                    sendData.put("post_location", addPostLocation.getText());
                    if(editMode){
                        sendData.put("id",postEdit.getID());
                        sendData.put("post_date", Utilities.convertTimePost(postEdit.getPostDate()));
                    }else{
                        sendData.put("post_date", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    }
                    sendData.put("post_status", true);
                    sendData.put("category_id", categoryID);
                    sendData.put("hiring_manager_id", hiringManagerID);
                    if(editMode){
                        PutDataWithJsonCallback putDataWithJsonCallback = new PutDataWithJsonCallback(sendData,getActivity()) {
                            @Override
                            public void receiveData(Object result) {
                                Boolean isSuccess = Utilities.isCreateUpdateSuccess((JSONObject) result);
                                if(isSuccess){
                                    Utilities.startFragWith(getActivity(), ChildHiringManagerActivity.class, "jobdetail", sendData.toString());
                                    Toast.makeText(getActivity().getApplicationContext(), "success ", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        putDataWithJsonCallback.execute(UrlStatic.URLEditPost + postEdit.getID() + ".json");
                    }else{
                        PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(sendData,getActivity()) {
                            @Override
                            public void receiveData(Object result) {
                                Boolean isSuccess = Utilities.isCreateUpdateSuccess((JSONObject) result);
                                if(isSuccess){
                                    Utilities.startFragWith(getActivity(), ChildHiringManagerActivity.class, "jobdetail", sendData.toString());
                                    Toast.makeText(getActivity().getApplicationContext(), "success ", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        postDataWithJsonCallback.execute(UrlStatic.URLPosts);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
