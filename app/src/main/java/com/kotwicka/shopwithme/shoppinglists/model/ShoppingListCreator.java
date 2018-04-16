package com.kotwicka.shopwithme.shoppinglists.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;

import org.joda.time.LocalDate;

public final class ShoppingListCreator {

    private ShoppingListCreator() {

    }

    public static ShoppingList fromName(final String name) {
        final ShoppingList shoppingList = new ShoppingList();
        shoppingList.setArchived(false);
        shoppingList.setCreationDate(LocalDate.now());
        shoppingList.setName(name);
        return shoppingList;
    }
}
