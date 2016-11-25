package com.example.myaceraspire.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Details extends AppCompatActivity {
TextView tv;
    String message,fund,main,water;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent=getIntent();
        String iyear=intent.getStringExtra("year").toLowerCase();
        String imonth=intent.getStringExtra("month").toLowerCase();
        String iflat=intent.getStringExtra("flat");


        tv=(TextView)findViewById(R.id.tv2);
        tv.setText("Data Not Found Please Go Back To Previous Screen And Update The Data");
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(getFilesDir(),"json.txt")));
            String str=br.readLine();
            JSONObject jroot=new JSONObject(str);

                JSONObject jyear=jroot.getJSONObject(iyear);
                message=jroot.getString("message");
                JSONObject jmonth=jyear.getJSONObject(imonth);
                JSONObject jflat=jmonth.getJSONObject(iflat);
                tv.setText(message+"\n");
                fund=String.valueOf(jflat.getInt("fund"));
                main= String.valueOf(jflat.getInt("main"));
                water=String.valueOf(jflat.getInt("water"));
               int total=jflat.getInt("fund")+jflat.getInt("main")+jflat.getInt("water");
                tv.append("\n Fund is : "+fund+"\n Water is: "+water+"\n Main is: "+main);
                tv.append("\n \n TOTAL :"+total);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
