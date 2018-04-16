package com.kotwicka.shopwithme.shoppinglists;

import com.kotwicka.shopwithme.shoppinglists.view.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {ShoppingListModule.class})
public interface ShoppingListComponent {

    void inject(MainActivity mainActivity);
}
