package com.example.cora;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    EditText name, ID, address, email, profession;

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
        });

        //throw EditProfile the current users data
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
        });
    }
}