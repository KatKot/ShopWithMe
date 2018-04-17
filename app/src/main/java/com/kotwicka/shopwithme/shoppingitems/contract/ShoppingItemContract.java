package com.kotwicka.shopwithme.shoppingitems.contract;

import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemViewModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface ShoppingItemContract {

    interface View {
        void setShoppingItems(final List<ShoppingItemViewModel> shoppingItems);
    }

    interface Presenter {

        void saveShoppingItem(final long listId, final String shoppingItemName);

        void loadShoppingItems(final long listId);

        void onDetachView();

    }

    interface Model {

        Flowable<List<ShoppingItemViewModel>> getShoppingItems(final long listId);

        Completable saveShoppingItem(final long listId, final String shoppingItemName);
    }
}
