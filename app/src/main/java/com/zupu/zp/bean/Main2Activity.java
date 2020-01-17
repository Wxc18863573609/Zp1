package com.zupu.zp.bean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.zupu.zp.R;
import com.zupu.zp.utliss.CenterLayoutManager;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    RecyclerView towrecycel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        towrecycel= findViewById(R.id.aaa);
        Button btn666= findViewById(R.id.btn666);
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            list.add(i+"条");
        }
        CenterLayoutManager   centerLayoutManager = new CenterLayoutManager(this);
      //  centerLayoutManager.setOrientation(CenterLayoutManager.HORIZONTAL);
        towrecycel.setLayoutManager(centerLayoutManager);
        DaocterAdapters daocterAdapters = new DaocterAdapters(list, this);
        towrecycel.setAdapter(daocterAdapters);


        btn666.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(1+1+"");


            }
        });
    }
}
