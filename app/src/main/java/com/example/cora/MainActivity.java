package com.example.cora;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    //Creating button variables.
    Button logoutBtn;
    Button forumBtn;
    Button reportBtn;
    Button editProBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        //Connecting each var to the buttons on screen


        logoutBtn = findViewById(R.id.Logoutid);
        forumBtn = findViewById(R.id.Forumid);
        reportBtn = findViewById(R.id.ReportOccurenceid);
        editProBtn = findViewById(R.id.EditProfileid);


        //This will swicth to the login page when logout button is clicked.
        logoutBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Login.class));
        });


        //This will switch to the Forum page when the forum button is clicked
        forumBtn.setOnClickListener(v -> {

            Toast.makeText(MainActivity.this, "Switched to Forum", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(MainActivity.this, Forum.class);
            startActivity(a);
        });

        //This will switch to the report occurence page when btn is clicked
        reportBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Switched to Report Occurence Page", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Forum.class)); //Change Forum.class to ReportOccurence.class
        });

        //This will switch to the Edit Profile page when Edit Profile button is clicked.
        editProBtn.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Switched to Edit Profile page", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Profile.class));
        });




    }
}