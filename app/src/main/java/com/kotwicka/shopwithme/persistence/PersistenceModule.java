package com.kotwicka.shopwithme.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.kotwicka.shopwithme.persistence.db.ShoppingDatabase;
import com.kotwicka.shopwithme.persistence.repository.ShoppingItemRepository;
import com.kotwicka.shopwithme.persistence.repository.ShoppingListRepository;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListId;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    private static final String SHOPPING_DB_NAME = "shopping";

    @Provides
    @Singleton
    public ShoppingDatabase providesDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, ShoppingDatabase.class, SHOPPING_DB_NAME).build();
    }

    @Provides
    @Singleton
    public ShoppingListRepository providesShoppingListRepository(final ShoppingDatabase shoppingDatabase, final ShoppingListId shoppingListId) {
        return new ShoppingListRepository(shoppingDatabase, shoppingListId);
    }

    @Provides
    @Singleton
    public ShoppingItemRepository providesShoppingListItemRepository(final ShoppingDatabase shoppingDatabase) {
        return new ShoppingItemRepository(shoppingDatabase);
    }

    @Provides
    @Singleton
    public ShoppingListId providesShoppingListId() {
        return new ShoppingListId();
    }
}
