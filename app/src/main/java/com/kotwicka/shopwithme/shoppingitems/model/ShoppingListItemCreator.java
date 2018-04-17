package com.kotwicka.shopwithme.shoppingitems.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;

public final class ShoppingListItemCreator {

    private ShoppingListItemCreator() {

    }

    public static ShoppingListItem fromNameAndListId(final String name, final long shoppingListId) {
        final ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setName(name);
        shoppingListItem.setShoppingListId(shoppingListId);
        return shoppingListItem;
    }
}
