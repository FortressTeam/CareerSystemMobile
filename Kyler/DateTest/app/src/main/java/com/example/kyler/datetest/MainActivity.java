package com.example.kyler.datetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText put;
    TextView result;
    Button get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        put = (EditText) findViewById(R.id.put);
        result = (TextView) findViewById(R.id.result);
        get = (Button) findViewById(R.id.change);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = null;
                String abc = null;
                try {
//                    date = new SimpleDateFormat("yyyy/mm/dd").parse(put.getText().toString());
                    date = new SimpleDateFormat("yyyy-MM-dd").parse("2016-02-19T00:00:00+0000");
                    SimpleDateFormat format2 =  new SimpleDateFormat("d / LLL - yyyy");
                    abc = format2.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                result.setText("Date :" + date.toString() + "\nTime: " + date.getTime() +"\nabc: "+abc);
            }
        });
    }
}
