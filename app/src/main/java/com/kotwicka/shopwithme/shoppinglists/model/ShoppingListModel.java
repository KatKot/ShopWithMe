package com.kotwicka.shopwithme.shoppinglists.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.persistence.repository.ShoppingListRepository;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;

import io.reactivex.Completable;

public class ShoppingListModel implements ShoppingListContract.Model {

    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListModel(final ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @Override
    public Completable saveShoppingList(final String name) {
        final ShoppingList shoppingList = ShoppingListCreator.fromName(name);
        return shoppingListRepository.insertShoppingList(shoppingList);

    }
}
