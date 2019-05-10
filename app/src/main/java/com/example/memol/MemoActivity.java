package com.example.memol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemoActivity extends AppCompatActivity {
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
//        listView.setLayoutManager(new LinearLayoutManager(MemoActivity.this));
        Toast.makeText(MemoActivity.this,"Please wait, it is downloading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ViewSingleMemo, ShowDataViewHolder>(ViewSingleMemo.class, R.layout.activity_viewsinglememo, ShowDataViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(final ShowDataViewHolder viewHolder, ViewSingleMemo model, final int position) {
                viewHolder.Image_URL(model.getImage_url());
                viewHolder.Memo_name(model.getMemo_Name());
                viewHolder.Memo_Date(model.getMemo_Date());
                viewHolder.Memo_Time(model.getMemo_Time());
                viewHolder.Memo_Location(model.getMemo_Location());
                viewHolder.Memo_Description(model.getMemo_Description());


//                viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MemoActivity.this);
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
        recyclerView.setAdapter( mFirebaseAdapter);
    }

    public static class ShowDataViewHolder extends RecyclerView.ViewHolder{

        private final TextView  nameText,timeText,dateText,locationText,descriptionText;
        private final ImageView image_url;


        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = (ImageView) itemView.findViewById(R.id.imageView);
            nameText = (TextView) itemView.findViewById(R.id.nameText);
            timeText = (TextView) itemView.findViewById(R.id.timeText);
            dateText = (TextView) itemView.findViewById(R.id.dateText);
            locationText = (TextView) itemView.findViewById(R.id.locationText);
            descriptionText = (TextView) itemView.findViewById(R.id.descriptionText);


        }

        private void Memo_name(String name) { nameText.setText(name); }
        private void Memo_Date(String memo_Date) { dateText.setText(memo_Date);}
        private void Memo_Time(String memo_Time) { timeText.setText(memo_Time);}
        private void Memo_Location(String memo_Location) { locationText.setText(memo_Location);}
        private void Memo_Description(String memo_Description) { descriptionText.setText(memo_Description);}

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

}

