package com.example.myaceraspire.myapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    Spinner syear,smonth,sflat;
    Button bupdate,bgetdetails;
    String year,month,flat,myjson;
    DatabaseReference root= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        syear=(Spinner)findViewById(R.id.spyear);
        smonth=(Spinner)findViewById(R.id.spmonth);
        sflat=(Spinner)findViewById(R.id.spflat);
        String[] ayear={"2015","2016","2017"};
        String[] amonth={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String[] aflat={"G1","G2","G3","G4","G5","G6",
                "101","102","103","104","105","106",
                "201","202","203","204","205","206",
                "301","302","303","304","305","306",
                "401","402","403","404","405","406",
                "501"};
        ArrayAdapter aayear=new ArrayAdapter(this,android.R.layout.simple_spinner_item,ayear);
        ArrayAdapter aamonth=new ArrayAdapter(this,android.R.layout.simple_spinner_item,amonth);
        ArrayAdapter aaflat=new ArrayAdapter(this,android.R.layout.simple_spinner_item,aflat);
        syear.setAdapter(aayear);
        smonth.setAdapter(aamonth);
        sflat.setAdapter(aaflat);
        syear.setOnItemSelectedListener(this);
        smonth.setOnItemSelectedListener(this);
        sflat.setOnItemSelectedListener(this);

        bupdate=(Button)findViewById(R.id.btnupdate);
        bgetdetails=(Button)findViewById(R.id.btngetdetails);
        bupdate.setOnClickListener(this);
        bgetdetails.setOnClickListener(this);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*ConnectivityManager cm= (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info=cm.getActiveNetworkInfo();
                if(info !=null && info.isConnectedOrConnecting()){
                    Toast.makeText(MainActivity.this, "no network", Toast.LENGTH_SHORT).show();
                }*/
                Map<String,Object> map=(Map<String, Object>) dataSnapshot.getValue();
                JSONObject jroot=new JSONObject(map);
                myjson=jroot.toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnupdate:
                try {
                    final File f=new File(getFilesDir(),"json.txt");
                    PrintWriter fw=new PrintWriter(f);
                    fw.write(myjson);
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                            Toast.makeText(this, "Successfully Updated Data", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btngetdetails:
                Intent i=new Intent(getApplicationContext(),Details.class);
                i.putExtra("year",year);
                i.putExtra("month",month);
                i.putExtra("flat",flat);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spyear:
                year=adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.spmonth:
                month=adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.spflat:
                flat=adapterView.getItemAtPosition(i).toString();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}
