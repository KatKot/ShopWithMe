package com.kotwicka.shopwithme.shoppingitems;

import com.kotwicka.shopwithme.persistence.repository.ShoppingItemRepository;
import com.kotwicka.shopwithme.shoppingitems.contract.ShoppingItemContract;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemModel;
import com.kotwicka.shopwithme.shoppingitems.presenter.ShoppingItemPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ShoppingItemModule {

    private final ShoppingItemContract.View view;

    public ShoppingItemModule(ShoppingItemContract.View view) {
        this.view = view;
    }

    @Provides
    public ShoppingItemContract.Presenter providesShoppingItemPresenter(final ShoppingItemContract.Model model) {
        return new ShoppingItemPresenter(view, model);
    }

    @Provides
    public ShoppingItemContract.Model providesShoppingItemModel(final ShoppingItemRepository shoppingItemRepository) {
        return new ShoppingItemModel(shoppingItemRepository);
    }
}
