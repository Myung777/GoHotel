package com.example.hotel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.example.hotel.Register.jArrayLogin;
import static com.example.hotel.Register.loginData;

public class Login extends AppCompatActivity {


public static int postion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        findViewById(R.id.btnJoin).setOnClickListener(onClickListener);
        findViewById(R.id.btnLogin).setOnClickListener(onClickListener);



    }

    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnJoin:
                    Intent intent = new Intent(Login.this,Register.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.btnLogin:
                    login();
                    break;

            }
        }
    };


    private void login(){
        SharedPreferences sf = getSharedPreferences("shared", 0);
        loginData=sf.getString("loginData","");

        Log.d("loginData",loginData);

        String id=((EditText)findViewById(R.id.idEditText1)).getText().toString();
        String password=((EditText)findViewById(R.id.pwEditText)).getText().toString();


        if(id.length()>0&&password.length()>0) {
            if(loginData!=null) {
                try {
                    jArrayLogin = new JSONArray(loginData);
                    for (int i = 0; i < jArrayLogin.length(); i++) {
                        JSONObject jObject = jArrayLogin.getJSONObject(i);
                        String userId = jObject.getString("userId");
                        String userPw = jObject.getString("userPw");
                        File userPf= new File(jObject.getString("userPf"));

                        Log.d("data Test", userId);
                        Log.d("data Test", userPw);
                        if (userId.equals(id) && userPw.equals(password)) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            SharedPreferences loginID = getSharedPreferences("shared", 0);
                            SharedPreferences.Editor editor = loginID.edit();//저장하려면 editor가 필요
                            editor.putString("loginID", userId);

                            editor.commit(); //완료한다.
                            postion=i;
                            Log.d("postion", String.valueOf(postion));

                            SharedPreferences loginPf = getSharedPreferences("shared", 0);
                            SharedPreferences.Editor editor1 = loginPf.edit();//저장하려면 editor가 필요
                            editor1.putString("loginPf", String.valueOf(userPf));
                            editor1.commit(); //완료한다.
                            Log.d("pfTest", String.valueOf(userPf));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        else{
            Toast.makeText(Login.this, "이메일 또는 비밀번호는 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}

