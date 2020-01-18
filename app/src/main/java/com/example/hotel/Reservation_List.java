package com.example.hotel;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class Reservation_List extends AppCompatActivity {
    private ReservationAdapter lAdapter;
    public static ArrayList<ReservationData> RarrayList;
    public static ImageView imageViewPicture1;
    private View view;

    private JSONArray rArrayLogin;
    public File hotlePicture;
    private String hotleId,hotelName;
    private int search;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation__list);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.resRecyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        RarrayList = new ArrayList<>();

        SharedPreferences loginID = getSharedPreferences("shared", 0);
        TabFragment2.login=loginID.getString("loginID","");
        SharedPreferences re = getSharedPreferences("shared", 0);
        String resData=re.getString("resData","");


        if(resData!=null) {
            try {
                rArrayLogin = new JSONArray(resData);
                for (int i = 0; i < rArrayLogin.length(); i++) {
                    JSONObject jObject = rArrayLogin.getJSONObject(i);
                    File picture = new File(jObject.getString("HotelImage"));
                    String HotelName = jObject.getString("HotelName");
                    String Day = jObject.getString("Day");
                    String DayEnd = jObject.getString("DayEnd");
                    String Room = jObject.getString("Room");
                    String ResName = jObject.getString("ResName");
                    String ResPrice = jObject.getString("ResPrice");
                    String ResId = jObject.getString("ResId");

                    if(TabFragment2.login.equals(ResId)) {
                        ReservationData res = new ReservationData(picture, HotelName, Day, DayEnd, Room, ResName, ResPrice, ResId);
                        RarrayList.add(0, res); //첫 줄에 삽입
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        lAdapter = new ReservationAdapter(RarrayList);
        mRecyclerView.setAdapter(lAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        ImageView btn = (ImageView) findViewById(R.id.logo);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.logo) {
                    // mRecyclerView.smoothScrollToPosition(0);

                }
            }
        });
    }

}
