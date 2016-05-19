package com.example.kyler.careersystem.HiringManager.customize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kyler.careersystem.Entities.PostsHasCurriculumVitaes;
import com.example.kyler.careersystem.Helper.OrientationHepler;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.UrlStatic;
import com.example.kyler.careersystem.Utilities;
import com.example.kyler.careersystem.WorkWithService.PostDataWithJsonCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kyler on 22/04/2016.
 */
public class SubmittedCVListViewAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity context;
    private ArrayList<SubmittedCVListViewItem> submittedCVListViewItems;
    private int ACCEPT = 1, REJECT = 2;
    private OrientationHepler orientationHepler;

    public SubmittedCVListViewAdapter(Activity context, ArrayList<SubmittedCVListViewItem> submittedCVListViewItems) {
        this.context = context;
        this.submittedCVListViewItems = submittedCVListViewItems;
    }

    @Override
    public int getCount() {
        return submittedCVListViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return submittedCVListViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.hiringmanager_submittedcv_listviewitem, null);
        }
        orientationHepler = new OrientationHepler();
        TextView submittedcv_listviewitem_applicantname = (TextView) view.findViewById(R.id.submittedcv_listviewitem_applicantname);
        TextView submittedcv_listviewitem_cvname = (TextView) view.findViewById(R.id.submittedcv_listviewitem_cvname);
        LinearLayout submittedcv_listviewitem_cv = (LinearLayout) view.findViewById(R.id.submittedcv_listviewitem_cv);
        final ImageView submittedcv_listviewitem_accept = (ImageView) view.findViewById(R.id.submittedcv_listviewitem_accept);
        final ImageView submittedcv_listviewitem_reject = (ImageView) view.findViewById(R.id.submittedcv_listviewitem_reject);
        if (submittedCVListViewItems.get(i).getPostsHasCurriculumVitaes().getAppliedCVStatus() == REJECT) {
            submittedcv_listviewitem_accept.setVisibility(View.GONE);
        } else if (submittedCVListViewItems.get(i).getPostsHasCurriculumVitaes().getAppliedCVStatus() == ACCEPT) {
            submittedcv_listviewitem_reject.setVisibility(View.GONE);
        } else {
            final int id = i;
            submittedcv_listviewitem_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doPost(id, 1, submittedcv_listviewitem_accept, submittedcv_listviewitem_reject);
                }
            });
            submittedcv_listviewitem_reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doPost(id, 2, submittedcv_listviewitem_reject, submittedcv_listviewitem_accept);
                }
            });
        }
        submittedcv_listviewitem_applicantname.setText(submittedCVListViewItems.get(i).getApplicantName());
        submittedcv_listviewitem_cvname.setText(submittedCVListViewItems.get(i).getCvName());
        submittedcv_listviewitem_applicantname.setOnClickListener(this);
        submittedcv_listviewitem_cv.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submittedcv_listviewitem_applicantname:
                break;
            case R.id.submittedcv_listviewitem_cv:
                break;
        }
    }

    private void doPost(final int id, final int status, final ImageView img1, final ImageView img2) {
        String message = "";
        if (status == REJECT) {
            message = "Do you want to reject this CV?";
        } else if (status == ACCEPT) {
            message = "Do you want to accept this CV?";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PostsHasCurriculumVitaes postsHasCurriculumVitaes = submittedCVListViewItems.get(id).getPostsHasCurriculumVitaes();
                        JSONObject jsSendData = new JSONObject();
                        try {
                            jsSendData.put("curriculum_vitae_id", postsHasCurriculumVitaes.getCurriculumVitaes());
                            jsSendData.put("post_id", postsHasCurriculumVitaes.getPostID());
                            jsSendData.put("applied_cv_status", status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final JSONObject sendData = jsSendData;
                        PostDataWithJsonCallback postDataWithJsonCallback = new PostDataWithJsonCallback(sendData, context) {
                            @Override
                            public void receiveData(Object result) {
                                if (!Utilities.isCreateUpdateSuccess((JSONObject) result)) {
                                    orientationHepler.displayViewHiringManager(context, 404);
                                } else {
                                    img1.setEnabled(false);
                                    img2.setVisibility(View.GONE);
                                    notifyDataSetChanged();
                                }
                            }
                        };
                        postDataWithJsonCallback.execute(UrlStatic.URLPostsHasCurriculumVitaes);
                    }
                })
                .setNegativeButton("No", null).show();
    }
}
