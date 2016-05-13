package com.example.kyler.careersystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Users;
import com.squareup.picasso.Picasso;

public class HiringManagerMainActivity extends AppCompatActivity implements View.OnClickListener,ListView.OnItemClickListener {

    private Handler mhHandler;
    private Users users = Utilities.users;
    private HiringManagers hiringManagers = Utilities.hiringManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiringmanager_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mhHandler = new Handler();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Utilities.navigationViewMenu = (ListView) findViewById(R.id.navigation_view_menu);
        View navigationViewHeader = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        View navigationViewFooter = getLayoutInflater().inflate(R.layout.nav_footer_main, null);
        Utilities.navigationViewMenu.addHeaderView(navigationViewHeader);
        Utilities.navigationViewMenu.addFooterView(navigationViewFooter);
        Utilities.loadNavigationViewHiringManager(this);
        ImageView hiringManagerImage = (ImageView) findViewById(R.id.nav_header_image);
        TextView hiringManagerName = (TextView) findViewById(R.id.nav_header_name);
        TextView hiringManagerEmail = (TextView) findViewById(R.id.nav_header_email);
        Picasso.with(this).load(UrlStatic.URLimg+"user_img/"+users.getUserAvatar()).into(hiringManagerImage);
        hiringManagerName.setText(hiringManagers.getHiringManagerName());
        hiringManagerEmail.setText(users.getUserEmail());
        LinearLayout navSettingControl = (LinearLayout) findViewById(R.id.nav_setting_control);
        navSettingControl.setOnClickListener(this);
        Utilities.navigationViewMenu.setOnItemClickListener(this);
        Bundle bundle = getIntent().getBundleExtra("back");
        if(bundle!=null){
            int id = bundle.getInt("itemID");
            Utilities.displayViewHiringManager(this, id);
        }
        else
            Utilities.displayViewHiringManager(this, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    moveTaskToBack(true);
                }
            }).setNegativeButton("No",null).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        final int id = i;
        final Activity activity = this;
        mhHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utilities.displayViewHiringManager(activity, id);
            }
        },300);
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
                        Utilities.logOut(HiringManagerMainActivity.this);
                        break;
                    case "Settings":
                        break;
                    case "Feedback":
                        Utilities.startFragWith(HiringManagerMainActivity.this, ChildApplicantActivity.class,"Feedback",null);
                        break;
                    default:
                        break;
                }
            }
        }).show();
    }
}
