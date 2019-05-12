package com.example.memol;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView re_userName, re_password , txt_toLogin;
    private Button bt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        re_userName = (TextView) findViewById(R.id.re_userName);
        re_password = (TextView) findViewById(R.id.re_password);
        bt_register = (Button) findViewById(R.id.bt_register);
        txt_toLogin = (TextView) findViewById(R.id.txt_toLogin);

        txt_toLogin.setPaintFlags(txt_toLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = re_userName.getText().toString();
                String getpassword = re_password.getText().toString();

                callregister(getemail, getpassword);
            }
        });
    }

    public void onClickedtoLogin(View view){
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        finish();
        startActivity(i);
    }


    private void callregister(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Account Created.",
                                    Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(email).build();
                            FirebaseUser user = mAuth.getCurrentUser();

                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Log.d("test", "User profile updated");
                                    }
                                }
                            });
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Sign Up failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
        finish();
        startActivity(i);
    }

}
