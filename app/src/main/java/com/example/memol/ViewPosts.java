package com.example.memol;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.memol.ViewSingleMemo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewPosts extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private FirebaseRecyclerAdapter<ViewSingleMemo, ShowDataViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("User_Memo");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewPosts.this));
        Toast.makeText(ViewPosts.this,"Please wait, it is downloading...", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ViewSingleMemo, ShowDataViewHolder>(ViewSingleMemo.class, R.layout.activity_viewsinglememo, ShowDataViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(final ShowDataViewHolder viewHolder, ViewSingleMemo model, final int position) {
                viewHolder.Image_URL(model.getImage_url());
                viewHolder.Name_Text(model.getMemo_Name());
                viewHolder.Date_Text(model.getMemo_Date());
                viewHolder.Time_text(model.getMemo_Time());
                viewHolder.Location_text(model.getMemo_Location());
                viewHolder.Description_text(model.getMemo_Description());


//                viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewPosts.this);
//                        builder.setMessage("Delete?").setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        int selectedItems = position;
//                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
//                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
//                                        recyclerView.invalidate();
//                                        onStart();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog dialog = builder.create();
//                        dialog.setTitle("Are you sure?");
//                        dialog.show();
//                    }
//                });
            }
        };


        recyclerView.setAdapter(mFirebaseAdapter);
    }

    public static class ShowDataViewHolder extends RecyclerView.ViewHolder{

        private final TextView  name_text , date_text , time_text , location_text , description_text;
        private final ImageView image_url;

        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = (ImageView) itemView.findViewById(R.id.imageView);
            name_text = (TextView) itemView.findViewById(R.id.nameText);
            date_text = (TextView) itemView.findViewById(R.id.dateText);
            time_text = (TextView) itemView.findViewById(R.id.timeText);
            location_text = (TextView) itemView.findViewById(R.id.locationText);
            description_text = (TextView) itemView.findViewById(R.id.descriptionText);


        }

        private void Name_Text(String name) {
            name_text.setText(name);
        }
        private void Date_Text(String date) {
            date_text.setText(date);
        }
        private void Time_text(String time) {
            time_text.setText(time);
        }
        private void Location_text(String location) {
            location_text.setText(location);
        }
        private void Description_text(String description) {
            description_text.setText(description);
        }

        private void Image_URL(String title) {
            Glide.with(itemView.getContext())
                    .load(title)
                    .crossFade()
                    .placeholder(R.drawable.loading)
                    .thumbnail(01.f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image_url);
        }
    }


    public void onClickToLedger(View view){
        Intent i = new Intent(ViewPosts.this, LedgerActivity.class);
        finish();
        startActivity(i);
    }
}

