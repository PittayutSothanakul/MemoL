package com.example.memol;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView id , password, txt_register;
//    private EditText email, password ,txt_register;
    private Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        id = (TextView) findViewById(R.id.userName);
        password = (TextView) findViewById(R.id.re_password);
        signin = (Button) findViewById(R.id.loginButton);
        txt_register = (TextView) findViewById(R.id.txt_register);

        txt_register.setPaintFlags(txt_register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // check if user is logged in
        if(mAuth.getCurrentUser() != null) {
            // User not login
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = id.getText().toString();
                String getpassword = password.getText().toString();

                callsignin(getemail, getpassword);
            }
        });

    }


        public void onClickedRegister(View view){
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
        finish();
        startActivity(i);
    }
    private void callsignin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("test", "Sign in is successful: " + task.isSuccessful());

                if(!task.isSuccessful()) {
                    Log.d("test", "Sign in with email failed: ", task.getException());
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }

}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        FirebaseApp.initializeApp(this);
//        mAuth = FirebaseAuth.getInstance();
//
//        userName = (EditText) findViewById(R.id.userName);
//        password = (EditText) findViewById(R.id.re_password);
//        bt_login = (Button) findViewById(R.id.loginButton);
//
//        if(mAuth.getCurrentUser() != null) {
//            // User not login
//            finish();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
//
//        bt_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String getemail = userName.getText().toString();
//                String getpassword = password.getText().toString();
//
//                calllogin(getemail, getpassword);
//            }
//        });
//
//    }
//
//
//    public void onClickedRegister(View view){
//        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
//        finish();
//        startActivity(i);
//    }
//
//
//    private void calllogin(String email, String password) {
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.d("test", "Sign in is successful: " + task.isSuccessful());
//
//                if(!task.isSuccessful()) {
//                    Log.d("test", "Sign in with email failed: ", task.getException());
//                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//                } else {
//                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
//                    finish();
//                    startActivity(i);
//                }
//            }
//        });
//    }
//
//
//}
