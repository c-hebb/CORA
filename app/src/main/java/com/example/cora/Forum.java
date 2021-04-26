package com.example.cora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Forum extends AppCompatActivity {

    //Creating Edit Text variables
    TextView NameBox, DateBox, MessageBox;

    //Creating vars for the posted forums.
    TextView NamePosted, DatePosted, MessagePosted;

    //Creating Button Variable
    Button SaveEntryBtn;
    Button BackBtn;

    String SHARED_PREFS = "shared prefs";

    String nameSaved = "No name Entered";
    String nameSaved1 = "";

    String dateSaved = "No Date Entered";
    String dateSaved1 = "";

    String messageSaved = "No Message Entered";
    String messageSaved1 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);


        //target element
        NameBox = findViewById(R.id.NameTxt);
        DateBox = findViewById(R.id.DateTxt);
        MessageBox = findViewById(R.id.MessageTxt);

        NamePosted = findViewById(R.id.NameId);
        DatePosted = findViewById(R.id.DateId);
        MessagePosted = findViewById(R.id.MessageId);

        SaveEntryBtn = findViewById(R.id.SaveEntryBtn);
        BackBtn = findViewById(R.id.BackBtnid);


        //Code to execute when "save entry" button is tapped.
        SaveEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Code to occur when the save button is clicked.

                //save data
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(nameSaved, NameBox.getText().toString());
                editor.putString(dateSaved, DateBox.getText().toString());
                editor.putString(messageSaved, MessageBox.getText().toString());
                editor.apply();

                nameSaved1 = sharedPreferences.getString(nameSaved, "");
                dateSaved1 = sharedPreferences.getString(dateSaved, "");
                messageSaved1 = sharedPreferences.getString(messageSaved, "");

                NamePosted.setText(nameSaved1);
                DatePosted.setText(dateSaved1);
                MessagePosted.setText(messageSaved1);

                NameBox.setText("");
                DateBox.setText("");
                MessageBox.setText("");

            }
        });

        //Back to Main Menu when the back button is pressed.
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Forum.this, "Back to Main", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
    }
}