package com.example.kyler.careersystem;


import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import static java.util.Arrays.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicantFragment extends Fragment implements View.OnClickListener,View.OnKeyListener{
    private EditText username,password;
    private Button loginNormal;
    private ImageView loginFacebook,loginTwitter,loginGooglePlus;
    private ImageView serviceSetting;
    CallbackManager callbackManager;
    ProfileTracker profileTracker;
    Profile profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_applicant,container,false);
        Utilities.hideSoftKeyboard(getActivity(),view.findViewById(R.id.applicant_login_linearlayout));
        username = (EditText) view.findViewById(R.id.applicant_login_username);
        password = (EditText) view.findViewById(R.id.applicant_login_password);
        loginFacebook = (ImageView) view.findViewById(R.id.applicant_login_facebook);
        loginTwitter = (ImageView) view.findViewById(R.id.applicant_login_twitter);
        loginGooglePlus = (ImageView) view.findViewById(R.id.applicant_login_googleplus);
        loginNormal = (Button) view.findViewById(R.id.applicant_loginnormal);
        serviceSetting = (ImageView) view.findViewById(R.id.service_setting);
        createDialogSetting();
        password.setOnKeyListener(this);
        loginNormal.setOnClickListener(this);
        serviceSetting.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            profile = currentProfile;
                            username.setText(profile.getName());
                            //this place for getting profile information for doing login.
                            profileTracker.stopTracking();
                        }
                    };
                    profileTracker.startTracking();
                } else {
                    profile = Profile.getCurrentProfile();
                    username.setText(profile.getName());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        loginFacebook.setOnClickListener(this);
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                profile = currentProfile;
            }
        };
        return view;
    }

    private boolean checkValidLogin(){
        if(username.getText().toString().trim().equals("")||password.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.applicant_loginnormal:
                if(checkValidLogin())
                    Utilities.doLoginNormal(getActivity(), username.getText().toString(), password.getText().toString(), 3);
                else
                    Toast.makeText(getActivity().getApplicationContext(),"username or password is missing",Toast.LENGTH_SHORT).show();
                break;
            case R.id.applicant_login_facebook:
                Utilities.doLoginNormal(getActivity(), "kyler", "123123123", 3);
//                LoginManager.getInstance().logInWithReadPermissions(this, asList("public_profile", "user_friends"));
                break;
            case R.id.service_setting:
                dialogSetting.show();
            default:break;
        }
    }

    private Dialog dialogSetting;
    private EditText serviceSettingEdit;
    private RadioButton rbKoding,rbEnclave,rbCloud9,rbCustom;
    private void createDialogSetting(){
        dialogSetting = new Dialog(getActivity());
        dialogSetting.setContentView(R.layout.service_setting_dialog);
        dialogSetting.setTitle("Choose your service");
        rbKoding = (RadioButton) dialogSetting.findViewById(R.id.service_setting_koding);
        rbEnclave = (RadioButton) dialogSetting.findViewById(R.id.service_setting_enclave);
        rbCloud9 = (RadioButton) dialogSetting.findViewById(R.id.service_setting_cloud9);
        rbCustom = (RadioButton) dialogSetting.findViewById(R.id.service_setting_custom);
        serviceSettingEdit = (EditText) dialogSetting.findViewById(R.id.service_setting_custom_edit);
        rbCustom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    serviceSettingEdit.setVisibility(View.VISIBLE);
                }else{
                    serviceSettingEdit.setVisibility(View.GONE);
                }
            }
        });
        Button serviceSettingSave = (Button) dialogSetting.findViewById(R.id.service_setting_save);
        serviceSettingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbKoding.isChecked()){
                    UrlStatic.service = "http://u1kkf43607cc.123456798a.koding.io/CareerSystemWebBased/career_system/";
                }else if(rbEnclave.isChecked()){
                    UrlStatic.service = "http://192.168.11.196/CareerSystemWebBased/career_system/";
                }else if(rbCloud9.isChecked()){
                    UrlStatic.service = "http://career-system-viennt.c9users.io/career_system/";
                }else{
                    UrlStatic.service = serviceSettingEdit.getText().toString();
                }
                UrlStatic.URLimg = UrlStatic.service + "img/";
                UrlStatic.URLSignin = UrlStatic.service + "api/users/signin.json";
                UrlStatic.URLUpdateToken = UrlStatic.service +"api/users/updateToken/";
                UrlStatic.URLApplicantsFollowPosts = UrlStatic.service +"api/applicants_follow_posts.json";
                UrlStatic.URLPostsHasCurriculumVitaes = UrlStatic.service +"api/posts_has_curriculum_vitaes.json";
                UrlStatic.URLCurriculumVitaes = UrlStatic.service +"api/curriculum_vitaes.json?applicant_id=";
                UrlStatic.URLfollow = UrlStatic.service +"api/follow.json";
                UrlStatic.URLPosts = UrlStatic.service + "api/posts.json";
                UrlStatic.URLEditPost = UrlStatic.service + "api/posts/";
                UrlStatic.URLManagePost = UrlStatic.service + "api/posts.json?post_status=1&limite=20&hiring_manager_id=";
                UrlStatic.URLHomefragment = UrlStatic.service + "api/posts.json?post_status=1&limit=10&page=";
                UrlStatic.URLCategories = UrlStatic.service + "api/categories.json?limit=100&page=";
                UrlStatic.URLFeedbacks = UrlStatic.service + "api/feedbacks.json";
                UrlStatic.URLApplicant = UrlStatic.service + "api/applicants/";
                UrlStatic.URLHiringManager = UrlStatic.service + "api/hiringmanagers/";
                UrlStatic.URLSkillTypes = UrlStatic.service + "api/skill_types.json";
                UrlStatic.URLSkills = UrlStatic.service + "api/skills.json";
                UrlStatic.URLPersonalHistory = UrlStatic.service + "api/personal_history.json";
                UrlStatic.URLPersonalHistory2 = UrlStatic.service + "api/personal_history/";
                UrlStatic.URLApplicantsHasSkills = UrlStatic.service + "api/applicants_has_skills.json";
                UrlStatic.URLApplicantsHasHobbies = UrlStatic.service + "api/applicants_has_hobbies.json";
                UrlStatic.URLHobbies = UrlStatic.service + "api/hobbies.json";
                dialogSetting.dismiss();
            }
        });
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        switch (view.getId()){
            case R.id.applicant_login_password:
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    if(checkValidLogin())
                        Utilities.doLoginNormal(getActivity(), username.getText().toString(), password.getText().toString(), 3);
                    else
                        Toast.makeText(getActivity().getApplicationContext(),"username or password is missing",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return false;
    }
}
