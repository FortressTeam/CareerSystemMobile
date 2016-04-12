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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class HiringManagerFragment extends Fragment implements View.OnClickListener {
    private Button loginNormal;
    private EditText username,password;

    public HiringManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hiring_manager,container,false);
        username = (EditText) view.findViewById(R.id.hiringmanager_login_username);
        password = (EditText) view.findViewById(R.id.hiringmanager_login_password);
        loginNormal = (Button) view.findViewById(R.id.hiringmanager_loginnormal);
        loginNormal.setOnClickListener(this);
        return view;
    }

    private void doLoginNormal(){
        JSONObject sendData = new JSONObject();
        try{
            sendData.put("username",username.getText().toString());
            sendData.put("password",password.getText().toString());
            JSONObject result = new PostDataWithJson(sendData,getActivity()).execute(UrlStatic.URLSignin).get();
            if(result!=null&&result.getString("message").equals("Success")){
                if(result.getJSONObject("user").getInt("group_id")==2) {
//                    UrlStatic.tokenAccess = result.getJSONObject("data").getString("token");
                    Toast.makeText(getActivity().getApplicationContext(), "Login success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginData.class).putExtra("key", 2);
                    Bundle bundle = new Bundle();
                    bundle.putString("jsuser",result.getJSONObject("user").toString());
                    intent.putExtra("sendData", bundle);
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

    private boolean checkValidLogin(){
        if(username.getText().toString().trim().equals("")||password.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hiringmanager_loginnormal:
                if(checkValidLogin())
                    doLoginNormal();
                else
                    Toast.makeText(getActivity().getApplicationContext(),"username or password is missing",Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
    }
}
