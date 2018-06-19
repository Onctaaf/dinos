package com.example.baksteen_13.dinopackopening;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {


    private Button mRegister;

    private EditText mEmail, mPassword, mName;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(RegistrationActivity.this, BurgerActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRegister = (Button) findViewById(R.id.submit_reg);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mName = (EditText) findViewById(R.id.name);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectId = 1;
                final RadioButton radioButton = (RadioButton) findViewById(selectId);


                final String name = mName.getText().toString();
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }else{
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Human").child(userId).child("name");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Tyrannosaurus").child("dummy").setValue("Tyrannosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Brachiosaurus").child("dummy").setValue("Brachiosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Spinosaurus").child("dummy").setValue("Spinosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Velociraptor").child("dummy").setValue("Velociraptor");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Stegosaurus").child("dummy").setValue("Stegosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Triceratops").child("dummy").setValue("Triceratops");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Allosaurus").child("dummy").setValue("Allosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Ankylosaurus").child("dummy").setValue("Ankylosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Dilophosaurus").child("dummy").setValue("Dilophosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Carnotaurus").child("dummy").setValue("Carnotaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Diplodocus").child("dummy").setValue("Diplodocus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Iguanodon").child("dummy").setValue("Iguanodon");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Giganotosaurus").child("dummy").setValue("Giganotosaurus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Baryonyx").child("dummy").setValue("Baryonyx");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Gallimimus").child("dummy").setValue("Gallimimus");
                            FirebaseDatabase.getInstance().getReference().child("Users").child("Dinos").child("Megalosaurus").child("dummy").setValue("Megalosaurus");
                            currentUserDb.setValue(name);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }


    @Override
    public void onBackPressed() {

    }
}
