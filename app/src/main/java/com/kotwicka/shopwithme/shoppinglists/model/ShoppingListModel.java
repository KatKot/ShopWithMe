package com.kotwicka.shopwithme.shoppinglists.model;

import com.kotwicka.shopwithme.persistence.dao.ShoppingListDao;
import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.persistence.repository.ShoppingListRepository;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;

import io.reactivex.Completable;
import io.reactivex.functions.Action;

public class ShoppingListModel implements ShoppingListContract.Model {

    private final ShoppingListRepository shoppingListRepository;

    public ShoppingListModel(final ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @Override
    public Completable saveShoppingList(final String name) {
        final ShoppingList shoppingList = new ShoppingList(name);
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                shoppingListRepository.insertShoppingList(shoppingList);
            }
        });

    }
}
