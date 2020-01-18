package com.example.hotel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;

import static com.example.hotel.TabFragment2.imageViewPicture;
import static com.example.hotel.TabFragment2.selectedImageUri;

public class Hotel_main extends AppCompatActivity implements View.OnClickListener{
    private static final int GET_GALLERY_IMAGE = 200;
    public static int num=1;
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    Button btnReview;
    public static  File tempFile= new File("/storage/emulated/0/DCIM/Camera/IMG_20191023_115656.jpg");
    private Button bt_tab1, bt_tab2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);
    bt_tab1 = (Button)findViewById(R.id.bt_tab1);
    bt_tab2 = (Button)findViewById(R.id.bt_tab2);

    // 탭 버튼에 대한 리스너 연결
        bt_tab1.setOnClickListener(this);
        bt_tab2.setOnClickListener(this);

    // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
    callFragment(FRAGMENT1);


}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_tab1 :
                // '버튼1' 클릭 시 '프래그먼트1' 호출
                callFragment(FRAGMENT1);
                break;

            case R.id.bt_tab2 :
                // '버튼2' 클릭 시 '프래그먼트2' 호출
                callFragment(FRAGMENT2);
                break;
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }





    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
            case 1:
                // '프래그먼트1' 호출
                TabFragment1 fragment1 = new TabFragment1();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                TabFragment2 fragment2 = new TabFragment2();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.commit();
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

                tempFile = new File(cursor.getString(column_index));

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
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageViewPicture.setImageBitmap(originalBm);

    }




}
