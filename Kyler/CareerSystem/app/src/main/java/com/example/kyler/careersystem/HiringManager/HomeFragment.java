package com.example.kyler.careersystem.HiringManager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.Entities.Users;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private ImageView companyImage;
    private TextView companyName,companySize,companyAdress,companyPhone,companyAbout,hiringManagerName;
//    private LinearLayout homeManagePost,homeAddPost,homeSubmittedResume,homeApointments,homeNotifications;
    private HiringManagers hiringManager = Utilities.hiringManagers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hiringmanager_home_fragment,container,false);
        hiringManagerName =(TextView) rootView.findViewById(R.id.home_hiring_manager_name);
        companyImage = (ImageView) rootView.findViewById(R.id.home_company_image);
        companyName = (TextView) rootView.findViewById(R.id.home_company_name);
        companySize = (TextView) rootView.findViewById(R.id.home_company_size);
        companyAdress = (TextView) rootView.findViewById(R.id.home_company_address);
        companyPhone = (TextView) rootView.findViewById(R.id.home_company_phone);
        companyAbout = (TextView) rootView.findViewById(R.id.home_company_about);
//        homeManagePost = (LinearLayout) rootView.findViewById(R.id.home_manage_post);
//        homeAddPost = (LinearLayout) rootView.findViewById(R.id.home_add_post);
//        homeSubmittedResume = (LinearLayout) rootView.findViewById(R.id.home_submitted_resume);
//        homeApointments = (LinearLayout) rootView.findViewById(R.id.home_appointments);
//        homeNotifications = (LinearLayout) rootView.findViewById(R.id.home_notifications);

        Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg+"company_img/"+hiringManager.getCompanyLogo()).into(companyImage);
        hiringManagerName.setText(hiringManager.getHiringManagerName());
        companyName.setText(hiringManager.getCompanyName());
        companySize.setText(hiringManager.getCompanySize()+"");
        companyAdress.setText(hiringManager.getCompanyAddress());
        companyAbout.setText(hiringManager.getCompanyAbout());
        companyPhone.setText(hiringManager.getHiringManagerPhone());
//        homeManagePost.setOnClickListener(this);
//        homeAddPost.setOnClickListener(this);
//        homeSubmittedResume.setOnClickListener(this);
//        homeApointments.setOnClickListener(this);
//        homeNotifications.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.home_manage_post:
//                Utilities.displayViewHiringManager(getActivity(),1);
//                break;
//            case R.id.home_add_post:
//                Utilities.startFragWith(getActivity(),ChildHiringManagerActivity.class,"addpost","");
//                break;
//            case R.id.home_submitted_resume:
//                break;
//            case R.id.home_appointments:
//                break;
//            case R.id.home_notifications:
//                break;
//
//        }
    }
}
