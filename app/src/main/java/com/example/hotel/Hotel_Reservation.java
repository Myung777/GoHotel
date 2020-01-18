package com.example.hotel;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.hotel.Hotel_List.hotelList;
import static com.example.hotel.ListAdapter.picture1;
import static com.example.hotel.Login.postion;
import static com.example.hotel.Register.jArrayLogin;
import static com.example.hotel.Register.loginData;
import static com.example.hotel.TabFragment1.choice;
import static com.example.hotel.TabFragment1.hotel;
import static com.example.hotel.TabFragment1.llike;

public class Hotel_Reservation extends AppCompatActivity {
    public  ArrayList<ReservationData> ReservationList = new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar1 = Calendar.getInstance();
    SimpleDateFormat sdf;
    JSONArray jsonArray,rArrayLogin;
    Button button;
    EditText et_Date1,et_Date;
    ImageView imageView;
    String userId1,userName1,userPhone,price1,price,resData;
    TextView hnameView,priceView,nameView,phoneView;
    RadioButton option1,option2;
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    DatePickerDialog.OnDateSetListener myDatePicker1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, month);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel__reservation);
        ReservationList.clear();

        SharedPreferences sf = getSharedPreferences("shared", 0);
        hotelList = sf.getString("hotelList", "");
        SharedPreferences log = getSharedPreferences("shared", 0);
        loginData = log.getString("loginData", "");
        SharedPreferences re = getSharedPreferences("shared", 0);
        resData = re.getString("resData", "");


        if (resData != null) {
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

                    ReservationData res = new ReservationData(picture, HotelName, Day, DayEnd, Room, ResName, ResPrice, ResId);
                    ReservationList.add(0, res); //첫 줄에 삽입
                    Log.d("log", String.valueOf(ReservationList));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            jArrayLogin = new JSONArray(loginData);
            {
                JSONObject jObject = jArrayLogin.getJSONObject(postion);
                userId1 = jObject.getString("userId");
                userName1 = jObject.getString("userName");
                userPhone = jObject.getString("userPh");
                nameView = (TextView) findViewById(R.id.name);
                phoneView = (TextView) findViewById(R.id.phone);

                nameView.setText(userName1);
                phoneView.setText(userPhone);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(LikeAdapter.like==2) {
            try {
                jsonArray = new JSONArray(hotelList);
                JSONObject jObject = jsonArray.getJSONObject(llike);
                picture1 = new File(jObject.getString("picture"));
                hotel = jObject.getString("hotel");
                price = jObject.getString("price");
                price1 = jObject.getString("price1");

                imageView = (ImageView) findViewById(R.id.hotelImage);
                hnameView = (TextView) findViewById(R.id.hotelName);
                priceView = (TextView) findViewById(R.id.price);

                imageView.setImageURI(Uri.fromFile(picture1));
                hnameView.setText(hotel);
                priceView.setText(price);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            LikeAdapter.like=0;
        }
        else {

            try {
                jsonArray = new JSONArray(hotelList);
                JSONObject jObject = jsonArray.getJSONObject(choice);
                picture1 = new File(jObject.getString("picture"));
                hotel = jObject.getString("hotel");
                price = jObject.getString("price");
                price1 = jObject.getString("price1");

                imageView = (ImageView) findViewById(R.id.hotelImage);
                hnameView = (TextView) findViewById(R.id.hotelName);
                priceView = (TextView) findViewById(R.id.price);

                imageView.setImageURI(Uri.fromFile(picture1));
                hnameView.setText(hotel);
                priceView.setText(price);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
        option1 = (RadioButton) findViewById(R.id.people2);
        option2 = (RadioButton) findViewById(R.id.people4);
        option1.setChecked(true);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.people2:
                        priceView.setText(price);
                        break;
                    case R.id.people4:
                        priceView.setText(price1);
                        break;

                }
            }
        });
        et_Date = (EditText) findViewById(R.id.Date);

        et_Date1 = (EditText) findViewById(R.id.DateEnd);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Hotel_Reservation.this, myDatePicker, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        et_Date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Hotel_Reservation.this, myDatePicker1, myCalendar1.get(Calendar.YEAR),
                        myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        button = (Button) findViewById(R.id.resButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = et_Date.getText().toString();
                    String date1 = et_Date1.getText().toString();
                    String room = option1.getText().toString();
                    String name = nameView.getText().toString();
                    String phone = phoneView.getText().toString();
                    String price = priceView.getText().toString();
                    if (!date.equals("체크인") && !date1.equals("체크아웃") && !name.equals("") && !phone.equals("")) {


                        ReservationData res = new ReservationData(picture1, hotel, date, date1, room, name, price, userId1);
                        ReservationList.add(0, res); //첫 줄에 삽입

                        try {
                            rArrayLogin = new JSONArray();//배열이 필요할때
                            for (int i = 0; i < ReservationList.size(); i++) {
                                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                sObject.put("HotelImage", ReservationList.get(i).getHotelImage());
                                sObject.put("HotelName", ReservationList.get(i).getHotelName());
                                sObject.put("Day", ReservationList.get(i).getDay());
                                sObject.put("DayEnd", ReservationList.get(i).getDayEnd());
                                sObject.put("Room", ReservationList.get(i).getRoom());
                                sObject.put("ResName", ReservationList.get(i).getResName());
                                sObject.put("ResPrice", ReservationList.get(i).getResPrice());
                                sObject.put("ResId", ReservationList.get(i).getResId());
                                rArrayLogin.put(sObject);

                                Log.d("JSON Test", rArrayLogin.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < ReservationList.size(); i++) {
                            SharedPreferences re = getSharedPreferences("shared", 0);
                            SharedPreferences.Editor editor = re.edit();//저장하려면 editor가 필요
                            editor.putString("resData", rArrayLogin.toString());
                            editor.commit(); //완료한다.

                        }
                        Toast.makeText(Hotel_Reservation.this, "호텔예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(Hotel_Reservation.this, "빈칸을 채워주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.Date);
        et_date.setText(sdf.format(myCalendar.getTime()));

    }
    private void updateLabel1() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date1 = (EditText) findViewById(R.id.DateEnd);
        et_date1.setText(sdf.format(myCalendar1.getTime()));
    }

}
