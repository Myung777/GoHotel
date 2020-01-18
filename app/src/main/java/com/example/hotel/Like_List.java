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

import static com.example.hotel.TabFragment1.loveData;

public class Like_List extends AppCompatActivity {
    private LikeAdapter lAdapter;
    public static ArrayList<LikeData> LarrayList;
    public static ImageView imageViewPicture1;
    private View view;

    private JSONArray hsonArray;
    public File hotlePicture;
    private String hotleId,hotelName;
    private int search;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like__list);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.LikeRecyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        LarrayList = new ArrayList<>();

        SharedPreferences sf = getSharedPreferences("shared", 0);
        Hotel_List.hotelList=sf.getString("hotelList","");
        SharedPreferences loginID = getSharedPreferences("shared", 0);
        TabFragment2.login=loginID.getString("loginID","");
        SharedPreferences lo = getSharedPreferences("shared", 0);
        loveData=lo.getString("loveData","");

        if(loveData!=null) {
            try {
                hsonArray = new JSONArray(loveData);
                for (int i = 0; i < hsonArray.length(); i++) {
                    JSONObject jObject = hsonArray.getJSONObject(i);
                    hotleId = jObject.getString("userId");
                    hotelName = jObject.getString("hotelName");
                    hotlePicture = new File(jObject.getString("picture"));


                    if(TabFragment2.login.equals(hotleId))
                    {
                        LikeData loveHotel1 = new LikeData(hotlePicture, hotelName);
                        LarrayList.add(loveHotel1);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



        lAdapter = new LikeAdapter(LarrayList);
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
