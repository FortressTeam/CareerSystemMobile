package com.example.kyler.careersystem.Applicant.Customize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.WorkWithService.DeleteDataWithJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by kyler on 05/04/2016.
 */
public class HobbieListViewAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<HobbieListViewItem> hobbieListViewItems;
    private boolean isDialogMode;
    private boolean hideButton;

    public HobbieListViewAdapter(Activity context, ArrayList<HobbieListViewItem> hobbieListViewItems, boolean isDialogMode, boolean hideButton) {
        this.context = context;
        this.hobbieListViewItems = hobbieListViewItems;
        this.isDialogMode = isDialogMode;
        this.hideButton = hideButton;
    }

    @Override
    public int getCount() {
        return hobbieListViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return hobbieListViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.applicant_myresume_hobbie_listviewitem,null);
        }
        TextView myresume_hobbie_name_listviewitem = (TextView) view.findViewById(R.id.myresume_hobbie_name_listviewitem);
        ImageView myresume_hobbie_delete_listviewitem = (ImageView) view.findViewById(R.id.myresume_hobbie_delete_listviewitem);
        ImageView myresume_hobbie_icon_listviewitem = (ImageView) view.findViewById(R.id.myresume_hobbie_icon_listviewitem);
        if(isDialogMode)
            myresume_hobbie_icon_listviewitem.setVisibility(View.INVISIBLE);
        else
            myresume_hobbie_icon_listviewitem.setVisibility(View.VISIBLE);
        myresume_hobbie_name_listviewitem.setText(hobbieListViewItems.get(i).getHobbieName());
        if(hideButton)
            myresume_hobbie_delete_listviewitem.setVisibility(View.INVISIBLE);
        else
            myresume_hobbie_delete_listviewitem.setVisibility(View.VISIBLE);
        myresume_hobbie_delete_listviewitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int applicantID = hobbieListViewItems.get(i).getApplicantID();
                final int hobbieID = hobbieListViewItems.get(i).getHobbieID();
                final int listViewID = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    JSONObject jsResult = new DeleteDataWithJson(context).execute(UrlStatic.URLApplicantsHasHobbies+"?applicant_id="+applicantID+"&hobby_id="+hobbieID).get();
                                    if(jsResult.getString("message").equals("Deleted")){
                                        hobbieListViewItems.remove(listViewID);
                                        notifyDataSetChanged();
                                        Toast.makeText(context.getApplicationContext(), "Delete success", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(context.getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("No",null).show();
            }
        });
        return view;
    }
}
