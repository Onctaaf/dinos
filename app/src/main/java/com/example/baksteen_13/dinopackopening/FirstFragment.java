package com.example.baksteen_13.dinopackopening;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Element;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import static com.example.baksteen_13.dinopackopening.R.*;

public class FirstFragment extends Fragment{


    private TextView mTextMessage;
    //private ArrayList<String> al;
    private arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;
    private String currentUId;
    private DatabaseReference usersDb;

    private cards cards_data[];
    public static String PACKAGE_NAME;
    MediaPlayer tyrannosaurus = new MediaPlayer();
    MediaPlayer triceratops = new MediaPlayer();
    MediaPlayer carnotaurus = new MediaPlayer();
    MediaPlayer diplodocus = new MediaPlayer();
    MediaPlayer dilophosaurus = new MediaPlayer();
    MediaPlayer gallimimus = new MediaPlayer();
    MediaPlayer allosaurus = new MediaPlayer();
    MediaPlayer ankylosaurus = new MediaPlayer();
    MediaPlayer megalosaurus = new MediaPlayer();
    MediaPlayer baryonyx = new MediaPlayer();
    MediaPlayer gigantosaurus = new MediaPlayer();
    MediaPlayer iguanodon = new MediaPlayer();
    MediaPlayer steganosaurus = new MediaPlayer();
    MediaPlayer velociraptor = new MediaPlayer();
    MediaPlayer spinosaurus = new MediaPlayer();
    MediaPlayer brachiosaurus = new MediaPlayer();
    ListView listView;
    List<cards> rowItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(layout.activity_main, container, false);


        PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();



        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getCurrentUser().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");




        super.onCreate(savedInstanceState);

        rowItems = new ArrayList<cards>();
        /*al.add("Tyrannosaurus");
        al.add("Brachiosaurus");
        al.add("Spinosaurus");
        al.add("Velociraptor");
        al.add("Stegosaurus");
        al.add("Triceratops");
        al.add("Allosaurus");
        al.add("Ankylosaurus");
        al.add("Dilophosaurus");
        al.add("Carnotaurus");
        al.add("Diplodocus");
        al.add("Iguanodon");
        al.add("Giganotosaurus");
        al.add("Baryonyx");
        al.add("Gallimimus");
        al.add("Megalosaurus");*/

        arrayAdapter = new arrayAdapter(getActivity(), layout.item, rowItems );

        /*int dinoId = 0;
        for(int i=0 ; i<arrayAdapter.getCount() ; i++){
            Object obj = arrayAdapter.getItem(i);

            String idstring = Integer.toString(dinoId);
            final String name = obj.toString();
            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child(idstring).child("name");
            currentUserDb.setValue(name);
            dinoId++;
        }*/

