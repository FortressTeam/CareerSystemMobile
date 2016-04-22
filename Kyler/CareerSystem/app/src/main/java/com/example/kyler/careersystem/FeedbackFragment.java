package com.example.kyler.careersystem;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kyler.careersystem.WorkWithService.PostDataWithJsonCallback;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FeedbackFragment extends Fragment implements View.OnClickListener,ObservableScrollViewCallbacks {
    private LinearLayout feedbackIdea,feedbackProblem,feedbackQuestion,feedbackPraise;
    private EditText feedbackTitle,feedbackComment;
    private Button feedbackSubmit,feedbackCancel;

    private ObservableScrollView observableScrollView;

    private int feedbackTypeID=0;
    private int userID=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feedback_fragment,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Feedback");
        observableScrollView = (ObservableScrollView) rootView.findViewById(R.id.feedback_scrollview);
        feedbackIdea = (LinearLayout) rootView.findViewById(R.id.feedback_idea);
        feedbackProblem = (LinearLayout) rootView.findViewById(R.id.feedback_problem);
        feedbackQuestion = (LinearLayout) rootView.findViewById(R.id.feedback_question);
        feedbackPraise = (LinearLayout) rootView.findViewById(R.id.feedback_praise);
        feedbackTitle = (EditText) rootView.findViewById(R.id.feedback_title_edittext);
        feedbackComment = (EditText) rootView.findViewById(R.id.feedback_comment_edittext);
        feedbackSubmit = (Button) rootView.findViewById(R.id.feedback_submit_button);
        feedbackCancel = (Button) rootView.findViewById(R.id.feedback_cancel_button);
        observableScrollView.setScrollViewCallbacks(this);
        feedbackSubmit.setOnClickListener(this);
        feedbackCancel.setOnClickListener(this);
        feedbackIdea.setOnClickListener(this);
        feedbackProblem.setOnClickListener(this);
        feedbackQuestion.setOnClickListener(this);
        feedbackPraise.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.feedback_idea:
                feedbackTypeID = 1;
                selectFeedbackType(view);
                break;
            case R.id.feedback_problem:
                feedbackTypeID = 2;
                selectFeedbackType(view);
                break;
            case R.id.feedback_question:
                feedbackTypeID = 3;
                selectFeedbackType(view);
                break;
            case R.id.feedback_praise:
                feedbackTypeID = 4;
                selectFeedbackType(view);
                break;
            case R.id.feedback_submit_button:
                if(nothingIsNull()){
                    postFeedback();
                }else{
                    creatAlertError();
                }
                break;
            case R.id.feedback_cancel_button:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    private void unselectFeedbackType(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            feedbackIdea.setElevation(2);
            feedbackProblem.setElevation(2);
            feedbackQuestion.setElevation(2);
            feedbackPraise.setElevation(2);
        }
    }

    private void selectFeedbackType(View view){
        unselectFeedbackType();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(20);
        }
    }

    private boolean hasTitle(){
        if(!feedbackTitle.getText().toString().equals(""))
            return true;
        else
            return false;
    }

    private boolean hasComment(){
        if(!feedbackComment.getText().toString().equals(""))
            return true;
        else
            return false;
    }

    private boolean nothingIsNull(){
        return hasTitle() && hasComment() && (feedbackTypeID!=0);
    }

    private boolean isPostFeedbackSuccess(JSONObject jsonObject){
        boolean result = false;
        try{
            if(jsonObject.get("message").equals("Saved"))
                result = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void postFeedback(){
        JSONObject jsonObject = new JSONObject();
        boolean isSuccess=false;
        try {
            jsonObject.put("feedback_title",feedbackTitle.getText());
            jsonObject.put("feedback_comment",feedbackComment.getText());
            jsonObject.put("feedback_date",new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            jsonObject.put("feedback_type_id",feedbackTypeID);
            jsonObject.put("user_id",userID);
            PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(jsonObject,getActivity()) {
                @Override
                public void receiveData(Object result) {
                    boolean isSuccess = isPostFeedbackSuccess((JSONObject) result);
                    if (isSuccess) {
                        Toast.makeText(getActivity().getApplicationContext(), "Create Post success ... ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Create Post fail ... Something went wrong ", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            postDataWithJsonCallback.execute(UrlStatic.URLFeedbacks);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void creatAlertError(){
        String message = "You missed :";
        if(!hasTitle())
            message +=" \nTitle ";
        if(!hasComment())
            message+=" \nComment ";
        if(feedbackTypeID==0)
            if(hasTitle()&&hasComment())
                message="You didn't choose the type of Feedback yet";
            else
                message="You didn't choose the type of Feedback yet\n"+message;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Missing").setMessage(message).setPositiveButton("OK",null).show();
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
}
