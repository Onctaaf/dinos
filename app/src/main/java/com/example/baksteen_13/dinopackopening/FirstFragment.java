package com.example.baksteen_13.dinopackopening;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.renderscript.Element;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
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
import static java.lang.StrictMath.toIntExact;

public class FirstFragment extends Fragment{
    private SoundPool soundPool;
    private int allosaurus1, steganosaurus1, ankylosaurus1, baryonyx1,gallimimus1, diplodocus1,dilophosaurus1, carnotaurus1, brachiosaurus1, giganotosaurus1, iguanodon1,megalosaurus1, spinosaurus1, triceratops1, tyrannosaurus1, velociraptor1;





    private TextView mTextMessage;
    //private ArrayList<String> al;
    private arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;
    private String currentUId;
    private DatabaseReference usersDb;

    private cards cards_data[];
    public static String PACKAGE_NAME;

    Count likeCount = new Count();



    ListView listView;
    List<cards> rowItems;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View myView = inflater.inflate(layout.activity_main, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(16)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        allosaurus1 = soundPool.load(getActivity(), raw.allosaurus, 1);
        ankylosaurus1 = soundPool.load(getActivity(), raw.ankylosaurus, 1);
        baryonyx1 = soundPool.load(getActivity(), raw.baryonyx, 1);
        gallimimus1 = soundPool.load(getActivity(), raw.gallimimus, 1);
        diplodocus1 = soundPool.load(getActivity(), raw.diplodocus, 1);
        dilophosaurus1 = soundPool.load(getActivity(), raw.dilophosaurus, 1);
        carnotaurus1 = soundPool.load(getActivity(), raw.carnotaurus, 1);
        brachiosaurus1 = soundPool.load(getActivity(), raw.brachiosaurus, 1);
        giganotosaurus1 = soundPool.load(getActivity(), raw.gigantosaurus, 1);
        iguanodon1 = soundPool.load(getActivity(), raw.iguanodon, 1);
        megalosaurus1 = soundPool.load(getActivity(), raw.megalosaurus, 1);
        spinosaurus1 = soundPool.load(getActivity(), raw.spinosaurus, 1);
        triceratops1 = soundPool.load(getActivity(), raw.triceratops, 1);
        tyrannosaurus1 = soundPool.load(getActivity(), raw.tyrannosaurus, 1);
        velociraptor1 = soundPool.load(getActivity(), raw.velociraptor, 1);
        steganosaurus1 = soundPool.load(getActivity(), raw.steganosaurus, 1);


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

        ImageButton like = (ImageButton) myView.findViewById(id.btndislike);


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
                usersDb.child("Dinos").child(obj.getName()).child("connections").child("dislike").child(currentUId).setValue(currentUId);
                usersDb.child("Human").child(currentUId).child("connections").child("dislike").child(obj.getName()).setValue(obj.getName());
                likeCount.setDislikes((int) (likeCount.getDislikes() + 1));
                Log.d("mine", "DIT IS NU HET AANTAL LIKES & DISLIKES" + likeCount.getLikes() + "   " + likeCount.getDislikes());
                Snackbar.make(myView, "Niet leuk", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                TextView dinoName = (TextView) myView.findViewById(R.id.name);
                Log.d("mine", obj.getUserId() + " " + obj.getName());
                usersDb.child("Dinos").child(obj.getName()).child("connections").child("like").child(currentUId).setValue(currentUId);
                usersDb.child("Human").child(currentUId).child("connections").child("like").child(obj.getName()).setValue(obj.getName());//aanpassen
                likeCount.setLikes((int) (likeCount.getLikes() + 1));
                Log.d("mine", "DIT IS NU HET AANTAL LIKES & DISLIKES" + likeCount.getLikes() + "   " + likeCount.getDislikes());
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
                Log.d("mine", "CLICK!!!!"+ obj.getUserId());
                startPlaying(myView, obj.getUserId());
            }
        });

        return myView;
    }/*end oncreateview()*/

    @Override
    public void onStop() {
        super.onStop();
        soundPool.release();
        arrayAdapter = null;
        soundPool = null;
        System.runFinalization(); Runtime.getRuntime().gc(); System.gc();
    }


   /*@Override
    public void onPause() {
        super.onPause();
        soundPool.release();
        soundPool = null;
    }*/

    public int counter = 0;
    private void getDinos() {
        final DatabaseReference dinosDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos");
        dinosDb.addChildEventListener(new ChildEventListener() {

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

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        if (!dataSnapshot.child("connections").child("like").hasChild(currentUId) && !dataSnapshot.child("connections").child("dislike").hasChild(currentUId)) {
                            //Log.d("mine", "" + dataSnapshot + dataSnapshot.child("connections").child());//deze shit gaat dus fout
                            cards theItem = new cards(dataSnapshot.getKey(), (String) dataSnapshot.child(dinoList.get(counter)).getKey());
                            theItem.setName(dinoList.get(counter));
                            theItem.setImage(dinoList.get(counter));
                            rowItems.add(theItem);
                            if (arrayAdapter != null) {
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    counter++;


                //}


//
////test
//                if (dataSnapshot.child(currentUId).child("connections").child("like").exists()){
//                    int likesCounted = toIntExact(dataSnapshot.child(currentUId).child("connections").child("like").getChildrenCount());
//                    likeCount.setLikes(likesCounted);
//                }
//                else {
//                    int likesCounted = 0;
//                    likeCount.setLikes(likesCounted);
//                }
//                if (dataSnapshot.child(currentUId).child("connections").child("dislike").exists()) {
//                    int dislikesCounted = toIntExact(dataSnapshot.child(currentUId).child("connections").child("dislike").getChildrenCount());
//                    likeCount.setDislikes(dislikesCounted);
//                }else{
//                    int dislikesCounted = 0;
//                    likeCount.setDislikes(dislikesCounted);
//                }
//                Log.d("mine", likeCount.getLikes() + "      " + likeCount.getDislikes());
//
////test


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
        //Log.d("mine", filename + "        HIEERRRR");
        if (filename.equals("Allosaurus")){
            soundPool.play(allosaurus1, 1, 1, 1, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.allosaurus).start();
            Log.d("mine", "play Allosaurus");
        }else if (filename.equals("Ankylosaurus")){
            soundPool.play(ankylosaurus1, 1, 1, 1, 0, 1);
            Log.d("mine", "play Ankylosaurus");
//            MediaPlayer.create(getActivity().getBaseContext(), raw.ankylosaurus).start();
        }else if (filename.equals("Baryonyx")){
            soundPool.play(baryonyx1, 1, 1, 1, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.baryonyx).start();
            Log.d("mine", "play Baryonyx");
        }else if (filename.equals("Brachiosaurus")){
            soundPool.play(brachiosaurus1, 1, 1, 1, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.brachiosaurus).start();
            Log.d("mine","play Brachiosaurus");
        }else if (filename.equals("Carnotaurus")){
            soundPool.play(carnotaurus1, 1, 1, 1, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.carnotaurus).start();
            Log.d("mine", "play Carnotaurus");
        }else if (filename.equals("Dilophosaurus")){
            soundPool.play(dilophosaurus1, 1, 1, 1, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.dilophosaurus).start();
            Log.d("mine", "play Dilophosaurus");
        }else if (filename.equals("Diplodocus")){
            soundPool.play(diplodocus1, 1, 1, 1, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.diplodocus).start();
            Log.d("mine", "play Diplodocus");
        }else if (filename.equals("Gallimimus")){
            soundPool.play(gallimimus1, 1, 1, 0, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.gallimimus).start();
            Log.d("mine", "play Gallimimus");
        }else if (filename.equals("Giganotosaurus")){
            soundPool.play(giganotosaurus1, 1, 1, 0, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.gigantosaurus).start();
            Log.d("mine", "play Gigantosaurus");
        }else if (filename.equals("Iguanodon")){
            soundPool.play(iguanodon1, 1, 1, 0, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.iguanodon).start();
            Log.d("mine", "play Iguanodon");
        }else if (filename.equals("Megalosaurus")){
            soundPool.play(megalosaurus1, 1, 1, 0, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.megalosaurus).start();
            Log.d("mine", "play Megalosaurus");
        }else if (filename.equals("Spinosaurus")){
            soundPool.play(spinosaurus1, 1, 1, 0, 0, 1);
//             MediaPlayer.create(getActivity().getBaseContext(), raw.spinosaurus).start();
            Log.d("mine", "play spinosaurus");
        }else if (filename.equals("Stegosaurus")){
            soundPool.play(steganosaurus1, 1, 1, 0, 0, 1);
//             MediaPlayer.create(getActivity().getBaseContext(), raw.steganosaurus).start();
            Log.d("mine", filename);
        }else if (filename.equals("Triceratops")){
            soundPool.play(triceratops1, 1, 1, 0, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.triceratops).start();
            Log.d("mine", "play Triceratops");
        }else if (filename.equals("Tyrannosaurus")){
            soundPool.play(tyrannosaurus1, 1, 1, 0, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.tyrannosaurus).start();
            Log.d("mine", "play Tyrannosaurus");
        }else if (filename.equals("Velociraptor")){
            soundPool.play(velociraptor1, 1, 1, 0, 0, 1);
//            MediaPlayer.create(getActivity().getBaseContext(), raw.velociraptor).start();
            Log.d("mine", "play velociraptor");
        }
        //mediaPlayer.start(); // no need to call prepare(); create() does that for you
    }


}
