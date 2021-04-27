package com.example.cora;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    //Creating button variables.
    Button logoutBtn;
    Button forumBtn;
    Button reportBtn;
    Button profileBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_CORA);
        setContentView(R.layout.activity_main_menu);

        //Connecting each var to the buttons on screen

        logoutBtn = findViewById(R.id.Logoutid);
        forumBtn = findViewById(R.id.Forumid);
        reportBtn = findViewById(R.id.ReportOccurenceid);
        profileBtn = findViewById(R.id.EditProfileid);


        //This will switch to the login page when logout button is clicked.
        logoutBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
        });


        //This will switch to the Forum page when the forum button is clicked
        forumBtn.setOnClickListener(v -> {

            Toast.makeText(MainActivity.this, "Switched to Forum", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(MainActivity.this, Forum.class);
            startActivity(a);
        });

        //This will switch to the report occurrence page when btn is clicked
        reportBtn.setOnClickListener(v -> {

            Toast.makeText(MainActivity.this, "Switched to Report Occurrence Page", Toast.LENGTH_SHORT).show();
            Intent e = new Intent(MainActivity.this, MainActivity.class); // Change to ReportOccurrence
            startActivity(e);

        });

        //This will switch to the Edit Profile page when Edit Profile button is clicked.
        profileBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Switched to Profile Page", Toast.LENGTH_SHORT).show();
            Intent b = new Intent(MainActivity.this, Profile.class);
            startActivity(b);
        });




    }
}