package com.example.kyler.careersystem.Applicant;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.ChildFragments.CompanyInformationFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.JobDetailFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeAboutFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeActivityFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeAwardFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeContactFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeEducationFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeExperienceFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeObjectiveFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.MyresumeProfileFragment;
import com.example.kyler.careersystem.Applicant.ChildFragments.SearchFragmentResult;
import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.FeedbackFragment;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.squareup.picasso.Picasso;

public class ChildApplicantActivity extends AppCompatActivity implements ListView.OnItemClickListener,View.OnClickListener {

    private ListView navigationViewMenu;
    private String receiveData;
    private Users users = Utilities.users;
    private Applicants applicants = Utilities.applicants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_applicant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setNavigationIcon(R.drawable.navigationbackicon);
        navigationViewMenu = (ListView) findViewById(R.id.navigation_view_menu);
        View navigationViewHeader = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        View navigationViewFooter = getLayoutInflater().inflate(R.layout.nav_footer_main, null);
        navigationViewMenu.addHeaderView(navigationViewHeader);
        navigationViewMenu.addFooterView(navigationViewFooter);
        Utilities.loadNavigationViewApplicant(this, navigationViewMenu);
        ImageView hiringManagerImage = (ImageView) findViewById(R.id.nav_header_image);
        TextView hiringManagerName = (TextView) findViewById(R.id.nav_header_name);
        TextView hiringManagerEmail = (TextView) findViewById(R.id.nav_header_email);
        Picasso.with(this).load(UrlStatic.URLimg+"user_img/"+users.getUserAvatar()).into(hiringManagerImage);
        hiringManagerName.setText(applicants.getApplicantName());
        hiringManagerEmail.setText(users.getUserEmail());
        LinearLayout navSettingControl = (LinearLayout) findViewById(R.id.nav_setting_control);
        navSettingControl.setOnClickListener(this);
        navigationViewMenu.setOnItemClickListener(this);
        navigationViewMenu.setOnItemClickListener(this);
        Bundle bundle = getIntent().getBundleExtra("sendBundle");
        String key = bundle.getString("key");
        receiveData = bundle.getString("sendData");
        startFragment(key);
    }

    private void startFragment(String key){
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (key){
            case "jobdetail":
                fragment = new JobDetailFragment();
                bundle.putString("sendData", receiveData);
                break;
            case "companydetail":
                fragment = new CompanyInformationFragment();
                bundle.putString("sendData", receiveData);
                break;

            case "myresumeeditprofile":
                fragment = new MyresumeProfileFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "myresumeeditcontact":
                fragment = new MyresumeContactFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "myresumeeditabout":
                fragment = new MyresumeAboutFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "myresumeaddeducation":
                fragment = new MyresumeEducationFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "myresumeaddexperience":
                fragment = new MyresumeExperienceFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "myresumeaddactivity":
                fragment = new MyresumeActivityFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "myresumeaddaward":
                fragment = new MyresumeAwardFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "myresumeeditobjectivegoal":
                fragment = new MyresumeObjectiveFragment();
                bundle.putString("sendData",receiveData);
                break;
            case "searchresult":
                fragment = new SearchFragmentResult();
                bundle.putString("sendData",receiveData);
                break;
            case "Feedback":
                fragment = new FeedbackFragment();
                break;
            default:break;
        }
        if(fragment!=null){
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.frame_main, fragment).commit();
        }
    }

    private void startActivity(int id){
        Intent intent = new Intent(this, ApplicantMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt("itemID",id);
        intent.putExtra("back", bundle);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nav_setting_control:
                showSettings();
                break;
            default:
                break;
        }
    }

    private void showSettings(){
        final String[] items = {"Log out","Settings","Feedback"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Setting").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                switch(items[i]){
                    case "Log out":
                        Utilities.logOut(ChildApplicantActivity.this);
                        break;
                    case "Settings":
                        break;
                    case "Feedback":
                        Utilities.startFragWith(ChildApplicantActivity.this, ChildApplicantActivity.class,"Feedback",null);
                        break;
                    default:
                        break;
                }
            }
        }).show();
    }
}
