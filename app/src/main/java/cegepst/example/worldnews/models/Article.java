package cegepst.example.worldnews.models;

public class Article {

    private ArticleMaker articleMaker;
    private String title;
    private String author;
    private String description;
    private int nbrViews;
    private boolean compactMode;

    public Article(int index, ArticleMaker articleMaker) {
        title = articleMaker.getArticleTitle(index);
        author = articleMaker.getAuthors(index);
        description = articleMaker.getArticleContent(index);
        nbrViews = RandomGenerator.getRandomInRange(Constants.MAX_VIEWS, Constants.MIN_VIEWS);
        compactMode = false;
    }

    public void setCompactMode(boolean compactMode) {
        this.compactMode = compactMode;
    }

    public ArticleMaker getArticleMaker() {
        return articleMaker;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getNbrViews() {
        return nbrViews;
    }

    public boolean isCompactMode() {
        return compactMode;
    }
}
