package cegepst.example.worldnews.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import cegepst.example.worldnews.R;
import cegepst.example.worldnews.contracts.MainContract;
import cegepst.example.worldnews.models.Article;
import cegepst.example.worldnews.models.ArticleMaker;
import cegepst.example.worldnews.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private MenuItem favoriteItem;
    private MainPresenter presenter;
    private ImageButton leftArrow;
    private ImageButton rightArrow;

    private ArrayList<Article> articles;
    private ArrayList<Article> favoriteArticles;
    private ArticleMaker articleMaker;

    private String email;
    private int articleIndex;
    private boolean isFirstFragment = true;

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
                        Toast.makeText(getApplicationContext(), R.string.mode_compact, Toast.LENGTH_SHORT).show();
                        // TODO : put article mode compact
                        return true;
                    case R.id.modeFull:
                        Toast.makeText(getApplicationContext(), R.string.mode_full, Toast.LENGTH_SHORT).show();
                        // TODO : put article mode full
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra("email")) {
            this.email = getIntent().getStringExtra("email");
        }
        presenter = new MainPresenter(this);
        initVariables();
        initBottomNavigation();
        initDrawerNavigation();
        changeFragmentArticle();
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
        initArticles();
        initComponents();
    }

    private void initComponents() {
        leftArrow = findViewById(R.id.leftButton);
        rightArrow = findViewById(R.id.rightButton);
    }

    private void initArticles() {
        articleMaker = new ArticleMaker(getApplicationContext());
        articles = new ArrayList<>();
        articleIndex = 1;
        for (int index = 0; index < 10; index++) {
            articles.add(new Article(index, articleMaker));
        }
    }

    @Override
    public void onFavoriteToggle(boolean favorite) {
        if (favoriteItem == null) {
            return;
        }
        favoriteItem.setIcon(favorite ? R.drawable.ic_favorite_filled : R.drawable.ic_favorite_outlined);
    }

    private void changeFragmentArticle() {
        Article currentArticle = articles.get(articleIndex);
        ArticleFragment articleFragment = ArticleFragment.newInstance(
                currentArticle.getTitle(),
                currentArticle.getAuthor(),
                currentArticle.getDescription(),
                currentArticle.getNbrViews()
        );
        if (isFirstFragment) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, articleFragment)
                    .commit();
            isFirstFragment = false;
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, articleFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void goBackArticle(View view) {
        articleIndex--;
        if (articleIndex == 0) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.VISIBLE);
            articleIndex = 0;
            changeFragmentArticle();
        } else {
            leftArrow.setVisibility(View.VISIBLE);
            rightArrow.setVisibility(View.VISIBLE);
            changeFragmentArticle();
        }
    }

    public void goForwardArticle(View view) {
        articleIndex++;
        if (articleIndex == 9) {
            rightArrow.setVisibility(View.GONE);
            leftArrow.setVisibility(View.VISIBLE);
            articleIndex = 9;
            changeFragmentArticle();
        } else {
            rightArrow.setVisibility(View.VISIBLE);
            leftArrow.setVisibility(View.VISIBLE);
            changeFragmentArticle();
        }
    }
}