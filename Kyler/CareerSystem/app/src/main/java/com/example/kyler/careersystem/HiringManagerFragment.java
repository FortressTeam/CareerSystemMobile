package com.example.kyler.careersystem;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HiringManagerFragment extends Fragment {
    private Button loginNormal;


    public HiringManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hiring_manager,container,false);
        loginNormal = (Button) view.findViewById(R.id.hiringmanager_loginnormal);
        loginNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LoginData.class).putExtra("key",2);
                startActivity(intent);
            }
        });
        return view;
    }

}
