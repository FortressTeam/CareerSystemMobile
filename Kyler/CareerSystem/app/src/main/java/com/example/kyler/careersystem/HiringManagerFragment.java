package com.example.kyler.careersystem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HiringManagerFragment extends Fragment implements View.OnClickListener, View.OnKeyListener {
    private Button loginNormal;
    private EditText username, password;

    public HiringManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hiring_manager, container, false);
        Utilities.hideSoftKeyboard(getActivity(),view.findViewById(R.id.hiringmanager_login_linearlayout));
        username = (EditText) view.findViewById(R.id.hiringmanager_login_username);
        password = (EditText) view.findViewById(R.id.hiringmanager_login_password);
        password.setOnKeyListener(this);
        loginNormal = (Button) view.findViewById(R.id.hiringmanager_loginnormal);
        loginNormal.setOnClickListener(this);
        return view;
    }

    private boolean checkValidLogin() {
        if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hiringmanager_loginnormal:
                if (checkValidLogin())
                    Utilities.doLoginNormal(getActivity(), username.getText().toString(), password.getText().toString(), 2);
                else
                    Toast.makeText(getActivity().getApplicationContext(), "username or password is missing", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        switch (view.getId()) {
            case R.id.hiringmanager_login_password:
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    if (checkValidLogin())
                        Utilities.doLoginNormal(getActivity(), username.getText().toString(), password.getText().toString(), 2);
                    else
                        Toast.makeText(getActivity().getApplicationContext(), "username or password is missing", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return false;
    }
}
