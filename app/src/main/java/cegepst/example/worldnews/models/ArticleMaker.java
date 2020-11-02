package cegepst.example.worldnews.models;

import android.content.Context;
import android.content.res.Resources;

import cegepst.example.worldnews.R;

public class ArticleMaker {

    private Context context;
    private String[] articleTitles;
    private String[] articleContent;
    private String[] authors;

    public ArticleMaker(Context context) {
        this.context = context;
        articleTitles = context.getResources().getStringArray(R.array.articleTitles);
        articleContent = context.getResources().getStringArray(R.array.articleContent);
        authors = context.getResources().getStringArray(R.array.authors);
    }

    public String getArticleTitle(int index) {
        return articleTitles[index];
    }

    public String getArticleContent(int index) {
        return articleContent[index];
    }

    public String getAuthors(int index) {
        return authors[index];
    }
}
