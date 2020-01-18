package com.example.hotel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static com.example.hotel.Hotel_List.hotelList;
import static com.example.hotel.Hotel_List.jArray1;
import static com.example.hotel.MainActivity.tempFile1;
import static com.example.hotel.TabFragment1.choice;
import static com.example.hotel.TabFragment2.login;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {

    private ArrayList<OurData> mList1;
    private Context mContext;
    private final int GET_GALLERY_IMAGE = 200;
    public static File picture1;
    int i =0;




    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener { // 1. 리스너 추가
        protected TextView title;
        protected ImageView picture;
        protected TextView content;




        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.resHotel);
            this.picture = (ImageView) view.findViewById(R.id.itemImage);
            this.content = (TextView) view.findViewById(R.id.day);
            view.setOnCreateContextMenuListener(this); //2. 리스너 등록

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(),Hotel_main.class);
                        v.getContext().startActivity(intent);
                        choice=getAdapterPosition();
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 3. 메뉴 추가

            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);


        }

        // 4. 캔텍스트 메뉴 클릭시 동작을 설정

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {
                    case 1001:
                        if(login.equals("admin")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            View view = LayoutInflater.from(mContext).inflate(R.layout.create_box, null, false);
                            builder.setView(view);
                            final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                            final EditText dialog_hotel = (EditText) view.findViewById(R.id.dialog_hotel);
                            Hotel_List.imageViewPicture1 = (ImageView) view.findViewById(R.id.dialog_image);
                            final EditText dialog_price = (EditText) view.findViewById(R.id.dialog_price);
                            final EditText dialog_price1 = (EditText) view.findViewById(R.id.dialog_price1);
                            final EditText dialog_location = (EditText) view.findViewById(R.id.dialog_location);
                            final EditText dialog_facilities = (EditText) view.findViewById(R.id.dialog_facilities);
                            final EditText dialog_context = (EditText) view.findViewById(R.id.dialog_context);
                            final EditText dialog_context2 = (EditText) view.findViewById(R.id.dialog_context2);
                            final EditText dialog_context3 = (EditText) view.findViewById(R.id.dialog_context3);

                            Hotel_List.imageViewPicture1.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    ((Activity) mContext).startActivityForResult(intent, GET_GALLERY_IMAGE);
                                    i = 1;

                                }
                            });

                            dialog_hotel.setText(mList1.get(getAdapterPosition()).getNameText());
                            Hotel_List.imageViewPicture1.setImageURI(Uri.fromFile(mList1.get(getAdapterPosition()).getHotelImage()));
                            dialog_price.setText(mList1.get(getAdapterPosition()).getPriceText());
                            dialog_price1.setText(mList1.get(getAdapterPosition()).getPriceText1());
                            dialog_location.setText(mList1.get(getAdapterPosition()).getLocationText());
                            dialog_facilities.setText(mList1.get(getAdapterPosition()).getFacilitiesText());
                            dialog_context.setText(mList1.get(getAdapterPosition()).getHotelContext());
                            dialog_context2.setText(mList1.get(getAdapterPosition()).getLatitude());
                            dialog_context3.setText(mList1.get(getAdapterPosition()).getLongitude());

                            try {
                                jArray1 = new JSONArray(hotelList);
                                JSONObject jObject = jArray1.getJSONObject(getAdapterPosition());
                                picture1 = new File(jObject.getString("picture"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (i == 0) {
                                tempFile1 = picture1;
                            }

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

                                    mList1.set(getAdapterPosition(), ourData);
                                    notifyItemChanged(getAdapterPosition());
                                    i = 0;

                                    try {
                                        jArray1 = new JSONArray();//배열이 필요할때
                                        for (int i = 0; i < mList1.size(); i++) {
                                            JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                            sObject.put("picture", mList1.get(i).getHotelImage());
                                            sObject.put("hotel", mList1.get(i).getNameText());
                                            sObject.put("price", mList1.get(i).getPriceText());
                                            sObject.put("price1", mList1.get(i).getPriceText1());
                                            sObject.put("location", mList1.get(i).getLocationText());
                                            sObject.put("facilities", mList1.get(i).getFacilitiesText());
                                            sObject.put("context", mList1.get(i).getHotelContext());
                                            sObject.put("context2", mList1.get(i).getLatitude());
                                            sObject.put("context3", mList1.get(i).getLongitude());
                                            jArray1.put(sObject);
                                            Log.d("JSON Test", jArray1.toString());

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    for (int i = 0; i < mList1.size(); i++) {
                                        SharedPreferences sf = mContext.getSharedPreferences("shared", 0);
                                        SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                                        hotelList = jArray1.toString();
                                        editor.putString("hotelList", hotelList);
                                        editor.commit(); //완료한다.
                                    }
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();

                            break;
                        }
                        else {
                            Toast.makeText(mContext, "자신의 게시물에만 접근 가능합니다", Toast.LENGTH_SHORT).show();

                        }

                    case 1002:
                        if(login.equals("admin")) {
                            mList1.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), mList1.size());

                            if (mList1.size() == 0) {
                                hotelList = null;
                                SharedPreferences sf = mContext.getSharedPreferences("shared", 0);
                                SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                                editor.putString("hotelList", hotelList);
                                editor.commit(); //완료한다.
                                Log.d("JSON Test", jArray1.toString());
                            }

                            try {
                                jArray1 = new JSONArray();//배열이 필요할때
                                for (int i = 0; i < mList1.size(); i++) {
                                    JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                    sObject.put("picture", mList1.get(i).getHotelImage());
                                    sObject.put("hotel", mList1.get(i).getNameText());
                                    sObject.put("price", mList1.get(i).getPriceText());
                                    sObject.put("price1", mList1.get(i).getPriceText1());
                                    sObject.put("location", mList1.get(i).getLocationText());
                                    sObject.put("facilities", mList1.get(i).getFacilitiesText());
                                    sObject.put("context", mList1.get(i).getHotelContext());
                                    sObject.put("context2", mList1.get(i).getLatitude());
                                    sObject.put("context3", mList1.get(i).getLongitude());

                                    jArray1.put(sObject);
                                    Log.d("JSON Test", jArray1.toString());

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < mList1.size(); i++) {
                                SharedPreferences sf = mContext.getSharedPreferences("shared", 0);
                                SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                                hotelList = jArray1.toString();
                                editor.putString("hotelList", hotelList);
                                editor.commit(); //완료한다.
                            }
                            break;
                        }
                        else {
                            Toast.makeText(mContext, "자신의 게시물에만 접근 가능합니다", Toast.LENGTH_SHORT).show();

                        }
                }
                return true;
            }
        };
    }


//    public ListAdapter(ArrayList<Dictionary> list) {
//        this.mList = list;
//    }

    public ListAdapter(Context context, ArrayList<OurData> list) {
        this.mList1 = list;
        this.mContext = context;

    }



    @Override
    public ListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        ListAdapter.CustomViewHolder viewHolder = new ListAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.CustomViewHolder viewholder, int position) {

        viewholder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        viewholder.content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewholder.title.setGravity(Gravity.START);

        viewholder.content.setGravity(Gravity.START);


        viewholder.title.setText(mList1.get(position).getNameText());
        viewholder.picture.setImageURI(Uri.fromFile(mList1.get(position).getHotelImage()));
        viewholder.content.setText(mList1.get(position).getLocationText());



    }


    @Override
    public int getItemCount() {
        return (null != mList1 ? mList1.size() : 0);
    }


}
