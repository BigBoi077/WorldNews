package cegepst.example.worldnews.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import cegepst.example.worldnews.R;

public class MainActivity extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra("email")) {
            this.email = getIntent().getStringExtra("email");
        }
    }
}