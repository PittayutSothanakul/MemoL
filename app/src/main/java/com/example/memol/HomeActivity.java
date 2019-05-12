package com.example.memol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;


public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView name_account, situationText;
    private ImageButton addMemo , addLedger;
    private CalendarView calendarView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        name_account = (TextView) findViewById(R.id.name_account);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        name_account.setText(mAuth.getCurrentUser().getEmail());

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("User_Memo");

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

    public void onClickToMemo(View view){
        Intent i = new Intent(HomeActivity.this, ViewPosts.class);
        finish();
        startActivity(i);
    }

    public void onClickToLedger(View view){
        Intent i = new Intent(HomeActivity.this, LedgerActivity.class);
        finish();
        startActivity(i);
    }

}
