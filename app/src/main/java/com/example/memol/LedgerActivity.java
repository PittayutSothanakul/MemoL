package com.example.memol;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class LedgerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    private TextView totalpriceText;
    private FirebaseRecyclerAdapter<ViewSingleLedger, ShowDataViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("User_Ledger");

        totalpriceText = (TextView) findViewById(R.id.totalpriceText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double sum =0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Map<String,Object> map = (Map<String,Object>) ds.getValue();
                    Object price = map.get("Ledger_Price");
                    Object currency = map.get("Ledger_Currency");
                    double pValue = Double.parseDouble(String.valueOf(price));
                    String pCurrency = String.valueOf(currency);

                    if(!pCurrency.equals("THB")){
                        if (pCurrency.equals("JPY")){
                            sum+=pValue *35.464 / 123.25;
                        }else if(pCurrency.equals("EUR")){
                            sum+=pValue *1 / 35.464;
                        }

                    }else {
                        sum+=pValue;
                    }
                    double twodecimal = Math.round(sum * 100.0) / 100.0;
                    totalpriceText.setText("Total Price = "+ String.valueOf(twodecimal) + " THB");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(LedgerActivity.this));
        Toast.makeText(LedgerActivity.this,"Please wait, it is downloading...", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ViewSingleLedger, ShowDataViewHolder>(ViewSingleLedger.class, R.layout.activity_viewsingleledger, ShowDataViewHolder.class, myRef) {
            @Override
            protected void populateViewHolder(final ShowDataViewHolder viewHolder, ViewSingleLedger model, final int position) {
                viewHolder.Image_URL(model.getImage_url());
                viewHolder.Name_Text(model.getLedger_Name());
                viewHolder.Date_Text(model.getLedger_Date());
                viewHolder.Time_text(model.getLedger_Time());
                viewHolder.Location_text(model.getLedger_Location());
                viewHolder.Description_text(model.getLedger_Description());
                viewHolder.Price_text(model.getLedger_Price() +" "+ model.getLedger_Currency());


                viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LedgerActivity.this);
                        builder.setMessage("Delete?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int selectedItems = position;
                                        mFirebaseAdapter.getRef(selectedItems).removeValue();
                                        mFirebaseAdapter.notifyItemRemoved(selectedItems);
                                        recyclerView.invalidate();
                                        onStart();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Are you sure?");
                        dialog.show();
                    }
                });
            }
        };


        recyclerView.setAdapter(mFirebaseAdapter);
    }

    public static class ShowDataViewHolder extends RecyclerView.ViewHolder{

        private final TextView  name_text , date_text , time_text , location_text , description_text, price_text;
        private final ImageView image_url;

        public ShowDataViewHolder(final View itemView) {
            super(itemView);
            image_url = (ImageView) itemView.findViewById(R.id.imageView);
            name_text = (TextView) itemView.findViewById(R.id.nameText);
            date_text = (TextView) itemView.findViewById(R.id.dateText);
            time_text = (TextView) itemView.findViewById(R.id.timeText);
            location_text = (TextView) itemView.findViewById(R.id.locationText);
            description_text = (TextView) itemView.findViewById(R.id.descriptionText);
            price_text = (TextView) itemView.findViewById(R.id.priceText);


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
        private void Price_text(String price) { price_text.setText(price);
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
    public void onClickToMemo(View view){
        Intent i = new Intent(LedgerActivity.this, ViewPosts.class);
        finish();
        startActivity(i);
    }
    public void onClickToHome(View view) {
        Intent i = new Intent(LedgerActivity.this, HomeActivity.class);
        finish();
        startActivity(i);
    }

}
