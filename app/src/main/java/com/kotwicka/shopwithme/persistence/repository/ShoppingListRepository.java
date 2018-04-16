package com.kotwicka.shopwithme.persistence.repository;

import com.kotwicka.shopwithme.persistence.db.ShoppingDatabase;
import com.kotwicka.shopwithme.persistence.entity.ShoppingList;

public class ShoppingListRepository {

    private final ShoppingDatabase shoppingDatabase;

    public ShoppingListRepository(final ShoppingDatabase shoppingDatabase) {
        this.shoppingDatabase = shoppingDatabase;
    }

    public void insertShoppingList(final ShoppingList shoppingList) {
        this.shoppingDatabase.shoppingListDao().insertShoppingList(shoppingList);
    }
}
