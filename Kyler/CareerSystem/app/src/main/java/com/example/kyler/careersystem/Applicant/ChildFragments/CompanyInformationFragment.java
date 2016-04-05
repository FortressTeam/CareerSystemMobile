package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kyler.careersystem.Entities.HiringManagers;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class CompanyInformationFragment extends Fragment implements View.OnClickListener{
    private ImageView companyImage;
    private TextView companyName,companySize,companyAdress,companyPhone,companyAbout;
    private Button subscribe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_company_information_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Company Information");
        Bundle bundle = getArguments();
        JSONObject jsonreceive = null;
        try {
            jsonreceive = new JSONObject(bundle.getString("sendData"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        companyImage = (ImageView) rootView.findViewById(R.id.company_information_image);
        companyName = (TextView) rootView.findViewById(R.id.company_information_companyname);
        companySize = (TextView) rootView.findViewById(R.id.company_information_companysize);
        companyAdress = (TextView) rootView.findViewById(R.id.company_information_companyaddress);
        companyPhone = (TextView) rootView.findViewById(R.id.company_information_companyphone);
        companyAbout = (TextView) rootView.findViewById(R.id.company_information_companyabout);
        HiringManagers hiringManager = new HiringManagers(jsonreceive);
        Picasso.with(getActivity().getApplicationContext()).load(UrlStatic.URLimg+"company_img/"+hiringManager.getCompanyLogo()).into(companyImage);
        companyName.setText(hiringManager.getCompanyName());
        companySize.setText(hiringManager.getCompanySize()+"");
        companyAdress.setText(hiringManager.getCompanyAddress());
        companyAbout.setText(hiringManager.getCompanyAbout());
        companyPhone.setText(hiringManager.getHiringManagerPhone());

        subscribe = (Button) rootView.findViewById(R.id.company_information_subscribe);
        subscribe.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.company_information_subscribe:
                Toast.makeText(getActivity().getApplicationContext(),companyName.getText()+" has "+companySize.getText()+" staff",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
    }
}
