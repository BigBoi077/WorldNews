package cegepst.example.worldnews.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import cegepst.example.worldnews.R;
import cegepst.example.worldnews.models.Article;
import cegepst.example.worldnews.models.ArticleMaker;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Article> articles;
    private ArticleMaker articleMaker;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
        if (getIntent().hasExtra("email")) {
            this.email = getIntent().getStringExtra("email");
        }

    }

    private void initVariables() {
        articleMaker = new ArticleMaker();
        articles = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            articles.add(new Article(index));
        }
    }
}