        getDinos();

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) myView.findViewById(id.frame);

        flingContainer.setAdapter(arrayAdapter);


        ImageButton dislike = (ImageButton) myView.findViewById(id.btndislike);
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) getView().findViewById(id.frame);
                //Snackbar.make(v, "DISLIKE", Snackbar.LENGTH_LONG).show();
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        ImageButton like = (ImageButton) myView.findViewById(id.btnlike);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) getView().findViewById(id.frame);
                //Snackbar.make(view, "LIKE", Snackbar.LENGTH_LONG).show();
                flingContainer.getTopCardListener().selectRight();

            }
        });
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                if (arrayAdapter.getCount() > 0) {
                    rowItems.remove(0);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                TextView dinoName = (TextView) myView.findViewById(R.id.name);
                Log.d("mine", obj.getUserId() + " " + obj.getName());
                usersDb.child("Dinos").child(obj.getName().toString()).child("connections").child("dislike").child(currentUId).setValue(currentUId);
                Snackbar.make(myView, "Niet leuk", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                TextView dinoName = (TextView) myView.findViewById(R.id.name);
                Log.d("mine", obj.getUserId() + " " + obj.getName());
                usersDb.child("Dinos").child(obj.getName().toString()).child("connections").child("like").child(currentUId).setValue(currentUId);

                Snackbar.make(myView, "Leuk", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                /*al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;*/
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                // View view = flingContainer.getSelectedView();
                // view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                // view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                /*Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentmanager = getFragmentManager();
                fragmentmanager.beginTransaction().replace(R.id.content_frame, new InfoFragment()).commit();*/

                cards obj = (cards) dataObject;
                startPlaying(myView, obj.getUserId());
            }
        });
        return myView;
    }/*end oncreateview()*/
    public int counter = 0;
    private void getDinos() {
        final DatabaseReference dinosDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos");
        dinosDb.addChildEventListener(new ChildEventListener() {



            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {

                        ArrayList<String> dinoList = new ArrayList<String>() {{
                            add("Allosaurus");
                            add("Ankylosaurus");
                            add("Baryonyx");
                            add("Brachiosaurus");
                            add("Carnotaurus");
                            add("Dilophosaurus");
                            add("Diplodocus");
                            add("Gallimimus");
                            add("Giganotosaurus");
                            add("Iguanodon");
                            add("Megalosaurus");
                            add("Spinosaurus");
                            add("Stegosaurus");
                            add("Triceratops");
                            add("Tyrannosaurus");
                            add("Velociraptor");
                        }};

                        if (!dataSnapshot.child("connections").child("like").hasChild(currentUId) && !dataSnapshot.child("connections").child("dislike").hasChild(currentUId)) {
                            //Log.d("mine", "" + dataSnapshot + dataSnapshot.child("connections").child());//deze shit gaat dus fout
                            cards theItem = new cards(dataSnapshot.getKey(), (String) dataSnapshot.child(dinoList.get(counter)).getKey());
                            theItem.setName(dinoList.get(counter));
                            theItem.setImage(dinoList.get(counter));


                            rowItems.add(theItem);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }
                    counter++;
                    Log.d("mine", "up de counter naar: " + counter);
                //}
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

}


    private void startPlaying(View myView, String filename) {
        Log.d("mine", filename + "        HIEERRRR");
        if (filename == "Allosaurus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.allosaurus);
        }else if (filename == "Ankylosaurus"){
            Log.d("mine", filename);
            MediaPlayer.create(getActivity().getBaseContext(), raw.ankylosaurus);
            Log.d("mine", filename);
        }else if (filename == "Baryonyx"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.baryonyx);
            Log.d("mine", filename);
        }else if (filename == "Brachiosaurus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.brachiosaurus);
            Log.d("mine", filename);
        }else if (filename == "Carnotaurus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.carnotaurus);
            Log.d("mine", "Carnotaurus");
        }else if (filename == "Dilophosaurus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.dilophosaurus);
            Log.d("mine", "Dilophosaurus");
        }else if (filename == "Diplodocus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.diplodocus);
            Log.d("mine", filename);
        }else if (filename == "Gallimimus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.gallimimus);
            Log.d("mine", filename);
        }else if (filename == "Gigantosaurus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.gigantosaurus);
            Log.d("mine", filename);
        }else if (filename == "Iguanodon"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.iguanodon);
            Log.d("mine", filename);
        }else if (filename == "Megalosaurus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.megalosaurus);
            Log.d("mine", filename);
        }else if (filename == "Spinosaurus"){
             MediaPlayer.create(getActivity().getBaseContext(), raw.spinosaurus);
            Log.d("mine", filename);
        }else if (filename == "Steganosaurus"){
             MediaPlayer.create(getActivity().getBaseContext(), raw.steganosaurus);
            Log.d("mine", filename);
        }else if (filename == "Triceratops"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.triceratops);
            Log.d("mine", "Triceratops");
        }else if (filename == "Tyrannosaurus"){
            MediaPlayer.create(getActivity().getBaseContext(), raw.tyrannosaurus);
            Log.d("mine", "Tyrannosaurus");
        }else{
            MediaPlayer.create(getActivity().getBaseContext(), raw.velociraptor).start();
            Log.d("mine", filename);
        }
        //mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }

}
