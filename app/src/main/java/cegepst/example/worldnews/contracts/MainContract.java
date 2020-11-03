package cegepst.example.worldnews.contracts;

public interface MainContract {
    interface View {
        void onFavoriteToggle(boolean favorite);
    }
    interface Presenter {
        void toggleFavorite();
        boolean getFavoriteState();
    }
}
