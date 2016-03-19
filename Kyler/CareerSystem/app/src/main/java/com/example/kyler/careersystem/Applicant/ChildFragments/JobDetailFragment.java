package com.example.kyler.careersystem.Applicant.ChildFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyler.careersystem.Applicant.ChildApplicantActivity;
import com.example.kyler.careersystem.GetDataFromService.GetJsonPostDetail;
import com.example.kyler.careersystem.R;
import com.example.kyler.careersystem.Utilities;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyler on 09/03/2016.
 */
public class JobDetailFragment extends Fragment implements ObservableScrollViewCallbacks,Button.OnClickListener {
    private ObservableScrollView scrollView;
    private TextView jobDetailCompanyInfo,jobDetailTitle,jobDetailSalary,jobDetailRequired,jobDetailOverview,jobDetailMoreInfo;
    private Button btApply;
    private ImageView companyImage;
    private FloatingActionButton jobDetailFloatactionbuttonFavorite;

    private JSONObject jsJob1;


    public JobDetailFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.applicant_job_detail_fragment, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Job Detail");
        Bundle bundle = getArguments();
        Toast.makeText(getActivity().getApplicationContext(),bundle.getString("sendData"),Toast.LENGTH_LONG).show();
        companyImage = (ImageView) rootView.findViewById(R.id.job_detail_image);
        btApply = (Button) rootView.findViewById(R.id.job_detail_btapply);
        jobDetailFloatactionbuttonFavorite = (FloatingActionButton) rootView.findViewById(R.id.job_detail_floatactionbutton_favorite);
        companyImage.setOnClickListener(this);
        jobDetailFloatactionbuttonFavorite.setOnClickListener(this);
//        if(bundle.getBoolean("applied")) {
            btApply.setVisibility(View.VISIBLE);
            btApply.setOnClickListener(this);
//        }else{
//            btApply.setVisibility(View.GONE);
//        }
        jobDetailCompanyInfo = (TextView) rootView.findViewById(R.id.job_detail_companyinfo);
        jobDetailTitle = (TextView) rootView.findViewById(R.id.job_detail_title);
        jobDetailSalary = (TextView) rootView.findViewById(R.id.job_detail_salary);
        jobDetailRequired = (TextView) rootView.findViewById(R.id.job_detail_required);
        jobDetailOverview = (TextView) rootView.findViewById(R.id.job_detail_overview);
        jobDetailMoreInfo = (TextView) rootView.findViewById(R.id.job_detail_moreinfo);
        try {
            jsJob1= new JSONObject("{ \"post_id\": \"1\"," +
                    " \"post_title\": \"Job xxxx\"," +
                    " \"post_required\": \"5 years experience\"," +
                    " \"post_moreinfo\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\"," +
                    "  \"post_content\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\",\n" +
                    " \"post_salary\": 300," +
                    " \"post_image\": \"https://cdn1.iconfinder.com/data/icons/avatar-3/512/Pilot-128.png\"," +
                    " \"post_date\": \"12/12/2016\"," +
                    " \"post_status\": 1," +
                    " \"category_name\": \"Information Technology\"," +
                    " \"company_overview\": \"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\"," +
                    " \"company_name\": \"Enclave\" }");
            if(jsJob1.has("post_image"))
                Picasso.with(getActivity().getApplicationContext()).load((jsJob1.getString("post_image"))).into(companyImage);
            if(jsJob1.has("company_overview"))
                jobDetailCompanyInfo.setText(jsJob1.getString("company_overview"));
            if(jsJob1.has("post_title"))
                jobDetailTitle.setText(jsJob1.getString("post_title"));
            if(jsJob1.has("post_salary"))
                jobDetailSalary.setText(jsJob1.getString("post_salary"));
            if(jsJob1.has("post_required"))
                jobDetailRequired.setText(jsJob1.getString("post_required"));
            if(jsJob1.has("post_content"))
                jobDetailOverview.setText(jsJob1.getString("post_content"));
            if(jsJob1.has("post_moreinfo"))
                jobDetailMoreInfo.setText(jsJob1.getString("post_moreinfo"));
//            new GetJsonPostDetail(getActivity(),jobDetailTitle,jobDetailSalary,jobDetailOverview).execute("http://192.168.11.108/CareerSystemWebBased/career_system/api/v1/posts/2.json");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        new Jsonworks().execute("http://192.168.11.108/CareerSystemWebBased/career_system/api/v1/posts.json");

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.job_detail_scrollview);
        scrollView.setScrollViewCallbacks(this);
        return rootView;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab == null) {
            return;
        }
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.job_detail_btapply:
                Toast.makeText(getActivity().getApplicationContext(), "You applied this job successfull!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.job_detail_floatactionbutton_favorite:
                Toast.makeText(getActivity().getApplicationContext(), "FloatActionButton!", Toast.LENGTH_SHORT).show();
                jobDetailFloatactionbuttonFavorite.setImageResource(R.drawable.starfollow);
                break;
            case R.id.job_detail_image:
                Utilities.startFragWith(getActivity(), ChildApplicantActivity.class, "companydetail", "Hello, I am Kyler!");
                break;
            default:
                break;
        }
    }

//    class Jsonworks extends AsyncTask<String,Void,String>{
//        TextView postTitle,postContent,postSalary;
//
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(getActivity());
//            pDialog.setMessage("Loading profile ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            StringBuilder result = new StringBuilder();
//            BufferedReader reader = null;
//            HttpURLConnection connection = null;
//            try {
//                URL url = new URL(strings[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                InputStream iS = connection.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(iS));
//                String line = "";
//                while((line=reader.readLine())!=null){
//                    result.append(line);
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if(connection!=null)
//                    connection.disconnect();
//                if(reader!=null)
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//            }
//            return result.toString();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            pDialog.dismiss();
//            try {
//                JSONObject parent = new JSONObject(s);
//                JSONArray jsonArray = new JSONArray(parent.getString("posts"));
//                JSONObject jsonObject = jsonArray.getJSONObject(1);
//                postTitle = (TextView) getActivity().findViewById(R.id.job_detail_title);
//                postContent = (TextView) getActivity().findViewById(R.id.job_detail_overview);
//                postSalary = (TextView) getActivity().findViewById(R.id.job_detail_salary);
//                postTitle.setText(jsonObject.getString("post_title"));
//                postContent.setText(jsonObject.getString("post_content"));
//                postSalary.setText(jsonObject.getString("post_salary"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
