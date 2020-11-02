package cegepst.example.worldnews.models;

public class Article {

    private ArticleMaker articleMaker;
    private String title;
    private String author;
    private String description;
    private int nbrViews;
    private boolean compactMode;

    public Article(ArticleMaker articleMaker) {
        this.articleMaker = articleMaker;
    }

    public Article(int index) {
        title = articleMaker.getArticleTitle(index);
        author = articleMaker.getAuthors(index);
        description = articleMaker.getArticleContent(index);
    }
}
