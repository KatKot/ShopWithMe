package com.kotwicka.shopwithme.shoppinglists.contract;

import io.reactivex.Completable;

public interface ShoppingListContract {

    interface View {
        void showEmptyListNameError();

        void showDuplicatedListNameError();

        void clearListNameError();

        void onNewShoppingListSaved(final long id);

        void showSaveListErrorMessage();
    }

    interface Presenter {
        boolean isValidShoppingList(final String name);

        void saveShoppingList(final String name);

        void onDetachView();
    }

    interface Model {
        Completable saveShoppingList(final String name);
    }
}
