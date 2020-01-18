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
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.CustomViewHolder> {

    private ArrayList<PopularData> mList2;
    private Context mContext;
    private final int GET_GALLERY_IMAGE = 200;
    public static File picture1;
    int i =0;

    public class CustomViewHolder extends RecyclerView.ViewHolder { // 1. 리스너 추가
        protected TextView title;
        protected ImageView picture;
        protected TextView content;

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.resHotel);
            this.picture = (ImageView) view.findViewById(R.id.itemImage);
            this.content = (TextView) view.findViewById(R.id.day);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), Hotel_main.class);
                    v.getContext().startActivity(intent);

                }
            });
        }

    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_item, parent, false);

        PopularAdapter.CustomViewHolder viewHolder = new PopularAdapter.CustomViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        holder.content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        holder.title.setGravity(Gravity.START);
        holder.content.setGravity(Gravity.START);

        holder.title.setText(mList2.get(position).getNameText());
        holder.picture.setImageURI(Uri.fromFile(mList2.get(position).getHotelImage()));
        holder.content.setText(""+mList2.get(position).getLike());
    }

    @Override
    public int getItemCount() {
            return (null != mList2 ? mList2.size() : 0);

    }
    public PopularAdapter(ArrayList<PopularData> list) {
        this.mList2 = list;
    }


}
