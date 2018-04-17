package com.kotwicka.shopwithme.shoppinglists.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;

import org.joda.time.DateTime;

public final class ShoppingListCreator {

    private ShoppingListCreator() {

    }

    public static ShoppingList fromName(final String name) {
        final ShoppingList shoppingList = new ShoppingList();
        shoppingList.setArchived(false);
        shoppingList.setCreationDate(DateTime.now());
        shoppingList.setName(name);
        return shoppingList;
    }
}
