package com.example.memol;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.firebase.client.Firebase;

public class AddMemoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button selectButton , enterButton;
    private TextView nameText , timeText , locationText , descriptionText;
    private FirebaseAuth mAuth;
    private Firebase mRootRef;
    private ProgressDialog mProgressDialog;
    private static  final int GALLERY_INTENT =2;
    public static final int READ_EXTERNAL_STORAGE = 0;
    private Uri imagesUri = null;
    private DatabaseReference mdatabaseRef;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemo);

        Firebase.setAndroidContext(this);

        selectButton = (Button) findViewById(R.id.selectButton);
        enterButton = (Button) findViewById(R.id.enterButton);
        nameText = (TextView) findViewById(R.id.nameText);
        imageView = (ImageView) findViewById(R.id.imageView);

        mProgressDialog = new ProgressDialog(AddMemoActivity.this);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "Call for permission", Toast.LENGTH_SHORT).show();
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
                    }
                }
                else {
                    callGalley();
                }
            }
        });

        // initialize firebase
        mdatabaseRef = FirebaseDatabase.getInstance().getReference();
        mRootRef = new Firebase("https://memol-1110c.firebaseio.com/").child("User_Details").push();
        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://memol-1110c.appspot.com");

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mName = nameText.getText().toString().trim();
                if(mName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter title", Toast.LENGTH_SHORT).show();
                    return;
                }
                Firebase childRef_name = mRootRef.child("Image_Title");
                childRef_name.setValue(mName);
                Toast.makeText(getApplicationContext(), "Update Info", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddMemoActivity.this, HomeActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    callGalley();
                break;
        }
        Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
    }

    private void callGalley() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            imagesUri = data.getData();
            imageView.setImageURI(imagesUri);
            StorageReference filePath = mStorage.child("User_Images").child(imagesUri.getLastPathSegment());

            mProgressDialog.setMessage("Uploading...");
            mProgressDialog.show();

            filePath.putFile(imagesUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                    downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUri) {
                            mRootRef.child("Image_URL").setValue(downloadUri.toString());
                            Glide.with(getApplicationContext())
                                    .load(downloadUri)
                                    .crossFade()
                                    .placeholder(R.drawable.loading)
                                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                    .into(imageView);
                            Toast.makeText(getApplicationContext(), "Updated...", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    });


                }
            });
        }
    }
}
