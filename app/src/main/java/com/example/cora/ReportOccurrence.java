package com.example.cora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class ReportOccurrence extends AppCompatActivity {
    TextView Title, Rate, Describe, Zip;
    Button Cancel, Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_CORA);
        setContentView(R.layout.activity_report_occurrence);

        Title = findViewById(R.id.Title);
        Rate = findViewById(R.id.Rating);
        Zip = findViewById(R.id.Location);
        Describe = findViewById(R.id.Describe);
        Submit = findViewById(R.id.Submit);
        Cancel = findViewById(R.id.Cancel);

        Submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String TitleVal = Title.getText().toString().trim();
                String ZipVal = Zip.getText().toString().trim();
                String RateVal = Rate.getText().toString().trim();
                String DescribeVal = Describe.getText().toString().trim();
                int Ratee;
                int Locat;

                if(TextUtils.isEmpty(TitleVal)){
                    Title.setError("Title is required");
                    return;
                }
                if (TitleVal.length() < 1) {
                    Title.setError("Title should have 1 or more character");
                    return;
                }
                if(TextUtils.isEmpty(ZipVal)){
                    Zip.setError("Location is required");
                    return;
                }
                if (ZipVal.length() != 5) {
                    Zip.setError("Location should be 5 digit zip code");
                    return;
                }
                try{
                    Locat = Integer.parseInt(ZipVal.trim());
                }
                catch (NumberFormatException nfe){
                    Zip.setError("Location should be 5 digit zip code");
                    return;
                }
                if(TextUtils.isEmpty(RateVal)){
                    Rate.setError("Crime severity rate is required");
                    return;
                }
                if (RateVal.length() > 2 || RateVal.length() < 0) {
                    Rate.setError("Crime severity rate should be rated from 1 to 10");
                    return;
                }
                try {
                    Ratee = Integer.parseInt(RateVal.trim());
                }
                catch(NumberFormatException nfe) {
                    Rate.setError("Crime severity rate should be a number from 1 to 10");
                    return;
                }
                if(Ratee > 10 || Ratee < 1)
                {
                    Rate.setError("Crime severity rate should be a number from 1 to 10");
                }
                if(TextUtils.isEmpty(DescribeVal)){
                    Describe.setError("Describing the occurrence is required");
                    return;
                }
                if (DescribeVal.length() < 1) {
                    Describe.setError("Describing the occurrence should have 1 or more character");
                    return;
                }
                Toast.makeText(ReportOccurrence.this, "The report has been submitted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}