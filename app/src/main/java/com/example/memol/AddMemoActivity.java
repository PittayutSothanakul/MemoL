package com.example.memol;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AddMemoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button selectButton , enterButton;
    private TextView nameText , timeText , locationText , descriptionText;
    private FirebaseAuth mAuth;
    private static  final int PICK_IMAGE =100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemo);

        mAuth = FirebaseAuth.getInstance();

        imageView = (ImageView) findViewById(R.id.imageView);
        selectButton = (Button) findViewById(R.id.selectButton);
        enterButton = (Button) findViewById(R.id.enterButton);
        nameText = (TextView) findViewById(R.id.nameText);
        timeText = (TextView) findViewById(R.id.timeText);
        locationText = (TextView) findViewById(R.id.locationText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);


        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });


    }
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode ,Intent data){
        super.onActivityResult(requestCode , resultCode , data);
        if(requestCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
