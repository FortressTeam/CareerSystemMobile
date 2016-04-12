package com.example.kyler.careersystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kyler.careersystem.WorkWithService.PostDataWithJson;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ApplicantFragment extends Fragment implements View.OnClickListener{
    private EditText username,password;
    private Button loginNormal;
    CallbackManager callbackManager;
    LoginButton loginButton;
    ProfileTracker profileTracker;
    Profile profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_applicant,container,false);
        username = (EditText) view.findViewById(R.id.applicant_login_username);
        password = (EditText) view.findViewById(R.id.applicant_login_password);
        loginNormal = (Button) view.findViewById(R.id.applicant_loginnormal);
        loginNormal.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.applicant_loginbutton);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null){
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
                }else{
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

    private void doLoginNormal(){
        JSONObject sendData = new JSONObject();
        try{
            sendData.put("username",username.getText().toString());
            sendData.put("password",password.getText().toString());
            JSONObject result = new PostDataWithJson(sendData,getActivity()).execute(UrlStatic.URLSignin).get();
            if(result!=null&&result.getString("message").equals("Success")){
                if(result.getJSONObject("user").getInt("group_id")==3) {
//                    UrlStatic.tokenAccess = result.getJSONObject("data").getString("token");
                    Toast.makeText(getActivity().getApplicationContext(), "Login success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginData.class).putExtra("key", 1);
                    Bundle bundle = new Bundle();
                    bundle.putString("jsuser",result.getJSONObject("user").toString());
                    intent.putExtra("sendData",bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
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
                    doLoginNormal();
                else
                    Toast.makeText(getActivity().getApplicationContext(),"username or password is missing",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
    }

}
