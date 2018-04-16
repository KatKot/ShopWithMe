package com.kotwicka.shopwithme.shoppinglists;

import com.kotwicka.shopwithme.persistence.repository.ShoppingListRepository;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListId;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListModel;
import com.kotwicka.shopwithme.shoppinglists.presenter.ShoppingListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ShoppingListModule {

    private final ShoppingListContract.View view;

    public ShoppingListModule(final ShoppingListContract.View view) {
        this.view = view;
    }

    @Provides
    public ShoppingListContract.Presenter providesShoppingListPresenter(final ShoppingListContract.Model model, final ShoppingListId shoppingListId) {
        return new ShoppingListPresenter(view, model, shoppingListId);
    }

    @Provides
    public ShoppingListContract.Model providesShoppingListModel(final ShoppingListRepository repository) {
        return new ShoppingListModel(repository);
    }
}
