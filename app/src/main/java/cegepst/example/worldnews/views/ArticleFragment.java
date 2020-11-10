package cegepst.example.worldnews.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cegepst.example.worldnews.R;

public class ArticleFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_AUTHOR = "author";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_VIEWS = "nbrViews";

    private String articleTitle;
    private String articleAuthor;
    private String articleDescription;
    private int nbrViews;

    public static ArticleFragment newInstance(String title, String author,
                                              String description, int nbrViews) {
        Bundle args = new Bundle();
        ArticleFragment fragment = new ArticleFragment();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_AUTHOR, author);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_VIEWS, nbrViews);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            articleTitle = getArguments().getString(ARG_TITLE);
            articleAuthor = getArguments().getString(ARG_AUTHOR);
            articleDescription = getArguments().getString(ARG_DESCRIPTION);
            nbrViews = getArguments().getInt(ARG_VIEWS);
        } else {
            articleTitle = "Default title";
            articleAuthor = "John Doe";
            articleDescription = "Lorem Ipsum";
            nbrViews = 666;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView articleTitle = view.findViewById(R.id.articleTitle);
        articleTitle.setText(this.articleTitle);

        TextView articleAuthor = view.findViewById(R.id.articleAuthor);
        articleAuthor.setText(this.articleAuthor);

        TextView numberViews = view.findViewById(R.id.articleViews);
        numberViews.setText(this.nbrViews + " vues");

        TextView articleDescription = view.findViewById(R.id.articleDescription);
        articleDescription.setText(this.articleDescription);
    }
}
