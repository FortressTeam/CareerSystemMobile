package com.example.kyler.careersystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        password.setOnKeyListener(this);
        loginNormal.setOnClickListener(this);
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
            default:break;
        }
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
