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
    private int favoriteArticlesIndex;
    private boolean isFirstFragment = true;
    private boolean isFavoriteMode = false;
    private boolean isCompactMode = false;

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
                        isFavoriteMode = false;
                        manageArrows();
                        changeFragmentArticle();
                        return true;
                    case R.id.navFavorites:
                        isFavoriteMode = true;
                        favoriteArticlesIndex = 0;
                        manageFavoriteArrows();
                        changeToFavoriteArticles();
                        return true;
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
                        isCompactMode = true;
                        changeFragmentArticle();
                        return true;
                    case R.id.modeFull:
                        Toast.makeText(getApplicationContext(), R.string.mode_full, Toast.LENGTH_SHORT).show();
                        isCompactMode = false;
                        changeFragmentArticle();
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
        leftArrow.setVisibility(View.GONE);
        rightArrow = findViewById(R.id.rightButton);
    }

    private void initArticles() {
        articleMaker = new ArticleMaker(getApplicationContext());
        articles = new ArrayList<>();
        favoriteArticles = new ArrayList<>();
        articleIndex = 0;
        favoriteArticlesIndex = 0;
        for (int index = 0; index < 10; index++) {
            articles.add(new Article(index, articleMaker));
        }
    }

    private void removeArticleWithSameTitle(String title) {
        for (int i = 0; i < favoriteArticles.size(); i++) {
            if (favoriteArticles.get(i).getTitle() == title) {
                favoriteArticles.remove(i);
            }
        }
    }

    @Override
    public void onFavoriteToggle(boolean favorite) {
        if (favoriteItem == null) {
            return;
        }
        if (favorite) {
            favoriteItem.setIcon(R.drawable.ic_favorite_outlined);
            removeArticleWithSameTitle(articles.get(articleIndex).getTitle());
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_filled);
            favoriteArticles.add(articles.get(articleIndex));
            articles.get(articleIndex).setFavorite(true);
        }
    }

    private void changeFragmentArticle() {
        Article currentArticle = articles.get(articleIndex);
        ArticleFragment fragment;
        if (isCompactMode) {
            fragment = makeCompactArticle();
        } else {
            fragment = ArticleFragment.newInstance(
                    currentArticle.getTitle(),
                    currentArticle.getAuthor(),
                    currentArticle.getDescription(),
                    currentArticle.getNbrViews()
            );
        }
        if (isFirstFragment) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
            isFirstFragment = false;
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private ArticleFragment makeCompactArticle() {
        Article currentArticle = articles.get(articleIndex);
        return ArticleFragment.newInstance(
                currentArticle.getTitle(),
                "",
                currentArticle.getDescription(),
                0
        );
    }

    private ArticleFragment makeFavoriteCompactArticle() {
        Article currentArticle;
        if (favoriteArticles.size() == 0) {
            currentArticle = new Article("Vous", "n'avez pas", "de favoris enregistrés");
        } else {
            try {
                currentArticle = favoriteArticles.get(favoriteArticlesIndex);
            } catch (IndexOutOfBoundsException e) {
                currentArticle = favoriteArticles.get(favoriteArticlesIndex - 1);
            }
            return ArticleFragment.newInstance(
                    currentArticle.getTitle(),
                    "",
                    currentArticle.getDescription(),
                    0);
        }
        return ArticleFragment.newInstance(
                currentArticle.getTitle(),
                "",
                currentArticle.getDescription(),
                0
        );
    }

    private void changeFavoriteAccordingToArticle() {
        if (favoriteItem == null) {
            return;
        }
        if (articles.get(articleIndex).isFavorite()) {
            favoriteItem.setIcon(R.drawable.ic_favorite_filled);
        } else {
            favoriteItem.setIcon(R.drawable.ic_favorite_outlined);
        }
    }

    private void changeToFavoriteArticles() {
        Article currentArticle;
        ArticleFragment fragment;
        if (favoriteArticles.size() == 0) {
            currentArticle = new Article("You", "don't", "have any favorites");
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.GONE);
        } else {
            try {
                currentArticle = favoriteArticles.get(favoriteArticlesIndex);
            } catch (IndexOutOfBoundsException e) {
                currentArticle = favoriteArticles.get(favoriteArticlesIndex - 1);
            }
        }
        if (isCompactMode) {
            fragment = makeFavoriteCompactArticle();
        } else {
            fragment = ArticleFragment.newInstance(
                    currentArticle.getTitle(),
                    currentArticle.getAuthor(),
                    currentArticle.getDescription(),
                    currentArticle.getNbrViews()
            );
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void manageFavoriteArticle() {
        if (favoriteArticles.size() == 0) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.GONE);
        }
        manageFavoriteArrows();
        changeToFavoriteArticles();
    }

    private void manageArrows() {
        if (articleIndex == 9) {
            rightArrow.setVisibility(View.GONE);
            leftArrow.setVisibility(View.VISIBLE);
            articleIndex = 9;
            return;
        }
        if (articleIndex == 0) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.VISIBLE);
            articleIndex = 0;
            return;
        }
        rightArrow.setVisibility(View.VISIBLE);
        leftArrow.setVisibility(View.VISIBLE);
    }

    private void manageFavoriteArrows() {
        if (favoriteArticles.size() == 1) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.GONE);
            return;
        }
        if (favoriteArticlesIndex == favoriteArticles.size() - 1) {
            rightArrow.setVisibility(View.GONE);
            leftArrow.setVisibility(View.VISIBLE);
            favoriteArticlesIndex = favoriteArticles.size();
            return;
        }
        if (favoriteArticlesIndex == 0) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.VISIBLE);
            favoriteArticlesIndex = 0;
            return;
        }
        rightArrow.setVisibility(View.VISIBLE);
        leftArrow.setVisibility(View.VISIBLE);
    }

    public void goBackArticle(View view) {
        if (isFavoriteMode) {
            favoriteArticlesIndex--;
            favoriteArticlesIndex--;
            manageFavoriteArticle();
            return;
        }
        articleIndex--;
        manageArrows();
        changeFragmentArticle();
        changeFavoriteAccordingToArticle();
    }

    public void goForwardArticle(View view) {
        if (isFavoriteMode) {
            favoriteArticlesIndex++;
            manageFavoriteArticle();
            return;
        }
        articleIndex++;
        manageArrows();
        changeFragmentArticle();
        changeFavoriteAccordingToArticle();
    }
}