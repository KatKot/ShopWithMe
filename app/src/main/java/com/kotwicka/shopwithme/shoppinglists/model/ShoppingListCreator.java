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

    public static ShoppingList fromVieModel(final ShoppingListViewModel viewModel) {
        final ShoppingList shoppingList = new ShoppingList();
        shoppingList.setCreationDate(viewModel.getCreationDateTime());
        shoppingList.setName(viewModel.getName());
        shoppingList.setId(viewModel.getId());
        return shoppingList;
    }
}
