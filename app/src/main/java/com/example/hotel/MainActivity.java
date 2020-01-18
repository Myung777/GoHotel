package com.example.hotel;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

import static com.example.hotel.Hotel_List.imageViewPicture1;
import static com.example.hotel.TabFragment2.selectedImageUri;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Home home;
    private Hotel_List hotel_list;
    private PopularHotel popularHotel;
    private MyPage mypage;
    public static  File tempFile1= new File("/storage/emulated/0/DCIM/Camera/IMG_20191023_115656.jpg");
    private static final int GET_GALLERY_IMAGE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_hotel:
                        setFrag(1);
                        break;
                    case R.id.action_rank:
                        setFrag(2);
                        break;
                    case R.id.action_user:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        home=new Home();
        hotel_list = new Hotel_List();
        popularHotel= new PopularHotel();
        mypage = new MyPage();
        setFrag(0);



    }
    private void setFrag(int n){
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame,home);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame,hotel_list);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame,popularHotel);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame,mypage);
                ft.commit();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            return;
        }
        if (requestCode == GET_GALLERY_IMAGE) {

            selectedImageUri= data.getData();

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert selectedImageUri != null;
                cursor = getContentResolver().query(selectedImageUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile1 = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        }
    }
    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile1.getAbsolutePath(), options);

        imageViewPicture1.setImageBitmap(originalBm);

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("on", "onStop: ");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("on", "onDestroy: ");
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("GO Hotel");
        builder.setContentText("GO Hotel을 이용해주셔서 감사합니다.");

        // 사용자가 탭을 클릭하면 자동 제거
        builder.setAutoCancel(true);

        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }
        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("on", "onPause: ");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("on", "onResume: ");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("on", "onStart: ");

    }
    @Override

    protected void onRestart() {
        super.onRestart();
        Log.i("on", "onRestart: ");
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("광고").setMessage("지금 신라호텔에서 이벤트중 입니다.\nGOGO");

        AlertDialog alertDialog = builder.create();

        alertDialog.show();*/

    }



}
