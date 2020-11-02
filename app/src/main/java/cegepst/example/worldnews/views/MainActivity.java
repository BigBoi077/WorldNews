package cegepst.example.worldnews.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import cegepst.example.worldnews.R;
import cegepst.example.worldnews.models.ArticleMaker;

public class MainActivity extends AppCompatActivity {

    private ArticleMaker articleMaker;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra("email")) {
            this.email = getIntent().getStringExtra("email");
        }
        articleMaker = new ArticleMaker();
    }
}