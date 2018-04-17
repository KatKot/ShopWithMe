package com.kotwicka.shopwithme.shoppinglists.contract;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface ShoppingListContract {

    interface View {
        void showEmptyListNameError();

        void showDuplicatedListNameError();

        void clearListNameError();

        void onNewShoppingListSaved(final long id);

        void showSaveListErrorMessage();

        void setShoppingLists(final List<ShoppingListViewModel> shoppingListViewModel);
    }

    interface Presenter {
        boolean isValidShoppingList(final String name);

        void saveShoppingList(final String name);

        void fetchActiveShoppingLists();

        void onDetachView();
    }

    interface Model {
        Completable saveShoppingList(final String name);
        Flowable<List<ShoppingListViewModel>> getActiveShoppingLists();
    }
}
