package com.kotwicka.shopwithme.shoppingitems.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;
import com.kotwicka.shopwithme.persistence.repository.ShoppingItemRepository;
import com.kotwicka.shopwithme.shoppingitems.contract.ShoppingItemContract;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class ShoppingItemModel implements ShoppingItemContract.Model {

    private final ShoppingItemRepository shoppingListItemRepository;

    public ShoppingItemModel(ShoppingItemRepository shoppingListItemRepository) {
        this.shoppingListItemRepository = shoppingListItemRepository;
    }

    @Override
    public Flowable<List<ShoppingItemViewModel>> getShoppingItems(final long listId) {
        return shoppingListItemRepository.getShoppingListItems(listId)
                .flatMap(new Function<List<ShoppingListItem>, Flowable<List<ShoppingItemViewModel>>>() {
                    @Override
                    public Flowable<List<ShoppingItemViewModel>> apply(List<ShoppingListItem> shoppingListItems) throws Exception {
                        return Flowable.fromIterable(shoppingListItems).map(new Function<ShoppingListItem, ShoppingItemViewModel>() {
                            @Override
                            public ShoppingItemViewModel apply(ShoppingListItem item) throws Exception {
                                return new ShoppingItemViewModel(item.getName());
                            }
                        }).toList().toFlowable();
                    }
                });
    }

    @Override
    public Completable saveShoppingItem(final long listId, final String shoppingItemName) {
        return shoppingListItemRepository.insertShoppingListItem(ShoppingListItemCreator.fromNameAndListId(shoppingItemName, listId));
    }
}
