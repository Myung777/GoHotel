package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyPage extends Fragment {

    private View view;
    ImageView btnLogout,btnLike,btnRes;
    TextView txt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mypage,container,false);

        btnLogout = (ImageView) view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnLogout:
                        //FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(),Login.class);
                        startActivity(intent);
                        getActivity().finish();
                        break;
                }
            }
        });
        btnLike = (ImageView) view.findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),Like_List.class);
                        startActivity(intent);

            }
        });
        btnRes = (ImageView) view.findViewById(R.id.btnRes);
        btnRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Reservation_List.class);
                startActivity(intent);

            }
        });

        return view;
    }
}
