package com.example.hotel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.hotel.Hotel_List.hotelList;
import static com.example.hotel.Hotel_List.jArray1;
import static com.example.hotel.TabFragment1.loveData;

public class PopularHotel extends Fragment {
    public static ArrayList<PopularData> ParrayList;
    private View view;
    private Context mContext;
    private PopularAdapter lAdapter;
    private File picture1;
    private String hotel,hotelName;
    private JSONArray hsonArray;
    int count;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_popular_hotel, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.PoRecyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        ParrayList = new ArrayList<>();

        SharedPreferences sf = getActivity().getSharedPreferences("shared", 0);
        hotelList = sf.getString("hotelList", "");
        SharedPreferences loginID = getActivity().getSharedPreferences("shared", 0);
        TabFragment2.login = loginID.getString("loginID", "");
        SharedPreferences lo = getActivity().getSharedPreferences("shared", 0);
        loveData = lo.getString("loveData", "");

        if(hotelList!=null) {
            try {
                jArray1 = new JSONArray(hotelList);
                for (int i = 0; i < jArray1.length(); i++) {
                    JSONObject jObject = jArray1.getJSONObject(i);
                    picture1 = new File(jObject.getString("picture"));
                    hotel = jObject.getString("hotel");
                    count=0;
                    try {
                        hsonArray = new JSONArray(loveData);
                        for (int j = 0; j < hsonArray.length(); j++) {
                            JSONObject hObject = hsonArray.getJSONObject(j);
                            hotelName = hObject.getString("hotelName");
                            if(hotelName.equals(hotel)){
                                count+=1;
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    PopularData loveHotel1 = new PopularData(picture1, hotel,count);
                    ParrayList.add(loveHotel1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Comparator<PopularData> noDesc = new Comparator<PopularData>() {

            public int compare(PopularData item1, PopularData item2) {
                int ret = 0 ;

                if (item1.getLike() < item2.getLike())
                    ret = 1 ;
                else if (item1.getLike() == item2.getLike())
                    ret = 0 ;
                else
                    ret = -1 ;

                return ret ;

                // 위의 코드를 간단히 만드는 방법.
                // return (item2.getNo() - item1.getNo()) ;
            }
        } ;

        Collections.sort(ParrayList, noDesc) ;


        lAdapter = new PopularAdapter(ParrayList);
        mRecyclerView.setAdapter(lAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        return view;
    }
}
