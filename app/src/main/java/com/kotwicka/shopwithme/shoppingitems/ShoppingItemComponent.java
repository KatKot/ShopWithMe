package com.kotwicka.shopwithme.shoppingitems;

import com.kotwicka.shopwithme.shoppingitems.view.ShoppingItemsActivity;

import dagger.Subcomponent;

@Subcomponent(modules = ShoppingItemModule.class)
public interface ShoppingItemComponent {

    void inject(ShoppingItemsActivity shoppingItemsActivity);
}
