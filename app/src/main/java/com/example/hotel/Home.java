package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.example.hotel.TabFragment1.choice;

public class Home extends Fragment {
    private View view;
    Button btnCall;
    ScrollView scrollView;
    private static Handler mHandler;
    ImageView adImage;
    int i=0;
    boolean isThread = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        i=1;
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        adImage = view.findViewById(R.id.adImage);
        adImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0) {
                    choice=2;
                    Intent intent = new Intent(getActivity(),Hotel_main.class);
                    startActivity(intent);

                }
                else if(i==1){
                    choice=0;
                    Intent intent = new Intent(getActivity(),Hotel_main.class);
                    startActivity(intent);

                }
                else if(i==2){
                    choice=1;
                    Intent intent = new Intent(getActivity(),Hotel_main.class);
                    startActivity(intent);

                }

            }
        });
        ImageView btn = (ImageView) view.findViewById(R.id.logo);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.logo) {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if(i==0) {
                    adImage.setImageResource(R.drawable.hotelthe);
                    i=1;

                }
                else if(i==1){
                    adImage.setImageResource(R.drawable.hotelgrand);
                    i=2;

                }
                else if(i==2){
                    adImage.setImageResource(R.drawable.hotelcon);
                    i=0;

                }

            }
        };



        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
        isThread=true;
        return view;

    }
    class NewRunnable implements Runnable {
        @Override
        public void run() {
            while (isThread) {

                try {
                    Thread.sleep(3000);
                    Log.d("tt", String.valueOf(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessage(0);
            }
        }
    }
    @Override
    public void onDestroyView() {
        Log.d(this.getClass().getSimpleName(), "onDestroyView()");
        isThread=false;
        super.onDestroyView();

    }



}
