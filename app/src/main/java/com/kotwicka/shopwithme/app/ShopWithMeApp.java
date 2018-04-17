package com.kotwicka.shopwithme.app;

import android.app.Application;

import com.kotwicka.shopwithme.shoppingitems.ShoppingItemComponent;
import com.kotwicka.shopwithme.shoppingitems.ShoppingItemModule;
import com.kotwicka.shopwithme.shoppingitems.contract.ShoppingItemContract;
import com.kotwicka.shopwithme.shoppinglists.ShoppingListComponent;
import com.kotwicka.shopwithme.shoppinglists.ShoppingListModule;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;

public class ShopWithMeApp extends Application {

    private static ShopWithMeApp INSTANCE;
    private ApplicationComponent applicationComponent;
    private ShoppingListComponent shoppingListComponent;
    private ShoppingItemComponent shoppingItemComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(INSTANCE))
                .build();
    }

    public static ShopWithMeApp get() {
        return INSTANCE;
    }

    public ShoppingListComponent withShoppingListComponent(final ShoppingListContract.View view) {
        if (shoppingListComponent == null) {
            shoppingListComponent = applicationComponent.plusShoppingListComponent(new ShoppingListModule(view));
        }
        return shoppingListComponent;
    }

    public void clearShoppingListComponent() {
        this.shoppingListComponent = null;
    }

    public ShoppingItemComponent withShoppingItemComponent(final ShoppingItemContract.View view) {
        if (shoppingItemComponent == null) {
            shoppingItemComponent = applicationComponent.plusShoppingItemComponent(new ShoppingItemModule(view));
        }
        return shoppingItemComponent;
    }

    public void clearShoppingItemComponent() {
        this.shoppingItemComponent = null;
    }
}
