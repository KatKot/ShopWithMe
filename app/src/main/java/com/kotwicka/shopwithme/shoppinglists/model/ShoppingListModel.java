package com.kotwicka.shopwithme.shoppinglists.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.persistence.repository.ShoppingListRepository;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

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

    @Override
    public Flowable<List<ShoppingListViewModel>> getActiveShoppingLists() {
        return shoppingListRepository.getActiveShoppingLists()
                .flatMap(new Function<List<ShoppingList>, Flowable<List<ShoppingListViewModel>>>() {
                    @Override
                    public Flowable<List<ShoppingListViewModel>> apply(List<ShoppingList> shoppingLists) throws Exception {
                        return Flowable.fromIterable(shoppingLists)
                                .map(new Function<ShoppingList, ShoppingListViewModel>() {
                                    @Override
                                    public ShoppingListViewModel apply(ShoppingList shoppingList) throws Exception {
                                        return new ShoppingListViewModel(shoppingList.getName(), shoppingList.getCreationDate(), shoppingList.getId());
                                    }
                                }).toList().toFlowable();
                    }
                });
    }
}
