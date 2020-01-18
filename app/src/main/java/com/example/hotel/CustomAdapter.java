package com.example.hotel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
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
import java.util.Locale;

import static com.example.hotel.Hotel_main.tempFile;
import static com.example.hotel.TabFragment1.hotel;
import static com.example.hotel.TabFragment2.jArray;
import static com.example.hotel.TabFragment2.jsonProfile;
import static com.example.hotel.TabFragment2.login;
import static com.example.hotel.TabFragment2.mArrayList;
import static com.example.hotel.TabFragment2.title1;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>  {

    private ArrayList<Dictionary> mList;
    private Context mContext;
    private final int GET_GALLERY_IMAGE = 200;
    public static File picture1;
    public static File profile1;
    int i =0;
    private ArrayList<Dictionary> arrayList;




    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener { // 1. 리스너 추가
        protected TextView title;
        protected ImageView picture;
        protected TextView content;
        protected TextView timeText;
        protected TextView idText;
        protected ImageView proFile;



        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title_listitem);
            this.picture = (ImageView) view.findViewById(R.id.imageView);
            this.content = (TextView) view.findViewById(R.id.content_listitem);
            this.timeText= (TextView)view.findViewById(R.id.dateNow);
            this.idText= (TextView)view.findViewById(R.id.logId);
            this.proFile = (ImageView) view.findViewById(R.id.idImageView);
            view.setOnCreateContextMenuListener(this); //2. 리스너 등록
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
                        if(TabFragment2.count==1) {
                            if (login.equals(idText.getText().toString())) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                View view = LayoutInflater.from(mContext).inflate(R.layout.edit_box, null, false);
                                builder.setView(view);
                                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                                final EditText editTextTitle = (EditText) view.findViewById(R.id.edittext_dialog_title);
                                TabFragment2.imageViewPicture = (ImageView) view.findViewById(R.id.imageView2);
                                final EditText editTextContent = (EditText) view.findViewById(R.id.edittext_dialog_content);

                                TabFragment2.imageViewPicture.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Intent.ACTION_PICK);
                                        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                        ((Activity) mContext).startActivityForResult(intent, GET_GALLERY_IMAGE);
                                        i = 1;

                                    }
                                });

                                editTextTitle.setText(mList.get(getAdapterPosition()).getTitle());
                                TabFragment2.imageViewPicture.setImageURI(Uri.fromFile(mList.get(getAdapterPosition()).getPicture()));
                                editTextContent.setText(mList.get(getAdapterPosition()).getContent());

                                try {
                                    jArray = new JSONArray(title1);
                                    JSONObject jObject = jArray.getJSONObject(getAdapterPosition());
                                    picture1 = new File(jObject.getString("picture"));
                                    profile1 = new File(jObject.getString("profile"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (i == 0) {
                                    tempFile = picture1;
                                }

                                final AlertDialog dialog = builder.create();
                                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        String strID = editTextTitle.getText().toString();
                                        String strContent = editTextContent.getText().toString();
                                        String strTime = timeText.getText().toString();
                                        String strId = idText.getText().toString();


                                        Dictionary dict = new Dictionary(strID, tempFile, strContent, strTime, strId, jsonProfile, hotel);

                                        mList.set(getAdapterPosition(), dict);
                                        notifyItemChanged(getAdapterPosition());
                                        i = 0;

                                        try {
                                            jArray = new JSONArray();//배열이 필요할때
                                            for (int i = 0; i < mList.size(); i++) {
                                                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                                sObject.put("title", mList.get(i).getTitle());
                                                sObject.put("picture", mList.get(i).getPicture());
                                                sObject.put("content", mList.get(i).getContent());
                                                sObject.put("time", mList.get(i).getTime());
                                                sObject.put("id", mList.get(i).getId());
                                                sObject.put("profile", mList.get(i).getProfile());
                                                jArray.put(sObject);

                                                Log.d("JSON Test", jArray.toString());

                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        for (int i = 0; i < mArrayList.size(); i++) {
                                            SharedPreferences sf = mContext.getSharedPreferences("shared", 0);
                                            SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                                            title1 = jArray.toString();
                                            editor.putString("title1", title1);
                                            editor.commit(); //완료한다.
                                        }
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();

                                break;
                            } else {
                                Toast.makeText(mContext, "자신의 게시물에만 접근 가능합니다", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(mContext, "모든 호텔이 나오게 돋보기 버튼을 눌러주신후 수정이 가능합니다.", Toast.LENGTH_SHORT).show();

                        }

                    case 1002:
                        if(TabFragment2.count==1) {
                            if (login.equals(idText.getText().toString())) {
                                mList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                                notifyItemRangeChanged(getAdapterPosition(), mList.size());

                                if (mList.size() == 0) {
                                    title1 = null;
                                    SharedPreferences sf = mContext.getSharedPreferences("shared", 0);
                                    SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                                    editor.putString("title1", title1);
                                    editor.commit(); //완료한다.
                                    Log.d("JSON Test", jArray.toString());
                                }

                                try {
                                    jArray = new JSONArray();//배열이 필요할때
                                    for (int i = 0; i < mList.size(); i++) {
                                        JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                        sObject.put("title", mList.get(i).getTitle());
                                        sObject.put("picture", mList.get(i).getPicture());
                                        sObject.put("content", mList.get(i).getContent());
                                        sObject.put("time", mList.get(i).getTime());
                                        sObject.put("id", mList.get(i).getId());
                                        sObject.put("profile", mList.get(i).getProfile());
                                        sObject.put("hotel", mList.get(i).getHotel());
                                        jArray.put(sObject);

                                        Log.d("JSON Test", jArray.toString());
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                for (int i = 0; i < mArrayList.size(); i++) {
                                    SharedPreferences sf = mContext.getSharedPreferences("shared", 0);
                                    SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                                    title1 = jArray.toString();
                                    editor.putString("title1", title1);
                                    editor.commit(); //완료한다.
                                }
                                break;
                            } else {
                                Toast.makeText(mContext, "자신의 게시물에만 접근 가능합니다", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else {
                            Toast.makeText(mContext, "모든 호텔이 나오게 돋보기 버튼을 눌러주신후 수정이 가능합니다.", Toast.LENGTH_SHORT).show();

                        }
                }
                return true;
            }
        };
    }


//    public CustomAdapter(ArrayList<Dictionary> list) {
//        this.mList = list;
//    }

    public CustomAdapter(Context context, ArrayList<Dictionary> list) {
        this.mList = list;
        this.mContext = context;
        arrayList = new ArrayList<Dictionary>();
        arrayList.addAll(list);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mList.clear();
        Log.d("list", String.valueOf(arrayList));
        if (charText.length() == 0) {
            mList.addAll(arrayList);
        } else {
            for (Dictionary recent : arrayList) {
                String name = recent.getHotel();
                if (name.toLowerCase().contains(charText)) {
                    mList.add(recent);
                }
            }
        }
        notifyDataSetChanged();
    }

    public CustomAdapter(ArrayList<Dictionary> list) {
        this.mList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item1, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        viewholder.content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        viewholder.title.setGravity(Gravity.START);

        viewholder.content.setGravity(Gravity.START);
        viewholder.proFile.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewholder.proFile.setClipToOutline(true);
        }

            viewholder.title.setText(mList.get(position).getTitle());
            viewholder.picture.setImageURI(Uri.fromFile(mList.get(position).getPicture()));
            viewholder.content.setText(mList.get(position).getContent());
            viewholder.timeText.setText(mList.get(position).getTime());
            viewholder.idText.setText(mList.get(position).getId());
            viewholder.proFile.setImageURI(Uri.fromFile(mList.get(position).getProfile()));


    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
