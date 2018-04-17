package com.kotwicka.shopwithme.persistence.repository;

import com.kotwicka.shopwithme.persistence.db.ShoppingDatabase;
import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListId;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;

public class ShoppingListRepository {

    private final ShoppingDatabase shoppingDatabase;
    private final ShoppingListId shoppingListId;

    public ShoppingListRepository(final ShoppingDatabase shoppingDatabase, ShoppingListId shoppingListId) {
        this.shoppingDatabase = shoppingDatabase;
        this.shoppingListId = shoppingListId;
    }

    public Completable insertShoppingList(final ShoppingList shoppingList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                long id = shoppingDatabase.shoppingListDao().insertShoppingList(shoppingList);
                shoppingListId.setShoppingListId(id);
            }
        });
    }

    public Flowable<List<ShoppingList>> getActiveShoppingLists() {
        return shoppingDatabase.shoppingListDao().getActiveShoppingLists();
    }

    public Flowable<List<ShoppingList>> getArchivedShoppingLists() {
        return shoppingDatabase.shoppingListDao().getArchivedShoppingLists();
    }

    public Completable updateShoppingList(final ShoppingList shoppingList) {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                shoppingDatabase.shoppingListDao().updateShoppingList(shoppingList);
            }
        });
    }
}
