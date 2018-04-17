package com.kotwicka.shopwithme.persistence.repository;

import com.kotwicka.shopwithme.persistence.db.ShoppingDatabase;
import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;

public class ShoppingItemRepository {

    private final ShoppingDatabase shoppingDatabase;

    public ShoppingItemRepository(ShoppingDatabase shoppingDatabase) {
        this.shoppingDatabase = shoppingDatabase;
    }

    public Completable insertShoppingListItem(final ShoppingListItem shoppingListItem) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                shoppingDatabase.shoppingListItemDao().insertShoppingListItem(shoppingListItem);
            }
        });
    }

    public Flowable<List<ShoppingListItem>> getShoppingListItems(final long shoppingListId) {
        return shoppingDatabase.shoppingListItemDao().getItemsForShoppingList(shoppingListId);
    }
}
