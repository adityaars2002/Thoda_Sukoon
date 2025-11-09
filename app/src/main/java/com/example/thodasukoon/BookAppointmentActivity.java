package com.example.thodasukoon;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BookAppointmentActivity extends AppCompatActivity {

    // In BookAppointmentActivity.java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment); // The layout file will be auto-generated

        // Retrieve data from the Intent
        String doctorName = getIntent().getStringExtra("doctor_name");
        String doctorAddress = getIntent().getStringExtra("doctor_address");

        // Now you can display this info in your layout, e.g., in a TextView
        setTitle("Book with " + doctorName);
    }

}