package com.example.hotel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import static com.example.hotel.TabFragment1.choice;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.CustomViewHolder> {

    private ArrayList<LikeData> mList2;
    private Context mContext;
    public static int like;
    private final int GET_GALLERY_IMAGE = 200;
    public static File picture1;
    int i =0;

    public class CustomViewHolder extends RecyclerView.ViewHolder { // 1. 리스너 추가
        protected TextView title;
        protected ImageView picture;
        public void replaceFragment(Fragment fragment){

        }

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.resHotel);
            this.picture = (ImageView) view.findViewById(R.id.itemImage);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Hotel_main.class);
                    v.getContext().startActivity(intent);
                    choice=getAdapterPosition();
                    like=1;
                }
            });
        }

    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.like_item, parent, false);

        LikeAdapter.CustomViewHolder viewHolder = new LikeAdapter.CustomViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


        holder.title.setGravity(Gravity.START);


        holder.title.setText(mList2.get(position).getNameText());
        holder.picture.setImageURI(Uri.fromFile(mList2.get(position).getHotelImage()));
    }

    @Override
    public int getItemCount() {
            return (null != mList2 ? mList2.size() : 0);

    }
    public LikeAdapter(ArrayList<LikeData> list) {
        this.mList2 = list;
    }


}
