package cegepst.example.worldnews.presenters;

import java.lang.ref.WeakReference;

import cegepst.example.worldnews.contracts.MainContract;
import cegepst.example.worldnews.views.MainActivity;

public class MainPresenter implements MainContract.Presenter {

    private WeakReference<MainContract.View> viewRef;
    private boolean isFavorite;

    public MainPresenter(MainContract.View view) {
        viewRef = new WeakReference<>(view);
        isFavorite = false;
    }

    @Override
    public void toggleFavorite() {
        isFavorite = !isFavorite;
        MainContract.View view = viewRef.get();
        if (view == null) {
            return;
        }
        view.onFavoriteToggle(isFavorite);
    }

    @Override
    public boolean getFavoriteState() {
        return this.isFavorite;
    }
}
