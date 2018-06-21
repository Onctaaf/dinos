package com.example.baksteen_13.dinopackopening;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;
import static java.lang.StrictMath.round;
import static java.lang.StrictMath.toIntExact;

public class SecondFragment extends Fragment{
    View myView;
    public PieChart pieChart;

   // public int likeCount;
    //public int dislikeCount;

    private FirebaseAuth mAuth;
    private String currentUId;
    private DatabaseReference usersDb;
    public Count likeCount = new Count();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);

        getHumans();


        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
       // currentUId = mAuth.getCurrentUser().getUid();
        currentUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        return myView;
    }


    private void getHumans() {
        DatabaseReference dinosDb = FirebaseDatabase.getInstance().getReference().child("Users");
        dinosDb.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("mine", "CURRENT DATASNAPSHOT     "  + dataSnapshot.child(currentUId));
                if (dataSnapshot.child(currentUId).child("connections").child("like").exists()){
                    int likesCounted = toIntExact(dataSnapshot.child(currentUId).child("connections").child("like").getChildrenCount());
                    likeCount.setLikes(likesCounted);
                }
                else {
                    int likesCounted = 0;
                    likeCount.setLikes(likesCounted);
                }
                if (dataSnapshot.child(currentUId).child("connections").child("dislike").exists()) {
                    int dislikesCounted = toIntExact(dataSnapshot.child(currentUId).child("connections").child("dislike").getChildrenCount());
                    likeCount.setDislikes(dislikesCounted);
                    Log.d("mine", "Hier dislikescounted: " + dislikesCounted + "     en hier likecount.getdislikes: " + likeCount.getDislikes());
                }else{
                    int dislikesCounted = 0;
                    likeCount.setDislikes(dislikesCounted);
                }
                Log.d("mine", "IK LOG NU SHIT OMDAT ER GELIKED/DISLIKED IS WOOOO" + dataSnapshot.child(currentUId).child("connections").child("like").getChildrenCount() + "      " + dataSnapshot.child(currentUId).child("connections").child("dislike").getChildrenCount());




                pieChart = (PieChart) myView.findViewById(R.id.idPieChart);
pieChart.setCenterTextSize(25f);
                pieChart.setUsePercentValues(true);
                pieChart.setExtraOffsets(5, 10, 5, 5);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);
                pieChart.setEntryLabelTextSize(25f);
                pieChart.setUsePercentValues(false);
                pieChart.getDescription().setTextSize(22f);
                pieChart.getDescription().setEnabled(false);
                pieChart.getLegend().setEnabled(false);

                List<PieEntry> entries = new ArrayList<>();

                //Log.d("mine", likeCount.getLikes() + "     "+ likeCount.getDislikes());

                entries.add(new PieEntry(round(likeCount.getLikes()), "Leuk"));
                entries.add(new PieEntry(likeCount.getDislikes(), "Niet leuk"));
                Log.d("mine", likeCount.getLikes() + "    " + likeCount.getDislikes());
                Log.d("mine", String.valueOf(entries));
                PieDataSet set = new PieDataSet(entries, "likes/dislikes");
                PieData data = new PieData(set);
                data.setValueTextSize(30f);
                data.setValueTextColor(WHITE);
                set.setColors(getResources().getColor(R.color.chartgreen), getResources().getColor(R.color.chartred));
                pieChart.setData(data);

                pieChart.invalidate(); // refresh
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}}
