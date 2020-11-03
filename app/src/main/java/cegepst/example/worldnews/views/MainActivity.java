package cegepst.example.worldnews.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

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
    private ArrayList<Article> favoriteArticles;
    private ArticleMaker articleMaker;
    private String email;
    private int articleIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra("email")) {
            this.email = getIntent().getStringExtra("email");
        }
        // presenter = new MainPresenter(this);
        initVariables();
        initBottomNavigation();
        initDrawerNavigation();
    }

    private void initDrawerNavigation() {
        DrawerLayout drawerLayout = findViewById(R.id.menuDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.action_open, R.string.action_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        NavigationView navigationView = findViewById(R.id.menuDrawerNav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navHome:
                        return true;
                    case R.id.navFavorites:
                        // TODO : only show favorites
                    default:
                        return false;
                }
            }
        });
    }

    private void initBottomNavigation() {
        BottomNavigationView navigationView = findViewById(R.id.menuBottom);
        navigationView.setSelectedItemId(R.id.modeCompact);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.modeCompact:
                        // TODO : put article mode compact
                        return true;
                    case R.id.modeFull:
                        // TODO : put article mode full
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionFavorite:
                if (favoriteItem == null) {
                    favoriteItem = item;
                }
                // presenter.toggleFavorite();
                return true;
            case R.id.actionTerminateSession:
                terminateSessionDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void terminateSessionDialog() {
        new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.terminate_session)
                .setMessage(R.string.terminate_session_message)
                .setPositiveButton(R.string.close_session_true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        terminateSession();
                    }
                }).setNegativeButton(R.string.close_session_false, null).show();
    }

    private void terminateSession() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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