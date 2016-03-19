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

import com.example.kyler.careersystem.R;
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
        Toast.makeText(getActivity().getApplicationContext(), bundle.getString("sendData"), Toast.LENGTH_LONG).show();
        companyImage = (ImageView) rootView.findViewById(R.id.company_information_image);
        companyName = (TextView) rootView.findViewById(R.id.company_information_companyname);
        companySize = (TextView) rootView.findViewById(R.id.company_information_companysize);
        companyAdress = (TextView) rootView.findViewById(R.id.company_information_companyaddress);
        companyPhone = (TextView) rootView.findViewById(R.id.company_information_companyphone);
        companyAbout = (TextView) rootView.findViewById(R.id.company_information_companyabout);

        try {
            JSONObject company = new JSONObject("{\n" +
                    "  \"company_image\": \"http://image.slidesharecdn.com/appleincpresentatioinslideshare-131122014841-phpapp02/95/apple-inc-presentatioin-1-638.jpg\",\n" +
                    "  \"company_name\": \"Enclave\",\n" +
                    "  \"company_size\": 200,\n" +
                    "  \"company_address\": \"443 Hoang Dieu, Da Nang, Viet Nam\",\n" +
                    "  \"company_phone\": \"0123123123\",\n" +
                    "  \"company_about\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\"\n" +
                    "}");
            if(company.has("company_image"))
                Picasso.with(getActivity().getApplicationContext()).load(company.getString("company_image")).into(companyImage);
            if(company.has("company_name"))
                companyName.setText(company.getString("company_name"));
            if(company.has("company_size"))
                companySize.setText(company.getInt("company_size")+"");
            if(company.has("company_address"))
                companyAdress.setText(company.getString("company_address"));
            if(company.has("company_phone"))
                companyPhone.setText(company.getString("company_phone"));
            if(company.has("company_about"))
                companyAbout.setText(company.getString("company_about"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
