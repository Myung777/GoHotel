package com.example.hotel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static android.content.ContentValues.TAG;
import static com.example.hotel.Hotel_main.tempFile;
import static com.example.hotel.TabFragment1.hotel;

public class TabFragment2 extends Fragment {

    public static ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;
    public static int count = 1;
    private final int GET_GALLERY_IMAGE = 200;
    public static ImageView imageViewPicture;
    public static Uri selectedImageUri;
    private Context mContext;
    public static JSONArray jArray;
    public static String title1;
    public static String time;
    public static File picture;
    public static File jsonProfile;
    public static File jProfile;
    public static String login;
    public static String hhotel;
    ImageButton imageButton;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tap_fragment2, container, false);



        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();



        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        SharedPreferences sf = getActivity().getSharedPreferences("shared", 0);
        title1=sf.getString("title1","");
        SharedPreferences loginID =getActivity(). getSharedPreferences("shared", 0);
        login=loginID.getString("loginID","");
        SharedPreferences loginPf =getActivity(). getSharedPreferences("shared", 0);
        jsonProfile= new File(loginPf.getString("loginPf", ""));


        Log.d("loginData",login);
        Log.d("loginData", String.valueOf(jsonProfile));

        if(title1!=null) {
            try {
                jArray = new JSONArray(title1);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    String title = jObject.getString("title");
                    picture = new File(jObject.getString("picture"));
                    String content = jObject.getString("content");
                    String nowTime = jObject.getString("time");
                    String id = jObject.getString("id");
                    jProfile = new File(jObject.getString("profile"));
                    hhotel = jObject.getString("hotel");

                        mArrayList.add(new Dictionary(title, picture, content, nowTime, id, jProfile, hhotel));

                }
                Log.d("JSON", jArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        mAdapter = new CustomAdapter(mContext,mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        imageButton=(ImageButton)view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count==1) {
                    mAdapter.filter(hotel);
                    count=0;
                }
                else{
                    mAdapter.filter("");
                    count=1;
                }
            }
        });
        Button buttonInsert = (Button)view.findViewById(R.id.btnReview);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.filter("");

//                count++;
//
//                // Dictionary 생성자를 사용하여 ArrayList에 삽입할 데이터를 만듭니다.
//                Dictionary data = new Dictionary(count+"","Apple" + count, "사과" + count);
//
//                //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
//                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
//
//                mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.edit_box, null, false);
                builder.setView(view);
                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText editTextTitle = (EditText) view.findViewById(R.id.edittext_dialog_title);
                imageViewPicture = (ImageView) view.findViewById(R.id.imageView2);
                final EditText editTextContent = (EditText) view.findViewById(R.id.edittext_dialog_content);
                final TextView idTextView = (TextView) view.findViewById(R.id.logId);
                imageViewPicture.setOnClickListener(new View.OnClickListener() {
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
                        String strTitle = editTextTitle.getText().toString();
                        String strContent = editTextContent.getText().toString();
                        TimeZone tz;

                        Date date = new Date();

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd \n     HH:mm:ss");

                        tz = TimeZone.getTimeZone("Asia/Seoul");

                        df.setTimeZone(tz);

                        Log.d(TAG, tz.getDisplayName() + df.format(date));

                        time=df.format(date);
                        Dictionary dict = new Dictionary(strTitle,tempFile, strContent,time,login,jsonProfile,hotel);
                        Log.d(" Test", String.valueOf(tempFile));
                        mArrayList.add(0, dict); //첫 줄에 삽입
                        //mArrayList.add(dict); //마지막 줄에 삽입
                        mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영
                        try {
                            jArray = new JSONArray();//배열이 필요할때
                            for (int i = 0; i < mArrayList.size(); i++) {
                                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                                sObject.put("title", mArrayList.get(i).getTitle());
                                sObject.put("picture", mArrayList.get(i).getPicture());
                                sObject.put("content", mArrayList.get(i).getContent());
                                sObject.put("time", mArrayList.get(i).getTime());
                                sObject.put("id", mArrayList.get(i).getId());
                                sObject.put("profile", mArrayList.get(i).getProfile());
                                sObject.put("hotel", mArrayList.get(i).getHotel());
                                jArray.put(sObject);
                                Log.d("JSON Test", jArray.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < mArrayList.size(); i++) {
                            SharedPreferences sf = getActivity().getSharedPreferences("shared", 0);
                            SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                            title1 = jArray.toString();
                            editor.putString("title1", title1);
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


}