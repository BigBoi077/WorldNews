package cegepst.example.worldnews.models;

import android.content.res.Resources;

import cegepst.example.worldnews.R;

public class ArticleMaker {

    private Resources resources;
    private String[] articleTitles;
    private String[] articleContent;
    private String[] authors;

    public ArticleMaker() {
        resources = Resources.getSystem();
        articleTitles = getStringArray(R.array.articleTitles);
        articleContent = getStringArray(R.array.articleContent);
        authors = getStringArray(R.array.authors);
    }

    private String[] getStringArray(int id) {
        return resources.getStringArray(id);
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
