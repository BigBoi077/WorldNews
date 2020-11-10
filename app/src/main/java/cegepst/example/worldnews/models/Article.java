package cegepst.example.worldnews.models;

public class Article {

    private ArticleMaker articleMaker;
    private final String title;
    private final String author;
    private final String description;
    private final int nbrViews;
    private boolean isFavorite;

    public Article(int index, ArticleMaker articleMaker) {
        title = articleMaker.getArticleTitle(index);
        author = articleMaker.getAuthors(index);
        description = articleMaker.getArticleContent(index);
        nbrViews = RandomGenerator.getRandomInRange(Constants.MAX_VIEWS, Constants.MIN_VIEWS);
        isFavorite = false;
    }

    public Article(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.nbrViews = 1;
        isFavorite = false;
    }

    public boolean isFavorite() {
        return isFavorite;
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

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }
}
