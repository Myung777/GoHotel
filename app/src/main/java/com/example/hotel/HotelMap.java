package com.example.hotel;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hotel.Hotel_List.hotelList;
import static com.example.hotel.TabFragment1.choice;
import static com.example.hotel.TabFragment1.hotel;

public class HotelMap extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    String context2,context3;
    JSONArray jsonArray;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_map);

        mapView = findViewById(R.id.map_view1);

        mapView.getMapAsync(this);
        SharedPreferences sf = getSharedPreferences("shared", 0);
        hotelList=sf.getString("hotelList","");

        try {
            jsonArray = new JSONArray(hotelList);
            JSONObject jObject = jsonArray.getJSONObject(choice);
            context2 = jObject.getString("context2");
            context3 = jObject.getString("context3");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Marker marker = new Marker();

        double latitude = Double.parseDouble(context2);
        double longitude = Double.parseDouble(context3);

        marker.setPosition(new LatLng(latitude,longitude));
        marker.setMap(naverMap);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
        naverMap.moveCamera(cameraUpdate);

        marker.setCaptionText(hotel);
    }
}
