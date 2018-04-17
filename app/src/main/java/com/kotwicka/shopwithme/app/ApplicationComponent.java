package com.kotwicka.shopwithme.app;

import com.kotwicka.shopwithme.persistence.PersistenceModule;
import com.kotwicka.shopwithme.shoppingitems.ShoppingItemComponent;
import com.kotwicka.shopwithme.shoppingitems.ShoppingItemModule;
import com.kotwicka.shopwithme.shoppinglists.ShoppingListComponent;
import com.kotwicka.shopwithme.shoppinglists.ShoppingListModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class, PersistenceModule.class})
@Singleton
public interface ApplicationComponent {

    ShoppingListComponent plusShoppingListComponent(ShoppingListModule module);
    ShoppingItemComponent plusShoppingItemComponent(ShoppingItemModule module);
}
