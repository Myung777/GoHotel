package com.example.hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static com.example.hotel.Hotel_List.hotelList;
import static com.example.hotel.Hotel_List.picture1;
import static com.example.hotel.Login.postion;
import static com.example.hotel.Register.jArrayLogin;
import static com.example.hotel.Register.loginData;
import static com.example.hotel.Register.userList;

public class TabFragment1 extends Fragment implements OnMapReadyCallback {
    public static ArrayList<LoveHotel> loveHotel = new ArrayList<>();
    View view;
    private JSONArray jsonArray,hsonArray;
    public static int choice;
    public static String hotel;
    private MapView mapView;
    TextView nameView,priceView,priceView1,locationView,facilitiesView,contextView,btnMap;
    ImageView imageView;
    ImageView likeButton;
    String userId,userName,userPw,userEmail,userPh,userLike,userId1,userName1,userPw1,userEmail1,userPh1,userLike1,hotleId,hotelName,hotleId1,hotelName1,context2,context3;
    File userPf,userPf1,hotlePicture;
    int on=1;
    Button butReservation;
    int search;
    public static int llike;
    public static String loveData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tap_fragment1,container,false);
        userList.clear();
        loveHotel.clear();
        mapView = view.findViewById(R.id.map_view);

        mapView.getMapAsync(this);


        btnMap=(TextView)view.findViewById(R.id.btnMap);
        butReservation=(Button)view.findViewById(R.id.butReservation);
        butReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Hotel_Reservation.class);
                startActivity(intent);


            }
        });

        SharedPreferences lo = getActivity().getSharedPreferences("shared", 0);
        loveData = lo.getString("loveData", "");
        Log.d("loveData", String.valueOf(loveData));
        SharedPreferences sf = getActivity().getSharedPreferences("shared", 0);
        hotelList=sf.getString("hotelList","");
        SharedPreferences log = getActivity().getSharedPreferences("shared", 0);
        loginData=log.getString("loginData","");

        if(LikeAdapter.like==1)
        {

            try {
                hsonArray = new JSONArray(loveData);
                    JSONObject jObject = hsonArray.getJSONObject(choice);
                    hotelName = jObject.getString("hotelName");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jsonArray = new JSONArray(hotelList);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObject = jsonArray.getJSONObject(i);
                    hotel = jObject.getString("hotel");

                    if(hotelName.equals(hotel)){
                        llike=i;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                jsonArray = new JSONArray(hotelList);
                    JSONObject jObject = jsonArray.getJSONObject(llike);
                    picture1 = new File(jObject.getString("picture"));
                    hotel = jObject.getString("hotel");
                    String price = jObject.getString("price");
                    String price1 = jObject.getString("price1");
                    String location = jObject.getString("location");
                    String facilities = jObject.getString("facilities");
                    String context = jObject.getString("context");
                    context2 = jObject.getString("context2");
                    context3 = jObject.getString("context3");
                    imageView = (ImageView) view.findViewById(R.id.hotelImage);
                    nameView = (TextView) view.findViewById(R.id.nameText);
                    priceView = (TextView) view.findViewById(R.id.priceText);
                    priceView1 = (TextView) view.findViewById(R.id.priceText1);
                    locationView = (TextView) view.findViewById(R.id.locationText);
                    facilitiesView = (TextView) view.findViewById(R.id.facilitiesText);
                    contextView = (TextView) view.findViewById(R.id.hotelContext);

                    imageView.setImageURI(Uri.fromFile(picture1));
                    nameView.setText(hotel);
                    priceView.setText(price);
                    priceView1.setText(price1);
                    locationView.setText(location);
                    facilitiesView.setText(facilities);
                    contextView.setText(context);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LikeAdapter.like=2;
        }
        else{
            try {
                jsonArray = new JSONArray(hotelList);
                JSONObject jObject = jsonArray.getJSONObject(choice);
                picture1 = new File(jObject.getString("picture"));
                hotel = jObject.getString("hotel");
                String price = jObject.getString("price");
                String price1 = jObject.getString("price1");
                String location = jObject.getString("location");
                String facilities = jObject.getString("facilities");
                String context = jObject.getString("context");
                context2 = jObject.getString("context2");
                context3 = jObject.getString("context3");
                imageView=(ImageView)view.findViewById(R.id.hotelImage);
                nameView =(TextView)view.findViewById(R.id.nameText);
                priceView =(TextView)view.findViewById(R.id.priceText);
                priceView1 =(TextView)view.findViewById(R.id.priceText1);
                locationView =(TextView)view.findViewById(R.id.locationText);
                facilitiesView =(TextView)view.findViewById(R.id.facilitiesText);
                contextView =(TextView)view.findViewById(R.id.hotelContext);

                imageView.setImageURI(Uri.fromFile(picture1));
                nameView.setText(hotel);
                priceView.setText(price);
                priceView1.setText(price1);
                locationView.setText(location);
                facilitiesView.setText(facilities);
                contextView.setText(context);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            jArrayLogin = new JSONArray(loginData);
            for (int i = 0; i < jArrayLogin.length(); i++) {
                JSONObject jObject = jArrayLogin.getJSONObject(i);

                 userId = jObject.getString("userId");
                 userName = jObject.getString("userName");
                 userPw = jObject.getString("userPw");
                 userEmail = jObject.getString("userEmail");
                 userPh = jObject.getString("userPh");
                 userPf = new File(jObject.getString("userPf"));
                 userLike = jObject.getString("userLike");

                User user = new User(userId, userName, userPw, userEmail, userPh, userPf, userLike);
                userList.add(user);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jArrayLogin = new JSONArray(loginData);
            {
                JSONObject jObject = jArrayLogin.getJSONObject(postion);
                userId1 = jObject.getString("userId");
                userName1 = jObject.getString("userName");
                userPw1 = jObject.getString("userPw");
                userEmail1 = jObject.getString("userEmail");
                userPh1 = jObject.getString("userPh");
                userPf1 = new File(jObject.getString("userPf"));
                userLike1 = jObject.getString("userLike");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(loveData!="") {
            try {
                hsonArray = new JSONArray(loveData);
                for (int i = 0; i < hsonArray.length(); i++) {
                    JSONObject jObject = hsonArray.getJSONObject(i);
                    hotleId = jObject.getString("userId");
                    hotelName = jObject.getString("hotelName");
                    hotlePicture = new File(jObject.getString("picture"));
                    LoveHotel loveHotel1 = new LoveHotel(hotleId, hotelName,hotlePicture);
                    loveHotel.add(loveHotel1);

                    if(hotleId.equals(userId1) && hotelName.equals(hotel)){
                        search=i;
                        Log.d("search", String.valueOf(search));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if(!loveData.equals("")) {
            try {
                hsonArray = new JSONArray(loveData);
                {
                    JSONObject jObject = hsonArray.getJSONObject(search);
                    hotleId1 = jObject.getString("userId");
                    hotelName1 = jObject.getString("hotelName");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        likeButton=(ImageView)view.findViewById(R.id.likeButton);

        if(!loveData.equals("")) {

            if (hotleId1.equals(userId1) && hotelName1.equals(hotel)) {
                likeButton.setImageResource(R.drawable.like);
                on = 0;
            } else {
                likeButton.setImageResource(R.drawable.binlike);
                on = 1;
            }
        }
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (on==1) {
                    likeButton.setImageResource(R.drawable.like);
                    on=0;
                    //User user = new User(userId1,userName1,userPw1,userEmail1,userPh1,userPf1,"dd");
                    //userList.set(postion, user);


                    LoveHotel loveHotel1 = new LoveHotel(userId1, hotel,picture1);
                    loveHotel.add(0, loveHotel1);
                    try {
                        hsonArray = new JSONArray();//배열이 필요할때
                        for (int i = 0; i < loveHotel.size(); i++) {
                            JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                            sObject.put("userId", loveHotel.get(i).getUserID());
                            sObject.put("hotelName", loveHotel.get(i).getHotelName());
                            sObject.put("picture", loveHotel.get(i).getHotelImage());
                            hsonArray.put(sObject);

                            Log.d("JSON Test", hsonArray.toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < loveHotel.size(); i++) {
                        SharedPreferences lo = getActivity().getSharedPreferences("shared", 0);
                        SharedPreferences.Editor editor = lo.edit();//저장하려면 editor가 필요
                        loveData=hsonArray.toString();
                        editor.putString("loveData", loveData);
                        editor.commit(); //완료한다.

                    }
                }
                else if (on==0) {
                    likeButton.setImageResource(R.drawable.binlike);
                    on=1;
                    //User user = new User(userId1,userName1,userPw1,userEmail1,userPh1,userPf1,"dd");
                    //userList.set(postion, user);

                    LoveHotel loveHotel1 = new LoveHotel(userId1,hotel,picture1);
                    loveHotel.remove(search);
                    try {
                        hsonArray = new JSONArray();//배열이 필요할때
                        for (int i = 0; i < loveHotel.size(); i++) {
                            JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                            sObject.put("userId", loveHotel.get(i).getUserID());
                            sObject.put("hotelName", loveHotel.get(i).getHotelName());
                            sObject.put("picture", loveHotel.get(i).getHotelImage());
                            hsonArray.put(sObject);
                            Log.d("JSON Test", hsonArray.toString());

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (loveHotel.size()==0) {
                        loveData = null;
                        SharedPreferences lo = getActivity().getSharedPreferences("shared", 0);
                        SharedPreferences.Editor editor = lo.edit();//저장하려면 editor가 필요
                        editor.putString("loveData", loveData);
                        editor.commit(); //완료한다.
                    }
                    for (int i = 0; i < loveHotel.size(); i++) {
                        SharedPreferences lo = getActivity().getSharedPreferences("shared", 0);
                        SharedPreferences.Editor editor = lo.edit();//저장하려면 editor가 필요
                        loveData = hsonArray.toString();
                        editor.putString("loveData", loveData);
                        editor.commit(); //완료한다.
                    }

                }
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HotelMap.class);
                startActivity(intent);
            }
        });
        return view;


    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Marker marker = new Marker();
        double latitude = Double.parseDouble(context2);
        double longitude = Double.parseDouble(context3);
        marker.setPosition(new LatLng(latitude,longitude));
        marker.setMap(naverMap);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
        Log.d("test", String.valueOf(latitude));
        naverMap.moveCamera(cameraUpdate);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setZoomGesturesEnabled(false);
        uiSettings.setScrollGesturesEnabled(false);

        marker.setCaptionText(hotel);
    }
}
