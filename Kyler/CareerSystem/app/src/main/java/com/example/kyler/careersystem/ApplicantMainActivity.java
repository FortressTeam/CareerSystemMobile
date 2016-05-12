package com.example.kyler.careersystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.SearchView;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.Applicant.SearchFragment;
import com.example.kyler.careersystem.Entities.Applicants;
import com.example.kyler.careersystem.Entities.Users;
import com.squareup.picasso.Picasso;

public class ApplicantMainActivity extends AppCompatActivity implements ListView.OnItemClickListener,View.OnClickListener,SearchView.OnQueryTextListener{
    ListView navigationViewMenu;
    private Handler mhHandler;
    private SearchView searchView;
    private Users users = Utilities.users;
    private Applicants applicants = Utilities.applicants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mhHandler = new Handler();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationViewMenu = (ListView) findViewById(R.id.navigation_view_menu);
        View navigationViewHeader = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        View navigationViewFooter = getLayoutInflater().inflate(R.layout.nav_footer_main, null);
        navigationViewMenu.addHeaderView(navigationViewHeader);
        navigationViewMenu.addFooterView(navigationViewFooter);
        Utilities.loadNavigationViewApplicant(this, navigationViewMenu);
        ImageView applicantImage = (ImageView) findViewById(R.id.nav_header_image);
        TextView applicantName = (TextView) findViewById(R.id.nav_header_name);
        TextView applicantEmail = (TextView) findViewById(R.id.nav_header_email);
        Picasso.with(this).load(UrlStatic.URLimg+"user_img/"+users.getUserAvatar()).into(applicantImage);
        applicantName.setText(applicants.getApplicantName());
        applicantEmail.setText(users.getUserEmail());
        LinearLayout navSettingControl = (LinearLayout) findViewById(R.id.nav_setting_control);
        navSettingControl.setOnClickListener(this);
        navigationViewMenu.setOnItemClickListener(this);
        Bundle bundle = getIntent().getBundleExtra("back");
        if(bundle!=null){
            int id = bundle.getInt("itemID");
            Utilities.displayViewApplicant(this, id);
        }
        else
            Utilities.displayViewApplicant(this, 1);
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
    protected void onResume() {
        if(applicants == null){
            this.onRestart();
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemSearch = menu.findItem(R.id.menu_search);
        searchView = (SearchView) itemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_search) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
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
                Utilities.displayViewApplicant(activity, id);
            }
        }, 300);
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
                        Utilities.logOut(ApplicantMainActivity.this);
                        break;
                    case "Settings":
                        break;
                    case "Feedback":
                        Utilities.startFragWith(ApplicantMainActivity.this, ChildApplicantActivity.class,"Feedback",null);
                        break;
                    default:
                        break;
                }
            }
        }).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Utilities.hideSoftKeyboard(this, findViewById(R.id.applicant_main_layout));
        Utilities.startFragWith(this,ChildApplicantActivity.class,"search",searchView.getQuery().toString());
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.onActionViewCollapsed();
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
