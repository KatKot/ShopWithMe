package com.kotwicka.shopwithme.shoppinglists.contract;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface ShoppingListContract {

    interface View {
        void showEmptyListNameError();
        void showDuplicatedListNameError();
        void clearListNameError();
        void onNewShoppingListSaved(final String name);
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
