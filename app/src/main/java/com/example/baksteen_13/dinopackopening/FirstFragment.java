package com.example.baksteen_13.dinopackopening;

import android.app.Fragment;
import android.app.FragmentManager;
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


    ListView listView;
    List<cards> rowItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(layout.activity_main, container, false);

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
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
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
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentmanager = getFragmentManager();
                fragmentmanager.beginTransaction().replace(R.id.content_frame, new InfoFragment()).commit();
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
                    if (dataSnapshot.exists()){
                        ArrayList<String> dinoList = new ArrayList<String>(){{ add("Allosaurus"); add("Ankylosaurus"); add("Baryonyx");add("Brachiosaurus");add("Carnotaurus");add("Dilophosaurus");add("Diplodocus");add("Gallimimus");  add("Giganotosaurus"); add("Iguanodon"); add("Megalosaurus"); add("Spinosaurus"); add("Stegosaurus"); add("Triceratops"); add("Tyrannosaurus"); add("Velociraptor");}};
                        if (!dataSnapshot.child(dinoList.get(i)).child("connections").child("like").hasChild(currentUId) && !dataSnapshot.child(dinoList.get(i)).child("connections").child("dislike").hasChild(currentUId)) {
                            Log.d("mine", "" + dataSnapshot + dataSnapshot.child(dinoList.get(counter)).child("connections").child("dislike").hasChild(currentUId));//deze shit gaat dus fout
                            cards theItem = new cards(dataSnapshot.getKey(), (String) dataSnapshot.child(dinoList.get(i)).getKey());
                            rowItems.add(theItem);
                            arrayAdapter.notifyDataSetChanged();
                        }
                        counter++;
                    }
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

}
