package com.example.cora;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    Button changeBtn;
    TextView name, ID, address, email, profession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_CORA);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.viewFullName);
        ID = findViewById(R.id.viewUTAID);
        address = findViewById(R.id.viewAddress);
        email = findViewById(R.id.viewEmail);
        profession = findViewById(R.id.viewProfession);
        changeBtn = findViewById(R.id.editProfileBtn);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        DocumentReference ref = fStore.collection("users").document(user.getUid());
        ref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot.exists()) {
                    name.setText(documentSnapshot.getString("FullName"));
                    ID.setText(documentSnapshot.getString("UTAid"));
                    address.setText(documentSnapshot.getString("Address"));
                    email.setText(documentSnapshot.getString("Email"));
                    profession.setText(documentSnapshot.getString("Profession"));
                } else {
                    Log.d(TAG, "User does not exist");
                }
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "Edit Profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });

    }
}

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //link variables to textViews
        name = findViewById(R.id.editName);
        ID = findViewById(R.id.editStudentID);
        address = findViewById(R.id.editAddress);
        email = findViewById(R.id.editEmail);
        profession = findViewById(R.id.editProfession);
        changeBtn = findViewById(R.id.saveChange);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        //gets the users info to display on profile page

        DocumentReference ref = fStore.collection("users").document(user.getUid());
        ref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot.exists()){
                    name.setText(documentSnapshot.getString("FullName"));
                    ID.setText(documentSnapshot.getString("UTAid"));
                    address.setText(documentSnapshot.getString("Address"));
                    email.setText(documentSnapshot.getString("Email"));
                    profession.setText(documentSnapshot.getString("Profession"));
                }
                else{
                    Log.d(TAG, "User does not exist");
                }
            }
        }); */

        //throw EditProfile the current users data
        /*
       changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),EditProfile.class);
                i.putExtra("full name", name.getText().toString());
                i.putExtra("ID", ID.getText().toString());
                i.putExtra("address", address.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("profession", profession.getText().toString());
                startActivity(i);
            }
        });*/