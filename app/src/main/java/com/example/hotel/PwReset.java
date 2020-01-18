package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PwReset extends AppCompatActivity {

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_reset);



        findViewById(R.id.btnSend).setOnClickListener(onClickListener);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


    }

    View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSend:
                    send();
                    break;
            }
        }
    };

    private void send(){

        String email=((EditText)findViewById(R.id.idEditText1)).getText().toString();


        if(email.length()>0) {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PwReset.this, "이메일을 보냈습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PwReset.this,Login.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
        else{
            Toast.makeText(PwReset.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}

