package com.example.kyler.careersystem.HiringManager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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

public class AddPostFragment extends Fragment implements View.OnClickListener,CheckBox.OnCheckedChangeListener,Spinner.OnItemSelectedListener,ObservableScrollViewCallbacks {
    private EditText addPostTitle,addPostSalary,addPostLocation,addPostContent;
    private Button addPostAdd,addPostCancel;
    private Spinner addPostCategory;
    private CheckBox addPostSalarycb;
    private ObservableScrollView observableScrollView;

    private int page=1;
    private boolean noMoreData = false;
    private boolean negotiable = false;
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
        addPostContent = (EditText) rootView.findViewById(R.id.hiringmanager_addpost_postcontent_edittext);
        addPostCategory = (Spinner) rootView.findViewById(R.id.hiringmanager_addpost_category_spinner);
        addPostSalarycb = (CheckBox) rootView.findViewById(R.id.hiringmanager_addpost_salary_checkbox);
        arrayListCategories = new ArrayList<>();
        ArrayList<String> arr = new ArrayList<>();
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
        addPostSalarycb.setOnCheckedChangeListener(this);
        addPostAdd.setOnClickListener(this);
        observableScrollView.setScrollViewCallbacks(this);
        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.isChecked()){
            addPostSalary.setText("");
            addPostSalary.setEnabled(false);
            negotiable=true;
        }
        else {
            addPostSalary.setEnabled(true);
            negotiable=false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoryID=arrayListCategories.get(i).getID();
        Toast.makeText(getActivity().getApplicationContext(),arrayListCategories.get(i).getCategoryName()+" "+arrayListCategories.get(i).getID(),Toast.LENGTH_SHORT).show();
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
                    if(salaryIsNumber()) {
                        createAlertConfirm();
                    }else{
                        new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("Salary must be a number... ").setPositiveButton("OK",null).show();
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

    private boolean hasContent(){
        if(!addPostContent.getText().toString().trim().equals(""))
            return true;
        else
            return false;
    }

    private boolean hasSalary(){
        if(negotiable)
            return true;
        else{
            if(!addPostSalary.getText().toString().trim().equals(""))
                return true;
            else
                return false;
        }
    }

    private boolean isNumber(String input){
        return input.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean nothingIsNull(){
        return hasTitle() && hasSalary() && hasLocation() && hasContent();
    }

    private boolean salaryIsNumber(){
        return isNumber(addPostSalary.getText().toString())&&!negotiable;
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

    private void createAlertConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm creating a post ...").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                JSONObject jsonObject = new JSONObject();
                Boolean isSuccess = false;
                try {
                    jsonObject.put("post_title", addPostTitle.getText());
                    jsonObject.put("post_content", addPostContent.getText());
                    if (!negotiable)
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
        if(!hasSalary())
            message+=" \nSalary ";
        if(!hasLocation())
            message+=" \nLocation ";
        if(!hasContent())
            message+=" \nContent";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Missing").setMessage(message).setPositiveButton("OK",null).show();
    }
}
