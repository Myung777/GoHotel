package com.example.hotel;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

import static com.example.hotel.MainActivity.tempFile1;
import static com.example.hotel.TabFragment2.login;

public class Hotel_List extends Fragment {
    private ListAdapter lAdapter;
    public static ArrayList<OurData> JarrayList;
    public static ImageView imageViewPicture1;
    public static JSONArray jArray1;
    private View view;
    public static String hotelList;
    private Context mContext1;
    public static File picture1;
    private final int GET_GALLERY_IMAGE = 200;
    public static int coupon=0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext1 = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.activity_hotel__list, container, false);


        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.ListRecyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext1);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        JarrayList = new ArrayList<>();
        SharedPreferences sf = getActivity().getSharedPreferences("shared", 0);
        hotelList=sf.getString("hotelList","");
        SharedPreferences loginID =getActivity(). getSharedPreferences("shared", 0);
        login=loginID.getString("loginID","");


        if(hotelList!=null) {
            try {
                jArray1 = new JSONArray(hotelList);
                for (int i = 0; i < jArray1.length(); i++) {
                    JSONObject jObject = jArray1.getJSONObject(i);
                    picture1 = new File(jObject.getString("picture"));
                    String hotel = jObject.getString("hotel");
                    String price = jObject.getString("price");
                    String price1 = jObject.getString("price1");
                    String location = jObject.getString("location");
                    String facilities = jObject.getString("facilities");
                    String context = jObject.getString("context");
                    String context2 = jObject.getString("context2");
                    String context3 = jObject.getString("context3");

                    JarrayList.add(new OurData(picture1, hotel, price, price1, location, facilities,context,context2,context3));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        lAdapter = new ListAdapter(mContext1,JarrayList);
        mRecyclerView.setAdapter(lAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        ImageView btn = (ImageView) view.findViewById(R.id.logo);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.logo) {
                   // mRecyclerView.smoothScrollToPosition(0);

                }
            }
        });

        Button button =(Button)view.findViewById(R.id.develop);
        if(login.equals("admin"))
            button.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.create_box, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText dialog_hotel = (EditText) view.findViewById(R.id.dialog_hotel);
                imageViewPicture1 = (ImageView) view.findViewById(R.id.dialog_image);
                final EditText dialog_price = (EditText) view.findViewById(R.id.dialog_price);
                final EditText dialog_price1 = (EditText) view.findViewById(R.id.dialog_price1);
                final EditText dialog_location = (EditText) view.findViewById(R.id.dialog_location);
                final EditText dialog_facilities = (EditText) view.findViewById(R.id.dialog_facilities);
                final EditText dialog_context = (EditText) view.findViewById(R.id.dialog_context);
                final EditText dialog_context2 = (EditText) view.findViewById(R.id.dialog_context2);
                final EditText dialog_context3 = (EditText) view.findViewById(R.id.dialog_context3);
                imageViewPicture1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                        getActivity().startActivityForResult(intent, GET_GALLERY_IMAGE);
                    }
                });


                ButtonSubmit.setText("삽입");


                final AlertDialog dialog = builder.create();
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String strHotel = dialog_hotel.getText().toString();
                        String strPrice = dialog_price.getText().toString();
                        String strPrice1 = dialog_price1.getText().toString();
                        String strLocation = dialog_location.getText().toString();
                        String strFacilities = dialog_facilities.getText().toString();
                        String strContext = dialog_context.getText().toString();
                        String strContext2 = dialog_context2.getText().toString();
                        String strContext3 = dialog_context3.getText().toString();

                        OurData ourData = new OurData(tempFile1,strHotel,strPrice, strPrice1,strLocation,strFacilities,strContext,strContext2,strContext3);

                        Log.d(" Test", String.valueOf(tempFile1));

                        JarrayList.add(0, ourData); //첫 줄에 삽입
                        //mArrayList.add(dict); //마지막 줄에 삽입
                        lAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                        try {
                            jArray1 = new JSONArray();//배열이 필요할때
                            for (int i = 0; i < JarrayList.size(); i++) {
                                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                sObject.put("picture", JarrayList.get(i).getHotelImage());
                                sObject.put("hotel", JarrayList.get(i).getNameText());
                                sObject.put("price", JarrayList.get(i).getPriceText());
                                sObject.put("price1", JarrayList.get(i).getPriceText1());
                                sObject.put("location", JarrayList.get(i).getLocationText());
                                sObject.put("facilities", JarrayList.get(i).getFacilitiesText());
                                sObject.put("context", JarrayList.get(i).getHotelContext());
                                sObject.put("context2", JarrayList.get(i).getLatitude());
                                sObject.put("context3", JarrayList.get(i).getLongitude());
                                jArray1.put(sObject);
                                Log.d("JSON Test", jArray1.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < JarrayList.size(); i++) {
                            SharedPreferences sf = getActivity().getSharedPreferences("shared", 0);
                            SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                            hotelList = jArray1.toString();
                            editor.putString("hotelList", hotelList);
                            editor.commit(); //완료한다.
                        }
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        Log.d(this.getClass().getSimpleName(), "onDestroyView()");
        super.onDestroyView();
    }




}
