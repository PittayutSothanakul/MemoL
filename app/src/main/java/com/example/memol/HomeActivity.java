package com.example.memol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView name_account;
    private Button addMemo , addLedger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        name_account = (TextView) findViewById(R.id.name_account);

        name_account.setText(mAuth.getCurrentUser().getEmail());
    }

    public void onLogoutButtonClicked(View view) {
        mAuth.signOut();
        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        finish();
        startActivity(i);
    }

    public void onClickedaddMemo(View view){
        Intent i = new Intent(HomeActivity.this, AddMemoActivity.class);
        finish();
        startActivity(i);
    }

    public void onClickedaddLedger(View view){
        Intent i = new Intent(HomeActivity.this, AddLedgerActivity.class);
        finish();
        startActivity(i);
    }

}
