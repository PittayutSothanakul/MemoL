package com.example.memol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText userName, password;
    private Button bt_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.re_password);
        bt_login = (Button) findViewById(R.id.loginButton);

        if(mAuth.getCurrentUser() != null) {
            // User not login
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = userName.getText().toString();
                String getpassword = password.getText().toString();

                calllogin(getemail, getpassword);
            }
        });

    }


    public void onClickedRegister(View view){
        Intent i = new Intent(MainActivity.this, RegisterActivity.class);
        finish();
        startActivity(i);
    }


    private void calllogin(String email, String password) {
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
