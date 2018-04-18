package com.kotwicka.shopwithme.shoppinglists.contract;

import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface ShoppingListContract {

    interface View {
        void showEmptyListNameError();

        void clearListNameError();

        void onNewShoppingListSaved(final long id, final String name);

        void setShoppingLists(final List<ShoppingListViewModel> shoppingListViewModel);
    }

    interface Presenter {
        boolean isValidShoppingList(final String name);

        void saveShoppingList(final String name);

        void fetchShoppingLists(final boolean shouldFetchActiveLists);

        void archiveShoppingList(final ShoppingListViewModel shoppingListViewModel);

        void onDetachView();
    }

    interface Model {
        Completable archiveShoppingList(final ShoppingListViewModel shoppingListViewModel);

        Completable saveShoppingList(final String name);

        Flowable<List<ShoppingListViewModel>> getActiveShoppingLists();

        Flowable<List<ShoppingListViewModel>> getArchiveShoppingLists();
    }
}
