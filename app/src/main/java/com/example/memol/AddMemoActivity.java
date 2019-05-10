package com.example.memol;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMemoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button selectButton , enterButton ;
    private ImageButton dateButton , timeButton;
    private TextView nameText , dateText,timeText , locationText , descriptionText;
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
        //create a date string.
        String date_n = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        Calendar c1 = Calendar.getInstance(); // Get current time
        c1.setTimeInMillis(System.currentTimeMillis());
        int hour = c1.get(Calendar.HOUR);
        int min = c1.get(Calendar.MINUTE);
        int sec = c1.get(Calendar.SECOND);

        selectButton = (Button) findViewById(R.id.selectButton);
        enterButton = (Button) findViewById(R.id.enterButton);
        timeButton = (ImageButton) findViewById(R.id.timeButton);
        dateButton = (ImageButton) findViewById(R.id.dateButton);
        nameText = (TextView) findViewById(R.id.nameText);
        dateText = (TextView) findViewById(R.id.dateText);
        timeText = (TextView) findViewById(R.id.timeText);
        locationText= (TextView) findViewById(R.id.locationText);
        descriptionText = (TextView) findViewById(R.id.descriptionText);
        imageView = (ImageView) findViewById(R.id.imageView);

        dateText.setText(date_n);
        String am_pm;
        if(hour<12){
            am_pm = "AM";

        }else{
            am_pm = "PM";
        }
        timeText.setText(hour + ":" + min + " " + am_pm);

        mProgressDialog = new ProgressDialog(AddMemoActivity.this);


        dateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance(); // Get current time
                c.setTimeInMillis(System.currentTimeMillis());
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd;
                dpd = new DatePickerDialog(AddMemoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dateText.setText(day + "-" + (month+1) + "-" + year);
                    }
                }, day, month, year);
                dpd.show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance(); // Get current time
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = c.get(Calendar.HOUR);
                int min = c.get(Calendar.MINUTE);
                int sec = c.get(Calendar.SECOND);
                TimePickerDialog tpd;
                tpd = new TimePickerDialog(AddMemoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {
                        String am_pm;
                        if(hour<12){
                            am_pm = "AM";

                        }else{
                            am_pm = "PM";
                        }
                        timeText.setText(hour + ":" + min + " " + am_pm);
                    }
                },hour,min,false);
                tpd.show();
            }
        });


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
        mRootRef = new Firebase("https://memol-1110c.firebaseio.com/").child("User_Memo").push();
        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://memol-1110c.appspot.com");

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mName = nameText.getText().toString().trim();
                final String mDate = dateText.getText().toString().trim();
                final String mTime = timeText.getText().toString().trim();
                final String mLocation = locationText.getText().toString().trim();
                final String mDescription = descriptionText.getText().toString().trim();

                if(mName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter title", Toast.LENGTH_SHORT).show();
                    return;
                }

                Firebase childRef_name = mRootRef.child("Memo_Name");
                Firebase chileRef_date = mRootRef.child("Memo_Date");
                Firebase chileRef_time = mRootRef.child("Memo_Time");
                Firebase chileRef_location = mRootRef.child("Memo_Location");
                Firebase chileRef_description = mRootRef.child("Memo_Description");

                childRef_name.setValue(mName);
                chileRef_date.setValue(mDate);
                chileRef_time.setValue(mTime);
                chileRef_location.setValue(mLocation);
                chileRef_description.setValue(mDescription);



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
            StorageReference filePath = mStorage.child("Memo_Images").child(imagesUri.getLastPathSegment());

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
