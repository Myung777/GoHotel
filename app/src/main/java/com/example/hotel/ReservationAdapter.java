package com.example.hotel;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.CustomViewHolder> {

    private ArrayList<ReservationData> mList2;
    int i =0;

    public class CustomViewHolder extends RecyclerView.ViewHolder { // 1. 리스너 추가
        protected ImageView picture;
        protected TextView resHotel,resRoom,resName,day,dayEnd,resPrice;


        public CustomViewHolder(View view) {
            super(view);
            this.resHotel = (TextView) view.findViewById(R.id.resHotel);
            this.resRoom = (TextView) view.findViewById(R.id.resRoom);
            this.resName = (TextView) view.findViewById(R.id.resName);
            this.day = (TextView) view.findViewById(R.id.day);
            this.dayEnd = (TextView) view.findViewById(R.id.dayEnd);
            this.resPrice = (TextView) view.findViewById(R.id.resPrice);
            this.picture = (ImageView) view.findViewById(R.id.itemImage);

        }

    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_item, parent, false);

        ReservationAdapter.CustomViewHolder viewHolder = new ReservationAdapter.CustomViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
       // holder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);


       // holder.title.setGravity(Gravity.START);

        holder.resHotel.setText(mList2.get(position).getHotelName());
        holder.resRoom.setText(mList2.get(position).getRoom());
        holder.resName.setText(mList2.get(position).getResName());
        holder.day.setText(mList2.get(position).getDay());
        holder.dayEnd.setText(mList2.get(position).getDayEnd());
        holder.resPrice.setText(mList2.get(position).getResPrice());
        holder.picture.setImageURI(Uri.fromFile(mList2.get(position).getHotelImage()));

    }

    @Override
    public int getItemCount() {
            return (null != mList2 ? mList2.size() : 0);

    }
    public ReservationAdapter(ArrayList<ReservationData> list) {
        this.mList2 = list;
    }


}
