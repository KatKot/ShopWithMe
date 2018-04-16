package com.kotwicka.shopwithme.persistence.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.kotwicka.shopwithme.persistence.converters.LocalDateConverter;
import com.kotwicka.shopwithme.persistence.dao.ShoppingListDao;
import com.kotwicka.shopwithme.persistence.dao.ShoppingListItemDao;
import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;

@Database(version = 1, entities = {ShoppingList.class, ShoppingListItem.class})
@TypeConverters(LocalDateConverter.class)
public abstract class ShoppingDatabase extends RoomDatabase {

    public abstract ShoppingListDao shoppingListDao();

    public abstract ShoppingListItemDao shoppingListItemDao();
}
