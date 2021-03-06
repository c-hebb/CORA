package com.example.cora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    // Variable Setup
    public static final String TAG = "TAG";
    EditText mFullName, mEmail, mPassword, mUTAid, mAddress, mProfession;
    Button mRegisterButton;
    TextView mLoginButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_CORA);
        setContentView(R.layout.activity_register);

        //Defines Variable based on User Input from Fields
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mUTAid = findViewById(R.id.UTAid);
        mAddress = findViewById(R.id.Address);
        mProfession = findViewById(R.id.profession);
        mRegisterButton = findViewById(R.id.registerButton);
        mLoginButton = findViewById(R.id.login_text);

        //Instances Firebase Authentication and Firestore
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Checks if User is already logged in, if so takes to Main page
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Text Field Verification
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString().trim();
                String UTAid = mUTAid.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                String profession = mProfession.getText().toString().trim();

                //Email REQUIRED
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                //Password REQUIRED
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                //Password length must be at least 6 characters
                if (password.length() < 6) {
                    mPassword.setError("Password must have 6 characters or more.");
                    return;
                }

                //Full Name REQUIRED
                if (TextUtils.isEmpty(fullName)) {
                    mFullName.setError("Full Name is Required.");
                    return;
                }

                //UTA ID Required TODO: Validate for Numbers only
                if (TextUtils.isEmpty(UTAid)) {
                    mUTAid.setError("UTA ID # is Required.");
                    return;
                }

                //Registers the User and Adds to Database
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If Authentication is Successful:
                        if(task.isSuccessful()) {

                            //Email Verification || (Part 4 @ 7Mins for Sending Again in profile if not verified)
                            FirebaseUser fUser = fAuth.getCurrentUser();
                            fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent: " + e.getMessage());
                                }
                            });

                            // Success Message
                            Toast.makeText(Register.this, "User Created!", Toast.LENGTH_SHORT).show();

                            //Add Additional Data to the Firestore database
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("FullName", fullName);
                            user.put("UTAid", UTAid);
                            user.put("Address", address);
                            user.put("Profession", profession);
                            user.put("Email", email);

                            //Pushes the data to the database
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User Profile is Created for: " + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });

                            // Sends User to Main Page
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            //If not Successful, display error.
                            Toast.makeText(Register.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //If Already Have Account: Send to Login Page || "Login Here"
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });




    }

}
