package com.example.hotel;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class Register extends AppCompatActivity {
    public static  ArrayList<User> userList = new ArrayList<>();
    public static JSONArray jArrayLogin;
    public static JSONArray jjArrayLogin;
    public static String loginData;
    public static File profile;
    private final int GET_GALLERY_IMAGE = 200;
    ImageView pfImage;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        pfImage = (ImageView) findViewById(R.id.pfImage);

        pfImage.setBackground(new ShapeDrawable(new OvalShape()));
        pfImage.setClipToOutline(true);

        findViewById(R.id.btnJoin).setOnClickListener(onClickListener);
        userList.clear();
        SharedPreferences sf = getSharedPreferences("shared", 0);
        loginData=sf.getString("loginData","");
        if(loginData!=null) {
            try {
                jjArrayLogin = new JSONArray(loginData);
                for (int i = 0; i < jjArrayLogin.length(); i++) {
                    JSONObject jObject = jjArrayLogin.getJSONObject(i);

                    String userId = jObject.getString("userId");
                    String userName = jObject.getString("userName");
                    String userPw = jObject.getString("userPw");
                    String userEmail = jObject.getString("userEmail");
                    String userPh = jObject.getString("userPh");
                    File userPf = new File(jObject.getString("userPf"));
                    String userLike = jObject.getString("userLike");

                    User user = new User(userId, userName, userPw, userEmail, userPh, userPf, userLike);
                    userList.add(0, user); //첫 줄에 삽입
                    Log.d("log", String.valueOf(userList));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pfImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tedPermission();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnJoin:
                    signUp();
                break;
            }
        }
    };

    private void signUp(){
        String id=((EditText)findViewById(R.id.idEditText)).getText().toString();
        String name=((EditText)findViewById(R.id.nameEditText)).getText().toString();
        String phone=((EditText)findViewById(R.id.phEditText)).getText().toString();
        String email=((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password=((EditText)findViewById(R.id.pwEditText)).getText().toString();


        if(id.length()>0&&password.length()>0) {
            User user = new User(id,name,password,email,phone,profile,"");
            userList.add(0, user); //첫 줄에 삽입
            Log.d("log", String.valueOf(userList));
            try {
                jArrayLogin = new JSONArray();//배열이 필요할때
                for (int i = 0; i < userList.size(); i++) {
                    JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                    sObject.put("userId", userList.get(i).getUserID());
                    sObject.put("userName", userList.get(i).getUserName());
                    sObject.put("userPw", userList.get(i).getUserPw());
                    sObject.put("userEmail", userList.get(i).getUserEmail());
                    sObject.put("userPh", userList.get(i).getUserPhone());
                    sObject.put("userPf", userList.get(i).getProfile());
                    sObject.put("userLike", userList.get(i).getLike());
                    jArrayLogin.put(sObject);

                    Log.d("JSON Test",jArrayLogin.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < userList.size(); i++) {
                SharedPreferences sf = getSharedPreferences("shared", 0);
                SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                editor.putString("loginData", jArrayLogin.toString());
                editor.commit(); //완료한다.

            }

            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        }

        else{
            Toast.makeText(Register.this, "이메일 또는 비밀번호는 입력해주세요.", Toast.LENGTH_SHORT).show();
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

            Uri uri= data.getData();

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert uri != null;
                cursor = getContentResolver().query(uri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                profile = new File(cursor.getString(column_index));

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
        Bitmap originalBm = BitmapFactory.decodeFile(profile.getAbsolutePath(), options);

        pfImage.setImageBitmap(originalBm);

    }
    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }

}
