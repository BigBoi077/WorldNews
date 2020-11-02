package cegepst.example.worldnews.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cegepst.example.worldnews.R;
import cegepst.example.worldnews.contracts.MainContract;
import cegepst.example.worldnews.models.Article;
import cegepst.example.worldnews.models.ArticleMaker;
import cegepst.example.worldnews.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MenuItem favoriteItem;
    private MainPresenter presenter;
    private ArrayList<Article> articles;
    private ArticleMaker articleMaker;
    private String email;
    private int articleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter((MainContract.View) this);
        initVariables();
        if (getIntent().hasExtra("email")) {
            this.email = getIntent().getStringExtra("email");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.actionFavorite:
                if (favoriteItem == null) {
                    favoriteItem = item;
                }
                presenter.toggleFavorite();
                return true;
            case R.id.terminateSession:
                // TODO : terminate session dialog
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initVariables() {
        articleMaker = new ArticleMaker(getApplicationContext());
        articles = new ArrayList<>();
        articleIndex = 0;
        for (int index = 0; index < 10; index++) {
            articles.add(new Article(index, articleMaker));
        }
    }
}