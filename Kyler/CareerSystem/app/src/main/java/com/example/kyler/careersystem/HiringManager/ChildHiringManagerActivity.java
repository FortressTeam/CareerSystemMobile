package com.example.kyler.careersystem.HiringManager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kyler.careersystem.ApplicantMainActivity;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.HiringManager.ChildFragments.AddPostFragment;
import com.example.kyler.careersystem.HiringManager.ChildFragments.JobDetailFragment;
import com.example.kyler.careersystem.HiringManagerMainActivity;
import com.example.kyler.careersystem.LoginActivity;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.squareup.picasso.Picasso;

public class ChildHiringManagerActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private ListView navigationViewMenu;
    private String receiveData;
    private Users users = Utilities.users;
    private HiringManagers hiringManagers = Utilities.hiringManagers;

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
        Utilities.loadNavigationViewHiringManager(this, navigationViewMenu);
        ImageView hiringManagerImage = (ImageView) findViewById(R.id.nav_header_image);
        TextView hiringManagerName = (TextView) findViewById(R.id.nav_header_name);
        TextView hiringManagerEmail = (TextView) findViewById(R.id.nav_header_email);
        Picasso.with(this).load(UrlStatic.URLimg+"user_img/"+users.getUserAvatar()).into(hiringManagerImage);
        hiringManagerName.setText(hiringManagers.getHiringManagerName());
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
            case "editpost":
                fragment = new AddPostFragment();
                bundle.putString("sendData", receiveData);
                break;
            case "addpost":
                fragment = new AddPostFragment();
                bundle.putString("sendData", receiveData);
                break;
        }
        if(fragment!=null){
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.frame_main, fragment).commit();
        }
    }

    private void startActivity(int id){
        Intent intent = new Intent(this, HiringManagerMainActivity.class);
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
                        Utilities.clear();
                        startActivity(new Intent(ChildHiringManagerActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//                        UrlStatic.tokenAccess="";
                        finish();
                        break;
                    case "Settings":
                        break;
                    case "Feedback":
                        Utilities.startFragWith(ChildHiringManagerActivity.this, ChildHiringManagerActivity.class,"Feedback",null);
                        break;
                    default:
                        break;
                }
            }
        }).show();
    }
}
