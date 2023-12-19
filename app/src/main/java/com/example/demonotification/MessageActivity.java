package com.example.demonotification;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        String dulieu = getIntent().getStringExtra("duLieu");
        Toast.makeText(this, "Du lieu:  " + dulieu, Toast.LENGTH_SHORT).show();
    }
}