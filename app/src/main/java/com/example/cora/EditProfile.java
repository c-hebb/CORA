package com.example.cora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText editName, editStudentID, editAddress, editEmail, editProfession;
    Button saveChange;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //receive the user data
        Intent data = getIntent();
        String fullName = data.getStringExtra("full name");
        String studentID = data.getStringExtra("ID");
        String address = data.getStringExtra("address");
        String email = data.getStringExtra("email");
        String profession = data.getStringExtra("profession");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        //link variables to textView
        editName = findViewById(R.id.editName);
        editStudentID = findViewById(R.id.editStudentID);
        editAddress = findViewById(R.id.editAddress);
        editEmail = findViewById(R.id.editEmail);
        editProfession = findViewById(R.id.editProfession);
        saveChange = findViewById(R.id.saveChange);

        //save the user data changes
        saveChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //check for empty fields
                if(editName.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty() || editStudentID.getText().toString().isEmpty() || editAddress.getText().toString().isEmpty() || editStudentID.getText().toString().isEmpty() || editProfession.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this, "Fields not valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                //update users registered email
                String email = editEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    //update the users information
                    public void onSuccess(Void aVoid) {
                        DocumentReference ref = fStore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("Address",editAddress.getText().toString());
                        edited.put("Email",editEmail.getText().toString());
                        edited.put("FullName",editName.getText().toString());
                        edited.put("Profession",editProfession.getText().toString());
                        edited.put("UTAid",editStudentID.getText().toString());
                        ref.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                                finish();
                            }
                        });
                        Toast.makeText(EditProfile.this, "Email is Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        //fill the textViews with the current user data
        editName.setText(fullName);
        editStudentID.setText(studentID);
        editAddress.setText(address);
        editProfession.setText(profession);
        editEmail.setText(email);

        //Test
        Log.d(TAG, "onCreate" + fullName + " " + email + " " + studentID + " " + address + " " + profession);
    }
}